package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class SwapMagePrayers extends JournalEntry {

    public static void update(Player player) {
        player.getPacketSender().setAlignment(541, (player.swapMagePrayerPosition ? 31 : 27), 0, 148);
        player.getPacketSender().setAlignment(541, (player.swapMagePrayerPosition ? 27 : 31), 111, 185);
    }

    @Override
    public void send(Player player) {
        if(player.swapMagePrayerPosition)
            send(player, "Swap Mage Prayer", "Enabled", Color.GREEN);
        else
            send(player, "Swap Mage Prayer", "Disabled", Color.RED);
        update(player);
    }

    @Override
    public void select(Player player) {
        player.swapMagePrayerPosition = !player.swapMagePrayerPosition;
        send(player);
    }
}