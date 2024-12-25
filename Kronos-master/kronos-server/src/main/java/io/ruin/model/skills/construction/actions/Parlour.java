package io.ruin.model.skills.construction.actions;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.firemaking.Burning;
import io.ruin.model.stat.StatType;

public class Parlour {
    static {
        for (Buildable fireplace: Buildable.FIREPLACES) {
            int unlit = fireplace.getBuiltObjects()[0];
            ObjectAction.register(unlit, "light", (player, obj) -> {
                if (!player.getInventory().contains(Burning.NORMAL.itemId, 1) || !player.getInventory().contains(Tool.TINDER_BOX, 1)) {
                    player.sendMessage("You'll need a tinderbox and some regular logs to light the fireplace.");
                    return;
                }
                player.startEvent(event -> {
                    player.lock();
                    player.animate(733);
                    event.delay(4);
                    player.getInventory().remove(Burning.NORMAL.itemId, 1);
                    player.getStats().addXp(StatType.Firemaking, 30, true);
                    obj.setId(unlit + 1);
                    player.unlock();
                    player.animate(-1);
                    player.sendMessage("You light the fireplace.");
                });
            });
        }
    }
}
