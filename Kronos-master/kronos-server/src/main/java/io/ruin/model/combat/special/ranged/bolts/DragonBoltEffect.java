package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;

import java.util.function.BiFunction;

public class DragonBoltEffect implements BiFunction<Entity, Hit, Boolean> {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(!Random.rollPercent(6))
            return false;
        if(target.getCombat().getDragonfireResistance() > 0.3)
            return false;
        int damage = target.hit(hit.boostDamage(0.45));
        if(damage > 0)
            target.graphics(756);
        return true;
    }

}