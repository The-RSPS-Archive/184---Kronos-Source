package io.ruin.model.entity.npc.actions.zeah;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;

public class Tynan {

    private static final int TYNAN = 6964;

    static {
        NPCAction.register(TYNAN, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "If you're looking for fishing supplies, you've come to the right place."),
                new OptionsDialogue(
                        new Option("Let's see what you've got for sale.", () -> ShopManager.openIfExists(player, "")),//TODO Fill this in
                        new Option("I'll be on my way.", () -> player.dialogue(
                                new PlayerDialogue("I'll be on my way."),
                                new NPCDialogue(npc, "See you around.")))
                )
        ));
    }
}
