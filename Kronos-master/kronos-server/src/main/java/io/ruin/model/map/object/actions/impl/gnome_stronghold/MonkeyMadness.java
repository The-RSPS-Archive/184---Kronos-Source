package io.ruin.model.map.object.actions.impl.gnome_stronghold;

import io.ruin.model.map.object.actions.ObjectAction;

public class MonkeyMadness {

    static {
        /**
         * Cave
         */
        ObjectAction.register(28686, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.getPacketSender().fadeOut();
            event.delay(2);
            player.sendFilteredMessage("You enter the cavern beneath the crash site.");
            player.sendFilteredMessage("Why would you want to go in there?");
            player.getMovement().teleport(2129, 5646);
            player.getPacketSender().fadeIn();
            event.delay(1);
            player.unlock();
        }));
        ObjectAction.register(28687, "climb-up", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(828);
            player.getPacketSender().fadeOut();
            event.delay(2);
            player.getMovement().teleport(2026, 5611);
            player.getPacketSender().fadeIn();
            event.delay(1);
            player.unlock();
        }));

        /**
         * Entrance
         */
        ObjectAction.register(28656, 1, (player, obj) -> {
            if (player.getAbsY() >= 5568) {
                player.startEvent(event -> {
                    player.lock();
                    player.getPacketSender().fadeOut();
                    event.delay(2);
                    player.getMovement().teleport(2435, 3519);
                    player.getPacketSender().fadeIn();
                    event.delay(1);
                    player.unlock();
                });
            } else {
                player.startEvent(event -> {
                    player.lock();
                    player.getPacketSender().fadeOut();
                    event.delay(2);
                    player.getMovement().teleport(1987, 5568);
                    player.getPacketSender().fadeIn();
                    event.delay(1);
                    player.unlock();
                });
            }
        });
    }
}
