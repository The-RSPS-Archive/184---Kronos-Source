package io.ruin.model.activities.wilderness;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.data.impl.Help;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.journal.toggles.RiskProtection;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.process.event.Event;
import io.ruin.utility.Broadcast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.ruin.cache.ItemID.BLOOD_MONEY;

public class SupplyChest {

    /* supply chest IDs */
    private static int SPAWNING_SUPPLY_CHEST = 33114;
    private static int UNLOOTED_SUPPLY_CHEST = 33115;
    private static int LOOTED_SUPPLY_CHEST = 31583;

    /* spawn/despawn timers */
    private static long spawnTicks = 0;
    private static long timeUntilDespawn = 0;
    private static long timeUntilUnlocked = 0;
    private static final long UNLOCK_TIMER = TimeUnit.MINUTES.toMillis(10);

    /* looting variables */
    private static SupplyChest ACTIVE;
    private static GameObject supplyChest;
    private static boolean looted = false;

    /* spawning arrays */
    private static final SupplyChest[] SPAWN_LOCATIONS = {
            new SupplyChest(new Position(3112, 3957, 0)),
    };

    private static final String[] LOCATION_HINTS = {
            "east of the Mage Arena entrance",
    };

    /* loot table */
    public static final LootTable SUPPLY_CHEST_TABLE = new LootTable().addTable(1,
            new LootItem(BLOOD_MONEY, 50, 250, 80), // Blood money
            new LootItem(11802, 1, 10), // Armadyl godsword
            new LootItem(13652, 1, 5) // Dragon claws
    );

    /**
     * Separator
     */

    private final Position chestPosition;

    public SupplyChest(Position chestPosition) {
        this.chestPosition = chestPosition;
    }

    static {
        /**
         * Event
         */
        /*World.startEvent(e -> {
            while (true) {
                spawnTicks = Server.getEnd(3 * 60 * 100);
                e.delay(3 * 60 * 100);
                int randomLocation = Random.get(0, SPAWN_LOCATIONS.length - 1);
                SupplyChest next = SPAWN_LOCATIONS[randomLocation];
                while (next == ACTIVE) {
                    randomLocation = Random.get(0, SPAWN_LOCATIONS.length - 1);
                    next = SPAWN_LOCATIONS[randomLocation];
                }
                ACTIVE = next;
                String spawnMessage = "The PvP Supply Chest has spawned " + LOCATION_HINTS[randomLocation] + " and will unlock in 10 minutes!";
                broadcastEvent(spawnMessage);
                spawnSupplyChest();
                e.delay(15 * 100); // 15 mins
                removeSupplyChest();
            }
        });

        ObjectAction.register(SPAWNING_SUPPLY_CHEST, 1, (player, obj) -> {
            player.startEvent(event -> {
                player.lock();
                player.animate(832);
                player.hit(new Hit().randDamage(1, 6));
                player.dialogue(new MessageDialogue("The " + Color.DARK_RED.wrap("PvP Supply Chest") + " will unlock in " + getRemainingTime() + "."));
                player.graphics(1605, 0, 20);
                player.unlock();
            });
        });

        ObjectAction.register(UNLOOTED_SUPPLY_CHEST, 1, (player, obj) -> {
            if (player.supplyChestWarning) {
                player.dialogue(
                        new MessageDialogue("<col=ff0000>Warning:</col> Looting this chest will high-risk skull and teleblock you. ALL players will be able to attack you while you have the rewards."),
                        new OptionsDialogue("Are you sure you wish to loot it?",
                                new Option("Yes, I'm brave.", () -> lootChest(player, obj)),
                                new Option("Eep! No thank you.", () -> player.sendFilteredMessage("You decide to not loot the chest.")),
                                new Option("Yes please, don't show this message again.", () -> {
                                    player.supplyChestWarning = false;
                                    lootChest(player, obj);
                                }))
                );
            } else {
                lootChest(player, obj);
            }
        });*/
    }

    private static void lootChest(Player player, GameObject obj) {
        player.startEvent(event -> {
            if(looted) {
                player.sendMessage("Someone already looted this supply chest!");
                return;
            }
            if(player.getCombat().getLevel() < 120) {
                player.sendMessage("You need at least 120 combat in order to loot the supply chest.");
                return;
            }
            player.lock();
            obj.setId(LOOTED_SUPPLY_CHEST);
            player.animate(832);
            player.graphics(1299, 43, 0);
            player.getCombat().tbTicks = 500;
            player.getPacketSender().sendWidget(Widget.TELEBLOCK, 300);
            player.sendMessage("<col=4f006f>A teleblock spell has been cast on you. It will expire in 5 minutes.");
            player.getCombat().skullHighRisk();
            player.supplyChestRestricted = true;
            looted = true;
            player.riskProtectionTier = 0;
            player.riskProtectionExpirationDelay.delay(10 * 100); // 10 minutes
            player.sendMessage("By opening the PVP supply chest you forfeit your risk protection for 10 minutes.");
//            if (player.journal == Journal.TOGGLES)
//                RiskProtection.INSTANCE.send(player);
            Item prize = SUPPLY_CHEST_TABLE.rollItem();
            player.getInventory().addOrDrop(prize.getId(), prize.getAmount());
            player.sendMessage("You just received " + prize.getDef().descriptiveName + " from the PvP Supply Chest!");
            player.sendMessage("The power of the chest has high-risk skulled and teleblocked you. Run!");
            String lootedMessage = player.getName() + " has looted the PvP Supply Chest! Get him!";
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", lootedMessage);
            broadcastEvent(lootedMessage);
            player.unlock();
        });
    }

