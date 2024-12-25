package io.ruin.model.activities.miscpvm.dragons;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Projectile;

public class MithrilDragon extends MetalDragon {

    protected static final Projectile MAGIC_PROJECTILE = new Projectile(136, 10, 31, 40, 56, 10, 16, 127);
    protected static final Projectile RANGED_PROJECTILE = new Projectile(16, 10, 31, 40, 56, 10, 16, 127);

    @Override
    public void init() {
        npc.dropListener = (killer, item) -> {
            if(killer != null && item.getId() == 2359 && Config.UNLOCK_DULY_NOTED.get(killer.player) == 1)
                item.setId(2360); //note mithril bars
        };
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (withinDistance(1) && Random.rollDie(5, 4))
            basicAttack();
        else if (!fire && Random.rollDie(2, 1))
            meleeDragonfire();
        else if (Random.rollDie(2, 1))
            magicAttack();
        else
            rangedAttack();
        return true;
    }

    private void rangedAttack() {
        projectileAttack(RANGED_PROJECTILE, 6722, AttackStyle.RANGED, 18);
    }

    private void magicAttack() {
        projectileAttack(MAGIC_PROJECTILE, 6722, AttackStyle.MAGIC, 18);
    }

}
