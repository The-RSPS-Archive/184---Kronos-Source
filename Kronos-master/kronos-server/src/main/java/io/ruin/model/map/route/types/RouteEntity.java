package io.ruin.model.map.route.types;

import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.route.RouteType;

/**
 * Used for walking to entities.
 */
public class RouteEntity extends RouteType {

    int someDirection; //? always 0 on client

    public void set(int x, int y, int xLength, int yLength, int someDirection) {
        this.x = x;
        this.y = y;
        this.xLength = xLength;
        this.yLength = yLength;
        this.someDirection = someDirection;
    }

    @Override
    public boolean method4274(int size, int x, int y, ClipUtils clipUtils) {
        return clipUtils.canStep(x, y, size, size, this.x, this.y, xLength, yLength, someDirection);
    }

}