package io.ruin.model.entity.npc;

import io.ruin.cache.NPCDef;
import io.ruin.model.entity.player.Player;

import java.util.Arrays;
import java.util.function.Consumer;

public interface NPCAction {

    void handle(Player player, NPC npc);

    /**
     * Default - (Registers the given action for every npc in the game with the given npcId)
     */

    static void register(int npcId, int option, NPCAction action) {
        NPCDef def = NPCDef.get(npcId);
        if(def.defaultActions == null)
            def.defaultActions = new NPCAction[5];
        def.defaultActions[option - 1] = action;
    }

    static boolean register(int npcId, String optionName, NPCAction action) {
        int option = NPCDef.get(npcId).getOption(optionName);
        if(option == -1)
            return false;
        register(npcId, option, action);
        return true;
    }

    static void register(int npcId, Consumer<NPCAction[]> actionsConsumer) {
        NPCAction[] actions = new NPCAction[6];
        actionsConsumer.accept(actions);
        NPCDef.get(npcId).defaultActions = Arrays.copyOfRange(actions, 1, actions.length);
    }

    /**
     * Global - (Registers the given action for several npcs in the game with the given name)
     */

    static void register(String npcName, int option, NPCAction action) {
        NPCDef.forEach(def -> {
            if(def.name.equalsIgnoreCase(npcName))
                register(def.id, option, action);
        });
    }

    static void register(String npcName, String optionName, NPCAction action) {
        NPCDef.forEach(def -> {
            if(def.name.equalsIgnoreCase(npcName))
                register(def.id, optionName, action);
        });
    }

    static void register(String npcName, Consumer<NPCAction[]> actionsConsumer) {
        NPCDef.forEach(def -> {
            if(def.name.equalsIgnoreCase(npcName))
                register(def.id, actionsConsumer);
        });
    }

    /**
     * Specific - (Registers the given action for the given npc)
     */

    static void register(NPC npc, int option, NPCAction action) {
        if(npc.actions == null)
            npc.actions = new NPCAction[5];
        npc.actions[option - 1] = action;
    }

    static boolean register(NPC npc, String optionName, NPCAction action) {
        int option = npc.getDef().getOption(optionName);
        if(option == -1)
            return false;
        register(npc, option, action);
        return true;
    }

    static void register(NPC npc, Consumer<NPCAction[]> actionsConsumer) {
        NPCAction[] actions = new NPCAction[6];
        actionsConsumer.accept(actions);
        npc.actions = Arrays.copyOfRange(actions, 1, actions.length);
    }

}
