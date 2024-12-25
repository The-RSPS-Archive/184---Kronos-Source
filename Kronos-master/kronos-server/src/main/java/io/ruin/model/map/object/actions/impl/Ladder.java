package io.ruin.model.map.object.actions.impl;

import io.ruin.cache.ObjectDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

public class Ladder {

    public static void climb(Player player, int x, int y, int height, boolean climbingUp, boolean animate, boolean tileCheck) {
        if (tileCheck) {
           if(!climbingUp && player.getHeight() == 0) {
               player.dialogue(new PlayerDialogue("I don't think this ladder leads anywhere."));
               return;
           }
        }
        if (animate) {
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(climbingUp ? 828 : 827);
                e.delay(1);
                player.getMovement().teleport(x, y, height);
                player.unlock();
            });
        } else {
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.getMovement().teleport(x, y, height);
                e.delay(1);
                player.unlock();
            });
        }
    }

    static {
        ObjectDef.forEach(def -> {
            if (def.name.equalsIgnoreCase("ladder") && def.defaultActions == null) {
                /**
                 * Climb up
                 */
                ObjectAction.register(def.id, "climb-up", (p, obj) -> {
                    climb(p, p.getAbsX(), p.getAbsY(), p.getHeight() + 1, true, true, true);
                });
                /**
                 * Climb down
                 */
                ObjectAction.register(def.id, "climb-down", (p, obj) -> {
                    climb(p, p.getAbsX(), p.getAbsY(), p.getHeight() - 1, false, true, true);
                });
                /**
                 * Climb
                 */
                ObjectAction.register(def.id, "climb", (p, obj) -> {
                    p.dialogue(
                            new OptionsDialogue("Climb up or down the ladder?",
                                    new Option("Climb Up.", () -> climb(p, p.getAbsX(), p.getAbsY(), p.getHeight() + 1, true, true, true)),
                                    new Option("Climb Down.", () -> climb(p, p.getAbsX(), p.getAbsY(), p.getHeight() - 1, false, true, true))
                            ));
                });
            }
        });

        /**
         * Individually handled ladders
         */
        //Edgeville dungeon
        ObjectAction.register(17385, 3097, 9867, 0, "climb-up", (player, obj) -> climb(player, 3096, 3468, 0, true, true, false));

        //Edgeville -> Air Obelisk
        ObjectAction.register(17385, 3088, 9971, 0, "climb-up", (player, obj) -> climb(player, 3089, 3571, 0, true, true, false));

        //Air Obelisk -> Edgeville
        ObjectAction.register(16680, 3088, 3571, 0, "climb-down", (player, obj) -> climb(player, 3087, 9971, 0, false, true, false));

        //Camelot spinning wheel ladder
        ObjectAction.register(26118, 2715, 3472, 1, "climb-up", (player, obj) -> climb(player, 2714, 3472, 3, true, true, false));

        //Camelot spinning wheel roof ladder down
        ObjectAction.register(26119, 2715, 3472, 3, "climb-down", (player, obj) -> climb(player, 2714, 3472, 1, false, true, false));

        //Pirates' cove
        ObjectAction.register(16960, 2213, 3795, 0, "climb", ((player, obj) -> climb(player, 2213, 3796, 1, true, true, false)));
        ObjectAction.register(16962, 2213, 3795, 1, "climb", ((player, obj) -> climb(player, 2213, 3794, 0, false, true, false)));
        ObjectAction.register(16959, 2214, 3801, 1, "climb", ((player, obj) -> climb(player, 2214, 3800, 2, true, true, false)));
        ObjectAction.register(16961, 2214, 3801, 2, "climb", ((player, obj) -> climb(player, 2214, 3802, 1, false, true, false)));
        ObjectAction.register(16962, 2212, 3809, 1, "climb", ((player, obj) -> climb(player, 2211, 3809, 0, false, true, false)));
        ObjectAction.register(16960, 2212, 3809, 0, "climb", ((player, obj) -> climb(player, 2213, 3809, 1, true, true, false)));

        //Lunar Isle
        ObjectAction.register(16961, 2127, 3893, 2, "climb", ((player, obj) -> climb(player, 2126, 3893, 1, false, true, true)));
        ObjectAction.register(16959, 2127, 3893, 1, "climb", ((player, obj) -> climb(player, 2128, 3893, 2, true, true, true)));
        ObjectAction.register(16962, 2118, 3894, 1, "climb", ((player, obj) -> climb(player, 2118, 3895, 0, false, true, true)));
        ObjectAction.register(16960, 2118, 3894, 0, "climb", ((player, obj) -> climb(player, 2118, 3893, 1, true, true, true)));

        /**
         * Stairs
         */
        //Lucien's house (there is a clue upstairs)
        ObjectAction.register(16671, 2572, 3325, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2574, 3325, 1));
        ObjectAction.register(16673, 2573, 3325, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2573, 3324, 0));

        //Home
        ObjectAction.register(25801, 2021, 3566, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2021, 3567, 0));
        ObjectAction.register(25935, 2020, 3565, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2021, 3567, 1));

        /**
         * Ladders we don't want to work!
         */
        ObjectAction.register(17028, 3154, 3743, 0, 1, (player, obj) -> player.sendFilteredMessage("This ladder looks broken.. maybe I shouldn't climb up."));

    }
}
