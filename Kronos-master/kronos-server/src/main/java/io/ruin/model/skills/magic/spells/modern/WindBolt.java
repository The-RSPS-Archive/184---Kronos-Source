package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class WindBolt extends TargetSpell {

    public WindBolt() {
        setLvlReq(17);
        setBaseXp(13.5);
        setMaxDamage(9);
        setAnimationId(1162);
        setCastGfx(117, 92, 0);
        setCastSound(218, 1, 0);
        setHitGfx(119, 124);
        setHitSound(219);
        setProjectile(new Projectile(118, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.CHAOS.toItem(1), Rune.AIR.toItem(2));
        setAutoCast(5);
    }

}