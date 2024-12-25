package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.object.actions.ObjectAction;

public class ShantayPass {

    static {
        ObjectAction.register(4031, 1, (player, obj) -> {
            player.step(0, player.getAbsY() > obj.y ? -2 : 2, StepType.FORCE_WALK);
        });
    }

}
