package io.ruin.model.skills.mining;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.stat.StatType;

public class MiningGuild {

    private static final int[] DWARVES = {7712, 7713, 7716};
    public static final Bounds MINERAL_AREA = new Bounds(3013, 9691, 3059, 9756, 0);

    private static void climbDown(Player player, int x, int y) {
        if (player.getStats().get(StatType.Mining).currentLevel < 60) {
            player.dialogue(new NPCDialogue(DWARVES[Random.get(DWARVES.length - 1)], "Sorry, but you need level 60 Mining to get in there.").animate(588));
            return;
        }

        Ladder.climb(player, x, y, 0, false, true, false);
    }

    private static void openDoor(Player player, GameObject door) {
        int miningLevel = player.getStats().get(StatType.Mining).currentLevel;
        if(miningLevel < 60) {
            player.dialogue(
                    new NPCDialogue(7712, "Sorry, but you're not experienced enough to go in there.").animate(588),
                    new MessageDialogue("You need a Mining level of 60 to access the Mining Guild."));
            return;
        }

        player.startEvent(event -> {
            player.lock();

            if(!player.isAt(door.x, player.getAbsY())) {
                player.stepAbs(door.x, player.getAbsY(), StepType.FORCE_WALK);
                event.delay(1);
            }
            GameObject opened = GameObject.spawn(1539, door.x, door.y + 1, door.z, door.type, 2);
            door.skipClipping(true).remove();
            if(player.getAbsY() > door.y)
                player.stepAbs(player.getAbsX(), player.getAbsY() - 1, StepType.FORCE_WALK);
            else
                player.stepAbs(player.getAbsX(), player.getAbsY() + 1, StepType.FORCE_WALK);
            event.delay(2);
            door.restore().skipClipping(false);
            opened.remove();

            player.unlock();
        });
    }

    static {
        /**
         * Ladders down
         */
        ObjectAction.register(30367, 3018, 3339, 0, "climb-down", (player, obj) -> climbDown(player, 3017, 9739));
        ObjectAction.register(30367, 3019, 3338, 0, "climb-down", (player, obj) -> climbDown(player, 3019, 9737));
        ObjectAction.register(30367, 3020, 3339, 0, "climb-down", (player, obj) -> climbDown(player, 3021, 9739));
        ObjectAction.register(30367, 3019, 3340, 0, "climb-down", (player, obj) -> climbDown(player, 3019, 9741));

        /**
         * Ladders up
         */
        ObjectAction.register(17385, 3019, 9740, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3019, 3341, 0, true, true, false));
        ObjectAction.register(17385, 3019, 9738, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3019, 3337, 0, true, true, false));
        ObjectAction.register(17385, 3020, 9739, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3021, 3339, 0, true, true, false));
        ObjectAction.register(17385, 3018, 9739, 0, "climb-up", (player, obj) -> Ladder.climb(player, 3017, 3339, 0, true, true, false));

        /**
         * Falador mine
         */
        ObjectAction.register(16664, 3058, 3376, 0, "climb-down", (player, obj) -> player.getMovement().teleport(3058, 9776));
        ObjectAction.register(23969, 3059, 9776, 0, "climb-up", (player, obj) -> player.getMovement().teleport(3061, 3376));

        /**
         * Doors
         */
        ObjectAction.register(30364, 3046, 9756, 0, "open", MiningGuild::openDoor);
        ObjectAction.register(30366, 3043, 9729, 0, "open", MiningGuild::openDoor);
        ObjectAction.register(30365, 3019, 9732, 0, "open", MiningGuild::openDoor);
    }
}
