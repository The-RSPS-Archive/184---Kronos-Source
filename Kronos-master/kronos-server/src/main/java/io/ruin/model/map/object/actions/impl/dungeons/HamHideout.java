package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.object.actions.ObjectAction;

public class HamHideout {

    static {
        /**
         * Trapdoor
         */
        ObjectAction.register(5492, 1, (player, obj) -> {
            if (Config.HAM_HIDEOUT_ENTRANCE.get(player) == 1) {
                player.sendFilteredMessage("You climb down through the trapdoor...");
                player.sendFilteredMessage("... and enter a dimly lit cavern area.");
                player.getMovement().teleport(3149, 9652, 0);
            } else {
                player.sendFilteredMessage("This trapdoor seems totally locked.");
            }
        });
        ObjectAction.register(5492, 5, (player, obj) -> player.startEvent(event -> {
            player.animate(537);
            player.sendFilteredMessage("You attempt to pick the lock on the trapdoor.");
            event.delay(5);
            player.animate(537);
            player.sendFilteredMessage("You attempt to pick the lock on the trapdoor.");
            event.delay(5);
            if (Random.rollPercent(70)) {
                player.sendFilteredMessage("You pick the lock on the trapdoor.");
                World.startEvent(e -> {
                    Config.HAM_HIDEOUT_ENTRANCE.set(player, 1);
                    e.delay(100);
                    Config.HAM_HIDEOUT_ENTRANCE.set(player, 0);
                });
            } else {
                player.sendFilteredMessage("You fail to pick the lock - your fingers get numb from fumbling with the lock.");
            }
        }));
        ObjectAction.register(5492, 2, (player, obj) -> {
            Config.HAM_HIDEOUT_ENTRANCE.set(player, 0);
            player.sendFilteredMessage("You close the trapdoor.");
        });
        /**
         * Ladder
         */
        ObjectAction.register(5493, 3149, 9653, 0, "climb-up", (player, obj) -> {
            player.getMovement().teleport(3166, 3251, 0);
            player.sendFilteredMessage("You leave the HAM Fanatics' Camp.");
        });
    }
}
