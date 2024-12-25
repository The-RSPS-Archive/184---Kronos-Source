package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class BloodBlitz extends BloodSpell {

    public BloodBlitz() {
        setLvlReq(80);
        setBaseXp(45.0);
        setMaxDamage(25);
        setAnimationId(1978);
        setCastSound(106, 1, 0);
        setHitGfx(375, 0);
        setHitSound(104);
        setProjectile(new Projectile(374, 43, 0, 51, 56, 10, 16, 64));
        setRunes(Rune.BLOOD.toItem(4), Rune.DEATH.toItem(2));
        setAutoCast(41);
    }

}