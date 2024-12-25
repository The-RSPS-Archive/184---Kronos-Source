package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.utility.Huffman;
import io.ruin.api.utils.IPMute;
import io.ruin.model.entity.player.Player;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.services.Loggers;
import io.ruin.services.Punishment;
import io.ruin.utility.IdHolder;

import java.util.Arrays;
import java.util.List;

@IdHolder(ids = {32})
public class ChatHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        boolean shadow = false;
        if(IPMute.isIPMuted(player.getIp()) || Punishment.isMuted(player)) {
            if(!player.shadowMute) {
                player.sendMessage("You're muted and can't talk.");
                return;
            }
            shadow = true;
        }

        int type = in.readByte();
        int effects = in.readShort();
        String message = Huffman.decrypt(in, 100);
        if(message.isEmpty()) {
            /* how does this even happen? */
            return;
        }

        /*
        * If player mentions any of these words don't send it.
        */
        List<String> badWords = Arrays.asList("runite", "ikov", "zenyte", "roatz", "alora", "nigger");
        for(String word : badWords) {
            if (message.toLowerCase().contains(word)) {
                return;
            }
        }
        if(type == 2) {
            message = message.substring(1, message.length());
            CentralClient.sendClanMessage(player.getUserId(), player.getClientGroupId(), message);
            Loggers.logClanChat(player.getUserId(), player.getName(), player.getIp(), message);
            return;
        }
        if(message.length() >= 3) {
            char c1 = message.charAt(0);
            char c2 = message.charAt(1);
            if((c1 == ':' && c2 == ':') || (c1 == ';' && c2 == ';')) {
                CommandHandler.handle(player, message.substring(2));
                return;
            }
            if (c1 == '!' && player.isAdmin()) {
                player.forceText(message.substring(1));
                return;
            }
        }
        player.getChatUpdate().set(shadow, effects, player.getClientGroupId(), type, message);
        Loggers.logPublicChat(player.getUserId(), player.getName(), player.getIp(), message, type, effects);
    }

}