package io.ruin.model.activities.bosses.dagannothkings;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;

public class DagannothPrime extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(162, 65, 31, 15, 56, 10, 15, 64);

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(12);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(12))
            return false;
        npc.animate(info.attack_animation);
        int delay = PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay));
        target.graphics(477, 100, delay);
        final int ticks = (delay * 25) / 600;
        npc.addEvent(event -> {
            event.delay(ticks);
            if (target == null)
                return;
            target.localPlayers().forEach(p -> {
                if (!canAttack(p) || Misc.getDistance(target.getPosition(), p.getPosition()) > 1) {
                    return;
                }
                p.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage));
                p.graphics(477, 100, 0);
            });
        });
        return true;
    }


    @Override
    public int getAggressionRange() {
        return 8;
    }

    @Override
    public int getAttackBoundsRange() {
        return 20;
    }
}
