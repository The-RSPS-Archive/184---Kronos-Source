package io.ruin.model.activities.godwars.combat.impl.bandos;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class SergeantGrimspike extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(1220, 30, 36, 30, 51, 5, 15, 64);

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
