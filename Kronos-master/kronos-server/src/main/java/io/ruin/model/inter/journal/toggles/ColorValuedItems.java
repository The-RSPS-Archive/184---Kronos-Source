package io.ruin.model.inter.journal.toggles;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class ColorValuedItems extends JournalEntry {

    public static void update(Player player) {
        player.getPacketSender().sendVarp(20006, player.colorValuedGroundItems ? 1 : 0);
    }

    @Override
    public void send(Player player) {
        if(!player.colorValuedGroundItems)
            send(player, "Color Valued Items", "Disabled", Color.RED);
        else
            send(player, "Color Valued Items", "Enabled", Color.GREEN);
        update(player);
    }

    @Override
    public void select(Player player) {
        player.colorValuedGroundItems = !player.colorValuedGroundItems;
        if(player.colorValuedGroundItems)
            player.sendMessage(Color.DARK_RED.wrap("Right clicking ground items will now display colored based on value."));
         else
            player.sendMessage(Color.DARK_RED.wrap("Right clicking ground items will now display the default color."));
        send(player);
    }

}