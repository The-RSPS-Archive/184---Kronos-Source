package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;

public class ConstructionWorker {

    static {
        NPCAction.register(3278, "talk-to", (player, npc) -> player.dialogue(new NPCDialogue(npc, "Please stand back, this area is under construction!").animate(588)));
    }
}
