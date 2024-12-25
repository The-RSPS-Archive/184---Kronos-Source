package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.shared.UpdateMask;

public class GraphicsUpdate extends UpdateMask {

    private int id = -2;

    private int height, delay;

    public void set(int id, int height, int delay) {
        this.id = id;
        this.height = height;
        this.delay = delay;
    }

    @Override
    public void reset() {
        id = -2;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return id != -2;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate) {
        if(playerUpdate) {
            out.addShort(id);
            out.addInt(height << 16 | delay);
        } else {
            out.addLEShortA(id);
            out.addInt1(height << 16 | delay);
        }
    }

    @Override
    public int get(boolean playerUpdate) {
        return playerUpdate ? 2048 : 8;
    }

}