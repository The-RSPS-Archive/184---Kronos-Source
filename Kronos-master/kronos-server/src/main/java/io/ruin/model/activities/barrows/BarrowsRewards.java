package io.ruin.model.activities.barrows;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.ruin.cache.ItemID.COINS_995;

public class BarrowsRewards {

    /**
     * This table is pretty much designed for stackable loots only.
     * (Amounts are modified within roll based on kill count)
     */

    private static final LootTable miscTable = new LootTable()
            .addTable(2, //low runes
                    new LootItem(558, 1, 3000, 3),          // mind runes
                    new LootItem(562, 1, 1000, 1)           // chaos runes
            )
            .addTable(1, //high runes
                    new LootItem(560, 1, 600, 3),           // death runes
                    new LootItem(565, 1, 300, 1)            // blood runes
            )
            .addTable(1, //other
                    new LootItem(4740, 1, 300, 5),          // bolt racks
                    new LootItem(COINS_995, 10000, 250000, 1)     // coins
            );

    /**
     * This table is basically for any non-stackable loot.
     * (Only one item can be rolled from this table per chest loot!)
     */

    private static final LootTable miscUniqueTable = new LootTable()
            .addTable(45) //nothing
            .addTable(1,
                    new LootItem(985, 1, 200),  // tooth half of key
                    new LootItem(987, 1, 200),  // loop half of key
                    new LootItem(1149, 1, 50),  // dragon med helm
                    new LootItem(12073, 1, 1)   // clue scroll (elite)
            );

    /**
     * Looting - Read the comments above if you plan on changing anything! :)
     */

    public static ItemContainer loot(Player player) {
        double brothersKilled = 0;
        List<Integer> barrowsIds = new ArrayList<>();
        for(BarrowsBrother b : BarrowsBrother.values()) {
           if(b.isKilled(player)) {
                brothersKilled++;
                Collections.addAll(barrowsIds, b.pieces);
           }
        }
        Collections.shuffle(barrowsIds);

        double barrowsChance = 1D / (450D - (58D * brothersKilled));
        double customBarrowsChance = barrowsChance *  2;
        boolean miscUnique = false;

        ItemContainer container = new ItemContainer();
        container.init(player, 6, -1, 64161, 141, false);
        container.sendAll = true;

        for(int i = 0; i < brothersKilled; i++) {
            if(Random.get() > customBarrowsChance) {
                /*
                 * Misc loot
                 */
                Item item;
                if(!miscUnique && (item = miscUniqueTable.rollItem()) != null) {
                    miscUnique = true;
                } else {
                    item = miscTable.rollItem();
                    int amount = item.getAmount() / 6; //divide by 6 since table can be rolled 6 times! (Imagine landing 3k mind runes 6 times.. lol!)
                    if(brothersKilled < 6) //if player didn't kill all 6 brothers, reduce the amount!
                        amount *= (brothersKilled / 6);
                    item.setAmount(amount);
                }
                container.add(item);
            } else {
                /**
                 * Barrows loot
                 */
                ItemDef def = ItemDef.get(barrowsIds.remove(0));
               // Broadcast.FRIENDS.sendNews(player, player.getName() + " just received " + def.descriptiveName + " from the Barrows chest!");
                container.add(def.id, 1);
            }
        }

        return container;
    }

}
