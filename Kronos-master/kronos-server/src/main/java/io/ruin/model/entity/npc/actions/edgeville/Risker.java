package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.shared.listeners.SpawnListener;

public class Risker {

    private static final int RISKER = 5523;
    static {
        SpawnListener.register(RISKER, npc -> npc.startEvent(event -> {
            while (true) {
                npc.forceText("Ready to take a risk?");
                event.delay(500);
            }
        }));
    }

}
