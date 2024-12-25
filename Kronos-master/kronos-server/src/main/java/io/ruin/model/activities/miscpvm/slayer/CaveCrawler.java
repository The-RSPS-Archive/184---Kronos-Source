package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCCombat;

public class CaveCrawler extends NPCCombat {


    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        basicAttack();
        npc.incrementHp(1);
        if (Random.rollDie(4, 1))
            target.poison(3);
        return true;
    }
}
