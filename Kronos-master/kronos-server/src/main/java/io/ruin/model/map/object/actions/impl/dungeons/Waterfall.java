package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.object.actions.ObjectAction;

public class Waterfall {

    static {
        /**
         * Open
         */
        ObjectAction.register(2002, 2568, 9893, 0, "open", (player, obj) -> player.sendFilteredMessage("This door is locked."));
        ObjectAction.register(2000, 2575, 9861, 0, "open", (player, obj) -> player.getMovement().teleport(2511, 3463, 0));
        ObjectAction.register(2010, 2511, 3464, 0, "open", (player, obj) -> player.getMovement().teleport(2575, 9861, 0));
    }
}
