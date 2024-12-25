package io.ruin.model.map.route.types;

import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.route.RouteType;

/**
 * Not sure. (Player route where size > 1 ?)
 */
public class RouteType_Sub4 extends RouteType {

    public void set(int x, int y, int xLength, int yLength) {
        this.x = x;
        this.y = y;
        this.xLength = xLength;
        this.yLength = yLength;
    }

    @Override
    public boolean method4274(int size, int x, int y, ClipUtils clipUtils) {
        return ClipUtils.withDistance(x, y, this.x, this.y, size, size, xLength, yLength);
    }

}