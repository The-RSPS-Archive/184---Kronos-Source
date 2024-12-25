package io.ruin.model.activities.inferno.monsters;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.StatType;

//Ranger (85)
public class JalMejRah extends NPCCombat {

    public static final Projectile PROJECTILE = new Projectile(1382, 50, 31, 10, 40, 8, 16, 128);

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(4);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(4)) {
            /**
             * Not in ranged distance
             */
            return false;
        }
        projectileAttack(PROJECTILE, 7578, AttackStyle.RANGED, info.max_damage);
        if (target.player != null) {
            target.player.getMovement().drainEnergy(3);
            if(Random.rollDie(4, 1)) { // 25% chance of draining combat stats
                target.player.getStats().get(StatType.Attack).drain(1);
                target.player.getStats().get(StatType.Strength).drain(1);
                target.player.getStats().get(StatType.Defence).drain(1);
                target.player.getStats().get(StatType.Ranged).drain(1);
                target.player.getStats().get(StatType.Magic).drain(1);
            }
        }
        return true;
    }

}