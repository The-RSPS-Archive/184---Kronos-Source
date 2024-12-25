package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class AsgarnianIceDungeon {

    static {
        /**
         * Entrance/exit
         */
        ObjectAction.register(17385, 3008, 9550, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3009, 3150, 0, true, true, false));
        ObjectAction.register(1738, 3008, 3150, 0, "climb-down", (player, obj) -> Ladder.climb(player, 3009, 9550, 0, false, false, false));

        /**
         * Skeletal Wyvern entrance
         */
        ObjectAction.register(10596, 3055, 9560, 0, "enter", (player, obj) -> {
            if (player.wyvernWarning) {
                player.dialogue(new MessageDialogue("STOP! The creatures in this cave are VERY dangerous. Are you sure you want to enter?"),
                        new OptionsDialogue(
                                new Option("Enter.", () -> player.getMovement().teleport(3056, 9555)),
                                new Option("Enter, and don't show the warning again.", () -> {
                                    player.wyvernWarning = false;
                                    player.getMovement().teleport(3056, 9555);
                                }),
                                new Option("Don't enter.", player::closeDialogue)
                        ));
            } else {
                player.getMovement().teleport(3056, 9555);
            }
        });
        ObjectAction.register(10595, 3055, 9556, 0, "enter", (player, obj) -> player.getMovement().teleport(3056, 9562));
        ObjectAction.register(8729, 3060, 9556, 0, "climb", (player, obj) -> player.startEvent(event -> {
            player.lock();
            if (player.getAbsY() <= 9555)
                player.stepAbs(3060, 9557, StepType.FORCE_WALK);
            else
                player.stepAbs(3060, 9555, StepType.FORCE_WALK);
            event.delay(2);
            player.unlock();
        }));
    }
}
