package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class IbanBlast extends TargetSpell {

    public IbanBlast() {
        setLvlReq(50);
        setBaseXp(30.0);
        setMaxDamage(25);
        setAnimationId(708);
        setCastGfx(87, 92, 0);
        setCastSound(162, 1, 0);
        setHitGfx(89, 124);
        setHitSound(163);
        setProjectile(new Projectile(88, 36, 31, 60, 56, 10, 16, 64));
        setRunes(Rune.DEATH.toItem(1), Rune.FIRE.toItem(5));
        setAutoCast(47);
    }

}