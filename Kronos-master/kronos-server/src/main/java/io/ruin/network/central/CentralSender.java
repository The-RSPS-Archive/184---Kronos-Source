package io.ruin.network.central;

import io.netty.channel.Channel;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.protocol.Protocol;
import io.ruin.api.utils.PunishmentType;

public class CentralSender {

    protected static Channel channel;

    private static void write(OutBuffer out) {
        if(channel == null)
            return;
        out.encode(null);
        channel.write(out.toBuffer());
    }

    /**
     * Default packets
     */

    protected static void sendPing() {
        OutBuffer out = new OutBuffer(1).sendFixedPacket(0);
        write(out);
    }

    public static void sendLogin(String ip, String name, String password, String email, String macAddress, String uuid, int tfaCode, boolean tfaTrust, int tfaTrustKey) {
        OutBuffer out = new OutBuffer(2 + Protocol.strLen(ip) + Protocol.strLen(name) + Protocol.strLen(password) + Protocol.strLen(email) +  5).sendVarBytePacket(1)
                .addString(ip)
                .addString(name)
                .addString(password)
                .addString(email)
                .addString(macAddress)
                .addString(uuid)
                .addMedium(tfaCode)
                .addByte(tfaTrust ? 1 : 0)
                .addByte(tfaTrustKey);
        write(out);
    }

    public static void sendLogout(int userId) {
        OutBuffer out = new OutBuffer(5).sendFixedPacket(2)
                .addInt(userId);
        write(out);
    }

    public static void sendSave(int userId, int attempt, String json) {
        OutBuffer out = new OutBuffer(8 + Protocol.strLen(json)).sendVarIntPacket(3)
                .addInt(userId)
                .addByte(attempt)
                .addString(json);
        write(out);
    }

    public static void sendGlobalMessage(int userId, String message) {
        OutBuffer out = new OutBuffer(6 + Protocol.strLen(message)).sendVarBytePacket(4)
                .addInt(userId)
                .addString(message);
        write(out);
    }

    /**
     * Social packets
     */

    public static void sendPrivacy(int userId, int privacy) {
        OutBuffer out = new OutBuffer(6).sendFixedPacket(5)
                .addInt(userId)
                .addByte(privacy);
        write(out);
    }

    public static void sendSocialRequest(int userId, String toName, int requestType) {
        OutBuffer out = new OutBuffer(7 + Protocol.strLen(toName)).sendVarBytePacket(6)
                .addInt(userId)
                .addString(toName)
                .addByte(requestType);
        write(out);
    }

    public static void sendPrivateMessage(int userId, int rankId, String toName, String message) {
        OutBuffer out = new OutBuffer(7 + Protocol.strLen(toName) + Protocol.strLen(message)).sendVarBytePacket(7)
                .addInt(userId)
                .addByte(rankId)
                .addString(toName)
                .addString(message);
        write(out);
    }

    public static void sendClanName(int userId, String clanName) {
        OutBuffer out = new OutBuffer(6 + Protocol.strLen(clanName)).sendVarBytePacket(8)
                .addInt(userId)
                .addString(clanName);
        write(out);
    }

    public static void sendClanSetting(int userId, int settingId, int value) {
        OutBuffer out = new OutBuffer(7).sendFixedPacket(9)
                .addInt(userId)
                .addByte(settingId)
                .addByte(value);
        write(out);
    }

    public static void sendClanRank(int userId, String friendName, int friendRank) {
        OutBuffer out = new OutBuffer(7 + Protocol.strLen(friendName)).sendVarBytePacket(10)
                .addInt(userId)
                .addString(friendName)
                .addByte(friendRank);
        write(out);
    }

    public static void sendClanRequest(int userId, String ownerName) {
        OutBuffer out = new OutBuffer(6 + Protocol.strLen(ownerName)).sendVarBytePacket(11)
                .addInt(userId)
                .addString(ownerName);
        write(out);
    }

    public static void sendClanKick(int userId, String kickName) {
        OutBuffer out = new OutBuffer(6 + Protocol.strLen(kickName)).sendVarBytePacket(12)
                .addInt(userId)
                .addString(kickName);
        write(out);
    }

    public static void sendClanMessage(int userId, int rankId, String message) {
        OutBuffer out = new OutBuffer(7 + Protocol.strLen(message)).sendVarBytePacket(13)
                .addInt(userId)
                .addByte(rankId)
                .addString(message);
        write(out);
    }

    public static void reloadBans() {
        OutBuffer out = new OutBuffer(2).sendFixedPacket(14);
        write(out);
    }

    public static void requestUUIDBan(String uuid) {
        OutBuffer out = new OutBuffer(1 + Protocol.strLen(uuid)).sendVarBytePacket(15)
                .addString(uuid);
        write(out);
    }

    public static void requestIPBan(int userId, String ip) {
        OutBuffer out = new OutBuffer(5 + Protocol.strLen(ip)).sendVarBytePacket(16)
                .addInt(userId)
                .addString(ip);
        write(out);
    }

    public static void requestMACBan(int userId, String mac) {
        OutBuffer out = new OutBuffer(5 + Protocol.strLen(mac)).sendVarBytePacket(17)
                .addInt(userId)
                .addString(mac);
        write(out);
    }

}