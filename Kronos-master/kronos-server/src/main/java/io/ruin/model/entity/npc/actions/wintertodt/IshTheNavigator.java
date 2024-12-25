package io.ruin.model.entity.npc.actions.wintertodt;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class IshTheNavigator {

    private static void aboutWintertodt(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about the Wintertodt."),
                new NPCDialogue(npc, "The Wintertodt terrorised Great Kourend many<br>centuries ago. It destroyed much of this northern land<br>" +
                        "over many years before it was finally driven back into<br>this prison."),
                new PlayerDialogue("Why did it attack Kourend?"),
                new NPCDialogue(npc, "No one knows for sure but I have a theory..."),
                new PlayerDialogue("Which is?"),
                new NPCDialogue(npc, "The Dark Altar. It felt the Altar's power and wanted to<br>consume it for itself. It wouldn't be the first time..."),
                new PlayerDialogue("Interesting..."),
                new OptionsDialogue(
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("What do you think of Pyromancers?", () -> aboutPyromancers(player, npc)),
                        new Option("I have to go.", () -> haveToGo(player, npc))
                )
        );
    }

    private static void aboutYourself(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about yourself."),
                new NPCDialogue(npc, "I'm Ish, one of the navigators of the Piscarilius House."),
                new PlayerDialogue("What brings a navigator up here?"),
                new NPCDialogue(npc, "Lady Piscarilius has sent me here to keep an eye on<br>the situation. She doesn't trust the other houses and" +
                        "<br>believes something sinister is going on here."),
                new PlayerDialogue("Like what?"),
                new NPCDialogue(npc, "I don't know, but I intend to find out."),
                new OptionsDialogue(
                        new Option("Tell me about the Wintertodt.", () -> aboutWintertodt(player, npc)),
                        new Option("What do you think of Pyromancers?", () -> aboutPyromancers(player, npc)),
                        new Option("I have to go.", () -> haveToGo(player, npc))
                )
        );
    }

    private static void aboutPyromancers(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("What do you think of Pyromancers?"),
                new NPCDialogue(npc, "I've heard stories about them and the things they have<br>done over the years. Be careful of them, they can't be<br>trusted."),
                new PlayerDialogue("You seem a bit paranoid."),
                new NPCDialogue(npc, "Better to be paranoid than a fool. Anyone with any<br>sense knows that the Pyromancers are trouble. They<br>say even Lord Arceuus doesn't trust them."),
                new OptionsDialogue(
                        new Option("Tell me about the Wintertodt.", () -> aboutWintertodt(player, npc)),
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("I have to go.", () -> haveToGo(player, npc))
                )
        );
    }

    private static void haveToGo(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("I have to go."),
                new NPCDialogue(npc, "Stay vigilant friend.")
        );
    }

    static {
        NPCAction.register(7378, "talk-to", (player, npc) -> {
            if(player.talkedToIgnisia) {
                player.dialogue(
                        new NPCDialogue(npc, "Be on your guard, friend."),
                        new OptionsDialogue(
                                new Option("Tell me about the Wintertodt.", () -> aboutWintertodt(player, npc)),
                                new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                                new Option("What do you think of Pyromancers?", () -> aboutPyromancers(player, npc)),
                                new Option("I have to go.", () -> haveToGo(player, npc))
                        )
                );

            } else {
                player.dialogue(
                        new NPCDialogue(npc, "Be vigilant friend."),
                        new PlayerDialogue("What do you mean?"),
                        new NPCDialogue(npc, "Ignisia will explain. Just be careful of what she tells<br>you.")
                );
            }
        });
    }
}
