package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

import static io.ruin.cache.ItemID.COINS_995;

/**
 * @author Adam Ali ("Kal-El") https://www.rune-server.ee/members/kal+el/
 */
public class WildernessDeadmanKey {

    private static final int CHEST_ID = 27290;

    private static final int DMM_KEY_ID = 13302;


    static {
        ObjectAction.register(CHEST_ID, "open", ((player, obj) -> openChest(player)));
    }

    private static Item[] REWARDS = {
            new Item(6914, 1),      // Masters wand
            new Item(4151, 1),      // Abyssal whip
            new Item(20128, 1),     // Hood of darkness
            new Item(20131, 1),     // Robe top of darkness
            new Item(20137, 1),     // Robe bottom of darkness
            new Item(4153, 1),      // Granite maul
            new Item(6528, 1),      // Obsidian maul
            new Item(10887, 1),     // Barrelchest anchor
            new Item(1249, 1),      // Dragon spear
            new Item(11128, 1),     // Berserker necklace
            new Item(4716, 1),      // Dharok's helm
            new Item(4718, 1),      // Dharok's greataxe
            new Item(4720, 1),      // Dharok's platebody
            new Item(4722, 1),      // Dharok's platelegs
            new Item(4708, 1),      // Ahrim's hood
            new Item(4712, 1),      // Ahrim's robetop
            new Item(4714, 1),      // Ahrim's robeskirt
            new Item(6585, 1),      // Dragon boots
            new Item(12831, 1),     // Blessed Spirit shield
            new Item(6733, 1),      // Archer ring
            new Item(6735, 1),      // Warrior ring
            new Item(6920, 1),      // Infinity boots
            new Item(6585, 1),      // Amulet of fury
            new Item(12397, 1)    // Royal Crown
    };


    private static void openChest(Player player) {
        Item dmmKey = player.getInventory().findItem(DMM_KEY_ID);

        if (dmmKey == null) {
            player.sendFilteredMessage("You need a key to open this chest");
            return;
        }

        player.startEvent(event -> {
            player.lock();
            player.sendFilteredMessage("You unlock the chest with your key");
            dmmKey.remove();
            player.animate(536);
            handleLoot(player);
            event.delay(1);
            player.unlock();
        });
    }

    private static void handleLoot(Player player) {
        Item loot = REWARDS[Random.get(REWARDS.length - 1)];
        player.getInventory().addOrDrop(loot.getId(), loot.getAmount());
        player.getInventory().addOrDrop(COINS_995, Random.get(100000, 150000));
        player.sendFilteredMessage(Color.BLUE.wrap("You have received " + ItemDef.get(loot.getId()).name + " from the Deadman chest!"));
    }

    public static void rollForDeadmanKey(Player killer, Player victim) {
        if (Random.rollDie(20, 1)) {
            killer.getInventory().addOrDrop(DMM_KEY_ID, 1);
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, killer.getName() + " has just received a PK Key by killing " + victim.getName() + "!");

        }
    }
}
