package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class Stun extends TargetSpell {

    public Stun() {
        setLvlReq(80);
        setBaseXp(90.0);
        setMaxDamage(0);
        setAnimationId(1169);
        setCastGfx(173, 92, 0);
        setCastSound(3004, 1, 0);
        setHitGfx(80, 124);
        setHitSound(3005);
        setProjectile(new Projectile(174, 36, 31, 52, 56, 10, 16, 64));
        setRunes(Rune.SOUL.toItem(1), Rune.EARTH.toItem(12), Rune.WATER.toItem(12));
    }

}