package io.ruin.central.model.world;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.util.concurrent.GenericFutureListener;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.protocol.Response;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.protocol.world.WorldStage;
import io.ruin.api.protocol.world.WorldType;
import io.ruin.api.utils.IPAddress;
import io.ruin.central.Server;
import io.ruin.central.model.Player;
import io.ruin.central.network.WorldDecoder;
import io.ruin.central.network.WorldSender;
import io.ruin.central.utility.Limbo;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class World extends WorldSender {

    public final int id;
    public final WorldStage stage;
    public final WorldType type;
    public final int settings;
    public final String host;
    public final String activity;
    public final int flag;
    public final ConcurrentLinkedQueue<LoginInfo> logins = new ConcurrentLinkedQueue();
    public final ArrayList<Player> players = new ArrayList();
    public WorldDecoder decoder;
    private long lastPing;

    private World(Channel channel, int id, int stage, int type, int settings, String host, String activity, int flag) {
        this.channel = channel;
        this.id = id;
        this.stage = WorldStage.values()[stage];
        this.type = WorldType.values()[type];
        this.settings = settings;
        this.host = host;
        this.activity = activity;
        this.flag = flag;
    }

    public void sendResponse(boolean connected) {
        OutBuffer out = WorldSender.getResponse(connected);
        if (connected) {
            Limbo.clear(this.id);
            WorldList.add(this);
            this.channel.writeAndFlush((Object) out.toBuffer());
            this.decoder = new WorldDecoder();
            this.channel.pipeline().replace("decoder", "decoder", (ChannelHandler) this.decoder);
            System.out.println("World " + this.id + " connected!");
        } else {
            this.channel.writeAndFlush((Object) out.toBuffer()).addListener((GenericFutureListener) ChannelFutureListener.CLOSE);
            System.out.println("World " + this.id + " is already connected!");
        }
    }

    public void destroy() {
        if (!this.players.isEmpty()) {
            Limbo.add(this);
            this.players.clear();
        }
        WorldList.remove(this);
    }

    private void finishLogin(LoginInfo info) {
        if (Server.isOnline(info.userId, info.name)) {
            this.sendLoginFailed(info.name, Response.ALREADY_LOGGED_IN);
            return;
        }
        this.players.add(new Player(info.userId, info.name, info.groupIds, this));
        this.sendLoginSuccess(info.userId, info.name, info.saved, info.groupIds, info.unreadPMs);
    }

    public void removePlayer(int userId) {
        Player player = this.get(userId);
        if (player == null) {
            return;
        }
        this.players.remove(player);
        player.destroy();
    }

    public Player get(int userId) {
        for (Player player : this.players) {
            if (player.userId != userId) continue;
            return player;
        }
        return null;
    }

    public boolean hasPlayer(int userId) {
        return this.get(userId) != null;
    }

    public boolean process() {
        if (!this.channel.isActive()) {
            System.out.println("World " + this.id + " disconnected!");
            return false;
        }
        long currentMs = System.currentTimeMillis();
        if (currentMs - this.decoder.lastMessageAt >= 30000L) {
            System.out.println("World " + this.id + " timed out!");
            return false;
        }
        if (currentMs - this.lastPing >= 1200L) {
            this.lastPing = currentMs;
            this.sendPing();
        }
        this.decoder.process(this, -1);
        this.logins.removeIf(login -> {
                    this.finishLogin(login);
                    return true;
                }
        );
        for (Player player : this.players) {
            player.process();
        }
        this.flush();
        return true;
    }

    public static World decode(Channel channel, InBuffer in) {
        int size = in.readUnsignedShort();
        if (in.remaining() != size) {
            System.err.println("Invalid World Request 1: " + IPAddress.get(channel));
            return null;
        }
        if (in.readLong() != 7281996L) {
            System.err.println("Invalid World Request 2: " + IPAddress.get(channel));
            return null;
        }
        int id = in.readUnsignedShort();
        int stage = in.readUnsignedByte();
        int type = in.readUnsignedByte();
        int settings = in.readInt();
        String host = in.readString();
        String activity = in.readString();
        int flag = in.readUnsignedByte();
        World world = new World(channel, id, stage, type, settings, host, activity, flag);
        while (in.remaining() > 0) {
            int groupId;
            int userId = in.readInt();
            String name = in.readString();
            ArrayList<Integer> groupIds = new ArrayList<Integer>();
            while ((groupId = in.readUnsignedByte()) != 0) {
                groupIds.add(groupId);
            }
            world.players.add(new Player(userId, name, groupIds, world));
        }
        return world;
    }
}

