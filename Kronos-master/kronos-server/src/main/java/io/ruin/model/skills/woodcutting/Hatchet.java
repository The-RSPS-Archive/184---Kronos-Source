package io.ruin.model.skills.woodcutting;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

public enum Hatchet {

    BRONZE(1, 879, 3291, 9),
    IRON(1, 877, 3290, 11),
    STEEL(6, 875, 3289, 14),
    BLACK(6, 873, 3288, 18),
    MITHRIL(21, 871, 3287, 22),
    ADAMANT(31, 869, 3286, 26),
    RUNE(41, 867, 3285, 31),
    DRAGON(61, 2846, 3292, 42),
    INFERNAL(61, 2117, 3292, 45);

    public final int levelReq, animationId, canoeAnimationId, points;

    Hatchet(int levelReq, int animationId, int canoeAnimationId, int points) {
        this.levelReq = levelReq;
        this.animationId = animationId;
        this.canoeAnimationId = canoeAnimationId;
        this.points = points;
    }

    private static Hatchet compare(Player player, Item item, Hatchet best) {
        if(item == null)
            return best;
        Hatchet hatchet = item.getDef().hatchet;
        if(hatchet == null)
            return best;
        if(player.getStats().get(StatType.Woodcutting).fixedLevel < hatchet.levelReq)
            return best;
        if(best == null)
            return hatchet;
        if(hatchet.levelReq < best.levelReq)
            return best;
        return hatchet;
    }

    public static Hatchet find(Player player) {
        Hatchet bestHatchet = null;
        for(Item item : player.getInventory().getItems())
            bestHatchet = Hatchet.compare(player, item, bestHatchet);
        Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
        return Hatchet.compare(player, weapon, bestHatchet);
    }

    static {
        ItemDef.forEach(def -> {
            String name = def.name.toLowerCase();
            for(Hatchet hatchet : Hatchet.values()) {
                if(name.startsWith(hatchet.name().toLowerCase() + " axe"))
                    def.hatchet = hatchet;
            }
        });
    }

}
