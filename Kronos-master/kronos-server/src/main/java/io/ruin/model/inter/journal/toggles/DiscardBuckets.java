package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class DiscardBuckets extends JournalEntry {

    @Override
    public void send(Player player) {
        if(player.discardBuckets)
            send(player, "Discard Buckets", "Enabled", Color.GREEN);
        else
            send(player, "Discard Buckets", "Disabled", Color.RED);
    }

    @Override
    public void select(Player player) {
        player.discardBuckets = !player.discardBuckets;
        send(player);
    }

}
