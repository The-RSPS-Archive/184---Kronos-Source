package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class WaterWave extends TargetSpell {

    public WaterWave() {
        setLvlReq(65);
        setBaseXp(37.5);
        setMaxDamage(18);
        setAnimationId(1167);
        setCastGfx(161, 92, 0);
        setCastSound(213, 1, 0);
        setHitGfx(163, 124);
        setHitSound(214);
        setProjectile(new Projectile(162, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.BLOOD.toItem(1), Rune.WATER.toItem(7), Rune.AIR.toItem(5));
        setAutoCast(14);
    }

}