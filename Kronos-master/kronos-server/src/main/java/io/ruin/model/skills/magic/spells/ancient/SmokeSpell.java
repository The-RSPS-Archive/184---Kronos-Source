package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class SmokeSpell extends TargetSpell {

    private int poisonDamage;

    public SmokeSpell(int poisonDamage) {
        this.poisonDamage = poisonDamage;
    }

    @Override
    protected void afterHit(Hit hit, Entity target) {
        if(hit.damage > 0 && Random.rollDie(4)) {
            if (hit.attacker.player != null && hit.attacker.player.getEquipment().hasId(22647)) // zuriel's staff
                target.poison(poisonDamage * 2);
            else
                target.poison(poisonDamage);
        }
    }
}
