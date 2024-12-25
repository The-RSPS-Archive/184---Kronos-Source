package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class SlayerInfo extends JournalEntry {

    @Override
    public void send(Player player) {
        send(player, "Slayer Info", "[ ? ]", Color.TOMATO);
    }

    @Override
    public void select(Player player) {
        //todo - send task name
        //todo - send task remaining
        //todo - send slayer points
    }

}