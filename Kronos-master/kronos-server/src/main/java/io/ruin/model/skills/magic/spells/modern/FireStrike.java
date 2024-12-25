package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class FireStrike extends FireSpell {

    public FireStrike() {
        setLvlReq(13);
        setBaseXp(11.5);
        setMaxDamage(8);
        setAnimationId(1162);
        setCastGfx(99, 92, 0);
        setCastSound(160, 1, 0);
        setHitGfx(101, 124);
        setHitSound(161);
        setProjectile(new Projectile(100, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.MIND.toItem(1), Rune.FIRE.toItem(3), Rune.AIR.toItem(2));
        setAutoCast(4);
    }

}