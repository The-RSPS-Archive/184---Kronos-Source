package io.ruin.model.activities.raids.xeric.chamber.combat;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tekton extends NPCCombat {

    private List<Position> bubbles = new ArrayList<>(20);
    private static final Projectile LAVA_PROJECTILE = new Projectile(660, 0, 0, 0, 100, 0, 45, 0);
    private boolean forcedSmith = false;
    @Override
    public void init() {
        int baseX = ((npc.getSpawnPosition().getX() >> 3) & (~3)) << 3; // base X of the chamber
        int baseY = ((npc.getSpawnPosition().getY() >> 3) & (~3)) << 3;
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                GameObject obj = Tile.getObject(29890, baseX + x, baseY + y, npc.getHeight(), 10, -1);
                if (obj != null) {
                    bubbles.add(new Position(baseX + x, baseY + y, npc.getHeight()));
                }
            }
        }
        npc.attackNpcListener = (player, npc1, message) -> false;
        npc.addEvent(event -> {
            npc.lock();
            while (npc.localPlayers().size() == 0) {
                event.delay(5);
            }
            npc.animate(7474);
            event.delay(2);
            npc.transform(7541);
            while (npc.localPlayers().size() < 1) event.delay(2);
            Player p = Random.get(npc.localPlayers());
            npc.face(p);
            npc.getRouteFinder().routeEntity(p);
            event.waitForMovement(npc);
            event.delay(1);
            npc.animate(7478);
            npc.transform(7542);
            event.delay(1);
            target = p;
            npc.attackNpcListener = null;
            npc.faceNone(false);
            npc.unlock();
        });
        npc.hitListener = new HitListener().postDamage(hit -> {
           if (npc.getHp() < npc.getMaxHp() / 2 && !forcedSmith && Random.rollDie(20, 2)) {
               forcedSmith = true;
               smith();
           }
        });
    }

    @Override
    public void follow() {
        //no follow
    }

    @Override
    public boolean attack() {
        List<Player> targets = npc.localPlayers().stream().filter(p -> {
            Position pos = Misc.getClosestPosition(npc, p);
            int xDist = Math.abs(pos.getX() - p.getAbsX());
            int yDist = Math.abs(pos.getY() - p.getAbsY());
            int dist = Math.max(xDist, yDist);
            return dist <= 3;
        }).collect(Collectors.toList());
        if (targets.size() == 0 || npc.localPlayers().size() == 0 || Random.rollPercent(5)) {
            smith();
            return false;
        }
        Player p = Random.get(targets);
        Direction side;
        Position pos = Misc.getClosestPosition(npc, p);
        int xDiff = pos.getX() - p.getAbsX();
        int yDiff = pos.getY() - p.getAbsY();
        if (xDiff > 0)
            side = Direction.WEST;
        else if (xDiff < 0)
            side = Direction.EAST;
        else if (yDiff > 0)
            side = Direction.SOUTH;
        else
            side = Direction.NORTH;
        npc.face(side);
        npc.animate(7483);
        npc.addEvent(event -> {
            event.delay(1);
            if (isDead())
                return;
            for (Player player : npc.localPlayers()) {
                Position src = Misc.getClosestPosition(npc, player);
                if ((side.deltaX != 0 && player.getAbsX() - src.getX() == side.deltaX) ||
                        (side.deltaY != 0 && player.getAbsY() - src.getY() == side.deltaY)) {
                    int maxDamage = 35;
                    if (player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE))
                        maxDamage /= 2;
                    player.hit(new Hit(npc, AttackStyle.CRUSH).randDamage(1, maxDamage).ignorePrayer().delay(0));
                }
            }
        });
        return true;
    }

    private void smith() {
        npc.addEvent(event -> {
            event.setCancelCondition(this::isDead);
            npc.lock();
            npc.localPlayers().forEach(plr -> plr.getCombat().reset());
            npc.attackNpcListener = (player, npc1, message) -> false;
            npc.hitListener = new HitListener().preDamage(hit -> hit.damage = 0);
            npc.animate(7479);
            npc.transform(7541);
            npc.faceNone(false);
            event.delay(2);
            npc.getRouteFinder().routeAbsolute(npc.getSpawnPosition().getX(), npc.getSpawnPosition().getY());
            event.waitForMovement(npc);
            npc.face(npc.spawnDirection);
            event.delay(1);
            npc.transform(7545);
            npc.animate(7475);
            for (int i = 0; i < 6; i++) {
                shootLava();
                event.delay(3);
                npc.incrementHp(Math.max((int) (npc.getMaxHp() * 0.005), 1));
            }
            npc.animate(7474);
            event.delay(2);
            npc.transform(7541);
            while (npc.localPlayers().size() < 1) event.delay(2);
            Player p = Random.get(npc.localPlayers());
            npc.face(p);
            npc.getRouteFinder().routeEntity(p);
            event.waitForMovement(npc);
            event.delay(1);
            npc.animate(7478);
            npc.transform(7542);
            event.delay(1);
            target = p;
            npc.attackNpcListener = null;
            npc.hitListener = null;
            npc.faceNone(false);
            npc.unlock();
        });
    }

    @Override
    public boolean isAggressive() {
        return npc.getId() != 7540 && npc.getId() != 7545 && !npc.isLocked();
    }

    private void shootLava() {
        for (Player p : npc.localPlayers()) {
            for (int i = 0; i < 2; i ++) {
                Position source = Random.get(bubbles);
                World.startEvent(event -> {
                    Position pos = p.getPosition().copy();
                    int delay = LAVA_PROJECTILE.send(source.getX(), source.getY(), pos.getX(), pos.getY());
                    World.sendGraphics(659, 0, delay, pos);
                    event.delay((delay * 25) / 600);
                    if (isDead() || npc.isRemoved() || target == null)
                        return;
                    int distance = Misc.getDistance(p.getPosition(), pos);
                    if (distance <= 1)
                        p.hit(new Hit().randDamage(1, distance == 1 ? 12 : 20).delay(0));
                });
            }
        }
    }

    @Override
    public int getRandomDropCount() {
        return 2 + (ChambersOfXeric.getPartySize(npc) / 2);
    }
}
