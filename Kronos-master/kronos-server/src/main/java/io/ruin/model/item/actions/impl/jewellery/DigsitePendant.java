package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.map.Bounds;

public enum DigsitePendant {

    FIVE(11194, 5, 11193),
    FOUR(11193, 4, 11192),
    THREE(11192, 3, 11191),
    TWO(11191, 2, 11190),
    ONE(11190, 1, -1);

    private final int id, charges, replacementId;

    DigsitePendant(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("pendant", false,
                new JeweleryTeleports.Teleport("Digsite", new Bounds(3337, 3444, 3339, 3446, 0)),
                new JeweleryTeleports.Teleport("House on the Hill", new Bounds(3763, 3868, 3765, 3870, 1)),
                new JeweleryTeleports.Teleport("Lithkren", new Bounds(1566, 5073, 1569, 5076, 0))
        );
        for(DigsitePendant pendant : values())
            teleports.register(pendant.id, pendant.charges, pendant.replacementId);
    }

}

