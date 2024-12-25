package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.activities.pvminstances.InstanceDialogue;
import io.ruin.model.activities.pvminstances.InstanceType;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.impl.Spade;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Region;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

public class GiantMoleLair {

    static {
        ObjectAction.register(12230, "climb", (player, obj) -> {
            Ladder.climb(player, 2985, 3316, 0, true, true, false);
        });
        Bounds parkBounds = new Bounds(2982, 3368, 3027, 3389, 0);
        parkBounds.forEachPos(pos -> {
            if (Tile.getObject(12202, pos.getX(), pos.getY(), pos.getZ()) != null) {
                Spade.registerDig(pos.getX(), pos.getY(), pos.getZ(), player -> {
                    player.dialogue(new OptionsDialogue("Giant Mole",
                            new Option("Enter public lair", () -> {
                                player.getMovement().teleport(1752, 5236, 0);
                            }),
                            new Option("View instance options", () -> {
                                InstanceDialogue.open(player, InstanceType.GIANT_MOLE);
                            }),
                            new Option("Cancel"))
                    );
                });
            }
        });

        ObjectAction.register(12202, "look-inside", (player, obj) -> {
            int count = Region.get(6992).players.size() + Region.get(6993).players.size();
            if (count == 0)
                player.sendMessage("You look inside the hole and see nobody inside.");
            else if (count == 1)
                player.sendMessage("You look inside the hole and see one adventurer inside.");
            else
                player.sendMessage("You look inside the hole and see " + count + " adventurers inside.");
        });

    }

}
