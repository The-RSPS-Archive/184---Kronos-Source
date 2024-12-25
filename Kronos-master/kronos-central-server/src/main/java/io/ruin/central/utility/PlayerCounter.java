package io.ruin.central.utility;

import io.ruin.api.process.ProcessWorker;
import io.ruin.central.Server;

public class PlayerCounter {
    private static PlayerCounter instance;
    private int count = -1;
    private boolean updated;

    public static void update(int count) {
    }

    private PlayerCounter() {
        ProcessWorker worker = Server.newWorker("player-counter", 1000L, 1);
        worker.queue(() -> {
                    if (!this.updated) {
                        return false;
                    }
                    this.updated = false;
                    return false;
                }
        );
    }
}

