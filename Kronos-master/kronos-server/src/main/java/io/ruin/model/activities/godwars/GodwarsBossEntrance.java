package io.ruin.model.activities.godwars;

import io.ruin.model.activities.pvminstances.InstanceDialogue;
import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;

public class GodwarsBossEntrance {

    private static final int ECUMENICAL_KEY = 11942;

    private static int kcRequirement(Player player) {
        if (player.isGroup(PlayerGroup.ZENYTE)) {
            return 0;
        } else if (player.isGroup(PlayerGroup.ONYX)) {
            return 0;
        } else if (player.isGroup(PlayerGroup.DRAGONSTONE)) {
            return 0;
        } else if (player.isGroup(PlayerGroup.DIAMOND)) {
            return 10;
        } else if (player.isGroup(PlayerGroup.RUBY)) {
            return 20;
        } else if (player.isGroup(PlayerGroup.EMERALD)) {
            return 30;
        } else if (player.isGroup(PlayerGroup.SAPPHIRE)) {
            return 35;
        } else {
            return 40;
        }
    }

    private static boolean enterThroughDoor(Player player, Config config, String name) {
        Item key = player.getInventory().findItem(ECUMENICAL_KEY);
        if (key != null) {
            key.remove();
            player.sendFilteredMessage("Your ecumenical key melts into the door.");
            return true;
        }

        int requiredKC = kcRequirement(player);
        if (config.get(player) >= requiredKC) {
            player.sendFilteredMessage("The door devours the life-force of " + requiredKC + " followers of " + name + " that you have slain.");
            config.set(player, config.get(player) - requiredKC);
            return true;
        }

        player.sendFilteredMessage("This door is locked by the power of " + name + "! You need to collect the essence of at least " + requiredKC +
                " followers before the door will open.");
        return false;
    }

    static {
        /**
         * Zamorak
         */
        ObjectAction.register(26505, "open", (player, obj) -> {
            if(player.isAt(2925, 5333) && enterThroughDoor(player, Config.GWD_ZAMORAK_KC, "Zamorak")) {
                player.getMovement().teleport(2925, 5331, 2);
            } else if(player.getAbsY() < obj.y) {
                player.getMovement().teleport(2925, 5333, 2);
            }
        });
        ObjectAction.register(26505, 2925, 5332, 2, "instance", (player, obj) -> InstanceDialogue.open(player, InstanceType.ZAMORAK_GWD));

            /**
             * Bandos
             */
        ObjectAction.register(26503, "open", (player, obj) -> {
            if(player.isAt(2862, 5354) && enterThroughDoor(player, Config.GWD_BANDOS_KC, "Bandos")) {
                player.getMovement().teleport(2864, 5354, 2);
            } else if(player.getAbsX() > obj.x) {
                player.getMovement().teleport(2862, 5354, 2);
            }
        });
        ObjectAction.register(26503, 2863, 5354, 2, "instance", (player, obj) -> InstanceDialogue.open(player, InstanceType.BANDOS_GWD));


            /**
             * Saradomin
             */
        ObjectAction.register(26504, "open", (player, obj) -> {
            if(player.isAt(2909, 5265) && enterThroughDoor(player, Config.GWD_SARADOMIN_KC, "Saradomin")) {
                player.getMovement().teleport(2907, 5265, 0);
            } else if(player.getAbsX() < obj.x) {
                player.getMovement().teleport(2909, 5265, 0);
            }
        });
        ObjectAction.register(26504, 2908, 5265, 0, "instance", (player, obj) -> InstanceDialogue.open(player, InstanceType.SARADOMIN_GWD));

        /**
         * Armadyl
         */
        ObjectAction.register(26502, "open", (player, obj) -> {
            if(player.isAt(2839, 5294) && enterThroughDoor(player, Config.GWD_ARMADYL_KC, "Armadyl")) {
                player.getMovement().teleport(2839, 5296, 2);
            } else if(player.getAbsY() > obj.y) {
                player.getMovement().teleport(2839, 5294, 2);
            }
        });
        ObjectAction.register(26502, 2839, 5295, 2, "instance", (player, obj) -> InstanceDialogue.open(player, InstanceType.ARMADYL_GWD));

        }

}
