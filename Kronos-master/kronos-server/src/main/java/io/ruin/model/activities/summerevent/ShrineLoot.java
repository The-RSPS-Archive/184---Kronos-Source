package io.ruin.model.activities.summerevent;

import io.ruin.cache.ItemID;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/20/2020
 */
public class ShrineLoot {

    public static final LootTable shrineLoot = new LootTable()
            .addTable(40, // resources
                    new LootItem(13442, 20, 50, 10), //100 Anglerfish
                    new LootItem(ItemID.WRATH_RUNE, 100, 250, 10),
                    new LootItem(12696, 3, 5, 10), //super combat
                    new LootItem(3025, 3, 5, 10), //super restore
                    new LootItem(ItemID.BLOOD_RUNE, 500, 1000, 10),
                    new LootItem(ItemID.DEATH_RUNE, 750, 1500, 10),
                    new LootItem(386, 50, 100, 10) //shark
            ).addTable(29, //barrows & weapons
                    new LootItem(ItemID.GUTHANS_CHAINSKIRT, 1, 10),
                    new LootItem(ItemID.GUTHANS_WARSPEAR, 1, 10),
                    new LootItem(ItemID.GUTHANS_PLATEBODY, 1, 10),
                    new LootItem(ItemID.GUTHANS_HELM, 1, 10),
                    new LootItem(4708, 1, 10), //Ahrim's hood
                    new LootItem(4712, 1, 10), //Ahrim's robetop
                    new LootItem(4714, 1, 10), //Ahrim's robeskirt
                    new LootItem(4716, 1, 10), //Dharok's helm
                    new LootItem(4720, 1, 10), //Dharok's platebody
                    new LootItem(4722, 1, 10), //Dharok's platelegs
                    new LootItem(4718, 1, 10), //Dharok's greataxe
                    new LootItem(12639, 1, 10), //Guthix halo
                    new LootItem(12430, 1, 10), //Afro
                    new LootItem(12245, 1, 10), //Beanie
                    new LootItem(12638, 1, 10), //Zamorak halo
                    new LootItem(12637, 1, 10), //Saraodmin halo
                    new LootItem(4151, 1, 10) //whip
            ).addTable(1, //summer rares
                    new LootItem(30217, 1, 1), //summer pet
                    new LootItem(30224, 1, 1), //summer santa
                    new LootItem(30227, 1, 1), //summer h'ween
                    new LootItem(30221, 1, 1), //summer phat
                    new LootItem(30185, 1, 1) //summer mystery box
            );
}
