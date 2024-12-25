package io.ruin.model.activities.wilderness.bosses.magearena;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;
import io.ruin.utility.TickDelay;

public class JusticiarZachariah extends NPCCombat {

    private static final Projectile SPECIAL_PROJECTILE = new Projectile(1515, 45, 31, 25, 80, 0, 16, 32);

    private TickDelay specialCooldown = new TickDelay();

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend);
    }

    private void preDefend(Hit hit) {
        if (hit.attackStyle != null && !hit.attackStyle.isMagic()) {
            hit.block();
            if (hit.attacker != null && hit.attacker.player != null)
                hit.attacker.player.sendMessage("Justiciar Zachariah's magic protects him against your attacks. He is only vulnerable to magic spells!");
        }
    }

    @Override
    public void follow() {
        follow(10);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        if (withinDistance(1) && Random.rollDie(2, 1)) {
            basicAttack();
            return true;
        }
        if (!withinDistance(1) && !specialCooldown.isDelayed() && Random.rollDie(3, 1)) {
            specialAttack();
        } else {
            magicAttack();
        }
        return true;
    }

    private void magicAttack() {
        npc.animate(7965);
        target.graphics(76, 128, 20);
        boolean prayer = target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(prayer ? 21 : 43).delay(1);
        hit.postDamage(entity -> {
            if (entity.player != null && hit.damage > 0 && Random.rollDie(5, 1)) {
                Stat magic = entity.player.getStats().get(StatType.Magic);
                int minLevel = (int) (magic.fixedLevel * 0.95);
                int drain = magic.currentLevel - minLevel;
                if (drain > 0) {
                    magic.drain(drain);
                    entity.player.sendMessage("You feel your magical powers weaken.");
                }
            }
        });
        target.hit(hit);

    }

    private void specialAttack() {
        npc.animate(7963);
        Position targetPosition = target.getPosition().copy();
        SPECIAL_PROJECTILE.send(npc, targetPosition);
        npc.addEvent(event -> {
            event.delay(2);
            if (target == null)
                return;
            if (target.getPosition().equals(targetPosition)) {
                Position pos = Misc.getClosestPosition(npc, target);
                Direction dir = Direction.getDirection(pos, targetPosition);
                Direction dir2 = Direction.getDirection(targetPosition, pos);
                pos.translate(dir.deltaX, dir.deltaY, 0);
                target.addEvent(e -> {
                    target.lock();
                    target.animate(1157);
                    target.player.getMovement().force(pos.getX() - target.getPosition().getX(), pos.getY() - target.getPosition().getY(), 0, 0, 10, 60, dir2);
                    target.forceText("Noooo!");
                    e.delay(2);
                    target.unlock();
                    target.root(4, true);
                });
            }
        });
        specialCooldown.delay(20);
    }
}
