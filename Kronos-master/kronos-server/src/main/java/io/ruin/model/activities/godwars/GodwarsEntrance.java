package io.ruin.model.activities.godwars;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;

public class GodwarsEntrance {

    static {
        ObjectAction.register(26419, 1, (player, obj) -> {
            if (Config.GODWARS_DUNGEON.get(player) == 0) {
                Item rope = player.getInventory().findItem(954);
                if(rope == null) {
                    player.sendFilteredMessage("You aren't carrying a rope with you.");
                    return;
                }

                rope.remove();
                Config.GODWARS_DUNGEON.set(player, 1);
            } else {
                player.startEvent(event -> {
                    player.lock();
                    player.animate(828);
                    event.delay(1);
                    player.getMovement().teleport(2882, 5311, 2);
                    player.unlock();
                });
            }
        });
        ObjectAction.register(26370, 1, (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(828);
            event.delay(1);
            player.getMovement().teleport(2916, 3746, 0);
            player.unlock();
        }));
    }
}
