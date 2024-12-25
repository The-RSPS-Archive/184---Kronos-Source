package io.ruin.model.item.actions.impl.combine;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;

public class SaradominsTear {

    private static final int SARADOMIN_SWORD = 11838;
    private static final int SARADOMIN_TEAR = 12804;
    private static final int BLESSED_SWORD = 12809;

    private static void makeSword(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>A blessed Saradomin sword is untradeable,<br> with an Attack requirement of 75.<br> After 10,000 hits, the sword crumbles to dust, and you get the Tear back."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your sword will be consumed.", result, 1, () -> {
                    primary.setId(BLESSED_SWORD);
                    AttributeExtensions.setCharges(primary, 10000);
                    secondary.remove();
                })
        );
    }

    private static void revertSword(Player player, Item primary) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>Reverting the blessed sword does NOT return the Saradomin sword, only the tear. Are you sure?"),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, you will only receive Saradomin's tear.", BLESSED_SWORD, 1, () -> {
                    primary.setId(SARADOMIN_TEAR);
                    primary.clearAttribute(AttributeTypes.CHARGES);
                })
        );
    }

    private static void check(Player player, Item item) {
        player.sendMessage("Your Saradomin's blessed sword has " + NumberUtils.formatNumber(AttributeExtensions.getCharges(item)) + " charges left.");
    }

    static {
        ItemAction.registerInventory(BLESSED_SWORD, "check", SaradominsTear::check);
        ItemAction.registerEquipment(BLESSED_SWORD, "check", SaradominsTear::check);
        ItemAction.registerInventory(BLESSED_SWORD, "revert", SaradominsTear::revertSword);
        ItemItemAction.register(SARADOMIN_SWORD, SARADOMIN_TEAR, (player, primary, secondary) -> makeSword(player, primary, secondary, BLESSED_SWORD));

        ItemDef.get(BLESSED_SWORD).addPostTargetDefendListener((player, item, hit, target) -> {
            int charges = AttributeExtensions.getCharges(item);
            if(charges == 0) //Assume the full sword was bought from a shop or the online store.
                charges = 10000;
            if(--charges <= 0) {
                item.remove();
                player.getInventory().addOrDrop(SARADOMIN_TEAR, 1);
            }
            AttributeExtensions.setCharges(item, charges);
        });
    }


}
