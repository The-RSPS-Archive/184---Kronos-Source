package io.ruin.model.skills.construction.servants.guild;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;

public class ChiefServant {

    static {
        NPCAction.register(5417, "talk-to", ChiefServant::talk);
    }

    private static void talk(Player player, NPC npc) {
        player.dialogue(new NPCDialogue(npc, "Hello " + player.getName() + ", how can I be of assistance today?"), new ActionDialogue(() -> sendOptions(player, npc)));
    }

    private static void sendOptions(Player player, NPC npc) {
        player.dialogue(new OptionsDialogue(
                new Option("What services do servants offer?", () -> {
                    player.dialogue(new NPCDialogue(npc, "That depends on which servant we're talking about, though all of them can fetch and take items to the bank."),
                            new NPCDialogue(npc, "More expensive servants can also carry more items in a single trip and perform their tasks faster,"),
                            new NPCDialogue(npc, "however they will only be willing to work for you if you have a certain Construction level."),
                            new ActionDialogue(() -> sendOptions(player, npc)));
                }),
                new Option( "I want to hire a servant.", () -> {
                    if (player.house == null) {
                        player.dialogue(new NPCDialogue(npc, "But you don't have a house! What use would a servant be?"),
                                new ActionDialogue(() -> sendOptions(player, npc)));
                        return;
                    }
                    if (player.house.getServantSave().getHiredServant() != null) {
                        player.dialogue(new NPCDialogue(npc, "You already have a servant. If you want to hire a different one, please fire your current servant first."),
                                new ActionDialogue(() -> sendOptions(player, npc)));
                        return;
                    }
                    if (!player.house.canHaveServant()) {
                        player.dialogue(new NPCDialogue(npc, "I'm sorry, but since servants stay at your house full time, it needs to have at least 2 bedrooms and 2 beds."),
                                new ActionDialogue(() -> sendOptions(player, npc)));
                        return;
                    }
                    player.dialogue(new NPCDialogue(npc, "Great! You can speak directly to the servants here in the guild to hire one of them."));
                }),
                new Option("I want to fire my servant.", () -> {
                    if (player.house == null) {
                        player.dialogue(new NPCDialogue(npc, "But you don't even have a house!"),
                                new ActionDialogue(() -> sendOptions(player, npc)));
                        return;
                    }
                    if (player.house.getServantSave().getHiredServant() == null) {
                        player.dialogue(new NPCDialogue(npc, "I must apologize, but I am confused. According to my records, you don't have a servant."),
                                new ActionDialogue(() -> sendOptions(player, npc)));
                        return;
                    }
                    player.dialogue(new NPCDialogue(npc, "As you wish. I will contact them and request their return to the guild."));
                    player.house.getServantSave().fire();
                    Config.HIRED_SERVANT.set(player, 0);
                }),
                new Option("Nevermind.")
        ));
    }

}
