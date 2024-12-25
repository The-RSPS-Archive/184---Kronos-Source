package io.ruin.model.activities.wilderness;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class KingBlackDragon {

    static {
        ObjectAction.register(18987, 3017, 3849, 0, "climb-down", (player, obj) -> Ladder.climb(player, 3069, 10255, player.getHeight(), false, false, false));
        ObjectAction.register(18988, 3069, 10256, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3016, 3849, player.getHeight(), true, false, false));
    }

}
