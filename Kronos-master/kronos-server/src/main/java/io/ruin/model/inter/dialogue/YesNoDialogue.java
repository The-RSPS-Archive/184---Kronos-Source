package io.ruin.model.inter.dialogue;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.item.Item;

public class YesNoDialogue implements Dialogue {

    private int itemId, itemAmount;

    private String title, message;

    private String customItemName;

    private Runnable yesAction;

    public YesNoDialogue(String title, String message, Item item, Runnable yesAction) {
        this(title, message, item.getId(), item.getAmount(), null, yesAction);
    }

    public YesNoDialogue(String title, String message, Item item, String customItemName, Runnable yesAction) {
        this(title, message, item.getId(), item.getAmount(), customItemName, yesAction);
    }

    public YesNoDialogue(String title, String message, int itemId, int itemAmount, Runnable yesAction) {
        this(title, message, itemId, itemAmount, null, yesAction);
    }

    public YesNoDialogue(String title, String message, int itemId, int itemAmount, String customItemName, Runnable yesAction) {
        this.title = title;
        this.message = message;
        this.itemId = itemId;
        this.itemAmount = itemAmount;
        this.customItemName = customItemName;
        this.yesAction = yesAction;
    }

    @Override
    public void open(Player player) {
        player.yesNoDialogue = this;
        player.openInterface(InterfaceType.CHATBOX, Interface.YES_NO_DIALOGUE);
        player.getPacketSender().sendClientScript(814, "oiss", itemId, itemAmount, title, message);
        if(customItemName != null)
            player.getPacketSender().sendString(Interface.YES_NO_DIALOGUE, 6, customItemName);
        player.getPacketSender().sendAccessMask(584, 0, 0, 1, 1);
    }

    static {
        InterfaceHandler.register(Interface.YES_NO_DIALOGUE, h -> h.actions[0] = (SlotAction) (p, slot) -> {
            if(slot == 1) {
                p.yesNoDialogue.yesAction.run();
                if(p.yesNoDialogue == null || p.yesNoDialogue != p.lastDialogue)
                    return;
            }
            p.closeDialogue();
        });
    }

}