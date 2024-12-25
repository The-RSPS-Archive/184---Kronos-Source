package io.ruin.model.item.actions.impl.scrolls.boosts;

import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

public class PetDropScroll {

    private static final int PET_DROP_SCROLL = 608;

    static {
        ItemAction.registerInventory(PET_DROP_SCROLL, "activate", (player, item) -> {
            if(player.petDropBonus.isDelayed()) {
                player.dialogue(new MessageDialogue("You already have a pet drop boost activated!"));
                return;
            }
            player.dialogue(new OptionsDialogue("Are you sure you want to active your scroll?",
                    new Option("Yes, active my pet drop boost scroll!", () -> {
                        item.remove();
                        player.petDropBonus.delaySeconds(30 * 60);
                        player.dialogue(new ItemDialogue().one(item.getId(), "You have activated your pet drop boost scroll. Your chance " +
                                "of getting a pet will be boosted by 25% for the next 30 minutes."));
                    }),
                    new Option("No, I'm not ready yet!")));
        });
        LoginListener.register(player -> {
            if (player.petDropBonusTimeLeft > 0) {
                player.petDropBonus.delay(player.petDropBonusTimeLeft);
                player.petDropBonusTimeLeft = 0;
            }
        });
    }
}
