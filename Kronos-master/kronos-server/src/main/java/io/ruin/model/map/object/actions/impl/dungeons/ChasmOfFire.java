package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class ChasmOfFire {

    private static void lift(Player player, boolean down) {
        player.startEvent(event -> {
            player.lock();
            player.dialogue(new MessageDialogue("You step inside the gibbet. <br> The chain creaks as you " + (down ? "descend further into the Chasm." :
                    "ascend towards the surface.")).hideContinue().lineHeight(30));
            player.getPacketSender().fadeOut();
            event.delay(2);
            player.getMovement().teleport(player.getAbsX(), player.getAbsY(), player.getHeight() + (down ? - 1 : + 1));
            event.delay(1);
            player.dialogue(new MessageDialogue("You step inside the gibbet. <br> The chain creaks as you " + (down ? "descend further into the Chasm." :
                    "ascend towards the surface.")).lineHeight(30));
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

    static {
        /**
         * Entrance/exit
         */
        ObjectAction.register(30236, 1436, 3671, 0, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.stepAbs(1436, 3671, StepType.FORCE_WALK);
            event.delay(1);
            Ladder.climb(player, 1435, 10077, 3, false, true, false);
            player.unlock();
        }));
        ObjectAction.register(30234, 1435, 10078, 3, "climb-up", (player, obj) -> Ladder.climb(player, 1435, 3671, 0, true, true, false));

        /**
         * Lift
         */
        ObjectAction.register(30258, 1437, 10094, 3, "enter", (player, obj) -> lift(player, true));
        ObjectAction.register(30258, 1452, 10068, 3, "enter", (player, obj) -> lift(player, true));
        ObjectAction.register(30258, 1458, 10075, 2, "enter", (player, obj) -> lift(player, true));
        ObjectAction.register(30259, 1437, 10094, 2, "enter", (player, obj) -> lift(player, false));
        ObjectAction.register(30259, 1452, 10068, 2, "enter", (player, obj) -> lift(player, false));
        ObjectAction.register(30258, 1458, 10095, 2, "enter", (player, obj) -> lift(player, true));
        ObjectAction.register(30259, 1458, 10095, 1, "enter", (player, obj) -> lift(player, false));
        ObjectAction.register(30259, 1458, 10075, 1, "enter", (player, obj) -> lift(player, false));
    }
}
