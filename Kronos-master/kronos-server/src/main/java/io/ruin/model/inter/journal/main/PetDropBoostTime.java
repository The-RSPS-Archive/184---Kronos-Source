package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class PetDropBoostTime extends JournalEntry {

    public static final PetDropBoostTime INSTANCE = new PetDropBoostTime();

    @Override
    public void send(Player player) {
        if(player.petDropBonus.isDelayed()) {
            int minutes = player.petDropBonus.remaining() / 100;
            if(minutes == 0)
                send(player, "Pet Drop Boost", "Expiring soon!", Color.ORANGE);
                else
            send(player, "Pet Drop Boost", minutes + (minutes == 1 ? " minute" : " minutes"), Color.GREEN);
        } else {
            send(player, "Pet Drop Boost", "Inactive", Color.RED);
        }
    }

    @Override
    public void select(Player player) {
        if(player.petDropBonus.isDelayed()) {
            player.sendMessage("You currently have a 25% increased chance of getting a pet!");
        } else {
            player.sendMessage("You do not have a pet drop boost activated.");
        }
    }

}