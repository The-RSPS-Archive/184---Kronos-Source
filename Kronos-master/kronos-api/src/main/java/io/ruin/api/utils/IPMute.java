package io.ruin.api.utils;

import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;

/**
 * @author ReverendDread on 6/28/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Slf4j
public class IPMute {

    private static final Map<String, String> ip_mutes = Maps.newHashMap();
    private static final File store = new File(System.getProperty("user.home") + "/Desktop/kronos/ip_mutes.txt");

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
            ip_mutes.put(user, ip);
        }
        br.close();
        //log.info("Loaded {} muted IPs.", ip_mutes.size());
    }

    private static void store(String user, String ip) {
        try (FileWriter fw = new FileWriter(store, true)){
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(user + " : " + ip);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean requestMute(String user, String ip) {
        ip_mutes.put(user, ip);
        store(user, ip);
        return true;
    }

    public static boolean refreshMutes() {
        ip_mutes.clear();
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isIPMuted(String ip) {
        return ip_mutes.containsValue(ip);
    }

}
