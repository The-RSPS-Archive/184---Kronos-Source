package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class TempleOfIkov {

    static {
        ObjectAction.register(98, 2650, 9804, 0, "climb-down", (player, obj) -> player.getMovement().teleport(2641, 9764, 0));
        ObjectAction.register(96, 2638, 9763, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2650, 9804, 0));
        ObjectAction.register(17385, 2677, 9805, 0, "climb-up", (player, obj) -> Ladder.climb(player, 2677, 3404, 0, true, true, false));
        ObjectAction.register(17384, 2677, 3405, 0, "climb-down", (player, obj) -> Ladder.climb(player, 2677, 9804, 0, false, true, false));
    }

}
