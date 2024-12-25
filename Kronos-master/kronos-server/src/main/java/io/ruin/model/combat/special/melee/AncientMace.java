package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

//Favour of the War God: Deal an attack which hits through Protect
//from Melee and siphons Prayer points equal to the damage dealt. (100%)
public class AncientMace implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 11061;
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(6147);
        player.graphics(1052);
        int damage = target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).ignorePrayer());
        if(damage > 0 && target.player != null) {
            target.player.getPrayer().drain(damage);
            player.getStats().get(StatType.Prayer).restore(damage, 0);
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 100;
    }
}
