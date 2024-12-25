package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class BroadcastHotspot extends JournalEntry {

    @Override
    public void send(Player player) {
        if(!player.broadcastHotspot)
            send(player, "Hotspot", "Disabled", Color.RED);
        else
            send(player, "Hotspot", "Enabled", Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.broadcastHotspot = !player.broadcastHotspot;
        if(player.broadcastHotspot)
            player.sendMessage(Color.DARK_GREEN.wrap("You will now get broadcasted messages about the Hotspot."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("You will no longer get broadcasted messaged about the Hotspot."));
        send(player);
    }

}
