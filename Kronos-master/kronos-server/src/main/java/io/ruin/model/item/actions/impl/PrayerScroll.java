package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

public class PrayerScroll {

    public static final int RIGOUR_SCROLL = 21034;
    public static final int AUGURY_SCROLL = 21079;
    public static final int PRESERVE_SCROLL = 21047;

    static {
        ItemAction.registerInventory(RIGOUR_SCROLL, "read", (player, item) -> read(player, item, Config.RIGOUR_UNLOCK, "Rigour"));
        ItemAction.registerInventory(AUGURY_SCROLL, "read", (player, item) -> read(player, item, Config.AUGURY_UNLOCK, "Augury"));
        ItemAction.registerInventory(PRESERVE_SCROLL, "read", (player, item) -> read(player, item, Config.PRESERVE_UNLOCK, "Preserve"));
    }

    private static void read(Player player, Item item, Config config, String name) {
        if(config.get(player) == 1) {
            player.dialogue(new ItemDialogue().one(item.getId(), "You can make out some faded word on the ancient parchment. It's an archaic invocation of the gods. However there's nothing more for you to learn."));
            return;
        }
        player.dialogue(
                new ItemDialogue().one(item.getId(), "You can make out some faded words on the ancient parchment. It appears to be an archaic invocation of the gods! Would you like to absorb its power?"),
                new OptionsDialogue("This will consume the scroll.",
                        new Option("Learn " + name, () -> {
                            item.remove();
                            config.set(player, 1);
                            player.animate(7403);
                            player.dialogue(new ItemDialogue().one(item.getId(), "You study the scroll and learn a new prayer: <col=800000>" + name));
                        }),
                        new Option("Cancel", player::closeDialogue)
                )
        );
    }

}