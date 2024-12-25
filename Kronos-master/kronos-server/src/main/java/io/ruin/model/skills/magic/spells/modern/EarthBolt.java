package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class EarthBolt extends TargetSpell {

    public EarthBolt() {
        setLvlReq(29);
        setBaseXp(19.5);
        setMaxDamage(11);
        setAnimationId(1162);
        setCastGfx(123, 92, 0);
        setCastSound(130, 1, 0);
        setHitGfx(125, 124);
        setHitSound(131);
        setProjectile(new Projectile(124, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.CHAOS.toItem(1), Rune.EARTH.toItem(3), Rune.AIR.toItem(2));
        setAutoCast(7);
    }

}