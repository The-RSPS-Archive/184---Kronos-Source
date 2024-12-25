package io.ruin.model.item.actions.impl.boxes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class SantaOutfitBox {

    private static final int SANTA_OUTFIT_BOX = 11918;
    private static final int ANTI_SANTA_OUTFIT_BOX = 12897;

    private static void openBox(Player player, Item item, int startId) {
        if (player.getInventory().getFreeSlots() < 4) {
            player.sendMessage("You need at least 4 free inventory slots to open this box.");
            return;
        }

        player.startEvent(event -> {
            player.lock();
            item.remove();
            for (int i = 0; i < 5; i++)
                player.getInventory().add(startId + i, 1);
            player.sendMessage("You take out the contents of the Santa Outfit Box.");
            player.unlock();
        });
    }

    static {
        ItemAction.registerInventory(SANTA_OUTFIT_BOX, "open", (player, item) -> openBox(player, item, 12887));
        ItemAction.registerInventory(ANTI_SANTA_OUTFIT_BOX, "open", (player, item) -> openBox(player, item, 12892));
    }

}
