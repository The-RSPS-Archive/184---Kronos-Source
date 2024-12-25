package io.ruin.model.item.actions.impl.storage;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.skills.magic.rune.Rune;

public class RunePouch extends ItemContainer {

    public static final int RUNE_POUCH = 12791;

    private void open() {
        if(player.getCombat().isDefending(6) || player.getCombat().isAttacking(6)) {
            player.sendFilteredMessage("You can't do this in combat.");
            return;
        }
        player.getPacketSender().sendAccessMask(Interface.RUNE_POUCH, 4, 0, 2, 1054 | 1 << 17 | 1 << 20);
        player.getPacketSender().sendAccessMask(Interface.RUNE_POUCH, 8, 0, 27, 1054);
        player.openInterface(InterfaceType.MAIN, Interface.RUNE_POUCH);
    }

    public void deposit(Item item, int amount) {
        if(amount <= 0)
            return;
        if(amount > item.getAmount())
            amount = item.getAmount();
        Rune rune = item.getDef().rune;
        if(rune == null) {
            player.sendMessage("You can't store this item in your pouch.");
            return;
        }
        int freeSlot = -1;
        Item stack = null;
        for(int i = 0; i < items.length; i++) {
            Item stored = items[i];
            if(stored == null) {
                if(freeSlot == -1)
                    freeSlot = i;
                continue;
            }
            if(item.getId() == stored.getId()) {
                stack = stored;
                break;
            }
        }
        if(stack != null) {
            amount = Math.min(amount, 16000 - stack.getAmount());
            stack.incrementAmount(amount);
            item.incrementAmount(-amount);
            return;
        }
        if(freeSlot != -1) {
            amount = Math.min(amount, 16000);
            set(freeSlot, new Item(item.getId(), amount));
            item.incrementAmount(-amount);
            return;
        }
        player.sendMessage("Not enough space in your pouch.");
    }

    private void withdraw(Item item, int amount) {
        if(item.move(item.getId(), amount, player.getInventory()) > 0)
            return;
        player.sendMessage("Not enough space in your inventory.");
    }

    public void empty(boolean bank) {
        if(itemCount == 0) {
            if(!bank) player.sendMessage("Your rune pouch is already empty.");
            return;
        }
        for(Item item : getItems()) {
            if(item != null)
                item.move(item.getId(), item.getAmount(), bank ? player.getBank() : player.getInventory());
        }
        if(itemCount == 0) {
            if(!bank) player.sendMessage("You empty your rune pouch.");
            return;
        }
        player.sendMessage("Not enough space in your inventory.");
    }

    @Override
    public boolean sendUpdates() {
        if(updatedCount == 0)
            return false;
        if(updatedSlots[0])
            update(0, Config.RUNE_POUCH_LEFT_TYPE, Config.RUNE_POUCH_LEFT_AMOUNT);
        if(updatedSlots[1])
            update(1, Config.RUNE_POUCH_MIDDLE_TYPE, Config.RUNE_POUCH_MIDDLE_AMOUNT);
        if(updatedSlots[2])
            update(2, Config.RUNE_POUCH_RIGHT_TYPE, Config.RUNE_POUCH_RIGHT_AMOUNT);
        updatedCount = 0;
        return true;
    }

    private void update(int slot, Config typeConfig, Config amountConfig) {
        Item item = items[slot];
        if(item == null) {
            typeConfig.set(player, 0);
            amountConfig.set(player, 0);
        } else {
            typeConfig.set(player, item.getDef().rune.ordinal() + 1);
            amountConfig.set(player, item.getAmount());
        }
        updatedSlots[slot] = false;
    }

    public static void destroy(Player player, Item item) {
        player.dialogue(
                new YesNoDialogue("Are you sure you want to do this?", "This Rune Pouch will be destroyed and all stored items will be dropped.", item, () -> {
                    item.remove();
                    player.sendMessage("You destroy your Rune Pouch!");
                    for(Item i : player.getRunePouch().getItems()) {
                        if(i != null) {
                            new GroundItem(i).owner(player).droppedBy(player).position(player.getPosition()).spawnPrivate();
                            i.remove();
                        }
                    }
                })
        );
    }

    static {
        ItemAction.registerInventory(RUNE_POUCH, "open", (player, item) -> player.getRunePouch().open());
        ItemAction.registerInventory(RUNE_POUCH, "empty", (player, item) -> player.getRunePouch().empty(false));
        ItemAction.registerInventory(RUNE_POUCH, "destroy", RunePouch::destroy);
        for(Rune rune : Rune.values())
            ItemItemAction.register(RUNE_POUCH, rune.getId(), (player, pouchItem, runeItem) -> player.getRunePouch().deposit(runeItem, runeItem.getAmount()));
        InterfaceHandler.register(Interface.RUNE_POUCH, h -> {
            h.actions[8] = (DefaultAction) (p, option, slot, itemId) -> {
                Item item = p.getInventory().get(slot, itemId);
                if(item == null)
                    return;
                if(option == 1)
                    p.getRunePouch().deposit(item, 1);
                else if(option == 2)
                    p.getRunePouch().deposit(item, 5);
                else if(option == 3)
                    p.getRunePouch().deposit(item, item.getAmount());
                else if(option == 4)
                    p.integerInput("Enter amount:", amt -> p.getRunePouch().deposit(item, amt));
                else
                    item.examine(p);
            };
            h.actions[4] = new InterfaceAction() {

                @Override
                public void handleClick(Player p, int option, int slot, int itemId) {
                    Item item = p.getRunePouch().get(slot, itemId);
                    if (item == null)
                        return;
                    if (option == 1)
                        p.getRunePouch().withdraw(item, 1);
                    else if (option == 2)
                        p.getRunePouch().withdraw(item, 5);
                    else if (option == 3)
                        p.getRunePouch().withdraw(item, item.getAmount());
                    else if (option == 4)
                        p.integerInput("Enter amount:", amt -> p.getRunePouch().withdraw(item, amt));
                    else
                        item.examine(p);
                }

                @Override
                public void handleDrag(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
                    RunePouch pouch = player.getRunePouch();

                    Item from = pouch.get(fromSlot);
                    Item to = pouch.get(toSlot);

                    if (from == null || to == null) {
                        return;
                    }

                    pouch.set(fromSlot, to);
                    pouch.set(toSlot, from);
                }

            };
        });
    }

}