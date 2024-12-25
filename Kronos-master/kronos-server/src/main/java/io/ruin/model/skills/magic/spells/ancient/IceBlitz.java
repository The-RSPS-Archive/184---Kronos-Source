package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class IceBlitz extends TargetSpell {

    public IceBlitz() {
        setLvlReq(82);
        setBaseXp(46.0);
        setMaxDamage(26);
        setAnimationId(1978);
        setCastGfx(366, 124, 0);
        setCastSound(171, 1, 0);
        setHitGfx(367, 0);
        setHitSound(169);
        setProjectile(new Projectile(56, 10));
        setRunes(Rune.BLOOD.toItem(2), Rune.DEATH.toItem(2), Rune.WATER.toItem(3));
        setAutoCast(42);
    }

    @Override
    protected void beforeHit(Hit hit, Entity target) {
        if (hit.attacker.player != null && hit.attacker.player.getEquipment().hasId(22647)) // zuriel's staff
            hit.boostAttack(0.1);
    }

    @Override
    public void afterHit(Hit hit, Entity target) {
        hold(hit, target, 15, true);
    }

}