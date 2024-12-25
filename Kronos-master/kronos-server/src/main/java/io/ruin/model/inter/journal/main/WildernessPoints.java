package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class WildernessPoints extends JournalEntry {

    public static final WildernessPoints INSTANCE = new WildernessPoints();

    public static double wildernessLevelModifier(Player player) {
        if(player.wildernessLevel > 50)
            return 3;
        else if(player.wildernessLevel >= 40)
            return 2.8;
        else if(player.wildernessLevel >= 30)
            return 2.2;
        else if(player.wildernessLevel >= 20)
            return 1.8;
        else if(player.wildernessLevel >= 10)
            return 1.4;
        else if(player.wildernessLevel >= 5)
            return 1.2;
        return 1;
    }

    @Override
    public void send(Player player) {
        send(player, "Wilderness Points", player.wildernessPoints, Color.GREEN);
    }

    @Override
    public void select(Player player) {
        player.forceText("!" + Color.ORANGE_RED.wrap("WILDERNESS POINTS:") + " " + player.wildernessPoints);
    }

}