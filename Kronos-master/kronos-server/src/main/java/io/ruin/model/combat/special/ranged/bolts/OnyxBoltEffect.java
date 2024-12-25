package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;

import java.util.function.BiFunction;

public class OnyxBoltEffect implements BiFunction<Entity, Hit, Boolean> {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(!Random.rollPercent(target.player != null ? 10 : 11))
            return false;
        int damage = target.hit(hit.boostDamage(0.20));
        int heal = (int) (damage * 0.25);
        if(heal > 0) {
            target.graphics(753);
            hit.attacker.incrementHp(heal);
        }
        return true;
    }

}