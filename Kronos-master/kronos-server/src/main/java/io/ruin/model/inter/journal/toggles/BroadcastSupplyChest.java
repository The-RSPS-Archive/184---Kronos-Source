package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class BroadcastSupplyChest extends JournalEntry {

    @Override
    public void send(Player player) {
        if(!player.broadcastSupplyChest)
            send(player, "Supply Chest", "Disabled", Color.RED);
        else
            send(player, "Supply Chest", "Enabled", Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.broadcastSupplyChest = !player.broadcastSupplyChest;
        if(player.broadcastSupplyChest)
            player.sendMessage(Color.DARK_GREEN.wrap("You will now get broadcasted messages about the Supply Chest."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("You will no longer get broadcasted messaged about the Supply Ches."));
        send(player);
    }

}
