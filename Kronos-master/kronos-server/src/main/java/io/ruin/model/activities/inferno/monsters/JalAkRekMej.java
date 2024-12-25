package io.ruin.model.activities.inferno.monsters;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

public class JalAkRekMej extends NPCCombat {  // magic bloblet

    private static final Projectile PROJECTILE = new Projectile(1381, 0, 31, 10, 40, 8, 16, 0);

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
        projectileAttack(PROJECTILE, 7581, AttackStyle.MAGIC, info.max_damage);
        return true;
    }
}
