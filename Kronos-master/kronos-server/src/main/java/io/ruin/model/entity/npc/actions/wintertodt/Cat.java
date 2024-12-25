package io.ruin.model.entity.npc.actions.wintertodt;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;

public class Cat {

    static {
        NPCAction.register(7380, "talk-to", (player, npc) -> player.dialogue(new NPCDialogue(npc, "Meow.")));
    }
}
