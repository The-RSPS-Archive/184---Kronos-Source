package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;

public class Lithkren {

    static {
        ObjectAction.register(32153, 1, (player, obj) -> {
            player.startEvent(event -> {
                int x = player.getAbsX();
                int y = player.getAbsY();

                switch (obj.direction) {
                    case 0:
                        if(y > obj.y)
                            y -= 2;
                        else
                            y += 2;
                        break;
                    case 1:
                    case 3:
                        if(x < obj.x)
                            x += 2;
                        else
                            x -= 2;
                    default: break;
                }
                player.lock();
                player.stepAbs(x, y, StepType.FORCE_WALK);
                final int finalX = x;
                final int finalY = y;
                event.waitForCondition(() -> player.getPosition().equals(finalX, finalY), 5);
                player.unlock();
            });

        });
        ObjectAction.register(32144, "read", (player, obj) -> player.dialogue(new MessageDialogue("You have killed " + NumberUtils.formatNumber(player.adamantDragonKills.getKills()) + " adamant dragons and " + NumberUtils.formatNumber(player.runeDragonKills.getKills()) + " rune dragons.")));
        ObjectAction.register(32117, "enter", (player, obj) -> player.getMovement().teleport(1568, 5060, 0));
        ObjectAction.register(32132, 1, (player, obj) -> player.getMovement().teleport(3549, 10482, 0));
        ObjectAction.register(32113, 1, (player, obj) -> {
            if (player.getAbsY() >= 10471)
                player.getMovement().teleport(player.getAbsX(), 10467, 0);
            else
                player.getMovement().teleport(player.getAbsX(), 10473, 0);
        });
        ObjectAction.register(32112, 1, (player, obj) -> player.getMovement().teleport(3555, 4002, 0));
        ObjectAction.register(32080, 1, (player, obj) -> player.getMovement().teleport(3549, 10449, 0));
        ObjectAction.register(32084, 1, (player, obj) -> player.getMovement().teleport(3555, 4000, 0));
        ObjectAction.register(32082, 1, (player, obj) -> player.getMovement().teleport(3561, 4004, 0));
        ObjectAction.register(32081, 1, (player, obj) -> player.getMovement().teleport(3556, 4004, 1));
    }
}
