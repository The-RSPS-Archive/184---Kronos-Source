package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class BloodBurst extends BloodSpell {

    public BloodBurst() {
        setLvlReq(68);
        setBaseXp(39.0);
        setMaxDamage(21);
        setAnimationId(1979);
        setCastSound(106, 1, 0);
        setHitGfx(376, 0);
        setHitSound(105);
        setMultiTarget();
        setProjectile(new Projectile(56, 10));
        setRunes(Rune.BLOOD.toItem(2), Rune.DEATH.toItem(2), Rune.CHAOS.toItem(4));
        setAutoCast(37);
    }

}