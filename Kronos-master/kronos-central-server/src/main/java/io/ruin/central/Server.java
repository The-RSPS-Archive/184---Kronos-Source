package io.ruin.central;

import io.ruin.api.netty.NettyServer;
import io.ruin.api.process.ProcessWorker;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.ThreadUtils;
import io.ruin.central.model.Player;
import io.ruin.central.model.world.World;
import io.ruin.central.model.world.WorldList;
import io.ruin.central.network.DefaultDecoder;
import io.ruin.central.utility.Limbo;
import io.ruin.central.utility.PlayerCounter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class Server extends ServerWrapper {


    public static final int PORT = 3845;

    public static final ProcessWorker worker = Server.newWorker("central-server", 30L, 5);
    public static final ArrayList<World> worlds = new ArrayList<World>();

    public static void main(String[] args) throws Exception {
        worker.queue(() -> {
            Server.process();
            return false;
        });
        WorldList.start();
        try {
            Properties properties = new Properties();
            NettyServer nettyServer = NettyServer.start("Kronos Central Server", PORT, DefaultDecoder.class, 5,
                    Boolean.parseBoolean(properties.getProperty("offline_mode")));
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println();
                System.out.println("Gracefully shutting down central server...");
                nettyServer.shutdown();
                ArrayList<World> worldQueue = new ArrayList<World>();
                worker.executeAwait(() -> worldQueue.addAll(worlds));
                do {
                    for (World world : worldQueue) {
                        int messageCount = world.decoder.getMessageCount();
                        if (messageCount <= 0) continue;
                        System.out.println("Waiting for world " + world.id + "... (" + messageCount + ")");
                        ThreadUtils.sleep(10L);
                        continue;
                    }
                    break;
                } while (true);
                System.out.println("Central server was shutdown successfully!");
                System.exit(1);
            }));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void process() {
        int playerCount = 0;
        Iterator<World> it = worlds.iterator();
        while (it.hasNext()) {
            World world = it.next();
            if (!world.process()) {
                it.remove();
                world.destroy();
            }
            playerCount += world.players.size();
        }
        PlayerCounter.update(playerCount);
    }

    public static void queueWorld(World world) {
        worker.execute(() -> {
                    boolean connected = Server.addWorld(world);
                    world.sendResponse(connected);
                }
        );
    }

    private static boolean addWorld(World world) {
        if (Server.getWorld(world.id) != null) {
            return false;
        }
        worlds.add(world);
        return true;
    }

    public static World getWorld(int id) {
        for (World world : worlds) {
            if (world.id != id) continue;
            return world;
        }
        return null;
    }

    public static Player getPlayer(int userId) {
        for (World world : worlds) {
            for (Player player : world.players) {
                if (player.userId != userId)
                    continue;
                return player;
            }
        }
        return null;
    }

    public static Player getPlayer(String name) {
        for (World world : worlds) {
            for (Player player : world.players) {
                if (!name.equalsIgnoreCase(player.name)) continue;
                return player;
            }
        }
        return null;
    }

    public static boolean isOnline(int userId, String name) {
        int limboWorld = Limbo.get(userId);
        if (limboWorld != -1) {
            System.err.println(name + " is in limbo on world " + limboWorld + "!");
            return true;
        }
        for (World world : worlds) {
            if (!world.hasPlayer(userId)) continue;
            System.err.println(name + " is online in world " + world.id + "!");
            world.sendOnlineCheck(userId);
            return true;
        }
        return false;
    }

    static {
        Server.init(Server.class);
    }
}

