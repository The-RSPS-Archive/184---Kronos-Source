package io.ruin.model.item.actions.impl.boxes;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

public class HerbBox {

    private static final LootTable table = new LootTable().addTable(1,
            new LootItem(249, 1, 25),   //Guam leaf
            new LootItem(251, 1, 18),   //Marrentill
            new LootItem(253, 1, 14),   //Tarromin
            new LootItem(255, 1, 12),   //Harralander
            new LootItem(257, 1, 9),    //Ranarr weed
            new LootItem(259, 1, 6),    //Irit leaf
            new LootItem(261, 1, 5),    //Avantoe
            new LootItem(263, 1, 5),    //Kwuarm
            new LootItem(265, 1, 3),    //Cadantine
            new LootItem(2481, 1, 2),   //Lantadyme
            new LootItem(267, 1, 1)     //Dwarf weed
    );

    private static void open(Player player, Item herbBox) {
        int currentCharges = AttributeExtensions.getCharges(herbBox);
        int amtToAdd = currentCharges == -1 || currentCharges == 0 ? 10 : currentCharges;
        for (int i = 0; i < amtToAdd; i++)
            player.getBank().add(table.rollItem().getId(), 1);
        herbBox.remove();
        player.sendMessage(amtToAdd + " herb" + (amtToAdd == 1 ? " has" : "s have") + " been deposited into your bank.");
    }

    private static void check(Player player, Item herbBox) {
        int charges = AttributeExtensions.getCharges(herbBox);
        if(charges == 0)
            AttributeExtensions.setCharges(herbBox, 10);
        charges = AttributeExtensions.getCharges(herbBox);
        player.sendMessage("Your box has " + charges + " herb" + (charges == 1 ? "" : "s") + " left.");
    }

    private static final int HERB_BOX = 11738;

    static {
        ItemAction.registerInventory(HERB_BOX, "take-one", (player, item) -> {
            if (player.getInventory().isFull()) {
                player.sendMessage("You need at least one inventory space to take a herb from your box.");
                return;
            }
            if (AttributeExtensions.getCharges(item) == 0) {
                AttributeExtensions.setCharges(item, 10);
            } else {
                int herb = table.rollItem().getId();
                AttributeExtensions.deincrementCharges(item, 1);
                player.getInventory().add(herb, 1);
                player.sendMessage("You open the herb box and find " + ItemDef.get(herb).descriptiveName + ".");
                if (AttributeExtensions.getCharges(item) == 0) {
                    item.remove();
                }
            }
        });
        ItemAction.registerInventory(HERB_BOX, "bank-all", HerbBox::open);
        ItemAction.registerInventory(HERB_BOX, "check", HerbBox::check);
    }
}
