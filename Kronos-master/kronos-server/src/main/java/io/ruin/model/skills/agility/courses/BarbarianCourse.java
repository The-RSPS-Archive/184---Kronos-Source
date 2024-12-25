package io.ruin.model.skills.agility.courses;

import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.stat.StatType;

public class BarbarianCourse {
    static {
        /**
         * Ropeswing
         */
        ObjectAction.register(23131, "swing-on", (player, obj) -> player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 35, "attempt this"))
                return;
            player.lock(LockType.FULL_DELAY_DAMAGE);
            obj.animate(54);
            player.animate(751);
            player.getMovement().force(0, -5, 0, 0, 30, 50, Direction.SOUTH);
            event.delay(1);
            obj.animate(55);
            player.getStats().addXp(StatType.Agility, 22.0, true);
            player.lastAgilityObjId = obj.id;
            player.unlock();
        }));
        Tile.getObject(23131, 2551, 3550, 0).walkTo = new Position(2551, 3554, 0);
        /**
         * Log balance
         */
        ObjectAction.register(23144, "walk-across", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.sendMessage("You walk carefully across the slippery log...");
            player.stepAbs(2541, 3546, StepType.FORCE_WALK);
            player.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            event.delay(10);
            player.getAppearance().removeCustomRenders();
            player.getStats().addXp(StatType.Agility, 13.7, true);
            player.sendMessage("...you make it safely to the other side.");
            if (player.lastAgilityObjId == 23131)
                player.lastAgilityObjId = obj.id;
            player.unlock();
        }));
        Tile.getObject(23144, 2550, 3546, 0).walkTo = new Position(2551, 3546, 0);
        /**
         * Obstacle net
         */
        ObjectAction.register(20211, "climb-over", (player, obj) -> player.startEvent(event -> {
            if (player.getAbsX() < obj.x) {
                return;
            }
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(828);
            event.delay(1);
            player.getMovement().teleport(player.getAbsX() - 2, player.getAbsY(), 1);
            player.getStats().addXp(StatType.Agility, 9.2, true);
            if (player.lastAgilityObjId == 23144)
                player.lastAgilityObjId = obj.id;
            player.unlock();
        }));
        /**
         * Balancing ledge
         */
        ObjectAction.register(23547, "walk-across", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(753);
            player.stepAbs(2532, 3547, StepType.FORCE_WALK);
            player.getAppearance().setCustomRenders(Renders.AGILITY_WALL);
            event.delay(4);
            player.stepAbs(2532, 3546, StepType.FORCE_WALK);
            player.getAppearance().removeCustomRenders();
            event.delay(1);
            player.getStats().addXp(StatType.Agility, 22.0, true);
            if (player.lastAgilityObjId == 20211)
                player.lastAgilityObjId = obj.id;
            player.unlock();
        }));
        /**
         * Ladder
         */
        ObjectAction.register(16682, "climb-down", (player, obj) -> Ladder.climb(player, player.getAbsX(), player.getAbsY(), 0, false, true, false));
        ObjectAction.register(16682, "climb-up", (player, obj) -> player.sendMessage("Why would you want to go backwards?"));
        /**
         * Crumbling wall one!
         */
        ObjectAction.register(1948, 2536, 3553, 0, "climb-over", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(839);
            player.getMovement().force(2, 0, 0, 0, 0, 60, Direction.EAST);
            event.delay(1);
            player.getStats().addXp(StatType.Agility, 13.7, true);
            if (player.lastAgilityObjId == 23547)
                player.lastAgilityObjId = obj.id;
            player.unlock();
        }));
        Tile.getObject(1948, 2536, 3553, 0).walkTo = new Position(2535, 3553, 0);
        /**
         * Crumbling wall two!
         */
        ObjectAction.register(1948, 2539, 3553, 0, "climb-over", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(839);
            player.getMovement().force(2, 0, 0, 0, 0, 60, Direction.EAST);
            event.delay(1);
            player.getStats().addXp(StatType.Agility, 13.7, true);
            player.unlock();
        }));
        Tile.getObject(1948, 2539, 3553, 0).walkTo = new Position(2538, 3553, 0);
        /**
         * Last crumbling wall! :- )
         */
        ObjectAction.register(1948, 2542, 3553, 0, "climb-over", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(839);
            player.getMovement().force(2, 0, 0, 0, 0, 60, Direction.EAST);
            event.delay(1);
            if (player.lastAgilityObjId == 1948) {
                player.getStats().addXp(StatType.Agility, 46.2, true);
                PlayerCounter.BARBARIAN_COURSE.increment(player, 1);
                AgilityPet.rollForPet(player, 22000);
            } else {
                player.getStats().addXp(StatType.Agility, 13.7, true);

            }
            player.lastAgilityObjId = -1;
            player.unlock();
        }));
        Tile.getObject(1948, 2542, 3553, 0).walkTo = new Position(2541, 3553, 0);
    }

}
