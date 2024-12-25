package io.ruin.model.entity.npc.actions.animals;

import io.ruin.api.utils.ArrayUtils;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.shared.listeners.SpawnListener;

public class Duck {

    static {
        SpawnListener.register(ArrayUtils.of("duck"), npc -> {
            npc.getDef().swimClipping = true;
            npc.addEvent(e -> {
                while(true) {
                    e.delay(Random.get(200, 400));
                    npc.forceText("Quack!");
                    npc.publicSound( 413, 7, 0);
                }
            });
        });
    }
}