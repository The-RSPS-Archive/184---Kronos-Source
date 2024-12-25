package io.ruin.model.activities.miscpvm;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class SaradominBattlemage extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(64, 0);

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
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC)
                .randDamage(info.max_damage)
                .clientDelay(delay);
        hit.postDamage(t -> {
            if(hit.damage > 0) {
                t.graphics(76, 128, 0);
            } else {
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        target.hit(hit);
        return true;
    }

}
