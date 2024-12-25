package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

public class UnderwallTunnel {

    public static void shortcut(Player p, GameObject wall, int levelReq) {
        if (!p.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        p.startEvent(e -> {
            if(wall.id == 16530) {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                p.animate(2589);
                p.getMovement().force(-1, 0, 0, 0, 0, 50, Direction.WEST);
                e.delay(2);
                p.animate(2590);
                p.getMovement().force(-3, 3, 0, 0, 0, 100, Direction.WEST);
                e.delay(3);
                p.animate(2591);
                p.getMovement().force(-1, 0, 0, 0, 15, 33, Direction.WEST);
                e.delay(1);
                p.unlock();
            } else {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                p.animate(2589);
                p.getMovement().force(1, 0, 0, 0, 0, 50, Direction.EAST);
                e.delay(2);
                p.animate(2590);
                p.getMovement().force(3, -3, 0, 0, 0, 100, Direction.EAST);
                e.delay(3);
                p.animate(2591);
                p.getMovement().force(1, 0, 0, 0, 15, 33, Direction.EAST);
                e.delay(1);
                p.unlock();
            }
        });
    }

}
