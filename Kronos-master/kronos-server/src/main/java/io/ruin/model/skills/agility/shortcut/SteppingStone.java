package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/6/2020
 */
public class SteppingStone {
    public static void crossEW (Player p, GameObject stone, int levelReq) {
        if (!p.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(741);
            if(p.getAbsX() > stone.x) {
                p.getMovement().force(-3, 0, 0, 0, 0, 38, Direction.WEST);
                e.delay(2);
                p.animate(741);
                p.getMovement().force(-2, 0, 0, 0, 0, 28, Direction.WEST);
            } else {
                p.getMovement().force(2, 0, 0, 0, 0, 28, Direction.EAST);
                e.delay(2);
                p.animate(741);
                p.getMovement().force(3, 0, 0, 0, 0, 38, Direction.EAST);
            }
            e.delay(2);
            p.getStats().addXp(StatType.Agility, 0.5, true);
            p.unlock();
        });
    }
}
