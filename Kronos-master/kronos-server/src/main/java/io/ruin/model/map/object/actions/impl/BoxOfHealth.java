package io.ruin.model.map.object.actions.impl;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 2/10/2020
 */


import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.actions.ObjectAction;

public class BoxOfHealth {

    static {
        ObjectAction.register(23709, "Open", ((player, obj) -> heal(player)));
    }

    private static void heal(Player player) {
        if (player.storeAmountSpent > 249 || player.isAdmin()) {
            player.lastBoxHeal = System.currentTimeMillis();
            player.animate(833);
            player.graphics(1039);
            player.getCombat().restore();
            player.sendMessage("You have been fully restored!");
        } else {
            player.sendMessage("You must be atleast a Diamond Donator to use this box!");
        }
    }

}
