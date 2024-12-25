package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/20/2020
 */
public class Bonds {

    private static void redeem(Player player, int amount, Item item) {
        player.dialogue(new ItemDialogue().one(item.getId(), "You are about to redeem this bond<br>" +
                "adding $"+amount+" to your amount donated.<br>" +
                "This will consume the bond forever."),
        new OptionsDialogue(
                new Option("Yes!", (player1 -> {
                    player.getInventory().remove(item);
                    player.storeAmountSpent += amount;
                    player.sendMessage("You have redeemed a $"+amount+" Bond. Your new total is: $"+player.storeAmountSpent);
                })),
                new Option("I'll keep it for now.", player::closeDialogue)
        ));
    }

    static {
        ItemAction.registerInventory(30276, 1, ((player, item) -> redeem(player, 5, item)));
        ItemAction.registerInventory(30279, 1, ((player, item) -> redeem(player, 10, item)));
        ItemAction.registerInventory(30282, 1, ((player, item) -> redeem(player, 25, item)));
        ItemAction.registerInventory(30285, 1, ((player, item) -> redeem(player, 50, item)));
        ItemAction.registerInventory(30288, 1, ((player, item) -> redeem(player, 100, item)));
        ItemAction.registerInventory(30291, 1, ((player, item) -> redeem(player, 250, item)));
        ItemAction.registerInventory(30294, 1, ((player, item) -> redeem(player, 500, item)));
    }
}
