package io.ruin.model.activities.wilderness;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.data.impl.Help;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;

public class Hotspot {

    private static final Hotspot[] HOTSPOTS = {
          new Hotspot("Mage Bank", new Bounds(3070, 3905, 3132, 3974, -1)),
            /*new Hotspot("Deserted Keep", new Bounds(3144, 3911, 3177, 3968, -1)),
            new Hotspot("East Dragons", new Bounds(3316, 3625, 3415, 3735, -1)),
            new Hotspot("West Dragons", new Bounds(2949, 3567, 3015, 3653, -1)),
            new Hotspot("Level 44 Obelisk", new Bounds(2966, 3834, 3024, 3888, -1)),
            new Hotspot("Level 50 Obelisk", new Bounds(3282, 3904, 3329, 3952, -1)),
            new Hotspot("Graveyard", new Bounds(3132, 3644, 3195, 3696, -1)),*/
            new Hotspot("Demonic Ruins", new Bounds(3261, 3856, 3346, 3903, -1)),
            //new Hotspot("Edgeville", new Bounds(2993, 3523, 3124, 3597, -1)),
            new Hotspot("Level 44 Obelisk", new Bounds(2966, 3834, 3024, 3888, -1)),
            new Hotspot("Wilderness Chins", new Bounds(3065, 3740, 3158, 3833, -1)),
            new Hotspot("Revenant Caves", new Bounds(3136, 10048, 3263, 10239, -1))
    };

    public static Hotspot ACTIVE = Random.get(HOTSPOTS);

    /**
     * Separator
     */

    public final String name;

    public final Bounds bounds;

    public Hotspot(String name, Bounds bounds) {
        this.name = name;
        this.bounds = bounds;
        MapListener.registerBounds(bounds)
                .onEnter(player -> {
                    if (bounds == ACTIVE.bounds)
                        player.sendMessage("<img=123>" + Color.ORANGE_RED.wrap("You have entered a wilderness hotspot, player kills in this area give double blood money!"));
                })
                .onExit((player, logout) -> {
                    if (!logout && bounds == ACTIVE.bounds)
                        player.sendMessage("<img=123>" + Color.ORANGE_RED.wrap("You have left the wilderness hotspot!"));
                });
    }

    /*
     * Event
     */
    static {
        int swapTicks = 20 * 100; //20 minutes
        World.startEvent(e -> {
            while (true) {
                Hotspot next = Random.get(HOTSPOTS);
                if (next == ACTIVE) {
                    e.delay(1); //Let's not risk hogging the current thread.
                    continue;
                }
                ACTIVE = next;
                String eventMessage = next.name + " is the new hotspot! Killing players here will give double blood money for the next 20 minutes!";
                broadcastEvent(eventMessage);
                e.delay(swapTicks);
            }
        });
    }

    private static void broadcastEvent(String eventMessage) {
        for(Player p : World.players) {
            if(p.broadcastHotspot)
                p.getPacketSender().sendMessage(eventMessage, "", 14);
        }
    }

    /**
     * Entry
     */
    public static final class Entry extends JournalEntry {

        public static final Entry INSTANCE = new Entry();

        @Override
        public void send(Player player) {
            send(player, "Hotspot", ACTIVE.name, Color.RED);
        }

        @Override
        public void select(Player player) {
            Help.open(player, "hotspots");
        }

    }

}
