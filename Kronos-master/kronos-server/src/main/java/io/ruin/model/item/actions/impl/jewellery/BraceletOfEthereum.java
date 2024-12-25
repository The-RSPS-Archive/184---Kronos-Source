package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.containers.Equipment;

public class BraceletOfEthereum {

    public static final int CHARGED = 21816;
    private static final int UNCHARGED = 21817;
    private static final int MAX_CHARGES = 16000;
    public static final int REVENANT_ETHER = 21820;

    static {
        /**
         * Uncharged
         */
        ItemAction.registerInventory(UNCHARGED, "dismantle", BraceletOfEthereum::dismantle);
        ItemAction.registerInventory(UNCHARGED, "toggle-absorption", BraceletOfEthereum::toggleAbsorption);
        ItemAction.registerEquipment(UNCHARGED, "check", BraceletOfEthereum::check);
        ItemAction.registerEquipment(UNCHARGED, "toggle absorption", BraceletOfEthereum::toggleAbsorption);
        ItemItemAction.register(UNCHARGED, REVENANT_ETHER, BraceletOfEthereum::charge);
        ItemDef.get(UNCHARGED).sigmundBuyPrice = 500;

        /**
         * Charged
         */
        ItemAction.registerEquipment(CHARGED, "toggle absorption", BraceletOfEthereum::toggleAbsorption);
        ItemAction.registerEquipment(CHARGED, "check", BraceletOfEthereum::check);
        ItemAction.registerInventory(CHARGED, "toggle-absorption", BraceletOfEthereum::toggleAbsorption);
        ItemAction.registerInventory(CHARGED, "check", BraceletOfEthereum::check);
        ItemAction.registerInventory(CHARGED, "uncharge", BraceletOfEthereum::uncharge);
        ItemItemAction.register(CHARGED, REVENANT_ETHER, BraceletOfEthereum::charge);
        ItemDef.get(CHARGED).breakId = UNCHARGED;
    }

    private static void toggleAbsorption(Player player, Item item) {
        player.autoCollectEther = !player.autoCollectEther;
        String toggle = player.autoCollectEther ? "on" : "off";
        player.sendMessage(Color.DARK_GREEN.wrap("Bracelet of Ethereum absorption is now toggled " + toggle + "!"));

    }

    private static void check(Player player, Item bracelet) {
        String ether;
        int etherAmount = AttributeExtensions.getCharges(bracelet);
        if (etherAmount == 0)
            ether = "0.0%, 0 ether";
        else
            ether = NumberUtils.formatOnePlace(((double) etherAmount / MAX_CHARGES) * 100D) + "%, " + NumberUtils.formatNumber(etherAmount) + " ether";
        player.sendMessage("Revenant ether: " + Color.DARK_GREEN.wrap(ether));
    }

    public static void charge(Player player, Item bracelet, Item etherItem) {
        int etherAmount = AttributeExtensions.getCharges(bracelet);
        int allowedAmount = MAX_CHARGES - etherAmount;
        if (allowedAmount == 0) {
            player.sendMessage("The bracelet can't hold anymore ether.");
            return;
        }
        int addAmount = Math.min(allowedAmount, etherItem.getAmount());
        etherItem.incrementAmount(-addAmount);
        AttributeExtensions.setCharges(bracelet, etherAmount + addAmount);
        bracelet.setId(CHARGED);
        check(player, bracelet);
    }

    private static void uncharge(Player player, Item bracelet) {
        player.dialogue(new YesNoDialogue("Are you sure you want to uncharge it?", "If you uncharge the bracelet, all the ether will be lost!", bracelet, () -> {
            int added = player.getInventory().add(REVENANT_ETHER, AttributeExtensions.getCharges(bracelet));
            if (added > 0) {
                AttributeExtensions.setCharges(bracelet, 0);
                bracelet.setId(UNCHARGED);
            } else {
                player.sendMessage("You do not have enough space in your inventory to do this.");
            }
        }));
    }

    public static void consumeCharge(Player player, Item item) {
        AttributeExtensions.deincrementCharges(item, 1);
        if (AttributeExtensions.getCharges(item) == 0) {
            player.sendMessage(Color.RED.wrap("Your bracelet has ran out of charges!"));
            item.setId(UNCHARGED);
        }
    }

    private static void dismantle(Player player, Item bracelet) {
        player.dialogue(
                new YesNoDialogue("Are you sure you want to dismantle it?", "The item will be destroyed and you will receive 250 revenant ethers.", bracelet, () -> {
                    bracelet.remove();
                    player.getInventory().add(REVENANT_ETHER, 250);
                    player.dialogue(new ItemDialogue().one(REVENANT_ETHER, "You dismantle your bracelet and collect 250 revenant ethers."));
                })
        );
    }

    private static boolean isToggled(Player pKiller, Item item) {
        if(!wearingBraceletOfEtherum(pKiller))
            return false;
        return item.getId() == REVENANT_ETHER && pKiller.autoCollectEther;
    }

    private static boolean wearingBraceletOfEtherum(Player player) {
        return player.getEquipment().hasId(UNCHARGED) || player.getEquipment().hasId(CHARGED);
    }

    public static boolean handleEthereumDrop(Player pKiller, Item item) {
        //Check if our player has a ring of wealth
        boolean braceletOfEthereum = isToggled(pKiller, item);
        if(RingOfWealth.check(pKiller, item) || braceletOfEthereum) {
            if (braceletOfEthereum) {
                Item brace = pKiller.getEquipment().get(Equipment.SLOT_HANDS);
                charge(pKiller, brace, item);
            } else {
                pKiller.getInventory().addOrDrop(item);
            }
            return true;
        }
        return false;
    }
}
