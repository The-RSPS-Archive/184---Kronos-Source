package io.ruin.model.item.listeners;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

@FunctionalInterface
public interface OutgoingHitListener {
    /**
     * @param player player that triggered this event
     * @param item   item that triggered this event
     * @param hit    the hit that triggered this event
     * @param target entity this hit is being dealt to
     */
    void accept(Player player, Item item, Hit hit, Entity target);

    default OutgoingHitListener andThen(OutgoingHitListener other) {
        return (player, item, hit, victim) -> {
            accept(player, item, hit, victim);
            other.accept(player, item, hit, victim);
        };
    }
}
