package io.ruin.update;

import io.ruin.api.filestore.FileStore;
import io.ruin.api.netty.NettyServer;
import io.ruin.api.utils.ServerWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.WatchService;
import java.util.Properties;

@Slf4j
public class Server extends ServerWrapper {

    public static FileStore fileStore;
    public static WatchService fileWatcher;

    public static void main(String[] args) throws Exception {
        try {
            Properties properties = new Properties();
            File systemProps = new File("server.properties");
            log.info("Looking for system.properties in {}", systemProps.getAbsolutePath());
            try (InputStream in = new FileInputStream(systemProps)) {
                properties.load(in);
            } catch (IOException e) {
                logError("Failed to load server settings!", e);
                throw e;
            }
            System.out.println("Initiating file store...");
            fileStore = new FileStore(properties.getProperty("cache_path"));
/*            FileWatcherService watcherService = new FileWatcherService(new File(properties.getProperty("cache_path")));
            watcherService.onFileModified(watchEvent -> {
               try {
                   if (watchEvent.context().toAbsolutePath().toString().endsWith("idx255")) {
                       System.out.println("Cache changed. Reloading reference table!");
                       fileStore.reloadReferenceTable();
                   }
               } catch (Exception ex) {
                   Server.logError("Error", ex);
               }
            }).start();*/
            NettyServer nettyServer = NettyServer.start("Update Server", 7304, HandshakeDecoder.class, 5, Boolean.parseBoolean(properties.getProperty("offline_mode")));
            Runtime.getRuntime().addShutdownHook(new Thread(nettyServer::shutdown));
        }
        catch (Exception e) {
            Server.logError("Error", e);
            return;
        }
    }

    static {
        Server.init(Server.class);
    }

}

