package io.ruin.model.activities.loyaltychest;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.services.Loggers;
import io.ruin.utility.TimedList;

import java.util.concurrent.TimeUnit;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public class LoyaltyChest {

    private static TimedList recentPlayers = new TimedList();
    private static final long LOYALTY_TIMER = TimeUnit.HOURS.toMillis(24);
    private static final long LOYALTY_SPREE_TIMER = TimeUnit.HOURS.toMillis(36);
    private static final int LOYALTY_CHEST = 32758;

    private static final Item[] PVP_REWARDS = {
            new Item(BLOOD_MONEY, 250),
            new Item(BLOOD_MONEY, 500),
            new Item(BLOOD_MONEY, 750),
            new Item(BLOOD_MONEY, 1000),
            new Item(BLOOD_MONEY, 1250),
            new Item(BLOOD_MONEY, 1500),
            new Item(BLOOD_MONEY, 1750),
            new Item(BLOOD_MONEY, 2000),
            new Item(BLOOD_MONEY, 2250),
            new Item(BLOOD_MONEY, 3000)
    };

    private static final Item[] ECO_REWARDS = {
            new Item(COINS_995, 10000),
            new Item(COINS_995, 25000),
            new Item(COINS_995, 50000),
            new Item(COINS_995, 100000),
            new Item(COINS_995, 125000),
            new Item(COINS_995, 150000),
            new Item(COINS_995, 175000),
            new Item(COINS_995, 200000),
            new Item(COINS_995, 250000),
            new Item(COINS_995, 500000)
    };

    private static String getRemainingTime(Player player) {
        long ms = player.loyaltyTimer - System.currentTimeMillis();
        long hours = TimeUnit.MILLISECONDS.toHours(ms);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        return (hours >= 1 ? (hours + " hour" + (hours > 1 ? "s" : "") + " and ") : "") +
                Math.max((minutes - TimeUnit.HOURS.toMinutes(hours)), 1) + " minute" +
                ((minutes - TimeUnit.HOURS.toMinutes(hours)) > 1 ? "s" : "");
    }


    static {

        /*ObjectAction.register(LOYALTY_CHEST, "loot", (player, obj) -> openChest(player));

        ObjectAction.register(LOYALTY_CHEST, "about", (player, obj) -> player.sendScroll("<col=800000>Loyalty Chests",
                "Loyalty Chests are our <col=8e1414>thank you for playing " + World.type.getWorldName() + "</col>. Every",
                "24 hours you'll be able to claim a reward. Continually claiming",
                "your rewards will result in a spree. If you don't claim your reward",
                "within <col=8e1414>12 hours of it being available, your spree will be reset</col>",
                "and you'll start at the beginning of the reward table. These are",
                "the requirements for advancing reward tables:",
                "",
                "   Thank you for playing " + World.type.getWorldName() + "."
        ));

        LoginListener.register(player -> {
            if (player.loyaltyTimer < System.currentTimeMillis())
                player.sendFilteredMessage(Color.COOL_BLUE.wrap("You are now eligible to open the loyalty chest. Thanks for playing " + World.type.getWorldName() + "!"));
        });


        InterfaceHandler.register(Interface.LOYALTY_CHEST, h -> {
            h.actions[25] = (SimpleAction) p -> claimReward(p, 1);
            h.actions[27] = (SimpleAction) p -> claimReward(p, 2);
            h.actions[29] = (SimpleAction) p -> claimReward(p, 3);
            h.actions[31] = (SimpleAction) p -> claimReward(p, 4);
            h.actions[33] = (SimpleAction) p -> claimReward(p, 5);
            h.actions[35] = (SimpleAction) p -> claimReward(p, 6);
            h.actions[37] = (SimpleAction) p -> claimReward(p, 7);
            h.actions[39] = (SimpleAction) p -> claimReward(p, 8);
            h.actions[41] = (SimpleAction) p -> claimReward(p, 9);
            h.actions[43] = (SimpleAction) p -> claimReward(p, 10);
        });

        LoginListener.register(e -> {
            if(e.loyaltyChestReward == 0)
                e.loyaltyChestReward = 1;
        });*/
    }

    private static void openChest(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.LOYALTY_CHEST);
        Item[] rewards = ECO_REWARDS;
        player.getPacketSender().sendLoyaltyRewards(player.loyaltyChestReward, player.loyaltyChestSpree, player.highestLoyaltyChestSpree,1, rewards);
    }

    private static void claimReward(Player player, int requestedDayReward) {
        if(player.loyaltyTimer > System.currentTimeMillis()) {
            player.dialogue(new ItemDialogue().one(744, "You have to wait " + Color.COOL_BLUE.wrap(getRemainingTime(player)) + " until you're able to open the Loyalty Chest.").lineHeight(24));
            return;
        }
        if(recentPlayers.contains(player.getIpInt(), System.currentTimeMillis(), 720L)) {
            player.dialogue(new ItemDialogue().one(744, "You have already claimed your reward in the past 24 hours.").lineHeight(24));
            return;
        }
        if(!player.getInventory().hasFreeSlots(2)) {
            player.dialogue(new ItemDialogue().one(744, "You need at least 2 free inventory slot to claim your loyalty rewards."));
            return;
        }
        if(requestedDayReward < player.loyaltyChestReward) {
            player.dialogue(new ItemDialogue().one(744, "You have already claimed this reward!"));
            return;
        }
        if(requestedDayReward > player.loyaltyChestReward) {
            player.dialogue(new ItemDialogue().one(744, "You have not unlocked this reward yet!"));
            return;
        }
        if(player.isLocked())
            return;
        player.startEvent(event -> {
            player.lock();
            if(player.loyaltyChestReward == 0)
                player.loyaltyChestReward = 1;
            if(player.loyaltySpreeTimer < System.currentTimeMillis() && player.loyaltyChestSpree != 0) {
                player.sendFilteredMessage(Color.COOL_BLUE.wrap("Your loyalty chest spree has reset."));
                player.loyaltyChestSpree = 0;
                player.loyaltyChestReward = 1;
            }
            giveReward(player);
            player.loyaltyChestSpree++;
            player.loyaltyChestReward++;
            if(player.loyaltyChestReward > 10)
                player.loyaltyChestReward = 1;
            if(player.loyaltyChestSpree > player.highestLoyaltyChestSpree)
                player.highestLoyaltyChestSpree = player.loyaltyChestSpree;
            player.loyaltyTimer = System.currentTimeMillis() + LOYALTY_TIMER;
            player.loyaltySpreeTimer = System.currentTimeMillis() + LOYALTY_SPREE_TIMER;
            recentPlayers.add(player.getIpInt(), System.currentTimeMillis());
            openChest(player);
            event.delay(1);
            player.unlock();
        });
    }

    private static  void giveReward(Player player) {
        Item[] rewards = ECO_REWARDS;
        Item rewardItem = rewards[player.loyaltyChestReward - 1];
        player.startEvent(event -> {
            player.lock();
            player.getInventory().addOrDrop(rewardItem);
            player.sendFilteredMessage(Color.COOL_BLUE.wrap("You have opened the loyalty chest a total of " + player.loyaltyChestCount + " time" + (player.loyaltyChestCount > 1 ? "s" : "") + " and are on a spree of " + player.loyaltyChestSpree + "."));
            player.sendFilteredMessage("You receive " + Color.COOL_BLUE.wrap(rewardItem.getDef().descriptiveName) + " for your loyalty towards " + Color.COOL_BLUE.wrap(World.type.getWorldName() + "") + ". Thank you for playing!");
            Loggers.logLoyaltyChest(player.getUserId(), player.getName(), player.getIp(), player.loyaltyChestSpree, rewardItem);
            player.unlock();
        });
    }

}
