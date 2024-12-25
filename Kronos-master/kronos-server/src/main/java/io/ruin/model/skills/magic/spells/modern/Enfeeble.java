package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class Enfeeble extends TargetSpell {

    public Enfeeble() {
        setLvlReq(73);
        setBaseXp(83.0);
        setMaxDamage(0);
        setAnimationId(1168);
        setCastGfx(170, 92, 0);
        setCastSound(148, 1, 0);
        setHitGfx(172, 124);
        setHitSound(150);
        setProjectile(new Projectile(171, 36, 31, 48, 56, 10, 16, 64));
        setRunes(Rune.SOUL.toItem(1), Rune.EARTH.toItem(8), Rune.WATER.toItem(8));
    }

}