package io.ruin.model.activities.barrows.brothers;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCCombat;

public class Torag extends NPCCombat {

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(1))
            return false;
        basicAttack();
        if(Random.rollDie(4)) {
            target.graphics(399, 0, 0);
            target.player.getMovement().drainEnergy(20);
        }
        return true;
    }

}