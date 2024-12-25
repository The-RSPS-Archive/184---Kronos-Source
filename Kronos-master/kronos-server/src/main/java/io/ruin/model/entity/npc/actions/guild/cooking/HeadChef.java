package io.ruin.model.entity.npc.actions.guild.cooking;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;

public class HeadChef {

    private static final int HEAD_CHEF = 2658;

    static {
        NPCAction.register(HEAD_CHEF, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Hello, welcome to the Cooking Guild. Only accomplished chefs and cooks" +
                        " are allowed in here. Feel free to use any of our facilities.")));
    }
}
