package io.ruin.model.map.route.types;

import io.ruin.model.map.ClipUtils;
import io.ruin.model.map.object.ObjectType;
import io.ruin.model.map.route.RouteType;

/**
 * Used for walking to objects.
 */
public class RouteObject extends RouteType {

    ObjectType type;
    int direction;
    Integer someDirection;

    public void set(int x, int y, int xLength, int yLength, ObjectType objectType, int direction, Integer someDirection) {
        this.x = x;
        this.y = y;
        this.xLength = xLength;
        this.yLength = yLength;
        this.type = objectType;
        this.direction = direction;
        this.someDirection = someDirection;
    }

    @Override
    public boolean method4274(int size, int x, int y, ClipUtils clipUtils) {
        if(someDirection != null && clipUtils.method3066(x, y, size, this.x, this.y, xLength, yLength, someDirection))
            return true;
        return clipUtils.method3065(x, y, size, this.x, this.y, type.value, direction);
    }

}
