package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class WaterSurge extends TargetSpell {

    public WaterSurge() {
        setLvlReq(85);
        setBaseXp(45.5);
        setMaxDamage(22);
        setAnimationId(7855);
        setCastGfx(1458, 92, 0);
        setCastSound(162, 1, 0);
        setHitGfx(1460, 124);
        setProjectile(new Projectile(1459, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.WATER.toItem(10), Rune.AIR.toItem(7), Rune.WRATH.toItem(1));
        setAutoCast(49);
    }

}