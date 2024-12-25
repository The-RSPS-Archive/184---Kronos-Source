package io.ruin.model.item.actions.impl.boxes.mystery;

import io.ruin.cache.Color;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

public class PVPArmourMysteryBox {

    private static final LootTable PVP_ARMOUR_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(22622, 1, 1), // Statius's warhammer
            new LootItem(22625, 1, 1), // Statius's full helm
            new LootItem(22628, 1, 1), // Statius's platebody
            new LootItem(22631, 1, 1), // Statius's platelegs

            new LootItem(22610, 1, 1), // Vesta's spear
            new LootItem(22613, 1, 1), // Vesta's longsword
            new LootItem(22616, 1, 1), // Vesta's chainbody
            new LootItem(22619, 1, 1), // Vesta's plateskirt

            new LootItem(22647, 1, 1), // Zuriel's staff
            new LootItem(22650, 1, 1), // Zuriel's hood
            new LootItem(22653, 1, 1), // Zuriel's robe top
            new LootItem(22656, 1, 1), // Zuriel's robe bottom

            new LootItem(22638, 1, 1), // Morrigan's coif
            new LootItem(22641, 1, 1), // Morrigan's leather body
            new LootItem(22644, 1, 1), // Morrigan's leather chaps

            new LootItem(22634, 50, 1), // Morrian's throwing axe
            new LootItem(22636, 50, 1) // Morrian's javelin
    );

    static {
        ItemAction.registerInventory(22330, "open", (player, item) -> {
            player.lock();
            Item reward = PVP_ARMOUR_BOX_TABLE.rollItem();
            player.closeDialogue();
            item.remove();
            player.getInventory().add(reward);
            player.sendFilteredMessage(Color.DARK_GREEN.wrap("You open the mystery box to find a " + reward.getDef().name + "."));
            //Broadcast.GLOBAL.sendNews(Icon.MYSTERY_BOX, "PVP Armour Mystery Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
            player.unlock();
        });

    }
}
