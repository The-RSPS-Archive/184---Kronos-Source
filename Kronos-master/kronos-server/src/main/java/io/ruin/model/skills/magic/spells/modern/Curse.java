package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class Curse extends TargetSpell {

    public Curse() {
        setLvlReq(19);
        setBaseXp(29.0);
        setMaxDamage(0);
        setAnimationId(1165);
        setCastGfx(108, 92, 0);
        setCastSound(127, 1, 0);
        setHitGfx(110, 124);
        setHitSound(125);
        setProjectile(new Projectile(109, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.BODY.toItem(1), Rune.EARTH.toItem(3), Rune.WATER.toItem(2));
    }

}