package io.ruin.model.map.object.actions.impl.zeah;


import io.ruin.model.map.Direction;
import io.ruin.model.map.object.actions.ObjectAction;

public class HandHolds {

    static {
        ObjectAction.register(27362, "climb", (player, obj) -> {
            if (obj.x == 1459 && obj.y == 3690) {
                player.startEvent(event -> {
                    player.lock();
                    player.face(1462, 3690);
                    event.delay(1);
                    player.animate(1148, 20);
                    player.getMovement().force(-6, 0, 0, 0, 25, 126, Direction.EAST);
                    event.delay(4);
                    player.unlock();
                });
            } else {
                player.startEvent(event -> {
                    player.lock();
                    player.face(1462, 3690);
                    event.delay(1);
                    player.animate(1148, 15);
                    player.getMovement().force(6, 0, 0, 0, 25, 135, Direction.EAST);
                    event.delay(4);
                    player.unlock();
                });
            }
        });
    }
}
