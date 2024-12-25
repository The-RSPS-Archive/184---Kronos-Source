package io.ruin.model.entity.npc.actions.guild.crafting;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

public class MasterCrafter {

    static {
        NPCAction.register(5810, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Hello and welcome to the Crafting Guild. Accomplished crafters" +
                        "from all over the land come here to use our top notch workshops.")
        ));
        NPCAction.register(5811, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Hello and welcome to the Crafting Guild. Accomplished crafters" +
                        "from all over the land come here to use our top notch workshops.")
        ));
        NPCAction.register(5812, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Yeah?"),
                new PlayerDialogue("Hello."),
                new NPCDialogue(npc, "Whassup?"),
                new PlayerDialogue("So... are you here to give crafting tips?"),
                new NPCDialogue(npc, "Dude, do I look like I wanna talk to you?"),
                new PlayerDialogue("I suppose not."),
                new NPCDialogue(npc, "Right on!")
        ));
    }
}
