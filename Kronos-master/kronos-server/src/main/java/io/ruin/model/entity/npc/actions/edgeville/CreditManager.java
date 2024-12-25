package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.inter.dialogue.Dialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.services.Store;
import io.ruin.utility.Broadcast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreditManager {

    public static final String STORE_URL = World.type.getWebsiteUrl() + "/store";
    static boolean doubleChance = false;
    private static void talkTo(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Hey there, Adventurer, are you admiring my mighty table of riches? The seemingly uncountable piles of gold laid before you? It's beautiful, isn't it?"),
                new PlayerDialogue("Not really. Who are you?"),
                new NPCDialogue(npc, "The Master of Merchants; The King of Kings; The Golden Prince! I'm the one who handles all transactions in and out of this world you see around you."),
                new NPCDialogue(npc, "What would you like to know?"),
                new OptionsDialogue(
                        new Option("How do I donate?", () -> player.dialogue(
                                new PlayerDialogue("How do I donate?").animate(588),
                                new NPCDialogue(npc, "Donations are currently being taken through the website on the Store page. Simply visit " + World.type.getWebsiteUrl() + " and head to the store."),
                                new NPCDialogue(npc, "As it stands, you can make payments through Paypal using your balance, or Stripe using Credit/debit and BTC!"),
                                new NPCDialogue(npc, "Would you like me to open the page for you?"),
                                new OptionsDialogue(
                                        new Option("Yes", () -> player.openUrl(World.type.getWorldName() + " Store", STORE_URL)),
                                        new Option("No", player::closeDialogue)
                                )
                        )),
                        new Option("What sort of items can I donate for?", () -> player.dialogue(
                                new PlayerDialogue("What sort of items am I able to donate for?"),
                                new NPCDialogue(npc, "I've quite a wide selection and I'm always looking to expand! Right now, there's everything from random boxed of chance to rare items and consumables."),
                                new NPCDialogue(npc, "Would you like to take a look now?"),
                                new OptionsDialogue("Would you like to view our online webstore?",
                                        new Option("Yes", () -> player.openUrl(World.type.getWorldName() + " Store", STORE_URL)),
                                        new Option("No", player::closeDialogue)
                                )
                        )),
                        new Option("What are the different tiers of donators?", () -> player.dialogue(
                                new PlayerDialogue("What are the different available tiers of donation?"),
                                new NPCDialogue(npc, "Ahh, yes! You want to know about the different titles. Of course, of course."),
                                new NPCDialogue(npc, "Right now, there's 6 available tiers, ranging from all different amounts. As you spend more, you'll automatically increase in title. I am in the highest possible tier, but that's to be expected."),
                                new NPCDialogue(npc, "There's the standard donator, which you'll likely see most people have, and an " + PlayerGroup.SAPPHIRE.tag() + " icon beside their name."),
                                new NPCDialogue(npc, "Next comes the Super Donator, a step above, and the people who hold " + PlayerGroup.RUBY.tag() + " beside their name."),
                                new NPCDialogue(npc, "Following that, there's the Extreme Donators, and their names will have a " + PlayerGroup.DIAMOND.tag() + " beside it."),
                                new NPCDialogue(npc, "Afterward, you'll find the Ultimate Donators, a growingly prestigious rank, and their names will be next to a " + PlayerGroup.DRAGONSTONE.tag() + " icon."),
                                new NPCDialogue(npc, "The next rank is quite excellent, and a personal favorite of mine, and that's the Master Donators. You'll find their names beside a " + PlayerGroup.ONYX.tag() + " icon."),
                                new NPCDialogue(npc, "And finally, and the highest rank of all, is the Godlike Donators. Their names will be beside a " + PlayerGroup.ZENYTE.tag() + "."),
                                new NPCDialogue(npc, "There may be more ranks to come, but those are all I know of at the moment! As great as I may be, the decisions do come down from the top."),
                                new PlayerDialogue("Awesome, thanks!"))
                        ),
                        new Option("How many credits have I spent in total?", () -> player.dialogue(
                                new PlayerDialogue("How many credits have I spent in total?"),
                                new NPCDialogue(npc, "You have spent a total of " + NumberUtils.formatNumber(player.storeAmountSpent) + " credits, or $" + NumberUtils.formatTwoPlaces(player.storeAmountSpent / 100D) + ".")
                        ))
                )
        );
    }

    private static void claimDonations(Player player, NPC npc) {
        Store.claimPurchases(player, npc, (items, spent, error) -> {
            boolean bankedItems = false;
            if(items.isEmpty()) {
                if(error)
                    player.dialogue(new NPCDialogue(npc, "Error claiming purchases, please try again."));
                else
                    player.dialogue(new NPCDialogue(npc, "No unclaimed purchases found."));
            } else {
                int totalPurchases = items.size();
                int claimedPurchases = 0;
                for(Iterator<Item> it = items.iterator(); it.hasNext(); ) {
                    Item item = it.next();
                    ItemDef def = item.getDef();
                    if (doubleChance) {
                        doubleChance = false;
                        if (Random.rollPercent(40) && item.getId() != 13190) {
                            item.setAmount(item.getAmount() * 2);
                            player.sendMessage("Your item was doubled :-)");
                        }
                    }
                    if(def.stackable) {
                        Item invItem = player.getInventory().findItem(item.getId());
                        if(invItem != null) {
                            invItem.incrementAmount(item.getAmount());
                            claimedPurchases++;
                            it.remove();
                        } else if(player.getInventory().add(item.getId(), item.getAmount()) > 0) {
                            claimedPurchases++;
                            it.remove();
                        }
                    } else {
                        int id = item.getId();
                        int amount = item.getAmount();
                        if(amount > player.getInventory().getFreeSlots()) {
                            player.getBank().add(item.getId(), item.getAmount());
                            claimedPurchases++;
                            it.remove();
                            if(!bankedItems)
                                bankedItems = true;
                        } else {
                            if (player.getInventory().add(id, amount) > 0) {
                                claimedPurchases++;
                                it.remove();
                            }
                        }
                    }
                }
                List<Dialogue> dialogues = new ArrayList<>();
                if(error)
                    dialogues.add(new NPCDialogue(npc, "<col=ff0000>Warning:</col> One or more purchases may not<br>have been able to be claimed at this time.").lineHeight(24));
                if (claimedPurchases == totalPurchases)
                    dialogues.add(new NPCDialogue(npc, "Claimed " + totalPurchases + " purchases!"));
                else
                    dialogues.add(new NPCDialogue(npc, "Claimed " + claimedPurchases + "/" + totalPurchases + " purchases.<br>Come back for the rest when you have enough inventory space.").lineHeight(19));
                if(bankedItems)
                    player.sendMessage(Color.COOL_BLUE.wrap("One or more of your claimed items have been deposited into your bank."));
                Broadcast.GLOBAL.sendNews(player, player.getName() + " has claimed a donation :O! Join them at ::store!");
                if (Random.get(1, 4) > 2) {
                    Broadcast.GLOBAL.sendNews(Icon.DONATOR, "Next donation claim ingame has a 40% chance of doubling!");
                    doubleChance = true;
                }
                player.storeAmountSpent += spent;
                PlayerGroup group = getGroup(player);
                if(group != null && !player.isGroup(group)) {
                    player.join(group);
                    group.sync(player, "donator");
                    player.sendMessage("Congratulations, you've unlocked a new donator rank: <img=" + group.clientImgId + ">");
                }
                player.dialogue(dialogues.toArray(new Dialogue[dialogues.size()]));
            }
        });
    }

    private static void openStore(Player player, NPC npc) {
        player.openUrl(World.type.getWorldName() + " Store", STORE_URL);
    }

    static {
        NPCAction.register(2108, "open-shop", CreditManager::openStore);
        NPCAction.register(2108, "claim-purchases", CreditManager::claimDonations);
    }

    /**
     * Misc
     */

    public static PlayerGroup getGroup(Player player) {
        if(player.storeAmountSpent >= 2500)
            return  PlayerGroup.ZENYTE;
        if(player.storeAmountSpent >= 1000)
            return PlayerGroup.ONYX;
        if(player.storeAmountSpent >= 500)
            return PlayerGroup.DRAGONSTONE;
        if(player.storeAmountSpent >= 250)
            return PlayerGroup.DIAMOND;
        if(player.storeAmountSpent >= 100)
            return PlayerGroup.RUBY;
        if(player.storeAmountSpent >= 50)
            return PlayerGroup.EMERALD;
        if(player.storeAmountSpent >= 1)
            return PlayerGroup.SAPPHIRE;
        return null;
    }

}