package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Config;

public class KDOverlay extends JournalEntry {

    @Override
    public void send(Player player) {
        send(player, Config.PVP_KD_OVERLAY.get(player) == 1);
    }

    private void send(Player player, boolean enabled) {
        if(enabled)
            send(player, ("Wilderness") + " K/D Overlay", "On", Color.GREEN);
        else
            send(player, ("Wilderness") + " K/D Overlay", "Off", Color.RED);
    }

    @Override
    public void select(Player player) {
        send(player, Config.PVP_KD_OVERLAY.toggle(player) == 1);
    }

}