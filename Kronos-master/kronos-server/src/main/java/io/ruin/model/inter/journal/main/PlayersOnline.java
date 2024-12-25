package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

import java.util.ArrayList;
import java.util.List;

public class PlayersOnline extends JournalEntry {

    public static final PlayersOnline INSTANCE = new PlayersOnline();

    @Override
    public void send(Player player) {
        send(player, "Players Online", World.players.count(), Color.GREEN);
    }

    @Override
    public void select(Player player) {
        List<String> playersOnline = new ArrayList<>();
        if (player.isAdmin()) {
            World.getPlayerStream().forEach(p -> {
                if (p.getUserId() != -1) {
                    playersOnline.add(p.getName() + " - Position: " + p.getPosition());
                }
            });
            String[] players = playersOnline.toArray(new String[0]);
            player.sendScroll("<col=800000>" + World.type.getWorldName() + "Players [" + realPlayers() + "]", players);
        }
        player.sendMessage("There are currently " + World.players.count() + " players online.");
    }

    private static int realPlayers() {
        int players = 0;
        for(Player p : World.players) {
            if (p.getChannel().id() != null)
                players++;
        }
        return players;
    }


}