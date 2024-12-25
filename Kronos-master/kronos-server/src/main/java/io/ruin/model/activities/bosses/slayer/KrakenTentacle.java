package io.ruin.model.activities.bosses.slayer;

import io.ruin.cache.NPCDef;
import io.ruin.model.activities.miscpvm.slayer.CaveKraken;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;

public class KrakenTentacle extends CaveKraken {

    private static final Projectile PROJECTILE = new Projectile(162, 60, 31, 25, 56, 10, 15, 0);

    static {
        NPCDef def = NPCDef.get(5534);
        def.attackOption = def.getOption("disturb");
        def.swimClipping = true;
        def = NPCDef.get(5535);
        def.swimClipping = true;
    }

    @Override
    public boolean allowRespawn() {
        return false;
    }

    @Override
    protected int getWhirlpoolId() {
        return 5534;
    }

    @Override
    protected int getSurfaceId() {
        return 5535;
    }

    @Override
    protected int getSurfacingAnimation() {
        return 3860;
    }

    @Override
    protected Projectile getProjectile() {
        return PROJECTILE;
    }

    @Override
    protected void preTargetDefend(Hit hit, Entity entity) {
        super.preTargetDefend(hit, entity);
        hit.ignorePrayer();
    }


}
