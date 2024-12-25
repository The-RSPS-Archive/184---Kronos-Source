package io.ruin.model.activities.godwars.combat.impl.bandos;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class SergeantSteelwill extends NPCCombat {
    private static final Projectile PROJECTILE = new Projectile(1217, 22, 31, 30, 56, 10, 16, 64);

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(8))
            return false;
        npc.graphics(1216, 30, 0);
        npc.animate(info.attack_animation);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC, null)
                .randDamage(35)
                .clientDelay(delay);
        hit.postDamage(t -> {
            if(hit.damage > 0) {
                t.graphics(166, 124, 0);
            } else {
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        target.hit(hit);
        return true;
    }
}
