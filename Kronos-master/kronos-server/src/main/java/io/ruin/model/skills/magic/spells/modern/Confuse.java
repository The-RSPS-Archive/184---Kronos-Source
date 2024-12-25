package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class Confuse extends TargetSpell {

    public Confuse() {
        setLvlReq(3);
        setBaseXp(13.0);
        setMaxDamage(0);
        setAnimationId(1163);
        setCastGfx(102, 92, 0);
        setCastSound(119, 1, 0);
        setHitGfx(104, 200);
        setHitSound(121);
        setProjectile(new Projectile(103, 36, 31, 61, 56, 10, 16, 64));
        setRunes(Rune.BODY.toItem(1), Rune.EARTH.toItem(2), Rune.WATER.toItem(3));
    }

}