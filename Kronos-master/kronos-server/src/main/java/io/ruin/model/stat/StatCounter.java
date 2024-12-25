package io.ruin.model.stat;

import com.google.gson.annotations.Expose;
import io.ruin.model.entity.player.Player;

public class StatCounter {

    @Expose public int type, startXp, endXp;

    public int index;

    public StatCounter(int index) {
        this.index = index;
    }

    public void send(Player player) {
    }

}