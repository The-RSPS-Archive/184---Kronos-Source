package io.ruin.model.map.route.routes;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.route.RouteFinder;
import io.ruin.utility.Misc;

import static io.ruin.model.map.route.RouteFinder.*;

public class DumbRoute {

    public static void route(Entity entity, int destX, int destY) {
        entity.resetSteps();
        if(entity.isMovementBlocked(false, false))
            return;
        int x = entity.getAbsX();
        int y = entity.getAbsY();
        int z = entity.getHeight();
        int size = entity.getSize();
        while(true) {
            Direction stepDir = getStepDirection(entity.getRouteFinder().getClipUtils(), x, y, z, size, destX, destY);
            if(stepDir == null)
                return;
            x += stepDir.deltaX;
            y += stepDir.deltaY;
            if(!entity.addStep(x, y) || (x == destX && y == destY))
                return;
        }
    }

    public static void step(Entity entity, int destX, int destY) {
        if(entity.isMovementBlocked(false, false))
            return;
        Direction stepDir = getStepDirection(entity.getRouteFinder().getClipUtils(), entity.getAbsX(), entity.getAbsY(), entity.getHeight(), entity.getSize(), destX, destY);
        if(stepDir != null)
            entity.step(stepDir.deltaX, stepDir.deltaY, StepType.NORMAL);
    }

    public static void step(Entity entity, Entity target, int distance) {
        /**
         * Use the route finder to find the exact x/y to walk to.. I know.. bad..
         */
        RouteFinder rf = entity.getRouteFinder();
        rf.customClipUtils = ClipUtils.NONE;
        rf.routeEntity(target);
        rf.customClipUtils = null;
        entity.getMovement().reset();
        //todo - Figure out a correct way to calc the destX/destY because this doesn't work like I want it to!!
        /**
         * If not already within distance, take a step..
         */
        if(!withinDistance(entity, target, distance))
            step(entity, rf.routeEntity.finishX, rf.routeEntity.finishY);
    }

    public static boolean withinDistance(Entity entity, Entity target, int distance) {
        Position source = entity.getPosition();
        if (entity.getSize() > 1) {
            source = Misc.getClosestPosition(entity, target); // if this is a "big" entity, we need to check from the closest position and use size 1, otherwise the getDirection check will fail in tight spaces as the npc won't be able to actually take the step, but might still be able to reach the target with an attack
        }
        if(distance == 1) {
            return entity.getRouteFinder().routeEntity.finished(entity.getPosition())
                    && getDirection(source.getX(), source.getY(), entity.getHeight(), 1, target.getAbsX(), target.getAbsY()) != null;
        } else {
            int absX = entity.getAbsX();
            int absY = entity.getAbsY();
            int size = entity.getSize();
            int targetX = target.getAbsX();
            int targetY = target.getAbsY();
            int targetSize = target.getSize();
            return !TargetRoute.inTarget(absX, absY, size, targetX, targetY, targetSize)
                    && Misc.getDistance(source, target.getPosition()) <= distance
                    //&& TargetRoute.inRange(absX, absY, size, targetX, targetY, targetSize, distance)
                    && ProjectileRoute.allow(source.getX(), source.getY(), entity.getHeight(), 1, targetX, targetY, targetSize);
        }
    }

    /**
     * Clipping stuff..
     */

    public static Direction getDirection(int x, int y, int z, int size, int destX, int destY) {
        return getDirection(ClipUtils.REGULAR, x, y, z, size, destX, destY);
    }

    public static Direction getDirection(ClipUtils clipping, int x, int y, int z, int size, int destX, int destY) {

        boolean west = false, east = false;
        if(x < destX)
            east = true;
        else if(x > destX)
            west = true;

        boolean south = false, north = false;
        if(y < destY)
            north = true;
        else if(y > destY)
            south = true;

        if(west) {
            if(south)
                return allowEntrance(clipping, x, y, z, size, Direction.SOUTH_WEST) ? Direction.SOUTH_WEST : null;
             if(north)
                 return allowEntrance(clipping, x, y, z, size, Direction.NORTH_WEST) ? Direction.NORTH_WEST : null;
            return allowEntrance(clipping, x, y, z, size, Direction.WEST) ? Direction.WEST : null;
        }
        if(east) {
            if(south)
                return allowEntrance(clipping, x, y, z, size, Direction.SOUTH_EAST) ? Direction.SOUTH_EAST : null;
            if(north)
                return allowEntrance(clipping, x, y, z, size, Direction.NORTH_EAST) ? Direction.NORTH_EAST : null;
            return allowEntrance(clipping, x, y, z, size, Direction.EAST) ? Direction.EAST : null;
        }
        if(south)
            return allowEntrance(clipping, x, y, z, size, Direction.SOUTH) ? Direction.SOUTH : null;
        if(north)
            return allowEntrance(clipping, x, y, z, size, Direction.NORTH) ? Direction.NORTH : null;
        return null;
    }

