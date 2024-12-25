package io.ruin.model.entity.npc.actions.guild.mining;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class Guard {

    private static final int GUARD = 6561;

    static {
        NPCAction.register(GUARD, "talk-to", (player, npc) -> {
            player.dialogue(new NPCDialogue(npc, "Halt! Are you here to sabotage the Motherlode Mine?").animate(614),
                    new OptionsDialogue(
                            new Option("What's the Motherload mine?", () -> player.dialogue(
                                    new PlayerDialogue("What's the motherload Mine?"),
                                    new NPCDialogue(npc, "Prospector Percy discovered unusual mineral veins in<br>this cave. Now he's built a machine to let us extract<br>useful ores.").animate(590),
                                    new NPCDialogue(npc, "Percy calls it the Motherlode. I don't know why, and<br>I'm not asking - that's between him and his mother.").animate(576),
                                    new NPCDialogue(npc, "Dwarves don't normally let humans claim areas of the<br>mine like this, but Percy's machine is really useful, so<br>we struck a deal.").animate(594)
                            )),
                            new Option("No, I'm not.", () -> player.dialogue(
                                    new PlayerDialogue("No, I'm not.").animate(614),
                                    new NPCDialogue(npc, "That's a relief. Have a nice day.")
                            )),
                            new Option("What would you do if I said 'yes'?", () -> player.dialogue(
                                    new PlayerDialogue("What would you do if I said 'yes'?"),
                                    new NPCDialogue(npc, "I'd ask you not to.").animate(575)
                            ))
                    ));
        });
    }
}
