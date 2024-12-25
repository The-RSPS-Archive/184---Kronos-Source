package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.cache.ItemID;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.utility.Utils;
import lombok.experimental.ExtensionMethod;

/**
 * @author ReverendDread on 5/16/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class SanguinestiStaff {

    public static final int CHARGED = 22323;
    public static final int UNCHARGED = 22481;
    public static final int MAX_CHARGES = 20_000;

    public static void consumeCharge(Player player, Item staff, Hit hit, Entity entity) {
        if (AttributeExtensions.getCharges(staff) > 0) {
            AttributeExtensions.deincrementCharges(staff, 1);
        } else {
            player.sendMessage(Color.RED.wrap("Your staff has ran out of charges!"));
            player.getCombat().updateWeapon(false);
        }
    }

    public static void check(Player player, Item staff) {
        player.sendMessage("Your Sanguinesti staff has " + NumberUtils.formatNumber(AttributeExtensions.getCharges(staff)) + " charges remaining.");
    }

    public static void uncharge(Player player, Item staff) {
        int charges = AttributeExtensions.getCharges(staff);
        player.dialogue(new OptionsDialogue(Color.DARK_RED.wrap("Uncharge your staff for all its charges? (regaining " + NumberUtils.formatNumber(charges * 3) + " blood runes)."),
                new Option("Proceed.", () -> {
                    player.getInventory().add(ItemID.BLOOD_RUNE, charges * 3);
                    AttributeExtensions.setCharges(staff, 0);
                    staff.setId(UNCHARGED);
                    player.dialogue(new ItemDialogue().one(CHARGED, "You uncharge your sanguindesti staff, regaining " +
                            NumberUtils.formatNumber(charges * 3) + " blood runes in the process."));
                }),
                new Option("Cancel.", Player::closeDialogue)
        ));
    }

    public static void charge(Player player, Item staff) {
        int currentCharges = AttributeExtensions.getCharges(staff);
        if (currentCharges >= MAX_CHARGES) {
            player.sendMessage("Your staff can't hold any more charges.");
            return;
        }
        int chargesInInventory = player.getInventory().getAmount(ItemID.BLOOD_RUNE) / 3;
        if (chargesInInventory == 0) {
            player.sendMessage("You require blood runes to charge your staff.");
            return;
        }
        int chargesToAdd = Math.min(chargesInInventory, MAX_CHARGES - currentCharges);
        player.integerInput("How many charges do you want to apply? (Up to " + NumberUtils.formatNumber(chargesToAdd) + ")", (input) -> {
            int allowed = MAX_CHARGES - currentCharges;
            int removed = player.getInventory().remove(ItemID.BLOOD_RUNE, Math.min(allowed * 3, input * 3));
            AttributeExtensions.addCharges(staff, removed / 3);
            staff.setId(CHARGED);
            check(player, staff);
        });
    }

    public static void charge(Player player, Item staff, Item rune) {
        charge(player, staff);
    }

    private static void wield(Player player, Item item) {
        player.sendMessage("Your sanguinesti staff has no charges! You can use blood runes to power the staff.");
    }

    static {

        ItemItemAction.register(UNCHARGED, ItemID.BLOOD_RUNE, SanguinestiStaff::charge);
        ItemAction.registerInventory(UNCHARGED, "charge", SanguinestiStaff::charge);
        ItemAction.registerInventory(UNCHARGED, "wield", SanguinestiStaff::wield);

        ItemItemAction.register(CHARGED, ItemID.BLOOD_RUNE, SanguinestiStaff::charge);
        ItemAction.registerInventory(CHARGED, "charge", SanguinestiStaff::charge);
        ItemAction.registerEquipment(CHARGED, "check", SanguinestiStaff::check);
        ItemAction.registerInventory(CHARGED, "check", SanguinestiStaff::check);
        ItemAction.registerInventory(CHARGED, "uncharge", SanguinestiStaff::uncharge);
        ItemDef.get(CHARGED).addPreTargetDefendListener(SanguinestiStaff::consumeCharge);

    }

}
