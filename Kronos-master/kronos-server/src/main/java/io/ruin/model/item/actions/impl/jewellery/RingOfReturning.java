package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.map.Bounds;

public enum RingOfReturning {

    FIVE(21129, 5, 21132),
    FOUR(21132, 4, 21134),
    THREE(21134, 3, 21136),
    TWO(21136, 2, 21138),
    ONE(21138, 1, -1);

    private final int id, charges, replacementId;

    RingOfReturning(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }
    static {
        JeweleryTeleports teleports = new JeweleryTeleports("ring", false,
                new JeweleryTeleports.Teleport("todo1", new Bounds(1111, 2222, 1111, 2222, 0)),
                new JeweleryTeleports.Teleport("todo2", new Bounds(1111, 2222, 1111, 2222, 0)),
                new JeweleryTeleports.Teleport("todo3", new Bounds(1111, 2222, 1111, 2222, 0)),
                new JeweleryTeleports.Teleport("todo4", new Bounds(1111, 2222, 1111, 2222, 0)),
                new JeweleryTeleports.Teleport("todo5", new Bounds(1111, 2222, 1111, 2222, 0))
        );
        for(RingOfReturning ring : values())
            teleports.register(ring.id, ring.charges, ring.replacementId);
    }
}

