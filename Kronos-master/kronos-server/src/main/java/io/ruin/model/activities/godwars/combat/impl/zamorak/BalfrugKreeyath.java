package io.ruin.model.activities.godwars.combat.impl.zamorak;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class BalfrugKreeyath extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(1227, 22, 31, 30, 51, 5, 16, 64);

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
        npc.graphics(1226, 30, 0);
        npc.animate(info.attack_animation);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC)
                .randDamage(info.max_damage)
                .clientDelay(delay);
        hit.postDamage(t-> {
            if(hit.damage > 0) {
                t.graphics(157, 124, 0);
            } else {
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        target.hit(hit);
        return true;
    }
}
