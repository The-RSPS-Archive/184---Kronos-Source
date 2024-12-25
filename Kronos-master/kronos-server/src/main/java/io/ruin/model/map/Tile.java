package io.ruin.model.map;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static io.ruin.model.map.route.RouteFinder.*;

@Slf4j
public class Tile {

    /**
     * Creation
     */

    public final Region region;

    public Tile(Region region) {
        this.region = region;
    }

    public static Tile get(int x, int y, int z) {
        return get(x, y, z, false);
    }

    public static Tile get(Position position) {
        return get(position.getX(), position.getY(), position.getZ(), false);
    }

    public static Tile get(Position position, boolean create) {
        return Region.get(position.getX(), position.getY()).getTile(position.getX(), position.getY(), position.getZ(), create);
    }

    public static Tile get(int x, int y, int z, boolean create) {
        return Region.get(x, y).getTile(x, y, z, create);
    }

    /**
     * Clipping
     */

    public int clipping;

    public int defaultClipping;

    public int projectileClipping; //I know I didn't want to do this either :[

    public void flagUnmovable() {
        clipping |= 0x200000;
    }

    public void unflagUnmovable() {
        clipping &= ~0x200000;
    }

    public void flagDecoration() {
        clipping |= 0x40000;
    }

    public void unflagDecoration() {
        clipping &= ~0x40000;
    }

    public boolean allowEntrance(int mask) {
        return (clipping & mask) == 0;
    }

    public boolean isTileFree() {
        return isFloorFree() && isWallsFree();
    }

    public boolean isTileFreeCheckDecor() {
        return isFloorFree() && isWallsFree() && isFloorFreeCheckDecor();
    }

    public boolean isFloorFreeCheckDecor() {
        return (clipping & (UNMOVABLE_MASK | DECORATION_MASK | OBJECT_MASK)) == 0;
    }

    public boolean isFloorFree() {
        boolean movable = (clipping & UNMOVABLE_MASK) == 0;
        boolean object = (clipping & OBJECT_MASK) == 0;
        return movable && object;
    }

    public boolean isWallsFree() {
        boolean north = (clipping & NORTH_MASK) == 0;
        boolean east = (clipping & EAST_MASK) == 0;
        boolean south = (clipping & SOUTH_MASK) == 0;
        boolean west = (clipping & WEST_MASK) == 0;
        boolean north_east = (clipping & NORTH_EAST_MASK) == 0;
        boolean south_east = (clipping & SOUTH_EAST_MASK) == 0;
        boolean north_west = (clipping & NORTH_WEST_MASK) == 0;
        boolean south_west = (clipping & SOUTH_WEST_MASK) == 0;
        return north && east && south && west && north_east && south_east && north_west && south_west;
    }

    public boolean allowStandardEntrance() {
        return allowEntrance(WEST_MASK)
                || allowEntrance(EAST_MASK)
                || allowEntrance(SOUTH_MASK)
                || allowEntrance(NORTH_MASK);
    }

    /**
     * Active
     */

    private boolean active;

    public void checkActive() {
        boolean active = false;
        if(groundItems != null && groundItems.size() > 0)
            active = true;
        else if(gameObjects != null && gameObjects.size() > 0)
            active = gameObjects.stream().anyMatch(GameObject::isCustom);
        if(this.active == active) {
            /* same active state */
            return;
        }
        if((this.active = active))
            region.activeTiles.add(this);
        else
            region.activeTiles.remove(this);
    }

    /**
     * Game objects
     */

    public ArrayList<GameObject> gameObjects;

    public void addObject(GameObject gameObject) {
        if(gameObjects == null)
            gameObjects = new ArrayList<>(4);
        gameObject.tile = this;
        gameObject.clip(false);
        gameObjects.add(gameObject);
        checkActive();
    }

    public void removeObject(GameObject gameObject) {
        if(gameObjects == null) {
            /* this tile has been destroyed */
            return;
        }
        gameObject.clip(true);
        gameObject.tile = null;
        gameObjects.remove(gameObject);
        checkActive();
    }

    public static GameObject getObject(int id, int x, int y, int z) {
        return getObject(id, x, y, z, -1, -1);
    }

    public static GameObject getObject(int id, int x, int y, int z, int type, int direction) {
        Tile tile = get(x, y, z, false);
        return tile == null ? null : tile.getObject(id, type, direction);
    }

    public GameObject getObject(int id, int type, int direction) {
        if(gameObjects != null) {
            for(GameObject gameObject : gameObjects) {
                if(gameObject != null
                        && (id == -1 || gameObject.id == id)
                        && (type == -1 || gameObject.type == type)
                        && (direction == -1 || gameObject.direction == direction))
                    return gameObject;
            }
        }
        return null;
    }

    /**
     * Ground items
     */

    public ArrayList<GroundItem> groundItems;

    public void addItem(GroundItem groundItem) {
        if(ItemDef.get(groundItem.id).stackable) {
            GroundItem stack = getItem(groundItem.id, groundItem.activeOwner);
            if(stack != null) {
                stack.amount = NumberUtils.intSum(stack.amount, groundItem.amount);
                stack.sendUpdate();
                return;
            }
            if(groundItems == null)
                groundItems = new ArrayList<>(8);
        } else {
            if(groundItems == null)
                groundItems = new ArrayList<>(Math.max(8, groundItem.amount));
            if(groundItem.amount > 1) {
                for(int i = 0; i < (groundItem.amount - 1); i++) {
                    GroundItem newItem = new GroundItem(groundItem.id, 1)
                            .owner(groundItem.originalOwner)
                            .position(groundItem.x, groundItem.y, groundItem.z);
                    newItem.tile = this;
                    groundItems.add(newItem);
                    newItem.sendAdd();
                }
                groundItem.amount = 1;
            }
        }
        groundItem.tile = this;
        groundItems.add(groundItem);
        groundItem.sendAdd();
        checkActive();
    }

