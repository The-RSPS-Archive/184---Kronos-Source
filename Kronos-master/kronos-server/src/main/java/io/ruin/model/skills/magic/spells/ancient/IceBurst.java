package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class IceBurst extends TargetSpell {

    public IceBurst() {
        setLvlReq(70);
        setBaseXp(40.0);
        setMaxDamage(22);
        setAnimationId(1979);
        setCastSound(171, 1, 0);
        setHitGfx(363, 0);
        setHitSound(170);
        setMultiTarget();
        setProjectile(new Projectile(366, 43, 0, 51, 56, 10, 16, 64).skipTravel());
        setRunes(Rune.DEATH.toItem(2), Rune.CHAOS.toItem(4), Rune.WATER.toItem(4));
        setAutoCast(38);
    }

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        if (hit.attacker.player != null && hit.attacker.player.getEquipment().hasId(22647)) // zuriel's staff
            hit.boostAttack(0.1);
    }

    @Override
    public void afterHit(Hit hit, Entity target) {
        hold(hit, target, 10, true);
    }

}