package io.ruin.model.entity.shared.listeners;

import io.ruin.model.entity.player.Player;

import java.util.ArrayList;

public interface DailyResetListener {

    ArrayList<DailyResetListener> LISTENERS = new ArrayList<>();

    void onReset(Player player);

    static void register(DailyResetListener listener) {
        LISTENERS.add(listener);
    }

    static void executeAll(Player player) {
        for(DailyResetListener listener : LISTENERS)
            listener.onReset(player);
    }

}
