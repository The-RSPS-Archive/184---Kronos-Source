package io.ruin.model.inter.actions;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;

public interface OnInterfaceAction extends InterfaceAction {

    void handleOnInterface(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId);

}