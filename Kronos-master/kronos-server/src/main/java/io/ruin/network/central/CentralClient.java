package io.ruin.network.central;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.ruin.Server;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.netty.ExceptionHandler;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.ThreadUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.process.task.TaskWorker;
import kilim.Task;

import java.io.IOException;

//todo - rewrite
public class CentralClient extends CentralSender {

    private static Bootstrap bootstrap;

    private static CentralDecoder decoder;

    private static long lastPing;

    public static void start() {
        bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("exception_handler", new ExceptionHandler());
            }
        });
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.remoteAddress(World.getCentralAddress(), 3845); //this ip must stay private
        TaskWorker.startTask(t -> {
            while(true) {
                process();
                Task.sleep(32L);
            }
        });
    }

    private static void process() {
        if(!validateConnection())
            close();
        else
            channel.flush();
    }

    private static boolean validateConnection() {
        if(channel == null || decoder == null) {
            /* connection not yet established */
            return establish();
        }
        if(!channel.isActive()) {
            System.out.println("Disconnected from Central Server!");
            return false;
        }
        long currentMs = System.currentTimeMillis();
        if(currentMs - decoder.lastMessageAt >= 30000) {
            System.out.println("Timed out from Central Server!");
            return false;
        }
        if(currentMs - lastPing >= 1200) {
            lastPing = currentMs;
            sendPing();
        }
        return true;
    }

    private static boolean establish() {
        ServerWrapper.println("Connecting to Central Server...");
        try {
            ChannelFuture future = bootstrap.connect().awaitUninterruptibly();
            if(!future.isDone())
                throw new IOException("Incomplete");
            if(future.isCancelled())
                throw new IOException("Cancelled");
            if(!future.isSuccess())
                throw future.cause();
            channel = future.channel();
            channel.pipeline().addFirst("decoder", decoder = new CentralDecoder());
            connect();
            decoder.awaitResponse();
            ServerWrapper.println("Central Server Connected!");
            return true;
        } catch(Throwable t) {
            ServerWrapper.println("Central Server Connection Failed: " + t.getMessage());
            close();
            ThreadUtils.sleep(1000L);
            return false;
        }
    }

    private static void connect() {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(13);
        int startPos = out.position();
        out.addShort(0) //expected length
                .addLong(7281996L)
                .addShort(World.id)
                .addByte(World.stage.ordinal())
                .addByte(World.type.ordinal())
                .addInt(World.settings)
                .addString(World.address)
                .addString(World.name)
                .addByte(World.flag.ordinal());
        Server.worker.executeLast(() -> {
            for(Player player : World.players.entityList) {
                if(player == null)
                    continue;
                out.addInt(player.getUserId());
                out.addString(player.getName());
                boolean[] groups = player.getGroups();
                for(int i = 0; i < groups.length; i++) {
                    if(groups[i])
                        out.addByte(i);
                }
                out.addByte(0); //ends groups
            }
            int endPos = out.position();
            out.position(startPos);
            out.addShort(endPos - startPos - 2);
            out.position(endPos);
            channel.writeAndFlush(out.encode(null).toBuffer());
        });
    }

    public static void close() {
        if(channel != null) {
            channel.close();
            channel = null;
        }
        decoder = null;
    }

}