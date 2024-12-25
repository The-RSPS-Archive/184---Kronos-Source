package io.ruin.model.activities.wilderness;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class PirateShip {
    static {
        ObjectAction.register(272, 3018, 3957, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3018, 3958, 1, true, true, false));
        ObjectAction.register(273, 3018, 3957, 1, "climb-down", (player, obj) -> Ladder.climb(player, 3018, 3958, 0, false, true, false));
        ObjectAction.register(245, 3019, 3959, 1, "climb-up", (player, obj) -> player.getMovement().teleport(3019, 3960, 2));
        ObjectAction.register(245, 3017, 3959, 1, "climb-up", (player, obj) -> player.getMovement().teleport(3017, 3960, 2));
        ObjectAction.register(246, 3017, 3959, 2, "climb-down", (player, obj) -> player.getMovement().teleport(3017, 3958, 1));
        ObjectAction.register(246, 3019, 3959, 2, "climb-down", (player, obj) -> player.getMovement().teleport(3019, 3958, 1));
    }
}
