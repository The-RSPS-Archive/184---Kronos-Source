package io.ruin.model.skills.thieving;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import static io.ruin.cache.ItemID.COINS_995;

public class WallSafe {

    public static final LootTable table = new LootTable().addTable(1,
            new LootItem(COINS_995, 1800, 2200, 295),   //coins
            new LootItem(1623, 1, 100),     //sapphire 1/5
            new LootItem(1621, 1, 50),      //emerald 1/10
            new LootItem(1619, 1, 33),      //ruby 1/15
            new LootItem(1617, 1, 16),      //diamond 1/30
            new LootItem(1631, 1, 5),       //dragonstone 1/100
            new LootItem(6571, 1, 1)        //onyx 1/500
    );

    private static void attempt(Player player, GameObject wallSafe) {
        if (!player.getStats().check(StatType.Thieving, 50, "attempt this"))
            return;
        if (player.getInventory().isFull()) {
            player.sendMessage("You don't have enough inventory space to do that.");
            return;
        }
        player.startEvent(event -> {
            if (!player.isAt(wallSafe.x, wallSafe.y))
                event.delay(1);
            player.privateSound(1243);
            player.sendFilteredMessage("You start cracking the safe.");
            player.animate(2247);
            event.delay(2);
            double chance = 0.5 + (double) (player.getStats().get(StatType.Thieving).currentLevel - 50) * 0.01;
            if (Random.get() > Math.min(chance, 0.85)) {
                player.lock();
                player.privateSound(1242);
                player.sendFilteredMessage("You slip and trigger a trap!");
                player.hit(new Hit().randDamage(6));
                player.animate(1113);
                event.delay(2);
                player.resetAnimation();
                player.unlock();
            } else {
                player.privateSound(1243);
                player.animate(2248);
                event.delay(2);
                player.sendFilteredMessage("You get some loot.");
                player.privateSound(1238);
                player.getStats().addXp(StatType.Thieving, 70, true);
                player.getInventory().add(getLoot(player));
                if (Achievement.QUICK_HANDS.isFinished(player) && Random.rollPercent(10))
                    player.getInventory().add(getLoot(player));
                PlayerCounter.WALL_SAFES_CRACKED.increment(player, 1);
                openSafe(wallSafe);
            }
        });
    }

    private static void openSafe(GameObject wallSafe) {
        World.startEvent(event -> {
            wallSafe.setId(7238);
            event.delay(3);
            wallSafe.setId(7236);
        });
    }

    private static Item getLoot(Player player) {
        Item item = table.rollItem();
        if (item.getId() == COINS_995) {
            item.setAmount((int) (item.getAmount() * ((1 + (player.getStats().get(StatType.Thieving).fixedLevel - 49) * 0.02))));
        }
        return item;
    }

    static {
        ObjectAction.register(7236, "crack", WallSafe::attempt);
    }

}