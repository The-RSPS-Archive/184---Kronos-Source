package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

public class CaptainRimor {

    static {
        NPCAction.register(7595, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "Beware the Chamber of Xeric, adventurer."),
                    new NPCDialogue(npc, "These dungeons and the perils within them are unique. You will encounter " +
                            "both dangerous and mysterious creatures here."),
                    new NPCDialogue(npc, "Remember that you may be kicked out at any time - any items dropped on the " +
                            "ground will be lost inside."),
                    new NPCDialogue(npc, "Do not allow your curiosity get the better of you."),
                    new PlayerDialogue("Thanks for the warning.")
            );
        });
    }
}
