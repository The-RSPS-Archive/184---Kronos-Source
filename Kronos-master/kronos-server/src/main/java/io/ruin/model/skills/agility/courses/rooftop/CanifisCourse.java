package io.ruin.model.skills.agility.courses.rooftop;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
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

public class CanifisCourse {

    private static final List<Position> MARK_SPAWNS = Arrays.asList(new Position(3508, 3494, 2), new Position(3502, 3506, 2), new Position(3499, 3505, 2), new Position(3489, 3500, 2), new Position(3492, 3499, 2), new Position(3476, 3496, 3), new Position(3475, 3493, 3), new Position(3482, 3486, 2), new Position(3478, 3484, 2), new Position(3493, 3476, 3), new Position(3495, 3472, 3), new Position(3491, 3472, 3), new Position(3513, 3479, 2), new Position(3512, 3481, 2), new Position(3510, 3476, 2));
    static {
        /*
         * Tall tree
         */
        ObjectAction.register(14843, "climb", (player, obj) -> player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 40, "attempt this"))
                return;
            player.lock(LockType.FULL_DELAY_DAMAGE);
            if (player.getAbsX() != 3507 && player.getAbsY() != 3488) {
                player.stepAbs(3507, 3488, StepType.FORCE_WALK);
                event.delay(1);
            }
            player.privateSound(1705, 1, 60);
            player.animate(1765, 60);
            player.getMovement().force(0, 1, 0, 0, 30, 60, Direction.NORTH);
            event.delay(2);
            player.getMovement().force(-2, 0, 0, 0, 60, 115, Direction.EAST);
            event.delay(4);
            player.getMovement().teleport(3506, 3492, 2);
            player.resetAnimation();
            player.getStats().addXp(StatType.Agility, 10, true);
            player.getMovement().restoreEnergy(Random.get(1, 2));
            player.unlock();
        }));

        Tile.getObject(14843, 3505, 3489, 0).walkTo = new Position(3507, 3488, 0);
        Tile.get(3505, 3492, 2, true).flagUnmovable();

        /*
         * Jump gap
         */
        ObjectAction.register(14844, "jump", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(1995, 20);
            player.getMovement().force(0, 1, 0, 0, 20, 40, Direction.NORTH);
            event.delay(1);
            player.animate(2586, 10);
            event.delay(1);
            player.animate(2588);
            player.getMovement().teleport(3502, 3504, 2);
            player.getStats().addXp(StatType.Agility, 8, true);
            player.getMovement().restoreEnergy(Random.get(1, 2));
            event.delay(1);
            player.unlock();
        }));

        /*
         * Jump gap
         */
        ObjectAction.register(14845, "jump", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            if (player.getAbsX() != 3498 && player.getAbsY() != 3504) {
                player.stepAbs(3498, 3504, StepType.FORCE_WALK);
                event.delay(1);
                player.face(Direction.WEST);
            }
            player.animate(2586, 15);
            event.delay(1);
            player.getMovement().teleport(3493, 3504, 2);
            player.animate(2588, 1);
            player.getStats().addXp(StatType.Agility, 8, true);
            player.getMovement().restoreEnergy(Random.get(1, 2));
            event.delay(1);
            player.step(-1, 0, StepType.FORCE_WALK);
            player.unlock();
        }));

        Tile.get(3497, 3503, 2, true).flagUnmovable();

        /*
         * Jump gap
         */
        ObjectAction.register(14848, "jump", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            if (player.getAbsX() != 3487 && player.getAbsY() != 3499) {
                player.stepAbs(3487, 3499, StepType.FORCE_WALK);
                event.delay(1);
                player.face(Direction.WEST);
            }
            player.privateSound(2468, 1, 20);
            player.animate(2583, 20);
            player.getMovement().force(-6, 0, 0, 0, 25, 30, Direction.WEST);
            event.delay(1);
            player.animate(2585, 0);
            event.delay(1);
            player.getMovement().force(-2, 0, 0, 0, 17, 26, Direction.WEST);
            player.getStats().addXp(StatType.Agility, 10, true);
            player.getMovement().restoreEnergy(Random.get(1, 2));
            event.delay(1);
            player.getMovement().teleport(player.getAbsX(), player.getAbsY(), player.getHeight() + 1);
            player.resetAnimation();
            player.unlock();
        }));

        Tile.get(3488, 3498, 2, true).flagUnmovable();

        /*
         * Jump gap
         */
        ObjectAction.register(14846, "jump", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.privateSound(2462, 1, 15);
            player.animate(2586, 15);
            event.delay(1);
            player.getMovement().teleport(3478, 3486, 2);
            player.animate(2588, 0);
            player.getStats().addXp(StatType.Agility, 8, true);
            player.getMovement().restoreEnergy(Random.get(1, 2));
            player.unlock();
        }));

        /*
         * Vault
         */
        ObjectAction.register(14894, "vault", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.getMovement().force(-2, 2, 0, 0, 0, 30, Direction.EAST);
            player.animate(1995);
            event.delay(1);
            player.getMovement().force(2, -3, 0, 0, 0, 30, Direction.EAST);
            player.animate(7132, 10);
            event.delay(1);
            player.getMovement().force(3, -2, 0, 0, 0, 30, Direction.EAST);
            event.delay(1);
            player.getMovement().force(1, -1, 0, 0, 0, 30, Direction.EAST);
            event.delay(1);
            player.getMovement().force(1, -1, 0, 0, 0, 30, Direction.EAST);
            event.delay(1);
            player.getMovement().force(1, -1, 0, 0, 0, 30, Direction.EAST);
            event.delay(1);
            player.getMovement().force(1, -1, 0, 0, 0, 30, Direction.EAST);
            event.delay(1);
            player.getMovement().force(1, -1, 0, 0, 0, 30, Direction.EAST);
            event.delay(1);
            player.getMovement().force(1, -1, 0, 0, 0, 30, Direction.EAST);
            event.delay(1);
            player.animate(2588);
            player.getMovement().teleport(3489, 3476, 3);
            player.getStats().addXp(StatType.Agility, 10, true);
            player.getMovement().restoreEnergy(Random.get(1, 2));
            player.unlock();
        }));
        Tile.get(3481, 3482, 2, true).flagUnmovable();

        /*
         * Jump gap
         */
        ObjectAction.register(14847, "jump", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            if (player.getAbsX() != 3502 && player.getAbsY() != 3476) {
                player.stepAbs(3502, 3476, StepType.FORCE_WALK);
                event.delay(1);
                player.face(Direction.WEST);
            }
            player.privateSound(2462, 1, 15);
            player.animate(2586, 10);
            event.delay(1);
            player.animate(2588);
            player.getMovement().teleport(3510, 3476, 2);
            player.getStats().addXp(StatType.Agility, 11, true);
            player.getMovement().restoreEnergy(Random.get(1, 2));
            event.delay(1);
            player.unlock();
        }));

        /*
         * Jump gap
         */
        ObjectAction.register(14897, "jump", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.privateSound(2462, 1, 15);
            player.animate(2586, 15);
            event.delay(1);
            player.animate(2588);
            player.getMovement().teleport(3510, 3485, 0);
            player.getStats().addXp(StatType.Agility, 175, true);
            player.getMovement().restoreEnergy(Random.get(1, 2));
            PlayerCounter.CANIFIS_ROOFTOP.increment(player, 1);
            AgilityPet.rollForPet(player, 18000);
            MarkOfGrace.rollMark(player, 40, MARK_SPAWNS);
            player.unlock();
        }));
        Tile.get(3508, 3480, 2, true).flagUnmovable();
    }
}
