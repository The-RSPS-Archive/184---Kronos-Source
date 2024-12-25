package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

//Smash: Deal an attack that inflicts 50% more damage
//and lowers your target's Defence level by 30%. (50%)
public class DragonWarhammer implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dragon warhammer");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(1378);
        player.graphics(1292);
        player.publicSound(2541);
        if(target.player != null)
            target.player.getStats().get(StatType.Defence).drain(0.30);
        else
            target.npc.getCombat().getStat(StatType.Defence).drain(0.30);
        target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.50));
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}