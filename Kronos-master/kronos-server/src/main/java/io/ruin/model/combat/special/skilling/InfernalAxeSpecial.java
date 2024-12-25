package io.ruin.model.combat.special.skilling;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

//Lumber Up: Increase your Woodcutting level by 3. (100%)
public class InfernalAxeSpecial implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.equals("infernal axe");
    }

    @Override
    public boolean handleActivation(Player player) {
        if (!player.getCombat().useSpecialEnergy(100))
            return false;
        player.animate(2876);
        player.graphics(479, 100, 0);
        player.forceText("Burn it down!");
        player.getStats().get(StatType.Woodcutting).boost(3, 0);
        player.infernalAxeSpecial = 200;
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 100;
    }

}
