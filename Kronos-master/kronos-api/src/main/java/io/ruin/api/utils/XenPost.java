package io.ruin.api.utils;

import java.util.Map;

public class XenPost {


    private static final String URL = "https://community.kronos.rip/integration/index.php";

    private static final String AUTH = "wrWn4Vsf6bQP2WR3f7KnLs9erLPpdJTw";

    public static String post(String file, Map<Object, Object> map) {
        map.put("auth", AUTH);
        map.put("file", file);
        return PostWorker.postArray(URL, map);
    }

}
