package io.ruin.model.activities.fightcaves.monsters;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class TokXil extends NPCCombat {

    private static final Projectile RANGED_PROJECTILE = new Projectile(443, 64, 31, 32, 40, 8, 16, 32).regionBased();

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(16);
    }

    @Override
    public boolean attack() {
        if(withinDistance(1)) {
            if(Random.rollDie(3)) {
                basicAttack(info.attack_animation, info.attack_style, info.max_damage);
                return true;
            }
        } else if(!withinDistance(16)) {
            /**
             * Not in ranged distance
             */
            return false;
        }
        projectileAttack(RANGED_PROJECTILE, 2633, AttackStyle.RANGED, info.max_damage);
        return true;
    }

}