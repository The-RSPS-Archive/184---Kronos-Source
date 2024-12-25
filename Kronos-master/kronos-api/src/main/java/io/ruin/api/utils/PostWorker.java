package io.ruin.api.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class PostWorker {

    public static String post(String url, Map<Object, Object> postMap) {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(5000);
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Connection","Keep-Alive");

            try(PrintStream ps = new PrintStream(con.getOutputStream())) {
                boolean first = true;
                for(Map.Entry<Object, Object> post : postMap.entrySet()) {
                    String key = URLEncoder.encode(""+post.getKey(), "UTF-8");
                    String value = URLEncoder.encode(""+post.getValue(), "UTF-8");
                    ps.print((first ? "" : "&") + key + "=" + value);
                    first = false;
                }
            }

            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while((line = br.readLine()) != null)
                    response.append(line);
                return response.toString();
            }
        } catch(IOException e) {
            ServerWrapper.logError("Failed to post: " + url, e);
            return null;
        } finally {
            if(con != null)
                con.disconnect();
        }
    }

    public static String post(String url, byte[] data) {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(5000);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Connection","Keep-Alive");
            con.setRequestProperty("Content-Length", Integer.toString(data.length));

            try(DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
                out.write(data);
            }

            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while((line = br.readLine()) != null)
                    response.append(line);
                return response.toString();
            }
        } catch(IOException e) {
            ServerWrapper.logError("Failed to post: " + url, e);
            return null;
        } finally {
            if(con != null)
                con.disconnect();
        }
    }

    public static String postArray(String url, Map<Object, Object> map) {
        return post(url, JsonUtils.toJson(map).getBytes());
    }

}