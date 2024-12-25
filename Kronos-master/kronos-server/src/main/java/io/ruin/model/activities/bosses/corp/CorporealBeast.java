package io.ruin.model.activities.bosses.corp;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.Tile;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.process.event.Event;
import io.ruin.utility.Misc;
import kilim.Pausable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CorporealBeast extends NPCCombat {

    private static final Projectile BASIC_PROJECTILE = new Projectile(314, 65, 31, 15, 56, 10, 15, 191);
    private static final Projectile SPLIT_START_PROJECTILE = new Projectile(315, 65, 31, 15, 56, 10, 15, 191);
    private static final Projectile SPLIT_PROJECTILE = new Projectile(315, 0, 0, 0, 60, 0, 8, 0);
    private static final Projectile POWERED_PROJECTILE = new Projectile(316, 65, 31, 15, 56, 10, 15, 191);
    private static final Projectile CORE_PROJECTILE = new Projectile(319, 0, 0, 0, 60, 0, 15, 0);

    private NPC core;

    @Override
    public void init() {
        npc.hitListener = new HitListener()
                .postDefend(this::postDefend)
                .postDamage(this::afterDamaged);
        npc.deathStartListener = (entity, killer, killHit) -> {
            if (core != null && !core.isRemoved())
                core.remove();
            for(Player player : npc.localPlayers())
                Config.CORPOREAL_BEAST_DAMAGE.set(player, 0);
        };
        npc.addEvent(event -> {
            while (true) {
                if (!npc.getCombat().isDead() && !npc.isHidden() && !npc.isRemoved()
                        && (npc.localPlayers().isEmpty() || npc.localPlayers().stream().noneMatch(p -> ProjectileRoute.allow(npc, p)))) {// no players in sight, reset
                    if (core != null)
                        core.remove();
                    restore();
                }
                event.delay(3);
            }
        });
    }

    private void sendCore() {
        core = new NPC(320).spawn(npc.getAbsX() + 1, npc.getAbsY() + 1, npc.getHeight());
        core.deathEndListener = (entity, killer, killHit) -> entity.npc.remove();
        core.addEvent(event -> {
            while (!core.isRemoved()) {
                moveCore(event);
                while (coreDrain())
                    event.delay(2);
                event.delay(1);
            }
        });
    }

    private boolean coreDrain() {
        if (core == null || core.isRemoved() || core.getCombat().isDead())
            return false;
        int damage = 0;
        for (Player player : npc.localPlayers()) {
            if (canAttack(player) && Misc.getDistance(player.getPosition(), core.getPosition()) <= 1 && ProjectileRoute.allow(npc, core)) {
                if (core.isPoisoned())
                    return true;
                damage += player.hit(new Hit().randDamage(12).ignorePrayer().ignoreDefence());
                player.sendMessage("The dark energy core drains life from you and transfers it to its master!");
            }
        }
        npc.incrementHp(damage);
        return damage > 0;
    }

    private void moveCore(Event event) throws Pausable {
        if (core == null || core.isRemoved())
            return;
        List<Player> targets = npc.localPlayers().stream().filter(p -> ProjectileRoute.allow(core, p)).collect(Collectors.toList());
        if (targets.size() == 0)
            return;
        Player player = Random.get(targets);
        List<Position> positions = new ArrayList<>(9);
        int radius = 1;
        for (int x = player.getAbsX() - radius; x <= player.getAbsX() + radius; x++) {
            for (int y = player.getAbsY() - radius; y <= player.getAbsY() + radius; y++) {
                Tile tile = Tile.get(x, y, npc.getHeight(), false);
                if (tile == null || tile.clipping == 0) {
                    positions.add(new Position(x, y, npc.getHeight()));
                }
            }
        }
        if (positions.size() == 0)
            return;
        Position targetPos = Random.get(positions);
        core.setHidden(true);
        CORE_PROJECTILE.send(core.getAbsX(), core.getAbsY(), targetPos.getX(), targetPos.getY());
        core.getMovement().teleport(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        event.delay(2);
        if (!core.getCombat().isDead() && !core.isRemoved()) {
            core.setHidden(false);
            core.face(player);
        }
    }

    private void postDefend(Hit hit) {
        if (hit.attacker != null && hit.attacker.player != null && hit.attackStyle != null) {
            Item weapon = hit.attacker.player.getEquipment().get(Equipment.SLOT_WEAPON);
            if (weapon == null || !usingSpear(hit.attacker.player)) {
                hit.damage *= 0.5;
                hit.attacker.player.sendMessage("The Corporeal Beast resists your weapon.");
            }
        }
        if (hit.damage > 100 && !hit.isBlocked()) // dmg cap
            hit.damage = 100;
        if (hit.attacker != null && hit.attacker.player != null && hit.damage > 0) {
            int damage = Math.min(2047, Config.CORPOREAL_BEAST_DAMAGE.get(hit.attacker.player) + hit.damage);
            Config.CORPOREAL_BEAST_DAMAGE.set(hit.attacker.player, damage);
        }
    }

    private boolean usingSpear(Player player) {
        return Config.WEAPON_TYPE.get(player) == 24 || Config.WEAPON_TYPE.get(player) == 15;
    }

    private void afterDamaged(Hit hit) {
        if(isDead()) //corp died, gg
            return;
        if ((core == null || core.isRemoved()) && hit.damage >= 32 && Random.rollDie(8, 1)) { // "When it takes a hit that deals 32 or more damage, there is a 1/8 chance of a Dark energy core appearing in its place"
            sendCore();
        }
        if (hit.attacker != null && target != hit.attacker && hit.damage >= 15 && Random.rollDie(5, 1)) {
            setTarget(hit.attacker);
            npc.face(hit.attacker);
        }
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (Random.rollDie(10, 6)) {
            if (withinDistance(1) && Random.rollDie(10, 6)) {
                basicAttack();
            } else {
                fireBasic();
            }
        } else if (Random.rollDie(3, 2)) {
            firePowered();
        } else {
            fireSplit();
        }
        return true;
    }

    private void fireBasic() {
        projectileAttack(BASIC_PROJECTILE, 1680, AttackStyle.MAGIC, 65);
    }

    private void firePowered() {
        npc.animate(1681);
        int duration = POWERED_PROJECTILE.send(npc, target);
        if (target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC))
            target.hit(new Hit(npc, AttackStyle.MAGIC, null).randDamage(10, 35).ignoreDefence().ignorePrayer().clientDelay(duration));
        else
            target.hit(new Hit(npc, AttackStyle.MAGIC, null).randDamage(30, 65).ignoreDefence().clientDelay(duration));
    }

    private void fireSplit() {
        npc.animate(1681);
        int duration = SPLIT_START_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC, null).randDamage(42).clientDelay(duration));
        final Entity t = target;
        npc.addEvent(event -> {
            event.delay(((duration * 25) / 600) - 1);
            Position[] targets = new Position[5];
            Position src = t.getPosition();
            for (int i = 0; i < targets.length; i++) {
                targets[i] = new Position(src.getX() + Random.get(-2, 2), src.getY() + Random.get(-2, 2), src.getZ());
                SPLIT_PROJECTILE.send(src.getX(), src.getY(), targets[i].getX(), targets[i].getY());
            }
            event.delay(2);
            for (Position pos : targets) {
                World.sendGraphics(317, 0, 0, pos.getX(), pos.getY(), pos.getZ());
            }
            for (Player player : npc.localPlayers()) {
                if (!canAttack(player))
                    continue;
                for (Position pos : targets) {
                    if (player.isAt(pos.getX(), pos.getY())) {
                        player.hit(new Hit(npc, AttackStyle.MAGIC, null).randDamage(15, 35).ignoreDefence().ignorePrayer());
                    }
                }
            }
        });
    }

    @Override
    public int getAttackBoundsRange() {
        return 20;
    }
}
