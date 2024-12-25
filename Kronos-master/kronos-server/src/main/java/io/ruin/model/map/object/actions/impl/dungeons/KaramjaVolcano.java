package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class KaramjaVolcano { // Elvarg entrance is handled in Elvarg class

    static {
        ObjectAction.register(18969, "climb", (player, obj) -> player.getMovement().teleport(2856, 3170, 0));
        ObjectAction.register(11441, "climb", (player, obj) -> Ladder.climb(player, 2856, 9570, 0, false, true, false));
        ObjectAction.register(2606, "open", (player, obj) -> {
            player.startEvent(event -> {
                player.lock();
                player.getRouteFinder().routeAbsolute(2836, player.getAbsY() >= 9600 ? 9600 : 9599);
                event.waitForMovement(player);
                if (!obj.isRemoved())
                    obj.remove();
                GameObject wall = GameObject.spawn(25140, obj.x, obj.y, 0, 0, 0);
                player.step(0, player.getAbsY() >= 9600 ? -1 : 1, StepType.FORCE_WALK);
                event.waitForMovement(player);
                wall.remove();
                obj.setId(2606);
                player.unlock();
            });
        });

        ObjectAction.register(25154, "enter", (player, obj) -> player.getMovement().teleport(2833, 9656, 0));
        ObjectAction.register(25213, "climb", (player, obj) -> player.getMovement().teleport(2834, 3258, 0));

    }

}
