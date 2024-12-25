package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class IceBarrage extends TargetSpell {

    public IceBarrage() {
        setLvlReq(94);
        setBaseXp(52.0);
        setMaxDamage(30);
        setAnimationId(1979);
        setCastSound(171, 1, 0);
        setHitGfx(369, 0);
        setHitSound(168);
        setMultiTarget();
        setProjectile(new Projectile(368, 43, 0, 51, 56, 10, 16, 64).skipTravel());
        setRunes(Rune.BLOOD.toItem(2), Rune.DEATH.toItem(4), Rune.WATER.toItem(6));
        setAutoCast(46);
    }

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        if (hit.attacker.player != null && hit.attacker.player.getEquipment().hasId(22647)) // zuriel's staff
            hit.boostAttack(0.1);
    }

    @Override
    public void afterHit(Hit hit, Entity target) {
        hold(hit, target, 20, true);
    }

}