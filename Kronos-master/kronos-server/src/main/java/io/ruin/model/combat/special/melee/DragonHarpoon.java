package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

public class DragonHarpoon implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dragon harpoon");
    }

    @Override
    public boolean handleActivation(Player player) {
        if(player.getStats().get(StatType.Fishing).currentLevel < 61) {
            player.sendMessage("You need a fishing level of 61 to use this special attack.");
            return false;
        }
        if(!player.getCombat().useSpecialEnergy(100))
            return false;
        player.animate(7393);
        player.graphics(246);
        player.forceText("Here fishy fishies!");
        player.sendMessage("As you harness the power of the dragon harpoon you feel a slight increase in your ability to catch fish.");
        player.getStats().get(StatType.Fishing).boost(3, 0.0);
        return true;
    }

}