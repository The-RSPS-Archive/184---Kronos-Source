package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {13})
public class RegionUpdateHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
    }

}