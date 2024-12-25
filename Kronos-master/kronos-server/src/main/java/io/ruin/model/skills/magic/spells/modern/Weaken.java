package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class Weaken extends TargetSpell {

    public Weaken() {
        setLvlReq(11);
        setBaseXp(21.0);
        setMaxDamage(0);
        setAnimationId(1164);
        setCastGfx(105, 92, 0);
        setCastSound(3011, 1, 0);
        setHitGfx(107, 200);
        setHitSound(3010);
        setProjectile(new Projectile(106, 36, 31, 44, 56, 10, 16, 64));
        setRunes(Rune.BODY.toItem(1), Rune.EARTH.toItem(2), Rune.WATER.toItem(3));
    }

}