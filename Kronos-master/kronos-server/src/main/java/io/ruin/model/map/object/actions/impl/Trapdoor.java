package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class Trapdoor {

    private static void open(Player player, GameObject trapdoor) {
        World.startEvent(event -> {
            trapdoor.setId(1581);
            event.delay(300);
            trapdoor.setId(trapdoor.originalId);
        });

        player.sendMessage("The trapdoor opens..");
    }

    private static void climbDown(Player player) {
        if (player.isAt(3096, 3468)) {
            player.sendMessage("You climb down through the trapdoor..");
            Ladder.climb(player, 3096, 9867, 0, false, true, false);
        } else {
            player.sendMessage("I'm not sure where this would take me..");
        }
    }

    private static void close(Player player, GameObject trapdoor) {
        trapdoor.setId(trapdoor.originalId);
        player.sendMessage("You close the trapdoor.");
    }

    public static void register() {

        /*
         * Edgeville dungeon
         */
        ObjectAction.register(1579, "open", Trapdoor::open);
        ObjectAction.register(1581, "climb-down", (player, obj) -> climbDown(player));
        ObjectAction.register(1581, "close", Trapdoor::close);

        /*
         * Rogues' Den
         */
        ObjectAction.register(7257, "enter", (player, obj) -> player.getMovement().teleport(3061, 4985, 1));
        ObjectAction.register(7258, "enter", (player, obj) -> player.getMovement().teleport(2906, 3537, 0));
    }

    static {
        register();
    }
}
