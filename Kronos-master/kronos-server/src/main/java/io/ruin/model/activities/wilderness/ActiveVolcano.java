package io.ruin.model.activities.wilderness;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.data.impl.Help;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static io.ruin.cache.ItemID.BLOOD_FRAGMENT;

public class ActiveVolcano {

    private static boolean DISABLED = false;

    private static long spawnTicks = 0;
    private static final int BOULDER = 31037;
    private static int BOULDER_STABILITY = 500;
    private static ActiveVolcano ACTIVE;
    private static long timeRemaining;
    private static GameObject boulder;
    public static ArrayList<Player> players = new ArrayList<>(500);
    private static final Bounds VOLCANO_BOUNDS = new Bounds(3333, 3914, 3390, 3975, -1);

    private static final ActiveVolcano[] SPAWNS = {
            new ActiveVolcano(new Position(3366, 3936, 0)),
            new ActiveVolcano(new Position(3353, 3934, 0)),
            new ActiveVolcano(new Position(3374, 3937, 0)),
            new ActiveVolcano(new Position(3361, 3924, 0)),
    };

    /**
     * Separator
     */

    private final Position boulderSpawn;

    public ActiveVolcano(Position boulderSpawn) {
        this.boulderSpawn = boulderSpawn;
    }

    static {
        /*
         * Event
         */
        if (!DISABLED) {
            /* event runs every 45 minutes */
            World.startEvent(e -> {
                while (true) {
                    spawnTicks = Server.getEnd(45 * 100);
                    ActiveVolcano next = Random.get(SPAWNS);
                    if (next == ACTIVE) {
                        e.delay(1);
                        continue;
                    }
                    ACTIVE = next;
                    String eventMessage = "There's been a disturbance reported at the Volcano!";
                    Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", eventMessage);
                    broadcastEvent(eventMessage);
                    addBoulder();
                    /* stop event after 15 minutes */
                    timeRemaining = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15);
                    e.delay(15 * 100);
                    removeBoulder(false);
                    /*delay 30 minutes before starting event */
                    e.delay(30 * 100);
                }
            });

            /*
             * Boulder
             */
            ObjectAction.register(BOULDER, 1, (player, obj) -> mine(player));

            /*
             * Map listener
             */
            MapListener.register(ActiveVolcano::checkActive)
                    .onEnter(ActiveVolcano::entered)
                    .onExit(ActiveVolcano::exited);
        }
    }

    private static void broadcastEvent(String eventMessage) {
        for(Player p : World.players) {
            if(p.broadcastActiveVolcano)
                p.getPacketSender().sendMessage(eventMessage, "", 14);
        }
    }

    private static boolean checkActive(Player player) {
        boolean active = player.getPosition().inBounds(VOLCANO_BOUNDS);
        if (active) {
            player.getPacketSender().sendString(Interface.VOLCANO_MINE, 8, "Time Left");
            player.getPacketSender().sendString(Interface.VOLCANO_MINE, 9, timeRemaining(timeRemaining - System.currentTimeMillis()));
            player.getPacketSender().sendString(Interface.VOLCANO_MINE, 10, "Fragments");
            player.getPacketSender().sendString(Interface.VOLCANO_MINE, 11, player.bloodyFragments + "");
            player.getPacketSender().sendString(Interface.VOLCANO_MINE, 12, "Boulder Stability");
            player.getPacketSender().sendString(Interface.VOLCANO_MINE, 13, boulderColor() + (int) (BOULDER_STABILITY * .20) + "%");
            player.getPacketSender().sendString(Interface.VOLCANO_MINE, 14, "Total Players");
            player.getPacketSender().sendString(Interface.VOLCANO_MINE, 15, players.size() + "");
            return true;
        }
        return false;
    }

    private static void entered(Player player) {
        players.add(player);
        player.openInterface(InterfaceType.SECONDARY_OVERLAY, Interface.VOLCANO_MINE);
        player.getPacketSender().setHidden(611, 16, true);
    }

    private static void exited(Player player, boolean logout) {
        players.remove(player);
        player.bloodyFragments = 0;
        if (!logout)
            player.closeInterface(InterfaceType.SECONDARY_OVERLAY);
    }

    private static void addBoulder() {
        boulder = GameObject.spawn(BOULDER, ACTIVE.boulderSpawn.getX(), ACTIVE.boulderSpawn.getY(), 0, 10, 0);
        BOULDER_STABILITY = 500;
    }

    private static void fallingLava(Player player, Position position) {
        World.startEvent(event -> {
            event.delay(1);
            for (Player p : player.localPlayers()) {
                if (p.getPosition().equals(position)) {
                    p.sendFilteredMessage(Color.DARK_RED.wrap("You hear something falling..."));
                    event.delay(2);
                    World.sendGraphics(1406, 0, 0, position);
                    event.delay(1);
                    if (p.getPosition().equals(position)) {
                        p.hit(new Hit().randDamage(10, 30));
                        p.resetAnimation();
                        p.sendFilteredMessage(Color.DARK_RED.wrap("A piece of flying lava hits you."));
                    } else {
                        p.sendFilteredMessage(Color.DARK_RED.wrap("You dodge the flying lava.. close one."));
                    }
                }
            }
        });
    }

    private static void removeBoulder(boolean success) {
        if (boulder != null) {
            boulder.setId(-1);
            boulder = null;
            if (success) {
                String successMessage = "The Volcano has been subdued! Well done everyone!";
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", successMessage);
                broadcastEvent(successMessage);
            } else {
                String failedMessage = "The Volcano has erupted! Help subdue it next time for blood money!";
                Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", failedMessage);
                broadcastEvent(failedMessage);
            }
        }
    }

    private static void removeShards(int amt) {
        BOULDER_STABILITY -= amt;
        if (BOULDER_STABILITY <= 0)
            BOULDER_STABILITY = 0;
    }

    private static String boulderColor() {
        if (BOULDER_STABILITY > 300)
            return "<col=00FF00>";
        else if (BOULDER_STABILITY > 100)
            return "<col=ffff00>";
        return "<col=FF0000>";
    }

    private static void mine(Player player) {
        player.startEvent(event -> {
            if (player.getInventory().isFull() && !player.getInventory().hasId(BLOOD_FRAGMENT)) {
                player.dialogue(new MessageDialogue("Your inventory is too full to do this."));
                return;
            }
            player.animate(627);
            player.sendFilteredMessage("You swing your pick at the boulder.");
            event.delay(Random.get(3, 8));
            while (true) {
                if (player.getInventory().isFull() && !player.getInventory().hasId(BLOOD_FRAGMENT)) {
                    player.dialogue(new MessageDialogue("You can't hold anymore fragments in your inventory."));
                    return;
                }
                int random = Random.get(5, 10);
                player.animate(627);
                player.getInventory().add(BLOOD_FRAGMENT, random);
                removeShards(random);
                player.bloodyFragments += random;
                player.sendFilteredMessage("You manage to mine " + random + " fragments.");
                if (BOULDER_STABILITY <= 0) {
                    removeBoulder(true);
                    player.resetAnimation();
                    break;
                }
                if (boulder == null) {
                    player.resetAnimation();
                    break;
                }
                if (Random.rollDie(10, 1))
                    fallingLava(player, player.getPosition().copy());
                event.delay(Random.get(3, 8));
            }
        });
    }

    private static String timeRemaining(long ms) {
        long hours = TimeUnit.MILLISECONDS.toHours(ms);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        if (ms < 0)
            return "None!";
        return String.format(
                "%02d:%02d",
                minutes - TimeUnit.HOURS.toMinutes(hours),
                TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(minutes)
        );
    }

    /**
     * Entry
     */
    public static final class Entry extends JournalEntry {

        public static final Entry INSTANCE = new Entry();

        @Override
        public void send(Player player) {
            int minsLeft = (int) ((spawnTicks - Server.currentTick()) / 100);
            if (minsLeft == 0)
                send(player, "Active Volcano", "Active!", Color.GREEN);
            else if (minsLeft == 1)
                send(player, "Active Volcano", "1 minute", Color.YELLOW);
            else if (minsLeft == 60)
                send(player, "Active Volcano", "1 hour", Color.RED);
            else if (minsLeft > 60) {
                int mins = minsLeft - 60;
                send(player, "Active Volcano", "1 hour " + mins + " minute" + (mins > 1 ? "s" : ""), Color.RED);
            } else
                send(player, "Active Volcano", minsLeft + " minutes", Color.RED);
        }

        @Override
        public void select(Player player) {
            Help.open(player, "active_volcano");
        }

    }

}
