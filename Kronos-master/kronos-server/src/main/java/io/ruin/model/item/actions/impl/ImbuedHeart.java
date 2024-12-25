package io.ruin.model.item.actions.impl;

import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.stat.StatType;

public class ImbuedHeart {

    static {
        ItemAction.registerInventory(20724, 1, (player, item) -> {
            if(DuelRule.NO_DRINKS.isToggled(player)) {
                player.sendMessage("You cannot use an Imbued Heart with drinks disabled.");
                return;
            }
            if(player.imbueHeartCooldown.isDelayed()) {
                int delay = player.imbueHeartCooldown.remaining();
                if(delay >= 100) {
                    int minutes = delay / 100;
                    player.sendMessage("The heart is still drained of its power. Judging by how it feels, it will be ready in around " + minutes + " minutes.");
                } else {
                    int seconds = delay / 10 * 6;
                    player.sendMessage("The heart is still drained of its power. Judging by how it feels, it will be ready in around " + seconds + " seconds.");
                }
            } else {
                player.graphics(1316);
                player.imbueHeartCooldown.delay(700);
                player.getStats().get(StatType.Magic).boost(1, 0.1);
                player.sendMessage("<col=FF0000>Your imbued heart has regained its magical power.");
            }
        });
    }

}
