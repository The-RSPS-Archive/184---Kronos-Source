package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class BroadcastActiveVolcano extends JournalEntry {

    @Override
    public void send(Player player) {
        if(!player.broadcastActiveVolcano)
            send(player, "Active Volcano", "Disabled", Color.RED);
        else
            send(player, "Active Volcano", "Enabled", Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.broadcastActiveVolcano = !player.broadcastActiveVolcano;
        if(player.broadcastActiveVolcano)
            player.sendMessage(Color.DARK_GREEN.wrap("You will now get broadcasted messages about the Active Volcano event."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("You will no longer get broadcasted messaged about the Active Volcano event."));
        send(player);
    }

}
