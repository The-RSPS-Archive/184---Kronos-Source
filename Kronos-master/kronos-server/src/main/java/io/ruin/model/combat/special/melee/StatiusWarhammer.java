package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

//The Statius's warhammer special attack, Smash, costs 35% of the wielder's special attack energy.
//Upon a successful hit it will deal anywhere between 25% and 125% of the wielder's standard max hit, any successful hit will lower the opponents Defence by 30%.
public class StatiusWarhammer implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("statius's warhammer");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(1378);
        player.graphics(1450);
        player.publicSound(2541);
        if(target.player != null)
            target.player.getStats().get(StatType.Defence).drain(0.30);
        else
            target.npc.getCombat().getStat(StatType.Defence).drain(0.30);
        target.hit(new Hit(player, attackStyle, attackType).randDamage((int)(maxDamage * 0.25), (int) (maxDamage * 1.25)));
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 35;
    }

}