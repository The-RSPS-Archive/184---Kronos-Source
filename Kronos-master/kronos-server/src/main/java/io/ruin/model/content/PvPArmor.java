package io.ruin.model.content;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;

/**
 * @author ReverendDread on 8/4/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class PvPArmor {

    private static final int CHARGES = 60_000;

    private static final int[] ARMOR_PARTS = {
            //Vesta's spear
            22610,
            //Vesta's longsword
            22613,
            //Vesta's chainbody
            22616,
            //Vesta's plateskirt
            22619,
            //Vesta's longsword
            23615,
            //Morrigin's coif
            22638,
            //Morrigin's leather body
            22641,
            //Morrgiin's leather chaps
            22644,
            //Statius's warhammer
            22622,
            //Statius's full helm
            22625,
            //Statius's platebody
            22628,
            //Statius's platelegs
            22631,
            //Statius's warhammer
            23620,
            //Zuriel's staff
            22647,
            //Zuriel's hood
            22650,
            //Zuriel's robe top
            22653,
            //Zuriel's robe bottom
            22656,
            //Zuriel's staff
            23617
    };

    static {
        for (int i : ARMOR_PARTS) {
            ItemDef.get(i).addPostDamageListener(PvPArmor::postDamageListener);
//            ItemAction.registerEquipment(i, "check", PvPArmor::checkCharges);
            ItemAction.registerInventory(i, "check", PvPArmor::checkCharges);
        }
    }

    private static void postDamageListener(Player player, Item item, Hit hit) {
        boolean applyCharges = !AttributeExtensions.hasAttribute(item, AttributeTypes.CHARGES);
        if (applyCharges) AttributeExtensions.putAttribute(item, AttributeTypes.CHARGES, CHARGES);
        if (AttributeExtensions.deincrementCharges(item, 1) <= 0) {
            player.sendMessage(Color.RED.wrap("Your " + item.getDef().name + " has degraded to dust."));
            item.remove();
        }
    }

    private static void checkCharges(Player player, Item item) {
        boolean applyCharges = !AttributeExtensions.hasAttribute(item, AttributeTypes.CHARGES);
        if (applyCharges) AttributeExtensions.putAttribute(item, AttributeTypes.CHARGES, CHARGES);
        player.sendMessage("Your " + item.getDef().name + " has " + NumberUtils.formatNumber(AttributeExtensions.getCharges(item)) + " charges remaining.");
    }

}
