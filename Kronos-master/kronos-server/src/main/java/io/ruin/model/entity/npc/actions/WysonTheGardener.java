package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;

public class WysonTheGardener {

    static {
        NPCAction.register(3253, "talk-to", (player, npc) -> {
            npc.faceTemp(player);
            player.dialogue(new NPCDialogue(3253, "Hello there, would you like to trade any mole parts?"),
                    new OptionsDialogue(
                            new Option("Yes", () -> exchange(player)),
                            new Option("What do you mean?",
                                    () -> {
                                        player.dialogue(new PlayerDialogue("What do you mean?"),
                                                new NPCDialogue(3253, "I can give you one bird nest for each mole part you bring me.<br>The bird nests will contain either a ring or some seeds."),
                                                new PlayerDialogue("Alright, and how can I find mole parts?"),
                                                new NPCDialogue(3253, "By killing the giant mole that lurks beneath this park.<br>Dig with a spade on any of the mole hills to access its lair."),
                                                new PlayerDialogue("Thank you."));
                                    }),
                            new Option("No")));
        });
    }

    private static void exchange(Player player) {
        if (!player.getInventory().hasFreeSlots(2)) {
            player.dialogue(new NPCDialogue(3253, "Sorry but you'll need at least two free inventory slots to carry the bird nests."));
            return;
        }
        int nestCount = player.getInventory().removeAll(true, new Item(7416, 10000), new Item(7418, 10000));
        if (nestCount == 0) {
            player.dialogue(new NPCDialogue(3253, "Doesn't look like you're caryring any mole parts.<br>Come back to me when you have some."));
            return;
        }
        int ringNests = nestCount / 3;
        int seedNests = nestCount - ringNests;
        player.nestBoxRings += ringNests;
        player.nestBoxSeeds += seedNests;
        player.getInventory().add(12793, 1); // seeds
        player.getInventory().add(12794, 1); // rings
        player.dialogue(new NPCDialogue(3253, "Thank you!<br>The nests are in those boxes I gave you.<br>Come see me again when you have more mole parts."));
    }

}
