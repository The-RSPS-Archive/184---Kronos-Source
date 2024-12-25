package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

public class LooseRailing {

    public static void shortcut(Player p, GameObject looseRailing, int levelReq) {
        if (!p.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            if(p.getAbsX() == looseRailing.x)
                p.getMovement().teleport(p.getAbsX() - 1, p.getAbsY());
            else
                p.getMovement().teleport(p.getAbsX() + 1, p.getAbsY());
            e.delay(1);
            p.unlock();
        });

    }

}
