package io.ruin.model.combat.special.ranged;

import io.ruin.cache.ItemDef;

public class MagicShortbowImbued extends MagicShortbow {

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 12788;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }
}
