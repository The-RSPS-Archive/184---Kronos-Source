package io.ruin.model.skills.agility.courses;

import io.ruin.model.World;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.stat.StatType;

public class WildernessCourse {

    private static void openGateEntrance() {
        World.startEvent(e -> {
            GameObject gate = Tile.getObject(23555, 2998, 3917, 0);
            if(gate != null) { //important check in case gate is currently opened.
                GameObject gateReplacement = GameObject.spawn(1548, 2998, 3916, 0, 0, 0);
                gate.skipClipping(true).remove();
                e.delay(2);
                gate.restore().skipClipping(false);
                gateReplacement.remove();
            }
        });
    }

    private static void openDoubleGate() {
        World.startEvent(e -> {
            GameObject eastGate = Tile.getObject(23552, 2998, 3931, 0);
            GameObject westGate = Tile.getObject(23554, 2997, 3931, 0);
            if(eastGate != null && westGate != null) { //important check in case gate is currently opened.
                GameObject eastGateReplacement = GameObject.spawn(1573, 2998, 3930, 0, 0, 2);
                GameObject westGateReplacement = GameObject.spawn(1574, 2997, 3930, 0, 0, 0);
                eastGate.skipClipping(true).remove();
                westGate.skipClipping(true).remove();
                e.delay(2);
                westGate.restore().skipClipping(false);
                eastGate.restore().skipClipping(false);
                westGateReplacement.remove();
                eastGateReplacement.remove();
            }
        });
    }

    static {
        /**
         * Lower gate
         */
        ObjectAction.register(23555, "open", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 52, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(2998, 3931, StepType.FORCE_WALK);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            openGateEntrance();
            e.delay(15);
            openDoubleGate();
            e.waitForMovement(p);
            p.insideWildernessAgilityCourse = true;
            p.getAppearance().removeCustomRenders();
            p.unlock();
        }));
        /**
         * Upper gate
         */
        int[] upperGate = {23552, 23554};
        for (int gate : upperGate) {
            ObjectAction.register(gate, "open", (p, obj) -> p.startEvent(e -> {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                if(p.getAbsX() != 2998 || p.getAbsY() != 3931) {
                    p.stepAbs(2998, 3931, StepType.FORCE_WALK);
                    e.delay(1);
                }
                openDoubleGate();
                p.stepAbs(2998, 3916, StepType.FORCE_WALK);
                p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
                e.delay(14);
                openGateEntrance();
                e.waitForMovement(p);
                p.insideWildernessAgilityCourse = false;
                p.getAppearance().removeCustomRenders();
                p.unlock();
            }));
        }
        /**
         * Obstacle Tunnel
         */
        ObjectAction.register(23137, 3004, 3938, 0, "squeeze-through", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            if(p.getAbsX() != 3004 && p.getAbsY() != 3937) {
                p.stepAbs(3004, 3937, StepType.FORCE_WALK);
                e.delay(1);
            }
            p.animate(749, 30);
            p.getMovement().force(0, 3, 0, 0, 33, 126, Direction.NORTH);
            e.delay(4);
            p.getMovement().teleport(p.getAbsX(), p.getAbsY() + 3, 0);
            e.delay(2);
            p.step(0, 6, StepType.FORCE_WALK);
            e.delay(4);
            p.animate(749, 30);
            p.getMovement().force(0, 3, 0, 0, 33, 126, Direction.NORTH);
            e.waitForMovement(p);
            p.getStats().addXp(StatType.Agility, 12.5, true);
            p.lastAgilityObjId = obj.id;
            p.unlock();
        }));
        ObjectAction.register(23137, 3004, 3948, 0, "squeeze-through", (player, obj) -> player.sendMessage("You can't enter the pipe from this side."));
        /**
         * Ropeswing
         */
        ObjectAction ropeswingAction = (p, obj) -> {
            p.startEvent(e -> {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                obj.animate(54);
                p.animate(751);
                p.getMovement().force(0, 5, 0, 0, 30, 50, Direction.NORTH);
                e.delay(1);
                obj.animate(55);
                p.getStats().addXp(StatType.Agility, 20.0, true);
                if (p.lastAgilityObjId == 23137)
                    p.lastAgilityObjId = obj.id;
                p.unlock();
            });
        };
        ObjectAction.register(23132, "swing-on", ropeswingAction);
        Tile.getObject(23132, 3005, 3952, 0).walkTo = new Position(3005, 3953, 0);
        /**
         * Stepping stone
         */
        ObjectAction.register(23556, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            for (int jump = 0; jump < 6; jump++) {
                p.animate(741);
                p.getMovement().force(-1, 0, 0, 0, 5, 35, Direction.WEST);
                if (jump != 5) e.delay(2);
                else e.delay(1);
            }
            p.getStats().addXp(StatType.Agility, 20.0, true);
            if (p.lastAgilityObjId == 23132)
                p.lastAgilityObjId = obj.id;
            p.unlock();
        }));
        ObjectAction.register(14758, 3005, 3963, 0, "climb-down", (player, obj) -> Ladder.climb(player, player.getAbsX(), player.getAbsY() + 6400, 0, false, true, false));
        ObjectAction.register(17385, 3005, 10363, 0, "climb-up", (player, obj) -> Ladder.climb(player, player.getAbsX(), player.getAbsY() - 6400, 0, true, true, false));
        Tile.get(2997, 3960, 0).flagUnmovable();
        Tile.get(3001, 3960, 0).flagUnmovable();
        /**
         * Log balance
         */
        ObjectAction.register(23542, "walk-across", (player, obj) -> player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.sendMessage("You walk carefully across the slippery log...");
            player.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            player.stepAbs(2994, 3945, StepType.FORCE_WALK);
            e.delay(8);
            player.sendMessage("...you make it safely to the other side.");
            player.getStats().addXp(StatType.Agility, 20.0, true);
            player.getAppearance().removeCustomRenders();
            if (player.lastAgilityObjId == 23556)
                player.lastAgilityObjId = obj.id;
            player.unlock();
        }));
       Tile.getObject(23542, 3001, 3945, 0).walkTo = new Position(3002, 3945, 0);
        /**
         * Rocks
         */
        Tile.getObject(23640, 2993, 3936, 0).walkTo = new Position(2993, 3937, 0);
        ObjectAction.register(23640, "climb", (player, obj) -> player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(740);
            player.getMovement().force(0, -4, 0, 0, 5, 80, Direction.SOUTH);
            e.delay(3);
            player.resetAnimation();
            if (player.lastAgilityObjId == 23542) {
                player.getStats().addXp(StatType.Agility, 498.9, true);
                AgilityPet.rollForPet(player, 17000);
                PlayerCounter.WILDERNESS_COURSE.increment(player, 1);
                player.lastAgilityObjId = -1;
            }
            player.unlock();
        }));
    }


}
