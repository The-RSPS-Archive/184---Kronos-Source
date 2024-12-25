package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.utility.Huffman;
import io.ruin.api.protocol.Protocol;
import io.ruin.model.entity.player.Player;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.services.Loggers;
import io.ruin.services.Punishment;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {84, 80, 48, 56, 93, 67})
public class FriendsHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        String name;
        if(opcode == 67) {
            /**
             * Rank friend
             */
            int rank = in.readByte();
            name = in.readString();
            CentralClient.sendClanRank(player.getUserId(), name, rank);
            return;
        }
        name = in.readString();
        if(opcode == 80) {
            /**
             * Add friend
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 1);
            return;
        }
        if(opcode == 48) {
            /**
             * Delete friend
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 2);
            return;
        }
        if(opcode == 84) {
            /**
             * Add ignore
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 3);
            return;
        }
        if(opcode == 56) {
            /**
             * Delete ignore
             */
            CentralClient.sendSocialRequest(player.getUserId(), name, 4);
            return;
        }
        if(opcode == 93) {
            /**
             * Private message
             */
            String message = Huffman.decrypt(in, 100);
            if(Punishment.isMuted(player)) {
                if(player.shadowMute)
                    player.getPacketSender().write(Protocol.outgoingPm(name, message));
                else
                    player.sendMessage("You're muted and can't talk.");
                return;
            }
            CentralClient.sendPrivateMessage(player.getUserId(), player.getClientGroupId(), name, message);
            Loggers.logPrivateChat(player.getUserId(), player.getName(), player.getIp(), name, message);
            return;
        }
    }

}