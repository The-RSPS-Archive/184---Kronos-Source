package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {7})
public class TeleportHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        int z = in.readByteS();
        int unknown = in.readLEInt();
        int y = in.readShort();
        int x = in.readLEShortA();

        player.getMovement().teleport(x, y, z);
    }

}
