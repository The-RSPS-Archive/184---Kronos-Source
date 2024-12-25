package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.cache.ItemID;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;

/**
 * @author ReverendDread on 6/16/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class CorruptedJavelin {

    public static final int UNCHARGED = 30009;
    public static final int CHARGED = 30012;
    public static final int ESSENCE = 30013;
    private static final int MAX_CHARGES = 10_000;

    private static void onJavelinHit(Player player, Item item, Hit hit, Entity entity) {
        int currentCharges = AttributeExtensions.getCharges(item);
        if (currentCharges <= 1) {
            item.setId(UNCHARGED);
            player.sendMessage(Color.RED.wrap("Your Corrupted javelin has ran out of charges!"));
            player.getCombat().updateWeapon(false);
        }
        AttributeExtensions.deincrementCharges(item, 1);
    }

    private static void charge(Player player, Item javelin, Item essence) {
        int currentCharges = AttributeExtensions.getCharges(javelin);
        if (currentCharges >= MAX_CHARGES) {
            player.sendMessage("Your javelin can't hold any more charges.");
            return;
        }
        int chargesInInventory = player.getInventory().getAmount(ESSENCE);
        if (chargesInInventory == 0) {
            player.sendMessage("You require corrupted essence to charge your javelin.");
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
        player.sendMessage("Your Corrupted javelin currently has " + NumberUtils.formatNumber(item.getAttributeInt(AttributeTypes.CHARGES)) + " charges.");
    }

    static {
        ItemDef.get(CHARGED).addPreTargetDefendListener(CorruptedJavelin::onJavelinHit);
        ItemAction.registerInventory(CHARGED, "check", CorruptedJavelin::check);
        ItemAction.registerInventory(CHARGED, "uncharge", CorruptedJavelin::uncharge);
        ItemAction.registerEquipment(CHARGED, "check", CorruptedJavelin::check);
        ItemItemAction.register(UNCHARGED, ESSENCE, CorruptedJavelin::charge);
        ItemItemAction.register(CHARGED, ESSENCE, CorruptedJavelin::charge);
    }

    private static void uncharge(Player player, Item javelin) {
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge this weapon, all the corrupted essence will be returned to your inventory.", javelin, () -> {
            int amount = AttributeExtensions.getCharges(javelin);
            player.getInventory().add(ESSENCE, amount);
            AttributeExtensions.setCharges(javelin, 0);
            javelin.setId(UNCHARGED);
        }));
    }

}
