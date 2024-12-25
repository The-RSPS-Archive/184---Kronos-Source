package io.ruin.model.entity.npc.actions.guild.woodcutting;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class Murfet {

    private static final int MURFET = 7237;

    static {
        NPCAction.register(MURFET, "talk-to", (player, npc) -> player.dialogue(
                new PlayerDialogue("Hello.").animate(567),
                new NPCDialogue(npc, "Hello, how can I help you?"),
                new OptionsDialogue(
                        new Option("What's in the cave?", () -> player.dialogue(
                                new PlayerDialogue("What's in the cave?"),
                                new NPCDialogue(npc, "Creatures known as ents inhabit the depths of the hills, rumour has it they reside there due to the presence of" +
                                        " the ancient redwood trees on the hills.").animate(590),
                                new NPCDialogue(npc, "If you're as skilled a fighter as you are a woodcutter, you may enter the caves. You should find Kai once" +
                                        " you are inside, he can give you more information.").animate(590)
                        )),
                        new Option("I was just passing through.", () -> player.dialogue(new PlayerDialogue("I was just passing through.").animate(588)))
                )
        ));
    }
}
