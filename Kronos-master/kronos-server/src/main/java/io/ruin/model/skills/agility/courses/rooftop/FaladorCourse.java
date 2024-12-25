package io.ruin.model.skills.agility.courses.rooftop;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.agility.courses.AgilityPet;
import io.ruin.model.skills.agility.courses.MarkOfGrace;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

public class FaladorCourse {
    private static final List<Position> MARK_SPAWNS = Arrays.asList(
            new Position(3038, 3343, 3),
            new Position(3049, 3348, 3),
            new Position(3049, 3357, 3),
            new Position(3045, 3365, 3),
            new Position(3035, 3362, 3),
            new Position(3028, 3353, 3),
            new Position(3017, 3345, 3),
            new Position(3011, 3339, 3),
            new Position(3016, 3333, 3)
    );

    static {
        /*
         * Wall climb
         */
        ObjectAction.register(14898, "climb", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 50, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(828, 15);
            e.delay(2);
            p.getMovement().teleport(3036, 3342, 3);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 8, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Tightrope
         */
        ObjectAction.register(14899, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2495, 5, 0);
            p.stepAbs(3047, 3343, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            e.waitForMovement(p);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 17, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Wall bricks
         */
        ObjectAction.register(14901, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2468, 0, 20);
            p.animate(2583, 20);
            p.step(0, 2, StepType.FORCE_RUN);
            p.getMovement().force(0, 2, 0, 0, 25, 30, Direction.NORTH);
            e.delay(1);
            p.getMovement().teleport(3050, 3351, 2);
            p.privateSound(2459, 0, 35);
            p.animate(1118);
            e.delay(1);
            p.step(1, 1, StepType.FORCE_WALK);
            p.getMovement().force(0, 1, 0, 0, 13, 22, Direction.WEST);
            p.getMovement().teleport(3051, 3352, 2);
            e.delay(1);
            for(int i = 0; i < 3; i++) {
                p.privateSound(2459, 0, 35);
                p.animate(1118);
                p.step(0, 1, StepType.FORCE_WALK);
                p.getMovement().force(0, 1, 0, 0, 34, 52, Direction.WEST);
                e.delay(2);
            }
            p.privateSound(2459, 0, 35);
            p.animate(1120);
            p.stepAbs(3050, 3357, StepType.FORCE_RUN);
            p.getMovement().force(-1, 2, 0, 0, 34, 60, Direction.WEST);
            e.delay(2);
            p.resetAnimation();
            p.getMovement().teleport(3050, 3357, 3);
            p.getStats().addXp(StatType.Agility, 45, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Gap
         */
        ObjectAction.register(14903, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(741);
            p.getMovement().force(0, 3, 0, 0, 15, 30, Direction.NORTH);
            e.delay(1);
            p.getMovement().teleport(3048, 3361, 3);
            p.getStats().addXp(StatType.Agility, 20, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        Tile.getObject(14903, 3048, 3359, 3).walkTo = new Position(3048, 3358, 3);

        /*
         * Gap
         */
        ObjectAction.register(14904, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(741);
            p.getMovement().force(-4, 0, 0, 0, 15, 30, Direction.WEST);
            e.delay(1);
            p.getMovement().teleport(3041, 3361, 3);
            p.getStats().addXp(StatType.Agility, 20, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

       Tile.getObject(14904, 3044, 3361, 3).walkTo = new Position(3045, 3361, 3);

        /*
         * Tightrope
         */
        ObjectAction.register(14905, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            p.privateSound(1935, 4, 0);
            p.stepAbs(3033, 3361, StepType.FORCE_WALK);
            e.waitForMovement(p);
            p.stepAbs(3028, 3356, StepType.FORCE_WALK);
            e.waitForMovement(p);
            p.stepAbs(3028, 3354, StepType.FORCE_WALK);
            e.waitForMovement(p);
            p.getAppearance().removeCustomRenders();
            e.delay(2);
            p.getStats().addXp(StatType.Agility, 45.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Tightrope
         */
        ObjectAction.register(14911, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(3026, 3353, StepType.FORCE_WALK);
            e.delay(2);
            p.privateSound(1935, 0, 5);
            p.animate(7134);
            p.getMovement().force(-5, 0, 0, 0, 0, 89, Direction.WEST);
            e.delay(3);
            p.getMovement().teleport(3021, 3353, 3);
            p.stepAbs(3020, 3353, StepType.FORCE_WALK);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 40.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Gap
         */
        ObjectAction.register(14919, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2461);
            p.animate(1603);
            p.getMovement().force(0, -4, 0, 0, 15, 30, Direction.SOUTH);
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 25, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        Tile.getObject(14919, 3016, 3352, 3).walkTo = new Position(3016, 3353, 3);

        /*
         * Gap
         */
        ObjectAction.register(14920, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock();
            p.privateSound(2461);
            p.animate(1603);
            p.getMovement().force(-2, 0, 0, 0, 15, 30, Direction.WEST);
            e.delay(1);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 10, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Gap
         */
        ObjectAction.register(14921, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2461);
            p.animate(1603);
            p.getMovement().force(0, -2, 0, 0, 15, 30, Direction.SOUTH);
            e.delay(1);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 10, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Gap
         */
        ObjectAction.register(14922, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2461);
            p.animate(1603);
            p.getMovement().force(0, -2, 0, 0, 15, 30, Direction.SOUTH);
            e.delay(1);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 10, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Gap
         */
        ObjectAction.register(14924, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2461);
            p.animate(1603);
            p.getMovement().force(2, 0, 0, 0, 15, 30, Direction.SOUTH);
            e.delay(1);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 10, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Gap
         */
        ObjectAction.register(14925, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2461);
            p.animate(1603);
            p.getMovement().force(3, 0, 0, 0, 15, 30, Direction.EAST);
            e.delay(1);
            p.resetAnimation();
            e.delay(1);
            p.animate(2586, 15);
            p.privateSound(2462, 1, 15);
            e.delay(1);
            p.getMovement().teleport(3029, 3333, 0);
            p.getStats().addXp(StatType.Agility, 180, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            AgilityPet.rollForPet(p, 15000);
            PlayerCounter.FALADOR_ROOFTOP.increment(p, 1);
            MarkOfGrace.rollMark(p, 50, MARK_SPAWNS);
            p.unlock();
        }));
    }
}
