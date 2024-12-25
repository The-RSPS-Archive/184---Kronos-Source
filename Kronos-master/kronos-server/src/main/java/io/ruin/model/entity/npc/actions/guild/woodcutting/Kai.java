package io.ruin.model.entity.npc.actions.guild.woodcutting;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class Kai {

    private static final int KAI = 7239;

    static {
        NPCAction.register(KAI, "talk-to", (player, npc) -> player.dialogue(
                new PlayerDialogue("Hello! What are you?"),
                new NPCDialogue(npc, "Hi there, my name is Kai and I'm the keeper of this<br>here ent habitat.").animate(568),
                new OptionsDialogue(
                        new Option("What exactly is an ent?", () -> player.dialogue(
                                new PlayerDialogue("What exactly is an ent?"),
                                new NPCDialogue(npc, "Good question! Ents are ancient beings that resemble trees, we believe they draw their energy directly from nature.").animate(569),
                                new OptionsDialogue(
                                        new Option("What did they come from?", () -> player.dialogue(
                                                new PlayerDialogue("Where did they come from?").animate(568),
                                                new NPCDialogue(npc, "Another good question! We're not sure exactly, we just know they're a very old race.").animate(568),
                                                new NPCDialogue(npc, "I have heard of similar creatures roaming a desolate part of the eastern continent, though.").animate(589),
                                                new OptionsDialogue(
                                                        new Option("Interesting... can I fight them?", () -> player.dialogue(
                                                                new PlayerDialogue("Interesting... can I fight them?"),
                                                                new NPCDialogue(npc, "Of course! There's no shortage of them residing within the dungeon, and they're a fantastic source of wood.").animate(606),
                                                                new PlayerDialogue("Thanks!").animate(567)
                                                        )),
                                                        new Option("Thanks for the information, see you around.", () -> player.dialogue(new PlayerDialogue("Thanks for the information, see you around.")))
                                                )
                                        )),
                                        new Option("That's nice, see you around.", () -> player.dialogue(new PlayerDialogue("That's nice, see you around.").animate(588)))
                                )
                        )),
                        new Option("That's nice, see you around.", () -> player.dialogue(new PlayerDialogue("That's nice, see you around.").animate(588)))
                )
        ));
    }
}
