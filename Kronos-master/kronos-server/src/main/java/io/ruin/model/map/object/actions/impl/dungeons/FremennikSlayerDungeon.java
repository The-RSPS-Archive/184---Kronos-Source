package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class FremennikSlayerDungeon {

    static {
        /**
         * Strange floor
         */
        ObjectAction.register(16544, 2774, 10003, 0, "jump-over", (player, obj) -> player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 81, "use this shortcut"))
                return;
            Direction dir = player.getAbsX() - obj.x > 0 ? Direction.WEST : Direction.EAST;
            player.lock();
            player.animate(741);
            player.getMovement().force(dir == Direction.WEST ? -2 : 2, 0, 0, 0, 15, 30, dir);
            event.delay(1);
            player.unlock();
        }));

        ObjectAction.register(16544, 2769, 10002, 0, "jump-over", (player, obj) -> player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 81, "use this shortcut"))
                return;
            Direction dir = player.getAbsX() - obj.x > 0 ? Direction.WEST : Direction.EAST;
            player.lock();
            player.animate(741);
            player.getMovement().force(dir == Direction.WEST ? -2 : 2, 0, 0, 0, 15, 30, dir);
            event.delay(1);
            player.unlock();
        }));

        ObjectAction.register(16539, 2734, 10008, 0, "squeeze-through", (player, obj) -> squeezeThroughCrack(player, obj, new Position(2730, 10008, 0), 62)); // crack
        ObjectAction.register(16539, 2731, 10008, 0, "squeeze-through", (player, obj) -> squeezeThroughCrack(player, obj, new Position(2735, 10008, 0), 62)); // crack

    }

    private static void squeezeThroughCrack(Player player, GameObject crack, Position destination, int levelReq) {
        if (!player.getStats().check(StatType.Agility, levelReq, "use this shortcut"))
            return;
        player.lock();
        player.startEvent(event -> {
            player.face(crack);
            player.animate(746);
            event.delay(1);
            player.getMovement().teleport(destination);
            player.animate(748);
            event.delay(1);
            player.unlock();
        });
    }
}