    public void removeItem(GroundItem groundItem) {
        if(groundItems == null) {
            /* tile has been destroyed */
            return;
        }
        groundItem.sendRemove();
        groundItem.tile = null;
        groundItems.remove(groundItem);
        checkActive();
    }

    public GroundItem getItem(int id, int ownerId) {
        if(groundItems == null)
            return null;
        for(GroundItem groundItem : groundItems) {
            if(groundItem.id == id && groundItem.activeOwner == ownerId)
                return groundItem;
        }
        return null;
    }

    public GroundItem getPickupItem(int id, int ownerId) {
        if(groundItems == null)
            return null;
        for(GroundItem groundItem : groundItems) {
            if(groundItem.id == id && (groundItem.activeOwner == -1 || groundItem.activeOwner == ownerId))
                return groundItem;
        }
        return null;
    }

    /**
     * Updating
     */

    public void update(Player player) {
        if (gameObjects != null) {
            for (GameObject gameObject : gameObjects) {
                if (gameObject.isCustom())
                    gameObject.send(player);
            }
        }
        if (groundItems != null) {
            for (GroundItem groundItem : groundItems) {
                if (groundItem.activeOwner == -1 || groundItem.activeOwner == player.getUserId()) {
                    groundItem.sendRemove(player);
                    groundItem.sendAdd(player);
                }
            }
        }
    }

    /**
     * Destroy
     */

    public void destroy() {
        if(gameObjects != null) {
            gameObjects.clear();
            gameObjects = null;
        }
        if(groundItems != null) {
            groundItems.clear();
            groundItems = null;
        }
        clipping = 0;
    }

    /**
     * Misc vars
     */

    public Consumer<Player> digAction;

    public boolean multi;

    public boolean roofExists;

    public boolean nearBank;

    public boolean allowDrop = true;

    public int playerCount, npcCount;

    public int wildernessLevel;

    public boolean safePVPInstance = false;

    private List<Consumer<Entity>> triggers;

    public static void occupy(Entity entity) {
        if(entity.occupyingTiles) {
            fill(entity, entity.getLastPosition(), -1);
            entity.occupyingTiles = false;
        }
        if(!entity.isHidden()) {
            if(entity.npc != null && !entity.npc.getDef().occupyTiles)
                return;
            fill(entity, entity.getPosition(), 1);
            entity.occupyingTiles = true;
        }
    }

    private static void fill(Entity entity, Position pos, int increment) {
        int size = entity.getSize();
        int absX = pos.getX();
        int absY = pos.getY();
        int z = pos.getZ();
        for(int x = absX; x < (absX + size); x++) {
            for(int y = absY; y < (absY + size); y++) {
                Tile tile = Tile.get(x, y, z, true);
                if(entity.player != null)
                    tile.playerCount += increment;
                else
                    tile.npcCount += increment;
            }
        }
    }

    public static boolean isOccupied(Entity entity, int stepX, int stepY) {
        int size = entity.getSize();
        int absX = entity.getAbsX();
        int absY = entity.getAbsY();
        int z = entity.getHeight();
        int eastMostX = absX + (size - 1);
        int northMostY = absY + (size - 1);
        for(int x = stepX; x < (stepX + size); x++) {
            for(int y = stepY; y < (stepY + size); y++) {
                if(x >= absX && x <= eastMostX && y >= absY && y <= northMostY) {
                    /* stepping within itself, allow it */
                    continue;
                }
                Tile tile = Tile.get(x, y, z, true);
                if(tile.playerCount > 0 || tile.npcCount > 0)
                    return true;
            }
        }
        return false;
    }

    /**
     * Misc
     */

    public static boolean allowObjectPlacement(Position position) {
        return allowObjectPlacement(position.getX(), position.getY(), position.getZ());
    }

    public static boolean allowObjectPlacement(int x, int y, int z) {
        Tile tile = get(x, y, z);
        if(tile == null)
            return true;
        if(tile.roofExists)
            return false;
        if(tile.gameObjects != null) {
            for(GameObject obj : tile.gameObjects) {
                if(obj.type == 10 || obj.type == 11)
                    return false;
            }
        }
        return true;
    }

    public void addTrigger(Consumer<Entity> trigger) {
        if (triggers == null)
            triggers = new ArrayList<>();
        triggers.add(trigger);
    }

    public void addPlayerTrigger(Consumer<Player> trigger) {
        addTrigger((e) -> {
            if (e instanceof Player)
                trigger.accept(e.player);
        });
    }

    public void checkTriggers(Entity entity) {
        if (triggers == null)
            return;
        triggers.forEach(t -> t.accept(entity));
    }

    public void removeTrigger(Consumer<Entity> trigger) {
        if (triggers == null)
            return;
        triggers.remove(trigger);
    }

    public void clearTriggers() {
        if (triggers == null)
            return;
        triggers.clear();

    }

    public boolean isActive() {
        return active;
    }

}