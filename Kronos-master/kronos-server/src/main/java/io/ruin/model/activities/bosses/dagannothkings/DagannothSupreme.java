package io.ruin.model.activities.bosses.dagannothkings;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;

public class DagannothSupreme extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(475, 65, 31, 15, 56, 10, 15, 64);

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
        target.hit(new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay));
        for (Player p : target.localPlayers()) {
            if (!canAttack(p) || Misc.getDistance(npc.getPosition(), p.getPosition()) > 6)
                continue;
            delay = PROJECTILE.send(npc, p);
            p.hit(new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay));
        }
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
