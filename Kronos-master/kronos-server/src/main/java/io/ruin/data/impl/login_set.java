package io.ruin.data.impl;

import com.google.gson.annotations.Expose;
import io.ruin.api.protocol.Response;
import io.ruin.api.utils.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.entity.player.PlayerLogin;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class login_set extends DataFile {

    private static final Map<String, Set> LOADED = new HashMap<>();

    @Override
    public String path() {
        return "logins/*.json";
    }

    @Override
    public Object fromJson(String fileName, String json) {
        Set set = JsonUtils.fromJson(json, Set.class);
        LOADED.put(fileName, set);
        return set;
    }

    private static final class Set {

        @Expose public PlayerGroup[] allowed_groups;
        @Expose public String[] allowed_names;
        @Expose public Response deny_response;

        private boolean isAllowed(PlayerLogin login) {
            if(allowed_groups == null || Stream.of(allowed_groups).mapToInt(g -> g.id).anyMatch(id -> login.info.groupIds.contains(id)))
                return true;
            if(allowed_names == null || Stream.of(allowed_names).anyMatch(name -> name.equalsIgnoreCase(login.info.name)))
                return true;
            return false;
        }

    }

    /**
     * Active
     */

    private static Set ACTIVE_SET;

    public static void setActive(Player player, String name) {
        if(player != null) {
            LOADED.clear();
            DataFile.reload(player, login_set.class);
        }
        if((name = name.trim()).isEmpty())
            name = World.stage.name().toLowerCase();
        if((ACTIVE_SET = LOADED.get(name)) != null) {
            if(player != null)
                player.sendMessage("Login set \"" + name + "\" is now active.");
        } else {
            if(player != null)
                player.sendMessage("Login set \"" + name + "\" does not exist!");
            else
                System.err.println("Login set \"" + name + "\" does not exist!");
        }
    }

    public static boolean deny(PlayerLogin login) {
        if(ACTIVE_SET == null || ACTIVE_SET.isAllowed(login))
            return false;
        login.deny(ACTIVE_SET.deny_response);
        return true;
    }

}
