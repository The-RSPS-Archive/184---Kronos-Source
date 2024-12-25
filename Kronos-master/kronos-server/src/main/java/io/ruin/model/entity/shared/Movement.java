package io.ruin.model.entity.shared;

import io.ruin.model.entity.Entity;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;

public class Movement {

    /**
     * Offsets
     */

    public int readOffset, writeOffset;

    public boolean isAtDestination() {
        return readOffset >= writeOffset;
    }

    /**
     * Movement directions
     */

    public int walkDirection = -1, runDirection = -1;

    public int getWalkDirection() {
        return walkDirection;
    }

    public int getRunDirection() {
        return runDirection;
    }

    /**
     * Steps
     */

    public StepType stepType = StepType.NORMAL;

    protected int[] stepsX, stepsY;

    protected boolean step(Entity entity) {
        if(isAtDestination())
            return false;

        int stepX = stepsX[readOffset];
        int stepY = stepsY[readOffset];

        Position position = entity.getPosition();
        int absX = position.getX();
        int absY = position.getY();
        int diffX = stepX - absX;
        int diffY = stepY - absY;

        if(diffX < 0)
            diffX = -1;
        else if(diffX > 0)
            diffX = 1;

        if(diffY < 0)
            diffY = -1;
        else if(diffY > 0)
            diffY = 1;

        int newX = absX + diffX;
        int newY = absY + diffY;

        if(!entity.getRouteFinder().allowStep(newX, newY))
            return false;

        position.set(newX, newY);
        if(newX == stepX && newY == stepY)
            readOffset++;
        return true;
    }

    public int[] getStepsX() {
        if(stepsX == null)
            stepsX = new int[50];
        return stepsX;
    }

    public int[] getStepsY() {
        if(stepsY == null)
            stepsY = new int[50];
        return stepsY;
    }

    /**
     * Reset
     */

    public void reset() {
        readOffset = 0;
        writeOffset = 0;
        stepType = StepType.NORMAL;
    }

    /**
     * Teleporting
     */

    protected int teleportStage = -1;

    public int teleportX, teleportY, teleportZ;

    public void teleport(int x, int y) {
        teleport(x, y, 0);
    }

    public void teleport(int[] coords) {
        teleport(coords[0], coords[1], coords[2]);
    }

    public void teleport(Position position) {
        teleport(position.getX(), position.getY(), position.getZ());
    }

    public void teleport(Bounds bounds) {
        teleport(bounds.randomX(), bounds.randomY(), bounds.z);
    }

    public void teleport(int x, int y, int z) {
        teleportStage = 0;
        teleportX = x;
        teleportY = y;
        teleportZ = z;
    }

    public boolean finishTeleport(Position position) {
        if(teleportStage == 0) {
            teleportStage = 1;
            position.set(teleportX, teleportY, teleportZ);
            reset();
            return true;
        }
        if(teleportStage == 1)
            teleportStage = -1;
        return false;
    }

    public boolean isTeleportQueued() {
        return teleportStage == 0;
    }

    public boolean hasTeleportUpdate() {
        return teleportStage == 1;
    }

    public boolean teleporting() {
        return teleportStage != -1;
    }

    /**
     * Misc
     */

    public boolean hasMoved() {
        return walkDirection != -1 || runDirection != -1 || hasTeleportUpdate();
    }

}