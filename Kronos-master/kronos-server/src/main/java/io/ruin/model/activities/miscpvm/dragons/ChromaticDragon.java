package io.ruin.model.activities.miscpvm.dragons;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;

public class ChromaticDragon extends NPCCombat {
    boolean fire;

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
        if (!fire && Random.rollDie(6, 1)) { // don't do dragon fire twice in a row
            meleeDragonfire();
        } else {
            basicAttack();
        }
        return true;
    }

    void meleeDragonfire() {
        fire = true;
        npc.animate(81);
        npc.graphics(1, 100, 0);
        target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(50));
    }

    @Override
    public Hit basicAttack() {
        fire = false;
        return super.basicAttack();
    }

    @Override
    public double getDragonfireResistance() {
        return 0.9;
    }
}
