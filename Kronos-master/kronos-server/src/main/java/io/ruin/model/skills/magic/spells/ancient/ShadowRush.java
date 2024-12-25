package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class ShadowRush extends ShadowSpell {

    public static final ShadowRush INSTANCE = new ShadowRush();

    public ShadowRush() {
        super(0.1);
        setLvlReq(52);
        setBaseXp(31.0);
        setMaxDamage(15);
        setAnimationId(1978);
        setCastSound(178, 1, 0);
        setHitGfx(379, 0);
        setHitSound(179);
        setProjectile(new Projectile(378, 43, 0, 51, 56, 10, 16, 64));
        setRunes(Rune.SOUL.toItem(1), Rune.DEATH.toItem(2), Rune.CHAOS.toItem(2), Rune.AIR.toItem(1));
        setAutoCast(32);
    }

}