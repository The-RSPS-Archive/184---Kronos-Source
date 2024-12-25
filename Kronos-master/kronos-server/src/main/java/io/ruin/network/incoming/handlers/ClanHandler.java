package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {53, 22})
public class ClanHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String username = in.readString();
        if(opcode == 53) {
            /**
             * Join / Leave
             */
            CentralClient.sendClanRequest(player.getUserId(), username);
            return;
        }
        if(opcode == 22) {
            /**
             * Kick
             */
            CentralClient.sendClanKick(player.getUserId(), username);
            return;
        }
    }

}