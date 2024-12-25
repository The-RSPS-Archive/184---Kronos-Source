package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class HighestDeathStreak extends JournalEntry {

    @Override
    public void send(Player player) {
        send(player, "Highest Death Streak", "15", Color.GREEN);
    }

    @Override
    public void select(Player player) {
    }

}