package io.ruin.model.entity.npc.actions.animals;

import io.ruin.api.utils.ArrayUtils;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.shared.listeners.SpawnListener;

public class Cow {

    static {
        SpawnListener.register(ArrayUtils.of("cow"), npc -> {
            npc.addEvent(e -> {
                while(true) {
                    e.delay(Random.get(30, 100));
                    npc.forceText("Moo");
                    npc.publicSound( 3044, 7, 0);
                }
            });
        });
    }
}