package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class BountyOverlay extends JournalEntry {

    @Override
    public void send(Player player) {
        if(!player.bountyHunterOverlay)
            send(player, "Bounty Hunter Overlay", "Disabled", Color.RED);
        else
            send(player, "Bounty Hunter Overlay", "Enabled", Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.bountyHunterOverlay = !player.bountyHunterOverlay;
        if(player.bountyHunterOverlay)
            player.sendMessage(Color.DARK_GREEN.wrap("You will no longer see bounty hunter overlay in wilderness."));
        else
            player.sendMessage(Color.DARK_GREEN.wrap("You will no longer see bounty hunter overlay in wilderness."));
        send(player);
    }

}
