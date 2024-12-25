package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class ExpCounter extends JournalEntry {

    @Override
    public void send(Player player) {
        send(player, player.showHitAsExperience);
    }

    private void send(Player player, boolean enabled) {
        if(enabled)
            send(player, "Exp Counter", "Damage", Color.GREEN);
        else
            send(player, "Exp Counter", "Exp", Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.showHitAsExperience = !player.showHitAsExperience;
        send(player, player.showHitAsExperience);
    }

}