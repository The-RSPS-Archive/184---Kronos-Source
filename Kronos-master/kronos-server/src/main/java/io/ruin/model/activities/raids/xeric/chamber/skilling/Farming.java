package io.ruin.model.activities.raids.xeric.chamber.skilling;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.CapePerks;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

import java.util.concurrent.atomic.AtomicInteger;

import static io.ruin.model.skills.Tool.SEED_DIBBER;

public enum Farming {

    NOXIFER(55, 20903, 20901, 29997, 30009),
    GOLPAR(27, 20906, 20904, 29998, 30010),
    BUCHU(39, 20909, 20907, 29999, 30011);

    public final int levelReq, seedId, harvestId, objectIdStart, objectIdEnd;

    Farming(int levelReq, int seedId, int harvestId, int objectIdStart, int objectIdEnd) {
        this.levelReq = levelReq;
        this.seedId = seedId;
        this.harvestId = harvestId;
        this.objectIdStart = objectIdStart;
        this.objectIdEnd = objectIdEnd;
    }

    /**
     * Herb patches
     */
    static {
        ObjectAction.register(29765, "inspect", (player, obj) -> {
            player.sendFilteredMessage("The herb patch appears to be empty.");
        });
        for (Farming patch : values()) {
            ItemObjectAction.register(patch.seedId, 29765, (player, item, obj) -> plantSeed(player, item, obj, patch));
            ObjectAction.register(patch.objectIdEnd, "pick", (player, obj) -> pickHerbPatch(player, obj, patch));
            for (int i = 0; i < 5; i++)
                ObjectAction.register(patch.objectIdStart + (3 * i), "inspect", (player, obj) -> inspectHerbPatch(player, obj, patch));
            ObjectAction.register(patch.objectIdEnd, "clear", Farming::clearHerbPatch);
        }
    }

    private static void plantSeed(Player player, Item item, GameObject object, Farming patch) {
        if (!player.getStats().check(StatType.Farming, patch.levelReq, SEED_DIBBER, patch.seedId, "plant a " + item.getDef().name))
            return;
        if (!player.getInventory().contains(Tool.SEED_DIBBER, 1)) {
            player.sendMessage("You need a seed dibber to plant seeds.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.animate(2291);
            event.delay(2);
            player.getInventory().remove(patch.seedId, 1);
            player.getStats().addXp(StatType.Farming, 6, false);
            player.sendFilteredMessage("You plant the " + item.getDef().name + " in the herb patch.");
            growHerbPatch(object, patch.objectIdStart);
            player.unlock();
        });
    }

    private static void growHerbPatch(GameObject object, int patchId) {
        World.startEvent(event -> {
            for (int i = 0; i < 5; i++) {
                object.setId(patchId + (3 * i));
                event.delay(13);
            }
        });
    }

    private static void pickHerbPatch(Player player, GameObject object, Farming patch) {
        AtomicInteger produceCount = new AtomicInteger(calculateProduceAmount(player));
        player.startEvent(event -> {
            if (!player.getInventory().contains(952, 1)) {
                player.sendMessage("You'll need a spade to harvest your crops.");
                return;
            }
            if (player.getInventory().getFreeSlots() == 0) {
                player.sendMessage("Not enough space in your inventory.");
                return;
            }
            player.sendFilteredMessage("You begin to harvest the herb patch.");
            while (true) {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.sendMessage("Not enough space in your inventory.");
                    return;
                }
                player.animate(2282, 2);
                event.delay(2);
                player.collectResource(patch.harvestId, 1);
                player.getInventory().add(patch.harvestId, 1);
                player.getStats().addXp(StatType.Farming, 15, false);
                produceCount.getAndDecrement();
                if (Random.rollPercent(10) || produceCount.get() == 0) {
                    object.setId(29765);
                    player.sendMessage("The herb patch is now empty.");
                    return;
                }
                event.delay(1);
            }
        });
    }

    public static int calculateProduceAmount(Player player) {
        int amount = Random.get(8, 10);

        if (CapePerks.wearsFarmingCape(player)) {
            amount += Random.get(1, 2);
        }
        return amount;
    }

    private static void inspectHerbPatch(Player player, GameObject object, Farming patch) {
        if (object.id == patch.objectIdEnd)
            player.sendFilteredMessage("This herb patch is ready to be picked.");
        else
            player.sendFilteredMessage("There's a " + ItemDef.get(patch.seedId).name + " growing here.");
    }

    private static void clearHerbPatch(Player player, GameObject object) {
        player.startEvent(event -> {
            player.lock();
            player.animate(830);
            event.delay(1);
            object.setId(29765);
            player.sendMessage("You clear the farming patch..");
            if (player.raidsParty.getMembers().size() != 0)
                player.raidsParty.getMembers().forEach(m -> m.sendFilteredMessage(player.getName() + " has cleared the farming patch."));
            event.delay(1);
            player.unlock();
        });
    }


    /**
     * Weeds
     */
    public static final LootTable SEEDS = new LootTable().addTable(1,
            new LootItem(20903, 1, 3, 1),   //Noxifer seed
            new LootItem(20906, 1, 3, 1),  //Golpar seed
            new LootItem(20909, 1, 3, 1)   //Buchu seed
    );

    static {
        ObjectAction.register(29773, "rake", (player, obj) -> {
            if (!player.getInventory().hasId(Tool.RAKE)) {
                player.sendFilteredMessage("You need a rake to do this.");
                return;
            }

            player.animate(2273);
            player.startEvent(event -> {
                while (true) {
                    player.animate(2273);
                    player.privateSound(2442);
                    event.delay(4);
                    if (Random.get() < 0.8) {
                        Item randomSeed = SEEDS.rollItem();
                        player.getInventory().add(randomSeed);
                        player.sendFilteredMessage("You find " + (randomSeed.getAmount() > 1 ? "several " : "a ") +
                                randomSeed.getDef().name + (randomSeed.getAmount() > 1 ? "s!" : "!"));
                    }
                }
            });
        });
    }
}
