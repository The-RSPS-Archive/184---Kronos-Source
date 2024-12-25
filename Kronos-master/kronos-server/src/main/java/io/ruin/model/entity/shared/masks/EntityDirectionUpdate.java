package io.ruin.model.entity.shared.masks;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.entity.shared.UpdateMask;

public class EntityDirectionUpdate extends UpdateMask {

    public int direction = -1;
    private boolean update;
    private int stage;

    public void set(int direction, boolean temporary) {
        this.direction = direction;
        this.stage = (temporary ? 1 : 0);
        this.update = true;
        setSent(false);
    }

    public void remove(boolean delay) {
        if(delay) {
            /**
             * Delay Remove
             */
            stage = 1;
            setSent(true);
        } else {
            /**
             * Remove
             */
            set(-1, false);
        }
    }

    @Override
    public void reset() {
        update = false;
        if(stage == 1) {
            /**
             * Queue remove
             */
            stage = 2;
            setSent(true);
        } else if(stage == 2) {
            /**
             * Remove
             */
            remove(false);
        }
    }

    @Override
    public boolean hasUpdate(boolean added) {
        return update || (added && direction != -1);
    }

    @Override
    public void send(OutBuffer out, boolean playerUpdate) {
        if(playerUpdate)
            out.addLEShortA(direction);
        else
            out.addShortA(direction);
    }

    @Override
    public int get(boolean playerUpdate) {
        return playerUpdate ? 64 : 2;
    }

}
