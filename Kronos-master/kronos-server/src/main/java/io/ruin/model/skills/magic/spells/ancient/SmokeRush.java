package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class SmokeRush extends SmokeSpell {

    public SmokeRush() {
        super(2);
        setLvlReq(50);
        setBaseXp(30.0);
        setMaxDamage(14);
        setAnimationId(1978);
        setCastSound(183, 1, 0);
        setHitGfx(385, 124);
        setHitSound(185);
        setProjectile(new Projectile(384, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.DEATH.toItem(2), Rune.CHAOS.toItem(2), Rune.FIRE.toItem(1), Rune.AIR.toItem(1));
        setAutoCast(31);
    }

}