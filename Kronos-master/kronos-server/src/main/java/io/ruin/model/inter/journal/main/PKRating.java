package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class PKRating extends JournalEntry {

    public static final PKRating INSTANCE = new PKRating();

    @Override
    public void send(Player player) {
        send(player, "PK Rating", player.pkRating, Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.forceText("!" + Color.ORANGE_RED.wrap("PK RATING:") + " " + player.pkRating);
    }

}