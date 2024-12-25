package io.ruin.model.inter.dialogue;

import io.ruin.model.entity.player.Player;

public interface Dialogue {

    void open(Player player);

    default void closed(Player player) {}

}