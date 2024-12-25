package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.woodcutting.Hatchet;
import io.ruin.model.stat.StatType;

public class Vine {

    private static void chopDown(Player player, GameObject vines) {
        Hatchet axe = Hatchet.find(player);
        if(axe == null) {
            player.sendMessage("You need an axe to chop down these vines.");
            player.sendMessage("You do not have an axe which you have the Woodcutting level to use.");
            return;
        }
        if(player.getStats().get(StatType.Woodcutting).currentLevel < 10) {
            player.sendMessage("You need a Woodcutting level of at least 10 to chop down these vines.");
            return;
        }

        player.startEvent(event -> {
            player.animate(Hatchet.RUNE.animationId);
            event.delay(3);
            if(vines.id != -1) {
                World.startEvent(e -> {
                    vines.remove();
                    event.delay(4);
                    vines.restore();
                });
            }
            int diffX = vines.x - player.getAbsX();
            int diffY = vines.y - player.getAbsY();
            if(Math.abs(diffX) > 1 || Math.abs(diffY) > 1 || (diffX + diffY) > 1)
                return;
            if(vines.direction == 1 || vines.direction == 3) {
                if(diffX == -1)
                    player.step(-2, 0, StepType.FORCE_WALK);
                else if(diffX == 1)
                    player.step(2, 0, StepType.FORCE_WALK);
            } else {
                if(diffY == -1)
                    player.step(0, -2, StepType.FORCE_WALK);
                else if(diffY == 1)
                    player.step(0, 2, StepType.FORCE_WALK);
            }

            player.animate(-1);
        });
    }

    static {
        ObjectAction.register(21731, "chop-down", Vine::chopDown);
        ObjectAction.register(21732, "chop-down", Vine::chopDown);
        ObjectAction.register(21733, "chop-down", Vine::chopDown);
        ObjectAction.register(21734, "chop-down", Vine::chopDown);
        ObjectAction.register(21735, "chop-down", Vine::chopDown);
    }
}
