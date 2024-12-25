package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class Edgeville {

    private static void swingAcross(Player player, int x, int y) {
        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(742);
            event.delay(1);
            player.getAppearance().setCustomRenders(Renders.MONKEY_BARS);
            player.stepAbs(x, y, StepType.FORCE_WALK);
            event.delay(7);
            player.getAppearance().removeCustomRenders();
            player.animate(743);
            player.unlock();
        });
    }

    private static Bounds WILDERNESS = new Bounds(3072, 9924, 3153, 10002, -1);
    private static final int[] LOOTING_BAG_NPCS = {2883, 2878, 3021, 4987, 3023, 2050, 82, 2840};

    static {
        /**
         * Looting bag drop
         */
        SpawnListener.register(LOOTING_BAG_NPCS, npc -> {
            if (npc.getPosition().inBounds(WILDERNESS)) {
                npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
                    if (killer == null)
                        return;
                    if (Random.rollDie(30, 1)) {
                        if(killer.player.getInventory().hasId(11941))
                            return;
                        new GroundItem(11941, 1).owner(killer.player).position(npc.getPosition()).spawn();
                    }
                };
            }
        });

        /**
         * Monkeybars
         */
        ObjectAction.register(23566, 3119, 9964, 0, "swing across", (player, obj) -> swingAcross(player, 3120, 9970));
        ObjectAction.register(23566, 3120, 9964, 0, "swing across", (player, obj) -> swingAcross(player, 3121, 9970));

        ObjectAction.register(23566, 3119, 9969, 0, "swing across", (player, obj) -> swingAcross(player, 3120, 9963));
        ObjectAction.register(23566, 3120, 9969, 0, "swing across", (player, obj) -> swingAcross(player, 3121, 9963));

        /**
         * Ladder
         */
        ObjectAction.register(17385, 3116, 9852, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3117, 3452, 0, true, true, false));
        ObjectAction.register(17384, 3116, 3452, 0, "climb-down", (player, obj) -> Ladder.climb(player, 3117, 9852, 0, false, true, false));

        /**
         * Brass key door
         */
        ObjectAction.register(1804, 3115, 3450, 0, "open", (player, obj) -> {
            if (player.getInventory().hasId(983)) {
                player.startEvent(event -> {
                    player.lock();

                    if (player.getAbsX() != obj.x || player.getAbsY() != obj.y + (obj.y > player.getAbsY() ? -1 : 0)) {
                        player.stepAbs(obj.x, obj.y + (obj.y > player.getAbsY() ? -1 : 0), StepType.FORCE_WALK);
                        event.delay(1);
                    }
                    GameObject opened = GameObject.spawn(11728, 3115, 3449, 0, obj.type, 0);
                    obj.skipClipping(true).remove();
                    player.stepAbs(player.getAbsX(), obj.y + (obj.y > player.getAbsY() ? 0 : -1), StepType.FORCE_WALK);
                    player.sendFilteredMessage("You unlock the door.");
                    event.delay(2);
                    obj.restore().skipClipping(false);
                    opened.remove();

                    player.unlock();
                });
            } else {
                player.sendFilteredMessage("The door is locked.");
            }
        });

        /**
         * Obstacle pipe
         */
        ObjectAction.register(16511, 3150, 9906, 0, "squeeze-through", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(749, 30);
            player.getMovement().force(3, 0, 0, 0, 33, 126, Direction.EAST);
            event.delay(3);
            player.getMovement().force(3, 0, 0, 0, 33, 126, Direction.EAST);
            event.delay(1);
            player.animate(749, 30);
            event.delay(2);
            player.unlock();
        }));
        Tile.getObject(16511, 3150, 9906, 0).walkTo = new Position(3149, 9906, 0);

        ObjectAction.register(16511, 3153, 9906, 0, "squeeze-through", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(749, 30);
            player.getMovement().force(-3, 0, 0, 0, 33, 126, Direction.WEST);
            event.delay(3);
            player.getMovement().force(-3, 0, 0, 0, 33, 126, Direction.WEST);
            event.delay(1);
            player.animate(749, 30);
            event.delay(2);
            player.unlock();
        }));
        Tile.getObject(16511, 3153, 9906, 0).walkTo = new Position(3155, 9906, 0);
    }

}
