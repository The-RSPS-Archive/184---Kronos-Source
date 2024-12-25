package io.ruin.model.map.route.routes;

import io.ruin.model.entity.Entity;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;

import static io.ruin.model.map.route.RouteFinder.*;

public class ProjectileRoute {

    public static boolean allow(Entity entity, Entity target) {
        return allow(entity.getAbsX(), entity.getAbsY(), entity.getHeight(), entity.getSize(), target.getAbsX(), target.getAbsY(), target.getSize());
    }

    public static boolean allow(Entity entity, Position dest) {
        return allow(entity.getAbsX(), entity.getAbsY(), entity.getHeight(), entity.getSize(), dest.getX(), dest.getY(), 1);
    }

    public static boolean allow(Entity entity, int destX, int destY) {
        return allow(entity.getAbsX(), entity.getAbsY(), entity.getHeight(), entity.getSize(), destX, destY, 1);
    }

    public static boolean allow(int absX, int absY, int z, int size, int targetX, int targetY, int targetSize) {
        targetX = targetX * 2 + targetSize - 1;
        targetY = targetY * 2 + targetSize - 1;

        absX = absX * 2 + size - 1;
        absY = absY * 2 + size - 1;

        if((targetX & 0x1) != 0)
            targetX += targetX > absX ? -1 : 1;
        if((targetY & 0x1) != 0)
            targetY += targetY > absY ? -1 : 1;

        if((absX & 0x1) != 0)
            absX += absX > targetX ? -1 : 1;
        if((absY & 0x1) != 0)
            absY += absY > targetY ? -1 : 1;

        return allow(absX >> 1, absY >> 1, z, targetX >> 1, targetY >> 1);
    }

    public static boolean allow(int absX, int absY, int z, int destX, int destY) {
        int dx = Math.abs(destX - absX);
        int dy = Math.abs(destY - absY);
        int sx = absX < destX ? 1 : -1;
        int sy = absY < destY ? 1 : -1;
        int err = dx - dy;
        int err2;
        int oldX = absX;
        int oldY = absY;
        while(true) {
            err2 = err << 1;
            if(err2 > -dy) {
                err -= dy;
                absX += sx;
            }
            if(err2 < dx) {
                err += dx;
                absY += sy;
            }
            if(!allowEntrance(oldX, oldY, z, (absX - oldX), (absY - oldY)))
                return false;
            if(absX == destX && absY == destY)
                return true;
            oldX = absX;
            oldY = absY;
        }
    }

    private static boolean allowEntrance(int x, int y, int z, int dx, int dy) {
        if(dx == -1 && dy == 0 && (getClipping(x - 1, y, z) & WEST_MASK) == 0)
            return true;
        if(dx == 1 && dy == 0 && (getClipping(x + 1, y, z) & EAST_MASK) == 0)
            return true;
        if(dx == 0 && dy == -1 && (getClipping(x, y - 1, z) & SOUTH_MASK) == 0)
            return true;
        if(dx == 0 && dy == 1 && (getClipping(x, y + 1, z) & NORTH_MASK) == 0)
            return true;
        if(dx == -1 && dy == -1 && (getClipping(x - 1, y - 1, z) & SOUTH_WEST_MASK) == 0 && (getClipping(x - 1, y, z) & WEST_MASK) == 0 && (getClipping(x, y - 1, z) & SOUTH_MASK) == 0)
            return true;
        if(dx == 1 && dy == -1 && (getClipping(x + 1, y - 1, z) & SOUTH_EAST_MASK) == 0 && (getClipping(x + 1, y, z) & EAST_MASK) == 0 && (getClipping(x, y - 1, z) & SOUTH_MASK) == 0)
            return true;
        if(dx == -1 && dy == 1 && (getClipping(x - 1, y + 1, z) & NORTH_WEST_MASK) == 0 && (getClipping(x - 1, y, z) & WEST_MASK) == 0 && (getClipping(x, y + 1, z) & NORTH_MASK) == 0)
            return true;
        if(dx == 1 && dy == 1 && (getClipping(x + 1, y + 1, z) & NORTH_EAST_MASK) == 0 && (getClipping(x + 1, y, z) & EAST_MASK) == 0 && (getClipping(x, y + 1, z) & NORTH_MASK) == 0)
            return true;
        return false;
    }

    private static int getClipping(int x, int y, int z) {
        Tile tile = Tile.get(x, y, z);
        return tile == null ? 0 : tile.projectileClipping;
    }

}