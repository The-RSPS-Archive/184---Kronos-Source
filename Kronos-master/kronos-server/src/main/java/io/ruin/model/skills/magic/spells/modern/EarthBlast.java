package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class EarthBlast extends TargetSpell {

    public EarthBlast() {
        setLvlReq(53);
        setBaseXp(31.5);
        setMaxDamage(15);
        setAnimationId(1162);
        setCastGfx(138, 92, 0);
        setCastSound(128, 1, 0);
        setHitGfx(140, 124);
        setHitSound(129);
        setProjectile(new Projectile(139, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.DEATH.toItem(1), Rune.EARTH.toItem(4), Rune.AIR.toItem(3));
        setAutoCast(11);
    }

}