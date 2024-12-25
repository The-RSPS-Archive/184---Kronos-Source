package io.ruin.model.skills.smithing;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.containers.Equipment;

public class RingOfForging {

    private static final int MAX_CHARGES = 60;
    private static final double ACTIVATE_CHANCE = 0.2;
    private static final int RING_ID = 2568;

    private static int getChargeCost(SmithBar bar) {
        switch (bar) {
            case MITHRIL:
                return 2;
            case ADAMANT:
                return 4;
            case RUNITE:
                return 10;
            default:
                return 1;
        }
    }

    private static void spendCharge(Player player, SmithBar bar, Item item) {
        AttributeExtensions.deincrementCharges(item, getChargeCost(bar));
        if (AttributeExtensions.getCharges(item) >= MAX_CHARGES) {
            item.remove();
            player.sendMessage("<col=ff0000>Your ring of forging has shattered!");
        }
    }

    public static boolean onSmith(Player player, SmithBar barType, SmithItem item) {
        if (item.barReq == 1)
            return false;
        if (!player.getEquipment().hasId(RING_ID))
            return false;
        if (Random.get() > ACTIVATE_CHANCE)
            return false;
        spendCharge(player, barType, player.getEquipment().get(Equipment.SLOT_RING));
        player.getInventory().add(barType.itemId, 1);
        player.sendFilteredMessage(Color.GREEN.wrap("Your ring of forging saves a bar!"));
        return true;
    }

    private static void check(Player player, Item item) {
        int charge = MAX_CHARGES - AttributeExtensions.getCharges(item);
        player.sendMessage("This ring of forging has " + charge + " charge" + (charge > 1 ? "s" : "") + " remaining.");
    }

    static {
        ItemAction.registerEquipment(RING_ID, 2,  RingOfForging::check);
    }

}
