package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;

import java.util.ArrayList;

public class BrimstoneRing {

    private static final int HYDRA_EYE = 22973;
    private static final int HYDRA_FANG = 22971;
    private static final int HYDRA_HEART = 22969;
    private static final int BRIMSTONE_RING = 22975;

    private static void makeRing(Player player) {
        ArrayList<Item> items = player.getInventory().collectOneOfEach(HYDRA_EYE, HYDRA_FANG, HYDRA_HEART);
        if (items == null) {
            player.sendMessage("You need Hydra's eye, Hydra's fang Hydra's heart in your inventory in order to construct a Brimstone ring.");
            return;
        }
        player.dialogue(
                new YesNoDialogue("Are you sure you want to do this?", "Combining these items into a Brimstone ring will be irreversible", BRIMSTONE_RING, 1, () -> {
                    items.forEach(Item::remove);
                    player.getInventory().add(BRIMSTONE_RING, 1);
                    player.sendMessage("You combine the items into a Brimstone ring.");
                })
        );
    }

    static {
        ItemItemAction.register(HYDRA_EYE, HYDRA_FANG, (player, primary, secondary) -> makeRing(player));
        ItemItemAction.register(HYDRA_EYE, HYDRA_HEART, (player, primary, secondary) -> makeRing(player));
        ItemItemAction.register(HYDRA_FANG, HYDRA_HEART, (player, primary, secondary) -> makeRing(player));
    }
}
