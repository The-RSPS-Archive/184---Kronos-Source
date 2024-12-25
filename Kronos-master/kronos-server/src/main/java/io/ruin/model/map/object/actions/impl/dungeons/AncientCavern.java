package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.object.actions.ObjectAction;

public class AncientCavern {

    static {
        ObjectAction.register(25274, 1, (player, obj) -> {
            player.sendFilteredMessage("You dive into the swirling maelstrom of the whirlpool.");
            player.sendFilteredMessage("You are swirled beneath the water, the darkness and pressure are overwhelming.");
            player.sendFilteredMessage("Mythical forces guide you into a cavern below the whirlpool.");
            player.getMovement().teleport(1763, 5365, 1);
        });

        ObjectAction.register(25216, 1, (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.sendFilteredMessage("You jump on the log and dislodge it. You guide your makeshift vessel through the caves to an unknown destination.");
            player.getPacketSender().fadeOut();
            event.delay(3);
            player.sendFilteredMessage("You find yourself on the banks of the river, far below the lake.");
            player.getMovement().teleport(2531, 3445);
            player.getPacketSender().fadeIn();
            event.delay(1);
            player.unlock();
        }));

        ObjectAction.register(25336, 1, (player, obj) -> player.getMovement().teleport(1768, 5366, 1));
        ObjectAction.register(25338, 1, (player, obj) -> player.getMovement().teleport(1772, 5366, 0));
        ObjectAction.register(25339, 1, (player, obj) -> player.getMovement().teleport(1778, 5343, 1));
        ObjectAction.register(25340, 1, (player, obj) -> player.getMovement().teleport(1778, 5346, 0));
        ObjectAction.register(25337, 1, (player, obj) -> player.sendFilteredMessage("The path is far too slippery to climb. How inconvenient."));
    }
}
