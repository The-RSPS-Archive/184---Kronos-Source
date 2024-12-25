package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;
import io.ruin.model.shop.ShopManager;

public class Richard {

    public static final int RICHARD = 2200;

    static {
        NPCAction.register(RICHARD, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "Hello there, are you interested in buying one of my special capes?"),
                    new OptionsDialogue(
                            new Option("What's so special about your capes?", () -> {
                                player.dialogue(
                                        new PlayerDialogue("What's so special about your capes?"),
                                        new NPCDialogue(npc, "Ahh well they make it less likely that you'll accidentally attack anyone wearing" +
                                                " the same cape as you. They also make it easier to distinguish people who're wearing the same cape as you"),
                                        new NPCDialogue(npc, "from everyone else. They're very useful when out in teh wilderness with" +
                                                " friends or anyone else you don't want to harm."),
                                        new NPCDialogue(npc, "So would you like to buy one?"),
                                        new OptionsDialogue(
                                                new Option("Yes please!", () -> ShopManager.openIfExists(player, "")),//TODO Fill this in
                                                new Option("No thanks.", () -> player.dialogue(new PlayerDialogue("No thanks.")))
                                ));
                            }),
                            new Option("Yes please!", () -> ShopManager.openIfExists(player, "")),//TODO Fill this in
                            new Option("No thanks.", () -> player.dialogue(new PlayerDialogue("No thanks.")))
                    ));
        });
        NPCAction.register(RICHARD, "trade", (player, npc) -> ShopManager.openIfExists(player, ""));//TODO Fill this in
    }
}
