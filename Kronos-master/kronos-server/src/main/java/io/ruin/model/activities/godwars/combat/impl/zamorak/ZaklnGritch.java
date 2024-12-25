package io.ruin.model.activities.godwars.combat.impl.zamorak;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class ZaklnGritch extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(1223, 30, 36, 30, 46, 5, 15, 64);

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
        npc.graphics(1222);
        projectileAttack(PROJECTILE, info.attack_animation, AttackStyle.RANGED, info.max_damage);
        return true;
    }
}
