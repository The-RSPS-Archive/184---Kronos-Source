package io.ruin.model.inter.dialogue;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;

public class ActionDialogue implements Dialogue {

    private Runnable action;

    public ActionDialogue(Runnable action) {
        this.action = action;
    }

    @Override
    public void open(Player player) {
        player.closeInterface(InterfaceType.CHATBOX);
        action.run();
    }

}