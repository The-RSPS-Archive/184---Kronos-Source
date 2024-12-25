package io.ruin.model.item.actions;

import io.ruin.cache.NPCDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import java.util.HashMap;

public interface ItemNPCAction {

    void handle(Player player, Item item, NPC npc);

    /**
     * Register actions without a specific item.
     */

    static void register(int npcId, ItemNPCAction action) {
        NPCDef npcDef = NPCDef.get(npcId);
        npcDef.defaultItemAction = action;
    }

    static void register(String npcName, ItemNPCAction action) {
        NPCDef.forEach(npcDef -> {
            if(npcDef.name.equalsIgnoreCase(npcName))
                register(npcDef.id, action);
        });
    }

    static void register(NPC npc, ItemNPCAction action) {
        npc.defaultItemAction = action;
    }

    /**
     * Register actions with specific items.
     */

    static void register(int itemId, int npcId, ItemNPCAction action) {
        NPCDef npcDef = NPCDef.get(npcId);
        if(npcDef.itemActions == null)
            npcDef.itemActions = new HashMap<>();
        npcDef.itemActions.put(itemId, action);
    }

    static void register(int itemId, String npcName, ItemNPCAction action) {
        NPCDef.forEach(npcDef -> {
            if(npcDef.name.equalsIgnoreCase(npcName))
                register(itemId, npcDef.id, action);
        });
    }

    static void register(int itemId, NPC npc, ItemNPCAction action) {
        if(npc.itemActions == null)
            npc.itemActions = new HashMap<>();
        npc.itemActions.put(itemId, action);
    }

    /**
     * Handle actions
     */

    static void handleAction(Player player, Item item, NPC npc) {
        /**
         * Actions unique to the given npc.
         */
        if(npc.itemActions != null) {
            ItemNPCAction action = npc.itemActions.get(item.getId());
            if(action != null) {
                action.handle(player, item, npc);
                return;
            }
        }
        if(npc.defaultItemAction != null) {
            npc.defaultItemAction.handle(player, item, npc);
            return;
        }
        /**
         * Actions shared by the given npc.
         */
        NPCDef def = npc.getDef();
        if(def.itemActions != null) {
            ItemNPCAction action = def.itemActions.get(item.getId());
            if(action != null) {
                action.handle(player, item, npc);
                return;
            }
        }
        if(def.defaultItemAction != null) {
            def.defaultItemAction.handle(player, item, npc);
            return;
        }
        /**
         * No action, hehe.
         */
        player.sendMessage("Nothing interesting happens.");
    }

}