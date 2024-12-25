package io.ruin.model.item.actions.impl.combine;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;

public enum ItemCombining {

    FROZEN_ABYSSAL_WHIP(4151, 12769, 12774, false),
    VOLCANIC_ABYSSAL_WHIP(4151, 12771, 12773, false),
    BLUE_BOW_MIX(12757, 11235, 12766, false),
    GREEN_BOW_MIX(12759, 11235, 12765, false),
    YELLOW_BOW_MIX(12761, 11235, 12767, false),
    WHITE_BOW_MIX(12763, 11235, 12768, false),
    ODIUM_WARD(12802, 11926, 12807, true),
    MALEDICTION_WARD(12802, 11924, 12806, true),
    AMULET_OF_FURY(6585, 12526, 12436, true),
    GRANITE_MAUL(12849, 4153, 12848, true),
    DRAGON_DEFENDER(12954, 20143, 19722, true),
    DRAGON_SCIMITAR(4587, 20002, 20000, true),
    DRAGON_PICKAXE(11920, 12800, 12797, true),
    AMULET_OF_TORTURE(19553, 20062, 20366, true),
    OCCULT_NECKLACE(12002, 20065, 19720, true),
    ARMADYL_GODSWORD(11802, 20068, 20368, true),
    BANDOS_GODSWORD(11804, 20071, 20370, true),
    SARADOMIN_GODSWORD(11806, 20074, 20372, true),
    ZAMORAK_GODSWORD(11808, 20077, 20374, true),
    DRAGONFIRE_SHIELD(11286, 1540, 11283, false),
    DRAGONFIRE_WARD(22006, 1540, 22003, false),
    DRAGON_CHAIN(3140, 12534, 12414, true),
    DRAGON_PLATELEGS(4087, 12536, 12415, true),
    DRAGON_SKIRT(4585, 12536, 12416, true),
    DRAGON_FULL_HELM(11335, 12538, 12417, true),
    DRAGON_SQUARE(1187, 12532, 12418, true),
    NORMAL_BATTLESTAFF(11787, 12798, 12795, true),
    MYSTIC_BATTLESTAFF(11789, 12798, 12796, true),
    DARK_INFINITY_HAT(12528, 6918, 12457, true),
    DARK_INFINITY_TOP(12528, 6916, 12458, true),
    DARK_INFINITY_BOTTOMS(12528, 6924, 12459, true),
    LIGHT_INFINITY_HAT(12530, 6918, 12419, true),
    LIGHT_INFINITY_TOP(12530, 6916, 12420, true),
    LIGHT_INFINITY_BOTTOMS(12530, 6924, 12421, true),
    AVERNIC_DEFENDER(12954, 22477, 22322, true),
    DRAGON_BOOTS(11840, 22231, 22234, true),
    SARADOMINS_LIGHT(11791, 13256, 22296, false),
    NECKLACE_OF_ANGUISH(19547, 22246, 22249, true),
    BONECRUSHER_NECKLACE(22988, 13116, 22986, false),
    DRAGON_HUNTER_LANCE(22966, 11889, 22978, false),
    TWISTED_BOW_ELDER(30039, 20997, 30036, true),
    TWISTED_BOW_KODAI(30040, 20997, 30037, true),
    PRIMORDIAL_BOOTS_G(30046, 13239, 30038, true),
    BRIMSTONE_BOOTS(23037, 22957, 22951, false),
    CORRUPTED_PICKAXEI(30098, 30145, 30101, false),
    KODAI_WAND(21043, 6914, 21006, false),
    ANCESTRAL_HAT_LIMITED(30160, 21018, 30175, false),
    ANCESTRAL_TOP_LIMITED(30163, 21021, 30177, false),
    ANCESTRAL_BOTTOM_LIMITED(30166, 21024, 30179, false),
    KODAI_WAND_LIMITED(30169, 21006, 30181, false),
    TWISTED_BOW_LIMITED(30172, 20997, 30183, false);

    public final int primaryId, secondaryId, combinedId;
    public final boolean reversible;

    ItemCombining(int primaryId, int secondaryId, int combinedId, boolean reversible) {
        this.primaryId = primaryId;
        this.secondaryId = secondaryId;
        this.combinedId = combinedId;
        this.reversible = reversible;
        ItemDef.get(combinedId).combinedFrom = this;
    }

    private static void make(Player player, Item item, Item kit, int resultID, boolean reversible) {
        String message;
        if (reversible)
            message = "Combine the " + item.getDef().name + " and " + kit.getDef().name + "?";
        else
            message = "Combining these items will be irreversible";
        player.dialogue(
            new YesNoDialogue("Are you sure you want to do this?", message, resultID, 1, () -> {
                player.animate(713);
                Item result = new Item(resultID, 1);
                AttributeExtensions.putAttributes(result, item.copyOfAttributes());
                item.remove();
                kit.remove();
                player.getInventory().add(result);
                new ItemDialogue().one(resultID, "You apply the " + item.getDef().name + " to the " + kit.getDef().name + ".");
            })
        );
    }

    private static void revert(Player player, Item kit, int primary, int revert) {
        if(player.getInventory().getFreeSlots() < 1) {
            player.sendMessage("You don't have enough inventory space to do this.");
            return;
        }
        Item item = new Item(primary, 1);
        AttributeExtensions.putAttributes(item, kit.copyOfAttributes());
        player.dialogue(
            new YesNoDialogue("Are you sure you want to do this?", "Revert the item back to its normal form and get the kit back?", primary, 1, () -> {
                player.getInventory().add(item);
                kit.setId(revert);
                AttributeExtensions.removeUpgrades(kit);
                new ItemDialogue().one(primary, "You remove the " + kit.getDef().name + " from the " + item.getDef().name + ".");
            })
        );
    }

    static {
        for (ItemCombining kit : values()) {
            ItemItemAction.register(kit.primaryId, kit.secondaryId, (player, primary, secondary) -> make(player, primary, secondary, kit.combinedId, kit.reversible));
            ItemAction.registerInventory(kit.combinedId, "dismantle", (player, item) -> revert(player, item, kit.primaryId, kit.secondaryId));
            ItemAction.registerInventory(kit.combinedId, "revert", (player, item) -> revert(player, item, kit.primaryId, kit.secondaryId));
            int combinedProtect = ItemDef.get(kit.combinedId).protectValue;
            int componentsProtect = Math.max(ItemDef.get(kit.primaryId).protectValue, ItemDef.get(kit.secondaryId).protectValue);
            if (combinedProtect < componentsProtect)
                ItemDef.get(kit.combinedId).protectValue = componentsProtect;
        }
    }

}
