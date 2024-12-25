package io.ruin.model.item.actions.impl.combine;

import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.actions.ItemAction;

public class AvernicDefender {

    private static final int DEFENDER_HILT = 22477;
    private static final int DRAGON_DEFENDER = 12954;

    static {
        ItemAction.registerInventory(DEFENDER_HILT, 1, (player, item) ->
                player.dialogue(new ItemDialogue().two(DEFENDER_HILT, DRAGON_DEFENDER, "You can combine this with a dragon defender to create an Avernic Defender.")));
    }
}
