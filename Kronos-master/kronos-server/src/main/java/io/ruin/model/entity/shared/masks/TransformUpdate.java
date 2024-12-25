package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.shared.UpdateMask;

public class TransformUpdate extends UpdateMask {

    private int id = -1;

    public void set(int id) {
        this.id = id;
    }

    @Override
    public void reset() {
        id = -1;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return id != -1;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate) {
        out.addLEShortA(id);
    }

    @Override
    public int get(boolean playerUpdate) {
        return 32;
    }

}
