package io.ruin.central.network;

import io.netty.channel.Channel;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.protocol.Protocol;
import io.ruin.api.protocol.Response;

import java.util.Iterator;
import java.util.List;

public class WorldSender {

    protected Channel channel;

    protected final void write(OutBuffer out) {
        out.encode(null);
        this.channel.write((Object)out.toBuffer());
    }

    protected void flush() {
        this.channel.flush();
    }

    public void sendPing() {
        OutBuffer out = new OutBuffer(1).sendFixedPacket(0);
        this.write(out);
    }

    public static OutBuffer getResponse(boolean connected) {
        return new OutBuffer(2).sendFixedPacket(1).addByte(connected ? 1 : 0);
    }

    public void sendLoginFailed(String name, Response response) {
        int size = 3;
        ++size;
        OutBuffer out = new OutBuffer(size +=  Protocol.strLen(name)).sendVarIntPacket(2).addByte(response.ordinal()).addString(name);
        this.write(out);
    }

    public void sendLoginSuccess(int userId, String name, String saved, List<Integer> groupIds, int unreadPMs) {
        int size = 3;
        ++size;
        size += Protocol.strLen(name);
        size += 4;
        size += Protocol.strLen(saved);
        size += 1;
        OutBuffer out = new OutBuffer(size += groupIds.size()).sendVarIntPacket(2).addByte(Response.SUCCESS.ordinal()).addString(name).addInt(userId).addString(saved).addByte(unreadPMs);
        Iterator<Integer> iterator = groupIds.iterator();
        while (iterator.hasNext()) {
            int groupId = iterator.next();
            out.addByte(groupId);
        }
        this.write(out);
    }

    public void sendSaveResponse(int userId, int attempt) {
        OutBuffer out = new OutBuffer(6).sendFixedPacket(3).addInt(userId).addByte(attempt);
        this.write(out);
    }

    public void sendOnlineCheck(int userId) {
        OutBuffer out = new OutBuffer(5).sendFixedPacket(4).addInt(userId);
        this.write(out);
    }

    public void sendClientPacket(int userId, byte[] data) {
        OutBuffer out = 4 + data.length < 256 ? new OutBuffer(6 + data.length).sendVarBytePacket(98) : new OutBuffer(7 + data.length).sendVarShortPacket(99);
        out.addInt(userId);
        out.addBytes(data);
        this.write(out);
    }
}

