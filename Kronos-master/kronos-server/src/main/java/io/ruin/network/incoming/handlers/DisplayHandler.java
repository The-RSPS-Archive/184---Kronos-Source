package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.EnumMap;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.utils.Config;
import io.ruin.network.PacketSender;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

import java.util.Map;

import static io.ruin.model.inter.Interface.COMBAT_OPTIONS;

@SuppressWarnings("Duplicates")
@IdHolder(ids = {72})
public class DisplayHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        int displayMode = in.readByte();
        int canvasWidth = in.readShort();
        int canvasHeight = in.readShort();
        if(!player.hasDisplay()) {
            player.setDisplayMode(displayMode);
            sendDisplay(player);
            player.start();
            player.setOnline(true);
        } else {
            boolean wasFixed = player.isFixedScreen();
            player.setDisplayMode(displayMode);
            if(wasFixed == player.isFixedScreen())
                return;
            updateDisplay(player);
        }
    }

    private static void sendDisplay(Player player) {
        PacketSender ps = player.getPacketSender();
        ps.sendGameFrame(165);
        ps.sendInterface(162, 165, 1, 1);
        ps.sendInterface(163, 165, 23, 1);
        if(Config.DATA_ORBS.get(player) == 0)
            ps.sendInterface(160, 165, 24, 1);

        if(Config.XP_COUNTER_SHOWN.get(player) == 1)
            ps.sendInterface(122, 165, 4, 1);

        //ps.sendInterface(378, 165, 28, 0); //welcome screen pt1
        //ps.sendInterface(50, 165, 27, 0); //welcome screen pt2

        ps.sendInterface(320, 165, 9, 1);
        ps.sendInterface(Interface.NOTICEBOARD, 165, 10, 1);
        ps.sendInterface(399, 629, 2, 1);
        ps.sendInterface(149, 165, 11, 1);
        ps.sendInterface(387, 165, 12, 1);
        ps.sendInterface(541, 165, 13, 1);
        ps.sendInterface(218, 165, 14, 1);
        ps.sendInterface(Config.FRIENDS_AND_IGNORE_TOGGLE.get(player) == 0 ? Interface.FRIENDS_LIST : Interface.IGNORE_LIST, 165, 17, 1);
        //ps.sendInterface(432, 165, 16, 1);
        ps.sendInterface(Interface.NOTICEBOARD, 165, 16, 1);
        ps.sendInterface(182, 165, 18, 1);
        ps.sendInterface(261, 165, 19, 1);
        ps.sendInterface(216, 165, 20, 1);
        ps.sendInterface(239, 165, 21, 1);
        ps.sendInterface(7, 165, 15, 1);
        ps.sendInterface(COMBAT_OPTIONS, 165, 8, 1);

        /**
         * Unlocks
         */
        ps.sendAccessMask(399, 7, 0, 19, 2);
        ps.sendAccessMask(399, 8, 0, 118, 2);
        ps.sendAccessMask(399, 9, 0, 11, 2);
        ps.sendAccessMask(261, 106, 1, 4, 2);
        ps.sendAccessMask(261, 107, 1, 4, 2);
        ps.sendAccessMask(216, 1, 0, 47, 2);
        ps.sendAccessMask(239, 2, 0, 580, 6);
        ps.sendAccessMask(218, 184, 0, 4, 2);

        Map<Integer, Integer> oldComponents = getToplevelComponents(player).ints();

        if(player.isFixedScreen()) {
            ps.sendGameFrame(548);
        } else if(Config.SIDE_PANELS.get(player) == 1) {
            ps.sendGameFrame(164);
        } else {
            ps.sendGameFrame(161);
        }

        Map<Integer, Integer> newComponents = getToplevelComponents(player).ints();
        moveSubInterfaces(oldComponents, newComponents, player);
    }

    private static void updateDisplay(Player player) {
        Map<Integer, Integer> oldComponents = getToplevelComponents(player).ints();

        PacketSender ps = player.getPacketSender();
        if(player.isFixedScreen()) {
            ps.sendGameFrame(548);
        } else if(Config.SIDE_PANELS.get(player) == 1) {
            ps.sendGameFrame(164);
        } else {
            ps.sendGameFrame(161);
        }

        Map<Integer, Integer> newComponents = getToplevelComponents(player).ints();
        moveSubInterfaces(oldComponents, newComponents, player);
    }

    public static void updateResizedTabs(Player player) {
        Map<Integer, Integer> oldComponents = getToplevelComponents(player).ints();
        PacketSender ps = player.getPacketSender();
        if(player.getGameFrameId() == 161) {
            ps.sendGameFrame(164);
        } else {
            ps.sendGameFrame(161);
        }

        Map<Integer, Integer> newComponents = getToplevelComponents(player).ints();
        moveSubInterfaces(oldComponents, newComponents, player);
    }

    private static void moveSubInterfaces(Map<Integer, Integer> oldComponents, Map<Integer, Integer> newComponents, Player player) {
        PacketSender ps = player.getPacketSender();

        for (Map.Entry<Integer, Integer> newComponent : newComponents.entrySet()) {
            int key = newComponent.getKey();
            int to = newComponent.getValue();

            if (to == -1) {
                continue;
            }

            if (oldComponents.containsKey(key)) {
                int from = oldComponents.get(key);

                if (from == -1) {
                    continue;
                }

                ps.moveInterface(from >> 16, from & 0xffff, to >> 16, to & 0xffff);
            }
        }
    }

    private static EnumMap getToplevelComponents(Player player) {
        switch (player.getGameFrameId()) {
            case Interface.FIXED_SCREEN:
                return EnumMap.get(1129);

            case Interface.RESIZED_SCREEN:
                return EnumMap.get(1130);

            case Interface.RESIZED_STACKED_SCREEN:
                return EnumMap.get(1131);

            case Interface.DEFAULT_SCREEN:
                return EnumMap.get(1132);
        }

        return null;
    }

}
