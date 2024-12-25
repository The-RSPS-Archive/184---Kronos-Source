package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.map.Bounds;

public enum CombatBracelet {

    SIX(11972, 6, 11974),
    FIVE(11974, 5, 11118),
    FOUR(11118, 4, 11120),
    THREE(11120, 3, 11122),
    TWO(11122, 2, 11124),
    ONE(11124, 1, 11126),
    UNCHARGED(11126, 0, -1);

    private final int id, charges, replacementId;

    CombatBracelet(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("bracelet", false,
                new JeweleryTeleports.Teleport("Warrior's Guild", new Bounds(2880, 3545, 2882, 3547, 0)),
                new JeweleryTeleports.Teleport("Champion's Guild", new Bounds(3190, 3366, 3192, 3368, 0)),
                new JeweleryTeleports.Teleport("Edgeville Monastery", new Bounds(3053, 3489, 3053, 3492, 0)),
                new JeweleryTeleports.Teleport("Ranging Guild", new Bounds(2653, 3440, 2655, 3442, 0))
        );
        for(CombatBracelet bracelet : values())
            teleports.register(bracelet.id, bracelet.charges, bracelet.replacementId);
    }

}
