package io.ruin.model.activities.wilderness;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.Help;
import io.ruin.model.World;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Position;
import io.ruin.utility.Broadcast;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public class BossEvent extends JournalEntry {

    /* our beautiful bosses */
    public static final int[] BOSSES = {7286, 5129, 3358};

    /* amount of blood money per damage */
    private static final double BLOOD_MONEY_PER_DAMAGE = 2;

    /* amount of blood money based on wilderness level */
    private static final double BLOOD_MONEY_PER_WILD_LEVEL = 1;

    /* how long until the boss despawns (minutes) */
    private static final int ACTIVE_TIME = 25;

    private static int currentBoss = 0;
    private static long nextSpawn = 0;
    public static NPC boss;

    /**
     * Spawn locations
     */
    private static final Position[] SPAWN_LOCATION = {
            new Position(3307, 3663, 0),
            new Position(3158, 3848, 0),
            new Position(3034, 3682, 0),
            new Position(3151, 3886, 0),
            new Position(3261, 3786, 0)
    };

    private static final String[] LOCATING_NAME = {
            "north of hill giants",
            "east of lava maze",
            "inside the bandit camp",
            "west of giant spider hill",
            "south east of lava dragon isle"
    };

    /**
     * Loot tables
     */
    private static final LootTable LOOT_TABLE = new LootTable().addTable(1,
            new LootItem(BLOOD_MONEY, 250, 1500, 20),   // Blood money
            new LootItem(6914, 1, 5),             // Masters wand
            new LootItem(12006, 1, 5),            // Abyssal tentacle
            new LootItem(20128, 1, 5),            // Hood of darkness
            new LootItem(20131, 1, 5),            // Robe top of darkness
            new LootItem(20137, 1, 5),            // Robe bottom of darkness
            new LootItem(4153, 1, 5),             // Granite maul
            new LootItem(6889, 1, 5),             // Mage's book
            new LootItem(11235, 1, 5),            // Dark bow
            new LootItem(11128, 1, 5),            // Berserker necklace
            new LootItem(4716, 1, 5),             // Dharok's helm
            new LootItem(4718, 1, 5),             // Dharok's greataxe
            new LootItem(4720, 1, 5),             // Dharok's platebody
            new LootItem(4722, 1, 5),             // Dharok's platelegs
            new LootItem(4712, 1, 5),             // Ahrim's robetop
            new LootItem(4714, 1, 5),             // Ahrim's robeskirt
            new LootItem(6585, 1, 5),             // Dragon boots
            new LootItem(12831, 1, 5),            // Blessed Spirit shield
            new LootItem(6585, 1, 5),             // Amulet of fury
            new LootItem(6914, 1, 1),             // Masters wand
            new LootItem(12006, 1, 1),            // Abyssal tentacle
            new LootItem(6889, 1, 1),             // Mage's book
            new LootItem(6737, 1, 1),             // Berserker ring
            new LootItem(6731, 1, 1),             // Seers ring
            new LootItem(19478, 1, 1),            // Heavy ballista
            new LootItem(11802, 1, 1),            // Armadyl godsword
            new LootItem(13271, 1, 1),            // Abyssal dagger
            new LootItem(11283, 1, 1),            // Dragon fireshield
            new LootItem(6585, 1, 1)              // Amulet of fury
    );

    private static final LootTable ECO_LOOT_TABLE = new LootTable().addTable(1,
            new LootItem(6914, 1, 10),             // Masters wand
            new LootItem(12006, 1, 10),            // Abyssal tentacle
            new LootItem(20128, 1, 10),            // Hood of darkness
            new LootItem(20131, 1, 10),            // Robe top of darkness
            new LootItem(20137, 1, 10),            // Robe bottom of darkness
            new LootItem(4153, 1, 10),             // Granite maul
            new LootItem(6889, 1, 10),             // Mage's book
            new LootItem(11235, 1, 10),            // Dark bow
            new LootItem(11128, 1, 10),            // Berserker necklace
            new LootItem(4716, 1, 10),             // Dharok's helm
            new LootItem(4718, 1, 10),             // Dharok's greataxe
            new LootItem(4720, 1, 10),             // Dharok's platebody
            new LootItem(4722, 1, 10),             // Dharok's platelegs
            new LootItem(4712, 1, 10),             // Ahrim's robetop
            new LootItem(4714, 1, 10),             // Ahrim's robeskirt
            new LootItem(6585, 1, 10),             // Dragon boots
            new LootItem(12831, 1, 10),            // Blessed Spirit shield
            new LootItem(6585, 1, 10),             // Amulet of fury
            new LootItem(6914, 1, 5),             // Masters wand
            new LootItem(12006, 1, 5),            // Abyssal tentacle
            new LootItem(6889, 1, 5),             // Mage's book
            new LootItem(6737, 1, 5),             // Berserker ring
            new LootItem(6731, 1, 5),             // Seers ring
            new LootItem(6920, 1, 5),             // Infinity boots
            new LootItem(21295, 1, 3),            // Infernal Cape
            new LootItem(12851, 1, 3),            // Amulet of the Damned
            new LootItem(21079, 1, 1),            // Arcane Prayer Scroll
            new LootItem(21034, 1, 1),            // Dexterous Prayer Scroll
            new LootItem(11804, 1, 1),            // Bandos godsword
            new LootItem(6585, 1, 1)              // Amulet of fury
    );

    /**
     * Separator
     */

    private int index = 0;
    private String bossName;

    public BossEvent(int index) {
        this.index = index;
        this.bossName = NPCDef.get(BOSSES[index]).name;
    }

    @Override
    public void send(Player player) {
        int minsLeft = (int) ((nextSpawn - Server.currentTick()) / 100) + getExtra();
        if (minsLeft == 0)
            send(player, bossName, "Active!", Color.ORANGE_RED);
        else if (minsLeft == 1)
            send(player, bossName, "1 minute", Color.YELLOW);
        else if (minsLeft == 60)
            send(player, bossName, "1 hour", Color.RED);
        else if (minsLeft > 60) {
            int mins = minsLeft - 60;
            send(player, bossName, "1 hour " + mins + " minute" + (mins > 1 ? "s" : ""), Color.RED);
        } else
            send(player, bossName, minsLeft + " minutes", Color.RED);
    }

    private int getExtra() {
        int boss = (currentBoss) % BOSSES.length;
        if (boss == index)
            return 0;
        else if ((boss + 1) % BOSSES.length == index)
            return 30;
        else if ((boss + 2) % BOSSES.length == index)
            return 60;
        return -1;
    }

    @Override
    public void select(Player player) {
        Help.open(player, "wild_boss_events");
    }

    private static NPC spawnNext() {
        int random = Random.get(SPAWN_LOCATION.length - 1);
        NPC boss = new NPC(BOSSES[currentBoss % BOSSES.length]).spawn(SPAWN_LOCATION[random]);
        BossEvent.boss = boss;
        boss.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            Killer firstPlaceKiller = null; //TOP 1st
            Killer secondPlaceKiller = null; //TOP 2nd
            Killer thirdPlaceKiller = null; //TOP 3rd

            for(Killer killas : boss.getCombat().killers.values()) {
                if (killas == null || killas.player == null || killas.damage < 1) {
                    continue;
                }

                if(firstPlaceKiller == null || killas.damage > firstPlaceKiller.damage)
                {
                    thirdPlaceKiller = secondPlaceKiller;
                    secondPlaceKiller = firstPlaceKiller;
                    firstPlaceKiller = killas;
                }
                else if (secondPlaceKiller == null || killas.damage > secondPlaceKiller.damage)
                {
                    thirdPlaceKiller = secondPlaceKiller;
                    secondPlaceKiller = killas;
                }
                else if  (thirdPlaceKiller == null || killas.damage > thirdPlaceKiller.damage)
                {
                    thirdPlaceKiller = killas;
                }
            }

            if (firstPlaceKiller != null) {
                firstPlaceKiller.player.getInventory().addOrDrop(new Item(COINS_995, 10000000));
            }

            if (secondPlaceKiller != null) {
                secondPlaceKiller.player.getInventory().addOrDrop(new Item(COINS_995, 5000000));
            }

            if (thirdPlaceKiller != null) {
                thirdPlaceKiller.player.getInventory().addOrDrop(new Item(COINS_995, 3000000));
            }

            rewards(boss, killer);
            String defeatMessage = boss.getDef().name + " has been defeated! Well done to everyone who participated!";
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", defeatMessage);
            broadcastEvent(defeatMessage);
            boss.remove();
            BossEvent.boss = null;
        };
        String spawnMessage = boss.getDef().name + " has been sighted in level " + boss.wildernessSpawnLevel + " wilderness " + LOCATING_NAME[random] + "!";
        Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", spawnMessage);
        broadcastEvent(spawnMessage);
        currentBoss++;
        return boss;
    }

    private static void despawn(NPC npc) {
        boss = null;
        npc.remove();
        String eventDespawnMessage = npc.getDef().name + " has escaped! Kill him next time for desirable rewards!";
        Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", eventDespawnMessage);
        broadcastEvent(eventDespawnMessage);
    }

    private static void broadcastEvent(String eventMessage) {
        for (Player p : World.players) {
            if (p.broadcastBossEvent)
                p.getPacketSender().sendMessage(eventMessage, "", 14);
        }
    }

    private static void rewards(NPC boss, Killer topKiller) {
        Pet pet = boss.getCombat().getInfo().pet;
        String currencyName = "blood money";
        for (Killer k : boss.getCombat().killers.values()) {
            Player player = k.player;
            if (player != null && player.getPosition().isWithinDistance(boss.getPosition(), 20)) {
                int amount = (int) ((topKiller.damage * bloodMoneyPerDamage(player)) + (topKiller.player.wildernessLevel * BLOOD_MONEY_PER_WILD_LEVEL));
                player.getInventory().addOrDrop(BLOOD_MONEY, amount);
                if (k == topKiller) { // whoever dealt most damage
                    player.getInventory().addOrDrop(LOOT_TABLE.rollItem());
                    player.sendFilteredMessage("<img=46> <col=1e44b3>You were awarded " + amount + " " + currencyName + " for being the MVP in killing " + boss.getDef().name + "!");
                } else {
                    player.sendFilteredMessage("<img=46> <col=1e44b3>You were awarded " + amount + " " + currencyName + " for your contribution towards killing " + boss.getDef().name + ".");
                    if (pet != null && Random.rollDie(pet.dropAverage))
                        pet.unlock(player);
                }
            }
        }
    }

    private static double bloodMoneyPerDamage(Player player) {
        return BLOOD_MONEY_PER_DAMAGE;
        /*if (player.isGroup(PlayerGroup.ZENYTE)) {
            return 9.0;
        } else if (player.isGroup(PlayerGroup.ONYX)) {
            return 8.0;
        } else if (player.isGroup(PlayerGroup.DRAGONSTONE)) {
            return 7.0;
        } else if (player.isGroup(PlayerGroup.DIAMOND)) {
            return 6.0;
        } else if (player.isGroup(PlayerGroup.RUBY)) {
            return 5.0;
        } else if (player.isGroup(PlayerGroup.SAPPHIRE)) {
            return 4.0;
        } else {
            return BLOOD_MONEY_PER_DAMAGE;
        }*/
    }


    private static double goldMoneyPerDamage(Player player) {
        if (player.isGroup(PlayerGroup.ZENYTE)) {
            return 900.0;
        } else if (player.isGroup(PlayerGroup.ONYX)) {
            return 800.0;
        } else if (player.isGroup(PlayerGroup.DRAGONSTONE)) {
            return 700.0;
        } else if (player.isGroup(PlayerGroup.DIAMOND)) {
            return 600.0;
        } else if (player.isGroup(PlayerGroup.RUBY)) {
            return 500.0;
        } else if (player.isGroup(PlayerGroup.EMERALD)) {
            return 400.0;
        } else if (player.isGroup(PlayerGroup.SAPPHIRE)) {
            return 300.0;
        } else {
            return BLOOD_MONEY_PER_DAMAGE;
        }
    }

