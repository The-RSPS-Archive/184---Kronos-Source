package io.ruin.model.item.actions.impl;

import io.ruin.model.item.actions.ItemItemAction;

public class CrystalKey {

    private static final int CRYSTAL_LOOP = 987;
    private static final int CRYSTAL_TOOTH = 985;
    private static final int CRYSTAL_KEY = 989;

    static {
        ItemItemAction.register(CRYSTAL_LOOP, CRYSTAL_TOOTH, (player, primary, secondary) -> {
            primary.setId(CRYSTAL_KEY);
            secondary.remove();
            player.sendMessage("You join the two halves of the key together.");
        });
    }

}
