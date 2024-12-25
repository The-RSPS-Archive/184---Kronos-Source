package io.ruin.model.skills.agility.courses.rooftop;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.agility.courses.AgilityPet;
import io.ruin.model.skills.agility.courses.MarkOfGrace;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

public class RellekkaCourse {
    private static final List<Position> MARK_SPAWNS = Arrays.asList(new Position(2622, 3676, 3), new Position(2617, 3664, 3), new Position(2618, 3660, 3), new Position(2628, 3652, 3), new Position(2628, 3655, 3), new Position(2641, 3649, 3), new Position(2643, 3651, 3), new Position(2649, 3659, 3), new Position(2644, 3662, 3), new Position(2658, 3674, 3), new Position(2656, 3681, 3));

    static {
        /*
         * Rough wall
         */
        ObjectAction.register(14946, "climb", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 80, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(828, 15);
            e.delay(2);
            p.getMovement().teleport(2626, 3676, 3);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 20, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));
        /*
         * Gap
         */
        ObjectAction.register(14947, "leap", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(1995, 15);
            e.delay(1);
            p.privateSound(1936);
            p.animate(1603);
            p.getMovement().force(-1, -4, 0, 0, 8, 50, Direction.SOUTH);
            e.delay(2);
            p.getStats().addXp(StatType.Agility, 30, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));
        /*
         * Tightrope
         */
        ObjectAction.register(14987, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2495, 3, 0);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            p.stepAbs(2626, 3654, StepType.FORCE_WALK);
            p.stepAbs(2627, 3654, StepType.FORCE_WALK);
            e.waitForMovement(p);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 40, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));
        /*
         * Gap + tightrope
         */
        ObjectAction.register(14990, "leap", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.getMovement().force(0, 3, 0, 0, 25, 30, Direction.NORTH);
            e.delay(1);
            p.animate(752);
            p.getAppearance().setCustomRenders(Renders.AGILITY_JUMP);
            e.delay(1);
            p.stepAbs(2635, 3658, StepType.FORCE_WALK);
            e.delay(6);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            p.stepAbs(2639, 3654, StepType.FORCE_WALK);
            p.stepAbs(2639, 3653, StepType.FORCE_WALK);
            e.delay(6);
            p.getAppearance().removeCustomRenders();
            p.getStats().addXp(StatType.Agility, 85, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));
        /*
         * Gap
         */
        ObjectAction.register(14991, "hurdle", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(1995, 15);
            e.delay(1);
            p.privateSound(1936);
            p.animate(1603);
            p.getMovement().force(0, 0, 0, 4, 80, 50, Direction.NORTH);
            e.delay(2);
            p.getStats().addXp(StatType.Agility, 25, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));
        /*
         * Tightrope
         */
        ObjectAction.register(14992, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2495, 6, 0);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            p.stepAbs(2647, 3663, StepType.FORCE_WALK);
            e.delay(1);
            p.stepAbs(2654, 3670, StepType.FORCE_WALK);
            e.delay(1);
            p.stepAbs(2655, 3670, StepType.FORCE_WALK);
            e.waitForMovement(p);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 105, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));
        /*
         * Pile of fish
         */
        ObjectAction.register(14994, "jump-in", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2586, 15);
            p.privateSound(2462, 0, 15);
            e.delay(1);
            p.animate(2588);
            p.getMovement().teleport(2653, 3676, 0);
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 475, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.stepAbs(2652, 3676, StepType.FORCE_WALK);
            PlayerCounter.RELLEKKA_ROOFTOP.increment(p, 1);
            MarkOfGrace.rollMark(p, 80, MARK_SPAWNS);
            AgilityPet.rollForPet(p, 16000);
            e.delay(1);
            p.unlock();
        }));
    }
}
