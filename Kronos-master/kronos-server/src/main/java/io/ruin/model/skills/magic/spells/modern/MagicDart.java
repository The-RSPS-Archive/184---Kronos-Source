package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class MagicDart extends TargetSpell {

    public MagicDart() {
        setLvlReq(50);
        setBaseXp(30.0);
        setMaxDamage(10);
        setAnimationId(1576);
        setCastSound(1718, 1, 0);
        setHitGfx(329, 124);
        setHitSound(174);
        setProjectile(new Projectile(328, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.DEATH.toItem(1), Rune.MIND.toItem(4));
        setAutoCast(18);
    }

}