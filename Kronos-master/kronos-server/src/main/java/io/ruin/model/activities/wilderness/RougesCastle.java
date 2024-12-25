package io.ruin.model.activities.wilderness;


import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.stat.StatType;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public class RougesCastle {

    public static final LootTable ECO_TABLE = new LootTable().addTable(1,

            new LootItem(COINS_995, 1500, 5000, 100),      //1500-5000 coins
            new LootItem(560, 30, 100),              // 30 death runes
            new LootItem(554, 30, 100),              // 30 fire runes
            new LootItem(558, 25, 100),              // 25 mind runes
            new LootItem(562, 40, 100),              // 40 chaos runes
            new LootItem(1608, 6, 100),              // 6 sapphires
            new LootItem(1606, 5, 100),              // 5 emeralds
            new LootItem(1602, 1, 3, 100),           // 1-3 diamonds
            new LootItem(386, 10, 100),              // 10 sharks
            new LootItem(352, 20, 100),              // 20 pike
            new LootItem(360, 15, 100),              // 15 raw tuna
            new LootItem(593, 25, 100),              // 25 ashes
            new LootItem(441, 10, 100),              // 10 iron ore
            new LootItem(454, 13, 100),              // 13 coal
            new LootItem(590, 3, 100),               // tinderbox
            new LootItem(1616, 3, 20)               // 2 dragonstones
    );

    public static final LootTable PVP_TABLE = new LootTable().addTable(1,
                    new LootItem(BLOOD_MONEY, 1, 10, 10), //Blood money
                    new LootItem(BLOOD_MONEY, 10, 25, 4), //Blood money
                    new LootItem(BLOOD_MONEY, 25, 50, 2), //Blood money
                    new LootItem(BLOOD_MONEY, 50, 100, 1), //Blood money

                    new LootItem(12702, 4, 8, 4), //Super combat potion(1)
                    new LootItem(12700, 4, 8, 3), //Super combat potion(2)
                    new LootItem(12698, 4, 8, 2), //Super combat potion(3)
                    new LootItem(12696, 4, 8, 1), //Super combat potion(4)

                    new LootItem(11937, 4, 6, 5), //Dark Crab
                    new LootItem(11937, 8, 10, 4), //Dark Crab
                    new LootItem(11937, 15, 25, 3), //Dark Crab
                    new LootItem(11937, 20, 40, 2), //Dark Crab
                    new LootItem(11937, 40, 50, 1), //Dark Crab

                    new LootItem(13442, 4, 6, 5), //Anglerfish
                    new LootItem(13442, 8, 10, 4), //Anglerfish
                    new LootItem(13442, 15, 25, 3), //Anglerfish
                    new LootItem(13442, 20, 40, 2), //Anglerfish
                    new LootItem(13442, 40, 50, 1) //Anglerfish
    );

    private static void openChest(Player player) {
        if(player.getCombat().getLevel() <= 87) {
            player.sendMessage("You have to be level 87 or higher combat to open this chest!");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.animate(537);
            player.hit(new Hit().randDamage(player.getHp() > 10 ? 15 : 5));
            player.sendMessage("You have activated a trap on the chest.");
            event.delay(1);
            player.unlock();
        });
    }

    private static void searchForTraps(Player player, GameObject chest) {
        if(player.getStats().get(StatType.Thieving).currentLevel <= 84) {
            player.sendMessage("You have to be level 84 thieving to open this chest!");
            return;
        }

        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.sendMessage("You search the chest for traps.");
            player.sendMessage("You find a trap on the chest.");
            event.delay(2);
            player.sendMessage("You disable the trap.");
            player.animate(535);
            player.getInventory().add(PVP_TABLE.rollItem());
            PlayerCounter.ROGUES_CASTLE_CHESTS.increment(player, 1);
            rougesAction(player);
            replaceChest(chest);
            player.getStats().addXp(StatType.Thieving, 100, true);
            player.unlock();
        });
    }

    private static void replaceChest(GameObject chest) {
        World.startEvent(event -> {
            chest.setId(26758);
            event.delay(40);
            chest.setId(chest.originalId);
        });
    }

    private static void rougesAction(Player player) {
        for(NPC npc : player.localNpcs()) {
            if(npc.getId() != 6603)
                continue;
            if(npc.getPosition().isWithinDistance(player.getPosition(), 7)) {
                npc.face(player);
                npc.getCombat().setTarget(player);
                npc.forceText("Someone's stealing from us, get them!");
            }
        }
    }

    static {
        /**
         * Chest
         */
        ObjectAction.register(26757, "open", (player, obj) -> openChest(player));
        ObjectAction.register(26757, "search for traps", RougesCastle::searchForTraps);

        /**
         * Ladders
         */
        ObjectAction.register(14735, 3280, 3936, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3282, 3936, player.getHeight() + 1, true, true, true);
        });
        /* first floor */
        ObjectAction.register(14736, 3280, 3936, 1, "climb", (player, obj) -> player.dialogue(
                new OptionsDialogue("Climb up or down the ladder?",
                        new Option("Climb Up.", () -> Ladder.climb(player, 3282, 3936, player.getHeight() + 1, true, true, true)),
                        new Option("Climb Down.", () -> Ladder.climb(player, 3281, 3935, player.getHeight() - 1, false, true, true))
                )));
        ObjectAction.register(14736, 3280, 3936, 1, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3282, 3936, player.getHeight() + 1, true, true, true);
        });
        ObjectAction.register(14736, 3280, 3936, 1, "climb-down", (player, obj) -> {
            Ladder.climb(player, 3281, 3935, player.getHeight() - 1, false, true, true);
        });
        /* second floor */
        ObjectAction.register(14736, 3280, 3936, 2, "climb", (player, obj) -> player.dialogue(
                new OptionsDialogue("Climb up or down the ladder?",
                        new Option("Climb Up.", () -> Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() + 1, true, true, true)),
                        new Option("Climb Down.", () -> Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() - 1, false, true, true))
                )));
        ObjectAction.register(14736, 3280, 3936, 2, "climb-up", (player, obj) -> {
            Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() + 1, true, true, true);
        });
        ObjectAction.register(14736, 3280, 3936, 2, "climb-down", (player, obj) -> {
            Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() - 1, false, true, true);
        });
        ObjectAction.register(14737, 3281, 3936, 3, "climb-down", (player, obj) -> {
            Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() - 1, false, true, true);
        });
    }
}
