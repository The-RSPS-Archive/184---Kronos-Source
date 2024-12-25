package io.ruin.model.item.actions.impl.boxes.mystery;

import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Broadcast;

public class ThirdAgeMysteryBox {

    private static final LootTable THIRD_AGE_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(12422, 1, 1), // 3rd age wand
            new LootItem(12424, 1, 1), // 3rd age bow
            new LootItem(12426, 1, 1), // 3rd age sword
            new LootItem(12437, 1, 1), // 3rd age cloak
            new LootItem(10330, 1, 1), // 3rd age range top
            new LootItem(10332, 1, 1), // 3rd age range legs
            new LootItem(10334, 1, 1), // 3rd age range coif
            new LootItem(10336, 1, 1), // 3rd age range vanbraces
            new LootItem(10338, 1, 1), // 3rd age robe top
            new LootItem(10340, 1, 1), // 3rd age robe
            new LootItem(10342, 1, 1), // 3rd age mage hat
            new LootItem(10344, 1, 1), // 3rd age amulet
            new LootItem(10346, 1, 1), // 3rd age platelegs
            new LootItem(10348, 1, 1), // 3rd age platebody
            new LootItem(10350, 1, 1), // 3rd age fullhelm
            new LootItem(10352, 1, 1) // 3rd age kiteshield
    );

    static {
        ItemAction.registerInventory(6831, "open", (player, item) -> {
            player.lock();
            Item reward = THIRD_AGE_BOX_TABLE.rollItem();
            player.closeDialogue();
            item.remove();
            player.getInventory().add(reward);
            player.sendFilteredMessage(Color.DARK_GREEN.wrap("You open the mystery box to find a " + reward.getDef().name + "."));
            Broadcast.GLOBAL.sendNews(Icon.MYSTERY_BOX, "3rd Age Mystery Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
            player.unlock();
        });

    }
}