    private static Direction getStepDirection(ClipUtils clipping, int x, int y, int z, int size, int destX, int destY) {

        boolean west = false, east = false;
        if(x < destX)
            east = true;
        else if(x > destX)
            west = true;

        boolean south = false, north = false;
        if(y < destY)
            north = true;
        else if(y > destY)
            south = true;

        if(west) {
            if(south) {
                if(allowEntrance(clipping, x, y, z, size, Direction.SOUTH_WEST))
                    return Direction.SOUTH_WEST;
                if(allowEntrance(clipping, x, y, z, size, Direction.SOUTH))
                    return Direction.SOUTH;
            } else if(north) {
                if(allowEntrance(clipping, x, y, z, size, Direction.NORTH_WEST))
                    return Direction.NORTH_WEST;
                if(allowEntrance(clipping, x, y, z, size, Direction.NORTH))
                    return Direction.NORTH;
            }
            return allowEntrance(clipping, x, y, z, size, Direction.WEST) ? Direction.WEST : null;
        }
        if(east) {
            if(south) {
                if(allowEntrance(clipping, x, y, z, size, Direction.SOUTH_EAST))
                    return Direction.SOUTH_EAST;
                if(allowEntrance(clipping, x, y, z, size, Direction.SOUTH))
                    return Direction.SOUTH;
            } else if(north) {
                if(allowEntrance(clipping, x, y, z, size, Direction.NORTH_EAST))
                    return Direction.NORTH_EAST;
                if(allowEntrance(clipping, x, y, z, size, Direction.NORTH))
                    return Direction.NORTH;
            }
            return allowEntrance(clipping, x, y, z, size, Direction.EAST) ? Direction.EAST : null;
        }
        if(south)
            return allowEntrance(clipping, x, y, z, size, Direction.SOUTH) ? Direction.SOUTH : null;
        if(north)
            return allowEntrance(clipping, x, y, z, size, Direction.NORTH) ? Direction.NORTH : null;
        return null;
    }

