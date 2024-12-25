package io.ruin.model.activities.raids.xeric.chamber.skilling;

import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;

public class OldTools {

    static {
        ObjectAction.register(29771, "take", (player, obj) -> {
            player.dialogue(new OptionsDialogue(
                    new Option("Take Rake", () -> player.getInventory().add(Tool.RAKE, 1)),
                    new Option("Take Spade", () -> player.getInventory().add(Tool.SPADE, 1)),
                    new Option("Take Seed Dibber", () -> player.getInventory().add(Tool.SEED_DIBBER, 1)),
                    new Option("Take All", () -> {
                        if (!player.getInventory().hasFreeSlots(3)) {
                            player.dialogue(new MessageDialogue("You don't have enough inventory space."));
                            return;
                        }
                        player.getInventory().add(Tool.RAKE, 1);
                        player.getInventory().add(Tool.SPADE, 1);
                        player.getInventory().add(Tool.SEED_DIBBER, 1);
                        player.sendMessage("You 'borrow' a selection of tools, or is that 'lend'?");
                    })
            ));
        });
    }
}
