package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.item.EquipmentCheck;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class FlamesOfZamorak extends TargetSpell {

    public FlamesOfZamorak() {
        setLvlReq(60);
        setBaseXp(61.0);
        setMaxDamage(20);
        setAnimationId(811);
        setHitGfx(78, 0);
        setHitSound(1655);
        setProjectile(new Projectile(64, 0));
        setRunes(Rune.BLOOD.toItem(2), Rune.FIRE.toItem(4), Rune.AIR.toItem(1));
        setAutoCast(20);
        setCastCheck(new EquipmentCheck("This spell requires a Zamorak staff equipped to cast.", 2417, 11791, 12902, 12904)); // require staff
    }

    @Override
    public void beforeHit(Hit hit, Entity target) {
        if(hit.attacker.player.getCombat().chargeTicks > 0 && Charge.capeCheck.hasItems(hit.attacker.player))
            hit.boostDamage(0.5); //makes max damage 30
    }

    @Override
    public void afterHit(Hit hit, Entity target) {
        if(hit.damage > 0 && target.player != null) {
            Stat magic = target.player.getStats().get(StatType.Magic);
            int minLevel = (int) (magic.fixedLevel * 0.95);
            int drain = magic.currentLevel - minLevel;
            if(drain > 0)
                magic.drain(drain);
        }
    }

}