package io.ruin.model.activities.barrows.brothers;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;

public class Guthan extends NPCCombat {

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
        npc.animate(info.attack_animation);
        Hit hit = new Hit(npc, info.attack_style).randDamage(info.max_damage);
        hit.postDamage(t -> {
            if(hit.damage > 0 && Random.rollDie(4, 1)) {
                t.graphics(398);
                npc.incrementHp(hit.damage);
            }
        });
        target.hit(hit);
        return true;
    }

}