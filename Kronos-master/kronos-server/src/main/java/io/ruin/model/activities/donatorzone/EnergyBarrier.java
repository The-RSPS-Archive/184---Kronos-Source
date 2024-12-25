package io.ruin.model.activities.donatorzone;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class EnergyBarrier {

    private static final int DONATOR_ENTRANCE = 4470;

    private static void enter(Player player, GameObject entrance) {
        player.startEvent(event -> {
            player.lock();
            if(player.getAbsX() != entrance.x || player.getAbsY() != entrance.y) {
                player.stepAbs(entrance.x, entrance.y, StepType.FORCE_WALK);
                event.delay(1);
            }
            player.step(0, 1, StepType.FORCE_WALK);
            event.delay(1);
            player.dialogue(new ItemDialogue().one(744, "Welcome to " + Color.COOL_BLUE.wrap(World.type.getWorldName() + " Donator Zone")
                    + ". Thank you for your support. This area is under construction and will be updated within the next few days."));
            player.unlock();
        });
    }

    private static void exit(Player player, GameObject exit) {
        player.startEvent(event -> {
           player.lock();
            if(player.getAbsX() != exit.x || player.getAbsY() - 1 != exit.y) {
                player.stepAbs(exit.x, exit.y, StepType.FORCE_WALK);
                event.delay(1);
            }
           player.step(0, -1, StepType.FORCE_WALK);
           event.delay(1);
           player.unlock();
        });
    }

    static {
        ObjectAction.register(DONATOR_ENTRANCE, 1, (player, obj) -> {
            /**
             * Enter/exit the donator area only if player is a donator or part of the staff team
             */
            if(player.isSapphire() || player.isSupport() || player.isModerator() || player.isAdmin()) {
                if(player.getAbsY() <= 3507)
                    enter(player, obj);
                 else
                    exit(player, obj);
            } else {
                player.dialogue(new MessageDialogue("You need to be a " + Color.COOL_BLUE.wrap("donator") +
                        " to enter this area. To donate for items and get additional benefits, please type " + Color.COOL_BLUE.wrap("::store") + "."));
            }
        });
    }
}
