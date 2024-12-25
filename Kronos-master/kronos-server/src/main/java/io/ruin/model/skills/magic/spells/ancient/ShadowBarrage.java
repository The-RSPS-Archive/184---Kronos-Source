package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class ShadowBarrage extends ShadowSpell {

    public static final ShadowBarrage INSTANCE = new ShadowBarrage();

    public ShadowBarrage() {
        super(0.15);
        setLvlReq(88);
        setBaseXp(49.0);
        setMaxDamage(28);
        setAnimationId(1979);
        setCastSound(178, 1, 0);
        setHitGfx(383, 0);
        setHitSound(175);
        setMultiTarget();
        setProjectile(new Projectile(56, 10));
        setRunes(Rune.SOUL.toItem(3), Rune.BLOOD.toItem(2), Rune.DEATH.toItem(4), Rune.AIR.toItem(4));
        setAutoCast(44);
    }

}