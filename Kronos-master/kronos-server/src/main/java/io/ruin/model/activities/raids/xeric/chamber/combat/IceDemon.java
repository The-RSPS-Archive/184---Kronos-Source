package io.ruin.model.activities.raids.xeric.chamber.combat;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;

public class IceDemon extends NPCCombat {

    private static final Projectile RANGED_PROJECTILE = new Projectile(1324, 60, 0, 50, 65, 0, 16,127);
    private static final Projectile MAGIC_PROJECTILE = new Projectile(366, 60, 0, 51, 65, 0, 16, 127);
    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        if (target.player != null) {
            if (target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) {
                rangedAttack();
                return true;
            } else if (target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
                magicAttack();
                return true;
            }
        }
        if (Random.rollDie(2, 1))
            magicAttack();
        else
            rangedAttack();
        return true;
    }

    private void rangedAttack() {
        npc.animate(info.attack_animation);
        Position targetPosition = target.getPosition().copy();
        int delay = RANGED_PROJECTILE.send(npc, targetPosition.getX(), targetPosition.getY());
        World.sendGraphics(1325, 0, delay, targetPosition);
        npc.addEvent(event -> {
            event.delay(delay * 25 / 600 - 1);
            npc.localPlayers().forEach(p -> {
                if (canAttack(p) && p.getPosition().equals(targetPosition)) {
                    int maxDamage = 40;
                    if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) {
                        maxDamage /= 2;
                    }
                    p.hit(new Hit(npc, AttackStyle.RANGED).randDamage(maxDamage).ignorePrayer());
                }
            });
        });
    }

    private void magicAttack() {
        npc.animate(info.attack_animation);
        Position targetPosition = target.getPosition().copy();
        int delay = MAGIC_PROJECTILE.send(npc, targetPosition.getX(), targetPosition.getY());
        targetPosition.area(1).forEach(pos -> World.sendGraphics(363, 0, delay, pos));
        npc.addEvent(event -> {
            event.delay(delay * 25 / 600 - 1);
            npc.localPlayers().forEach(p -> {
                if (canAttack(p) && p.getPosition().isWithinDistance(targetPosition, 1)) {
                    int maxDamage = 40;
                    if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) {
                        maxDamage /= 2;
                    }
                    p.hit(new Hit(npc, AttackStyle.RANGED).randDamage(maxDamage).ignorePrayer());
                    p.freeze(3, npc);
                }
            });
        });
    }
}
