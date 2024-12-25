package io.ruin.model.item.actions.impl.chargable;

import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;

public class CrawsBow {

    private static final int CHARGED = 22550;
    private static final int UNCHARGED = 22547;

    private static final int MAX_AMOUNT = 16000;
    private static final int REVENANT_ETHER = 21820;
    private static final int ACTIVATION_AMOUNT = 1000;

    static {
        ItemAction.registerInventory(CHARGED, "check", CrawsBow::check);
        ItemAction.registerEquipment(CHARGED, "check", CrawsBow::check);
        ItemAction.registerInventory(CHARGED, "uncharge", CrawsBow::uncharge);
        ItemItemAction.register(CHARGED, REVENANT_ETHER, CrawsBow::charge);
        ItemItemAction.register(UNCHARGED, REVENANT_ETHER, CrawsBow::charge);
        ItemDef.get(CHARGED).addPreTargetDefendListener((player, item, hit, target) -> {
            if (hit.attackStyle != null && hit.attackStyle.isRanged() && target.npc != null && player.wildernessLevel > 0) {
                if(consumeCharge(player, item)) {
                    hit.boostAttack(0.5); //50% accuracy increase
                    hit.boostDamage(0.5); //50% damage increase
                }
            }
        });
    }

    private static void check(Player player, Item bow) {
        int etherAmount = AttributeExtensions.getCharges(bow) - ACTIVATION_AMOUNT;
        player.sendMessage("Your bow has " + (etherAmount) + " charge" + (etherAmount <= 1 ? "" : "s") + " left powering it.");
    }

    private static void charge(Player player, Item crawsBow, Item etherItem) {
        int etherAmount = AttributeExtensions.getCharges(crawsBow);
        int allowedAmount = MAX_AMOUNT - etherAmount;
        if (allowedAmount == 0) {
            player.sendMessage("Craw's Bow can't hold any more revenant ether.");
            return;
        }
        if(etherAmount == 0 && etherItem.getAmount() < ACTIVATION_AMOUNT) {
            player.sendMessage("You require at least 1000 revenant ether to activate this weapon.");
            return;
        }
        int addAmount = Math.min(allowedAmount, etherItem.getAmount());
        int newTotal = etherAmount + (addAmount);
        etherItem.incrementAmount(-addAmount);
        AttributeExtensions.setCharges(crawsBow, newTotal);
        etherAmount = AttributeExtensions.getCharges(crawsBow);
        crawsBow.setId(CHARGED);
        if(etherAmount == 0)
            player.sendMessage("You use 1000 ether to activate the weapon.");
        player.sendMessage("You add a further " + (etherAmount == 0 ? addAmount - ACTIVATION_AMOUNT: addAmount)
                + " revenant ether to your weapon giving it a total of " + (etherAmount - ACTIVATION_AMOUNT) + " charges");
    }

    private static void uncharge(Player player, Item crawsBow) {
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge this weapon, all the revenant ether will be returned to your inventory.", crawsBow, () -> {
            int etherAmount = AttributeExtensions.getCharges(crawsBow);
            player.getInventory().addOrDrop(REVENANT_ETHER, etherAmount);
            AttributeExtensions.setCharges(crawsBow, 0);
            crawsBow.setId(UNCHARGED);
        }));
    }

    public static boolean consumeCharge(Player player, Item item) {
        int currentCharges = AttributeExtensions.getCharges(item);
        if (currentCharges <= 1000) {
            player.sendMessage(Color.DARK_RED.wrap("Your weapon has run out of revenant ether!"));
            return false;
        }
        AttributeExtensions.deincrementCharges(item, 1);
        return true;
    }

}

