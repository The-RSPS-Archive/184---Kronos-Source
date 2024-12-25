package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.item.EquipmentCheck;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;

public class SaradominStrike extends TargetSpell {

    public SaradominStrike() {
        setLvlReq(60);
        setBaseXp(61.0);
        setMaxDamage(20);
        setAnimationId(811);
        setHitGfx(76, 128);
        setAutoCast(52);
        setHitSound(1659);
        setProjectile(new Projectile(64, 0));
        setRunes(Rune.BLOOD.toItem(2), Rune.FIRE.toItem(2), Rune.AIR.toItem(4));
        setCastCheck(new EquipmentCheck("This spell requires a Zamorak staff equipped to cast.", 2415, 22296)); // require staff
    }

    @Override
    public void beforeHit(Hit hit, Entity target) {
        if(hit.attacker.player.getCombat().chargeTicks > 0 && Charge.capeCheck.hasItems(hit.attacker.player))
            hit.boostDamage(0.5); //makes max damage 30
    }

    @Override
    public void afterHit(Hit hit, Entity target) {
        if(hit.damage > 0 && target.player != null)
            target.player.getPrayer().drain(1);
    }

}