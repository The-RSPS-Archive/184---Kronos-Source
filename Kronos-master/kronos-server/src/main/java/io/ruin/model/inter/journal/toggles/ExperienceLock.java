package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

/**
 * @author ReverendDread on 7/21/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class ExperienceLock extends JournalEntry {

    @Override
    public void send(Player player) {
        String status = player.experienceLock ? "Enabled" : "Disabled";
        send(player, "Experience Lock", status, player.experienceLock ? Color.GREEN : Color.RED);
    }

    @Override
    public void select(Player player) {
        player.experienceLock = !player.experienceLock;
        player.sendMessage("Your experience has been " + (player.experienceLock ? "locked" : "unlocked") + ".");
        send(player);
    }

}
