package io.ruin.model.entity.npc.actions.piscatoris;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.handlers.CollectionBox;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.shop.ShopManager;

public class ArnoldLydspor {

    static {
        NPCAction.register(4293, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "What you want from Arnold, heh?"),
                    new OptionsDialogue(
                            new Option("Can you open my bank account, please?.", () -> player.getBank().open()),
                            new Option("I'd like to check my Bank PIN settings.", () -> player.getBankPin().openSettings()),
                            new Option("I'd like to collect items.", () -> CollectionBox.open(player)),
                            new Option("Would you like to trade?", () -> player.dialogue(
                                    new PlayerDialogue("Would you like to trade?"),
                                    new NPCDialogue(npc, "Ya, I have wide range of stock..."),
                                    new ActionDialogue(() -> ShopManager.openIfExists(player,"")))//TODO Fill this in
                            ),
                            new Option("Nothing, I just came to chat.", () -> player.dialogue(new PlayerDialogue("Nothing, I just came to chat."),
                                    new NPCDialogue(npc, "Ah, that is nice - always I like to chat, but Herr Caranos tell me to get back to work! " +
                                            "Here, you been nice so have a present:"),
                                    new ActionDialogue(() -> {
                                        player.getInventory().addOrDrop(1965, 1);
                                        player.dialogue(
                                                new PlayerDialogue("A cabbage?"),
                                                new NPCDialogue(npc, "Ja, cabbage is good for you!"),
                                                new PlayerDialogue("Um... thanks!"));
                                    }))
                            )
                    )
            );
        });

        /**
         * Barrel
         */
        ObjectAction.register(13600, "take-from", (player, obj) -> player.dialogue(new PlayerDialogue("I think I should maybe catch my own.")));
    }
}
