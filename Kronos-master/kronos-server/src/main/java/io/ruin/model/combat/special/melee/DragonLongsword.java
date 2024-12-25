package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

//Cleave: Deal a powerful attack
//that inflicts 15% more damage. (25%)
public class DragonLongsword implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dragon longsword");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(1058);
        player.graphics(248, 96, 0);
        player.publicSound(2529);
        target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.15));
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 25;
    }

}