package io.ruin.api.utils;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

/**
 * @author ReverendDread on 6/29/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Slf4j
public class UUIDBan {

    private static final List<String> banned_ips = Lists.newArrayList();
    private static final File store = new File(System.getProperty("user.home") + "/Desktop/kronos/uuid_bans.txt");

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
            banned_ips.add(token);
        }
        br.close();
        //log.info("Loaded {} uuid bans.", banned_ips.size());
    }

    private static void store(String uuid) {
        try (FileWriter fw = new FileWriter(store, true)){
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(uuid);
            log.info("UUID banned {}", uuid);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean requestBan(String uuid) {
        banned_ips.add(uuid);
        store(uuid);
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

    public static boolean isUUIDBanned(String ip) {
        return banned_ips.contains(ip);
    }

}
