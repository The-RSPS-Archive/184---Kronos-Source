package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class EarthWave extends TargetSpell {

    public EarthWave() {
        setLvlReq(70);
        setBaseXp(40.0);
        setMaxDamage(19);
        setAnimationId(1167);
        setCastGfx(164, 92, 0);
        setCastSound(134, 1, 0);
        setHitGfx(166, 124);
        setHitSound(135);
        setProjectile(new Projectile(165, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.BLOOD.toItem(1), Rune.EARTH.toItem(7), Rune.AIR.toItem(5));
        setAutoCast(15);
    }

}