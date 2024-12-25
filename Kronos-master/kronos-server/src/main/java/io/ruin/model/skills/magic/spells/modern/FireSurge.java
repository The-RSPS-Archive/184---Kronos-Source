package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class FireSurge extends FireSpell {

    public FireSurge() {
        setLvlReq(95);
        setBaseXp(50);
        setMaxDamage(24);
        setAnimationId(7855);
        setCastGfx(1464, 92, 0);
        setCastSound(162, 1, 0);
        setHitGfx(1466, 124);
        setProjectile(new Projectile(1465, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.FIRE.toItem(10), Rune.AIR.toItem(7), Rune.WRATH.toItem(1));
        setAutoCast(51);
    }

}