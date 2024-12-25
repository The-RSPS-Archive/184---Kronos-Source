package io.ruin.model.map.object.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class Flax {

    private static void pick(Player player, GameObject flax) {
        if(player.getInventory().isFull()) {
            player.sendMessage("You can't carry any more flax.");
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.animate(827);
            event.delay(1);
            player.getInventory().add(1779, 1);
            player.sendMessage("You pick some flax.");
            if(Random.rollDie(6, 1))
                removeFlax(flax);
            player.unlock();
        });
    }

    private static void removeFlax(GameObject flax) {
        World.startEvent(event -> {
            flax.remove();
            event.delay(20);
            flax.restore();
        });
    }

    static {
        ObjectAction.register(14896, "pick", Flax::pick);
    }

}