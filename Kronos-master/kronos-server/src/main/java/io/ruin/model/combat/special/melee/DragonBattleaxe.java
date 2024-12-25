package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

//Rampage: Drain your Attack, Defence, Ranged & Magic levels by 10%, while
//increasing your Strength be 10 levels, plus 25% of the drained levels. (100%)
public class DragonBattleaxe implements Special {

    private static final StatType[] DRAIN_STATS = {StatType.Attack, StatType.Defence, StatType.Ranged, StatType.Magic};

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dragon battleaxe");
    }

    @Override
    public boolean handleActivation(Player player) {
        if(!player.getCombat().useSpecialEnergy(100))
            return false; //don't allow it to be toggled on
        if(DuelRule.NO_DRINKS.isToggled(player) || DuelRule.NO_SPECIALS.isToggled(player))
            return false;
        player.animate(1056);
        player.graphics(246);
        player.forceText("Raarrrrrgggggghhhhhhh!");
        int drained = 0;
        for(StatType statType : DRAIN_STATS)
            drained += player.getStats().get(statType).drain(0.10);
        player.getStats().get(StatType.Strength).boost(10 + (int) (drained * 0.25), 0.0);
        return true;
    }

}