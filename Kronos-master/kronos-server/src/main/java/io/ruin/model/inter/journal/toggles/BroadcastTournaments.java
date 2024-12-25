package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class BroadcastTournaments extends JournalEntry {

    @Override
    public void send(Player player) {
        if(!player.broadcastTournaments)
            send(player, "Tournaments", "Disabled", Color.RED);
        else
            send(player, "Tournaments", "Enabled", Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.broadcastTournaments = !player.broadcastTournaments;
        if(player.broadcastTournaments)
            player.sendMessage(Color.DARK_GREEN.wrap("You will now get broadcasted messages about the Tournaments."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("You will no longer get broadcasted messaged about the Tournaments."));
        send(player);
    }

}
