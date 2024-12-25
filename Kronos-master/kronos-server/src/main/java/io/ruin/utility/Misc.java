package io.ruin.utility;

import io.ruin.model.entity.Entity;
import io.ruin.model.map.Position;

public class Misc {

    private static long lastUpdateTime = 0;
    private static long timeCorrection = 0;

    public static synchronized long currentTimeCorrectedMillis() {
        long current = System.currentTimeMillis();
        if (current < lastUpdateTime)
            timeCorrection += lastUpdateTime - current;
        lastUpdateTime = current;
        return current + timeCorrection;
    }

    public static int getDistance(Position src, Position dest) {
        return getDistance(src.getX(), src.getY(), dest.getX(), dest.getY());
    }

    public static int getDistance(Position src, int destX, int destY) {
        return getDistance(src.getX(), src.getY(), destX, destY);
    }

    public static int getDistance(int x1, int y1, int x2, int y2) {
        int diffX = Math.abs(x1 - x2);
        int diffY = Math.abs(y1 - y2);
        return Math.max(diffX, diffY);
    }

    public static int getClosestX(Entity src, Entity target) {
        return getClosestX(src, target.getPosition());
    }

    public static int getClosestX(Entity src, Position target) {
        if (src.getSize() == 1)
            return src.getAbsX();
        if (target.getX() < src.getAbsX())
            return src.getAbsX();
        else if (target.getX() >= src.getAbsX() && target.getX() <= src.getAbsX() + src.getSize() - 1)
            return target.getX();
        else
            return src.getAbsX() + src.getSize() - 1;
    }

    public static int getClosestY(Entity src, Entity target) {
        return getClosestY(src, target.getPosition());
    }

    public static int getClosestY(Entity src, Position target) {
        if (src.getSize() == 1)
            return src.getAbsY();
        if (target.getY() < src.getAbsY())
            return src.getAbsY();
        else if (target.getY() >= src.getAbsY() && target.getY() <= src.getAbsY() + src.getSize() - 1)
            return target.getY();
        else
            return src.getAbsY() + src.getSize() - 1;
    }

    public static Position getClosestPosition(Entity src, Entity target) {
        return new Position(getClosestX(src, target), getClosestY(src, target), src.getHeight());
    }

    public static Position getClosestPosition(Entity src, Position target) {
        return new Position(getClosestX(src, target), getClosestY(src, target), src.getHeight());
    }

    public static int getEffectiveDistance(Entity src, Entity target) {
        Position pos = getClosestPosition(src, target);
        Position pos2 = getClosestPosition(target, src);
        return getDistance(pos, pos2);
    }

}
