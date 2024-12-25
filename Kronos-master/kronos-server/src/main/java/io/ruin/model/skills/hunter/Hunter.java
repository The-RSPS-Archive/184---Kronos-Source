package io.ruin.model.skills.hunter;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Region;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.ground.GroundItemAction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.hunter.creature.Creature;
import io.ruin.model.skills.hunter.traps.Trap;
import io.ruin.model.skills.hunter.traps.TrapType;
import io.ruin.model.stat.StatType;

public class Hunter {
    public static boolean canPlaceTrap(Player player, TrapType type) {
        if (!player.getStats().check(StatType.Hunter, type.getLevelReq(), "place this trap")) {
            return false;
        }
        if (player.traps.size() >= getMaxTraps(player)) {
            player.sendMessage("You can only place up to " + getMaxTraps(player) + " at your current Hunter level.");
            return false;
        }
        Region region  = player.getPosition().getRegion();
        if (region.id != 15148 && !Tile.allowObjectPlacement(player.getPosition())) {
            player.sendMessage("You can't place a trap here.");
            return false;
        }
        return true;
    }

    public static int getMaxTraps(Player player) {
        int level = Math.min(99, player.getStats().get(StatType.Hunter).currentLevel);
        return 1 + (level / 20);
    }

    public static void placeItemTrap(Player player, TrapType type, Item item) {
        if (!canPlaceTrap(player, type))
            return;
        player.startEvent(event -> {
            player.lock();
            player.animate(type.getPlaceAnimation());
            item.remove();
            GroundItem groundItem = new GroundItem(item.getId(), 1)
                    .owner(player)
                    .position(player.getPosition())
                    .spawn();
            event.delay(2);
            player.resetAnimation();
            if(!groundItem.isRemoved()) { //item should never be removed but this is just a safe check regardless
                groundItem.remove();
                GameObject obj = GameObject.spawn(type.getActiveObjectId(), player.getAbsX(), player.getAbsY(), player.getHeight(), 10, 0);
                Trap trap = new Trap(player, type, obj);
                player.traps.add(trap);
                type.onPlace(player, obj);
                player.getRouteFinder().routeSelf();
                addTimeoutEvent(player, trap);
                event.delay(1);
            }
            player.unlock();
        });
    }

    public static void placeGroundItemTrap(Player player, TrapType type, GroundItem groundItem) { // "lay" option on ground item
        if (!canPlaceTrap(player, type))
            return;
        player.startEvent(event -> {
            player.lock();
            player.animate(type.getPlaceAnimation());
            event.delay(2); // todo check if traps need different delays
            player.resetAnimation();
            if(!groundItem.isRemoved()) { //item could be removed, so this check is important
                groundItem.remove();
                GameObject obj = GameObject.spawn(type.getActiveObjectId(), player.getAbsX(), player.getAbsY(), player.getHeight(), 10, 0);
                Trap trap = new Trap(player, type, obj);
                player.traps.add(trap);
                type.onPlace(player, obj);
                player.getRouteFinder().routeSelf();
                addTimeoutEvent(player, trap);
                event.delay(1);
            }
            player.unlock();
        });
    }

    public static void dismantleTrap(Player player, GameObject obj) {
        if (!isOwner(player, obj)) {
            player.sendMessage("This isn't your trap.");
            return;
        }
        if (obj.trap == null) {
            destroyTrap(obj);
            return;
        }
        if (!player.getInventory().hasRoomFor(obj.trap.getTrapType().getItemId())) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        if (obj.trap.isBusy()) {
            return;
        }
        player.startEvent(event -> {
            player.lock();
            player.animate(obj.trap.getTrapType().getDismantleAnimation());
            event.delay(2);
            if(!obj.isRemoved()) {
                player.getInventory().add(obj.trap.getTrapType().getItemId(), 1);
                obj.trap.getTrapType().onRemove(player, obj);
                destroyTrap(obj);
            }
            player.unlock();
        });
    }

    public static void destroyTrap(GameObject obj) {
        if (!obj.isRemoved())
            obj.remove();
        if (obj.trap != null) {
            if (obj.trap.getOwner() != null)
                obj.trap.getOwner().traps.remove(obj.trap);
            obj.trap.setRemoved(true);
            obj.trap = null;
        }
    }

    public static boolean isOwner(Player player, GameObject obj) {
        if (obj.trap == null)
            return true;
        return obj.trap.getOwner() == player;
    }

    public static void registerTrap(TrapType type, boolean registerItemActions) {
        if (registerItemActions) {
            ItemAction.registerInventory(type.getItemId(), 1, (player, item) -> placeItemTrap(player, type, item));
            GroundItemAction.register(type.getItemId(), "lay", (player, groundItem, distance) -> placeGroundItemTrap(player, type, groundItem)); // todo probably need to walk on top
        }
        ObjectAction.register(type.getActiveObjectId(), "dismantle", Hunter::dismantleTrap);
        ObjectAction.register(type.getFailedObjectId(), "dismantle", Hunter::dismantleTrap);
        ObjectAction.register(type.getActiveObjectId(), "reset", Hunter::dismantleTrap);
        ObjectAction.register(type.getFailedObjectId(), "reset", Hunter::dismantleTrap);
        for (int id : type.getSuccessObjects())
            ObjectAction.register(id, 1, Hunter::checkTrap);
    }

    public static void checkTrap(Player player, GameObject obj) {
        if (!isOwner(player, obj)) {
            player.sendMessage("This isn't your trap.");
            return;
        }
        if (obj.trap == null || obj.trap.getTrappedCreature() == null) {
            dismantleTrap(player, obj);
            return;
        }
        obj.trap.getTrappedCreature().check(player, obj);
    }


    public static void registerCreature(Creature creature) {
        creature.register();
    }

    public static void addTimeoutEvent(Player player, Trap trap) {
        player.addEvent(event -> {
            event.delay(100);
            if (!trap.isRemoved() && trap.getOwner() != null && trap.getObject() != null && !trap.getObject().isRemoved() && trap.getOwner().traps.contains(trap)) {
                trap.getTrapType().collapse(player, trap, true);
                trap.setRemoved(true);
            }
        });
    }

    public static void collapseAll(Player player) {
        player.traps.forEach(trap -> trap.getTrapType().collapse(player, trap, false));
        player.traps.removeIf(trap -> trap.getObject() == null || trap.getObject().isRemoved());
    }
}
