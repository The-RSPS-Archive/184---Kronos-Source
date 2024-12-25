package io.ruin.model.combat.special.skilling;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

//Rock Knocker: Increase your Mining level by 3. (100%)
public class InfernalPickaxeSpecial implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.equals("infernal pickaxe");
    }

    @Override
    public boolean handleActivation(Player player) {
        if(!player.getCombat().useSpecialEnergy(100))
            return false;
        player.animate(2876);
        player.graphics(479);
        player.forceText("Burning!");
        player.getStats().get(StatType.Mining).boost(3, 0);
        player.infernalAxeSpecial = 200;
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 100;
    }

}
