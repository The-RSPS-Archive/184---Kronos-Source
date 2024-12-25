package io.ruin.model.item.actions.impl.boxes;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

import static io.ruin.cache.ItemID.BLOOD_MONEY;

public class WildernessRewardBox {

    /**
     * Wilderness reward box item ids
     */
    private static final int SMALL_BOX = 2730;
    private static final int MEDIUM_BOX = 2732;
    private static final int LARGE_BOX = 2734;
    private static final int GIANT_BOX = 2736;

    /**
     * Wilderness reward box roll chances
     */
    private static final int SMALL_BOX_CHANCE = 25;
    private static final int MEDIUM_BOX_CHANCE = 75;
    private static final int LARGE_BOX_CHANCE = 150;
    private static final int GIANT_BOX_CHANCE = 500;

    /**
     * Wilderness reward box loot tables
     */
    private static final LootTable small = new LootTable().addTable(1,
            new LootItem(BLOOD_MONEY, 1, 100, 20),    // Blood money
            new LootItem(6914, 1, 3),             // Masters wand
            new LootItem(4151, 1, 3),             // Abyssal whip
            new LootItem(20595, 1, 3),            // Elder chaos hood
            new LootItem(20517, 1, 3),            // Elder chaos top
            new LootItem(20520, 1, 3),            // Elder chaos robe
            new LootItem(4153, 1, 3),             // Granite maul
            new LootItem(6528, 1, 3),             // Obsidian maul
            new LootItem(10887, 1, 3),            // Barrelchest anchor
            new LootItem(1249, 1, 3),             // Dragon spear
            new LootItem(11128, 1, 3),            // Berserker necklace
            new LootItem(6585, 1, 1)              // Amulet of fury
    );

    private static final LootTable medium = new LootTable().addTable(1,
            new LootItem(BLOOD_MONEY, 100, 300, 20),   // Blood money
            new LootItem(6914, 1, 3),             // Masters wand
            new LootItem(4151, 1, 3),             // Abyssal whip
            new LootItem(20128, 1, 3),            // Hood of darkness
            new LootItem(20131, 1, 3),            // Robe top of darkness
            new LootItem(20137, 1, 3),            // Robe bottom of darkness
            new LootItem(4153, 1, 3),             // Granite maul
            new LootItem(6528, 1, 3),             // Obsidian maul
            new LootItem(10887, 1, 3),            // Barrelchest anchor
            new LootItem(1249, 1, 3),             // Dragon spear
            new LootItem(11128, 1, 3),            // Berserker necklace
            new LootItem(4716, 1, 3),             // Dharok's helm
            new LootItem(4718, 1, 3),             // Dharok's greataxe
            new LootItem(4720, 1, 3),             // Dharok's platebody
            new LootItem(4722, 1, 3),             // Dharok's platelegs
            new LootItem(4708, 1, 3),             // Ahrim's hood
            new LootItem(4712, 1, 3),             // Ahrim's robetop
            new LootItem(4714, 1, 3),             // Ahrim's robeskirt
            new LootItem(6585, 1, 3),             // Dragon boots
            new LootItem(12831, 1, 3),            // Blessed Spirit shield
            new LootItem(6733, 1, 3),             // Archer ring
            new LootItem(6735, 1, 3),             // Warrior ring
            new LootItem(6920, 1, 3),             // Infinity boots
            new LootItem(6585, 1, 1)              // Amulet of fury
    );

    private static final LootTable big = new LootTable().addTable(1,
            new LootItem(BLOOD_MONEY, 300, 500, 20),   // Blood money
            new LootItem(6914, 1, 3),              // Masters wand
            new LootItem(12006, 1, 3),             // Abyssal tentacle
            new LootItem(20128, 1, 3),             // Hood of darkness
            new LootItem(20131, 1, 3),             // Robe top of darkness
            new LootItem(20137, 1, 3),             // Robe bottom of darkness
            new LootItem(4153, 1, 3),              // Granite maul
            new LootItem(6889, 1, 3),              // Mage's book
            new LootItem(11235, 1, 3),             // Dark bow
            new LootItem(11128, 1, 3),             // Berserker necklace
            new LootItem(4716, 1, 3),              // Dharok's helm
            new LootItem(4718, 1, 3),              // Dharok's greataxe
            new LootItem(4720, 1, 3),              // Dharok's platebody
            new LootItem(4722, 1, 3),              // Dharok's platelegs
            new LootItem(4708, 1, 3),              // Ahrim's hood
            new LootItem(4712, 1, 3),              // Ahrim's robetop
            new LootItem(4714, 1, 3),              // Ahrim's robeskirt
            new LootItem(6585, 1, 3),              // Dragon boots
            new LootItem(12831, 1, 3),             // Blessed Spirit shield
            new LootItem(6737, 1, 3),              // Berserker ring
            new LootItem(6731, 1, 3),              // Seers ring
            new LootItem(6920, 1, 3),              // Infinity boots
            new LootItem(13237, 1, 3),             // Pegasian boots
            new LootItem(13239, 1, 3),             // Primordial boots
            new LootItem(19478, 1, 3),             // Heavy ballista
            new LootItem(6585, 1, 1)               // Amulet of fury
    );

