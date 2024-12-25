package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.shared.UpdateMask;

public class ForceMovementUpdate extends UpdateMask {

    private int diffX1, diffY1;
    private int diffX2, diffY2;
    private int speed1;
    private int speed2;
    private int direction;
    public boolean updated = false;

    public void set(int diffX1, int diffY1, int diffX2, int diffY2, int speed1, int speed2, int direction) {
        this.diffX1 = diffX1;
        this.diffY1 = diffY1;
        this.diffX2 = diffX2;
        this.diffY2 = diffY2;
        this.speed1 = speed1;
        this.speed2 = speed2;
        this.direction = direction;
        this.updated = true;
    }

    @Override
    public void reset() {
        updated = false;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return updated;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate) {
        out.addByteA(diffX1);
        out.addByteS(diffY1);
        out.addByteA(diffX2);
        out.addByteA(diffY2);
        out.addShort(speed1);
        out.addLEShortA(speed2);
        out.addShortA(direction);
    }

    @Override
    public int get(boolean playerUpdate) {
        return 1024;
    }

}