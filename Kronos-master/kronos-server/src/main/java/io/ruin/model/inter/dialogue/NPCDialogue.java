package io.ruin.model.inter.dialogue;

import io.ruin.cache.NPCDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NPCDialogue implements Dialogue {

    private NPCDef npcDef;

    private String message;

    private Runnable onDialogueOpened;

    private int animationId = 554;

    private int lineHeight;

    private boolean hideContinue;

    public NPCDialogue(int npcId, String message) {
        this(NPCDef.get(npcId), message);
    }

    public NPCDialogue(NPC npc, String message) {
        this(npc.getDef(), message);
    }

    private NPCDialogue(NPCDef npcDef, String message) {
        this.npcDef = npcDef;
        this.message = message;
    }

    public NPCDialogue onDialogueOpened(Runnable openDialogueOpened) {
        this.onDialogueOpened = openDialogueOpened;
        return this;
    }

    public NPCDialogue animate(int animationId) {
        this.animationId = animationId;
        return this;
    }

    public NPCDialogue lineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public NPCDialogue hideContinue() {
        this.hideContinue = true;
        return this;
    }

    @Override
    public void open(Player player) {
        player.openInterface(InterfaceType.CHATBOX, Interface.NPC_DIALOGUE);
        player.getPacketSender().sendNpcHead(Interface.NPC_DIALOGUE, 1, npcDef.id);
        player.getPacketSender().animateInterface(Interface.NPC_DIALOGUE, 1, animationId);
        player.getPacketSender().sendString(Interface.NPC_DIALOGUE, 2, npcDef.name);
        player.getPacketSender().sendString(Interface.NPC_DIALOGUE, 4, message);
        player.getPacketSender().setTextStyle(Interface.NPC_DIALOGUE, 4, 1, 1, lineHeight);
        player.getPacketSender().sendAccessMask(Interface.NPC_DIALOGUE, 3, -1, -1, 1);
        if (hideContinue)
            player.getPacketSender().setHidden(Interface.NPC_DIALOGUE, 3, true);
        else
            player.getPacketSender().sendString(Interface.NPC_DIALOGUE, 3, "Click here to continue");
        if (onDialogueOpened != null)
            onDialogueOpened.run();
    }

    static {
        InterfaceHandler.register(Interface.NPC_DIALOGUE, h -> h.actions[3] = (SimpleAction) player -> {
            player.continueDialogue();
            player.onDialogueContinued();
        });
    }

}
