package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class EarthSurge extends TargetSpell {

    public EarthSurge() {
        setLvlReq(90);
        setBaseXp(47.5);
        setMaxDamage(23);
        setAnimationId(7855);
        setCastGfx(1461, 92, 0);
        setCastSound(162, 1, 0);
        setHitGfx(1463, 124);
        setProjectile(new Projectile(1462, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.EARTH.toItem(10), Rune.AIR.toItem(7), Rune.WRATH.toItem(1));
        setAutoCast(50);
    }

}