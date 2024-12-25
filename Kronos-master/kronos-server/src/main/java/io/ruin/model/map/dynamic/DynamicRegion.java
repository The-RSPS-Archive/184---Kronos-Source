package io.ruin.model.map.dynamic;

import io.ruin.model.map.Region;

public class DynamicRegion {

    private Region region;

    private int fromRegionBaseX, fromRegionBaseY;

    public int getX(int baseX) {
        int localX = baseX - fromRegionBaseX;
        return region.baseX + localX;
    }

    public int getY(int baseY) {
        int localY = baseY - fromRegionBaseY;
        return region.baseY + localY;
    }

    public int revertX(int x) {
        int localX = x - region.baseX;
        return fromRegionBaseX + localX;
    }

    public int revertY(int y) {
        int localY = y - region.baseY;
        return fromRegionBaseY + localY;
    }

}
