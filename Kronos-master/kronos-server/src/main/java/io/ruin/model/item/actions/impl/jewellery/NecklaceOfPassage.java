package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.item.actions.impl.jewellery.JeweleryTeleports.Teleport;
import io.ruin.model.map.Bounds;

public enum NecklaceOfPassage {

    FIVE(21146, 5, 21149),
    FOUR(21149, 4, 21151),
    THREE(21151, 3, 21153),
    TWO(21153, 2, 21155),
    ONE(21155, 1, -1);

    private final int id, charges, replacementId;

    NecklaceOfPassage(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("necklace", false,
                new Teleport("Wizard's Tower", new Bounds(3115, 3171, 3112, 3168, 0)),
                new Teleport("The Outpost", new Bounds(2430, 3350, 2433, 3353, 0)),
                new Teleport("Eagle's Eyrie", new Bounds(3404, 3156, 3406, 3158, 0))
        );
        for(NecklaceOfPassage necklace : values())
            teleports.register(necklace.id, necklace.charges, necklace.replacementId);
    }

}