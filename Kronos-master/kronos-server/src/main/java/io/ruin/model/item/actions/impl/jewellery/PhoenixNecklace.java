package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;

public class PhoenixNecklace {

    public static void check(Player player) {
        if (player.getHp() <= player.getMaxHp() * 0.20 && !player.getCombat().isDead()) {
            Item necklace = player.getEquipment().get(Equipment.SLOT_AMULET);
            if (necklace == null || necklace.getId() != 11090)
                return;
            if (DuelRule.NO_FOOD.isToggled(player))
                return;
            necklace.remove();
            int heal = (int) (player.getMaxHp() * 0.30);
            player.incrementHp(heal);
            player.sendFilteredMessage("Your phoenix necklaces regains your health and crumbles to dust.");
        }
    }
}
