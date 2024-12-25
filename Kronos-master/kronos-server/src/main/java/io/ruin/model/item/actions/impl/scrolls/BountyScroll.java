package io.ruin.model.item.actions.impl.scrolls;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class BountyScroll {

    static {
        ItemAction.registerInventory(12846, "read", (player, item) -> {
            if(Config.BOUNTY_HUNTER_TELEPORT.get(player) == 1) {
                player.dialogue(new ItemDialogue().one(12846, "You have already learned how to teleport to your bounty target."));
                return;
            }
            player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Llearn how to teleport to your bounty target?", new Item(12846, 1), () -> {
                item.remove();
                Config.BOUNTY_HUNTER_TELEPORT.set(player, 1);
                player.dialogue(new ItemDialogue().one(12846, "You use the energy from your scroll and learn how to teleport to your bounty target.").lineHeight(24));
            }));
        });
    }
}
