package io.ruin.model.combat.special.ranged.bolts;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;

import java.util.function.BiFunction;

public class RubyBoltEffect implements BiFunction<Entity, Hit, Boolean> {

    @Override
    public Boolean apply(Entity target, Hit hit) {
        if(!Random.rollPercent(target.player != null ? 11 : 6))
            return false;
        Entity attacker = hit.attacker;
        int sacrificeDamage = (int) (attacker.getHp() * 0.10);
        int specialDamage;
        // Quick little fix to prevent abuse for HP events (where admins set their hitpoints to 10k and players hit 1,500 dmg)
        if(target.player != null)
            specialDamage = (int) ((target.getHp() > 99 ? 99 : target.getHp()) * 0.20);
        else
            specialDamage = (int) ((target.getHp()) * 0.20);
        if(sacrificeDamage <= 0 || specialDamage <= 0)
            return false;
        if (specialDamage > 100)
            specialDamage = 100;
        attacker.hit(new Hit().fixedDamage(sacrificeDamage));
        target.hit(hit.fixedDamage(specialDamage).ignoreDefence().ignorePrayer());
        target.graphics(754);
        return true;
    }

}