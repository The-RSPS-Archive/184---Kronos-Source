package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class HideIcon extends JournalEntry {

    @Override
    public void send(Player player) {
        if(player.hidePlayerIcon)
            send(player, "Hide Player Icon", "Enabled", Color.GREEN);
        else
            send(player, "Hide Player Icon", "Disabled", Color.RED);
    }

    @Override
    public void select(Player player) {
        if(player.isModerator() || player.isSupport() || player.isAdmin()) {
            player.sendFilteredMessage("You're unable to use this feature as a staff member.");
            player.hidePlayerIcon = false;
            send(player);
            return;
        }
        player.hidePlayerIcon = !player.hidePlayerIcon;
        send(player);
    }

}