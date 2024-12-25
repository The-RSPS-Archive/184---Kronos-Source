package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class NestBoxes {

    static {
        ItemAction.registerInventory(12793, "extract", (player, item) -> extract(player, item, false));
        ItemAction.registerInventory(12794, "extract", (player, item) -> extract(player, item, true));

        ItemAction.registerInventory(12793, "check", (player, item) -> check(player, item, false));
        ItemAction.registerInventory(12794, "check", (player, item) -> check(player, item, true));
    }

    private static void check(Player player, Item item, boolean rings) {
        int amount = rings ? player.nestBoxRings : player.nestBoxSeeds;
        player.sendMessage("The box currently contains " + amount + " bird nest" + ( amount > 1 ? "s" : "") + ".");
    }

    private static void extract(Player player, Item item, boolean rings) {
        if (!player.isNearBank()) {
            player.sendMessage("You can only open nest boxes while near a bank.");
            return;
        }
        int freeSlots = player.getInventory().getFreeSlots();
        if (freeSlots == 0) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        int inBox = rings ? player.nestBoxRings : player.nestBoxSeeds;
        int toExtract = Math.min(inBox, freeSlots);
        player.getInventory().add(rings ? 5074 : 5073, toExtract);
        if (rings)
            player.nestBoxRings -= toExtract;
        else
            player.nestBoxSeeds -= toExtract;
        if ((rings && player.nestBoxRings == 0)
            || (!rings && player.nestBoxSeeds == 0)) {
            item.setId(12792);
            player.sendMessage("The nest box is now empty.");
        }
    }

}
