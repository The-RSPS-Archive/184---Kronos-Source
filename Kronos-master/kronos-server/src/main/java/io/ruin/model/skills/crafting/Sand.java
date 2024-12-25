package io.ruin.model.skills.crafting;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.Tool;

public class Sand {
    static {
        ItemObjectAction.register(Tool.EMPTY_BUCKET, "sand pit", (player, item, obj) -> fill(player));
    }

    private static void fill(Player player) {
        player.startEvent(event -> {
            player.lock();
            player.animate(895);
            event.delay(1);
            player.getInventory().collectItems(Tool.EMPTY_BUCKET).forEach(item -> item.setId(1783));
            player.unlock();
            player.sendFilteredMessage("You fill your buckets with sand.");
        });
    }
}
