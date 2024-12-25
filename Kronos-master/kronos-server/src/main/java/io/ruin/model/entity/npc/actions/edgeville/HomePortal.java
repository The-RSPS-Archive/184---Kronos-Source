package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.cache.ObjectID;
import io.ruin.data.impl.teleports;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.item.actions.impl.ItemSet;
import io.ruin.model.map.object.actions.ObjectAction;

public class HomePortal {

    static {

        for(int trader : new int[]{2149, 2148}) {
            SpawnListener.register(trader, npc -> npc.startEvent(event -> {
                while (true) {
                    npc.forceText("Come check out the trading post!");
                    event.delay(150);
                }
            }));
            NPCAction.register(trader, "sets", (player, npc) -> ItemSet.open(player));
            NPCAction.register(trader, "exchange", ((player, npc) -> {
                if (player.getGameMode().isIronMan()) {
                    player.sendMessage("Your gamemode prevents you from accessing the trading post!");
                    return;
                }
                player.getTradePost().openViewOffers();
            }));
        }

        SpawnListener.register(4159, npc -> npc.startEvent(event -> {
            while (true) {
                npc.forceText("Use the portal to teleport around the world!");
                event.delay(100);
            }
        }));

//        ObjectAction.register(ObjectID.KRONOS_TELEPORTER, "teleport", (player, npc) -> teleports.open(player));
//        ObjectAction.register(ObjectID.KRONOS_TELEPORTER, "previous teleport", (player, npc) -> teleports.previous(player));
    }
}
