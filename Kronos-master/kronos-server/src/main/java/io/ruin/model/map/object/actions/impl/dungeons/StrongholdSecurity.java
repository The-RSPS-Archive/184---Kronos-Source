package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class StrongholdSecurity {

    private static void teleportPlayer(Player player, int x, int y) {
        player.startEvent(event -> {
            player.lock();
            player.animate(4282);
            event.delay(1);
            player.getMovement().teleport(x, y);
            player.animate(4283);
            event.delay(1);
            player.unlock();
        });
    }

    static {
        register();
    }

    public static void register() {
        /**
         * Entrance
         */
        ObjectAction.register(20790, "climb-down", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, false, false);
            player.dialogue(new MessageDialogue("You squeeze through the hole and find a ladder a few feet down leading into the Stronghold of Security."));
        });

        /**
         * First level
         */
        ObjectAction.register(20782, 1881, 5232, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3081, 3421, 0, true, true, false);
            player.sendFilteredMessage("You climb the ladder to the surface.");
        });
        ObjectAction.register(20784, 1913, 5226, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3081, 3421, 0, true, true, false);
            player.sendFilteredMessage("You climb the ladder to the surface.");
        });
        ObjectAction.register(20784, 1859, 5244, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3081, 3421, 0, true, true, false);
            player.sendFilteredMessage("You climb the ladder to the surface.");
        });
        ObjectAction.register(20786, 1863, 5238, 0, "use", (player, obj) -> {
            player.getMovement().teleport(1914, 5222, 0);
            player.sendFilteredMessage("You enter the portal to be whisked through to the treasure room.");
        });
        ObjectAction.register(20785, 1902, 5222, 0, "climb-down", (player, obj) -> {
            Ladder.climb(player, 2042, 5245, 0, false, true, false);
            player.sendFilteredMessage("You climb down the ladder to the next level.");
        });

        /**
         * Second level
         */
        ObjectAction.register(19003, 2042, 5246, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(19001, 2040, 5208, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(19001, 2031, 5189, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(19001, 2017, 5210, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(19001, 2011, 5192, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 1859, 5243, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(19005, 2039, 5240, 0, "use", (player, obj) -> {
            player.getMovement().teleport(2021, 5223, 0);
            player.sendFilteredMessage("You enter the portal to be whisked through to the treasure room.");
        });
        ObjectAction.register(19004, 2026, 5218, 0, "climb-down", (player, obj) -> {
            Ladder.climb(player, 2123, 5252, 0, false, true, false);
            player.sendFilteredMessage("You climb down the ladder to the next level.");
        });

        /**
         * Third level
         */
        ObjectAction.register(23705, 2123, 5251, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 2042, 5245, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(23703, 2150, 5278, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 2042, 5245, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(23707, 2120, 5258, 0, "use", (player, obj) -> {
            player.getMovement().teleport(2146, 5287, 0);
            player.sendFilteredMessage("You enter the portal to be whisked through to the treasure room.");
        });
        ObjectAction.register(23706, 2148, 5284, 0, "climb-down", (player, obj) -> {
            Ladder.climb(player, 2358, 5215, 0, false, true, false);
            player.sendFilteredMessage("You climb down the ladder to the next level.");
        });

        /**
         * Forth level
         */
        ObjectAction.register(23921, 2358, 5216, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 2123, 5252, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(23922, 2365, 5212, 0, "use", (player, obj) -> {
            player.getMovement().teleport(2341, 5219, 0);
            player.sendFilteredMessage("You enter the portal to be whisked through to the treasure room.");
        });
        ObjectAction.register(23732, 2309, 5240, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 2123, 5252, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });
        ObjectAction.register(23732, 2350, 5215, 0, "climb-up", (player, obj) -> {
            Ladder.climb(player, 2123, 5252, 0, true, true, false);
            player.sendFilteredMessage("You climb up the ladder to the level above.");
        });


        /**
         * Double doors
         */
        final int[] DOORS = {19206, 19207, 17009, 17100, 23653, 23654, 23727, 23728};
        for (int door : DOORS) {
            ObjectAction.register(door, "open", (player, obj) -> {
                boolean atObjX = obj.x == player.getAbsX();
                boolean atObjY = obj.y == player.getAbsY();

                if (obj.direction == 0 && atObjX)
                    teleportPlayer(player, player.getAbsX() - 1, player.getAbsY());
                else if (obj.direction == 1 && atObjY)
                    teleportPlayer(player, obj.x, obj.y + 1);
                else if (obj.direction == 2 && atObjX)
                    teleportPlayer(player, obj.x + 1, obj.y);
                else if (obj.direction == 3 && atObjY)
                    teleportPlayer(player, obj.x, obj.y - 1);
                else
                    teleportPlayer(player, obj.x, obj.y);
            });
        }

        /**
         * Gifts
         */
        ObjectAction.register(20656, "open", (player, obj) -> player.sendFilteredMessage("You find nothing interesting."));
        ObjectAction.register(19000, "open", (player, obj) -> player.sendFilteredMessage("You find nothing interesting."));
        ObjectAction.register(23731, "open", (player, obj) -> player.sendFilteredMessage("You find nothing interesting."));
    }
}
