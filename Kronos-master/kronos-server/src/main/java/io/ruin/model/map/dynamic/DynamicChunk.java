package io.ruin.model.map.dynamic;

// ~~~ ROTATIONS ~~~ \\
// 0 = south, 1 = west \\
// 2 = north, 3 = east \\
// ~~~~~~~~~~~~~~~~~~~~ \\
public class DynamicChunk {

    public final int x, y, z;

    public final int regionId;

    public int pointX, pointY, pointZ; //build points

    public int rotation;

    public DynamicChunk(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.regionId = ((x / 8) << 8) + (y / 8);
    }

    public DynamicChunk pos(int pointX, int pointY, int pointZ) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.pointZ = pointZ;
        return this;
    }

    public DynamicChunk rotate(int rotation) {
        this.rotation = rotation;
        return this;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public static int rotatedX(int localX, int localY, int chunkRotation) {
        if(chunkRotation == 0)
            return localX;
        if(chunkRotation == 1)
            return localY;
        if(chunkRotation == 2)
            return 7 - localX;
        return 7 - localY;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public static int rotatedX(int localX, int localY, int chunkRotation, int xLength, int yLength, int direction) {
        if((direction & 0x1) == 1) {
            int oldXLength = xLength;
            xLength = yLength;
            yLength = oldXLength;
        }
        if(chunkRotation == 0)
            return localX;
        if(chunkRotation == 1)
            return localY;
        if(chunkRotation == 2)
            return 7 - localX - (xLength - 1);
        return 7 - localY - (yLength - 1);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public static int rotatedY(int localX, int localY, int chunkRotation) {
        if(chunkRotation == 0)
            return localY;
        if(chunkRotation == 1)
            return 7 - localX;
        if(chunkRotation == 2)
            return 7 - localY;
        return localX;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public static int rotatedY(int localX, int localY, int chunkRotation, int xLength, int yLength, int direction) {
        if((direction & 0x1) == 1) {
            int oldXLength = xLength;
            xLength = yLength;
            yLength = oldXLength;
        }
        if(chunkRotation == 0)
            return localY;
        if(chunkRotation == 1)
            return 7 - localX - (xLength - 1);
        if(chunkRotation == 2)
            return 7 - localY - (yLength - 1);
        return localX;
    }

}