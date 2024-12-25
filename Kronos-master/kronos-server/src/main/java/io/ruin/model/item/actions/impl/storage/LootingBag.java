package io.ruin.model.item.actions.impl.storage;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.map.ground.GroundItem;

public class LootingBag extends ItemContainer {

    public static final int CLOSED_LOOTING_BAG = 11941;

    public static final int OPENED_LOOTING_BAG = 22586;

    private boolean deposit;

    {
        /**
         * Fixes issue with bank not recognizing looting bag on login.
         */
        sendAll = true;
    }

    private void openCheck() {
        deposit = false;
        player.openInterface(InterfaceType.INVENTORY_OVERLAY, Interface.LOOTING_BAG);
        player.getPacketSender().sendAccessMask(Interface.LOOTING_BAG, 5, 0, 27, 1024);
        player.getPacketSender().sendClientScript(495, "s1", "Looting bag", 0);
        sendAll = true;
    }

    private void openDeposit() {
        deposit = true;
        player.openInterface(InterfaceType.INVENTORY_OVERLAY, Interface.LOOTING_BAG);
        player.getPacketSender().sendAccessMask(Interface.LOOTING_BAG, 5, 0, 27, 542);
        player.getPacketSender().sendClientScript(495, "s1", "Add to bag", 1);
        player.getPacketSender().setHidden(Interface.LOOTING_BAG, 7, true);
    }

    private void deposit(Item item, int amount) {
        if(item.getId() == CLOSED_LOOTING_BAG) {
            player.sendMessage("You may be surprised to learn that bagception is not permitted.");
            return;
        }
        if(player.wildernessLevel == 0) {
            player.sendMessage("You can't put items in the looting bag unless you're in the Wilderness.");
            return;
        }
        if(!item.getDef().tradeable || item.getAttributeHash() != 0) {
            player.sendMessage("Only tradeable items can be put in the bag.");
            return;
        }
        if(item.move(item.getId(), amount, this) == 0)
            player.sendMessage("Not enough space in your looting bag.");
    }

    private static int[] LOOTING_BAGS = {
            CLOSED_LOOTING_BAG, OPENED_LOOTING_BAG
    };

    public static void destroy(Player player, Item item) {
        if(player.wildernessLevel > 0) {
            player.sendMessage("You can't destroy your looting bag inside the wilderness!");
            return;
        }
        player.dialogue(
            new YesNoDialogue("Are you sure you want to do this?", "This Looting bag will be destroyed and all stored items will be dropped.", item, () -> {
                item.remove();
                player.sendMessage("You destroy your looting bag!");
                for(Item i : player.getLootingBag().getItems()) {
                    if(i != null) {
                        new GroundItem(i).owner(player).droppedBy(player).position(player.getPosition()).spawnPrivate();
                        i.remove();
                    }
                }
            })
        );
    }

    static {
        InterfaceHandler.register(Interface.LOOTING_BAG, h -> {
            h.actions[2] = (SimpleAction) p -> {
                p.closeInterface(InterfaceType.INVENTORY_OVERLAY);
            };
            h.actions[5] = (DefaultAction) (player, option, slot, itemId) -> {
                LootingBag bag = player.getLootingBag();
                if(!bag.deposit) {
                    Item item = bag.get(slot, itemId);
                    if(item == null)
                        return;
                    item.examine(player);
                } else {
                    Item item = player.getInventory().get(slot, itemId);
                    if(item == null)
                        return;
                    if(option == 1)
                        bag.deposit(item, 1);
                    else if(option == 2)
                        bag.deposit(item, 5);
                    else if(option == 3)
                        bag.deposit(item, Integer.MAX_VALUE);
                    else if(option == 4)
                        player.integerInput("Enter amount:", amt -> bag.deposit(item, amt));
                    else
                        item.examine(player);
                }
            };
        });

        for(int LOOTING_BAG : LOOTING_BAGS) {
            ItemItemAction.register(LOOTING_BAG, (player, lootingBag, itemUsed) -> {
                if(!player.getInventory().hasMultiple(itemUsed.getId())) {
                    player.getLootingBag().deposit(itemUsed, 1);
                    return;
                }
                player.dialogue(new OptionsDialogue(
                        "How many would you like to deposit?",
                        new Option("One", () -> player.getLootingBag().deposit(itemUsed, 1)),
                        new Option("Five", () -> player.getLootingBag().deposit(itemUsed, 5)),
                        new Option("X", () -> player.integerInput("How many would you like to deposit?", amt -> player.getLootingBag().deposit(itemUsed, amt))),
                        new Option("All", () -> player.getLootingBag().deposit(itemUsed, Integer.MAX_VALUE))
                ));
            });

            ItemAction.registerInventory(LOOTING_BAG, "check", (player, item) -> player.getLootingBag().openCheck());
            ItemAction.registerInventory(LOOTING_BAG, "deposit", (player, item) -> player.getLootingBag().openDeposit());
            ItemAction.registerInventory(LOOTING_BAG, "destroy", LootingBag::destroy);
        }

        ItemAction.registerInventory(CLOSED_LOOTING_BAG, "open", (player, item) -> {
            item.setId(OPENED_LOOTING_BAG);
            player.sendFilteredMessage("You open your looting bag, ready to fill it.");
        });

        ItemAction.registerInventory(OPENED_LOOTING_BAG, "close", (player, item) -> {
            item.setId(CLOSED_LOOTING_BAG);
            player.sendFilteredMessage("You close your looting bag.");
        });
    }

}
