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

public class AlKharidCourse {

    private static final List<Position> MARK_SPAWNS = Arrays.asList(
            new Position(3276, 3188, 3),
            new Position(3271, 3191, 3),
            new Position(3273, 3182, 3),
            new Position(3267, 3171, 3),
            new Position(3271, 3170, 3),
            new Position(3268, 3163, 3),
            new Position(3266, 3166, 3),
            new Position(3291, 3163, 3),
            new Position(3297, 3168, 3),
            new Position(3301, 3164, 3),
            new Position(3316, 3161, 1),
            new Position(3318, 3163, 1),
            new Position(3315, 3176, 2),
            new Position(3317, 3178, 2),
            new Position(3315, 3183, 3),
            new Position(3313, 3181, 3),
            new Position(3302, 3189, 3),
            new Position(3300, 3190, 3)
    );
    static {
        /*
         * Wall climb
         */
        ObjectAction.register(11633, "climb", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 20, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(828, 15);
            e.delay(2);
            p.getMovement().teleport(3273, 3192, 3);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 10.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Tightrope
         */
        ObjectAction.register(14398, "cross", (p, obj) -> p.startEvent(e -> {
            if (obj.x < 2070) {
                int x = 2054;
                if (p.getAbsX() > 2045) {
                    x = 2036;
                }
                p.lock(LockType.FULL_DELAY_DAMAGE);
                p.stepAbs(x, 3621, StepType.FORCE_WALK);
                e.delay(1);
                p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
                e.delay(18);
                p.getAppearance().removeCustomRenders();
                e.delay(1);
                p.getStats().addXp(StatType.Agility, 4, true);
                p.getMovement().restoreEnergy(Random.get(1, 2));
                p.unlock();

            } else {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                p.stepAbs(3272, 3172, StepType.FORCE_WALK);
                e.delay(1);
                p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
                e.delay(9);
                p.getAppearance().removeCustomRenders();
                e.delay(1);
                p.getStats().addXp(StatType.Agility, 30.0, true);
                p.getMovement().restoreEnergy(Random.get(1, 2));
                p.unlock();
            }
        }));

        /*
         * Cable swing
         */
        ObjectAction.register(14402, "swing-across", (p, obj) -> p.startEvent(e -> {
           p.lock(LockType.FULL_DELAY_DAMAGE);
           p.sendMessage("You begin an almighty run-up...");
           p.animate(1995);
           p.getMovement().force(0, 0, 3, 0, 30, 0, Direction.EAST);
           e.delay(1);
           p.sendMessage("You gained enough momentum to swing to the other side!");
           p.animate(751);
           p.getMovement().teleport(3269, 3166, 3);
           p.getMovement().force(0, 0, 15, 0, 60, 0, Direction.EAST);
           e.delay(2);
           p.getMovement().teleport(3284, 3166, 3);
           p.getStats().addXp(StatType.Agility, 40.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
           p.unlock();
        }));

        /*
         * Teeth swing
         */
        ObjectAction.register(14403, "teeth-grip", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2586, 10);
            e.delay(1);
            p.privateSound(1933);
            p.privateSound(1934,1,15);
            p.getMovement().teleport(3303, 3163, 1);
            p.animate(1601);
            e.delay(1);
            p.animate(1602);
            for(int i = 0; i < 12; i++) {
                p.getMovement().force(1, 0, 0, 0, 30, 0, Direction.EAST);
                e.delay(1);
            }
            e.delay(1);
            p.resetAnimation();
            p.stepAbs(3315, 3163, StepType.FORCE_WALK);
            p.getStats().addXp(StatType.Agility, 40.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         * Palm tree swing
         */
        ObjectAction.register(14404, "swing-across", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2468, 1, 10);
            p.animate(2583);
            p.getMovement().force(0, 4, 0, 0, 35, 49, Direction.NORTH);
            p.face(Direction.EAST);
            e.delay(2);
            p.privateSound(2459, 1, 35);
            p.animate(1122);
            p.getMovement().force(0, 1, 0, 0, 34, 42, Direction.NORTH);
            p.face(Direction.EAST);
            e.delay(2);
            p.animate(1124);
            p.getMovement().force(-1, 4, 0, 0, 34, 52, Direction.NORTH_WEST);
            e.delay(1);
            p.privateSound(2455);
            p.animate(2588);
            e.delay(1);
            p.getMovement().teleport(3317, 3174, 2);
            p.getStats().addXp(StatType.Agility, 10, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        Tile.getObject(14404, 3318, 3166, 1).walkTo = new Position(3318, 3165, 1);

        /*
         * Wall climb
         */
        ObjectAction.register(11634, "climb", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(828, 15);
            e.delay(2);
            p.getMovement().teleport(3316, 3180, 3);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 5, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        /*
         *  Last tightrope
         */
        ObjectAction.register(14409, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2495, 0, 6);
            p.stepAbs(3302, 3186, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            e.delay(11);
            p.stepAbs(3302, 3187, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().removeCustomRenders();
            p.getStats().addXp(StatType.Agility, 15, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        Tile.getObject(14409, 3313, 3186, 3).walkTo = new Position(3314, 3186, 3);

        /*
         *  Jump down
         */
        ObjectAction.register(14399, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2586);
            e.delay(1);
            p.getMovement().teleport(3299, 3194, 0);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 30, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            PlayerCounter.ALKHARID_ROOFTOP.increment(p, 1);
            AgilityPet.rollForPet(p, 13000);
            MarkOfGrace.rollMark(p, 20, MARK_SPAWNS);
            p.unlock();
        }));
    }
}
