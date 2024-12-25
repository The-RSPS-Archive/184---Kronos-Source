package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class BroadcastBossEvent extends JournalEntry {

    @Override
    public void send(Player player) {
        if(!player.broadcastBossEvent)
            send(player, "Boss Events", "Disabled", Color.RED);
        else
            send(player, "Boss Events", "Enabled", Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.broadcastBossEvent = !player.broadcastBossEvent;
        if(player.broadcastBossEvent)
            player.sendMessage(Color.DARK_GREEN.wrap("You will now get broadcasted messages about the Wilderness Boss event."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("You will no longer get broadcasted messaged about the Wilderness Boss event."));
        send(player);
    }

}
