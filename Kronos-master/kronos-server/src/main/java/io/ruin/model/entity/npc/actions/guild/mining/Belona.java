package io.ruin.model.entity.npc.actions.guild.mining;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.shop.ShopManager;

public class Belona {

    private static final int BELONA = 7719;

    static {
        NPCAction.register(BELONA, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Hello human."),
                new PlayerDialogue("What do you do here?"),
                new NPCDialogue(npc, "This guild contains minerals that haven't been found anywhere else. I'm here to try and " +
                        "identify them."),
                new NPCDialogue(npc, "If you come across any unusual minerals when mining here I'll take them off your hands. " +
                        "Don't worry, I'll make it worth your while."),
                new OptionsDialogue(
                        new Option("I have minerals to trade.", () -> ShopManager.openIfExists(player, "")),//TODO Fill this in
                        new Option("What can you offer me?", () -> player.dialogue(
                                new PlayerDialogue("What will you give me in return for these minerals?"),
                                new NPCDialogue(npc, "I have some gloves that will help you when Mining. When you wear these gloves " +
                                        "you'll find that some rocks will instantly respawn."),
                                new PlayerDialogue("How do they do that?"),
                                new NPCDialogue(npc, "Magic!"),
                                new NPCDialogue(npc, "So would you like to take a look?"),
                                new OptionsDialogue(
                                        new Option("Yes please.", () -> ShopManager.openIfExists(player, "")),//TODO Fill this in
                                        new Option("No thanks.", () -> player.dialogue(
                                                new PlayerDialogue("That's all thank you."),
                                                new NPCDialogue(npc, "Don't forget to come to me if you find any minerals.")))
                                )

                        )),
                        new Option("Can you combine my mining gloves?", () -> player.dialogue(
                                new PlayerDialogue("Can you combine my mining gloves?"),
                                new NPCDialogue(npc, "I guess I could combine some Mining gloves with some Superior mining gloves. " +
                                        "These new gloves would require 70 Mining to equip."),
                                new NPCDialogue(npc, "It wouldn't be free though. I'd want 60 minerals in return."),
                                new OptionsDialogue(
                                        new Option("That's fine. Combine my gloves.", () -> {
                                            Item miningGloves = player.getInventory().findItem(21343);
                                            if (miningGloves == null) {
                                                player.dialogue(new NPCDialogue(npc, "You don't have any mining gloves for me to combine. You'll need to bring me some Mining gloves and some Superior mining gloves."));
                                                return;
                                            }
                                            Item superiorMiningGloves = player.getInventory().findItem(21345);
                                            if (superiorMiningGloves == null) {
                                                player.dialogue(new NPCDialogue(npc, "You don't have any superior mining gloves for me to combine. You'll need to bring me some Mining gloves and some Superior mining gloves."));
                                                return;
                                            }
                                            Item minerals = player.getInventory().findItem(21341);
                                            if (minerals == null || minerals.getAmount() < 60) {
                                                player.dialogue(new NPCDialogue(npc, "You don't have enough minerals You'll need to bring me 60 minerals if you want me to combine your gloves."));
                                                return;
                                            }

                                            minerals.remove(60);
                                            miningGloves.remove();
                                            superiorMiningGloves.remove();
                                            player.getInventory().add(21392, 1);
                                            player.dialogue(
                                                    new NPCDialogue(npc, "Here's a pair of expert mining gloves!"),
                                                    new PlayerDialogue("Thanks!"));
                                        }),
                                        new Option("That's too much.", () -> player.dialogue(
                                                new PlayerDialogue("That's too much.."),
                                                new NPCDialogue(npc, "Fair enough. I'll be here if you change your mind.")
                                        ))
                                )
                        )),
                        new Option("That's all.", () -> player.dialogue(
                                new PlayerDialogue("That's all thank you."),
                                new NPCDialogue(npc, "Don't forget to come to me if you find any minerals.")
                        ))
                )

        ));
        NPCAction.register(BELONA, "toggle-minerals", (player, npc) -> {
            if (player.miningGuildMinerals) {
                player.miningGuildMinerals = false;
                player.dialogue(
                        new PlayerDialogue("I don't want to receive minerals."),
                        new NPCDialogue(npc, "Very well. You'll not longer find any minerals when you mine here. If you" +
                                " change your mind just let me know.")

                );
            } else {
                player.miningGuildMinerals = true;
                player.dialogue(
                        new PlayerDialogue("I'd like to receive minerals."),
                        new NPCDialogue(npc, "As you wish, You'll now find minerals when you mine here. Let me know if" +
                                " you want to change this.")
                );
            }
        });
    }
}