    private static boolean allowEntrance(ClipUtils clipUtils, int x, int y, int z, int size, Direction dir) {
        int dx = dir.deltaX;
        int dy = dir.deltaY;
        if(size == 1) {
            if(dx == -1 && dy == 0 && (clipUtils.getAbs(x - 1, y, z) & WEST_MASK) == 0)
                return true;
            if(dx == 1 && dy == 0 && (clipUtils.getAbs(x + 1, y, z) & EAST_MASK) == 0)
                return true;
            if(dx == 0 && dy == -1 && (clipUtils.getAbs(x, y - 1, z) & SOUTH_MASK) == 0)
                return true;
            if(dx == 0 && dy == 1 && (clipUtils.getAbs(x, y + 1, z) & NORTH_MASK) == 0)
                return true;
            if(dx == -1 && dy == -1 && (clipUtils.getAbs(x - 1, y - 1, z) & SOUTH_WEST_MASK) == 0 && (clipUtils.getAbs(x - 1, y, z) & WEST_MASK) == 0 && (clipUtils.getAbs(x, y - 1, z) & SOUTH_MASK) == 0)
                return true;
            if(dx == 1 && dy == -1 && (clipUtils.getAbs(x + 1, y - 1, z) & SOUTH_EAST_MASK) == 0 && (clipUtils.getAbs(x + 1, y, z) & EAST_MASK) == 0 && (clipUtils.getAbs(x, y - 1, z) & SOUTH_MASK) == 0)
                return true;
            if(dx == -1 && dy == 1 && (clipUtils.getAbs(x - 1, y + 1, z) & NORTH_WEST_MASK) == 0 && (clipUtils.getAbs(x - 1, y, z) & WEST_MASK) == 0 && (clipUtils.getAbs(x, y + 1, z) & NORTH_MASK) == 0)
                return true;
            if(dx == 1 && dy == 1 && (clipUtils.getAbs(x + 1, y + 1, z) & NORTH_EAST_MASK) == 0 && (clipUtils.getAbs(x + 1, y, z) & EAST_MASK) == 0 && (clipUtils.getAbs(x, y + 1, z) & NORTH_MASK) == 0)
                return true;
        } else if(size == 2) {
            if(dx == -1 && dy == 0 && (clipUtils.getAbs(x - 1, y, z) & SOUTH_WEST_MASK) == 0 && (clipUtils.getAbs(x - 1, y + 1, z) & NORTH_WEST_MASK) == 0)
                return true;
            if(dx == 1 && dy == 0 && (clipUtils.getAbs(x + 2, y, z) & SOUTH_EAST_MASK) == 0 && (clipUtils.getAbs(x + 2, y + 1, z) & NORTH_EAST_MASK) == 0)
                return true;
            if(dx == 0 && dy == -1 && (clipUtils.getAbs(x, y - 1, z) & SOUTH_WEST_MASK) == 0 && (clipUtils.getAbs(x + 1, y - 1, z) & SOUTH_EAST_MASK) == 0)
                return true;
            if(dx == 0 && dy == 1 && (clipUtils.getAbs(x, y + 2, z) & NORTH_WEST_MASK) == 0 && (clipUtils.getAbs(x + 1, y + 2, z) & NORTH_EAST_MASK) == 0)
                return true;
            if(dx == -1 && dy == -1 && (clipUtils.getAbs(x - 1, y, z) & 0x124013e) == 0 && (clipUtils.getAbs(x - 1, y - 1, z) & SOUTH_WEST_MASK) == 0 && (clipUtils.getAbs(x, y - 1, z) & 0x124018f) == 0)
                return true;
            if(dx == 1 && dy == -1 && (clipUtils.getAbs(x + 1, y - 1, z) & 0x124018f) == 0 && (clipUtils.getAbs(x + 2, y - 1, z) & SOUTH_EAST_MASK) == 0 && (clipUtils.getAbs(x + 2, y, z) & 0x12401e3) == 0)
                return true;
            if(dx == -1 && dy == 1 && (clipUtils.getAbs(x - 1, y + 1, z) & 0x124013e) == 0 && (clipUtils.getAbs(x - 1, y + 2, z) & NORTH_WEST_MASK) == 0 && (clipUtils.getAbs(x, y + 2, z) & 0x12401f8) == 0)
                return true;
            if(dx == 1 && dy == 1 && (clipUtils.getAbs(x + 1, y + 2, z) & 0x12401f8) == 0 && (clipUtils.getAbs(x + 2, y + 2, z) & NORTH_EAST_MASK) == 0 && (clipUtils.getAbs(x + 2, y + 1, z) & 0x12401e3) == 0)
                return true;
        } else {
            if(dx == -1 && dy == 0 && (clipUtils.getAbs(x - 1, y, z) & SOUTH_WEST_MASK) == 0 && (clipUtils.getAbs(x - 1, y + size - 1, z) & NORTH_WEST_MASK) == 0) {
                for(int i = 1; i < size - 1; i++) {
                    if((clipUtils.getAbs(x - 1, i + y, z) & 0x124013e) != 0)
                        return false;
                }
                return true;
            }
            if(dx == 1 && dy == 0 && (clipUtils.getAbs(x + size, y, z) & SOUTH_EAST_MASK) == 0 && (clipUtils.getAbs(size + x, size + y - 1, z) & NORTH_EAST_MASK) == 0) {
                for(int i = 1; i < size - 1; i++) {
                    if((clipUtils.getAbs(size + x, y + i, z) & 0x12401e3) != 0)
                        return false;
                }
                return true;
            }
            if(dx == 0 && dy == -1 && (clipUtils.getAbs(x, y - 1, z) & SOUTH_WEST_MASK) == 0 && (clipUtils.getAbs(size + x - 1, y - 1, z) & SOUTH_EAST_MASK) == 0) {
                for(int i = 1; i < size - 1; i++) {
                    if((clipUtils.getAbs(x + i, y - 1, z) & 0x124018f) != 0)
                        return false;
                }
                return true;
            }
            if(dx == 0 && dy == 1 && (clipUtils.getAbs(x, y + size, z) & NORTH_WEST_MASK) == 0 && (clipUtils.getAbs(x + size - 1, size + y, z) & NORTH_EAST_MASK) == 0) {
                for(int i = 1; i < size - 1; i++) {
                    if((clipUtils.getAbs(i + x, y + size, z) & 0x12401f8) != 0)
                        return false;
                }
                return true;
            }
            if(dx == -1 && dy == -1 && (clipUtils.getAbs(x - 1, y - 1, z) & SOUTH_WEST_MASK) == 0) {
                for(int i = 1; i < size - 1; i++) {
                    if((clipUtils.getAbs(x - 1, i + (y - 1), z) & 0x124013e) != 0 || ((clipUtils.getAbs(i + (x - 1), y - 1, z) & 0x124018f) != 0))
                        return false;
                }
                return true;
            }
            if(dx == 1 && dy == -1 && (clipUtils.getAbs(x + size, y - 1, z) & SOUTH_EAST_MASK) == 0) {
                for(int i = 1; i < size - 1; i++) {
                    if((clipUtils.getAbs(size + x, y - 1 + i, z) & 0x12401e3) != 0 || (clipUtils.getAbs(i + x, y - 1, z) & 0x124018f) != 0)
                        return false;
                }
                return true;
            }
            if(dx == -1 && dy == 1 && (clipUtils.getAbs(x - 1, size + y, z) & NORTH_WEST_MASK) == 0) {
                for(int i = 1; i < size - 1; i++) {
                    if((clipUtils.getAbs(x - 1, i + y, z) & 0x124013e) != 0 || (clipUtils.getAbs(x - 1 + i, size + y, z) & 0x12401f8) != 0)
                        return false;
                }
                return true;
            }
            if(dx == 1 && dy == 1 && (clipUtils.getAbs(size + x, size + y, z) & NORTH_EAST_MASK) == 0) {
                for(int i = 1; i < size - 1; i++) {
                    if((clipUtils.getAbs(x + i, size + y, z) & 0x12401f8) != 0 || (clipUtils.getAbs(x + size, y + i, z) & 0x12401e3) != 0)
                        return false;
                }
                return true;
            }
        }
        return false;
    }

}
