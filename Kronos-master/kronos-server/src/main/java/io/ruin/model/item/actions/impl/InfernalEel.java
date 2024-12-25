package io.ruin.model.item.actions.impl;

import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.skills.Tool;

public class InfernalEel {

    private static final int INFERNAL_EEL = 21293;

    public static final LootTable chanceTable = new LootTable().addTable(1,
            new LootItem(6529, 10, 20, 16), // tokkul
            new LootItem(9194, 1, 3),       // onyx bolt tips
            new LootItem(11994, 1, 5, 1)    // lava scale shard
    );

    static {
        ItemItemAction.register(INFERNAL_EEL, Tool.HAMMER, (player, primary, secondary) -> {
            Item loot = chanceTable.rollItem();
            player.startEvent(event -> {
                player.lock();
                player.animate(7553);
                player.getInventory().remove(primary.getId(), 1);
                player.getInventory().add(loot);
                player.sendFilteredMessage("You break open the infernal eel and extract some " + loot.getDef().name + ".");
                event.delay(1);
                player.unlock();
            });
        });
    }
}
