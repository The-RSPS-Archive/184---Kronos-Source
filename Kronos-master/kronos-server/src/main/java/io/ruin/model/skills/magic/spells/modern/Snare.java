package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class Snare extends RootSpell {

    public Snare() {
        super(10);
        setLvlReq(50);
        setBaseXp(60.0);
        setMaxDamage(2);
        setAnimationId(1161);
        setCastGfx(177, 120, 0);
        setCastSound(3003, 1, 0);
        setHitGfx(180, 124);
        setHitSound(3002);
        setProjectile(new Projectile(178, 45, 0, 75, 56, 10, 16, 64));
        setRunes(Rune.NATURE.toItem(3), Rune.EARTH.toItem(4), Rune.WATER.toItem(4));
    }

}