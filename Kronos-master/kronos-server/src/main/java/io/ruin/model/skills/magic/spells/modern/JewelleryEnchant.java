package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

public class JewelleryEnchant extends Spell {

    public enum EnchantData {
        /**
         * Lvl-1 Enchant
         */
        SAPPHIRE_RING(1637, 2550, 719, 238),
        SAPPHIRE_AMULET(1694, 1727, 719, 114),
        SAPPHIRE_NECKLACE(1656, 3853, 719, 114),
        SAPPHIRE_BRACELET(11072, 11074, 719, -1),

        OPAL_RING(21081, 21126, 719, 238),
        OPAL_AMULET(21108, 21160, 719, 114),
        OPAL_NECKLACE(21090, 21143, 719, 114),
        OPAL_BRACELET(21117, 21177, 719, -1),

        /**
         * Lvl-2 Enchant
         */
        EMERALD_RING(1639, 2552, 719, 238),
        EMERALD_AMULET(1696, 1729, 719, 114),
        EMERALD_NECKLACE(1658, 5521, 719, 114),
        EMERALD_BRACELET(11076, 11079, 719, -1),

        JADE_RING(21084, 21129, 719, 238),
        JADE_AMULET(21111, 21163, 719, 114),
        JADE_NECKLACE(21093, 21146, 719, 114),
        JADE_BRACELET(21120, 21180, 719, -1),

        /**
         * Lvl-3 Enchant
         */
        RUBY_RING(1641, 2568, 712, 238),
        RUBY_AMULET(1698, 1725, 720, 115),
        RUBY_NECKLACE(1660, 11194, 720, 115),
        RUBY_BRACELET(11085, 11088, 720, -1),

        TOPAZ_RING(21087, 21140, 720, 238),
        TOPAZ_AMULET(21114, 21166, 720, 115),
        TOPAZ_NECKLACE(21096, 21157, 720, 115),
        TOPAZ_BRACELET(21123, 21183, 720, -1),

        /**
         * Lvl-4 Enchant
         */
        DIAMOND_RING(1643, 2570, 712, 238),
        DIAMOND_AMULET(1700, 1731, 720, 115),
        DIAMOND_NECKLACE(1662, 11090, 720, 115),
        DIAMOND_BRACELET(11092, 11095, 720, -1),

        /**
         * Lvl-5 Enchant
         */
        DRAGONSTONE_RING(1645, 2572, 712, 238),
        DRAGONSTONE_AMULET(1702, 1712, 721, 116),
        DRAGONSTONE_NECKLACE(1664, 11105, 721, 116),
        DRAGONSTONE_BRACELET(11115, 11118, 721, -1),

        /**
         * Lvl-6 Enchant
         */
        ONYX_RING(6575, 6583, 712, 238),
        ONYX_AMULET(6581, 6585, 721, 452),
        ONYX_NECKLACE(6577, 11128, 721, 452),
        ONYX_BRACELET(11130, 11133, 721, -1),

        /**
         * Lvl-7 Enchant
         */
        ZENYTE_RING(19538, 19550, 712, 238),
        ZENYTE_AMULET(19541, 19553, 721, 452),
        ZENYTE_NECKLACE(19535, 19547, 721, 452),
        ZENYTE_BRACELET(19532, 19544, 721, 452);

        public final int item, product, anim, graphic;

        EnchantData(int item, int product, int anim, int graphic) {
            this.item = item;
            this.product = product;
            this.anim = anim;
            this.graphic = graphic;
        }
    }

    public enum EnchantLevel {

