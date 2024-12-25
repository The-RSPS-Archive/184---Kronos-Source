package io.ruin.central.utility;

import io.ruin.api.utils.JsonUtils;
import io.ruin.api.utils.XenPost;
import io.ruin.central.Server;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class XenUser {

    private static final XenUser INVALID_USER = new XenUser();
    private static final ConcurrentHashMap<String, XenUser> LOADED = new ConcurrentHashMap();
    public int id = -1;
    public String name;
    public String lastName;

    public static void forObj(Object obj, Consumer<XenUser> xenUserConsumer) {
        if (obj instanceof Integer) {
            XenUser.forId((Integer)obj, xenUserConsumer);
        } else {
            XenUser.forName((String)obj, xenUserConsumer);
        }
    }

    public static void forId(int id, Consumer<XenUser> xenUserConsumer) {
        XenUser user = XenUser.get(id);
        if (user != null) {
            xenUserConsumer.accept(user);
            return;
        }
        Server.worker.execute(() -> XenUser.load("" + id, true), xenUserConsumer);
    }

    public static void forName(String name, Consumer<XenUser> xenUserConsumer) {
        XenUser user = XenUser.get(name);
        if (user != null) {
            xenUserConsumer.accept(user);
            return;
        }
        Server.worker.execute(() -> XenUser.load(name, false), xenUserConsumer);
    }

    private static XenUser load(String search, boolean byId) {
        HashMap<Object, Object> postMap = new HashMap<Object, Object>();
        postMap.put("t", byId ? 0 : 1);
        postMap.put("v", search);
        String result = XenPost.post("get_user", postMap);
        System.err.println(result);
        if (result == null || !result.startsWith("d=")) {
            return null;
        }
        try {
            Map map = JsonUtils.fromJson(result.substring(2), Map.class, new Type[]{String.class, String.class});
            String id = (String)map.get("user_id");
            if (id == null) {
                return INVALID_USER;
            }
            XenUser user = new XenUser();
            user.id = Integer.valueOf(id);
            user.name = (String)map.get("username");
            System.out.println((String)map.get("username"));
            user.lastName = (String)map.get("last_username");
            LOADED.put(user.name.toLowerCase(), user);
            return user;
        }
        catch (Exception e) {
            Server.logError(e.getMessage());
            return null;
        }
    }

    public static XenUser get(int userId) {
        for (XenUser user : LOADED.values()) {
            if (user.id != userId) continue;
            return user;
        }
        return null;
    }

    public static XenUser get(String name) {
        return LOADED.get(name.toLowerCase());
    }
}

