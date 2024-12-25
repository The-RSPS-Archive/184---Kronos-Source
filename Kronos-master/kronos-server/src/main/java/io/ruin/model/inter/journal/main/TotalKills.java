package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Config;

public class TotalKills extends JournalEntry {

    @Override
    public void send(Player player) {
        send(player, "Total Kills", Config.PVP_KILLS.get(player), Color.GREEN);
    }

    @Override
    public void select(Player player) {
        KillDeathRatio.shout(player);
    }

}