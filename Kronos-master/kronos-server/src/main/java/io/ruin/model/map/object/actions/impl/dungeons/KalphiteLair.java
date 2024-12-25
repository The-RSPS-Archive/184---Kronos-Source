package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.activities.pvminstances.InstanceDialogue;
import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Region;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.stat.StatType;

public class KalphiteLair {

    static {
        ObjectAction.register(3832, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3509, 9496, 2, true, true, false);
        });
        ObjectAction.register(23609, 1, (player, obj) -> {
            if (Config.KALPHITE_LAIR_ROPE_INTERIOR.get(player) == 0)
                return; // rope not used
            player.getMovement().teleport(3507, 9494, 0);
        });
        ObjectAction peekAction = (player, obj) -> {
            int players = (int) Region.get(13972).players.stream().filter(p -> p.getHeight() == 0).count();
            if (players == 0)
                player.sendMessage("It doesn't look like there's anyone down there.");
            else
                player.sendMessage("It looks like there " + (players > 1 ? "are" : "is") + " " + players + " adventurer" + (players > 1 ? "s" : "") + " down there.");
        };
        ObjectAction.register(23609, 2, peekAction);
        ObjectAction.register(29705, "peek", peekAction);
        ObjectAction.register(29705, "instance", (player, obj) -> InstanceDialogue.open(player, InstanceType.KALPHITE_QUEEN));

        ObjectAction.register(16465, 1, (player, obj) -> {
            if (!player.getStats().check(StatType.Agility, 86, "use this shortcut")) {
                return;
            }
            if (player.isAt(3500, 9510)) {
                player.getMovement().teleport(3506, 9505, 2);
            } else {
                player.getMovement().teleport(3500, 9510, 2);
            }
        });
        ObjectAction.register(23596, 1, (player, obj) -> player.sendMessage("Looks like this tunnel is blocked."));
        ObjectAction.register(3829, "climb-up", (player, obj) -> {
            Ladder.climb(player, 3226, 3108, 0, true, true, false);
        });
        ObjectAction.register(3827, 1, (player, obj) -> {
            if (Config.KALPHITE_LAIR_ROPE_EXTERIOR.get(player) == 0)
                return; // rope not used
            player.getMovement().teleport(3484, 9510, 2);
        });

        ItemObjectAction.register(3827, (player, item, obj) -> {
            if (item.getId() != 954) {
                player.sendMessage("Nothing interesting happens.");
                return;
            }
            if (Config.KALPHITE_LAIR_ROPE_EXTERIOR.get(player) == 1) {
                player.sendMessage("You already have a rope set up here.");
                return;
            }
            item.remove();
            Config.KALPHITE_LAIR_ROPE_EXTERIOR.set(player, 1);
            player.sendMessage("You set up the rope.");
        });
        ItemObjectAction.register(23609, (player, item, obj) -> {
            if (item.getId() != 954) {
                player.sendMessage("Nothing interesting happens.");
                return;
            }
            if (Config.KALPHITE_LAIR_ROPE_INTERIOR.get(player) == 1) {
                player.sendMessage("You already have a rope set up here.");
                return;
            }
            item.remove();
            Config.KALPHITE_LAIR_ROPE_INTERIOR.set(player, 1);
            player.sendMessage("You set up the rope.");
        });
    }

}
