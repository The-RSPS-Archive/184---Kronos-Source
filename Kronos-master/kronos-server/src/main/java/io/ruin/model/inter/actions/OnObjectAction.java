package io.ruin.model.inter.actions;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.map.object.GameObject;

public interface OnObjectAction extends InterfaceAction {

    void handleOnObject(Player player, int slot, int itemId, GameObject obj);

}