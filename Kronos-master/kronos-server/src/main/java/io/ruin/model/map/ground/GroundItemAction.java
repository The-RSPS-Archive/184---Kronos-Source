package io.ruin.model.map.ground;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;

import java.util.Arrays;
import java.util.function.Consumer;

public interface GroundItemAction {

    void handle(Player player, GroundItem groundItem, int distance);

    static void register(int itemId, int option, GroundItemAction action) {
        ItemDef def = ItemDef.get(itemId);
        if(def.groundActions == null)
            def.groundActions = new GroundItemAction[5];
        def.groundActions[option - 1] = action;
    }

    static boolean register(int itemId, String optionName, GroundItemAction action) {
        int option = ItemDef.get(itemId).getGroundOption(optionName);
        if(option == -1)
            return false;
        register(itemId, option, action);
        return true;
    }

    static void register(int itemId, Consumer<GroundItemAction[]> actionsConsumer) {
        GroundItemAction[] actions = new GroundItemAction[5 + 1];
        actionsConsumer.accept(actions);
        ItemDef.get(itemId).groundActions = Arrays.copyOfRange(actions, 1, actions.length);
    }

}