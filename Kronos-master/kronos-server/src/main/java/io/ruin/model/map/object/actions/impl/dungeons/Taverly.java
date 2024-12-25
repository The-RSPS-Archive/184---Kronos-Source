package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.stat.StatType;

public class Taverly {

    static {
        /**
         * Door to blue drags
         */
        ObjectAction.register(2623, 2924, 9803, 0, "open", (player, obj) -> {
            player.startEvent(event -> {
                int delay = 1;
                if (player.getAbsX() >= obj.x) { //behind
                    if (player.getAbsX() != obj.x || player.getAbsY() != obj.y) {
                        player.stepAbs(obj.x, obj.y, StepType.FORCE_WALK);
                        event.delay(1);
                    }
                    delay = 2;
                }
                player.lock(LockType.FULL);
                obj.remove();
                GameObject openDoor = GameObject.spawn(2623, 2923, 9803, 0, 0, 1);
                player.step(player.getAbsX() < 2924 ? 1 : -1, 0, StepType.FORCE_WALK);
                event.delay(delay);
                openDoor.remove();
                obj.restore();
                player.unlock();
            });
        });
        /**
         * Ladders
         */
        ObjectAction.register(17385, 2884, 9797, 0, "climb-up", (player, obj) -> Ladder.climb(player, 2884, 3398, 0, true, true, false));
        ObjectAction.register(16680, 2884, 3397, 0, "climb-down", (player, obj) -> Ladder.climb(player, 2884, 9798, 0, false, true, false));
        ObjectAction.register(17385, 2842, 9824, 0, "climb-up", (player, obj) -> Ladder.climb(player, 2841, 3424, 0, true, true, false));
        ObjectAction.register(17384, 2842, 3424, 0, "climb-down", (player, obj) -> Ladder.climb(player, 2841, 9824, 0, false, true, false));

        /**
         * Steps
         */
        ObjectAction.register(30189, 2881, 9825, 0, "climb", (player, obj) -> {
            if (!player.getStats().check(StatType.Agility, 80, "use this shortcut"))
                return;
            player.getMovement().teleport(2880, 9825, 1);
        });
        Tile.getObject(30189, 2881, 9825, 0).walkTo = new Position(2883, 9825, 0);
        ObjectAction.register(30190, 2881, 9825, 1, "climb", (player, obj) -> player.getMovement().teleport(2883, 9825, 0));
        ObjectAction.register(30189, 2904, 9813, 0, "climb", (player, obj) -> {
            player.getMovement().teleport(2903, 9813, 1);
        });

        ObjectAction.register(30190, 2904, 9813, 1, "climb", (player, obj) -> player.getMovement().teleport(2906, 9813, 0));

        /**
         * Rocks
         */
        ObjectAction.register(154, 2887, 9823, 0, "jump-up", (player, obj) -> player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 70, "use this shortcut"))
                return;

            player.lock();
            player.animate(741);
            player.getMovement().force(1, 0, 0, 0, 15, 30, Direction.EAST);
            event.delay(2);
            player.animate(2588);
            player.getMovement().teleport(player.getAbsX() + 1, player.getAbsY(), 1);
            event.delay(1);
            player.unlock();
        }));
        ObjectAction.register(14106, 2887, 9823, 1, "jump-down", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2586, 30);
            event.delay(2);
            player.getMovement().teleport(player.getAbsX() - 1, player.getAbsY(), 0);
            event.delay(1);
            player.animate(741);
            player.getMovement().force(-1, 0, 0, 0, 15, 30, Direction.WEST);
            event.delay(1);
            player.unlock();
        }));

        /**
         * Strange floor
         */
        ObjectAction.register(16510, 2879, 9813, 0, "jump-over", (player, obj) -> player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 80, "use this shortcut"))
                return;
            Direction dir = player.getAbsX() - obj.x > 0 ? Direction.WEST : Direction.EAST;
            player.lock();
            player.animate(741);
            player.getMovement().force(dir == Direction.WEST ? -2 : 2, 0, 0, 0, 15, 30, dir);
            event.delay(1);
            player.unlock();
        }));

        /**
         * Obstacle pipe
         */
        ObjectAction.register(16509, 2887, 9799, 0, "squeeze-through", (player, obj) -> player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 70, "use this shortcut"))
                return;
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(749, 30);
            player.getMovement().force(3, 0, 0, 0, 33, 126, Direction.EAST);
            event.delay(3);
            player.getMovement().force(3, 0, 0, 0, 33, 126, Direction.EAST);
            event.delay(1);
            player.animate(749);
            event.delay(2);
            player.unlock();
        }));
        ObjectAction.register(16509, 2890, 9799, 0, "squeeze-through", (player, obj) -> player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 70, "use this shortcut"))
                return;
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(749, 30);
            player.getMovement().force(-3, 0, 0, 0, 33, 126, Direction.WEST);
            event.delay(3);
            player.getMovement().force(-3, 0, 0, 0, 33, 126, Direction.WEST);
            event.delay(1);
            player.animate(749);
            event.delay(2);
            player.unlock();
        }));

        /**
         * Loose railing
         */
        ObjectAction.register(28849, 2935, 9810, 0, "squeeze-through", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.getAppearance().setCustomRenders(Renders.LOOSE_RAILINGS);
            if (obj.x == player.getAbsX() && obj.y == player.getAbsY())
                player.step(1, 0, StepType.FORCE_WALK);
            else
                player.step(-1, 0, StepType.FORCE_WALK);
            event.delay(1);
            player.getAppearance().removeCustomRenders();
            player.unlock();
        }));

        /**
         * Entrance
         */
        ObjectAction.register(26569, 2874, 9847, 0, "crawl", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(1310, 1237, 0);
            player.unlock();
        }));
        ObjectAction.register(26567, 2874, 9846, 0, "crawl", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(1310, 1237, 0);
            player.unlock();
        }));
        ObjectAction.register(26568, 2874, 9848, 0, "crawl", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(1310, 1237, 0);
            player.unlock();
        }));

        /**
         * Exit
         */
        ObjectAction.register(26564, 1309, 1236, 0, "crawl", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(2873, 9847, 0);
            player.unlock();
        }));
        ObjectAction.register(26565, 1311, 1236, 0, "crawl", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(2873, 9847, 0);
            player.unlock();
        }));
        ObjectAction.register(26566, 1310, 1236, 0, "crawl", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(2873, 9847, 0);
            player.unlock();
        }));
    }
}
