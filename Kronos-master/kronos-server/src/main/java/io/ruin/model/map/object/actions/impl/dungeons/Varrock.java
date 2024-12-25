package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class Varrock {

    static {
        /**
         * Ladders
         */
        ObjectAction.register(11806, 3237, 9858, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3236, 3458, 0, true, true, false));
        ObjectAction.register(17385, 3230, 9904, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3230, 3505, 0, true, true, false));

        /**
         * Hole
         */
        ObjectAction.register(7142, 3230, 3504, 0, "climb-down", (player, obj) -> Ladder.climb(player, 3229, 9904, 0, false, true, false));

        /**
         * Manhole
         */
        ObjectAction.register(881, "open", (player, obj) -> {
            obj.setId(882);
            player.sendFilteredMessage("You pull back the cover from over the manhole.");
        });
        ObjectAction.register(882, "close", (player, obj) -> {
            obj.setId(obj.originalId);
            player.sendFilteredMessage("You place the cover back over the manhole.");
        });
        ObjectAction.register(882, "climb-down", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.getMovement().teleport(3237, 9858, 0);
            player.sendFilteredMessage("You climb down through the manhole.");
            player.unlock();
        }));
    }
}
