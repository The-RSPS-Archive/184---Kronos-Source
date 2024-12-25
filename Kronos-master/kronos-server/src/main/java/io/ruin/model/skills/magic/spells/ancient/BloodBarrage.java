package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class BloodBarrage extends BloodSpell {

    public BloodBarrage() {
        setLvlReq(92);
        setBaseXp(51.0);
        setMaxDamage(29);
        setAnimationId(1979);
        setCastSound(106, 1, 0);
        setHitGfx(377, 0);
        setHitSound(102);
        setMultiTarget();
        setProjectile(new Projectile(51, 56, 10));
        setRunes(Rune.SOUL.toItem(1), Rune.BLOOD.toItem(4), Rune.DEATH.toItem(4));
        setAutoCast(45);
    }

}