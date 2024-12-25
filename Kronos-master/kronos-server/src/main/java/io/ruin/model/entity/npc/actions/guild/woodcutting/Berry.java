package io.ruin.model.entity.npc.actions.guild.woodcutting;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

public class Berry {

    private static final int BERRY = 7235;

    static {
        NPCAction.register(BERRY, "talk-to", (player, npc) -> player.dialogue(
                new PlayerDialogue("Hello.").animate(567),
                new NPCDialogue(npc, "Good day, adventurer. If you have any questions, I'm<br>sure Lars will be more than " +
                        "happy to answer them. He's<br>in the building to the south.").animate(569),
                new NPCDialogue(npc, "Also don't forget to visit my sister's axe shop. She works<br>in the same building as Lars.").animate(568),
                new PlayerDialogue("Thanks!").animate(567)
        ));
    }

}
