package io.ruin.model.item.actions.impl;

import io.ruin.cache.ItemDef;

public enum ItemBreaking {

    /* capes */
    FIRE_CAPE(6570, 20445, 25, 50000, false),
    INFERNAL_CAPE(21295, 21287, 25, 200000, false),
    FIRE_MAX_CAPE(13329, 20447, 25, 50000, false),
    INFERNAL_MAX_CAPE(21284, 21289, 25, 200000, false),
    AVAS_ASSEMBLER(22109, 21914, 25, 50000, false),
    ASSEMBLER_MAX_CAPE(21898, 21916, 25, 50000, false),

    /* defenders */
    BRONZE_DEFENDER(8844, 20449, 5, 1000, true),
    IRON_DEFENDER(8845, 20451, 5, 2000, true),
    STEEL_DEFENDER(8846, 20453, 5, 2500, true),
    BLACK_DEFENDER(8847, 20455, 5, 5000, true),
    MITHRIL_DEFENDER(8848, 20457, 5, 15000, true),
    ADAMANT_DEFENDER(8849, 20459, 5, 25000, true),
    RUNE_DEFENDER(8850, 20461, 5, 35000, true),
    DRAGON_DEFENDER(12954, 20463, 10, 40000, false),
    AVERNIC_DEFENDER(22322, 22441, 15, 50000, false),

    /* void */
    VOID_KNIGHT_MAGE_HELMET(11663, 20477, 25, 40000, false),
    VOID_KNIGHT_RANGE_HELMET(11664, 20479, 25, 40000, false),
    VOID_KNIGHT_MELEE_HELMET(11665, 20481, 25, 40000, false),
    VOID_KNIGHT_TOP(8839, 20465, 25, 45000, false),
    VOID_KNIGHT_ROBE(8840, 20469, 25, 45000, false),
    VOID_KNIGHT_MACE(8841, 20473, 15, 10000, true),
    VOID_GLOVES(8842, 20475, 25, 30000, false),
    ELITE_VOID_TOP(13072, 20467, 25, 50000, false),
    ELITE_VOID_ROBE(13073, 20471, 25, 50000, false),

    /* barbarian assault */
    FIGHTER_HAT(10548, 20507, 15, 45000, false),
    RANGER_HAT(10550, 20509, 15, 45000, false),
    HEALER_HAT(10547, 20511, 15, 45000, false),
    FIGHTER_TORSO(10551, 20513, 15, 50000, false),
    PENANCE_SKIRT(10555, 20515, 15, 20000, false),

    /* decorative armour */
    DECORATIVE_SWORD(4508, 20483, 5, 5000, false),
    DECORATIVE_TOP_GOLD(4509, 20485, 5, 5000, false),
    DECORATIVE_LEGS_GOLD(4510, 20487, 5, 5000, false),
    DECORATIVE_HELM_GOLD(4511, 20489, 5, 5000, false),
    DECORATIVE_SHIELD_GOLD(4512, 20491, 5, 5000, false),
    DECORATIVE_SKIRT_GOLD(11895, 20493, 5, 5000, false),
    DECORATIVE_ROBE_TOP(11896, 20495, 5, 5000, false),
    DECORATIVE_ROBE_SKIRT(11897, 20497, 5, 5000, false),
    DECORATIVE_HAT(11898, 20499, 5, 5000, false),
    DECORATIVE_RANGE_BODY(11899, 20501, 5, 5000, false),
    DECORATIVE_CHAPS(11900, 20503, 5, 5000, false),
    DECORATIVE_QUIVER(11901, 20505, 5, 5000, false),

    /* halos */
    SARADOMIN_HALO(12637, 20537, 15, 25000, false),
    ZAMORAK_HALO(12638, 20539, 15, 25000, false),
    GUTHIX_HALO(12639, 20541, 15, 25000, false),

    /* barrows */
    VERAC_HELM(4753, 4980, 10, 60000, false),
    VERAC_FLAIL(4755, 4986, 10, 100000, false),
    VERAC_BRASSARD(4757, 4992, 10, 90000, false),
    VERAC_PLATE_SKIRT(4759, 4998, 10, 80000, false),

    TORAG_HELM(4745, 4956, 10, 60000, false),
    TORAG_HAMMERS(4747, 4962, 10, 100000, false),
    TORAG_PLATE_BODY(4749, 4968, 10, 90000, false),
    TORAG_PLATE_LEGS(4751, 4974, 10, 80000, false),

    DHAROK_HELM(4716, 4884, 10, 60000, false),
    DHAROK_GREAT_AXE(4718, 4890, 10, 100000, false),
    DHAROK_PLATE_BODY(4720, 4896, 10, 90000, false),
    DHAROK_PLATE_LEGS(4722, 4902, 10, 80000, false),

    AHRIM_HOOD(4708, 4860, 10, 60000, false),
    AHRIM_STAFF(4710, 4866, 10, 100000, false),
    AHRIM_ROBE_TOP(4712, 4872, 10, 90000, false),
    AHRIM_ROBE_SKIRT(4714, 4878, 10, 80000, false),

    KARIL_COIF(4732, 4932, 10, 60000, false),
    KARIL_CROSSBOW(4734, 4938, 10, 100000, false),
    KARIL_LEATHER_TOP(4736, 4944, 10, 90000, false),
    KARIL_LEATHER_SKIRT(4738, 4950, 10, 80000, false),

    GUTHAN_HELM(4724, 4908, 10, 6000, false),
    GUTHAN_WAR_SPEAR(4726, 4914, 10, 10000, false),
    GUTHAN_PLATE_BODY(4728, 4920, 10, 9000, false),
    GUTHAN_CHAIN_SKIRT(4730, 4926, 10, 80000, false);

    public final int brokenId, fixedId, bmRepairCost, coinRepairCost;
    public final boolean freeFromShops;

    ItemBreaking(int fixedId, int brokenId, int bmRepairCost, int coinRepairCost, boolean freeFromShops) {
        this.fixedId = fixedId;
        this.brokenId = brokenId;
        this.bmRepairCost = bmRepairCost;
        this.coinRepairCost = coinRepairCost;
        this.freeFromShops = freeFromShops;
        ItemDef.get(brokenId).brokenFrom = this;
        ItemDef.get(fixedId).breakTo = this;
    }

}