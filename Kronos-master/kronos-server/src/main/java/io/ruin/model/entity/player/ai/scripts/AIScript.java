package io.ruin.model.entity.player.ai.scripts;

import io.ruin.model.entity.player.ai.AIPlayer;

public abstract class AIScript {

    protected AIPlayer player;

    protected int delay = 1;

    public AIScript(AIPlayer player) {
        this.player = player;

    }

    public abstract void start();


    public abstract boolean run();

    public abstract void finish();

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
