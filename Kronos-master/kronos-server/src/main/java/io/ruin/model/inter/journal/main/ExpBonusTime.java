package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class ExpBonusTime extends JournalEntry {

    public static final ExpBonusTime INSTANCE = new ExpBonusTime();

    @Override
    public void send(Player player) {
        if(player.expBonus.isDelayed()) {
            int minutes = player.expBonus.remaining() / 100;
            if(minutes == 0)
                send(player, "XP Boost", "Expiring soon!", Color.ORANGE);
                else
            send(player, "XP Boost", minutes + (minutes == 1 ? " minute" : " minutes"), Color.GREEN);
        } else {
            send(player, "XP Boost", "Inactive", Color.RED);
        }
    }

    @Override
    public void select(Player player) {
        if(player.expBonus.isDelayed()) {
            player.sendMessage("You have a 100% experience boost activated.");
        } else {
            player.sendMessage("You do not have an experience boost activated.");
        }
    }

}