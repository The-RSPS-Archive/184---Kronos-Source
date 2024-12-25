package io.ruin.model.skills.construction.seat;

import io.ruin.model.entity.player.Player;

public interface Seat {

    void stand(Player player);

    int getEatAnimation(Player player);

    void restore(Player player);
}
