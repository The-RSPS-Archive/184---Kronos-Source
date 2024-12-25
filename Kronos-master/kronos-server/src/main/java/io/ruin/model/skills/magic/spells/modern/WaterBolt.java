package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class WaterBolt extends TargetSpell {

    public WaterBolt() {
        setLvlReq(23);
        setBaseXp(16.5);
        setMaxDamage(10);
        setAnimationId(1162);
        setCastGfx(120, 92, 0);
        setCastSound(209, 1, 0);
        setHitGfx(122, 124);
        setHitSound(210);
        setProjectile(new Projectile(121, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.CHAOS.toItem(1), Rune.WATER.toItem(2), Rune.AIR.toItem(2));
        setAutoCast(6);
    }

}