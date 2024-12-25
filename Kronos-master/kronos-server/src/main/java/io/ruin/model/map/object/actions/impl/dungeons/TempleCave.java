package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class TempleCave {

    private static final int[] WALL_OF_FLAME = {4765, 4766};
    private static final int ZENYTE_SHARD = 19529;
    private static final int CUT_ONYX = 6573;
    private static final int ZENYTE = 19496;

    private static void forge(Player player) {
        Item shard = player.getInventory().findItem(ZENYTE_SHARD);
        Item onyx = player.getInventory().findItem(CUT_ONYX);

        if (shard == null || onyx == null) {
            player.dialogue(new ItemDialogue().two(ZENYTE_SHARD, CUT_ONYX,
                    "You need a <col=000080>cut onyx<col=000000> to fuse with a <col=000080>" +
                            "zenyte shard<col=000000> to<br><col=000000>create an uncut zenyte."));
            return;
        }
        player.dialogue(
                new YesNoDialogue("Are you sure you want to do this?", "Fuse your onyx and zenyte shard?", ZENYTE, 1, () ->
                        player.startEvent(event -> {
                            shard.remove();
                            onyx.remove();
                            player.getInventory().add(ZENYTE, 1);
                            player.sendFilteredMessage("You reach into the extremely hot flames and fuse the zenyte and onyx together, forming an uncut zenyte.");
                        })
                )
        );
    }

    static {
        /**
         * Trapdoor
         */
        ObjectAction.register(4879, "open", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(827);
            event.delay(1);
            obj.setId(4880);
            player.unlock();
        }));
        ObjectAction.register(4880, "close", (player, obj) -> player.startEvent(event -> {
            player.lock();
            obj.setId(4879);
            player.unlock();
        }));
        ObjectAction.register(4880, "climb-down", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.sendFilteredMessage("You climb down the trapdoor.");
            event.delay(1);
            Ladder.climb(player, 2807, 9201, 0, false, true, false);
            player.unlock();
        }));

        /**
         * Climbing rope
         */
        ObjectAction.register(4881, "climb", (player, obj) -> {
            player.startEvent(event -> {
                player.lock();
                player.animate(828);
                event.delay(1);
                player.getMovement().teleport(2806, 2785);
                player.unlock();
            });
        });

        /**
         * Wall of flame
         */
        for (int flame : WALL_OF_FLAME) {
            ItemObjectAction.register(ZENYTE_SHARD, flame, (player, item, obj) -> forge(player));
            ItemObjectAction.register(CUT_ONYX, flame, (player, item, obj) -> forge(player));
        }
    }
}
