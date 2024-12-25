package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class EarthStrike extends TargetSpell {

    public EarthStrike() {
        setLvlReq(9);
        setBaseXp(9.5);
        setMaxDamage(6);
        setAnimationId(1162);
        setCastGfx(96, 92, 0);
        setCastSound(132, 1, 0);
        setHitGfx(98, 124);
        setHitSound(133);
        setProjectile(new Projectile(97, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.MIND.toItem(1), Rune.EARTH.toItem(2), Rune.AIR.toItem(1));
        setAutoCast(3);
    }

}