package io.ruin.model.item.actions.impl.scrolls;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;

public enum TeleportScroll {

    NARDAH(12402, new Bounds(3419, 2916, 3421, 2918, 0)),
    DIGSITE(12403, new Bounds(3324, 3411, 3327, 3414, 0)),
    FELDIP_HILLS(12404, new Bounds(2540, 2924, 2542, 2926, 0)),
    REVENANT_SCROLL(21802, new Bounds(3128, 3830, 3131, 3835, 0)),
    LUNAR_ISLE(12405, new Bounds(2098, 3913, 2101, 3915, 0)),
    MORT_TON(12406, new Bounds(3487, 3287, 3489, 3289, 0)),
    PEST_CONTROL(12407, new Bounds(2657, 2658, 2659, 2660, 0)),
    PISCATORIS(12408, new Bounds(2338, 3648, 2341, 3651, 0)),
    TAI_BWO_WANNAI(12409, new Bounds(2787, 3064, 2791, 3067, 0)),
    ELF_CAMP(12410, new Bounds(2202, 3352, 2206, 3354, 0)),
    MOS_LE_HARMLESS(12411, new Bounds(3684, 2968, 3687, 2971, 0)),
    LUMBRIDGE_YARD(12642, new Bounds(3306, 3488, 3309, 3490, 0)),
    ZAL_ANDRA(12938, new Bounds(2194, 3055, 2197, 3057, 0)),
    KEY_MASTER(13249, new Bounds(1312, 1249, 1315, 1251, 0));

    public final int id;

    public final Bounds bounds;

    TeleportScroll(int id, Bounds bounds) {
        this.id = id;
        this.bounds = bounds;
    }

    private void teleport(Player player, Item scroll) {
        player.getMovement().startTeleport(event -> {
            player.animate(3864);
            player.graphics(1039);
            player.privateSound(200, 0, 10);
            scroll.remove(1);
            event.delay(2);
            player.getMovement().teleport(bounds);
        });
    }

    static {
        for(TeleportScroll scroll : values())
            ItemAction.registerInventory(scroll.id, "teleport", scroll::teleport);
    }

}
