package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.shared.listeners.SpawnListener;

public class RangedShop {

    private final static int SHOPKEEPER = 5051;

    static {
        SpawnListener.register(SHOPKEEPER, npc -> npc.skipReachCheck = p -> p.equals(3078, 3509));
    }
}
