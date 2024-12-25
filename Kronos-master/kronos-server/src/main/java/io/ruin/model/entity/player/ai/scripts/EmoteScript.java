package io.ruin.model.entity.player.ai.scripts;

import io.ruin.model.entity.player.ai.AIPlayer;
import io.ruin.model.inter.handlers.TabEmote;

public class EmoteScript extends AIScript {

    int index = 12;

    public EmoteScript(AIPlayer player) {
        super(player);
    }

    @Override
    public void start() {

    }

    @Override
    public boolean run() {
        player.animate(TabEmote.values()[index++].animationID);
        setDelay(2);
        return false;
    }

    @Override
    public void finish() {

    }
}
