package io.ruin.model.activities.inferno.monsters;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class JalAkRekXil extends NPCCombat {  // ranged bloblet

    private static final Projectile PROJECTILE = new Projectile(1379, 0, 31, 10, 40, 8, 16, 0);

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(10);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        projectileAttack(PROJECTILE, 7581, AttackStyle.RANGED, info.max_damage);
        return true;
    }
}
