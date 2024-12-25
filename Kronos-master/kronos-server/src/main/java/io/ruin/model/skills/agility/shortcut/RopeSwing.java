package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

/**
 * @author ReverendDread on 3/16/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class RopeSwing {

    public static void shortcut(Player player, GameObject object, int level, Position position, Position destination) {
        if (!player.getStats().check(StatType.Agility, level, "swing-on"))
            return;
        player.startEvent(e -> {
            e.path(player, position);
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(751);
            e.delay(1);
            if (player.getAbsX() > object.x)
                player.getMovement().force(destination.getX() - player.getAbsX(), 0, 0, 0, 0, 50, Direction.WEST);
            else
                player.getMovement().force(destination.getX() - player.getAbsX(), 0, 0, 0, 0, 50, Direction.EAST);
            e.delay(2);
            player.getStats().addXp(StatType.Agility, 0.5, true);
            player.unlock();
        });
    }

}
