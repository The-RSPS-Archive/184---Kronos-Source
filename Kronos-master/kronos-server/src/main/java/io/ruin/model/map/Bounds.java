package io.ruin.model.map;

import io.ruin.api.utils.Random;

import java.awt.*;
import java.util.function.Consumer;

public class Bounds {

    public final int swX, swY;

    public final int neX, neY;

    public final int z;

    private int sides;
    private int[][] points;

    public Bounds(Position position, int range) {
        this(position.getX(), position.getY(), position.getZ(), range);
    }

    public Bounds(Position swPosition, Position nePosition, int z) {
        this(swPosition.getX(), swPosition.getY(), nePosition.getX(), nePosition.getY(), z);
    }

    public Bounds(int x, int y, int z, int range) {
        this(x - range, y - range, x + range, y + range, z);
    }

    public Bounds(int swX, int swY, int neX, int neY, int z) {
        this.swX = swX;
        this.swY = swY;
        this.neX = neX;
        this.neY = neY;
        this.z = z;
    }

    public void forEachPos(Consumer<Position> consumer) {
        int minZ, maxZ;
        if(z == -1) {
            minZ = 0;
            maxZ = 3;
        } else {
            minZ = z;
            maxZ = minZ;
        }
        for(int z = minZ; z <= maxZ; z++) {
            for(int x = swX; x <= neX; x++) {
                for(int y= swY; y <= neY; y++)
                    consumer.accept(new Position(x, y, z));
            }
        }
    }

    public boolean inBounds(int x, int y, int z, int range) {
        return !(this.z != -1 && z != this.z) && x >= swX - range && x <= neX + range && y >= swY - range && y <= neY + range;
    }

    public boolean inBounds(int[][] bounds, Position position) {
        boolean inside = false;
        int x = position.getX(), y = position.getY();
        for (int i = 0, j = bounds.length - 1; i < bounds.length; j = i++) {
            if ((bounds[i][1] < y && bounds[j][1] >= y) || (bounds[j][1] < y && bounds[i][1] >= y)) {
                if (bounds[i][0] + (y - bounds[i][1]) / (bounds[j][1] - bounds[i][1]) * (bounds[j][0] - bounds[i][0]) < x) {
                    inside = !inside;
                }
            }
        }
        return inside;
    }

    public boolean intersects(Bounds other) {
        return swX < other.neX && neX > other.swX && swY < other.neY && neY > other.swY;
    }


    public Position randomPosition() {
        return new Position(randomX(), randomY(), z == -1 ? Random.get(0, 3) : z);
    }

    public int randomX() {
        return Random.get(swX, neX);
    }

    public int randomY() {
        return Random.get(swY, neY);
    }

    public Region getRegion() {
        return Region.get(swX, swY);
    }

    /**
     * Misc
     */

    public static Bounds fromRegion(int regionId) {
        return fromRegion(regionId, -1);
    }

    public static Bounds fromRegion(int regionId, int z) {
        int baseRegionX = (regionId >> 8) * 64, baseRegionY = (regionId & 0xff) * 64;
        return new Bounds(baseRegionX, baseRegionY, baseRegionX + 63, baseRegionY + 63, z);
    }

    public static Bounds fromRegions(int... regionIds) {
        Bounds sw = null, ne = null;
        for(int regionId : regionIds) {
            Bounds bounds = fromRegion(regionId);
            if(sw == null || (bounds.swX < sw.swX && bounds.swY < sw.swY))
                sw = bounds;
            if(ne == null || (bounds.neX > ne.neX && bounds.neY > ne.neY))
                ne = bounds;
        }
        return new Bounds(sw.swX, sw.swY, ne.neX, ne.neY, -1);
    }

}
