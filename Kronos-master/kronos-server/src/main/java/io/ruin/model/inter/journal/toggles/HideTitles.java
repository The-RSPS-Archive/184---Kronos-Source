package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class HideTitles extends JournalEntry {

    public static void update(Player player) {
        player.getPacketSender().sendVarp(20005, player.hideTitles ? 1 : 0);
    }

    @Override
    public void send(Player player) {
        if(!player.hideTitles)
            send(player, "Hide Player Titles", "Disabled", Color.RED);
        else
            send(player, "Hide Player Titles", "Enabled", Color.GREEN);
        update(player);
    }

    @Override
    public void select(Player player) {
        player.hideTitles = !player.hideTitles;
        send(player);
    }

}
