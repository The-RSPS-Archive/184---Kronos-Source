package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.NPCDialogue;

public class Paul { //TODO

    private final static int PAUL = 317;

    static {
        NPCAction.register(PAUL, "Talk-to", (player, npc) -> player.dialogue(new NPCDialogue(PAUL, "I'm the ultimate ironman NPC... options are coming!")));
        SpawnListener.register(PAUL, npc -> npc.skipReachCheck = p -> p.equals(3083, 3510));
    }

}
