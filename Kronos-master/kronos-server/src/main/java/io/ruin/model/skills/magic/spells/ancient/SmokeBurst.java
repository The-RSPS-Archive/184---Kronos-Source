package io.ruin.model.skills.magic.spells.ancient;

import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;

public class SmokeBurst extends SmokeSpell {

    public SmokeBurst() {
        super(2);
        setLvlReq(62);
        setBaseXp(36.0);
        setMaxDamage(18);
        setAnimationId(1979);
        setCastSound(183, 1, 0);
        setHitGfx(389, 124);
        setHitSound(182);
        setMultiTarget();
        setProjectile(new Projectile(388, 43, 31, 51, 56, 10, 16, 64).skipTravel());
        setRunes(Rune.DEATH.toItem(2), Rune.CHAOS.toItem(4), Rune.FIRE.toItem(2), Rune.AIR.toItem(2));
        setAutoCast(35);
    }

}