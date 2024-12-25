package io.ruin;

import com.ea.agentloader.AgentLoader;
import io.ruin.api.database.Database;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.database.DummyDatabase;
import io.ruin.api.filestore.FileStore;
import io.ruin.api.netty.NettyServer;
import io.ruin.api.process.ProcessWorker;
//import io.ruin.api.rest.KronosRest;
import io.ruin.api.utils.*;
import io.ruin.cache.*;
import io.ruin.data.DataFile;
import io.ruin.data.impl.login_set;
import io.ruin.data.yaml.YamlLoader;
import io.ruin.model.World;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.actions.impl.Trapdoor;
import io.ruin.model.map.object.actions.impl.dungeons.StrongholdSecurity;
import io.ruin.model.map.object.actions.impl.edgeville.Giveaway;
import io.ruin.model.shop.ShopManager;
import io.ruin.network.LoginDecoder;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.process.CoreWorker;
import io.ruin.process.LoginWorker;
import io.ruin.process.event.EventWorker;
import io.ruin.services.LatestUpdate;
import io.ruin.services.Loggers;
import io.ruin.services.discord.DiscordConnection;
import io.ruin.utility.CharacterBackups;
import io.ruin.utility.OfflineMode;
import kilim.WeavingClassLoader;
import kilim.agent.KilimAgent;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class Server extends ServerWrapper {

    public static final ProcessWorker worker = newWorker("server-worker", 600L, Thread.NORM_PRIORITY + 1);

    public static Database gameDb = new DummyDatabase();

    public static Database siteDb = new DummyDatabase();

    public static final Database dumpsDb = new DummyDatabase();

    public static Database forumDb = new DummyDatabase();

    public static List<Runnable> afterData = new ArrayList<>();

    public static FileStore fileStore;

    public static File dataFolder;

    public static CharacterBackups backups = new CharacterBackups();

    public static boolean dataOnlyMode = false;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Throwable {

        if (kilim.tools.Kilim.trampoline(new Object(), true, args))  {
            return;
        }
//        Thread.currentThread().setContextClassLoader(new WeavingClassLoader());
//        AgentLoader.loadAgentClass(KilimAgent.class.getName(), "");
//        KilimAgent.classLoader = (WeavingClassLoader) Thread.currentThread().getContextClassLoader();
        log.info("{}", Thread.currentThread().getContextClassLoader());
        WeavingClassLoader classLoader = new WeavingClassLoader();
        Thread.currentThread().setContextClassLoader(classLoader);
        AgentLoader.loadAgentClass(KilimAgent.class.getName(), "");
        KilimAgent.classLoader = classLoader;

        long startTime = System.currentTimeMillis();

        init(Server.class);

        /*
         * Server properties
         */
        println("Loading server settings...");
        Properties properties = new Properties();
        File systemProps = new File("server.properties");
        log.info("Looking for system.properties in {}", systemProps.getAbsolutePath());
        try (InputStream in = new FileInputStream(systemProps)) {
            properties.load(in);
        } catch (IOException e) {
            logError("Failed to load server settings!", e);
            throw e;
        }

        /*
         * World information
         */
        World.parse(properties);

        /*
         * Offline mode
         */
        if (OfflineMode.enabled = Boolean.valueOf(properties.getProperty("offline_mode"))) {
            OfflineMode.setPath();
            println("WARNING: Offline mode enabled!");
        }

        /*
         * Loading (Data from cache & databases)
         */
        println("Loading server data...");
        try {

            fileStore = new FileStore(properties.getProperty("cache_path"));
            dataFolder = FileUtils.get(properties.getProperty("data_path"));
            Varpbit.load();
            IdentityKit.load();
            AnimDef.load();
            GfxDef.load();
            ScriptDef.load();
            InterfaceDef.load();
            ItemDef.load();
            NPCDef.load();
            ObjectDef.load();
            DataFile.load();

            /*
             * The following must come after DataFile.load
             */
            login_set.setActive(null, properties.getProperty("login_set"));

            //KronosRest.start();

        } catch (Throwable t) {
            logError("", t);
            return;
        }

        /*
         * Database connections
         */
        if (!OfflineMode.enabled) {
            println("Connecting to SQL databases...");

            siteDb = new Database(properties.getProperty("database_host"), "kronos", properties.getProperty("database_user"), properties.getProperty("database_password"));
            gameDb = new Database(properties.getProperty("database_host"), "game", properties.getProperty("database_user"), properties.getProperty("database_password"));
            forumDb = new Database(properties.getProperty("database_host"), "community", properties.getProperty("database_user"), properties.getProperty("database_password"));

            DatabaseUtils.connect(new Database[]{gameDb, forumDb, siteDb}, errors -> {
                if (!errors.isEmpty()) {
                    for (Throwable t : errors)
                        logError("Database error", t);
                    System.exit(1);
                }
            });

            Loggers.clearOnlinePlayers(World.id);
            LatestUpdate.fetch();
            Giveaway.updateTotalAmount();
            if(!OfflineMode.enabled && !World.isDev()) {
                DiscordConnection.setup("NjY2ODYwNTYwNjcxMDQ3Njg5.XiTqtw.aUSPC_CW6Oszpz1Ru1e08AQjsMQ");
            }
        }


        Achievement.staticInit();

        ShopManager.registerUI();

        /*
         * Loading (After data has been loaded!
         */
        for (Runnable r : afterData) {
            try {
                r.run();
            } catch (Throwable t) {
                logError("", t);
                return;
            }
        }
        afterData.clear();
        afterData = null;

        /*
         * Loading (Scripts & handlers)
         */
        println("Loading server scripts & handlers...");
        try {
            Special.load();
            Incoming.load();

            // When packaged, priority messes up and these load too late.
            StrongholdSecurity.register();
            Trapdoor.register();

            PackageLoader.load("io.ruin"); //ensures all static blocks load

            // When packaged, priority messes up and these load too late.
            StrongholdSecurity.register();


            YamlLoader.initYamlFiles();
            Trapdoor.register();

            backups.start();

        } catch (Throwable t) {
            logError("Error loading handlers", t);
            //return;
        }

        /*
         * Processing
         */
        println("Starting server workers...");
        worker.queue(() -> {
            CoreWorker.process();
            EventWorker.process();
            return false;
        });
        LoginWorker.start();
        //LiveData.start();
        /*
         * Network
         */
        NettyServer nettyServer = NettyServer.start(World.type.getWorldName() + " World (" + World.id + ") Server", World.port, LoginDecoder.class, 5, Boolean.parseBoolean(properties.getProperty("offline_mode")));

        /*
         * Central server
         */
        if (!OfflineMode.enabled)
            CentralClient.start();

        //log.info("Data path = {}", Server.dataFolder.getAbsolutePath());
        ServerWrapper.println("Started server in " + (System.currentTimeMillis() - startTime) + "ms.");

        /*
         * Shutdown hook
         */
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println();
            System.out.println("Server shutting down with " + worker.getQueuedTaskCount() + " tasks queued.");
            System.out.println("Gracefully shutting down world server...");
            /*
             * Shutdown network
             */
            nettyServer.shutdown();

            /*
             * Remove players
             */
            int fails = 0;
            while (true) {
                try {
                    for(Player p : World.players) {
                        if(p.getChannel().id() == null)
                            p.logoutStage = -1;
                    }
                    if (Server.worker.getExecutor().submit(World::removePlayers).get())
                        break;

                    ThreadUtils.sleep(10L); //^ that will already be a big enough delay
                } catch (Throwable t) {
                    logError("ERROR: Removing online players", t);
                    if (++fails >= 5 && World.removePlayers())
                        break;
                    ThreadUtils.sleep(1000L);
                }
            }
        }));
    }

    /**
     * Timing
     */
    public static long currentTick() {
        return worker.getExecutions();
    }

    public static long getEnd(long ticks) {
        return currentTick() + ticks;
    }

    public static boolean isPast(long end) {
        return currentTick() >= end;
    }

    public static int tickMs() {
        return (int) Server.worker.getPeriod();
    }

    public static int toTicks(int seconds) {
        return (seconds * 1000) / tickMs();
    }

}