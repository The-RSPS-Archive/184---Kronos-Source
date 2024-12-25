package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class ShadowBlitz extends ShadowSpell {

    public static final ShadowBlitz INSTANCE = new ShadowBlitz();

    public ShadowBlitz() {
        super(0.15);
        setLvlReq(76);
        setBaseXp(43.0);
        setMaxDamage(24);
        setAnimationId(1978);
        setCastSound(178, 1, 0);
        setHitGfx(381, 0);
        setHitSound(176);
        setProjectile(new Projectile(380, 43, 0, 51, 56, 10, 16, 64));
        setRunes(Rune.SOUL.toItem(2), Rune.BLOOD.toItem(2), Rune.DEATH.toItem(2), Rune.AIR.toItem(2));
        setAutoCast(40);
    }

}