package io.ruin.model.activities.barrows.brothers;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.StatType;

public class Karil extends NPCCombat {

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(8))
            return false;
        projectileAttack(Projectile.BOLT, info.attack_animation, AttackStyle.RANGED, info.max_damage);
        if(Random.rollDie(4)) {
            target.graphics(401, 100, 0);
            target.player.getStats().get(StatType.Magic).drain(5);
        }
        return true;
    }

}