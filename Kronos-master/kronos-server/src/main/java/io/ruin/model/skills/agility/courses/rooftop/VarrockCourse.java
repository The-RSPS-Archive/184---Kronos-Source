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

public class VarrockCourse {
    private static final List<Position> MARK_SPAWNS = Arrays.asList(
            new Position(3214, 3417, 3),
            new Position(3202, 3417, 3),
            new Position(3194, 3416, 1),
            new Position(3194, 3404, 3),
            new Position(3196, 3394, 3),
            new Position(3205, 3395, 3),
            new Position(3226, 3402, 3),
            new Position(3236, 3407, 3)
    );
    static {
        /*
         * Rough wall
         */
        ObjectAction.register(14412, "climb", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 30, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(828, 15);
            e.delay(2);
            p.getMovement().teleport(3220, 3414, 3);
            p.animate(2585);
            e.delay(2);
            p.getMovement().teleport(3219, 3414, 3);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 12.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));
        /*
         * Clothes line
         */
        ObjectAction.register(14413, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2461, 0, 0);
            p.animate(741);
            p.getMovement().force(-2, 0, 0, 0, 15, 30, Direction.WEST);
            e.delay(1);
            p.getMovement().teleport(3212, 3414, 3);
            e.delay(1);
            p.privateSound(2461, 0, 15);
            p.animate(741, 15);
            p.getMovement().force(-2, 0, 0, 0, 30, 45, Direction.WEST);
            e.delay(1);
            p.getMovement().teleport(3210, 3414, 3);
            e.delay(1);
            p.privateSound(2461, 0, 0);
            p.animate(741);
            p.getMovement().force(-2, 0, 0, 0, 15, 30, Direction.WEST);
            e.delay(1);
            p.getMovement().teleport(3208, 3414, 3);
            p.getStats().addXp(StatType.Agility, 21.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));
        /*
         * Gap
         */
        ObjectAction.register(14414, "leap", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2462, 0, 15);
            p.animate(2586, 15);
            e.delay(1);
            p.getMovement().teleport(3197, 3416, 1);
            p.animate(2588);
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 17.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));
        /*
         * Wall
         */
        ObjectAction.register(14832, "balance", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(3194, 3416, StepType.FORCE_WALK);
            e.waitForTile(p, 3194, 3416);
            p.animate(1995, 15);
            p.getMovement().force(-1, 0, 0, 0, 15, 45, Direction.WEST);
            e.delay(1);
            p.getMovement().teleport(3193, 3416, 1);
            p.privateSound(2468, 0, 20);
            p.animate(2583, 30);
            p.getMovement().force(-3, -2, 0, 0, 25, 30, Direction.WEST);
            e.delay(1);
            p.getMovement().teleport(3190, 3414, 1);
            for (int i = 0; i < 5; i++) {
                p.privateSound(2459, 0, 35);
                p.animate(1122);
                p.getMovement().force(0, -1, 0, 0, 34, 52, Direction.WEST);
                e.delay(2);
                p.getMovement().teleport(p.getAbsX(), p.getAbsY() - 1, p.getHeight());
            }
            p.getAppearance().setCustomRenders(Renders.AGILITY_WALL);
            p.animate(753);
            e.delay(1);
            p.stepAbs(3190, 3407, StepType.FORCE_WALK);
            p.privateSound(2451, 2, 0);
            e.delay(1);
            p.privateSound(2451, 2, 0);
            e.delay(1);
            p.privateSound(2468, 0, 0);
            p.getAppearance().removeCustomRenders();
            p.animate(741);
            p.getMovement().force(2, -1, 0, 0, 5, 30, Direction.EAST);
            e.delay(1);
            p.getMovement().teleport(3192, 3406, 3);
            p.getStats().addXp(StatType.Agility, 25.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        Tile.getObject(14832, 3191, 3415, 1).walkTo = new Position(3194, 3416, 0);

        /*
         * Gap
         */
        ObjectAction.register(14833, "leap", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            e.delay(1);
            p.privateSound(2468, 0, 20);
            p.animate(2583, 10);
            p.getMovement().force(0, -3, 0, 0, 25, 30, Direction.SOUTH);
            e.delay(1);
            p.getMovement().teleport(p.getAbsX(), 3399, 3);
            p.animate(2585);
            p.getMovement().force(0, -1, 0, 0, 17, 26, Direction.SOUTH);
            e.delay(1);
            p.getMovement().teleport(p.getAbsX(), 3398, 3);
            p.getStats().addXp(StatType.Agility, 9.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Gap
         */
        ObjectAction.register(14834, "leap", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(1995);
            p.privateSound(2461, 0, 15);
            p.animate(4789, 15);
            p.getMovement().force(7, 2, 0, 0, 20, 50, Direction.EAST);
            e.delay(2);
            p.getMovement().teleport(3215, 3399, 3);
            e.delay(1);
            p.privateSound(2468, 0, 0);
            p.animate(2583);
            p.getMovement().teleport(3217, 3399, 3);
            p.getMovement().force(2, 0, 0, 0, 5, 10, Direction.EAST);
            e.delay(1);
            p.getMovement().teleport(3217, 3399, 3);
            p.animate(2585);
            p.getMovement().force(1, 0, 0, 0, 18, 27, Direction.EAST);
            e.delay(1);
            p.resetAnimation();
            p.getMovement().teleport(3218, 3399, 3);
            p.getStats().addXp(StatType.Agility, 22.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Gap
         */
        ObjectAction.register(14835, "leap", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(3462, 0, 15);
            p.animate(2586, 15);
            e.delay(1);
            p.getMovement().teleport(3236, 3403, 3);
            p.animate(2588);
            e.delay(1);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 4.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Ledge
         */
        ObjectAction.register(14836, "hurdle", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(1936, 0, 0);
            p.animate(1603);
            p.getMovement().force(0, 2, 0, 0, 8, 50, Direction.NORTH);
            e.delay(1);
            p.getMovement().teleport(3236, 3410, 3);
            p.getStats().addXp(StatType.Agility, 3.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Edge
         */
        ObjectAction.register(14841, "jump-off", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(741);
            p.getMovement().force(0, 1, 0, 0, 15, 30, Direction.NORTH);
            e.delay(1);
            p.getMovement().teleport(3236, 3416, 2);
            e.delay(1);
            p.privateSound(2462, 0, 15);
            p.animate(2586, 15);
            e.delay(1);
            p.getMovement().teleport(3236, 3417, 0);
            p.animate(2588);
            p.getStats().addXp(StatType.Agility, 125.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            AgilityPet.rollForPet(p, 14000);
            PlayerCounter.VARROCK_ROOFTOP.increment(p, 1);
            MarkOfGrace.rollMark(p, 30, MARK_SPAWNS);
            p.unlock();
        }));
    }

}
