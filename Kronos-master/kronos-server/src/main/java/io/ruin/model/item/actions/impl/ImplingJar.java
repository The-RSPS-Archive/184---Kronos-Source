package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import lombok.Getter;

public enum ImplingJar {

    BABY_IMPLING_JAR(11238, new LootTable().addTable(1,
            new LootItem(1755, 1, 10),        //Chisel
            new LootItem(1734, 1, 10),        //Thread
            new LootItem(1733, 1, 10),        //Needle
            new LootItem(946, 1, 10),         //Knife
            new LootItem(1985, 1, 10),        //Cheese
            new LootItem(2347, 1, 10),        //Hammer
            new LootItem(1759, 1, 10),        //Ball of wool
            new LootItem(1927, 1, 5),         //Bucket of milk
            new LootItem(319, 1, 5),          //Anchovies
            new LootItem(2007, 1, 5),         //Spice
            new LootItem(1779, 1, 5),         //Flax
            new LootItem(7170, 1, 5),         //Mud pie
            new LootItem(401, 1, 5),          //Seaweed
            new LootItem(1438, 1, 5),         //Air talisman
            new LootItem(2355, 1, 1),         //Silver bar
            new LootItem(1607, 1, 1),         //Sapphire
            new LootItem(1743, 1, 1),         //Hard leather
            new LootItem(379, 1, 1),          //Lobster
            new LootItem(1761, 1, 1),         //Soft clay
            new LootItem(2677, 1, 1)          //Clue scroll (easy)
    )),
    YOUNG_IMPLING_JAR(11240, new LootTable().addTable(1,
            new LootItem(361, 1, 10),         //Tuna
            new LootItem(1901, 1, 10),        //Chocolate slice
            new LootItem(1523, 1, 10),        //Lockpick
            new LootItem(1539, 5, 10),        //Steel nails
            new LootItem(7936, 1, 10),        //Pure essence
            new LootItem(855, 1, 5),          //Yew longbow
            new LootItem(1353, 1, 5),         //Steel axe
            new LootItem(453, 1, 5),          //Coal
            new LootItem(1777, 1, 5),         //Bow string
            new LootItem(231, 1, 5),          //Snape grass
            new LootItem(1761, 1, 5),         //Soft clay
            new LootItem(2677, 1, 5),         //Clue scroll (easy)
            new LootItem(1097, 1, 1),         //Studded chaps
            new LootItem(1157, 1, 1),         //Steel full helm
            new LootItem(8778, 1, 1),         //Oak plank
            new LootItem(133, 1, 1),          //Defence potion(3)
            new LootItem(2359, 1, 1)          //Mithril bar
    )),
    GOURMET_IMPLING_JAR(11242, new LootTable().addTable(1,
            new LootItem(365, 1, 10),         //Bass
            new LootItem(361, 1, 10),         //Tuna
            new LootItem(2011, 1, 10),        //Curry
            new LootItem(2327, 1, 10),        //Meat pie
            new LootItem(1897, 1, 10),        //Chocolate cake
            new LootItem(2293, 1, 10),        //Meat pizza
            new LootItem(5004, 1, 10),        //Frog spawn
            new LootItem(2007, 1, 10),        //Spice
            new LootItem(5970, 1, 10),        //Curry leaf
            new LootItem(1883, 1, 5),         //Ugthanki kebab
            new LootItem(380, 4, 5),          //Lobster
            new LootItem(386, 3, 5),          //Shark
            new LootItem(7178, 1, 5),         //Garden pie
            new LootItem(7188, 1, 5),         //Fish pie
            new LootItem(3145, 2, 5),         //Cooked Karambwan
            new LootItem(2677, 1, 5),         //Clue scroll (easy)
            new LootItem(7179, 6, 1),         //Garden pie
            new LootItem(374, 3, 1),          //Swordfish
            new LootItem(7218, 1, 1)          //Summer pie
    )),
    EARTH_IMPLING_JAR(11244, new LootTable().addTable(1,
            new LootItem(1442, 1, 10),        //Fire talisman
            new LootItem(1440, 1, 10),        //Earth talisman
            new LootItem(5535, 1, 10),        //Earth tiara
            new LootItem(557, 32, 10),        //Earth rune
            new LootItem(447, 1, 3, 10),      //Mithril ore
            new LootItem(237, 1, 10),         //Unicorn horn
            new LootItem(2353, 1, 10),        //Steel bar
            new LootItem(1273, 1, 10),        //Mithril Pickaxe
            new LootItem(5311, 2, 10),        //Wildblood seed
            new LootItem(5104, 2, 10),        //Jangerberry seed
            new LootItem(6033, 6, 10),        //Compost
            new LootItem(6035, 2, 5),         //Super compost
            new LootItem(1784, 4, 5),         //Bucket of sand
            new LootItem(5294, 2, 5),         //Harralander seed
            new LootItem(454, 2, 5),          //Coal
            new LootItem(444, 1, 1),          //Gold ore
            new LootItem(1622, 2, 1),         //Uncut emerald
            new LootItem(1606, 2, 1),         //Emerald
            new LootItem(1603, 1, 1),         //Ruby
            new LootItem(2801, 1, 1)          //Clue scroll (medium)
    )),
    ESSENCE_IMPLING_JAR(11246, new LootTable().addTable(1,
            new LootItem(7937, 20, 35, 10),   //Pure essence
            new LootItem(555, 30, 10),        //Water rune
            new LootItem(556, 30, 10),        //Air rune
            new LootItem(558, 25, 10),        //Mind rune
            new LootItem(559, 28, 10),        //Body rune
            new LootItem(562, 4, 10),         //Chaos rune
            new LootItem(1448, 1, 10),        //Mind talisman
            new LootItem(564, 4, 5),          //Cosmic rune
            new LootItem(2801, 1, 5),         //Clue scroll (medium)
            new LootItem(563, 13, 1),         //Law rune
            new LootItem(565, 7, 1),          //Blood rune
            new LootItem(566, 11, 1),         //Soul rune
            new LootItem(561, 13, 1)          //Nature rune
    )),
    ECLECTIC_IMPLING_JAR(11248, new LootTable().addTable(1,
            new LootItem(1273, 1, 10),        //Mithril Pickaxe
            new LootItem(5970, 1, 10),        //Curry leaf
            new LootItem(231, 1, 10),         //Snape grass
            new LootItem(556, 30, 60, 10),    //Air rune
            new LootItem(8779, 4, 10),        //Oak plank
            new LootItem(2358, 5, 10),        //Gold bar
            new LootItem(444, 1, 10),         //Gold ore
            new LootItem(4527, 1, 10),        //Empty candle lantern
            new LootItem(237, 1, 10),         //Unicorn horn
            new LootItem(2801, 1, 5),         //Clue scroll (medium)
            new LootItem(1199, 1, 1),         //Adamant kiteshield
            new LootItem(2493, 1, 1),         //Blue d'hide chaps
            new LootItem(10083, 1, 1),        //Red spiky vambs
            new LootItem(1213, 1, 1),         //Rune dagger
            new LootItem(1391, 1, 1),         //Battlestaff
            new LootItem(7208, 1, 1),         //Wild pie
            new LootItem(5321, 3, 1),         //Watermelon seed
            new LootItem(450, 10, 1),         //Adamant ore
            new LootItem(1601, 1, 1)          //Diamond
    )),
    NATURE_IMPLING_JAR(11250, new LootTable().addTable(1,
            new LootItem(5100, 1, 10),        //Limpwurt seed
            new LootItem(5104, 1, 10),        //Jangerberry seed
            new LootItem(5281, 1, 10),        //Belladonna seed
            new LootItem(5294, 1, 10),        //Harralander seed
            new LootItem(6016, 1, 10),        //Cactus spine
            new LootItem(1513, 1, 10),        //Magic logs
            new LootItem(254, 4, 10),         //Tarromin
            new LootItem(5974, 1, 10),        //Coconut
            new LootItem(5297, 1, 10),        //Irit seed
            new LootItem(5286, 1, 1),         //Curry tree seed
            new LootItem(5285, 1, 1),         //Orange tree seed
            new LootItem(3000, 1, 1),         //Snapdragon
            new LootItem(263, 1, 1),          //Kwuarm seed
            new LootItem(261, 1, 1),          //Avantoe seed
            new LootItem(2722, 1, 1),         //Clue scroll (hard)
            new LootItem(5313, 1, 1),         //Willow seed
            new LootItem(5304, 1, 1),         //Torstol seed
            new LootItem(5295, 1, 1),         //Ranarr seed
            new LootItem(270, 2, 1),          //Torstol
            new LootItem(5303, 1, 1)          //Dwarf weed seed
    )),
    MAGPIE_IMPLING_JAR(11252, new LootTable().addTable(1,
            new LootItem(1701, 3, 5),         //Diamond amulet
            new LootItem(1732, 3, 5),         //Amulet of power
            new LootItem(2569, 3, 5),         //Ring of forging
            new LootItem(4097, 1, 5),         //Mystic boots
            new LootItem(4095, 1, 5),         //Mystic gloves
            new LootItem(1347, 1, 5),         //Rune warhammer
            new LootItem(2571, 4, 5),         //Ring of life
            new LootItem(1215, 1, 5),         //Dragon dagger
            new LootItem(1185, 1, 5),         //Rune sq shield
            new LootItem(3391, 1, 5),         //Splitbark gauntlets
            new LootItem(5541, 1, 5),         //Nature tiara
            new LootItem(1748, 6, 5),         //Black dragonhide
            new LootItem(2364, 2, 5),         //Runite bar
            new LootItem(1602, 4, 5),         //Diamond
            new LootItem(5287, 1, 5),         //Pineapple seed
            new LootItem(987, 1, 5),          //Loop half of key
            new LootItem(985, 1, 5),          //Tooth half of key
            new LootItem(993, 1, 5),          //Sinister key
            new LootItem(5300, 1, 5),         //Snapdragon seed
            new LootItem(2722, 1, 1)          //Clue scroll (hard)
    )),
    NINJA_IMPLING_JAR(11254, new LootTable().addTable(1,
            new LootItem(6328, 1, 4),         //Snakeskin boots
            new LootItem(3385, 1, 4),         //Splitbark helm
            new LootItem(4097, 1, 4),         //Mystic boots
            new LootItem(1113, 1, 4),         //Rune chainbody
            new LootItem(4095, 1, 4),         //Mystic gloves
            new LootItem(6313, 1, 4),         //Opal machete
            new LootItem(3101, 1, 4),         //Rune claws
            new LootItem(1333, 1, 4),         //Rune scimitar
            new LootItem(5680, 1, 4),         //Dragon dagger(p+)
            new LootItem(892, 70, 4),         //Rune arrow
            new LootItem(811, 70, 4),         //Rune dart
            new LootItem(868, 40, 4),         //Rune knife
            new LootItem(805, 50, 4),         //Rune thrownaxe
            new LootItem(9342, 2, 4),         //Onyx bolts
            new LootItem(9194, 4, 4),         //Onyx bolt tips
            new LootItem(1748, 10, 16, 4),    //Black dragonhide
            new LootItem(2435, 4, 4),         //Prayer potion(3)
            new LootItem(5938, 4, 4),         //Weapon poison(+)
            new LootItem(6156, 3, 4),         //Dagannoth hide
            new LootItem(2722, 1, 10)         //Clue scroll (hard)
    )),
    DRAGON_IMPLING_JAR(11256, new LootTable().addTable(1,
            new LootItem(4093, 1, 3),         //Mystic robe bottom
            new LootItem(1705, 3, 3),         //Amulet of glory
            new LootItem(1703, 2, 3),         //Dragonstone amulet
            new LootItem(11212, 100, 250, 3), //Dragon arrow
            new LootItem(9341, 10, 40, 3),    //Dragonstone bolts
            new LootItem(1305, 1, 3),         //Dragon longsword
            new LootItem(5698, 1, 3),         //Dragon dagger(p++)
            new LootItem(11230, 100, 250, 3), //Dragon dart
            new LootItem(9193, 10, 30, 3),    //Dragonstone bolt tips
            new LootItem(1616, 3, 3),         //Dragonstone
            new LootItem(11232, 100, 350, 3), //Dragon dart tip
            new LootItem(11237, 100, 350, 3), //Dragon arrowtips
            new LootItem(19582, 25, 35, 3),   //Dragon javelin heads
            new LootItem(535, 100, 300, 3),   //Babydragon bones
            new LootItem(537, 50, 100, 3),    //Dragon bones
            new LootItem(5316, 1, 3),         //Magic seed
            new LootItem(5300, 6, 3),         //Snapdragon seed
            new LootItem(7219, 15, 3),        //Summer pie
            new LootItem(4093, 1, 3)          //Clue scroll (elite)

    )),
    LUCKY_IMPLING(19732, new LootTable()), // Handled manually
    ;

