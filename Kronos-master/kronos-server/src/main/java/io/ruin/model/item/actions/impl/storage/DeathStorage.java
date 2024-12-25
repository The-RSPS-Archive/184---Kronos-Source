package io.ruin.model.item.actions.impl.storage;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.model.combat.Killer;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.IKOD;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.inter.utils.Unlock;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public class DeathStorage extends ItemContainer {


    static {
        // death storage chest -- maybe find something that fits the area better?
        ObjectAction.register(31675, 1, (p, obj) -> {
            if (p.getDeathStorage().isEmpty()) {
                p.sendMessage("The chest is currently empty. Should you die, you may retrieve your lost items from it.");
            } else {
                p.getDeathStorage().open();
                p.getPacketSender().resetHintIcon(false);
            }
        });

    }

    static {
        InterfaceHandler.register(Interface.DEATH_STORAGE, h -> {
            h.actions[3] = (DefaultAction) (p, option, slot, itemId) -> {
                if (option == 2)
                    p.getDeathStorage().take(slot);
                else if (option == 9)
                    p.getDeathStorage().value(slot);
                else
                    p.getDeathStorage().examine(slot);
            };
            h.actions[6] = (SimpleAction) p -> {
                if (p.getDeathStorage().isUnlocked())
                    p.getDeathStorage().takeAll();
                else
                    p.getDeathStorage().unlock();
            };
            h.actions[8] = (SimpleAction) p -> p.getDeathStorage().discardAll();
            h.closedAction = (p, integer) -> {
                if (p.getDeathStorage().isUnlocked() && !p.getDeathStorage().isEmpty()) {
                    p.sendMessage(Color.RED.wrap("WARNING:") + " Should you die again, all items currently in death storage will be gone forever!");
                }
            };
        });
    }

    @Expose private boolean unlocked = false;

    private void discardAll() {
        player.dialogue(new MessageDialogue("Are you sure you want to discard all items?<br><br>They will be lost forever!"),
                new OptionsDialogue("Discard all items?",
                        new Option("Yes", () -> {
                            reset();
                            player.closeInterface(InterfaceType.MAIN);
                            player.sendMessage("The storage has been cleared.");
                        }),
                        new Option("No", this::open)
                )
        );
    }

    private void take(int slot) {
        Item item = get(slot);
        if (item == null)
            return;
        if (!unlocked && item.getId() != BLOOD_MONEY && item.getId() != COINS_995) {
            player.sendMessage("You must first unlock your items. Click the padlock icon to pay the fee.");
            return;
        }
        if (item.move(item.getId(), item.getAmount(), player.getInventory()) == 0)
            player.sendMessage("Not enough space in your inventory.");
        sendUpdates();
    }

    private void value(int slot) {
        Item item = get(slot);
        if (item == null)
            return;
        player.sendMessage(item.getDef().name + ": " + NumberUtils.formatNumber(item.getDef().value * item.getAmount()) + " coins.");
    }

    private void examine(int slot) {
        Item item = get(slot);
        if (item == null)
            return;
        item.examine(player);
    }

    private void unlock() {
        if (unlocked)
            return;
        /*
         * Donator Benefit: [Free death chest recovery]
         */
        if (player.isRuby()) {
            unlocked = true;
            update();
            player.sendMessage("Your donator rank unlocks the death chest.");
            return;
        }

        Item cost = getUnlockCost();
        if (cost == null) {
            unlocked = true;
            update();
            return;
        }
        if (!player.getInventory().contains(cost, true)) {
            player.sendMessage("You do not have the required items to pay the unlock fee.");
            return;
        }
        player.getInventory().remove(cost.getId(), cost.getAmount(), true, null);
        unlocked = true;
        update();
        player.sendMessage("You may now collect your items.");
    }

    private void takeAll() {
        for (Item item : getItems()) {
            if (item != null) {
                if (item.move(item.getId(), item.getAmount(), player.getInventory()) != item.getAmount()) {
                    break;
                }
            }
        }
        sendUpdates();
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void open() {
        send(player);
        player.openInterface(InterfaceType.MAIN, Interface.DEATH_STORAGE);
        update();
    }

    private void update() {
        Config.DEATH_STORAGE_TYPE.set(player, unlocked ? 4 : 3);
        new Unlock(602, 3).children(0, 50).unlockMultiple(player, 1,8,9);
        Item cost = getUnlockCost();
        if (!unlocked && cost != null) {
            player.addEvent(event -> {
                event.delay(1); // this is required because the interface runs a script that changes the string on the child we want to modify,
                //the delay shouldn't be noticeable because the string is only displayed when the player hovers over the button
                //but this could be avoided by modifying the interface/script
                player.getPacketSender().sendString(602, 11, "Fee to unlock:<br><col=ffffff>" + NumberUtils.formatNumber(cost.getAmount()) + " x " + cost.getDef().name);
            });
        }
    }

    private Item getUnlockCost() {
        if (player.getStats().totalLevel < 500)
            return null;
        return new Item(COINS_995, (int) (200000 + (((player.getStats().totalLevel - 500) / (2277d - 500)) * 800000)));

    }

    public void reset() {
        clear();
        unlocked = getUnlockCost() == null;
    }

    public void death(Killer killer) {
        reset();
        IKOD.forLostItem(player, killer, this::add);
        if (!isEmpty()) {
            if (!player.edgeHome) {
                player.getPacketSender().sendHintIcon(2013, 3580);
            } else {
                player.getPacketSender().sendHintIcon(3090, 3492);
            }
        }
    }
}
