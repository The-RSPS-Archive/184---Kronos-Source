package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.utility.Broadcast;

public enum WildernessKey {
    ONE_M(8943, 1),
    FIVE_M(8944, 5),
    TEN_M(8945, 10),
    TWENTY_FIVE_M(8946, 25),
    FIFTY_M(8947, 50),
    ONE_HUNDRED_M(8948, 100);

    private final int itemId, goldReward;

    WildernessKey(int itemId, int goldReward) {
        this.itemId = itemId;
        this.goldReward = goldReward;
    }

    public static void rollForPlayerKill(Player player, Player killed) {
        if(Random.rollDie(200, 1)) {
            player.getInventory().addOrDrop(WildernessKey.ONE_M.itemId, 1);
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 1M Wilderness key by killing " + killed.getName() +"!");
        } else if(Random.rollDie(1000, 1)) {
            player.getInventory().addOrDrop(WildernessKey.FIVE_M.itemId, 1);
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 5M Wilderness key by killing " + killed.getName() +"!");
        } else if(Random.rollDie(2500, 1)) {
            player.getInventory().addOrDrop(WildernessKey.TEN_M.itemId, 1);
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 10M Wilderness key by killing " + killed.getName() +"!");
        } else if(Random.rollDie(10000, 1)) {
            player.getInventory().addOrDrop(WildernessKey.TWENTY_FIVE_M.itemId, 1);
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 25M Wilderness key by killing " + killed.getName() +"!");
        } else if(Random.rollDie(40000, 1)) {
            player.getInventory().addOrDrop(WildernessKey.FIFTY_M.itemId, 1);
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 50M Wilderness key by killing " + killed.getName() +"!");
        } else if(Random.rollDie(70000, 1)) {
            player.getInventory().addOrDrop(WildernessKey.ONE_HUNDRED_M.itemId, 1);
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 100M Wilderness key by killing " + killed.getName() +"!");
        }
    }

    public static void rollForWildernessBossKill(Player player, NPC npc) {
        if(npc.getId() == 6609 || npc.getId() == 6610 || npc.getId() == 6619 || npc.getId() == 6618 || npc.getId() == 2054 || npc.getId() == 6611) {
            if(Random.rollDie(50, 1)) {
                player.getInventory().addOrDrop(WildernessKey.ONE_M.itemId, 1);
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 1M Wilderness key by killing " + npc.getDef().name +"!");
            } else if(Random.rollDie(200, 1)) {
                player.getInventory().addOrDrop(WildernessKey.FIVE_M.itemId, 1);
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 5M Wilderness key by killing " + npc.getDef().name +"!");
            } else if(Random.rollDie(500, 1)) {
                player.getInventory().addOrDrop(WildernessKey.TEN_M.itemId, 1);
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 10M Wilderness key by killing " + npc.getDef().name +"!");
            } else if(Random.rollDie(2000, 1)) {
                player.getInventory().addOrDrop(WildernessKey.TWENTY_FIVE_M.itemId, 1);
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 25M Wilderness key by killing " + npc.getDef().name +"!");
            } else if(Random.rollDie(5000, 1)) {
                player.getInventory().addOrDrop(WildernessKey.FIFTY_M.itemId, 1);
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 50M Wilderness key by killing " + npc.getDef().name +"!");
            } else if(Random.rollDie(10000, 1)) {
                player.getInventory().addOrDrop(WildernessKey.ONE_HUNDRED_M.itemId, 1);
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, player.getName() + " has just received a 100M Wilderness key by killing " + npc.getDef().name +"!");
            }
        }
    }

    static {
        for(WildernessKey wildernessKey : WildernessKey.values()) {
            ItemAction.registerInventory(wildernessKey.itemId, "information", (player, item) -> {
                String adminNames = "Joshua/Fugazy/Tyler";
                player.dialogue(new ItemDialogue().one(wildernessKey.itemId, "You can redeem this key for " + wildernessKey.goldReward + "M OSRS gold by speaking with " + Color.COOL_BLUE.wrap(adminNames) + "."));
            });
        }
    }

}
