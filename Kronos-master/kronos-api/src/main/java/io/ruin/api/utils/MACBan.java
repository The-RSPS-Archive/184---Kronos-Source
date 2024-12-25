package io.ruin.api.utils;

import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;

/**
 * @author ReverendDread on 6/27/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Slf4j
public class MACBan {

    private static final Map<String, String> banned_macs = Maps.newHashMap();
    private static final File store = new File(System.getProperty("user.home") + "/Desktop/kronos/mac_bans.txt");

    static {
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the file at server startup
     */
    @SneakyThrows
    private static void initialize() throws IOException {
        if (!store.exists()) {
            store.createNewFile();
        }
        String token;
        BufferedReader br = new BufferedReader(new FileReader(store));
        while ((token = br.readLine()) != null && !token.isEmpty()) {
            String[] split = token.split(" : ");
            String user = split[0], mac = split[1];
            banned_macs.put(user, mac);
        }
        br.close();
        //log.info("Loaded {} banned MAC bans.", banned_macs.size());
    }

    private static void store(String user, String mac) {
        try (FileWriter fw = new FileWriter(store, true)){
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(user + " : " + mac);
            log.info("MAC banned {}, {}", user, mac);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean requestBan(String user, String mac) {
        banned_macs.put(user, mac);
        store(user, mac);
        return true;
    }

    public static boolean refreshBans() {
        banned_macs.clear();
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isMACBanned(String mac) {
        return banned_macs.containsValue(mac);
    }

}
