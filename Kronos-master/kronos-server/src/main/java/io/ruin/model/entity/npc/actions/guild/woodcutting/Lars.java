package io.ruin.model.entity.npc.actions.guild.woodcutting;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class Lars {

    private static final int LARS = 7236;

    public static void aboutGuild(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about the guild."),
                new NPCDialogue(npc, "I founded the woodcutting guild when I retired from the Shayzien military. I spotted the ancient " +
                        "redwood trees in the hills many years ago from the Shayzien camp, and decided to construct the guild around them.").animate(570),
                new NPCDialogue(npc, "You can find them to the West, just follow the path.").animate(567),
                new NPCDialogue(npc, "However, if you're skilled in combat, or simply afraid of<br>heights, the ent cavern may be more suited for you.<br>" +
                        "The ents dwell deep beneath the ancient redwoods, and<br>can be accessed through the cave to the North-West.").animate(591),
                new NPCDialogue(npc, "Is there anything else I can help you with?"),
                new OptionsDialogue(
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("That's all, thanks.", () -> player.dialogue(new PlayerDialogue("That's all, thanks.").animate(567)))
                )
        );
    }

    public static void aboutYourself(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about yourself."),
                new NPCDialogue(npc, "My name is Lars, and I am the founder of the woodcutting guild. I originally grew up in a Shayzien camp, and was raised on the battlefield.").animate(569),
                new NPCDialogue(npc, "As I grew older, I found peace in the Hosidius house,<br>and the joys of skilled labour. I turned in my battleaxe<br>for a hatchet, and set up a guild for those also talented<br>in the skill of woodcutting.").animate(570),
                new NPCDialogue(npc, "Is there anything else I can help you with?"),
                new OptionsDialogue(
                        new Option("Tell me about the guild.", () -> aboutGuild(player, npc)),
                        new Option("That's all, thanks.", () -> player.dialogue(new PlayerDialogue("That's all, thanks.").animate(567)))
                )
        );
    }

    static {
        NPCAction.register(LARS, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Welcome to the woodcutting guild. How may I help you?").animate(568),
                new OptionsDialogue(
                        new Option("Tell me about the guild.", () -> aboutGuild(player, npc)),
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("I'm just passing by.", () -> player.dialogue(new PlayerDialogue("I'm just passing by.").animate(567)))
                )
        ));
    }
}
