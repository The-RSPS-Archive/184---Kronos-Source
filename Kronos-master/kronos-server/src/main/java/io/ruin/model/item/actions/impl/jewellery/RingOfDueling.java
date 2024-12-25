package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.map.Bounds;

public enum RingOfDueling {

    EIGHT(2552, 8, 2554),
    SEVEN(2554, 7, 2556),
    SIX(2556, 6, 2558),
    FIVE(2558, 5, 2560),
    FOUR(2560, 4, 2562),
    THREE(2562, 3, 2564),
    TWO(2564, 2, 2566),
    ONE(2566, 1, -1);

    public final int id, charges, replacementId;

    RingOfDueling(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("ring", false,
                new JeweleryTeleports.Teleport("Duel Arena", new Bounds(3313, 3232, 3318, 3237, 0)),
                new JeweleryTeleports.Teleport("Castle Wars", new Bounds(2439, 3088, 2442, 3092, 0)),
                new JeweleryTeleports.Teleport("Clan Wars", new Bounds(3387, 3158, 3390, 3161, 0))
        );
        for(RingOfDueling ring : values())
            teleports.register(ring.id, ring.charges, ring.replacementId);
    }

}
