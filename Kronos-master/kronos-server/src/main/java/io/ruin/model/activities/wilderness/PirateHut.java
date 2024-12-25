package io.ruin.model.activities.wilderness;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class PirateHut {

    private static final int LOCKPICK = 1523;
    private static final Bounds PIRATE_HUT = new Bounds(3038, 3949, 3044, 3959, 0);

    private static void openDoor(Player player, GameObject door, int stepX, int stepY, int openX, int openY, int openDirection) {
        if (!player.getPosition().inBounds(PIRATE_HUT)) {
            player.sendMessage("The door is locked.");
            return;
        }
        player.startEvent(event -> {
            player.lock();

            if(player.getAbsX() != door.x || player.getAbsY() != door.y) {
                player.stepAbs(door.x, door.y, StepType.FORCE_WALK);
                event.delay(1);
            }
            World.startEvent(e -> {
                GameObject opened = GameObject.spawn(1539, openX, openY, door.z, door.type, openDirection);
                door.skipClipping(true).remove();
                event.delay(2);
                door.restore().skipClipping(false);
                opened.remove();
            });
            player.stepAbs(stepX, stepY, StepType.FORCE_WALK);
            player.sendMessage("You go through the door.");
            event.delay(1);

            player.unlock();
        });
    }

    private static void picklockDoor(Player player, GameObject door, int stepX, int stepY, int xDif, int yDif, int openX, int openY, int openDirection) {
        if(player.getPosition().inBounds(PIRATE_HUT)) {
            player.sendMessage("The door is already unlocked.");
            return;
        }
        if(!player.getStats().check(StatType.Thieving, 39, "pick lock this door"))
            return;
        if(!player.getInventory().hasId(LOCKPICK)) {
            player.sendMessage("You need a lockpick for this.");
            return;
        }
        player.sendMessage("You attempt to pick the lock.");
        if(Random.rollDie(2, 1)) {
            player.sendMessage("You manage to pick the lock.");
            PlayerCounter.PICKED_LOCKS.increment(player, 1);
            player.startEvent(event -> {
                player.lock();

                if(player.getAbsX() != door.x + xDif || player.getAbsY() != door.y + yDif) {
                    player.stepAbs(door.x, door.y, StepType.FORCE_WALK);
                    event.delay(1);
                }
                GameObject opened = GameObject.spawn(1539, openX, openY, door.z, door.type, openDirection);
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
        ObjectAction.register(11727, 3044, 3956, 0, "open", (player, obj) -> openDoor(player, obj, 3045, 3956, 3045, 3956, 3));
        ObjectAction.register(11727, 3038, 3956, 0, "open", (player, obj) -> openDoor(player, obj, 3037, 3956, 3037, 3956, 1));
        ObjectAction.register(11727, 3041, 3959, 0, "open", (player, obj) -> openDoor(player, obj, 3041, 3960, 3041, 3960, 2));

        ObjectAction.register(11727, 3044, 3956, 0, "pick-lock", (player, obj) -> picklockDoor(player, obj, 3044, 3956, 1, 0, 3045, 3956, 3));
        ObjectAction.register(11727, 3038, 3956, 0, "pick-lock", (player, obj) -> picklockDoor(player, obj, 3038, 3956, -1, 0, 3037, 3956, 1));
        ObjectAction.register(11727, 3041, 3959, 0, "pick-lock", (player, obj) -> picklockDoor(player, obj, 3041, 3959, 0, 1, 3041, 3960, 2));
    }
}
