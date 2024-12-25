package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.cache.Color;
import io.ruin.model.activities.pvminstances.InstanceDialogue;
import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.slayer.Slayer;

public class KrakenCove {

    static {
        SpawnListener.forEach(n -> {
            if (n.getCombat() != null && n.spawnPosition.getRegion().id == 9116) {
                n.attackNpcListener = (player, npc, message) -> {
                    if (!Slayer.isTask(player, npc)) {
                        if(n.getId() == 2917 || player.slayerTask != null && player.slayerTask.name.contains("Kraken") && npc.getId() == 5534 || npc.getId() == 5535)
                            return true;
                        if (message)
                            player.sendMessage("You cannot attack monsters in this cave unless they are assigned to you by a slayer master.");
                        return false;
                    }
                    return true;
                };
            }
        });
        /*
         * Entrance/exit
         */
        ObjectAction.register(30177, 2277, 3611, 0, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(2276, 9988, 0);
            player.unlock();
        }));
        ObjectAction.register(30178, 2276, 9987, 0, "leave", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(2278, 3610, 0);
            player.unlock();
        }));

        /*
         * Kraken boss
         */
        ObjectAction.register(537, 2280, 10017, 0, "enter", (player, obj) -> {
            if(player.krakenWarning) {
                player.dialogue(
                        new MessageDialogue(Color.DARK_RED.wrap("WARNING!") +
                                "<br> This is the lair of the Kraken boss. <br> Are you sure you want to enter?").lineHeight(24),
                        new OptionsDialogue("Enter the boss area?",
                                new Option("Yes.", () -> player.getMovement().teleport(2280, 10022)),
                                new Option("Yes, and don't warn me again.", () -> {
                                    player.getMovement().teleport(2280, 10022);
                                    player.krakenWarning = false;
                                }),
                                new Option("No.", player::closeDialogue)
                        )
                );
            } else {
                player.getMovement().teleport(2280, 10022);
            }
        });
        ObjectAction.register(537, 2280, 10017, 0, "private", (player, obj) -> InstanceDialogue.open(player, InstanceType.KRAKEN));
        ObjectAction.register(538,"use", (player, obj) -> player.getMovement().teleport(2280, 10016));
    }
}
