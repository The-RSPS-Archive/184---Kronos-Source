package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class SmokeBarrage extends SmokeSpell {

    public SmokeBarrage() {
        super(4);
        setLvlReq(86);
        setBaseXp(48.0);
        setMaxDamage(27);
        setAnimationId(1979);
        setCastSound(183, 1, 0);
        setHitGfx(391, 124);
        setHitSound(180);
        setMultiTarget();
        setProjectile(new Projectile(390, 43, 31, 51, 56, 10, 16, 64).skipTravel());
        setRunes(Rune.BLOOD.toItem(2), Rune.DEATH.toItem(4), Rune.FIRE.toItem(4), Rune.AIR.toItem(4));
        setAutoCast(43);
    }

}