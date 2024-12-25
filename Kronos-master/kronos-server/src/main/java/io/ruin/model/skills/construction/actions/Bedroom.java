package io.ruin.model.skills.construction.actions;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Construction;
import io.ruin.model.skills.construction.House;

import static io.ruin.cache.ItemID.COINS_995;

public class Bedroom {

    static {
        ObjectAction.register(Buildable.SERVANTS_MONEYBAG.getBuiltObjects()[0], "use", Construction.forHouseOwnerOnly((player, house) -> {
            player.dialogue(new ItemDialogue().one(Buildable.SERVANTS_MONEYBAG.getItemId(), house.getMoneyInMoneybag() == 0 ? "The moneybag is empty." : "The servant's moneybag currently has " + NumberUtils.formatNumber(house.getMoneyInMoneybag()) + " coins in it."),
                    new OptionsDialogue("Choose an option",
                            new Option("Deposit", () -> deposit(player, house)),
                            new Option("Withdraw", () -> withdraw(player, house)),
                            new Option("Cancel")
                    )
            );
        }));
    }

    private static void withdraw(Player player, House house) {
        if (house.getMoneyInMoneybag() <= 0) {
            player.dialogue(new MessageDialogue("The moneybag is empty."));
            return;
        }
        player.integerInput("Enter amount:", amount -> {
            amount = Math.min(amount, house.getMoneyInMoneybag());
            int added = player.getInventory().add(COINS_995, amount);
            if (added > 0) {
                house.setMoneyInMoneybag(house.getMoneyInMoneybag() - amount);
                player.sendMessage("You withdraw " + NumberUtils.formatNumber(added) + " coins from the moneybag.");
            } else {
                player.dialogue(new MessageDialogue("Not enough space in your inventory."));
            }
        });
    }

    private static void deposit(Player player, House house) {
        if (player.getInventory().getAmount(COINS_995) == 0) {
            player.dialogue(new MessageDialogue("You don't have any coins in your inventory."));
            return;
        }
        player.integerInput("Enter amount:", amount -> {
            amount = Math.min(player.getInventory().getAmount(COINS_995), amount);
            if (amount <= 0)
                return;
            house.setMoneyInMoneybag((int) Math.min((long)(house.getMoneyInMoneybag() + amount), Integer.MAX_VALUE));
            player.getInventory().remove(COINS_995, amount);
            player.sendMessage("You deposit " + NumberUtils.formatNumber(amount) + " coins into the servant's moneybag.");
        });
    }

}
