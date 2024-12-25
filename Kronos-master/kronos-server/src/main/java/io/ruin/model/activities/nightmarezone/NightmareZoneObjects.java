package io.ruin.model.activities.nightmarezone;

import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.object.actions.ObjectAction;

public class NightmareZoneObjects {

    static {
        /**
         * Rewards chest
         */
        ObjectAction.register(26273, "search", (player, obj) -> {
            player.openInterface(InterfaceType.MAIN, Interface.NIGHTMARE_ZONE_REWARDS);
        });
    }
}
