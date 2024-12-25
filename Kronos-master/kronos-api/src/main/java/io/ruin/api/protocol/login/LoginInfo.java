package io.ruin.api.protocol.login;

import io.netty.channel.Channel;
import io.ruin.api.utils.IPAddress;

import java.util.List;

public class LoginInfo {

    public final Channel channel;
    public final String ipAddress;
    public final int ipAddressInt;
    public final String macAddress;
    public final String password;
    public final String email;
    public final String uuid;
    public final int tfaCode;
    public final boolean tfaTrust;
    public final int tfaTrustValue;
    public final int worldId;
    public final int[] keys;

    public int userId = -1;
    public String name;
    public String saved;
    public int unreadPMs;
    public List<Integer> groupIds;

    public LoginInfo(String ip, String name, String password, String email, String macAddress, String uuid, int tfaCode, boolean tfaTrust, int tfaTrustValue) {
        this.channel = null;
        this.ipAddress = ip;
        this.ipAddressInt = IPAddress.toInt(ipAddress);
        this.name = name;
        this.password = password;
        this.email = email;
        this.tfaCode = tfaCode;
        this.tfaTrust = tfaTrust;
        this.tfaTrustValue = tfaTrustValue;
        this.worldId = -1;
        this.keys = null;
        this.unreadPMs = 0;
        this.macAddress = macAddress;
        this.uuid = uuid;
    }

    public LoginInfo(Channel channel, String name, String password, String email, String macAddress, String uuid, int tfaCode, boolean tfaTrust, int tfaTrustValue, int worldId, int[] keys) {
        this.channel = channel;
        if(channel.id() == null) { //bots
            this.ipAddress = "";
            this.ipAddressInt = 0;
        } else {
            this.ipAddress = IPAddress.get(channel);
            this.ipAddressInt = IPAddress.toInt(ipAddress);
        }
        this.name = name;
        this.password = password;
        this.email = email;
        this.uuid = uuid;
        this.tfaCode = tfaCode;
        this.tfaTrust = tfaTrust;
        this.tfaTrustValue = tfaTrustValue;
        this.worldId = worldId;
        this.keys = keys;
        this.unreadPMs = 0;
        this.macAddress = macAddress;
    }

    public void update(int userId, String name, String saved, List<Integer> groupIds, int unreadPMs) {
        this.userId = userId;
        this.name = name;
        this.saved = saved;
        this.groupIds = groupIds;
        this.unreadPMs = unreadPMs;
    }

}