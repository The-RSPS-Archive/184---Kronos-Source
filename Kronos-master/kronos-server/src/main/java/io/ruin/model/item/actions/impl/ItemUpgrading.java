package io.ruin.model.item.actions.impl;

import io.ruin.cache.ItemDef;

public enum ItemUpgrading {

    VOID_TOP(8839, 13072, 3000, 7000000),
    VOID_BOTTOM(8840, 13073, 3000, 7000000),
    MAGIC_SHORT(861, 12788, 200, 4000000),
    SEERS_RING(6731, 11770, 2500, 5000000),
    ARCHERS_RING(6733, 11771, 2500, 5000000),
    WARRIOR_RING(6735, 11772, 1000, 5000000),
    BERSERKER_RING(6737, 11773, 1000, 5000000),
    SLAYER_HELMET(11864, 11865, 2000, 10000000),
    TYRANNICAL_RING(12603, 12691, 1500, 5000000),
    TREASONOUS_RING(12605, 12692, 1500, 5000000),
    RING_OF_THE_GODS(12601, 13202, 1500, 5000000),
    BLACK_MASK(8921, 11784, 1500, 10000000),
    BLACK_SLAYER_HELMET(19639, 19641, 2000, 10000000),
    GREEN_SLAYER_HELMET(19643, 19645, 2000, 10000000),
    RED_SLAYER_HELMET(19647, 19649, 2000, 10000000),
    TURQUOISE_SLAYER_HELMET(21888, 21890, 2000, 10000000),
    RING_OF_SUFFERING(19550, 19710, 2000, 5000000),
    GUTHIX_CAPE(2413, 21793, 5000, 3000000),
    ZAMORAK_CAPE(2414, 21795, 5000, 3000000),
    SARADOMIN_CAPE(2412, 21791, 5000, 3000000),
    SALVE_AMULET(4081, 12017, 5000, 3000000);
    public final int regularId, upgradeId;

    public final int bmUpgradeCost, coinUpgradeCost;

    ItemUpgrading(int regularId, int upgradeId, int bmUpgradeCost, int coinUpgradeCost) {
        this.regularId = regularId;
        this.upgradeId = upgradeId;
        this.bmUpgradeCost = bmUpgradeCost;
        this.coinUpgradeCost = coinUpgradeCost;

        ItemDef regularDef = ItemDef.get(regularId);
        ItemDef upgradeDef = ItemDef.get(upgradeId);
        if(upgradeDef.protectValue < regularDef.protectValue)
            upgradeDef.protectValue = regularDef.protectValue;
        upgradeDef.protectValue += bmUpgradeCost;
        upgradeDef.upgradedFrom = this;
    }

}

