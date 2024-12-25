package io.ruin.model.map.object.actions.impl;

import io.ruin.cache.ObjectDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class Drawer {

    private static void open(Player player, GameObject drawer) {
        player.startEvent(event -> {
            player.lock();
            player.animate(832);
            drawer.setId(drawer.id + 1);
            player.unlock();
        });
    }

    private static void shut(Player player, GameObject drawer) {
        player.startEvent(event -> {
            player.lock();
            player.animate(832);
            drawer.setId(drawer.originalId);
            player.unlock();
        });
    }

    static {
        ObjectDef.forEach(objDef -> {
            if(objDef.name.equalsIgnoreCase("drawers")) {
                ObjectAction.register(objDef.id, "open", Drawer::open);
                ObjectAction.register(objDef.id, "close", Drawer::shut);
                ObjectAction.register(objDef.id, "shut", Drawer::shut);
            }
        });

    }

}
