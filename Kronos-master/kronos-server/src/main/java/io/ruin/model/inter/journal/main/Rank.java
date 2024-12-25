package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class Rank extends JournalEntry {

    @Override
    public void send(Player player) {
        send(player, "Rank", "<img=1>Administrator", Color.YELLOW);
    }

    @Override
    public void select(Player player) {
    }

}