package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class BloodRush extends BloodSpell {

    public BloodRush() {
        setLvlReq(56);
        setBaseXp(33.0);
        setMaxDamage(16);
        setAnimationId(1978);
        setCastSound(106, 1, 0);
        setHitGfx(373, 0);
        setHitSound(110);
        setProjectile(new Projectile(56, 10));
        setRunes(Rune.BLOOD.toItem(1), Rune.DEATH.toItem(2), Rune.CHAOS.toItem(2));
        setAutoCast(33);
    }

}