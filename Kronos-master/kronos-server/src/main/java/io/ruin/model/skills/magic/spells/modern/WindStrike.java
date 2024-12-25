package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class WindStrike extends TargetSpell {

    public WindStrike() {
        setLvlReq(1);
        setBaseXp(5.5);
        setMaxDamage(2);
        setAnimationId(1162);
        setCastGfx(90, 92, 0);
        setCastSound(220, 1, 0);
        setHitGfx(92, 124);
        setHitSound(221);
        setProjectile(new Projectile(91, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.MIND.toItem(1), Rune.AIR.toItem(1));
        setAutoCast(1);
    }

}