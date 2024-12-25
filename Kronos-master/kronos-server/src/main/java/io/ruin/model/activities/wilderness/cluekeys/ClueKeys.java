package io.ruin.model.activities.wilderness.cluekeys;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.Spade;
import io.ruin.model.map.Position;
import io.ruin.model.map.route.RouteFinder;

import java.util.Arrays;

import static io.ruin.cache.ItemID.COINS_995;

/**
 * @author Adam Ali ("Kal-El") https://www.rune-server.ee/members/kal+el/
 */
public class ClueKeys {

    private final static int NPC_ID = 4067;

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


    static {
        Arrays.stream(KeyData.values()).forEach(keyData -> Spade.registerDig(keyData.getPosition().getX(), keyData.getPosition().getY(), keyData.getPosition().getZ(), player -> {
            if (!player.getInventory().contains(keyData.getItem())) {
                return;
            }
            player.getInventory().remove(keyData.getItem().getId(), keyData.getItem().getAmount());
            registerDemon(player);
        }));

        Arrays.stream(KeyData.values()).forEach(keyData -> {
            ItemAction.registerInventory(keyData.getItem().getId(), "Check-Hint", (player, item) -> {
                player.startEvent(event -> {
                    if (showHint(player, keyData)) {
                        player.getPacketSender().sendHintIcon(keyData.getPosition());
                    } else {
                        player.sendFilteredMessage(keyData.getClue());
                    }
                });
            });
        });

    }

    public static boolean showHint(Player player, KeyData data) {
        return data.getPosition().isWithinDistance(player.getPosition(), 10);
    }

    public static void registerDemon(Player player) {
        Position pos = RouteFinder.findWalkable(player.getPosition());
        NPC demon = new NPC(NPC_ID).spawn(pos).targetPlayer(player, true);
        demon.forceText("You dare disturb me!!");
        demon.attackTargetPlayer(() -> !player.getPosition().isWithinDistance(demon.getPosition()));
        demon.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            Item loot = REWARDS[Random.get(REWARDS.length - 1)];
            killer.player.getInventory().addOrDrop(loot.getId(), loot.getAmount());
            killer.player.getInventory().addOrDrop(COINS_995, 1000000);
            demon.remove();
        };
    }


}
