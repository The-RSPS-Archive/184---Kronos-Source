package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class WaterStrike extends TargetSpell {

    public WaterStrike() {
        setLvlReq(5);
        setBaseXp(7.5);
        setMaxDamage(4);
        setAnimationId(1162);
        setCastGfx(93, 92, 0);
        setCastSound(211, 1, 0);
        setHitGfx(95, 124);
        setHitSound(212);
        setProjectile(new Projectile(94, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.MIND.toItem(1), Rune.WATER.toItem(1), Rune.AIR.toItem(1));
        setAutoCast(2);
    }

}