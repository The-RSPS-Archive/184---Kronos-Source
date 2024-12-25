package io.ruin.model.activities.wilderness;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class AxeHut {

    private static final int LOCKPICK = 1523;
    private static final Bounds AXE_HUT = new Bounds(3187, 3958, 3194, 3962, 0);

    private static void openDoor(Player player, GameObject door, int stepX, int stepY, int openX, int openY, int openDirection, int yDif) {
        if (!player.getPosition().inBounds(AXE_HUT)) {
            player.sendMessage("The door is locked.");
            return;
        }
        player.startEvent(event -> {
            player.lock();

            if (player.getAbsX() != door.x || player.getAbsY() != door.y + yDif) {
                player.stepAbs(door.x, door.y, StepType.FORCE_WALK);
                event.delay(1);
            }
            GameObject opened = GameObject.spawn(1548, openX, openY, door.z, door.type, openDirection);
            door.skipClipping(true).remove();
            player.stepAbs(stepX, stepY, StepType.FORCE_WALK);
            player.sendMessage("You go through the door.");
            event.delay(2);
            door.restore().skipClipping(false);
            opened.remove();

            player.unlock();
        });
    }

    private static void picklockDoor(Player player, GameObject door, int stepX, int stepY, int openX, int openY, int openDirection) {
        if (player.getPosition().inBounds(AXE_HUT)) {
            player.sendMessage("The door is already unlocked.");
            return;
        }
        if (!player.getStats().check(StatType.Thieving, 39, "pick lock this door"))
            return;
        if (!player.getInventory().hasId(LOCKPICK)) {
            player.sendMessage("You need a lockpick for this.");
            return;
        }
        player.sendMessage("You attempt to pick the lock.");
        if (Random.rollDie(2, 1)) {
            player.sendMessage("You manage to pick the lock.");
            PlayerCounter.PICKED_LOCKS.increment(player, 1);
            player.startEvent(event -> {
                player.lock();

                if (player.getAbsX() != door.x || player.getAbsY() != door.y) {
                    player.stepAbs(door.x, door.y, StepType.FORCE_WALK);
                    event.delay(1);
                }
                GameObject opened = GameObject.spawn(1548, openX, openY, door.z, door.type, openDirection);
                door.skipClipping(true).remove();
                player.stepAbs(stepX, stepY, StepType.FORCE_WALK);
                player.getStats().addXp(StatType.Thieving, 22.0, false);
                event.delay(2);
                door.restore().skipClipping(false);
                opened.remove();

                player.unlock();
            });
            return;
        }
        player.sendMessage("You fail to the pick lock.");

    }

    static {
        ObjectAction.register(11726, 3190, 3957, 0, "open", (player, obj) -> openDoor(player, obj, 3190, 3957, 3190, 3958, 2, + 1));
        ObjectAction.register(11726, 3191, 3963, 0, "open", (player, obj) -> openDoor(player, obj, 3191, 3963, 3191, 3962, 0, - 1));

        ObjectAction.register(11726, 3190, 3957, 0, "pick-lock", (player, obj) -> picklockDoor(player, obj, 3190, 3958, 3190, 3958, 2));
        ObjectAction.register(11726, 3191, 3963, 0, "pick-lock", (player, obj) -> picklockDoor(player, obj, 3191, 3962, 3191, 3962, 0));
    }
}
