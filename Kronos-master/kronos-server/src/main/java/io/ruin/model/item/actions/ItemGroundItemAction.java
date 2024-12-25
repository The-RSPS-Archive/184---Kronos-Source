package io.ruin.model.item.actions;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.ground.GroundItem;

import java.util.HashMap;

public interface ItemGroundItemAction {

    void handle(Player player, Item item, GroundItem groundItem, int distance);

    /**
     * Register actions without a specific item.
     */

    static void register(int groundItemId, ItemGroundItemAction action) {
        ItemDef def = ItemDef.get(groundItemId);
        def.defaultItemGroundItemAction = action;
    }

    /**
     * Register actions with specific items.
     */

    static void register(int itemId, int groundItemId, ItemGroundItemAction action) {
        ItemDef def = ItemDef.get(groundItemId);
        if(def.itemGroundItemActions == null)
            def.itemGroundItemActions = new HashMap<>();
        def.itemGroundItemActions.put(itemId, action);
    }

    /**
     * Handle actions
     */

    static void handleAction(Player player, Item item, GroundItem groundItem, int distance) {
        ItemDef def = ItemDef.get(groundItem.id);
        if(def.itemGroundItemActions != null) {
            ItemGroundItemAction action = def.itemGroundItemActions.get(item.getId());
            if(action != null) {
                action.handle(player, item, groundItem, distance);
                return;
            }
        }
        if(def.defaultItemGroundItemAction != null) {
            def.defaultItemGroundItemAction.handle(player, item, groundItem, distance);
            return;
        }
        player.sendMessage("Nothing interesting happens.");
    }

}