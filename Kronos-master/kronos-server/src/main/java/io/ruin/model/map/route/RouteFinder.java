package io.ruin.model.map.route;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.cache.ObjectDef;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.shared.Movement;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.ObjectType;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.map.route.types.RouteAbsolute;
import io.ruin.model.map.route.types.RouteEntity;
import io.ruin.model.map.route.types.RouteObject;
import io.ruin.model.map.route.types.RouteRelative;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RouteFinder {

    public static final int OBJECT_MASK = 0x100;

    public static final int UNMOVABLE_MASK = 0x200000;

    public static final int DECORATION_MASK = 0x40000;

    public static final int WEST_MASK = 0x1240108, EAST_MASK = 0x1240180;

    public static final int SOUTH_MASK = 0x1240102, NORTH_MASK = 0x1240120;

    public static final int SOUTH_WEST_MASK = 0x124010e, NORTH_WEST_MASK = 0x1240138;

    public static final int SOUTH_EAST_MASK = 0x1240183, NORTH_EAST_MASK = 0x12401e0;

    /**
     * Separator
     */

    private Entity entity;

    private ClipUtils clipUtils;

    public ClipUtils getClipUtils() {
        return clipUtils;
    }

    public ClipUtils customClipUtils;

    public RouteFinder(Entity entity) {
        this.entity = entity;
        if(entity.npc != null) {
            NPCDef def = entity.npc.getDef();
            if(def.flightClipping) {
                this.clipUtils = ClipUtils.FLIGHT;
                return;
            }
            if(def.swimClipping) {
                this.clipUtils = ClipUtils.SWIM;
                return;
            }
        }
        this.clipUtils = ClipUtils.REGULAR;
    }

    /**
     * Moves entity according to given route.
     */

    public void route(RouteType route, boolean message, boolean ignoreFreeze) {
        Position position = entity.getPosition();
        int baseX = (position.getFirstChunkX() - 6) * 8;
        int baseY = (position.getFirstChunkY() - 6) * 8;
        ClipUtils clipUtils = customClipUtils != null ? customClipUtils : this.clipUtils;
        clipUtils.update(baseX, baseY, position.getZ()); //Warning, since all RouteFinder classes share 1 ClipUtils class, this function cannot be called concurrently. (Which is fine in our case anyways..)
        Movement movement = entity.getMovement();
        movement.readOffset = 0;
        movement.writeOffset = findRoute(position.getBaseLocalX(), position.getBaseLocalY(), entity.getSize(), route, clipUtils, true, movement.getStepsX(), movement.getStepsY());
        movement.stepType = StepType.NORMAL;
        if(movement.writeOffset == -1) {
            route.finishX = -1;
            route.finishY = -1;
        } else if(movement.writeOffset == 0) {
            route.finishX = position.getX();
            route.finishY = position.getY();
        } else {
            route.finishX = movement.getStepsX()[movement.writeOffset - 1];
            route.finishY = movement.getStepsY()[movement.writeOffset - 1];
        }
        if(entity.isMovementBlocked(message, ignoreFreeze))
            entity.getMovement().reset();
    }

    /**
     * Route absolute (Attempts to move exactly to the given coordinates)
     */

    private RouteAbsolute routeAbsolute;

    public RouteType routeAbsolute(int destX, int destY) {
        return routeAbsolute(destX, destY, false);
    }

    public RouteType routeAbsolute(int destX, int destY, boolean message) {
        if(routeAbsolute == null)
            routeAbsolute = new RouteAbsolute();
        routeAbsolute.set(destX, destY);
        route(routeAbsolute, message, false);
        return routeAbsolute;
    }

    /**
     * Relative route (Attempts to move to the given coordinates based on size)
     */

    private RouteRelative routeRelative;

    public RouteType routeRelative(int destX, int destY) {
        if(routeRelative == null)
            routeRelative = new RouteRelative();
        routeRelative.set(destX, destY, entity.getSize(), entity.getSize(), 0);
        route(routeRelative, true, false);
        return routeRelative;
    }

    /**
     * Route ground item
     */

    public void routeGroundItem(GroundItem groundItem, Consumer<Integer> successConsumer) {
        RouteType route = routeAbsolute(groundItem.x, groundItem.y);
        //todo - make this use route relavite when the obj is on something !
        //RouteType route = routeRelative(groundItem.x, groundItem.y);
        entity.startEvent(event -> {
            while(!entity.getMovement().isAtDestination()) {
                if(groundItem.tile == null) {
                    entity.face(groundItem.x, groundItem.y);
                    entity.getMovement().reset();
                    return;
                }
                event.delay(1);
            }
            if(groundItem.tile == null) {
                entity.face(groundItem.x, groundItem.y);
                return;
            }
            if(route.finished(entity.getPosition())) {
                if(route.reachable) {
                    successConsumer.accept(0);
                    return;
                }
                Position pos = entity.getPosition();
                int diffX = pos.getX() - groundItem.x;
                int diffY = pos.getY() - groundItem.y;
                int mask = getDirectionMask(diffX, diffY);
                if(mask != 0) {
                    Tile tile = Tile.get(pos.getX(), pos.getY(), pos.getZ());
                    if(tile == null || tile.allowEntrance(mask)) {
                        entity.face(groundItem.x, groundItem.y);
                        event.delay(1); //trying to match osrs as much as possible
                        successConsumer.accept(1);
                        return;
                    }
                }
            }
            entity.face(groundItem.x, groundItem.y);
            if(entity.player != null)
                entity.player.getMovement().outOfReach();
        }).ignoreCombatReset();
    }

    /**
     * Route object
     */

    private RouteObject routeObject;

    public RouteObject routeObject(GameObject gameObject) {
        if(routeObject == null)
            routeObject = new RouteObject();
        ObjectDef definition = gameObject.getDef();
        if(gameObject.type == 10 || gameObject.type == 11 || gameObject.type == 22) {
            int xLength, yLength;
            if(gameObject.direction == 0 || gameObject.direction == 2) {
                xLength = definition.xLength;
                yLength = definition.yLength;
            } else {
                xLength = definition.yLength;
                yLength = definition.xLength;
            }
            int someDirection = gameObject.getDef().someDirection;
            if(gameObject.direction != 0)
                someDirection = (0xf & someDirection << gameObject.direction) + (someDirection >> -gameObject.direction + 4);
            routeObject.set(gameObject.x, gameObject.y, xLength, yLength, ObjectType.values()[gameObject.type], gameObject.direction, someDirection);
        } else if(gameObject.type >= 0 && gameObject.type <= 3 || gameObject.type == 9) {
            routeObject.set(gameObject.x, gameObject.y, 0, 0, ObjectType.values()[gameObject.type], gameObject.direction, null);
        } else {
            routeObject.set(gameObject.x, gameObject.y, 0, 0, ObjectType.values()[gameObject.type], gameObject.direction, null);
        }
        route(routeObject, false, false);
        return routeObject;
    }

    public void routeObject(GameObject gameObject, Runnable successAction) {
        RouteType route;
        if(gameObject.walkTo != null)
            route = routeAbsolute(gameObject.walkTo.getX(), gameObject.walkTo.getY());
        else
            route = routeObject(gameObject);
        if(entity.player != null) {
            if(entity.player.emoteDelay.isDelayed()) {
                entity.player.resetAnimation();
                entity.player.emoteDelay.reset();
            }
        }
        /**
         * No event required, already at destination.
         */
        if(route.finished(entity.getPosition())) {
            entity.face(gameObject);
            if(route.reachable) {
                successAction.run();
                return;
            }
            if(gameObject.skipReachCheck != null && gameObject.skipReachCheck.test(entity.getPosition())) {
                successAction.run();
                return;
            }
            if(entity.player != null)
                entity.player.getMovement().outOfReach();
            return;
        }
        /**
         * Event required, not yet at destination.
         */
        int id = gameObject.id;
        entity.startEvent(event -> {
            while(!entity.getMovement().isAtDestination()) {
                if(gameObject.id != id || gameObject.tile == null) {
                    entity.face(gameObject);
                    entity.getMovement().reset();
                    return;
                }
                event.delay(1);
            }
            entity.face(gameObject);
            if(gameObject.id != id || gameObject.tile == null) {
                /* obj was changed or removed */
                return;
            }
            if(route.reachable) {
                if(route.finished(entity.getPosition())) {
                    successAction.run();
                    return;
                }
            }
            if(gameObject.skipReachCheck != null && gameObject.skipReachCheck.test(entity.getPosition())) {
                successAction.run();
                return;
            }
            if(entity.player != null)
                entity.player.getMovement().outOfReach();
        }).ignoreCombatReset();
    }

    /**
     * Route entity
     */

    public RouteEntity routeEntity;

    public RouteEntity routeSelf() {
        return routeEntity(entity);
    }

    public RouteEntity routeSelf(boolean ignoreFreeze) {
        return routeEntity(entity, ignoreFreeze);
    }

    public RouteEntity routeEntity(Entity target) {
        routeTheEntity(target);
        route(routeEntity, false, false);
        return routeEntity;
    }

    public RouteEntity routeEntity(Entity target, boolean ignoreFreeze) {
        routeTheEntity(target);
        route(routeEntity, false, ignoreFreeze);
        return routeEntity;
    }

    private void routeTheEntity(Entity target) {
        Position targetPos = target.getPosition();
        int x = targetPos.getX();
        int y = targetPos.getY();
        int size = target.getSize();
        if(routeEntity == null)
            routeEntity = new RouteEntity();
        if(entity.player != null) {
            if(entity.player.emoteDelay.isDelayed()) {
                entity.player.resetAnimation();
                entity.player.emoteDelay.reset();
            }
        }
        routeEntity.set(x, y, size, size, 0);
    }

    /**
     * Misc routes stuff
     */

    public TargetRoute targetRoute;

    /**
     * Step check
     */

    public boolean allowStep(int stepX, int stepY) {
        if(targetRoute != null && !targetRoute.allowStep(entity, stepX, stepY)) {
            entity.getMovement().reset();
            return false;
        }
        if(entity.getMovement().stepType == StepType.NORMAL) {
            if(entity.player != null) {
                if(Wilderness.stopFollowing(entity.player, stepX, stepY))
                    return false;
            } else if(!entity.npc.getDef().ignoreOccupiedTiles && Tile.isOccupied(entity, stepX, stepY)) {
                entity.getMovement().reset(); //let's reset the movement so the occupied check isn't spammed (Unless ofc it's combat, it will be spammed regardless)
                return false;
            }
            if(DumbRoute.getDirection(entity.getRouteFinder().getClipUtils(), entity.getAbsX(), entity.getAbsY(), entity.getHeight(), entity.getSize(), stepX, stepY) == null) {
                //movement stays "queued" as long as you stay still.
                return false;
            }
        }
        return true;
    }

    /**
     * Route finding
     */

    int[][] directions = new int[128][128];
    int[][] distances = new int[128][128];
    int[] queueX = new int[4096];
    int[] queueY = new int[4096];
    int foundMapX, foundMapY;

    public int findRoute(int fromMapX, int fromMapY, int size, RouteType route, ClipUtils clipUtils, boolean findAlternative, int[] stepsX, int[] stepsY) {
        for(int x = 0; x < 128; x++) {
            for(int y = 0; y < 128; y++) {
                directions[x][y] = 0;
                distances[x][y] = 99999999;
            }
        }
        route.localize(clipUtils);

        if(size == 1)
            route.reachable = findRoute1(fromMapX, fromMapY, route, clipUtils);
        else if(size == 2)
            route.reachable = findRoute2(fromMapX, fromMapY, route, clipUtils);
        else
            route.reachable = findRouteXAlternative(fromMapX, fromMapY, size, route, clipUtils);

        int arrayOffsetX = fromMapX - 64;
        int arrayOffsetY = fromMapY - 64;
        int foundMapX = this.foundMapX;
        int foundMapY = this.foundMapY;
        if(!route.reachable) {
            if(!findAlternative)
                return -1;
            int lowestCost = 2147483647;
            int lowestDistance = 2147483647;
            int checkRange = 10;
            int toMapX = route.x;
            int toMapY = route.y;
            int toSizeX = route.xLength;
            int toSizeY = route.yLength;
            for(int checkMapX = toMapX - checkRange; checkMapX <= toMapX + checkRange; checkMapX++) {
                for(int checkMapY = toMapY - checkRange; checkMapY <= checkRange + toMapY; checkMapY++) {
                    int arrayX = checkMapX - arrayOffsetX;
                    int arrayY = checkMapY - arrayOffsetY;
                    if(arrayX >= 0 && arrayY >= 0 && arrayX < 128 && arrayY < 128 && (distances[arrayX][arrayY] < 100)) {
                        int deltaX = 0;
                        if(checkMapX < toMapX)
                            deltaX = toMapX - checkMapX;
                        else if(checkMapX > toSizeX + toMapX - 1)
                            deltaX = checkMapX - (toSizeX + toMapX - 1);
                        int deltaY = 0;
                        if(checkMapY < toMapY)
                            deltaY = toMapY - checkMapY;
                        else if(checkMapY > toSizeY + toMapY - 1)
                            deltaY = checkMapY - (toMapY + toSizeY - 1);
                        int cost = deltaY * deltaY + deltaX * deltaX;
                        if(cost < lowestCost || (lowestCost == cost && (distances[arrayX][arrayY]) < lowestDistance)) {
                            lowestCost = cost;
                            lowestDistance = (distances[arrayX][arrayY]);
                            foundMapX = checkMapX;
                            foundMapY = checkMapY;
                        }
                    }
                }
            }
            if(2147483647 == lowestCost)
                return -1;
        }
        if(foundMapX == fromMapX && fromMapY == foundMapY)
            return 0;
        int writeOffset = 0;
        queueX[writeOffset] = foundMapX;
        queueY[writeOffset++] = foundMapY;
        int lastWrittenDirection;
        int direction = lastWrittenDirection = (directions[foundMapX - arrayOffsetX][foundMapY - arrayOffsetY]);
        while(foundMapX != fromMapX || fromMapY != foundMapY) {
            if(lastWrittenDirection != direction) {
                lastWrittenDirection = direction;
                queueX[writeOffset] = foundMapX;
                queueY[writeOffset++] = foundMapY;
            }
            if((direction & 0x2) != 0)
                foundMapX++;
            else if((direction & 0x8) != 0)
                foundMapX--;
            if((direction & 0x1) != 0)
                foundMapY++;
            else if((direction & 0x4) != 0)
                foundMapY--;
            direction = (directions[foundMapX - arrayOffsetX][foundMapY - arrayOffsetY]);
        }
        int stepCount = 0;
        while(writeOffset-- > 0) {
            stepsX[stepCount] = clipUtils.baseX + queueX[writeOffset];
            stepsY[stepCount] = clipUtils.baseY + queueY[writeOffset];
            if(++stepCount >= stepsX.length)
                break;
        }
        return stepCount;
    }

    private boolean findRoute1(int fromMapX, int fromMapY, RouteType route, ClipUtils clipUtils) {
        int currentMapX = fromMapX;
        int currentMapY = fromMapY;
        int currentArrayOffsetX = 64;
        int currentArrayOffsetY = 64;
        int baseArrayOffsetX = fromMapX - currentArrayOffsetX;
        int baseArrayOffsetY = fromMapY - currentArrayOffsetY;
        directions[currentArrayOffsetX][currentArrayOffsetY] = 99;
        distances[currentArrayOffsetX][currentArrayOffsetY] = 0;
        int writeOffset = 0;
        int readOffset = 0;
        queueX[writeOffset] = fromMapX;
        queueY[writeOffset++] = fromMapY;
        while(readOffset != writeOffset) {
            currentMapX = queueX[readOffset];
            currentMapY = queueY[readOffset];
            readOffset = readOffset + 1 & 0xfff;
            currentArrayOffsetX = currentMapX - baseArrayOffsetX;
            currentArrayOffsetY = currentMapY - baseArrayOffsetY;
            int currentClipMapX = currentMapX;
            int currentClipMapY = currentMapY;
            if(route.method4274(1, currentMapX, currentMapY, clipUtils)) {
                foundMapX = currentMapX;
                foundMapY = currentMapY;
                return true;
            }
            int distance = distances[currentArrayOffsetX][currentArrayOffsetY] + 1;
            if(currentArrayOffsetX > 0 && directions[currentArrayOffsetX - 1][currentArrayOffsetY] == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY) & WEST_MASK) == 0) {
                queueX[writeOffset] = currentMapX - 1;
                queueY[writeOffset] = currentMapY;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX - 1][currentArrayOffsetY] = 2;
                distances[currentArrayOffsetX - 1][currentArrayOffsetY] = distance;
            }
            if(currentArrayOffsetX < 127 && directions[currentArrayOffsetX + 1][currentArrayOffsetY] == 0 && (clipUtils.get(currentClipMapX + 1, currentClipMapY) & EAST_MASK) == 0) {
                queueX[writeOffset] = currentMapX + 1;
                queueY[writeOffset] = currentMapY;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX + 1][currentArrayOffsetY] = 8;
                distances[currentArrayOffsetX + 1][currentArrayOffsetY] = distance;
            }
            if(currentArrayOffsetY > 0 && directions[currentArrayOffsetX][currentArrayOffsetY - 1] == 0 && (clipUtils.get(currentClipMapX, currentClipMapY - 1) & SOUTH_MASK) == 0) {
                queueX[writeOffset] = currentMapX;
                queueY[writeOffset] = currentMapY - 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX][currentArrayOffsetY - 1] = 1;
                distances[currentArrayOffsetX][currentArrayOffsetY - 1] = distance;
            }
            if(currentArrayOffsetY < 127 && directions[currentArrayOffsetX][currentArrayOffsetY + 1] == 0 && (clipUtils.get(currentClipMapX, currentClipMapY + 1) & NORTH_MASK) == 0) {
                queueX[writeOffset] = currentMapX;
                queueY[writeOffset] = currentMapY + 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX][currentArrayOffsetY + 1] = 4;
                distances[currentArrayOffsetX][currentArrayOffsetY + 1] = distance;
            }
            if(currentArrayOffsetX > 0 && currentArrayOffsetY > 0 && directions[currentArrayOffsetX - 1][currentArrayOffsetY - 1] == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY - 1) & SOUTH_WEST_MASK) == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY) & WEST_MASK) == 0 && (clipUtils.get(currentClipMapX, currentClipMapY - 1) & SOUTH_MASK) == 0) {
                queueX[writeOffset] = currentMapX - 1;
                queueY[writeOffset] = currentMapY - 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX - 1][currentArrayOffsetY - 1] = 3;
                distances[currentArrayOffsetX - 1][currentArrayOffsetY - 1] = distance;
            }
            if(currentArrayOffsetX < 127 && currentArrayOffsetY > 0 && directions[currentArrayOffsetX + 1][currentArrayOffsetY - 1] == 0 && (clipUtils.get(currentClipMapX + 1, currentClipMapY - 1) & SOUTH_EAST_MASK) == 0 && (clipUtils.get(currentClipMapX + 1, currentClipMapY) & EAST_MASK) == 0 && (clipUtils.get(currentClipMapX, currentClipMapY - 1) & SOUTH_MASK) == 0) {
                queueX[writeOffset] = currentMapX + 1;
                queueY[writeOffset] = currentMapY - 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX + 1][currentArrayOffsetY - 1] = 9;
                distances[currentArrayOffsetX + 1][currentArrayOffsetY - 1] = distance;
            }
            if(currentArrayOffsetX > 0 && currentArrayOffsetY < 127 && directions[currentArrayOffsetX - 1][currentArrayOffsetY + 1] == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY + 1) & NORTH_WEST_MASK) == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY) & WEST_MASK) == 0 && (clipUtils.get(currentClipMapX, currentClipMapY + 1) & NORTH_MASK) == 0) {
                queueX[writeOffset] = currentMapX - 1;
                queueY[writeOffset] = currentMapY + 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX - 1][currentArrayOffsetY + 1] = 6;
                distances[currentArrayOffsetX - 1][currentArrayOffsetY + 1] = distance;
            }
            if(currentArrayOffsetX < 127 && currentArrayOffsetY < 127 && directions[currentArrayOffsetX + 1][currentArrayOffsetY + 1] == 0 && (clipUtils.get(currentClipMapX + 1, currentClipMapY + 1) & NORTH_EAST_MASK) == 0 && (clipUtils.get(currentClipMapX + 1, currentClipMapY) & EAST_MASK) == 0 && (clipUtils.get(currentClipMapX, currentClipMapY + 1) & NORTH_MASK) == 0) {
                queueX[writeOffset] = currentMapX + 1;
                queueY[writeOffset] = currentMapY + 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX + 1][currentArrayOffsetY + 1] = 12;
                distances[currentArrayOffsetX + 1][currentArrayOffsetY + 1] = distance;
            }
        }
        foundMapX = currentMapX;
        foundMapY = currentMapY;
        return false;
    }

    private boolean findRoute2(int fromMapX, int fromMapY, RouteType route, ClipUtils clipUtils) {
        int currentMapX = fromMapX;
        int currentMapY = fromMapY;
        int currentArrayOffsetX = 64;
        int currentArrayOffsetY = 64;
        int baseArrayOffsetX = fromMapX - currentArrayOffsetX;
        int baseArrayOffsetY = fromMapY - currentArrayOffsetY;
        directions[currentArrayOffsetX][currentArrayOffsetY] = 99;
        distances[currentArrayOffsetX][currentArrayOffsetY] = 0;
        int writeOffset = 0;
        int readOffset = 0;
        queueX[writeOffset] = fromMapX;
        queueY[writeOffset++] = fromMapY;
        while(readOffset != writeOffset) {
            currentMapX = queueX[readOffset];
            currentMapY = queueY[readOffset];
            readOffset = readOffset + 1 & 0xfff;
            currentArrayOffsetX = currentMapX - baseArrayOffsetX;
            currentArrayOffsetY = currentMapY - baseArrayOffsetY;
            int currentClipMapX = currentMapX;
            int currentClipMapY = currentMapY;
            if(route.method4274(2, currentMapX, currentMapY, clipUtils)) {
                foundMapX = currentMapX;
                foundMapY = currentMapY;
                return true;
            }
            int distance = distances[currentArrayOffsetX][currentArrayOffsetY] + 1;
            if(currentArrayOffsetX > 0 && (directions[currentArrayOffsetX - 1][currentArrayOffsetY]) == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY) & SOUTH_WEST_MASK) == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY + 1) & NORTH_WEST_MASK) == 0) {
                queueX[writeOffset] = currentMapX - 1;
                queueY[writeOffset] = currentMapY;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX - 1][currentArrayOffsetY] = 2;
                distances[currentArrayOffsetX - 1][currentArrayOffsetY] = distance;
            }
            if(currentArrayOffsetX < 126 && (directions[currentArrayOffsetX + 1][currentArrayOffsetY]) == 0 && (clipUtils.get(currentClipMapX + 2, currentClipMapY) & SOUTH_EAST_MASK) == 0 && (clipUtils.get(currentClipMapX + 2, currentClipMapY + 1) & NORTH_EAST_MASK) == 0) {
                queueX[writeOffset] = currentMapX + 1;
                queueY[writeOffset] = currentMapY;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX + 1][currentArrayOffsetY] = 8;
                distances[currentArrayOffsetX + 1][currentArrayOffsetY] = distance;
            }
            if(currentArrayOffsetY > 0 && (directions[currentArrayOffsetX][currentArrayOffsetY - 1]) == 0 && (clipUtils.get(currentClipMapX, currentClipMapY - 1) & SOUTH_WEST_MASK) == 0 && (clipUtils.get(currentClipMapX + 1, currentClipMapY - 1) & SOUTH_EAST_MASK) == 0) {
                queueX[writeOffset] = currentMapX;
                queueY[writeOffset] = currentMapY - 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX][currentArrayOffsetY - 1] = 1;
                distances[currentArrayOffsetX][currentArrayOffsetY - 1] = distance;
            }
            if(currentArrayOffsetY < 126 && (directions[currentArrayOffsetX][currentArrayOffsetY + 1]) == 0 && (clipUtils.get(currentClipMapX, currentClipMapY + 2) & NORTH_WEST_MASK) == 0 && (clipUtils.get(currentClipMapX + 1, currentClipMapY + 2) & NORTH_EAST_MASK) == 0) {
                queueX[writeOffset] = currentMapX;
                queueY[writeOffset] = currentMapY + 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX][currentArrayOffsetY + 1] = 4;
                distances[currentArrayOffsetX][currentArrayOffsetY + 1] = distance;
            }
            if(currentArrayOffsetX > 0 && currentArrayOffsetY > 0 && (directions[currentArrayOffsetX - 1][currentArrayOffsetY - 1]) == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY) & 0x124013e) == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY - 1) & SOUTH_WEST_MASK) == 0 && (clipUtils.get(currentClipMapX, currentClipMapY - 1) & 0x124018f) == 0) {
                queueX[writeOffset] = currentMapX - 1;
                queueY[writeOffset] = currentMapY - 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX - 1][currentArrayOffsetY - 1] = 3;
                distances[currentArrayOffsetX - 1][currentArrayOffsetY - 1] = distance;
            }
            if(currentArrayOffsetX < 126 && currentArrayOffsetY > 0 && (directions[currentArrayOffsetX + 1][currentArrayOffsetY - 1]) == 0 && (clipUtils.get(currentClipMapX + 1, currentClipMapY - 1) & 0x124018f) == 0 && (clipUtils.get(currentClipMapX + 2, currentClipMapY - 1) & SOUTH_EAST_MASK) == 0 && (clipUtils.get(currentClipMapX + 2, currentClipMapY) & 0x12401e3) == 0) {
                queueX[writeOffset] = currentMapX + 1;
                queueY[writeOffset] = currentMapY - 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX + 1][currentArrayOffsetY - 1] = 9;
                distances[currentArrayOffsetX + 1][currentArrayOffsetY - 1] = distance;
            }
            if(currentArrayOffsetX > 0 && currentArrayOffsetY < 126 && (directions[currentArrayOffsetX - 1][currentArrayOffsetY + 1]) == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY + 1) & 0x124013e) == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY + 2) & NORTH_WEST_MASK) == 0 && (clipUtils.get(currentClipMapX, currentClipMapY + 2) & 0x12401f8) == 0) {
                queueX[writeOffset] = currentMapX - 1;
                queueY[writeOffset] = currentMapY + 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX - 1][currentArrayOffsetY + 1] = 6;
                distances[currentArrayOffsetX - 1][currentArrayOffsetY + 1] = distance;
            }
            if(currentArrayOffsetX < 126 && currentArrayOffsetY < 126 && (directions[currentArrayOffsetX + 1][currentArrayOffsetY + 1]) == 0 && (clipUtils.get(currentClipMapX + 1, currentClipMapY + 2) & 0x12401f8) == 0 && (clipUtils.get(currentClipMapX + 2, currentClipMapY + 2) & NORTH_EAST_MASK) == 0 && (clipUtils.get(currentClipMapX + 2, currentClipMapY + 1) & 0x12401e3) == 0) {
                queueX[writeOffset] = currentMapX + 1;
                queueY[writeOffset] = currentMapY + 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX + 1][currentArrayOffsetY + 1] = 12;
                distances[currentArrayOffsetX + 1][currentArrayOffsetY + 1] = distance;
            }
        }
        foundMapX = currentMapX;
        foundMapY = currentMapY;
        return false;
    }

    private boolean findRouteX(int fromMapX, int fromMapY, int size, RouteType route, ClipUtils clipUtils) {
        int currentMapX = fromMapX;
        int currentMapY = fromMapY;
        int currentArrayOffsetX = 64;
        int currentArrayOffsetY = 64;
        int baseArrayOffsetX = fromMapX - currentArrayOffsetX;
        int baseArrayOffsetY = fromMapY - currentArrayOffsetY;
        directions[currentArrayOffsetX][currentArrayOffsetY] = 99;
        distances[currentArrayOffsetX][currentArrayOffsetY] = 0;
        int writeOffset = 0;
        int readOffset = 0;
        queueX[writeOffset] = fromMapX;
        queueY[writeOffset++] = fromMapY;
        w: while(readOffset != writeOffset) {
            currentMapX = queueX[readOffset];
            currentMapY = queueY[readOffset];
            readOffset = readOffset + 1 & 0xfff;
            currentArrayOffsetX = currentMapX - baseArrayOffsetX;
            currentArrayOffsetY = currentMapY - baseArrayOffsetY;
            int currentClipMapX = currentMapX;
            int currentClipMapY = currentMapY;
            if(route.method4274(size, currentMapX, currentMapY, clipUtils)) {
                foundMapX = currentMapX;
                foundMapY = currentMapY;
                return true;
            }
            int distance = distances[currentArrayOffsetX][currentArrayOffsetY] + 1;
            if(currentArrayOffsetX > 0 && directions[currentArrayOffsetX - 1][currentArrayOffsetY] == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY) & SOUTH_WEST_MASK) == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY + size - 1) & NORTH_WEST_MASK) == 0) {
                for(int i = 1; i < size; i++) {
                    if((clipUtils.get(currentClipMapX - 1, i + currentClipMapY) & 0x124013e) != 0)
                        continue w;
                }
                queueX[writeOffset] = currentMapX - 1;
                queueY[writeOffset] = currentMapY;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX - 1][currentArrayOffsetY] = 2;
                distances[currentArrayOffsetX - 1][currentArrayOffsetY] = distance;
            }
            if(currentArrayOffsetX < 128 - size && directions[currentArrayOffsetX + 1][currentArrayOffsetY] == 0 && (clipUtils.get(currentClipMapX + size, currentClipMapY) & SOUTH_EAST_MASK) == 0 && (clipUtils.get(size + currentClipMapX, size + currentClipMapY - 1) & NORTH_EAST_MASK) == 0) {
                for(int i = 1; i < size; i++) {
                    if((clipUtils.get(size + currentClipMapX, currentClipMapY + i) & 0x12401e3) != 0)
                        continue w;
                }
                queueX[writeOffset] = currentMapX + 1;
                queueY[writeOffset] = currentMapY;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX + 1][currentArrayOffsetY] = 8;
                distances[currentArrayOffsetX + 1][currentArrayOffsetY] = distance;
            }
            if(currentArrayOffsetY > 0 && directions[currentArrayOffsetX][currentArrayOffsetY - 1] == 0 && (clipUtils.get(currentClipMapX, currentClipMapY - 1) & SOUTH_WEST_MASK) == 0 && (clipUtils.get(size + currentClipMapX - 1, currentClipMapY - 1) & SOUTH_EAST_MASK) == 0) {
                for(int i = 1; i < size; i++) {
                    if((clipUtils.get(currentClipMapX + i, currentClipMapY - 1) & 0x124018f) != 0)
                        continue w;
                }
                queueX[writeOffset] = currentMapX;
                queueY[writeOffset] = currentMapY - 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX][currentArrayOffsetY - 1] = 1;
                distances[currentArrayOffsetX][currentArrayOffsetY - 1] = distance;
            }
            if(currentArrayOffsetY < 128 - size && directions[currentArrayOffsetX][currentArrayOffsetY + 1] == 0 && (clipUtils.get(currentClipMapX, currentClipMapY + size) & NORTH_WEST_MASK) == 0 && (clipUtils.get(currentClipMapX + size - 1, size + currentClipMapY) & NORTH_EAST_MASK) == 0) {
                for(int i = 1; i < size; i++) {
                    if((clipUtils.get(i + currentClipMapX, currentClipMapY + size) & 0x12401f8) != 0)
                        continue w;
                }
                queueX[writeOffset] = currentMapX;
                queueY[writeOffset] = currentMapY + 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX][currentArrayOffsetY + 1] = 4;
                distances[currentArrayOffsetX][currentArrayOffsetY + 1] = distance;
            }
            if(currentArrayOffsetX > 0 && currentArrayOffsetY > 0 && directions[currentArrayOffsetX - 1][currentArrayOffsetY - 1] == 0 && (clipUtils.get(currentClipMapX - 1, currentClipMapY - 1) & SOUTH_WEST_MASK) == 0) {
                for(int i = 1; i < size; i++) {
                    if((clipUtils.get(currentClipMapX - 1, i + (currentClipMapY - 1)) & 0x124013e) != 0 || ((clipUtils.get(i + (currentClipMapX - 1), currentClipMapY - 1) & 0x124018f) != 0))
                        continue w;
                }
                queueX[writeOffset] = currentMapX - 1;
                queueY[writeOffset] = currentMapY - 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX - 1][currentArrayOffsetY - 1] = 3;
                distances[currentArrayOffsetX - 1][currentArrayOffsetY - 1] = distance;
            }
            if(currentArrayOffsetX < 128 - size && currentArrayOffsetY > 0 && directions[currentArrayOffsetX + 1][currentArrayOffsetY - 1] == 0 && (clipUtils.get(currentClipMapX + size, currentClipMapY - 1) & SOUTH_EAST_MASK) == 0) {
                for(int i = 1; i < size; i++) {
                    if((clipUtils.get(size + currentClipMapX, currentClipMapY - 1 + i) & 0x12401e3) != 0 || (clipUtils.get(i + currentClipMapX, currentClipMapY - 1) & 0x124018f) != 0)
                        continue w;
                }
                queueX[writeOffset] = currentMapX + 1;
                queueY[writeOffset] = currentMapY - 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX + 1][currentArrayOffsetY - 1] = 9;
                distances[currentArrayOffsetX + 1][currentArrayOffsetY - 1] = distance;
            }
            if(currentArrayOffsetX > 0 && currentArrayOffsetY < 128 - size && directions[currentArrayOffsetX - 1][currentArrayOffsetY + 1] == 0 && (clipUtils.get(currentClipMapX - 1, size + currentClipMapY) & NORTH_WEST_MASK) == 0) {
                for(int i = 1; i < size; i++) {
                    if((clipUtils.get(currentClipMapX - 1, i + currentClipMapY) & 0x124013e) != 0 || (clipUtils.get(currentClipMapX - 1 + i, size + currentClipMapY) & 0x12401f8) != 0)
                        continue w;
                }
                queueX[writeOffset] = currentMapX - 1;
                queueY[writeOffset] = currentMapY + 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX - 1][currentArrayOffsetY + 1] = 6;
                distances[currentArrayOffsetX - 1][currentArrayOffsetY + 1] = distance;
            }
            if(currentArrayOffsetX < 128 - size && currentArrayOffsetY < 128 - size && directions[currentArrayOffsetX + 1][currentArrayOffsetY + 1] == 0 && (clipUtils.get(size + currentClipMapX, size + currentClipMapY) & NORTH_EAST_MASK) == 0) {
                for(int i = 1; i < size; i++) {
                    if((clipUtils.get(currentClipMapX + i, size + currentClipMapY) & 0x12401f8) != 0 || (clipUtils.get(currentClipMapX + size, currentClipMapY + i) & 0x12401e3) != 0)
                        continue w;
                }
                queueX[writeOffset] = currentMapX + 1;
                queueY[writeOffset] = currentMapY + 1;
                writeOffset = writeOffset + 1 & 0xfff;
                directions[currentArrayOffsetX + 1][currentArrayOffsetY + 1] = 12;
                distances[currentArrayOffsetX + 1][currentArrayOffsetY + 1] = distance;
            }
        }
        foundMapX = currentMapX;
        foundMapY = currentMapY;
        return false;
    }

    /**
     * Derp
     */

    public static int getDirectionMask(int diffX, int diffY) {
        if(diffX == -1) {
            if(diffY == -1)
                return SOUTH_WEST_MASK;
            if(diffY == 1)
                return NORTH_WEST_MASK;
            return WEST_MASK;
        }
        if(diffX == 1) {
            if(diffY == -1)
                return SOUTH_EAST_MASK;
            if(diffY == 1)
                return NORTH_EAST_MASK;
            return EAST_MASK;
        }
        if(diffY == -1)
            return SOUTH_MASK;
        if(diffY == 1)
            return NORTH_MASK;
        return 0; //invalid diffs
    }

    /**
     * Walkable stuff
     */

    public static Position findWalkable(Position fromPos) {
        return findWalkable(fromPos.getX(), fromPos.getY(), fromPos.getZ());
    }

    public static Position findWalkable(int fromX, int fromY, int fromZ) {
        int radius = 1; //Extending this would require much heavier checks.
        List<Position> positions = new ArrayList<>(radius * 8);
        for(int x = (fromX - radius); x <= (fromX + radius); x++) {
            for(int y = (fromY - radius); y <= (fromY + radius); y++) {
                if(x == fromX && y == fromY) {
                    /* exclude this tile! */
                    continue;
                }
                Tile tile = Tile.get(x, y, fromZ);
                if(tile != null && !tile.allowStandardEntrance()) {
                    /* tile can't be entered! */
                    continue;
                }
                positions.add(new Position(x, y, fromZ));
            }
        }
        return Random.get(positions);
    }

    boolean findRouteXAlternative(final int fromMapX, final int fromMapY, final int size, RouteType route, ClipUtils clipUtils) {
        int currentMapX = fromMapX;
        int currentMapY = fromMapY;
        final byte startOffsetX = 64;
        final byte startOffsetY = 64;
        final int baseArrayOffsetX = fromMapX - startOffsetX;
        final int int_6 = fromMapY - startOffsetY;
        directions[startOffsetX][startOffsetY] = 99;
        distances[startOffsetX][startOffsetY] = 0;
        final byte byte_2 = 0;
        int int_7 = 0;
        queueX[byte_2] = fromMapX;
        int int_14 = byte_2 + 1;
        queueY[byte_2] = fromMapY;

        while (true) {
            label313:
            while (true) {
                int currentClipMapX;
                int currentClipMapY;
                int int_10;
                int int_11;
                int int_12;
                int int_13;
                do {
                    do {
                        do {
                            label290:
                            do {
                                if (int_7 == int_14) {
                                    foundMapX = currentMapX;
                                    foundMapY = currentMapY;
                                    return false;
                                }

                                currentMapX = queueX[int_7];
                                currentMapY = queueY[int_7];
                                int_7 = (int_7 + 1) & 0xFFF;
                                int_12 = currentMapX - baseArrayOffsetX;
                                int_13 = currentMapY - int_6;
                                currentClipMapX = currentMapX;
                                currentClipMapY = currentMapY;
                                if (route.method4274(size, currentMapX, currentMapY, clipUtils)) {
                                    foundMapX = currentMapX;
                                    foundMapY = currentMapY;
                                    return true;
                                }

                                int_10 = distances[int_12][int_13] + 1;
                                if ((int_12 > 0) && (directions[int_12 - 1][int_13] == 0)
                                        && ((clipUtils.get(currentClipMapX - 1, currentClipMapY) & 0x124010E) == 0)
                                        && ((clipUtils.get(currentClipMapX - 1, (currentClipMapY + size) - 1) & 0x1240138) == 0)) {
                                    int_11 = 1;

                                    while (true) {
                                        if (int_11 >= (size - 1)) {
                                            queueX[int_14] = currentMapX - 1;
                                            queueY[int_14] = currentMapY;
                                            int_14 = (int_14 + 1) & 0xFFF;
                                            directions[int_12 - 1][int_13] = 2;
                                            distances[int_12 - 1][int_13] = int_10;
                                            break;
                                        }

                                        if ((clipUtils.get(currentClipMapX - 1, int_11 + currentClipMapY) & 0x124013E) != 0) {
                                            break;
                                        }

                                        ++int_11;
                                    }
                                }

                                if ((int_12 < (128 - size)) && (directions[int_12 + 1][int_13] == 0)
                                        && ((clipUtils.get(currentClipMapX + size, currentClipMapY) & 0x1240183) == 0)
                                        && ((clipUtils.get(currentClipMapX + size, (currentClipMapY + size) - 1) & 0x12401E0) == 0)) {
                                    int_11 = 1;

                                    while (true) {
                                        if (int_11 >= (size - 1)) {
                                            queueX[int_14] = currentMapX + 1;
                                            queueY[int_14] = currentMapY;
                                            int_14 = (int_14 + 1) & 0xFFF;
                                            directions[int_12 + 1][int_13] = 8;
                                            distances[int_12 + 1][int_13] = int_10;
                                            break;
                                        }

                                        if ((clipUtils.get(currentClipMapX + size, currentClipMapY + int_11) & 0x12401E3) != 0) {
                                            break;
                                        }

                                        ++int_11;
                                    }
                                }

                                if ((int_13 > 0) && (directions[int_12][int_13 - 1] == 0)
                                        && ((clipUtils.get(currentClipMapX, currentClipMapY - 1) & 0x124010E) == 0)
                                        && ((clipUtils.get((currentClipMapX + size) - 1, currentClipMapY - 1) & 0x1240183) == 0)) {
                                    int_11 = 1;

                                    while (true) {
                                        if (int_11 >= (size - 1)) {
                                            queueX[int_14] = currentMapX;
                                            queueY[int_14] = currentMapY - 1;
                                            int_14 = (int_14 + 1) & 0xFFF;
                                            directions[int_12][int_13 - 1] = 1;
                                            distances[int_12][int_13 - 1] = int_10;
                                            break;
                                        }

                                        if ((clipUtils.get(currentClipMapX + int_11, currentClipMapY - 1) & 0x124018F) != 0) {
                                            break;
                                        }

                                        ++int_11;
                                    }
                                }

                                if ((int_13 < (128 - size)) && (directions[int_12][int_13 + 1] == 0)
                                        && ((clipUtils.get(currentClipMapX, currentClipMapY + size) & 0x1240138) == 0)
                                        && ((clipUtils.get((currentClipMapX + size) - 1, currentClipMapY + size) & 0x12401E0) == 0)) {
                                    int_11 = 1;

                                    while (true) {
                                        if (int_11 >= (size - 1)) {
                                            queueX[int_14] = currentMapX;
                                            queueY[int_14] = currentMapY + 1;
                                            int_14 = (int_14 + 1) & 0xFFF;
                                            directions[int_12][int_13 + 1] = 4;
                                            distances[int_12][int_13 + 1] = int_10;
                                            break;
                                        }

                                        if ((clipUtils.get(int_11 + currentClipMapX, currentClipMapY + size) & 0x12401F8) != 0) {
                                            break;
                                        }

                                        ++int_11;
                                    }
                                }

                                if ((int_12 > 0) && (int_13 > 0) && (directions[int_12 - 1][int_13 - 1] == 0)
                                        && ((clipUtils.get(currentClipMapX - 1, currentClipMapY - 1) & 0x124010E) == 0)) {
                                    int_11 = 1;

                                    while (true) {
                                        if (int_11 >= size) {
                                            queueX[int_14] = currentMapX - 1;
                                            queueY[int_14] = currentMapY - 1;
                                            int_14 = (int_14 + 1) & 0xFFF;
                                            directions[int_12 - 1][int_13 - 1] = 3;
                                            distances[int_12 - 1][int_13 - 1] = int_10;
                                            break;
                                        }

                                        if (((clipUtils.get(currentClipMapX - 1, int_11 + (currentClipMapY - 1)) & 0x124013E) != 0)
                                                || ((clipUtils.get(int_11 + (currentClipMapX - 1), currentClipMapY - 1) & 0x124018F) != 0)) {
                                            break;
                                        }

                                        ++int_11;
                                    }
                                }

                                if ((int_12 < (128 - size)) && (int_13 > 0)
                                        && (directions[int_12 + 1][int_13 - 1] == 0)
                                        && ((clipUtils.get(currentClipMapX + size, currentClipMapY - 1) & 0x1240183) == 0)) {
                                    int_11 = 1;

                                    while (true) {
                                        if (int_11 >= size) {
                                            queueX[int_14] = currentMapX + 1;
                                            queueY[int_14] = currentMapY - 1;
                                            int_14 = (int_14 + 1) & 0xFFF;
                                            directions[int_12 + 1][int_13 - 1] = 9;
                                            distances[int_12 + 1][int_13 - 1] = int_10;
                                            break;
                                        }

                                        if (((clipUtils.get(currentClipMapX + size, int_11 + (currentClipMapY - 1)) & 0x12401E3) != 0)
                                                || ((clipUtils.get(currentClipMapX + int_11, currentClipMapY - 1) & 0x124018F) != 0)) {
                                            break;
                                        }

                                        ++int_11;
                                    }
                                }

                                if ((int_12 > 0) && (int_13 < (128 - size))
                                        && (directions[int_12 - 1][int_13 + 1] == 0)
                                        && ((clipUtils.get(currentClipMapX - 1, currentClipMapY + size) & 0x1240138) == 0)) {
                                    for (int_11 = 1; int_11 < size; int_11++) {
                                        if (((clipUtils.get(currentClipMapX - 1, int_11 + currentClipMapY) & 0x124013E) != 0)
                                                || ((clipUtils.get(int_11 + (currentClipMapX - 1), currentClipMapY + size) & 0x12401F8) != 0)) {
                                            continue label290;
                                        }
                                    }

                                    queueX[int_14] = currentMapX - 1;
                                    queueY[int_14] = currentMapY + 1;
                                    int_14 = (int_14 + 1) & 0xFFF;
                                    directions[int_12 - 1][int_13 + 1] = 6;
                                    distances[int_12 - 1][int_13 + 1] = int_10;
                                }
                            }
                            while (int_12 >= (128 - size));
                        }
                        while (int_13 >= (128 - size));
                    }
                    while (directions[int_12 + 1][int_13 + 1] != 0);
                }
                while ((clipUtils.get(currentClipMapX + size, currentClipMapY + size) & 0x12401E0) != 0);

                for (int_11 = 1; int_11 < size; int_11++) {
                    if (((clipUtils.get(int_11 + currentClipMapX, currentClipMapY + size) & 0x12401F8) != 0)
                            || ((clipUtils.get(currentClipMapX + size, currentClipMapY + int_11) & 0x12401E3) != 0)) {
                        continue label313;
                    }
                }

                queueX[int_14] = currentMapX + 1;
                queueY[int_14] = currentMapY + 1;
                int_14 = (int_14 + 1) & 0xFFF;
                directions[int_12 + 1][int_13 + 1] = 12;
                distances[int_12 + 1][int_13 + 1] = int_10;
            }
        }
    }

}