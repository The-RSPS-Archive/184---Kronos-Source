package io.ruin.model.item.actions.impl;

import io.ruin.cache.ItemDef;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.actions.ItemAction;

public class DestroyAction {

    static {
        ItemDef.forEach(def -> ItemAction.registerInventory(def.id, "destroy", (player, item) -> {
            player.dialogue(
                    new YesNoDialogue(
                            "Are you sure you want to destroy this item?",
                            "Warning: This action cannot be undone.",
                            item, item::remove
                    )
            );
        }));
    }

}
