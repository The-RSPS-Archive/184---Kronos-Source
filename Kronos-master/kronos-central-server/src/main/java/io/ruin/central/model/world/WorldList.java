package io.ruin.central.model.world;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.process.ProcessWorker;
import io.ruin.api.utils.PostWorker;
import io.ruin.central.Server;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorldList {

    private static final ConcurrentLinkedQueue<World> active = new ConcurrentLinkedQueue();
    private static boolean forceUpdate;
    private static int updateTicks;
    private static final String AUTH_TOKEN = "s5T4zaigCbbs1p5gKuVr1RZ9THiFst";

    public static void start() {
        ProcessWorker worker = Server.newWorker("world-list", 1000L, 4);
        worker.queue(() -> {
            WorldList.update(active.toArray(new World[0]));
            return false;
        });
    }

    public static void add(World world) {
        active.offer(world);
        forceUpdate = true;
    }

    public static void remove(World world) {
        active.remove(world);
        forceUpdate = true;
    }

    private static void update(World[] worlds) {

        if (!forceUpdate && ++updateTicks < 10) {
            return;
        }
        forceUpdate = false;
        updateTicks = 0;
        try {
            OutBuffer out = new OutBuffer(255);
            out.addInt(0);
            out.addShort(worlds.length);
            for (World world : worlds) {
                out.addShort(world.id);
                out.addInt(world.settings);
                out.addString(world.host);
                out.addString(world.activity);
                out.addByte(world.flag);
                out.addShort((int) (world.players.size() * 1.6));
            }
            int pos = out.position();
            out.position(0);
            out.addInt(pos - 4);
            out.position(pos);
            String result = PostWorker.post("https://community.kronos.rip/integration/world_updater.php?k=" + AUTH_TOKEN, out.toByteArray());
            if (result == null || !result.equals("1")) {
                throw new IOException("Failed to update world list!");
            }
        } catch (Throwable t) {
            Server.logError(t.getMessage());
        }
    }
}