    @Getter
    private final int itemId;
    @Getter
    private final LootTable lootTable;

    ImplingJar(int itemId, LootTable lootTable) {
        this.itemId = itemId;
        this.lootTable = lootTable;
    }

    public static ImplingJar forJarId(int id) {
        for (ImplingJar jar : values()) {
            if (jar.itemId == id) {
                return jar;
            }
        }
        return null;
    }

    public static final int IMPLING_JAR = 11260;

    private static void loot(Player player, Item item, ImplingJar jar) {
        // OSRS demands 2 free slots.
        if (player.getInventory().getFreeSlots() < 2) {
            player.sendMessage("You'll need to clear some space in your pack before looting the jar.");
            return;
        }

        if (Random.rollDie(10, 1)) {
            item.remove();
            player.sendMessage("You break the jar as you try and open it. You throw the shattered remains away.");
        } else {
            item.setId(IMPLING_JAR);
        }

        Item loot;
        if (jar == LUCKY_IMPLING) {
            ClueType randomClue = Random.get(ClueType.values());
            loot = new LootTable().addTable(1, randomClue.loots).rollItem();
        } else {
            loot = jar.lootTable.rollItem();
        }

        player.getInventory().addOrDrop(loot);
    }

    static {
        for (ImplingJar jar : ImplingJar.values()) {
            ItemAction.registerInventory(jar.itemId, "loot", (player, item) -> loot(player, item, jar));
        }
    }

}
