package io.ruin.model.item.actions;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import java.util.ArrayList;

public interface ItemItemAction {

    void handle(Player player, Item primary, Item secondary);

    /**
     * Default register method - For private use only.
     */

    default void register(int id, boolean primary) {
        ItemDef def = ItemDef.get(id);
        ArrayList<ItemItemAction> list;
        if(primary) {
            if(def.primaryItemItemActions == null)
                def.primaryItemItemActions = new ArrayList<>();
            list = def.primaryItemItemActions;
        } else {
            if(def.secondaryItemItemActions == null)
                def.secondaryItemItemActions = new ArrayList<>();
            list = def.secondaryItemItemActions;
        }
        list.add(this);
        list.trimToSize(); //only okay because it's on startup ;)
    }

    /**
     * Registers actions without a specific secondary item.
     */

    static void register(int primaryId, ItemItemAction action) {
        ItemDef def = ItemDef.get(primaryId);
        def.defaultPrimaryItemItemAction = action;
    }

    /**
     * Registers actions with a specific primary & secondary item.
     */

    static void register(int primaryId, int secondaryId, ItemItemAction action) {
        action.register(primaryId, true);
        action.register(secondaryId, false);
    }

    /**
     * Handle actions
     */

    static void handleAction(Player player, Item from, Item to) {
        ItemDef fromDef = from.getDef();
        ItemDef toDef = to.getDef();
        ItemItemAction action;
        if((action = match(fromDef.primaryItemItemActions, toDef.secondaryItemItemActions)) != null) {
            action.handle(player, from, to);
            return;
        }
        if((action = match(toDef.primaryItemItemActions, fromDef.secondaryItemItemActions)) != null) {
            action.handle(player, to, from);
            return;
        }
        if((action = fromDef.defaultPrimaryItemItemAction) != null) {
            action.handle(player, from, to);
            return;
        }
        if((action = toDef.defaultPrimaryItemItemAction) != null) {
            action.handle(player, to, from);
            return;
        }
        player.sendMessage("Nothing interesting happens.");
    }

    static ItemItemAction match(ArrayList<ItemItemAction> primaryActions, ArrayList<ItemItemAction> secondaryActions) {
        if(primaryActions == null || secondaryActions == null)
            return null;
        for(ItemItemAction primary : primaryActions) {
            for(ItemItemAction secondary : secondaryActions) {
                if(primary == secondary)
                    return primary;
            }
        }
        return null;
    }

}
