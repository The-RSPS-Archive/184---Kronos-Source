package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

//Sever: Deal a slash with increased accuracy that prevents your target
//from using protection prayers for 5 seconds if it successfully hits. (55%)
public class DragonScimitar implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dragon scimitar");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(1872);
        player.graphics(347, 96, 0);
        player.publicSound(2529);
        int damage = target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostAttack(0.25));
        if(damage > 0 && target.player != null)
            target.player.getPrayer().slashPrayers();
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 55;
    }

}