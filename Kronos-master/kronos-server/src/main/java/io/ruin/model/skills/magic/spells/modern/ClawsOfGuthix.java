package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.item.EquipmentCheck;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class ClawsOfGuthix extends TargetSpell {

    public ClawsOfGuthix() {
        setLvlReq(60);
        setBaseXp(61.0);
        setMaxDamage(20);
        setAnimationId(811);
        setHitGfx(77, 96);
        setHitSound(1653);
        setProjectile(new Projectile(64, 0));
        setRunes(Rune.BLOOD.toItem(2), Rune.FIRE.toItem(1), Rune.AIR.toItem(4));
        setAutoCast(19);
        setCastCheck(new EquipmentCheck("This spell requires a guthix staff or void knight mace equipped to cast.",2416, 8841)); // require staff (guthix or void)
    }

    @Override
    public void beforeHit(Hit hit, Entity target) {
        if(hit.attacker.player.getCombat().chargeTicks > 0 && Charge.capeCheck.hasItems(hit.attacker.player))
            hit.boostDamage(0.5); //makes max damage 30
    }

    @Override
    public void afterHit(Hit hit, Entity target) {
        if(hit.damage > 0 && target.player != null) {
            Stat defence = target.player.getStats().get(StatType.Magic);
            int minLevel = (int) (defence.fixedLevel * 0.95);
            int drain = defence.currentLevel - minLevel;
            if(drain > 0)
                defence.drain(drain);
        }
    }

}