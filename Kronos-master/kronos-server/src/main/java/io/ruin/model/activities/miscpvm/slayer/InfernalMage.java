package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class InfernalMage extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(130, 43, 31, 51, 56, 10, 16, 64);


    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        npc.animate(info.attack_animation);
        npc.graphics(129, 92, 0);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC)
                .randDamage(info.max_damage)
                .clientDelay(delay);
        hit.postDamage(t -> {
           if(hit.damage > 0) {
               t.graphics(131, 124, delay);
           } else {
               t.graphics(85, 124, delay);
               hit.hide();
           }
        });
        target.hit(hit);
        return true;
    }
}
