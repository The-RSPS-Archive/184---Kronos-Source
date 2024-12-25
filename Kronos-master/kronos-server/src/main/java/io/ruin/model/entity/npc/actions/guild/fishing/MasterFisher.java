package io.ruin.model.entity.npc.actions.guild.fishing;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.stat.StatType;

public class MasterFisher {

    private static final int MASTER_FISHER = 2913;

    static {
        NPCAction.register(MASTER_FISHER, "talk-to", (player, npc) -> {
            int fishingLevel = player.getStats().get(StatType.Fishing).currentLevel;

            if(fishingLevel < 68) {
                player.dialogue(new NPCDialogue(npc,"Hello, I'm afraid only the top fishers are allowed to use our premier fishing facilities."));
            } else {
                player.dialogue(new NPCDialogue(npc, "Hello, welcome to the fishing guild. Please feel fre to make use of any of our facilities."));
            }
        });
    }
}
