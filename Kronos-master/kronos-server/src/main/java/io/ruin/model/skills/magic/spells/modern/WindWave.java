package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class WindWave extends TargetSpell {

    public WindWave() {
        setLvlReq(62);
        setBaseXp(36.0);
        setMaxDamage(17);
        setAnimationId(1167);
        setCastGfx(158, 92, 0);
        setCastSound(222, 1, 0);
        setHitGfx(160, 124);
        setHitSound(223);
        setProjectile(new Projectile(159, 43, 31, 51, 56, 10, 16, 64));
        setRunes(Rune.BLOOD.toItem(1), Rune.AIR.toItem(5));
        setAutoCast(13);
    }

}