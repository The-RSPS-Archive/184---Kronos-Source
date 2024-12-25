package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.activities.pvminstances.InstanceDialogue;
import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;

public class Cerberus {

    private static int eastCount = 0;
    private static int westCount = 0;
    private static int northCount = 0;

    private static void turnWinch(Player player, int x, int y) {
        player.startEvent(event -> {
            player.lock();
            player.animate(4506);
            player.getPacketSender().fadeOut();
            event.delay(2);
            player.getMovement().teleport(x, y);
            event.delay(2);
            player.getPacketSender().fadeIn();
            event.delay(1);
            player.unlock();
        });
    }

    private static void peekWinch(Player player, int count) {
        if (count == 0)
            player.dialogue(new NPCDialogue(5870, "No adventurers are inside the cave.").animate(588));
        else
            player.dialogue(new NPCDialogue(5870, count + " adventurer is inside the cave.").animate(588));
    }

    private static void exitPortcullis(Player player, int x, int y) {
        player.dialogue(
                new OptionsDialogue("Do you wish to leave?",
                        new Option("Yes, I'm scared.", () -> player.startEvent(event -> {
                            player.lock();
                            player.getPacketSender().fadeOut();
                            event.delay(2);
                            player.getMovement().teleport(x, y);
                            event.delay(2);
                            player.getPacketSender().fadeIn();
                            event.delay(1);
                            player.unlock();
                        })),
                        new Option("Nah, I'll stay.", player::closeDialogue)
                )
        );
    }

    static {
        /**
         * Portcullis
         */
        ObjectAction.register(21772, 1303, 1289, 0, "exit", (player, obj) -> exitPortcullis(player, 1309, 1269));
        ObjectAction.register(21772, 1367, 1225, 0, "exit", (player, obj) -> exitPortcullis(player, 1328, 1253));
        ObjectAction.register(21772, 1239, 1225, 0, "exit", (player, obj) -> exitPortcullis(player, 1291, 1253));

        /**
         * Iron winch
         */
        ObjectAction.register(23104, 1291, 1254, 0, "turn", (player, obj) -> turnWinch(player, 1240, 1226));
        ObjectAction.register(23104, 1328, 1254, 0, "turn", (player, obj) -> turnWinch(player, 1368, 1226));
        ObjectAction.register(23104, 1307, 1269, 0, "turn", (player, obj) -> turnWinch(player, 1304, 1290));

        ObjectAction.register(23104, 1291, 1254, 0, "peek", (player, obj) -> peekWinch(player, westCount));
        ObjectAction.register(23104, 1328, 1254, 0, "peek", (player, obj) -> peekWinch(player, eastCount));
        ObjectAction.register(23104, 1307, 1269, 0, "peek", (player, obj) -> peekWinch(player, northCount));

        ObjectAction.register(23104, "instance", (player, obj) -> InstanceDialogue.open(player, InstanceType.CERBERUS));

        MapListener.registerRegion(4883).onEnter(player -> westCount++).onExit((player, logout) -> westCount--);
        MapListener.registerRegion(5395).onEnter(player -> eastCount++).onExit((player, logout) -> eastCount--);
        MapListener.registerRegion(5140).onEnter(player -> northCount++).onExit((player, logout) -> northCount--);

        for (int y = 1290; y <= 1300; y++) {
            Tile.get(1303, y, 0, false).flagUnmovable();
            Tile.get(1305, y, 0, false).flagUnmovable();
        }
        for (int y = 1226; y <= 1236; y++) {
            Tile.get(1239, y, 0, false).flagUnmovable();
            Tile.get(1241, y, 0, false).flagUnmovable();
        }
        for (int y = 1226; y <= 1236; y++) {
            Tile.get(1367, y, 0, false).flagUnmovable();
            Tile.get(1369, y, 0, false).flagUnmovable();
        }

        for(int i = 1306; i < 1316; i ++) {
            Tile.get(1294, i, 0, false).flagUnmovable();
            Tile.get(1314, i, 0, false).flagUnmovable();
        }

        for(int i = 1242; i < 1252; i ++) {
            Tile.get(1358, i, 0, false).flagUnmovable();
            Tile.get(1378, i, 0, false).flagUnmovable();
            Tile.get(1230, i, 0, false).flagUnmovable();
            Tile.get(1250, i, 0, false).flagUnmovable();
        }
    }
}
