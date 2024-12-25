package io.ruin.model.entity.shared;

import io.ruin.api.buffer.OutBuffer;

public abstract class UpdateMask {

    private boolean sent;

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isSent() {
        return sent;
    }

    public abstract void reset();

    public abstract boolean hasUpdate(boolean justAdded);

    public abstract void send(OutBuffer out, boolean playerUpdate);

    public abstract int get(boolean playerUpdate);

}
