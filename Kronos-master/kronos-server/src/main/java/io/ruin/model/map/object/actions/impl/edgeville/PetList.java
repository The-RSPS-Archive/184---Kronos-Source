package io.ruin.model.map.object.actions.impl.edgeville;

import io.ruin.api.utils.Random;
import io.ruin.cache.EnumMap;
import io.ruin.cache.ItemDef;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.object.actions.ObjectAction;

public class PetList {

    static {
        EnumMap map = EnumMap.get(985);
        StringBuilder sb = new StringBuilder();
        for(int key : map.keys) {
            int itemId = map.intValues[key];
            ItemDef def = ItemDef.get(itemId);
            if(def.pet == null) {
                System.err.println(def.name + " (" + itemId + ") pet not supported!");
                continue;
            }
            sb.append(def.name).append("|");
        }
        String s = sb.toString();
        ObjectAction.register(29226, "read", (player, obj) -> {
            player.openInterface(InterfaceType.MAIN, 210);
            player.getPacketSender().sendClientScript(647, "s", s);
            if(Random.rollDie(5, 1))
                player.sendFilteredMessage("<col=8f4808>Looking for a quick way to get a pet? You can purchase a pet box at our ::store!");
        });
    }

}
