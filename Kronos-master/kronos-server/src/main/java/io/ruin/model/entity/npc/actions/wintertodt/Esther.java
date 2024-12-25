package io.ruin.model.entity.npc.actions.wintertodt;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class Esther {

    private static void aboutWintertodt(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about the Wintertodt."),
                new NPCDialogue(npc, "Some would say that ice preserves while fire destroys,<br>but no amount of fire is destroying the Wintertodt," +
                        "<br>that's a cold that will freeze the air in your lungs."),
                new NPCDialogue(npc, "Even the heat generated from burning the Bruma<br>roots only weakens it, and that doesn't last very long<br>nowadays."),
                new PlayerDialogue("Looks like an angry snow storm to me..."),
                new NPCDialogue(npc, "What you see in that pit is not the Wintertodt at full<br>strength, it never will be while the Bruma tree still" +
                        "<br>burns and the Doors of Dinh remain closed."),
                new OptionsDialogue(
                        new Option("Tell me about the Bruma tree.", () -> aboutBruamaTree(player, npc)),
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("See you later.", () -> seeYouLater(player, npc))
                )
        );
    }

    private static void aboutBruamaTree(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about the Bruma tree."),
                new NPCDialogue(npc, "The Bruma tree is what keeps the Wintertodt weak, the<br>prison was built here by Dinh because of its usefulness" +
                        "<br>against the Wintertodt. Some legends say that Xeric is<br>the cause for the doors weakening, or maybe he made"),
                new NPCDialogue(npc, "the Wintertodt stronger?"),
                new PlayerDialogue("It's a known fact that he was interested in the Bruma<br>Tree, for what purpose... there are only rumours."),
                new NPCDialogue(npc, "Regardless, the Bruma tree definitely contains powerful<br>magic, I've been studying the sap for years and it has" +
                        "<br>truly astounding properties."),
                new NPCDialogue(npc, "There's not many fires that burn green now, are<br>there?"),
                new PlayerDialogue("What about gnomish fire lighters?"),
                new NPCDialogue(npc, "..."),
                new OptionsDialogue(
                        new Option("Tell me about Wintertodt", () -> aboutWintertodt(player, npc)),
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("See you later.", () -> seeYouLater(player, npc))
                )
        );
    }

    private static void aboutYourself(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about yourself."),
                new NPCDialogue(npc, "Me? I'm a member of the Woodcutting guild, more of<br>" +
                        "a researcher though... I'm currently tasked with<br>growing a new Bruma tree."),
                new PlayerDialogue("How's that going?"),
                new NPCDialogue(npc, "Poorly. I believe the Bruma tree can only be grown in<br>a freezing cold climate."),
                new PlayerDialogue("Well, isn't that here?"),
                new NPCDialogue(npc, "It's just an idea really, for all I know, it could be magic<br>way beyond me, beyond even the Pyromancers."),
                new OptionsDialogue(
                        new Option("Tell me about Wintertodt", () -> aboutWintertodt(player, npc)),
                        new Option("Tell me about the Bruma tree.", () -> aboutBruamaTree(player, npc)),
                        new Option("See you later.", () -> seeYouLater(player, npc))
                )
        );
    }

    private static void seeYouLater(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("See you later."),
                new NPCDialogue(npc, "Goodbye!")
        );
    }

    static {
        NPCAction.register(7375, "talk-to", (player, npc) -> {
            if(player.talkedToIgnisia) {
                player.dialogue(
                        new NPCDialogue(npc, "Are you here to fight the cold?"),
                        new PlayerDialogue("Uhm... I guess?"),
                        new NPCDialogue(npc, "Great! Ask me anything you like about the Bruma<br>Tree, I'm an expert."),
                        new OptionsDialogue(
                                new Option("Tell me about Wintertodt", () -> aboutWintertodt(player, npc)),
                                new Option("Tell me about the Bruma tree.", () -> aboutBruamaTree(player, npc)),
                                new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                                new Option("See you later.", () -> seeYouLater(player, npc))
                        )
                );
            } else {
                player.dialogue(
                        new PlayerDialogue("Hi."),
                        new NPCDialogue(npc, "Hello."),
                        new PlayerDialogue("What's happening here?"),
                        new NPCDialogue(npc, "You'd best talk to Ignisia, she's in charge around here.")
                );
            }
        });
    }
}
