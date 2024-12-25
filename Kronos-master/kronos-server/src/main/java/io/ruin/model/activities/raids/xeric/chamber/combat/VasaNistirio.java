package io.ruin.model.activities.raids.xeric.chamber.combat;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class VasaNistirio extends NPCCombat {

    static {
        NPCDef.get(7565).ignoreOccupiedTiles = true;
        NPCDef.get(7566).ignoreOccupiedTiles = true;
        NPCDef.get(7567).ignoreOccupiedTiles = true;
    }

    private static final Projectile TELEPORT_ATTACK_PROJECTILE = new Projectile(1327, 90, 0, 25, 75, 0, 16, 0);
    private static final Projectile ROCK_PROJECTILE = new Projectile(1329, 90, 0, 25, 75, 0, 16, 0);
    private Position[] tpAttackProjectiles; // positions where the teleport attack projectiles are fired
    private List<Position> closeTeleportPositions;
    private List<Position> farTeleportPositions;
    private List<NPC> crystals;
    private NPC channeledCrystal;

    private int charges;

    @Override
    public void init() {
        tpAttackProjectiles = new Position[]{
            npc.getSpawnPosition().relative(-1, -1),
            npc.getSpawnPosition().relative(2, -1),
            npc.getSpawnPosition().relative(5, -1),
            npc.getSpawnPosition().relative(5, 2),
            npc.getSpawnPosition().relative(5, 5),
            npc.getSpawnPosition().relative(2, 5),
            npc.getSpawnPosition().relative(-1, 5),
            npc.getSpawnPosition().relative(-1, 2),
        };
        closeTeleportPositions = npc.getSpawnPosition().relative(2, 2).area(3, pos -> !pos.inBounds(npc.getBounds())); // edges of the boss
        farTeleportPositions = new ArrayList<>(36);
        farTeleportPositions.addAll(npc.getSpawnPosition().relative(-6, 3).area(1));
        farTeleportPositions.addAll(npc.getSpawnPosition().relative(2, 11).area(1));
        farTeleportPositions.addAll(npc.getSpawnPosition().relative(10, 2).area(1));
        farTeleportPositions.addAll(npc.getSpawnPosition().relative(1, -6).area(1));
    }

    private void setCrystals() {
        crystals = StreamSupport.stream(npc.localNpcs().spliterator(), false)
                .filter(n -> n.getId() == 7568)
                .limit(4)
                .collect(Collectors.toList());
        crystals.forEach(crystal -> setCrystalVulnerable(crystal, false));
    }

    private void teleportAttack() {
        charges = 0;
        npc.addEvent(event -> {
            npc.lock();
            event.setCancelCondition(() -> isDead() || target == null);
            if (npc.getId() == 7565) {
                setCrystals(); // have to do this here instead of in init method because getting an npc's local npcs requires players to be nearby
                npc.animate(7408);
                event.delay(6);
                npc.transform(7566);
            }
            if (!npc.isAt(npc.getSpawnPosition())) {
                npc.getRouteFinder().routeAbsolute(npc.getSpawnPosition().getX(), npc.getSpawnPosition().getY());
                int ticks = 0;
                while (!npc.getMovement().isAtDestination()) {
                    if (ticks++ % 3 == 0)
                        rocks();
                    event.delay(1);
                }
                event.delay(1);
            }
            npc.animate(7409);
            List<Player> players = npc.localPlayers().stream().filter(p -> ProjectileRoute.allow(npc, p)).collect(Collectors.toList());
            players.forEach(p -> {
                p.addEvent(e -> {
                    e.setCancelCondition(() -> p.dead());
                    p.lock();
                    p.animate(3865);
                    p.graphics(1296);
                    e.delay(7);
                    if (players.size() == 1 || Random.get() > 0.5) {
                        //teleport close
                        p.stun(4, true);
                        p.getMovement().teleport(Random.get(closeTeleportPositions));
                    } else {
                        //teleport far
                        p.getMovement().teleport(Random.get(farTeleportPositions));
                    }
                    p.unlock();
                    p.animate(-1);
                });
            });
            int damage = target.getHp() - 5;
            event.delay(11);
            npc.animate(7410);
            for (Position pos : tpAttackProjectiles) {
                int delay = TELEPORT_ATTACK_PROJECTILE.send(npc, pos);
                World.sendGraphics(1328, 0, delay, pos);
            }
            Bounds npcBounds = npc.getBounds();
            if (players.stream().anyMatch(p -> p.getPosition().inBounds(npcBounds) && p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC))) {
                damage /= 2;
            }
            event.delay(2);
            int finalDamage = damage;
            players.stream().filter(p -> Misc.getEffectiveDistance(npc, p) <= 1).forEach(p -> {
                p.hit(new Hit(npc).fixedDamage(finalDamage));
            });
            npc.unlock();
        });
    }

    @Override
    public void follow() {
        //never follow
    }

    @Override
    public boolean attack() {
        if (!withinDistance(16))
            return false;
        if (npc.getId() == 7565) { // initial form
             teleportAttack();
             return true;
        }
        if (!npc.isLocked() && channeledCrystal == null) { // idle
            if (charges >= 66)
                teleportAttack();
            else
                channel();
            return true;
        }
        return true;
    }

    private void channel() {
        npc.addEvent(event -> {
            event.setCancelCondition(() -> {
                if (isDead() || target == null) {
                    channeledCrystal = null;
                    setVasaVulnerable(true);
                    npc.transform(7566);
                    return true;
                }
                return false;
            });
            npc.lock();
            channeledCrystal = Random.get(crystals.stream().filter(n -> !n.getCombat().isDead()).collect(Collectors.toList()));
            if (channeledCrystal == null)
                return;
            npc.getRouteFinder().routeEntity(channeledCrystal);
            int ticks = -2;
            while (!npc.getMovement().isAtDestination()) {
                if (ticks++ % 3 == 0)
                    rocks();
                event.delay(1);
            }
            npc.animate(7412);
            npc.transform(7567);
            npc.face(channeledCrystal);
            setCrystalVulnerable(channeledCrystal, true);
            setVasaVulnerable(false);
            while (!channeledCrystal.getCombat().isDead() && charges++ < 66) {
                npc.incrementHp(npc.getMaxHp() / 200);
                event.delay(1);
            }
            event.delay(1);
            setCrystalVulnerable(channeledCrystal, false);
            setVasaVulnerable(true);
            npc.animate(7414);
            npc.transform(7566);
            npc.faceNone(false);
            channeledCrystal = null;
            event.delay(2);
            npc.unlock();
        });
    }

    private void rocks() {
        npc.localPlayers().forEach(p -> {
            if (p.getCombat().isDead() || Misc.getEffectiveDistance(npc, p) >= 10 || !ProjectileRoute.allow(npc, p))
                return;
            int delay = ROCK_PROJECTILE.send(npc, p.getPosition());
            Position targetPos = p.getPosition().copy();
            World.sendGraphics(1330, 0, delay, targetPos);
            p.addEvent(event -> {
                event.delay(2);
                if (p.getPosition().isWithinDistance(targetPos, 1)) {
                    int maxDamage = getMaxDamage();
                    if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES))
                        maxDamage /= 2;
                    p.hit(new Hit(npc).randDamage(maxDamage).ignorePrayer().ignoreDefence());
                }
            });
        });
    }

    @Override
    public void faceTarget() {
        //no facing
    }

    private GameObject getCrystalObject(NPC crystalNPC) {
        return Tile.getObject(-1, crystalNPC.getAbsX(), crystalNPC.getAbsY(), crystalNPC.getHeight(), 10, -1);
    }

    private void setCrystalVulnerable(NPC npc, boolean vulnerable) {
        if (!vulnerable) {
            getCrystalObject(npc).setId(29775);
            npc.attackNpcListener = (player, npc1, message) -> {
                if (message)
                    player.sendMessage("This crystal is currently invulnerable!");
                return false;
            };
        } else {
            npc.attackNpcListener = null;
            getCrystalObject(npc).setId(29774);
        }
    }

    private void setVasaVulnerable(boolean vulnerable) {
        if (!vulnerable) {
            npc.attackNpcListener = (player, npc1, message) -> {
                if (message)
                    player.sendMessage("Vasa Nistirio is invulnerable to attacks while channeling power from the crystal!");
                return false;
            };
        } else {
            npc.attackNpcListener = null;
        }
    }
}
