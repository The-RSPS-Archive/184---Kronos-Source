package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.NPCDialogue;

public class Juan { //TODO

    private final static int JUAN = 3369;

    static {
        NPCAction.register(JUAN, "Talk-to", (player, npc) -> player.dialogue(new NPCDialogue(JUAN, "I'm the hardcore ironman NPC... options are coming!")));
        SpawnListener.register(JUAN, npc -> npc.skipReachCheck = p -> p.equals(3083, 3509));
    }

}
