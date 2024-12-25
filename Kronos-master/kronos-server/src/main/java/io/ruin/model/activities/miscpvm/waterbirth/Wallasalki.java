package io.ruin.model.activities.miscpvm.waterbirth;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class Wallasalki extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(136, 30, 31, 15, 56, 10, 15, 32);

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
        Hit hit = projectileAttack(PROJECTILE, info.attack_animation, AttackStyle.MAGIC, info.max_damage);
        hit.postDamage(entity -> {
            if (hit.damage > 0)
                entity.graphics(137, 124, 0);
            else {
                entity.graphics(85, 124, 0);
                hit.hide();
            }
        });
        return true;
    }
}
