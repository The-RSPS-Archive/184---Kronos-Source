package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class WindBlast extends TargetSpell {

    public WindBlast() {
        setLvlReq(41);
        setBaseXp(25.5);
        setMaxDamage(13);
        setAnimationId(1162);
        setCastGfx(132, 92, 0);
        setCastSound(216, 1, 0);
        setHitGfx(134, 124);
        setHitSound(217);
        setProjectile(new Projectile(133, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.DEATH.toItem(1), Rune.AIR.toItem(3));
        setAutoCast(9);
    }

}