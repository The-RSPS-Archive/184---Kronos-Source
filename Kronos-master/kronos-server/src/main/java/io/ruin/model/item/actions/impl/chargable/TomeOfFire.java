package io.ruin.model.item.actions.impl.chargable;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;

public class TomeOfFire {

    private static final int UNCHARGED = 20716;
    private static final int CHARGED = 20714;
    private static final int BURNT_PAGE = 20718;
    private static final int MAX_CHARGES = 20000;

    static {
        ItemAction.registerEquipment(CHARGED, "check", TomeOfFire::check);
        ItemAction.registerInventory(CHARGED, "check", TomeOfFire::check);

        ItemAction.registerInventory(CHARGED, "Add or Remove pages", TomeOfFire::addOrRemovePages);
        ItemAction.registerEquipment(CHARGED, "Add or Remove pages", TomeOfFire::addOrRemovePages);

        ItemAction.registerInventory(UNCHARGED, "Add pages", TomeOfFire::addPages);
        ItemAction.registerEquipment(UNCHARGED, "Add pages", TomeOfFire::addPages);

        ItemItemAction.register(CHARGED, BURNT_PAGE, (player, primary, secondary) -> addPages(player, primary));
        ItemItemAction.register(UNCHARGED, BURNT_PAGE, (player, primary, secondary) -> addPages(player, primary));
    }

    private static void addOrRemovePages(Player player, Item primary) {
        player.dialogue(new OptionsDialogue(
                new Option("Add all pages.", () -> addPages(player, primary)),
                new Option("Remove pages.", () -> pagesToRemove(player, primary)))
        );
    }

    private static void pagesToRemove(Player player, Item primary) {
        player.dialogue(new OptionsDialogue(
                new Option("Remove one page.", () -> removePages(player, primary, 1, false)),
                new Option("Remove X pages.", () -> player.integerInput("Remove how many pages? (0-" + AttributeExtensions.getCharges(primary) / 20 + ")", pagesToAdd -> removePages(player, primary, pagesToAdd, false))),
                new Option("Empty book.", () -> removePages(player, primary, MAX_CHARGES, true))
        ));
    }

    private static void addPages(Player player, Item tomeOfFire) {
        int charges = AttributeExtensions.getCharges(tomeOfFire);
        int allowedAmount = MAX_CHARGES - charges;
        if (allowedAmount == 0) {
            player.sendMessage("Your tome of fire can't hold anymore burnt pages.");
            return;
        }
        Item burntPage = player.getInventory().findItem(BURNT_PAGE);
        if (burntPage == null) {
            player.sendMessage("You have no pages to add to your tome.");
            return;
        }
        int addAmount = Math.min(allowedAmount, burntPage.getAmount() * 20);
        tomeOfFire.putAttribute(AttributeTypes.CHARGES, charges + addAmount);
        burntPage.incrementAmount(-addAmount / 20);
        tomeOfFire.setId(CHARGED);
        check(player, tomeOfFire);
    }

    private static void removePages(Player player, Item tomeOfFire, int pagesToRemove, boolean emptyBook) {
        int charges = AttributeExtensions.getCharges(tomeOfFire);
        if (charges < 20) {
            player.sendMessage("Your Tome of fire doesn't have any pages to remove.");
            return;
        }
        if (player.getInventory().isFull() && player.getInventory().count(BURNT_PAGE) == 0) {
            player.sendMessage("You need at least 1 free inventory space to do this.");
            return;
        }
        int toRemove = pagesToRemove;
        if(pagesToRemove > charges / 20)
            toRemove = charges / 20;
        while (pagesToRemove-- > 0) {
            if (tomeOfFire.getAttributeInt(AttributeTypes.CHARGES) < 20)
                break;
            tomeOfFire.putAttribute(AttributeTypes.CHARGES, tomeOfFire.getAttributeInt(AttributeTypes.CHARGES) - 20);
            player.getInventory().add(BURNT_PAGE, 1);
            if (tomeOfFire.getAttributeInt(AttributeTypes.CHARGES) <= 0)
                tomeOfFire.setId(UNCHARGED);
        }
        if (emptyBook)
            player.sendMessage("You empty your book of pages.");
        else if (toRemove == 1)
            player.sendMessage("You remove a page from the book.");
        else
            player.sendMessage("You remove " + toRemove + " pages from the book.");
    }

    private static void check(Player player, Item tomeOfFire) {
        int charges = AttributeExtensions.getCharges(tomeOfFire);
        player.sendMessage("Your tome currently holds " + (charges) + " charge" + (charges <= 1 ? "" : "s") + ".");
    }

    public static boolean consumeCharge(Player player) {
        Item item = player.getEquipment().findItem(CHARGED);
        if(item == null)
            return false;
        int charges = AttributeExtensions.getCharges(item);
        item.putAttribute(AttributeTypes.CHARGES, charges - 1);
        if (charges == 0) {
            player.sendMessage(Color.RED.wrap("Your tome has ran out of charges!"));
            item.setId(UNCHARGED);
        }
        return true;
    }

}
