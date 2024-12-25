package io.ruin.model.activities.miscpvm;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class Lizardman extends NPCCombat {

    private static final Projectile RANGED_PROJECTILE = new Projectile(1291, 47, 36, 61, 71, 5, 5, 20);

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if(withinDistance(1)) {
            if(Random.rollDie(3)) {
                basicAttack(info.attack_animation, info.attack_style, info.max_damage);
                return true;
            }
        } else if(!withinDistance(8)) {
            /**
             * Not in ranged distance
             */
            return false;
        }
        projectileAttack(RANGED_PROJECTILE, 7193, AttackStyle.RANGED, info.max_damage);
        return true;
    }

}