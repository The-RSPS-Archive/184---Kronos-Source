package io.ruin.model.activities.miscpvm;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCCombat;

public class PoisonousKalphite extends NPCCombat {


    private int poisonDamage;

    @Override
    public void init() {
        poisonDamage = npc.getDef().combatLevel > 85 ? 6 : 4;
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        if (Random.rollDie(4, 1)) {
            target.poison(poisonDamage);
        }
        basicAttack();
        return true;
    }
}
