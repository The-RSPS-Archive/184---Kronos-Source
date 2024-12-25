package io.ruin.model.inter.handlers;

import io.ruin.cache.ItemDef;
import io.ruin.model.activities.wilderness.BloodyChest;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.*;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.process.CoreWorker;
import io.ruin.process.event.EventWorker;
import io.ruin.process.task.TaskWorker;
import io.ruin.services.Loggers;
import io.ruin.utility.Misc;

import java.util.List;

public class TabInventory {

    static {
        InterfaceHandler.register(Interface.INVENTORY, h -> {
            h.actions[0] = new InterfaceAction() {
                @Override
                public void handleClick(Player player, int option, int slot, int itemId) {
                    click(player, option, slot, itemId);
                }
                @Override
                public void handleDrag(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
                    drag(player, fromSlot, toSlot);
                }
                @Override
                public void handleOnInterface(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
                    onInterface(player, fromSlot, fromItemId, toSlot, toItemId);
                }
                @Override
                public void handleOnGroundItem(Player player, int slot, int itemId, GroundItem groundItem, int distance) {
                    onGroundItem(player, slot, itemId, groundItem, distance);
                }
                @Override
                public void handleOnEntity(Player player, Entity entity, int slot, int itemId) {
                    onEntity(player, entity, slot, itemId);
                }
                @Override
                public void handleOnObject(Player player, int slot, int itemId, GameObject obj) {
                    onObject(player, slot, itemId, obj);
                }
            };
        });
    }

    private static void click(Player player, int option, int slot, int itemId) {
        if(player.isLocked())
            return;
        Item item = player.getInventory().get(slot, itemId);
        if(item == null)
            return;
        if(option == 10) {
            item.examine(player);
            return;
        }
        int i = option - 1;
        if(i < 0 || i >= 5)
            return;
        ItemDef def = item.getDef();
        ItemAction[] actions = def.inventoryActions;
        if(actions != null) {
            ItemAction action = actions[i];
            if(action != null) {
                action.handle(player, item);
                return;
            }
        }
        if(option == def.dropOption) {
            Tile tile = player.getPosition().getTile();
            if(tile != null && !tile.allowDrop) {
                player.sendMessage("You can't drop items here.");
                return;
            }
            if (player.jailOresAssigned > 0) {
                player.sendMessage("You cannot drop items while in jail.");
                return;
            }
            if(player.getDuel().stage >= 4) {
                player.sendMessage("You can't drop items in a duel.");
                return;
            }
            if(player.joinedTournament) {
                player.sendMessage("You can't drop items while you're signed up for a tournament.");
                return;
            }
            if (player.isInOwnHouse() && player.getCurrentHouse().isBuildingMode()) {
                player.sendMessage("You can't drop items while in building mode.");
                return;
            }
            if(player.supplyChestRestricted) {
                player.sendMessage("The power of the supply chest prevents you from dropping items!");
                return;
            }
            if(player.wildernessLevel > 0 && BloodyChest.hasBloodyKey(player)) {
                player.sendMessage("The power of your bloody key prevents you from dropping items!");
            }
            item.remove();
            player.resetActions(true, false, true);
            player.privateSound(2739);
            if(player.wildernessLevel > 0) {
                /**
                 * Dropping in the wilderness
                 */
                if(def.consumable || !def.tradeable)
                    new GroundItem(item).owner(player).droppedBy(player).position(player.getPosition()).spawnPrivate();
                else
                    new GroundItem(item).owner(player).droppedBy(player).position(player.getPosition()).spawnPublic();
            } else {
                /**
                 * Player is in a raid, so make items appear instantly.
                 */
                if (player.raidsParty != null) {
                    new GroundItem(item).owner(player).droppedBy(player).position(player.getPosition()).spawnPublic();
                } else {
                    /**
                     * Regular dropping
                     */
                    new GroundItem(item).owner(player).droppedBy(player).position(player.getPosition()).spawn();
                }
            }
            Loggers.logDrop(player.getUserId(), player.getName(), player.getIp(), item.getId(), item.getAmount(), player.getAbsX(), player.getAbsY(), player.getHeight());
            return;
        }
        if (option == def.equipOption) {
            player.getEquipment().equip(item);
            player.resetActions(false, player.getMovement().following != null, true);
            return;
        }
        item.examine(player);
    }

    public static void drag(Player player, int fromSlot, int toSlot) {
        if(fromSlot == toSlot)
            return;
        if(!player.getInventory().validateSlots(fromSlot, toSlot)) {
            return;
        }
        Item fromItem = player.getInventory().get(fromSlot);
        if(fromItem == null) {
            player.getInventory().update(toSlot);
            player.getInventory().update(fromSlot);
            return;
        }
        player.closeChatbox(false);
        Item toItem = player.getInventory().get(toSlot);
        if(toItem == null) {
            player.getInventory().set(fromSlot, null);
            player.getInventory().set(toSlot, fromItem);
        } else {
            player.getInventory().set(toSlot, fromItem);
            player.getInventory().set(fromSlot, toItem);
        }
    }

    private static void onInterface(Player player, int fromSlot, int fromItemId, int toSlot, int toItemId) {
        Item fromItem = player.getInventory().get(fromSlot, fromItemId);
        if(fromItem == null)
            return;
        Item toItem = player.getInventory().get(toSlot, toItemId);
        if(toItem == null)
            return;
        ItemItemAction.handleAction(player, fromItem, toItem);
    }

    private static void onGroundItem(Player player, int slot, int itemId, GroundItem groundItem, int distance) {
        Item item = player.getInventory().get(slot, itemId);
        if(item == null)
            return;
        ItemGroundItemAction.handleAction(player, item, groundItem, distance);
    }

    private static void onEntity(Player player, Entity entity, int slot, int itemId) {
        Item item = player.getInventory().get(slot, itemId);
        if(item == null)
            return;
        if(entity.npc != null)
            ItemNPCAction.handleAction(player, item, entity.npc);
        else
            ItemPlayerAction.handleAction(player, item, entity.player);
    }

    private static void onObject(Player player, int slot, int itemId, GameObject obj) {
        Item item = player.getInventory().get(slot, itemId);
        if(item == null)
            return;
        ItemObjectAction.handleAction(player, item, obj);
    }

}