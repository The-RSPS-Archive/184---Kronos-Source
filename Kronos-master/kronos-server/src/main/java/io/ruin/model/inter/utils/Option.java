package io.ruin.model.inter.utils;

import io.ruin.model.entity.player.Player;

import java.util.function.Consumer;

public class Option {

    public final String name;

    public final Consumer<Player> consumer;

    public Option(String name) {
        this(name, p -> {});
    }

    public Option(String name, Runnable runnable) {
        this(name, p -> runnable.run());
    }

    public Option(String name, Consumer<Player> consumer) {
        this.name = name;
        this.consumer = consumer;
    }

    public void select(Player player) {
        consumer.accept(player);
    }

}