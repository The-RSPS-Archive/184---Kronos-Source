package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.map.Bounds;

public enum SkillsNecklace {

    SIX(11968, 6, 11970),
    FIVE(11970, 5, 11105),
    FOUR(11105, 4, 11107),
    THREE(11107, 3, 11109),
    TWO(11109, 2, 11111),
    ONE(11111, 1, 11113),
    UNCHARGED(11113, 0, -1);

    private final int id, charges, replacementId;

    SkillsNecklace(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("necklace", false,
                new JeweleryTeleports.Teleport("Fishing Guild", new Bounds(2610, 3390, 2612, 3392, 0)),
                new JeweleryTeleports.Teleport("Mining Guild", new Bounds(3045, 9759, 3047, 9761, 0)),
                new JeweleryTeleports.Teleport("Crafting Guild", new Bounds(2932, 3292, 2934, 3294, 0)),
                new JeweleryTeleports.Teleport("Cooking Guild", new Bounds(3142, 3441, 3143, 3442, 0)),
                new JeweleryTeleports.Teleport("Woodcutting Guild", new Bounds(1659, 3504, 1661, 3506, 0)),
                new JeweleryTeleports.Teleport("Farming Guild", new Bounds(1248, 3719, 1249, 3720, 0))
        );
        for(SkillsNecklace necklace : values())
            teleports.register(necklace.id, necklace.charges, necklace.replacementId);
    }
}
