package io.ruin.model.inter;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;

public interface InterfaceAction {

    default void handleClick(Player player, int option, int slot, int itemId) {
    }

    default void handleDrag(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
    }

    default void handleOnInterface(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
        player.sendMessage("Nothing interesting happens.");
    }

    default void handleOnGroundItem(Player player, int slot, int itemId, GroundItem groundItem, int distance) {
        player.sendMessage("Nothing interesting happens.");
    }

    default void handleOnObject(Player player, int slot, int itemId, GameObject obj) {
        player.sendMessage("Nothing interesting happens.");
    }

    default void handleOnEntity(Player player, Entity entity, int slot, int itemId) {
        player.sendMessage("Nothing interesting happens.");
    }

}