package io.ruin.network.central;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.netty.MessageDecoder;
import io.ruin.api.protocol.Response;
import io.ruin.api.protocol.login.LoginRequest;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.process.LoginWorker;
import kilim.Pausable;
import kilim.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CentralDecoder extends MessageDecoder {

    private volatile byte response;

    private CountDownLatch latch = new CountDownLatch(1);

    protected long lastMessageAt = System.currentTimeMillis();

    protected CentralDecoder() {
        super(null, false);
    }

    protected final void awaitResponse() throws IOException {
        try {
            latch.await(10L, TimeUnit.SECONDS);
        } catch(InterruptedException e) {
            /* ignored */
        }
        latch = null;
        if(response != 1)
            throw new IOException("Connection Denied: " + response);
    }

    @Override
    protected void handle(Object object, InBuffer in, int opcode) {
        lastMessageAt = System.currentTimeMillis();
        if(opcode == 0) {
            /**
             * Ping
             */
            return;
        }
        if(opcode == 1) {
            /**
             * Connection response
             */
            response = in.readByte();
            latch.countDown();
            return;
        }
        if(opcode == 2) {
            /**
             * Login response
             */
            Response response = Response.valueOf(in.readUnsignedByte());
            String name = in.readString();
            LoginRequest request = LoginWorker.get(name);
            if(request == null)
                return;
            if(response != Response.SUCCESS) {
                request.deny(response);
                return;
            }
            int userId = in.readInt();
            String saved = in.readString();
            byte unreadPMs = in.readByte();
            List<Integer> groupIds = null;
            while(in.remaining() > 0) {
                if(groupIds == null)
                    groupIds = new ArrayList<>();
                groupIds.add(in.readUnsignedByte());
            }
            request.info.update(userId, name, saved, groupIds, unreadPMs);
            request.success();
            return;
        }
        if(opcode == 3) {
            /**
             * Save response
             */
            int userId = in.readInt();
            int attempt = in.readUnsignedByte();
            Player player = World.getPlayer(userId, false);
            if(player != null)
                player.finishLogout(attempt);
            return;
        }
        if(opcode == 4) {
            /**
             * Online check
             */
            int userId = in.readInt();
            if(World.getPlayer(userId, false) == null) {
                System.out.println("Logging out 'stuck' user: " + userId);
                CentralClient.sendLogout(userId);
            }
            return;
        }
        if(opcode == 98 || opcode == 99) {
            /**
             * Client packet
             */
            int userId = in.readInt();
            OutBuffer out = new OutBuffer(in.remaining());
            while(in.remaining() > 0)
                out.addByte(in.readByte());
            new Task() { //todo - come back to this
                @Override
                public void execute() throws Pausable {
                    long sleepPeriod = 10L;
                    long timeoutPeriod = 5000L;
                    while(true) {
                        Player player = World.getPlayer(userId, true);
                        if(player != null && player.isOnline()) {
                            Server.worker.execute(() -> player.getPacketSender().write(out));
                            return;
                        }
                        if(timeoutPeriod < sleepPeriod)
                            return;
                        timeoutPeriod -= sleepPeriod;
                        Task.sleep(sleepPeriod);
                    }
                }
            }.start();
            return;
        }
    }

    @Override
    protected int getSize(int opcode) {
        switch(opcode) {
            case 0:  return 0;          //ping
            case 1:  return 1;          //connected
            case 2:  return VAR_INT;    //login response
            case 3:  return 5;          //saved response
            case 4:  return 4;          //online check
            case 98: return VAR_BYTE;   //client packet
            case 99: return VAR_SHORT;  //client packet
        }
        return UNHANDLED;
    }

}