package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.player.Player;

/**
 * @author ReverendDread on 7/19/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class Traveling {

    public static void fadeTravel(Player player, int x, int y, int z) {
        player.startEvent(e -> {
            player.getPacketSender().fadeOut();
            e.delay(2);
            player.getMovement().teleport(x, y, z);
            player.getPacketSender().clearFade();
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

}
