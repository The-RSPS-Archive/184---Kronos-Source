package io.ruin.model.inter.actions;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;

public interface DefaultAction extends InterfaceAction {

    void handleClick(Player player, int option, int slot, int itemId);

}