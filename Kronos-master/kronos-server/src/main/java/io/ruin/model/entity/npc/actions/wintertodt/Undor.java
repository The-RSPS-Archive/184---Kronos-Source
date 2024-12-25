package io.ruin.model.entity.npc.actions.wintertodt;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class Undor {

    private static void aboutWintertodt(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tel me about the Wintertodt."),
                new NPCDialogue(npc, "They say it's some kind of powerful fire spirit."),
                new PlayerDialogue("Don't you mean ice?").animate(575),
                new NPCDialogue(npc, "Aye that as well."),
                new NPCDialogue(npc, "What it is doesn't bother me, just as long as we keep it<br>behind them doors."),
                new OptionsDialogue(
                        new Option("Tell me about the Doors of Dinh.", () -> doorOfDinh(player, npc)),
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("Goodbye.", () -> goodbye(player, npc))
                )
        );
    }

    private static void doorOfDinh(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about the Doors of Dinh."),
                new NPCDialogue(npc, "The Doors were built by Dinh to contain the<br>Wintertodt. " +
                        "This was over a thousand years ago and<br>they still stand today."),
                new PlayerDialogue("Who was Dinh?"),
                new NPCDialogue(npc, "Dinh was the greatest smith that the world has ever<br>seen. " +
                        "I am the finest smith around today and even I<br>cannot match his abilities."),
                new OptionsDialogue(
                        new Option("Tell me about the Wintertodt.", () -> aboutWintertodt(player, npc)),
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("Goodbye.", () -> goodbye(player, npc))
                )
        );
    }

    private static void aboutYourself(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about yourself."),
                new NPCDialogue(npc, "The name's Undor. You'll struggle to find a dwarf in<br>this land who knows more about metalwork than me."),
                new NPCDialogue(npc, "One day I hope to be as good as the legendary Dinh<br>himself."),
                new PlayerDialogue("What are you doing here?"),
                new NPCDialogue(npc, "They say the Doors are failing and the Wintertodt is<br>trying to escape. I'm here to prove to them that the<br>Doors are fine."),
                new PlayerDialogue("And are they?"),
                new NPCDialogue(npc, "Of course they are! They were made by Dinh himself.").animate(614),
                new OptionsDialogue(
                        new Option("Tell me about the Wintertodt.", () -> aboutWintertodt(player, npc)),
                        new Option("Tell me about the Doors of Dinh.", () -> doorOfDinh(player, npc)),
                        new Option("Goodbye.", () -> goodbye(player, npc))
                )
        );
    }

    private static void goodbye(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Goodbye."),
                new NPCDialogue(npc, "Until next time.")

        );
    }

    static {
        NPCAction.register(7375, "talk-to", (player, npc) -> {
            if (player.talkedToIgnisia) {
                player.dialogue(
                        new NPCDialogue(npc, "Hello there."),
                        new OptionsDialogue(
                                new Option("Tell me about the Wintertodt.", () -> aboutWintertodt(player, npc)),
                                new Option("TTell me about the Doors of Dinh.", () -> doorOfDinh(player, npc)),
                                new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                                new Option("Goodbye.", () -> goodbye(player, npc))
                        )
                );
            } else {
                player.dialogue(
                        new PlayerDialogue("Hello."),
                        new NPCDialogue(npc, "Well hello there."),
                        new PlayerDialogue("What's all this then?"),
                        new NPCDialogue(npc, "Talk to that fiery lass, she'll sort you out.")
                );
            }
        });
    }
}
