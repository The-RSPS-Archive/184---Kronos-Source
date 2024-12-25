package io.ruin.model.map.object.actions.impl.edgeville;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

import static io.ruin.cache.ItemID.COINS_995;

public class CrystalKeyChest {

    private static final Item[][] LOOTS = {
            /**
             * Spinach roll & coins
            */
            new Item[]{
                    new Item(13442, 60),
                    new Item(COINS_995, 300000)
            },
            /**
             * Raw swordfish & coins
            */
            new Item[]{
                    new Item(COINS_995, 1000000),
                    new Item(13440, 60)
            },
            /**
             * Runes
            */
            new Item[]{
                    new Item(560, 500),
                    new Item(566, 450),
                    new Item(565, 450)
            },
            /**
             * Coal ore
            */
            new Item[]{
                    new Item(454, 600)
            },
            /**
             * Gems
            */
            new Item[]{
                    new Item(1632, 10)
            },
            /**
             * Tooth half of a key & coins
            */
            new Item[]{
                    new Item(COINS_995, 750000),
                    new Item(985, 1)
            },
            /**
             * Runite bars
            */
            new Item[]{
                    new Item(2364, 20)
            },
            /**
             * Loop half of a key
            */
            new Item[]{
                    new Item(COINS_995, 750000),
                    new Item(987, 1)
            },
            /**
             * Iron ore
            */
            new Item[]{
                    new Item(450, 150)
            },
            /**
             * Adamant sq
            */
            new Item[]{
                    new Item(1202, 3)
            },
            /**
             * Rune platelegs/plateskirt
            */
            new Item[]{
                    new Item(1080, 3),
                    new Item(1094, 3)
            }
    };

    private static final Item[] RARE_LOOT = {
            /**
             * New crystal bow
            */
            new Item(4212, 1),
            /**
             * New crystal shield
            */
            new Item(4224, 1),
            /**
             * New crystal hally
            */
            new Item(13091, 1),
    };


    static {
        ObjectAction.register(172, "open", (player, obj) -> {
            Item crystalKey = player.getInventory().findItem(989);
            if (crystalKey == null) {
                player.sendFilteredMessage("You need a crystal key to open this chest.");
                return;
            }

            player.startEvent(event -> {
                player.lock();
                player.sendFilteredMessage("You unlock the chest with your key.");
                crystalKey.remove();
                player.privateSound(51);
                player.animate(536);
                World.startEvent(e -> {
                    obj.setId(173);
                    e.delay(2);
                    obj.setId(obj.originalId);
                });
                crystalKey.setId(1631); //dragonstone
                if(Random.get() <= 0.004) { //1/250
                    /**
                     * Rare loot
                     */
                    Item loot = RARE_LOOT[Random.get(RARE_LOOT.length - 1)];
                    player.getInventory().add(loot.getId(), loot.getAmount());
                    Broadcast.WORLD.sendNews(player.getName() + " just received " + loot.getDef().descriptiveName + " from the crystal chest!");
                } else {
                    /**
                     * Regular loot
                     */
                    Item[] loot = LOOTS[Random.get(LOOTS.length - 1)];
                    for(Item item : loot)
                        player.getInventory().addOrDrop(item.getId(), item.getAmount());
                }
                event.delay(1);
                player.unlock();
            });
        });
    }
}
