package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class ShadowSpell extends TargetSpell {

    private double attackDrain;

    public ShadowSpell(double attackDrain) {
        this.attackDrain = attackDrain;
    }

    @Override
    protected void afterHit(Hit hit, Entity target) {
        if(hit.damage > 0 && target.player != null && Random.rollDie(4)) {
            Stat attack = target.player.getStats().get(StatType.Attack);
            double drain = attack.currentLevel - (attack.fixedLevel * (1 - attackDrain));
            if (hit.attacker.player != null && hit.attacker.player.getEquipment().hasId(22647)) // zuriel's staff
                drain *= 2;
            if(drain > 0)
                attack.drain((int) drain);
        }
    }
}
