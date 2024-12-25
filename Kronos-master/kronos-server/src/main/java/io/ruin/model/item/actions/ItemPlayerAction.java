package io.ruin.model.item.actions;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import java.util.HashMap;

public interface ItemPlayerAction {

    HashMap<Integer, ItemPlayerAction> defaultActions = new HashMap<>();

    void handle(Player player, Item item, Player other);

    static void register(int itemId, ItemPlayerAction action) {
        defaultActions.put(itemId, action);
    }

    static void handleAction(Player player, Item item, Player other) {
        ItemPlayerAction action = defaultActions.get(item.getId());
        if(action != null) {
            action.handle(player, item, other);
            return;
        }
        player.sendMessage("Nothing interesting happens.");
    }

}