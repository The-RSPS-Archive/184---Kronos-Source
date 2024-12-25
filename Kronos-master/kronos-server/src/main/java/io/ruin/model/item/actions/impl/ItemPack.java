package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public enum ItemPack {
    // Runes
    FIRE_RUNES(12734, 554),
    WATER_RUNES(12730, 555),
    AIR_RUNES(12728, 556),
    EARTH_RUNES(12732, 557),
    MIND_RUNES(12736, 558),
    CHAOS_RUNES(12738, 562),
    CATALYTIC_RUNES(12738, 562),

    // Farming
    SACKS(13252, 5419),
    BASKETS(13254, 5377),
    POT(13250, 5357),
    COMPOST(19704, 6033),

    // Slayer supplies
    UNFINISHED_BROAD_ARROWHEADS(11885, 11874),
    UNFINISHED_BROAD_BOLTS(11887, 11876),

    // Fishing
    BAIT(11883, 313),
    FEATHERS(11881, 314),
    SANDWORM(13432, 13431),

    // Herblore
    VIAL(11877, 230),
    VIAL_OF_WATER(11879, 228),
    EMPTY_JUGS(20742, 1936),
    AMYLASE(12641, 12640),
    EYE_OF_NEWT(12859, 222),

    // Hunter
    BIRD_SNARE(12740, 10007),
    BOX_TRAP(12742, 10009),

    // Last Man Standing
    ADAMANT_ARROW_PACK(20525, 20388, 50),
    RUNE_ARROW_PACK(20607, 20600, 50),

    //Misc
    SOFT_CLAY(12009, 1762);

    public final int itemID, packContents, packContentsAmt;

    ItemPack(int itemID, int packContents) {
        this.itemID = itemID;
        this.packContents = packContents;
        this.packContentsAmt = 100;
    }

    ItemPack(int itemID, int packContents, int itemAmount) {
        this.itemID = itemID;
        this.packContents = packContents;
        this.packContentsAmt = itemAmount;
    }

    private void open(Player player, Item itemPack) {
        itemPack.remove();
        if (itemPack.getId() == ItemPack.CATALYTIC_RUNES.itemID) {
            player.getInventory().add(562, 50);
            player.getInventory().add(560, 50);
            player.getInventory().add(565, 50);
        } else {
            player.getInventory().add(packContents, packContentsAmt);
        }
        player.sendFilteredMessage("You empty the contents of the item pack.");
    }

    static {
        for(ItemPack item : values())
            ItemAction.registerInventory(item.itemID, 1, item::open);
    }
}
