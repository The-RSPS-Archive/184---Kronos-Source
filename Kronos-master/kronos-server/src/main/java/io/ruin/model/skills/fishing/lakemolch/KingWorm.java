package io.ruin.model.skills.fishing.lakemolch;

import io.ruin.model.map.object.actions.ObjectAction;

public class KingWorm {

    private static final int KING_WORM_OBJECT = 34648;
    private static final int KING_WORM_ITEM = 2162;

    static {
        ObjectAction.register(KING_WORM_OBJECT, "take", (player, obj) -> {
            if(player.getInventory().isFull()) {
                player.sendFilteredMessage("You need at least 1 inventory space to do this.");
                return;
            }
            player.startEvent(event -> {
               player.lock();
               player.animate(827);
               event.delay(1);
               player.getInventory().add(KING_WORM_ITEM, 1);
               player.unlock();
            });
        });
    }

}
