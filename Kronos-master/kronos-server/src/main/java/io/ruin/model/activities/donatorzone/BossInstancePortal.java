package io.ruin.model.activities.donatorzone;

import io.ruin.model.activities.pvminstances.InstanceDialogue;
import io.ruin.model.map.object.actions.ObjectAction;

public class BossInstancePortal {

    static {
        ObjectAction.register(4407, "use", (player, obj) -> InstanceDialogue.open(player));
    }

}
