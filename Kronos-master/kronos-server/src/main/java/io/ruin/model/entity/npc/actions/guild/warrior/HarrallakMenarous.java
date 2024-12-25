package io.ruin.model.entity.npc.actions.guild.warrior;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

public class HarrallakMenarous {

    static {
        NPCAction.register(2458, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "Wecome to my humble guild " + player.getName() + "."),
                    new PlayerDialogue("Thank you!")
            );
        });
    }
}
