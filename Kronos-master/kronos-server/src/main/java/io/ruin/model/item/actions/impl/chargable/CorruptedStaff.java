package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;

/**
 * @author ReverendDread on 6/17/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class CorruptedStaff {

    public static final int UNCHARGED = 30023;
    public static final int CHARGED = 30032;
    public static final int ESSENCE = 30013;
    private static final int MAX_CHARGES = 10_000;

    private static void onStaffHit(Player player, Item item, Hit hit, Entity entity) {
        int currentCharges = AttributeExtensions.getCharges(item);
        if (currentCharges <= 1) {
            item.setId(UNCHARGED);
            player.sendMessage(Color.RED.wrap("Your Corrupted staff has ran out of charges!"));
            player.getCombat().updateWeapon(false);
        }
        AttributeExtensions.deincrementCharges(item, 1);
    }

    private static void charge(Player player, Item javelin, Item essence) {
        int currentCharges = AttributeExtensions.getCharges(javelin);
        if (currentCharges >= MAX_CHARGES) {
            player.sendMessage("Your staff can't hold any more charges.");
            return;
        }
        int chargesInInventory = player.getInventory().getAmount(ESSENCE);
        if (chargesInInventory == 0) {
            player.sendMessage("You require corrupted essence to charge your staff.");
            return;
        }
        int chargesToAdd = Math.min(chargesInInventory, MAX_CHARGES - currentCharges);
        player.integerInput("How many charges do you want to apply? (Up to " + NumberUtils.formatNumber(chargesToAdd) + ")", (input) -> {
            int allowed = MAX_CHARGES - currentCharges;
            int removed = player.getInventory().remove(ESSENCE, Math.min(allowed, input));
            AttributeExtensions.addCharges(javelin, removed);
            javelin.setId(CHARGED);
            check(player, javelin);
        });
    }

    private static void check(Player player, Item item) {
        player.sendMessage("Your Corrupted staff currently has " + NumberUtils.formatNumber(item.getAttributeInt(AttributeTypes.CHARGES)) + " charges.");
    }

    static {
        ItemDef.get(CHARGED).addPreTargetDefendListener(CorruptedStaff::onStaffHit);
        ItemAction.registerInventory(CHARGED, "check", CorruptedStaff::check);
        ItemAction.registerInventory(CHARGED, "uncharge", CorruptedStaff::uncharge);
        ItemAction.registerEquipment(CHARGED, "check", CorruptedStaff::check);
        ItemItemAction.register(UNCHARGED, ESSENCE, CorruptedStaff::charge);
        ItemItemAction.register(CHARGED, ESSENCE, CorruptedStaff::charge);
    }

    private static void uncharge(Player player, Item javelin) {
        int reqSlots = 0;
        int amnt = AttributeExtensions.getCharges(javelin);
        if (amnt > 0) {
            if (!player.getInventory().hasId(ESSENCE))
                reqSlots++;
        }
        if (player.getInventory().getFreeSlots() < reqSlots) {
            player.sendMessage("You don't have enough inventory space to uncharge your Corrupted staff.");
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge this weapon, all the corrupted essence will be returned to your inventory.", javelin, () -> {
            if (!player.getInventory().contains(javelin))
                return;
            if (amnt > 0)
                player.getInventory().add(ESSENCE, amnt);
            AttributeExtensions.setCharges(javelin, 0);
            javelin.setId(UNCHARGED);
        }));
    }

}