        ONE(7, 17.0,
                new Item[]{
                        Rune.COSMIC.toItem(1), Rune.WATER.toItem(1)},
                EnchantData.SAPPHIRE_RING,
                EnchantData.SAPPHIRE_AMULET,
                EnchantData.SAPPHIRE_NECKLACE,
                EnchantData.SAPPHIRE_BRACELET,
                EnchantData.OPAL_RING,
                EnchantData.OPAL_AMULET,
                EnchantData.OPAL_NECKLACE,
                EnchantData.OPAL_BRACELET),
        TWO(27, 37.0,
                new Item[]{
                        Rune.COSMIC.toItem(1), Rune.AIR.toItem(3)},
                EnchantData.EMERALD_RING,
                EnchantData.EMERALD_AMULET,
                EnchantData.EMERALD_NECKLACE,
                EnchantData.EMERALD_BRACELET,
                EnchantData.JADE_RING,
                EnchantData.JADE_AMULET,
                EnchantData.JADE_NECKLACE,
                EnchantData.JADE_BRACELET),
        THREE(49, 47.0,
                new Item[]{
                        Rune.COSMIC.toItem(1), Rune.FIRE.toItem(5)},
                EnchantData.RUBY_RING,
                EnchantData.RUBY_AMULET,
                EnchantData.RUBY_NECKLACE,

                EnchantData.RUBY_BRACELET,
                EnchantData.TOPAZ_RING,
                EnchantData.TOPAZ_AMULET,
                EnchantData.TOPAZ_NECKLACE,
                EnchantData.TOPAZ_BRACELET),
        FOUR(57, 67.0,
                new Item[]{
                        Rune.COSMIC.toItem(1), Rune.EARTH.toItem(10)},
                EnchantData.DIAMOND_RING,
                EnchantData.DIAMOND_AMULET,
                EnchantData.DIAMOND_NECKLACE,
                EnchantData.DIAMOND_BRACELET),
        FIVE(68, 78.0,
                new Item[]{
                        Rune.COSMIC.toItem(1), Rune.EARTH.toItem(15), Rune.WATER.toItem(15)},
                EnchantData.DRAGONSTONE_RING,
                EnchantData.DRAGONSTONE_AMULET,
                EnchantData.DRAGONSTONE_NECKLACE,
                EnchantData.DRAGONSTONE_BRACELET),
        SIX(87, 97.0,
                new Item[]{
                        Rune.COSMIC.toItem(1), Rune.EARTH.toItem(20), Rune.FIRE.toItem(20)},
                EnchantData.ONYX_RING,
                EnchantData.ONYX_AMULET,
                EnchantData.ONYX_NECKLACE,
                EnchantData.ONYX_BRACELET),
        SEVEN(93, 110.0,
                new Item[]{
                        Rune.COSMIC.toItem(1), Rune.BLOOD.toItem(20), Rune.SOUL.toItem(20)},
                EnchantData.ZENYTE_RING,
                EnchantData.ZENYTE_AMULET,
                EnchantData.ZENYTE_NECKLACE,
                EnchantData.ZENYTE_BRACELET);

        public final int levelReq;
        public final double exp;
        public final Item[] runes;
        public final EnchantData[] data;

        EnchantLevel(int levelReq, double exp, Item[] runes, EnchantData... data) {
            this.levelReq = levelReq;
            this.exp = exp;
            this.runes = runes;
            this.data = data;
        }
    }

    public JewelleryEnchant(EnchantLevel enchantLevel) {
        registerItem(enchantLevel.levelReq, enchantLevel.exp, true, enchantLevel.runes, (player, item) -> {
            for (EnchantData enchantLevelData : enchantLevel.data) {
                if (item.getId() != enchantLevelData.item) {
                    continue;
                }

                player.animate(enchantLevelData.anim);
                player.graphics(enchantLevelData.graphic, 100, 0);
                item.setId(enchantLevelData.product);
                return true;
            }

            player.sendMessage("Nothing interesting happens.");
            return false;
        });
    }

    static { // registering enchant tabs
        register(8016, EnchantLevel.ONE);
        register(8017, EnchantLevel.TWO);
        register(8018, EnchantLevel.THREE);
        register(8019, EnchantLevel.FOUR);
        register(8020, EnchantLevel.FIVE);
        register(8021, EnchantLevel.SIX);
    }

    private static void register(int tab, EnchantLevel spell) {
        for (EnchantData data : spell.data) {
            ItemItemAction.register(tab, data.item, (player, primary, secondary) -> {
                primary.incrementAmount(-1);
                secondary.setId(data.product);
                player.animate(data.anim);
                player.graphics(data.graphic, 100, 0);
            });
        }
        ItemAction.registerInventory(tab, 1, (player, item) -> player.sendMessage("Use this tablet on a piece of its unenchanted jewellery."));
    }
}
