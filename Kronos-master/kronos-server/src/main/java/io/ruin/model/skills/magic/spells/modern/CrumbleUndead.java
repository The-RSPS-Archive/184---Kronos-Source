package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class CrumbleUndead extends TargetSpell {

    public static final CrumbleUndead INSTANCE = new CrumbleUndead();

    public CrumbleUndead() {
        setLvlReq(39);
        setBaseXp(24.5);
        setMaxDamage(15);
        setAnimationId(1166);
        setCastGfx(145, 92, 0);
        setCastSound(122, 1, 0);
        setHitGfx(147, 124);
        setHitSound(124);
        setProjectile(new Projectile(146, 31, 31, 46, 36, 10, 16, 64));
        setRunes(Rune.CHAOS.toItem(1), Rune.EARTH.toItem(2), Rune.AIR.toItem(2));
        setAutoCast(17);
    }

}