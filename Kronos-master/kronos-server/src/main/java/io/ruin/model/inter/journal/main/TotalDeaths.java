package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Config;

public class TotalDeaths extends JournalEntry {

    @Override
    public void send(Player player) {
        send(player, "Total Deaths", Config.PVP_DEATHS.get(player), Color.GREEN);
    }

    @Override
    public void select(Player player) {
        KillDeathRatio.shout(player);
    }

}