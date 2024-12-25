package io.ruin.model.entity.shared.listeners;

import io.ruin.model.entity.player.Player;

import java.util.ArrayList;

public interface LoginListener {

    ArrayList<LoginListener> LISTENERS = new ArrayList<>();

    void onLogin(Player player);

    static void register(LoginListener listener) {
        LISTENERS.add(listener);
        LISTENERS.trimToSize(); //only okay because on startup
    }

    static void executeAll(Player player) {
        for(LoginListener listener : LISTENERS)
            listener.onLogin(player);
    }

}