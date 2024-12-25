package io.ruin.api.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author ReverendDread on 6/26/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Slf4j
public class IPBans {

    private static final Map<String, String> banned_ips = Maps.newHashMap();
    private static final File store = new File(System.getProperty("user.home") + "/Desktop/kronos/ip_bans.txt");

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
            String user = split[0], ip = split[1];
            banned_ips.put(user, ip);
        }
        br.close();
        //log.info("Loaded {} banned IPs.", banned_ips.size());
    }

    private static void store(String user, String ip) {
        try (FileWriter fw = new FileWriter(store, true)){
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(user + " : " + ip);
            log.info("IP banned {}, {}", user, ip);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean requestBan(String user, String ip) {
        banned_ips.put(user, ip);
        store(user, ip);
        return true;
    }

    public static boolean refreshBans() {
        banned_ips.clear();
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isIPBanned(String ip) {
        return banned_ips.containsValue(ip);
    }

}
