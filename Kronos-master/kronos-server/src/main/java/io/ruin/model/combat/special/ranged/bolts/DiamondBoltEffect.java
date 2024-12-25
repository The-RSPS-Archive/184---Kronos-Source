package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;

import java.util.function.BiFunction;

public class DiamondBoltEffect implements BiFunction<Entity, Hit, Boolean> {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(!Random.rollPercent(target.player != null ? 5 : 10))
            return false;
        target.graphics(758);
        target.hit(hit.boostDamage(0.15).ignoreDefence());
        return true;
    }

}