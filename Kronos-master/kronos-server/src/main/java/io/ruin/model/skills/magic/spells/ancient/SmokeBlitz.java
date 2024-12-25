package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class SmokeBlitz extends SmokeSpell {

    public SmokeBlitz() {
        super(4);
        setLvlReq(74);
        setBaseXp(42.0);
        setMaxDamage(23);
        setAnimationId(1978);
        setCastSound(183, 1, 0);
        setHitGfx(387, 124);
        setHitSound(181);
        setProjectile(new Projectile(386, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.BLOOD.toItem(2), Rune.DEATH.toItem(2), Rune.FIRE.toItem(2), Rune.AIR.toItem(2));
        setAutoCast(39);
    }

}