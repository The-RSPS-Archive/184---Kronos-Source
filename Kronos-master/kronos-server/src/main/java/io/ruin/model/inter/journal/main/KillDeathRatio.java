package io.ruin.model.inter.journal.main;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Config;

public class KillDeathRatio extends JournalEntry {

    @Override
    public void send(Player player) {
        int kills = Config.PVP_KILLS.get(player);
        int deaths = Config.PVP_DEATHS.get(player);
        send(player, "Kill/Death Ratio", toRatio(kills, deaths), kills < 1 ? Color.RED : Color.GREEN);
    }

    @Override
    public void select(Player player) {
        shout(player);
    }

    public static void shout(Player player) {
        int kills = Config.PVP_KILLS.get(player);
        int deaths = Config.PVP_DEATHS.get(player);
        player.forceText("!" + Color.ORANGE_RED.wrap("KILLS:") + " " + kills + "  " + Color.ORANGE_RED.wrap("DEATHS:") + " " + deaths + "  " + Color.ORANGE_RED.wrap("RATIO:") + " " + toRatio(kills, deaths));
    }

    private static String toRatio(int kills, int deaths) {
        return NumberUtils.formatTwoPlaces(kills / Math.max(1D, deaths));
    }

}