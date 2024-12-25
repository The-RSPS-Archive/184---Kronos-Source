package io.ruin.model.activities.barrows.brothers;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCCombat;

public class Verac extends NPCCombat {

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
        if(Random.rollDie(4))
            basicAttack(info.attack_animation, info.attack_style, 15).ignorePrayer();
        else
            basicAttack();
        return true;
    }

}