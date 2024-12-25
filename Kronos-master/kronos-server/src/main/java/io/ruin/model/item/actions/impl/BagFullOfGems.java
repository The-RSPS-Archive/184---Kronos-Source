package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

public class BagFullOfGems {

    private static final int NOTED_UNCUT_SAPPHIRE = 1624;
    private static final int NOTED_UNCUT_EMERALD = 1622;
    private static final int NOTED_UNCUT_RUBY = 1620;
    private static final int NOTED_UNCUT_DIAMOND = 1618;
    private static final int NOTED_UNCUT_DRAGONSTONE = 1632;
    private static final int NOTED_UNCUT_ONYX = 6572;

    private static final LootTable chanceTable = new LootTable().addTable(1,
            new LootItem(NOTED_UNCUT_SAPPHIRE, 1, 2599),
            new LootItem(NOTED_UNCUT_EMERALD, 1, 1500),
            new LootItem(NOTED_UNCUT_RUBY, 1, 560),
            new LootItem(NOTED_UNCUT_DIAMOND, 1, 230),
            new LootItem(NOTED_UNCUT_DRAGONSTONE, 1, 110),
            new LootItem(NOTED_UNCUT_ONYX, 1, 1)
    );

    private static void open(Player player, Item item) {
        if (freeSlots(player) < 5) {
            player.sendMessage("You will need up to 5 free inventory spaces to open the bag.");
            return;
        }

        for (int i = 0; i < 40; i++)
            player.getInventory().add(chanceTable.rollItem());
        player.sendMessage("You open the bag and find 40 uncut gems.");
        item.remove();
    }

    private static int freeSlots(Player player) {
        int freeSlots = player.getInventory().getFreeSlots();
        if (player.getInventory().hasId(NOTED_UNCUT_SAPPHIRE))
            freeSlots++;
        if (player.getInventory().hasId(NOTED_UNCUT_EMERALD))
            freeSlots++;
        if (player.getInventory().hasId(NOTED_UNCUT_RUBY))
            freeSlots++;
        if (player.getInventory().hasId(NOTED_UNCUT_DIAMOND))
            freeSlots++;
        if (player.getInventory().hasId(NOTED_UNCUT_DRAGONSTONE))
            freeSlots++;
        return freeSlots;
    }

    static {
        ItemAction.registerInventory(19473, "open", BagFullOfGems::open);
        ItemAction.registerInventory(19473, "destroy", (player, item) -> player.dialogue(
                new YesNoDialogue("Are you sure you want to do this?", "The Bag full of gems will be destroyed.", item, () -> {
                    item.remove();
                    for (Item i : player.getLootingBag().getItems()) {
                        if (i != null)
                            i.remove();
                    }
                })
        ));
    }
}