    private static void spawnSupplyChest() {
        World.startEvent(event -> {
            timeUntilUnlocked = System.currentTimeMillis() + UNLOCK_TIMER;
            supplyChest = GameObject.spawn(SPAWNING_SUPPLY_CHEST, ACTIVE.chestPosition.getX(), ACTIVE.chestPosition.getY(), 0, 10, 0);
            World.players.forEach(p -> {
                if(p.getPosition().isWithinDistance(ACTIVE.chestPosition, 4))
                    knockBackPlayers(p, supplyChest);
            });
            event.delay(10 * 100);
            String unlockMessage = "The PvP Supply Chest has been unlocked!";
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", unlockMessage);
            broadcastEvent(unlockMessage);
            supplyChest.setId(UNLOOTED_SUPPLY_CHEST);
        });
    }

    private static void broadcastEvent(String eventMessage) {
        for(Player p : World.players) {
            if(p.broadcastSupplyChest)
                p.getPacketSender().sendMessage(eventMessage, "", 14);
            else
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", eventMessage);
        }
    }

    private static void knockBackPlayers(Player player, GameObject obj) {
        List<Position> positions = player.getPosition().relative(3, 3).area(4, p -> p.getTile().clipping == 0);
        Position pos = positions.remove(Random.get(positions.size() - 1));
            if (player.player != null) {
                int finalEndX = pos.getX();
                int finalEndY = pos.getY();
                player.player.addEvent((Event event) -> {
                    final Player p = player.player;
                    p.lock();
                    p.animate(1157);
                    p.graphics(245, 5, 124);
                    p.hit(new Hit().randDamage(5, 20));
                    p.stun(2, true);
                    int diffX = finalEndX - player.getAbsX();
                    int diffY = finalEndY - player.getAbsY();
                    p.getMovement().teleport(finalEndX, finalEndY, player.getHeight());
                    p.getMovement().force(diffX, diffY, 0, 0, 10, 60, Direction.getDirection(new Position(obj.x, obj.y, 0), player.getPosition()));
                    p.sendMessage("The PvP Supply Chest throws you backwards!");
                    p.unlock();
                });
            }
    }

    private static void removeSupplyChest() {
        if (supplyChest != null) {
            supplyChest.setId(-1);
            supplyChest = null;
            looted = false;
        }
    }

    private static String getRemainingTime() {
        long ms = timeUntilUnlocked - System.currentTimeMillis();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(ms);
        return (minutes >= 1 ? (Color.DARK_RED.wrap(minutes + " minute" + (minutes > 1 ? "s" : "")) + " and ") : "") +
                Color.DARK_RED.wrap(Math.max((seconds - TimeUnit.HOURS.toMinutes(minutes)), 1) + " second" +
                        ((seconds - TimeUnit.HOURS.toMinutes(minutes)) > 1 ? "s" : ""));
    }

    /**
     * Entry
     */

    public static final class Entry extends JournalEntry {

        public static final Entry INSTANCE = new Entry();

        @Override
        public void send(Player player) {
            if(looted) {
                send(player, "PvP Supply Chest", "Looted", Color.RED);
                return;
            }

            long ms = timeUntilUnlocked - System.currentTimeMillis();
            long lockedMinutesLeft = TimeUnit.MILLISECONDS.toMinutes(ms);
            if(lockedMinutesLeft >= 0) {
                send(player, "PvP Supply Chest", "Active ", Color.YELLOW);
                return;
            }

            int minsLeft = (int) ((spawnTicks - Server.currentTick()) / 100);
            if(minsLeft < 0) {
                send(player, "PvP Supply Chest", "Active", Color.GREEN);
                return;
            }
            if (minsLeft == 0)
                send(player, "PvP Supply Chest", "Right away!", Color.GREEN);
            else if (minsLeft == 1)
                send(player, "PvP Supply Chest", "1 minute", Color.YELLOW);
            else if (minsLeft == 60)
                send(player, "PvP Supply Chest", "1 hour", Color.RED);
            else if (minsLeft > 60) {
                int hours = minsLeft / 60;
                send(player, "PvP Supply Chest", hours + " hour" + (hours > 1 ? "s" : ""), Color.RED);
            } else
                send(player, "PvP Supply Chest", minsLeft + " minutes", Color.RED);
        }

        @Override
        public void select(Player player) {
            Help.open(player, "supply_chest");
        }

    }

}
