package io.ruin.model.activities.godwars;

import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.MapListener;

public class Godwars {

    private static void enteredMain(Player player) {
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, Interface.GODWARS);
    }

    private static void exitedMain(Player player, boolean logout) {
        if(!logout) {
            player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
            Config.GWD_ARMADYL_KC.set(player, 0);
            Config.GWD_BANDOS_KC.set(player, 0);
            Config.GWD_SARADOMIN_KC.set(player, 0);
            Config.GWD_ZAMORAK_KC.set(player, 0);
        }
    }

    static {
        MapListener
                .register(p -> {
                    if (p.lastRegion != null
                            && (p.lastRegion.id == 11602
                            || p.lastRegion.id == 11346
                            || p.lastRegion.id == 11347
                            || p.lastRegion.id == 11603)) // regular areas
                        return true;
                    else if (p.currentInstance != null
                            && (p.currentInstance.getType() == InstanceType.SARADOMIN_GWD
                            || p.currentInstance.getType() == InstanceType.ARMADYL_GWD
                            || p.currentInstance.getType() == InstanceType.BANDOS_GWD
                            || p.currentInstance.getType() == InstanceType.ZAMORAK_GWD)) { // instanced rooms
                        return true;
                    }
                    return false;
                })
//                .registerRegions(11602, 11603, 11602, 11346, 11347)
                .onEnter(Godwars::enteredMain)
                .onExit(Godwars::exitedMain);
    }


}
