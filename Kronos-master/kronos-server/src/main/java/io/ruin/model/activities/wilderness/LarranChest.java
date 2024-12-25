package io.ruin.model.activities.wilderness;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

/**
 * @author Adam Ali ("Kal-El") https://www.rune-server.ee/members/kal+el/
 */
public class LarranChest {

    private static final int CHEST = 34832;

    private static final int LARRAN_KEY_ID = 23490;

    static {
        ObjectAction.register(CHEST, 1, ((player, obj) -> openChest(player))); //TODO: Add options to object def
    }

    private static void openChest(Player player) {
        Item larrenKey = player.getInventory().findItem(LARRAN_KEY_ID);

        if (larrenKey == null) {
            player.sendFilteredMessage("You need a key to open this chest");
            return;
        }

        player.startEvent(event -> {
            player.lock();
            player.sendFilteredMessage("You unlock the chest with your key");
            Broadcast.GLOBAL.sendNews("The Larran's Chest has been opened!");
            larrenKey.remove(1);
            player.animate(536);
            Item loot = new LarranChestLoot().rollItem();
            player.getInventory().addOrDrop(loot);
            event.delay(1);
            player.unlock();
        });
    }
}