//    static {
//        World.startEvent(e -> {
//            LocalDateTime now = LocalDateTime.now(), firstSpawn;
//            if (now.getMinute() > 30)
//                firstSpawn = now.plus(Duration.ofHours(1)).withMinute(0);
//            else
//                firstSpawn = now.withMinute(30);
//            firstSpawn = firstSpawn.withSecond(0).with(ChronoField.MILLI_OF_SECOND, 0);
//            int ticks = (int) (Duration.between(now, firstSpawn).toMillis() / Server.tickMs());
//            nextSpawn = Server.getEnd(ticks);
//            e.delay(ticks);
//            while (true) {
//                NPC npc = spawnNext();
//                nextSpawn = Server.getEnd(60 * 100);
//                e.delay(ACTIVE_TIME * 100);
//                if (!npc.isRemoved() && !npc.getCombat().isDead()) {
//                    if (!npc.getCombat().isDefending(10)) {
//                        despawn(npc);
//                    } else { // if the npc is in combat wait until it's out of it to despawn
//                        World.startEvent(event -> {
//                            while (npc.getCombat().isDefending(10)) {
//                                event.delay(5);
//                            }
//                            if (!npc.isRemoved() && !npc.getCombat().isDead()) // in case it's no longer considered to be in combat because it died so the removal will be handled somewhere else
//                                despawn(npc); // npc is still alive but out of combat, but its timer has expired, remove it
//                        });
//                    }
//                }
//                e.delay((60 - ACTIVE_TIME) * 100);
//            }
//        });
//    }

}