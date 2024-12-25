package io.ruin.api.utils;

import io.netty.channel.Channel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class IPAddress {

    public static String get() throws IOException {
        String[] checkSites = {
                "http://checkip.amazonaws.com",
                "http://bot.whatismyipaddress.com",
                "http://icanhazip.com/"
        };
        for(String site : checkSites) {
            try(BufferedReader in = new BufferedReader(new InputStreamReader(new URL(site).openStream()))) {
                String ip = in.readLine();
                if(ip != null && (ip = ip.trim()).length() > 0)
                    return ip;
            } catch(Exception e) {
                System.err.println("Failed to read IP from " + site + " (" + e.getMessage() + ")");
            }
        }
        throw new IOException("Failed to obtain IP Address!");
    }

    public static String get(Channel channel) {
        return channel.remoteAddress().toString().split(":")[0].replace("/", "");
    }

    public static int toInt(String ip) {
        try {
            byte[] bytes = InetAddress.getByName(ip).getAddress();
            int val = 0;
            for(byte b : bytes) {
                val <<= 8;
                val |= b & 0xff;
            }
            return val;
        } catch(Exception e) {
            ServerWrapper.logError("", e);
            return -1;
        }
    }

}
