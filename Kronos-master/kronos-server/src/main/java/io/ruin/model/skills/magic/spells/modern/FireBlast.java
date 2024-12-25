package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class FireBlast extends FireSpell {

    public FireBlast() {
        setLvlReq(59);
        setBaseXp(34.5);
        setMaxDamage(16);
        setAnimationId(1162);
        setCastGfx(129, 92, 0);
        setCastSound(155, 1, 0);
        setHitGfx(131, 124);
        setHitSound(156);
        setProjectile(new Projectile(130, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.DEATH.toItem(1), Rune.FIRE.toItem(5), Rune.AIR.toItem(4));
        setAutoCast(12);
    }

}