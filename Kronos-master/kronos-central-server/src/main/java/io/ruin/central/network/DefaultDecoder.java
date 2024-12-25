package io.ruin.central.network;

import io.ruin.central.Server;
import io.ruin.central.model.world.World;
import io.netty.channel.Channel;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.netty.MessageDecoder;

public class DefaultDecoder extends MessageDecoder<Channel> {

    public DefaultDecoder() {
        super(null, false);
    }

    @Override
    protected void handle(Channel channel, InBuffer in, int opcode) {
        if (opcode == 13) {
            World world = World.decode(channel, in);
            if (world == null) {
                channel.close();
            } else {
                Server.queueWorld(world);
            }
        }
    }

    @Override
    protected int getSize(int opcode) {
        switch (opcode) {
            case 13: {
                return -2;
            }
        }
        return -128;
    }
}

