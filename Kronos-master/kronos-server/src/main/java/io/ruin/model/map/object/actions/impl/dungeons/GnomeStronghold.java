package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.object.actions.ObjectAction;

public class GnomeStronghold {

    static {
        /**
         * Entrance/exit
         */
        ObjectAction.register(26709, 2428, 3423, 0, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(2429, 9824, 0);
            player.unlock();
        }));
        ObjectAction.register(27257, 2430, 9824, 0, "use", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(2430, 3424, 0);
            player.unlock();
        }));

    }
}
