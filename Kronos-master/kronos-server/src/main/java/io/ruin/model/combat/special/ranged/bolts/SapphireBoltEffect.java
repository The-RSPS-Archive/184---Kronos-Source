package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

import java.util.function.BiFunction;

public class SapphireBoltEffect implements BiFunction<Entity, Hit, Boolean> {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(target.player == null || !Random.rollPercent(5))
            return false;
        Stat prayer = hit.attacker.player.getStats().get(StatType.Prayer);
        int drain = prayer.currentLevel / 20;
        if(drain == 0)
            return false;
        prayer.boost(drain / 2, 0.0);
        target.player.getPrayer().drain(drain);
        target.graphics(751);
        target.hit(hit);
        return true;
    }

}