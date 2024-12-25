package io.ruin.model.item.actions.impl.chargable;

import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;

public class ThammaronsSceptre {

    private static final int CHARGED = 22555;
    private static final int UNCHARGED = 22552;

    private static final int MAX_AMOUNT = 16000;
    private static final int REVENANT_ETHER = 21820;
    private static final int ACTIVATION_AMOUNT = 1000;

    static {
        ItemAction.registerInventory(CHARGED, "check", ThammaronsSceptre::check);
        ItemAction.registerEquipment(CHARGED, "check", ThammaronsSceptre::check);
        ItemAction.registerInventory(CHARGED, "uncharge", ThammaronsSceptre::uncharge);
        ItemItemAction.register(CHARGED, REVENANT_ETHER, ThammaronsSceptre::charge);
        ItemItemAction.register(UNCHARGED, REVENANT_ETHER, ThammaronsSceptre::charge);
        ItemDef.get(CHARGED).addPreTargetDefendListener((player, item, hit, target) -> {
            if (hit.attackStyle != null && hit.attackStyle.isMagic() && target.npc != null && player.wildernessLevel > 0) {
                if(consumeCharge(player, item)) {
                    hit.boostAttack(1); //100% accuracy increase
                    hit.boostDamage(0.25); //25% damage increase
                }
            }
        });
    }

    private static void check(Player player, Item sceptre) {
        int etherAmount = AttributeExtensions.getCharges(sceptre) - ACTIVATION_AMOUNT;
        player.sendMessage("Your sceptre has " + (etherAmount) + " charge" + (etherAmount <= 1 ? "" : "s") + " left powering it.");
    }

    private static void charge(Player player, Item sceptre, Item etherItem) {
        int etherAmount = AttributeExtensions.getCharges(sceptre);
        int allowedAmount = MAX_AMOUNT - etherAmount;
        if (allowedAmount == 0) {
            player.sendMessage("Thammaron's sceptre can't hold any more revenant ether.");
            return;
        }
        if(etherAmount == 0 && etherItem.getAmount() < ACTIVATION_AMOUNT) {
            player.sendMessage("You require at least 1000 revenant ether to activate this weapon.");
            return;
        }
        int addAmount = Math.min(allowedAmount, etherItem.getAmount());
        etherItem.incrementAmount(-addAmount);
        AttributeExtensions.addCharges(sceptre, addAmount);
        etherAmount = AttributeExtensions.getCharges(sceptre);
        sceptre.setId(CHARGED);
        if(etherAmount == 0)
            player.sendMessage("You use 1000 ether to activate the weapon.");
        player.sendMessage("You add a further " + (etherAmount == 0 ? addAmount - ACTIVATION_AMOUNT: addAmount)
                + " revenant ether to your weapon giving it a total of " + (sceptre.getAttributeInt(AttributeTypes.CHARGES) - ACTIVATION_AMOUNT) + " charges");
    }

    private static void uncharge(Player player, Item sceptre) {
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge this weapon, all the revenant ether will be returned to your inventory.", sceptre, () -> {
            int etherAmount = AttributeExtensions.getCharges(sceptre);
            player.getInventory().add(REVENANT_ETHER, etherAmount);
            AttributeExtensions.setCharges(sceptre, 0);
            sceptre.setId(UNCHARGED);
        }));
    }

    public static boolean consumeCharge(Player player, Item item) {
        int charges = AttributeExtensions.getCharges(item);
        if(charges <= 1000) {
            player.sendMessage(Color.DARK_RED.wrap("Your weapon has run out of revenant ether!"));
            return false;
        }
        AttributeExtensions.deincrementCharges(item, 1);
        return true;
    }
}
