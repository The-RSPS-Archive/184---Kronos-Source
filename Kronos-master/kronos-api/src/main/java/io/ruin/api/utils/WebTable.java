package io.ruin.api.utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class WebTable {

    private static final Gson GSON = new Gson();

    private LinkedHashMap<String, String> header;

    private ArrayList<LinkedHashMap<String, String>> entries;

    private LinkedHashMap<String, String> lastEntry;

    public WebTable(String title) {
        header = new LinkedHashMap<>();
        header.put("title", title);
        entries = new ArrayList<>();
    }

    public WebTable requireIp(String ip) {
        if(!ip.equals("127.0.0.1"))
            header.put("required_ip", ip);
        return this;
    }

    public WebTable newEntry() {
        entries.add(lastEntry = new LinkedHashMap<>());
        return this;
    }

    public WebTable add(String key, Object value) {
        lastEntry.put(key, ""+value);
        return this;
    }

    public String getURL() {
        lastEntry = null;
        StringBuilder sb = new StringBuilder()
                .append(GSON.toJson(header))
                .append('\n')
                .append(GSON.toJson(entries));
        String name = "" + System.currentTimeMillis();
        String result = PostWorker.post("https://noveaps.com/extra/tools/table/write.php?k=93lkS3j6Q&n=" + name, sb.toString().getBytes());
        if(result == null || !result.equals("1"))
            return null;
        return "https://noveaps.com/extra/tools/table/?t=" + name;
    }

}