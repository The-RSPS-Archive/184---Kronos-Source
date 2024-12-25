package io.ruin.model.item.actions.impl;

import io.ruin.model.item.actions.ItemItemAction;

public class AvasAssembler {

    private static final int AVAS_ASSEMBLER = 22109;
    private static final int AVAS_ACCUMULATOR = 10499;
    private static final int[] VORKATH_HEADS = { 21907, 2425, 21912 };

    static {
        for(int VORKATH_HEAD : VORKATH_HEADS) {
            ItemItemAction.register(AVAS_ACCUMULATOR, VORKATH_HEAD, (player, primary, secondary) -> {
                primary.setId(AVAS_ASSEMBLER);
                secondary.remove();
                player.sendMessage("You use your Vorkath head on the Accumulator and create an Assembler.");
            });
        }
    }

}
