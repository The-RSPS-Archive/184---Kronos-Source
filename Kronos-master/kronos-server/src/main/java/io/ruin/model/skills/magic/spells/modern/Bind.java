package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class Bind extends RootSpell {

    public Bind() {
        super(5);
        setLvlReq(20);
        setBaseXp(30.0);
        setMaxDamage(0);
        setAnimationId(1161);
        setCastGfx(177, 120, 0);
        setCastSound(101, 1, 0);
        setHitGfx(181, 124);
        setHitSound(99);
        setProjectile(new Projectile(178, 45, 0, 75, 56, 10, 16, 64));
        setRunes(Rune.NATURE.toItem(2), Rune.EARTH.toItem(3), Rune.WATER.toItem(3));
    }

}