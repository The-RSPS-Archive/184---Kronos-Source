package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class Entangle extends RootSpell {

    public Entangle() {
        super(15);
        setLvlReq(79);
        setBaseXp(89.0);
        setMaxDamage(5);
        setAnimationId(1161);
        setCastGfx(177, 120, 0);
        setCastSound(151, 1, 0);
        setHitGfx(179, 100);
        setHitSound(153);
        setProjectile(new Projectile(178, 45, 0, 75, 56, 10, 16, 64));
        setRunes(Rune.NATURE.toItem(4), Rune.EARTH.toItem(5), Rune.WATER.toItem(5));
        setCastCheck(TargetSpell::allowHold);
    }

}