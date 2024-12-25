package io.ruin.model.item.actions.impl;

import io.ruin.cache.Icon;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemPlayerAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Broadcast;

public class ChristmasCracker {

    private static final LootTable CHRISTMAS_CRACKER = new LootTable().addTable(1,
            new LootItem(1038, 1, 1), //Red partyhat
            new LootItem(1040, 1, 1), //Yellow partyhat
            new LootItem(1042, 1, 1), //Blue partyhat
            new LootItem(1044, 1, 1), //Green partyhat
            new LootItem(1046, 1, 1), //Purple partyhat
            new LootItem(1048, 1, 1)  //White partyhat
    );

    static {
        ItemPlayerAction.register(962, (player, item, other) -> {
            if(other.getInventory().isFull()) {
                player.sendFilteredMessage("That player doesn't have enough inventory space.");
                return;
            }

            player.startEvent(event -> {
                player.lock();
                player.sendFilteredMessage("You pull a Christmas cracker...");
                player.animate(451);
                player.graphics(176, 82, 0);
                event.delay(1);
                Item prize = CHRISTMAS_CRACKER.rollItem();
                Broadcast.GLOBAL.sendNews(Icon.MYSTERY_BOX, "Christmas Cracker", "" + player.getName() + " just received " + prize.getDef().descriptiveName + "!");
                item.setId(prize.getId());
                player.unlock();
            });
        });
    }
}
