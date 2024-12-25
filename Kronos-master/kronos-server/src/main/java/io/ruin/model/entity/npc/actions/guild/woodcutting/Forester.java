package io.ruin.model.entity.npc.actions.guild.woodcutting;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;

public class Forester {

    private static final int FORESTER = 7238;

    static {
        NPCAction.register(FORESTER, "talk-to", (player, npc) -> {
            if (Random.rollDie(2, 1))
                player.dialogue(new NPCDialogue(npc, "Nice weather we're having today.").animate(567));
            else
                player.dialogue(new NPCDialogue(npc, "It's so peaceful here, don't you agree?").animate(588));
        });
    }
}
