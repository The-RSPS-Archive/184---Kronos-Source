package io.ruin.model.inter.dialogue;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;

public class ItemDialogue implements Dialogue {

    private int itemId1, itemId2;

    private String message;

    private int lineHeight;

    private Runnable action;

    private boolean hideContinue;

    public ItemDialogue one(int itemId, String message) {
        this.itemId1 = itemId;
        this.itemId2 = -1;
        this.message = message;
        return this;
    }

    public ItemDialogue two(int itemId1, int itemId2, String message) {
        this.itemId1 = itemId1;
        this.itemId2 = itemId2;
        this.message = message;
        return this;
    }

    public ItemDialogue action(Runnable action) {
        this.action = action;
        return this;
    }

    public ItemDialogue lineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public ItemDialogue hideContinue() {
        this.hideContinue = true;
        return this;
    }

    @Override
    public void open(Player player) {
        if(itemId2 == -1) {
            player.openInterface(InterfaceType.CHATBOX, Interface.ONE_ITEM_DIALOGUE);
            player.getPacketSender().sendItem(Interface.ONE_ITEM_DIALOGUE, 1, itemId1, 10000);
            player.getPacketSender().sendString(Interface.ONE_ITEM_DIALOGUE, 2, message);
            player.getPacketSender().setTextStyle(Interface.ONE_ITEM_DIALOGUE, 2, 1, 1, lineHeight);
            if(!hideContinue) {
                player.getPacketSender().sendClientScript(2868, "s", "Click here to continue");
            }
        } else {
            player.openInterface(InterfaceType.CHATBOX, Interface.TWO_ITEM_DIALOGUE);
            player.getPacketSender().sendItem(Interface.TWO_ITEM_DIALOGUE, 1, itemId1, 10000);
            player.getPacketSender().sendItem(Interface.TWO_ITEM_DIALOGUE, 3, itemId2, 10000);
            player.getPacketSender().sendString(Interface.TWO_ITEM_DIALOGUE, 2, message);
            player.getPacketSender().setTextStyle(Interface.TWO_ITEM_DIALOGUE, 2, 1, 1, lineHeight);
            if(hideContinue)
                player.getPacketSender().setHidden(Interface.TWO_ITEM_DIALOGUE, 4, true);
            else
                player.getPacketSender().sendString(Interface.TWO_ITEM_DIALOGUE, 4, "Click here to continue");
        }
        if(action != null)
            action.run();
    }

    static {
        InterfaceHandler.register(Interface.ONE_ITEM_DIALOGUE, h -> h.actions[0] = (SimpleAction) Player::continueDialogue);
        InterfaceHandler.register(Interface.TWO_ITEM_DIALOGUE, h -> h.actions[4] = (SimpleAction) Player::continueDialogue);
    }

}
