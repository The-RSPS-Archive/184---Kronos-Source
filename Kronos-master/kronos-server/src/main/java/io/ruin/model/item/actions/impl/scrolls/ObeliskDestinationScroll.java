package io.ruin.model.item.actions.impl.scrolls;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;

public class ObeliskDestinationScroll {

    static {
        ItemAction.registerInventory(1505, "read", (player, item) -> {
            if(player.obeliskRedirectionScroll) {
                player.dialogue(new ItemDialogue().one(item.getId(), "You can make out some faded word on the ancient parchment. It's an archaic invocation of the gods. However there's nothing more for you to learn."));
                return;
            }
            player.dialogue(
                    new ItemDialogue().one(item.getId(), "You can make out some faded words on the ancient parchment. It appears to be an archaic invocation of the gods! Would you like to absorb its power?"),
                    new OptionsDialogue("This will consume the scroll.",
                            new Option("Learn how to set Obelisk Directions", () -> {
                                item.remove();
                                player.obeliskRedirectionScroll = true;
                                player.animate(7403);
                                player.dialogue(new ItemDialogue().one(item.getId(), "You study the scroll and learn how to set Obelisk Destinations."));
                            }),
                            new Option("Cancel", player::closeDialogue)
                    )
            );
        });
    }

}
