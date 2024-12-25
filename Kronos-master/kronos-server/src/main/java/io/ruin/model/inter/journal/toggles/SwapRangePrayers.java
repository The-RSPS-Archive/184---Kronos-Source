package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class SwapRangePrayers extends JournalEntry {

    public static void update(Player player) {
        player.getPacketSender().setAlignment(541, (player.swapRangePrayerPosition ? 26 : 30), 74, 185);
        player.getPacketSender().setAlignment(541, (player.swapRangePrayerPosition ? 30 : 26), 148, 111);
    }

    @Override
    public void send(Player player) {
        if(player.swapRangePrayerPosition)
            send(player, "Swap Range Prayer", "Enabled", Color.GREEN);
        else
            send(player, "Swap Range Prayer", "Disabled", Color.RED);
        update(player);
    }

    @Override
    public void select(Player player) {
        player.swapRangePrayerPosition = !player.swapRangePrayerPosition;
        send(player);
    }

}