package io.ruin.model.map.route.types;

import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.route.RouteType;

/**
 * Used for npc walking in client. (Hmmm?)
 */
public class RouteRelative extends RouteType {

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
        return clipUtils.method3066(x, y, size, this.x, this.y, xLength, yLength, someDirection);
    }

}
