package io.ruin.model.entity.npc.actions.guild.mining;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class Utrec {

    private static final int UTREC = 7720;

    static {
        NPCAction.register(UTREC, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "All this mining is tiring work.").animate(614),
                new PlayerDialogue("But you're not even mining at the moment."),
                new NPCDialogue(npc, "Hey! I've been mining all day thank you very much! I'm allowed a break ain't I?").animate(614),
                new PlayerDialogue("Yes, I suppose."),
                new NPCDialogue(npc, "I should think so. Anyway, did you need anything?"),
                new OptionsDialogue(
                        new Option("What are these rocks?", () -> player.dialogue(
                                new PlayerDialogue("What are these rocks?"),
                                new NPCDialogue(npc, "Rocks? They're not rocks, they're minerals!").animate(614),
                                new PlayerDialogue("Oh okay. What are they tough?"),
                                new NPCDialogue(npc, "Amethyst! We use it a lot back in Keldagrim but this is the only place we've managed to find it."),
                                new PlayerDialogue("What can I use it for?"),
                                new NPCDialogue(npc, "We've found it quite good for ranging ammunition, something you adventuring lot might like."),
                                new NPCDialogue(npc, "We use it a lot for Construction back in Keldagrim as well."),
                                new PlayerDialogue("Awesome! Does that mean I can use it in my house?"),
                                new NPCDialogue(npc, "No."),
                                new PlayerDialogue("Why not?"),
                                new NPCDialogue(npc, "Because I said so that's why! Even the best human builders wouldn't know how to build with this stuff.").animate(614),
                                new PlayerDialogue("Awww.")
                        )),
                        new Option("No thanks.", () -> player.dialogue(
                                new PlayerDialogue("No thanks."),
                                new NPCDialogue(npc, "Well be on your way then. I've got mining to do!" )
                        ))
                )
        ));
    }
}
