package io.ruin.model.activities.miscpvm.dragons;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.map.Projectile;

public class BrutalDragon extends MetalDragon {

    private static final Projectile WATER_PROJECTILE = new Projectile(133, 10, 31, 60, 56, 10, 16, 127);
    private static final Projectile WIND_PROJECTILE = new Projectile(136, 10, 31, 60, 56, 10, 16, 127);
    private static final Projectile FIRE_PROJECTILE = new Projectile(130, 10, 31, 60, 56, 10, 16, 127);

    private Projectile projectile;

    @Override
    public void init() {
        super.init();
        switch (npc.getId()) {
            case 2918: // green
                projectile = WATER_PROJECTILE;
                break;
            case 7273: // blue
                projectile = WIND_PROJECTILE;
                break;
            case 7274: // red
            case 7275: // black
                projectile = FIRE_PROJECTILE;
                break;
            default:
                System.err.println("Assigned brutal dragon script with no projectile, npc id " + npc.getId());
                break;
        }
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (withinDistance(1) && Random.rollDie(5, 4))
            basicAttack();
        else if (!fire && Random.rollDie(2, 1))
            meleeDragonfire();
        else
            magicAttack();
        return true;
    }

    private void magicAttack() {
        fire = false;
        projectileAttack(projectile, 6722, AttackStyle.MAGIC, info.max_damage);
    }
}
