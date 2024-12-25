package io.ruin.model.item.actions.impl;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.ItemAction;

public class SaradominLight {

    static {
        ItemAction.registerInventory(13256, "consume", (player, item) -> {
            if (Config.SARADOMINS_LIGHT.get(player) == 1) {
                player.dialogue(new ItemDialogue().one(13256, "You have already been enlightened by the light of<br>" +
                        "Saradomin. Perhaps you could give it to someone else."));
                return;
            }

            player.dialogue(
                    new ItemDialogue().one(13256, "As you commune with the holy star, you feel the light<br>" +
                    "of Saradomin preparing to fill your mind and enlighten<br>" +
                    "your vision. If you submit, it will help you see through<br>the darkness of Zamorak's evil."),
                    new YesNoDialogue("Are you sure you want to do this?", "Submit to the light of Saradomin?", item, () -> {
                        item.remove();
                        player.getPacketSender().sendClientScript(1068, "");
                        Config.SARADOMINS_LIGHT.set(player, 1);
                        player.dialogue(new ItemDialogue().one(13256, "You submit to the light of Saradomin.<br>" +
                                "Zamorak's darkness will henceforth have no effect on<br>you."));
                    })
            );
        });
    }
}
