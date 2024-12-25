package io.ruin.model.activities.miscpvm.waterbirth;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class RangedDagannoth extends NPCCombat {


    private static final Projectile PROJECTILE = new Projectile(294, 25, 31, 15, 56, 10, 15, 0);

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
        projectileAttack(PROJECTILE, info.attack_animation, AttackStyle.RANGED, info.max_damage);
        return true;
    }
}
