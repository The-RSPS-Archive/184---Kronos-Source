package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;

public class DiceBag {

    public static final int DICE_BAG = 10834;

    public static void roll(Player player, int sides, boolean rollHigh, boolean rollLow) {
/*      Anyone can dice for now?? TODO: Readdress this later.
        if (!player.isDiceHost() && !player.isAdmin()) {
            player.sendFilteredMessage("You need to be an official dice host to use this.");
            return;
        }*/
        player.stringInput("Enter player's display name:", name -> {
            name = name.replaceAll("[^a-zA-Z0-9\\s]", "");
            name = name.substring(0, Math.min(name.length(), 12));
            if (name.isEmpty()) {
                player.retryStringInput("Invalid username, try again:");
                return;
            }
            if (name.equalsIgnoreCase(player.getName())) {
                player.retryStringInput("Cannot roll against yourself, try again:");
                return;
            }
            Player target = World.getPlayer(name);
            if (target == null) {
                player.retryStringInput("Player cannot be found, try again:");
                return;
            }
            player.startEvent(event -> {
                int roll = Random.get(1, sides);
                if (roll > 10 && sides == 100 && Random.rollDie(10, 1))
                    roll /= 10;
                if (rollHigh && sides == 100) {
                    roll = Random.get(55, 100);
                } else if (rollLow && sides == 100) {
                    roll = Random.get(1, 54);
                }
                player.lock();
                player.forceText("<img=119> Rolling against "  + target.getName() + "...");
                player.sendFilteredMessage("You roll the dice..");
                event.delay(3);
                player.forceText("<img=119> ...Rolled a " + roll + "/" + sides + "");
                player.sendMessage("<img=119> You rolled a " + roll + "/" + sides + " on the dice.");
                target.sendMessage("<img=119> " + player.getName() + " rolled a " + roll + "/" + sides + " on the dice.");
                player.unlock();
            });
        });

    }

    static {
        ItemAction.registerInventory(DICE_BAG, "roll 4-sided", (player, item) -> roll(player, 4, false, false));
        ItemAction.registerInventory(DICE_BAG, "roll 12-sided", (player, item) -> roll(player, 12, false, false));
        ItemAction.registerInventory(DICE_BAG, "roll 100-sided", (player, item) -> roll(player, 100, false, false));
    }
}