    private static final LootTable giant = new LootTable().addTable(1,
            new LootItem(BLOOD_MONEY, 500, 1000, 20),  // Blood money
            new LootItem(6914, 1, 3),              // Masters wand
            new LootItem(12006, 1, 3),             // Abyssal tentacle
            new LootItem(6889, 1, 3),              // Mage's book
            new LootItem(6737, 1, 3),              // Berserker ring
            new LootItem(6731, 1, 3),              // Seers ring
            new LootItem(6920, 1, 3),              // Infinity boots
            new LootItem(13237, 1, 3),             // Pegasian boots
            new LootItem(13239, 1, 3),             // Primordial boots
            new LootItem(19478, 1, 3),             // Heavy ballista
            new LootItem(11802, 1, 3),             // Armadyl godsword
            new LootItem(11804, 1, 3),             // Bandos godsword
            new LootItem(13271, 1, 3),             // Abyssal dagger
            new LootItem(11283, 1, 3),             // Dragon fireshield
            new LootItem(11832, 1, 3),             // Bandos chestplate
            new LootItem(11834, 1, 3),             // Bandos tassets
            new LootItem(6585, 1, 1)               // Amulet of fury
    );

    public static int rollForDrop(Player player) {
        // roll for giant
        if(Random.rollDie((int) Math.ceil(GIANT_BOX_CHANCE * donatorBonus(player)), 1)) {
            player.sendFilteredMessage("<col=6f0000>You've received a giant wilderness reward box!");
            return GIANT_BOX;
        }

        // roll for large
        if(Random.rollDie((int) Math.ceil(LARGE_BOX_CHANCE * donatorBonus(player)), 1)) {
            player.sendFilteredMessage("<col=6f0000>You've received a large wilderness reward box!");
            return LARGE_BOX;
        }

        // roll for medium
        if(Random.rollDie((int) Math.ceil(MEDIUM_BOX_CHANCE * donatorBonus(player)), 1)) {
            player.sendFilteredMessage("<col=6f0000>You've received a medium wilderness reward box!");
            return MEDIUM_BOX;
        }

        // roll for small
        if(Random.rollDie((int) Math.ceil(SMALL_BOX_CHANCE * donatorBonus(player)), 1)) {
            player.sendFilteredMessage("<col=6f0000>You've received a small wilderness reward box!");
            return SMALL_BOX;
        }

        return -1;
    }

    private static double donatorBonus(Player player) {
        if (player.isGroup(PlayerGroup.ZENYTE)) {
            return 0.75;
        } else if (player.isGroup(PlayerGroup.ONYX)) {
            return 0.80;
        } else if (player.isGroup(PlayerGroup.DRAGONSTONE)) {
            return 0.85;
        } else if (player.isGroup(PlayerGroup.DIAMOND)) {
            return 0.87;
        } else if (player.isGroup(PlayerGroup.RUBY)) {
            return 0.90;
        } else if (player.isGroup(PlayerGroup.SAPPHIRE)) {
            return 0.93;
        } else if (player.isGroup(PlayerGroup.EMERALD)) {
            return 0.95;
        } else {
            return 1.00;
        }
    }

    private static void open(Player player, Item item, LootTable table) {
        player.lock();
        Item reward = table.rollItem();
        item.remove();
        player.getInventory().add(reward);
        player.sendMessage(Color.DARK_RED.wrap("You open the wilderness reward box and find " + reward.getDef().descriptiveName + "!"));
        player.unlock();
    }

    static {
        ItemAction.registerInventory(SMALL_BOX, "open", (player, item) -> open(player, item, small));
        ItemAction.registerInventory(MEDIUM_BOX, "open", (player, item) -> open(player, item, medium));
        ItemAction.registerInventory(LARGE_BOX, "open", (player, item) -> open(player, item, big));
        ItemAction.registerInventory(GIANT_BOX, "open", (player, item) -> open(player, item, giant));
    }


}
