package io.ruin.model.activities.wilderness;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class GodwarsDungeon {

    static {
        /**
         * Entrance
         */
        ObjectAction.register(26766, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3065, 10159, 3);
            player.unlock();
        }));

        /**
         * Exit
         */
        ObjectAction.register(26767, 3065, 10160, 3, "use", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3017, 3740, 0);
            player.unlock();
        }));

        /**
         * Juttin wall
         */
        ObjectAction.register(26768, "pass", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(player.getAbsY() > 10148 ? 3276 : 3277);
            player.privateSound(2489, 1, 0);
            if (player.getAbsY() > 10148)
                player.getMovement().force(0, -2, 0, 0, 28, 120, Direction.SOUTH);
            else
                player.getMovement().force(0, 2, 0, 0, 28, 120, Direction.NORTH);
            player.stepAbs(3066, player.getAbsY() > 10148 ? 10147 : 10149, StepType.FORCE_WALK);
            event.delay(3);
            player.privateSound(2490, 1, 0);
            player.getStats().addXp(StatType.Agility, 6.0, false);
            player.unlock();
        }));

        /**
         * Boulder
         */
        NPCAction.register(6621, "move", (player, npc) -> player.startEvent(event -> {
            player.lock();
            player.animate(player.getAbsX() >= 3054 ? 3065 : 6130, 0);
            event.delay(2);
            player.getStats().addXp(StatType.Strength, 6.0, false);
            player.stepAbs(player.getAbsX() >= 3054 ? player.getAbsX() - 3 : player.getAbsX() + 3, player.getAbsY(), StepType.FORCE_WALK);
            npc.stepAbs(npc.getAbsX(), npc.getAbsY() + 1, StepType.FORCE_WALK);
            event.delay(3);
            npc.stepAbs(npc.getAbsX(), npc.getAbsY() - 1, StepType.FORCE_WALK);
            player.unlock();
        }));

        /**
         * South side crevice entrance
         */
        ObjectAction.register(26767, 3066, 10142, 3, "use", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3062, 10130, 0);
            player.unlock();
        }));

        /**
         * South side crevice exit
         */
        ObjectAction.register(26769, 3062, 10131, 0, "use", (player, obj) -> player.startEvent(event -> {
            player.lock();
            if (!player.isAt(3062, 10130)) {
                player.stepAbs(3062, 10130, StepType.FORCE_WALK);
                event.delay(1);
            }
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3066, 10143, 3);
            player.unlock();
        }));

        /**
         * North side crevice entrance
         */
        ObjectAction.register(26767, 3049, 10165, 3, "use", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3034, 10158, 0);
            player.unlock();
        }));

        /**
         * North side crevice exit
         */
        ObjectAction.register(26769, 3035, 10158, 0, "use", (player, obj) -> player.startEvent(event -> {
            player.lock();
            if (!player.isAt(3062, 10130)) {
                player.stepAbs(3034, 10158, StepType.FORCE_WALK);
                event.delay(1);
            }
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3050, 10165, 3);
            player.unlock();
        }));
    }
}
