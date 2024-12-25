package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class IceRush extends TargetSpell {

    public IceRush() {
        setLvlReq(58);
        setBaseXp(34.0);
        setMaxDamage(17);
        setAnimationId(1978);
        setCastSound(171, 1, 0);
        setHitGfx(361, 0);
        setHitSound(173);
        setProjectile(new Projectile(360, 43, 0, 51, 56, 10, 16, 64));
        setRunes(Rune.DEATH.toItem(2), Rune.CHAOS.toItem(2), Rune.WATER.toItem(2));
        setAutoCast(34);
    }

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        if (hit.attacker.player != null && hit.attacker.player.getEquipment().hasId(22647)) // zuriel's staff
            hit.boostAttack(0.1);
    }

    @Override
    public void afterHit(Hit hit, Entity target) {
        hold(hit, target, 5, true);
    }

}