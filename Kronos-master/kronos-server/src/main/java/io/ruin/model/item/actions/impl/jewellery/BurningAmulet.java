package io.ruin.model.item.actions.impl.jewellery;

public enum BurningAmulet {

    FIVE(21166, 5, 21169),
    FOUR(21169, 4, 21171),
    THREE(21171, 3, 21173),
    TWO(21173, 2, 21175),
    ONE(21175, 1, -1);

    private final int id, charges, replacementId;

    BurningAmulet(int id, int charges, int replacementId) {
        this.id = id;
        this.charges = charges;
        this.replacementId = replacementId;
    }

    static {
        JeweleryTeleports teleports = new JeweleryTeleports("amulet", false,
                new JeweleryTeleports.Teleport("Chaos Temple", 3236, 3635, 0),
                new JeweleryTeleports.Teleport("Bandit Camp", 3037, 3651, 0),
                new JeweleryTeleports.Teleport("Lava Maze", 3030, 3841, 0)
        );
        for(BurningAmulet amulet : values())
            teleports.register(amulet.id, amulet.charges, amulet.replacementId);
    }

}