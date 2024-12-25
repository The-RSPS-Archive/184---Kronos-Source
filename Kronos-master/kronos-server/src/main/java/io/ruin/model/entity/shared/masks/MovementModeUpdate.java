package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.shared.UpdateMask;

public class MovementModeUpdate extends UpdateMask {

    private boolean update;

    private int mode;

    public void set(int mode) {
        if(this.mode == mode)
            return;
        this.mode = mode;
        this.update = true;
    }

    @Override
    public void reset() {
        update = false;
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return update || added;
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate) {
        out.addByte(mode);
    }

    @Override
    public int get(boolean playerUpdate) {
        return 4096;
    }

}