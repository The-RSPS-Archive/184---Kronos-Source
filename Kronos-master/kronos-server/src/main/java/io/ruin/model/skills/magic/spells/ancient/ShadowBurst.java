package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class ShadowBurst extends ShadowSpell {

    public static final ShadowBurst INSTANCE = new ShadowBurst();

    public ShadowBurst() {
        super(0.1);
        setLvlReq(64);
        setBaseXp(37.0);
        setMaxDamage(19);
        setAnimationId(1979);
        setCastSound(178, 1, 0);
        setHitGfx(382, 0);
        setHitSound(177);
        setMultiTarget();
        setProjectile(new Projectile(56, 10));
        setRunes(Rune.SOUL.toItem(2), Rune.DEATH.toItem(2), Rune.CHAOS.toItem(4), Rune.AIR.toItem(1));
        setAutoCast(36);
    }

}