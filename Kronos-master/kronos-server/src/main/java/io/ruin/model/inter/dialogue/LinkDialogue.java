package io.ruin.model.inter.dialogue;

import io.ruin.model.World;
import io.ruin.model.entity.npc.actions.edgeville.CreditManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;

public class LinkDialogue implements Dialogue {

    private int itemId1, itemId2;

    private String message, description;

    private Runnable action;

    public LinkDialogue one(int itemId, String message, String description) {
        this.itemId1 = itemId;
        this.itemId2 = -1;
        this.message = message;
        this.description = description;
        return this;
    }

    public LinkDialogue two(int itemId1, int itemId2, String message, String description) {
        this.itemId1 = itemId1;
        this.itemId2 = itemId2;
        this.message = message;
        this.description = description;
        return this;
    }

    public LinkDialogue action(Runnable action) {
        this.action = action;
        return this;
    }

    @Override
    public void open(Player player) {
        player.openInterface(InterfaceType.CHATBOX, Interface.LINK_DIALOGUE);
        player.getPacketSender().sendItem(Interface.LINK_DIALOGUE, 1, itemId1, 10000);
        if(itemId2 != -1)
            player.getPacketSender().sendItem(Interface.LINK_DIALOGUE, 2, itemId2, 10000);
        player.getPacketSender().sendString(Interface.LINK_DIALOGUE, 3, message);
        player.getPacketSender().sendString(Interface.LINK_DIALOGUE, 4, description);
        player.getPacketSender().setHidden(Interface.LINK_DIALOGUE, 9, true);
        Config.CHATBOX_INTERFACE_USE_FULL_FRAME.set(player, 1);
        if (action != null)
            action.run();
    }

    static {
        InterfaceHandler.register(Interface.LINK_DIALOGUE, h -> {
            h.actions[5] = (SimpleAction) Player::continueDialogue;
            h.actions[8] = (SimpleAction) player -> {
                player.dialogue(
                        new OptionsDialogue("Would you like to open our store?",
                                new Option("Yes", () -> player.openUrl(World.type.getWorldName() + " Store", CreditManager.STORE_URL)),
                                new Option("No", player::closeDialogue)
                        )
                );
            };
            h.actions[6] = (SimpleAction) player -> {
                player.dialogue(
                        new OptionsDialogue("Are you sure you'd like to vote now?",
                                new Option("Yes", () -> player.openUrl(World.type.getWorldName() + " Voting", World.type.getWebsiteUrl() + "/voting")),
                                new Option("No", player::closeDialogue)
                        )
                );
            };
            h.actions[7] = (SimpleAction) player -> {
                player.dialogue(
                        new OptionsDialogue("Are you sure you'd like to view the Hiscores??",
                                new Option("Yes", () -> player.openUrl(World.type.getWorldName() + " Hiscores", World.type.getWebsiteUrl() + "/hiscores")),
                                new Option("No", player::closeDialogue)
                        )
                );
            };
            h.closedAction = (player, integer) -> Config.CHATBOX_INTERFACE_USE_FULL_FRAME.set(player, 0);
        });

    }

}
