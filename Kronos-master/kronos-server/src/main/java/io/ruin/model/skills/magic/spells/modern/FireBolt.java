package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class FireBolt extends FireSpell {

    public FireBolt() {
        setLvlReq(35);
        setBaseXp(21.5);
        setMaxDamage(12);
        setAnimationId(1162);
        setCastGfx(126, 92, 0);
        setCastSound(157, 1, 0);
        setHitGfx(128, 124);
        setHitSound(158);
        setProjectile(new Projectile(127, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.CHAOS.toItem(1), Rune.FIRE.toItem(4), Rune.AIR.toItem(3));
        setAutoCast(8);
    }

}