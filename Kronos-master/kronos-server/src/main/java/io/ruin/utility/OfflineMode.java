package io.ruin.utility;

import io.ruin.Server;
import io.ruin.api.protocol.Protocol;
import io.ruin.api.protocol.Response;
import io.ruin.api.utils.FileUtils;
import io.ruin.api.utils.ListUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.entity.player.PlayerLogin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class OfflineMode {

    public static boolean enabled;
    
    public static String path;

    public static void setPath() throws IOException {
        File offlineFolder = FileUtils.get(System.getProperty("user.home") + "/Desktop/" + World.type.getWorldName()+"/~development");
        if(!offlineFolder.exists() && !offlineFolder.mkdirs())
            throw new IOException("Offline mode folder could not be created!");
        path = offlineFolder.getPath();
    }

    /**
     * Players
     */

    private static final Object userIdLock = new Object();

    private static final HashMap<String, Integer> tempUserIds = new HashMap<>();

    private static int userIdOffset;
    
    public static boolean savePlayer(Player player, int logoutAttempt, String json) {
        if(!enabled)
            return false;
        try {
            File folder = FileUtils.get(path + "/players");
            if(!folder.exists() && !folder.mkdir())
                throw new IOException("Failed to create offline players folder!");
            Files.write(FileUtils.get(folder.getPath() + "/" + player.getName().toLowerCase() + ".json").toPath(), json.getBytes());
        } catch(IOException e) {
            Server.logError("", e);
        } finally {
            if(logoutAttempt != -1)
                player.finishLogout(logoutAttempt);
        }
        return true;
    }
    
    public static boolean loadPlayer(PlayerLogin login) {
        if(!enabled)
            return false;
        if(Protocol.method360(login.info.name) == null) {
            login.deny(Response.CHANGE_DISPLAY_NAME);
            return true;
        }
        Integer userId;
        synchronized(userIdLock) {
            String key = login.info.name.toLowerCase();
            userId = tempUserIds.computeIfAbsent(key, k -> ++userIdOffset);
        }
        String saved = "";
        try {
            File savedFile = FileUtils.get(path + "/players/" + login.info.name.toLowerCase() + ".json");
            if(savedFile.exists())
                saved = new String(Files.readAllBytes(savedFile.toPath()));
        } catch(Exception e) {
            Server.logError("", e);
        }
        login.info.update(userId, login.info.name, saved, ListUtils.toList(PlayerGroup.ADMINISTRATOR.id), 0);
        login.success();
        return true;
    }

}