package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;

public class CollectionBox {

    public static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.COLLECTION_BOX);
        //todo
    }

}
