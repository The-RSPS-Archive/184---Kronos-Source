package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

public class BonesPeaches extends Spell {

    public static final Item[] RUNES = new Item[]{
            Rune.NATURE.toItem(2),
            Rune.WATER.toItem(4),
            Rune.EARTH.toItem(4)
    };
    public static final int LVL_REQ = 60;
    public static final double XP = 35.5;

    public BonesPeaches() {
        registerClick(LVL_REQ, XP, true, RUNES, BonesPeaches::cast);
    }

    public static boolean cast(Player p, Integer i) {
        int count = 0;
        for(Item item : p.getInventory().getItems()) {
            if(item != null && item.getDef().allowFruit) {
                item.setId(6883);
                count++;
            }
        }
        if(count > 0) {
            p.animate(722);
            p.graphics(141, 92, 0);
            return true;
        }
        p.sendMessage("You don't have any bones in your inventory to turn to peaches.");
        return false;
    }

    static {
        ItemAction.registerInventory(8015, 1, (player, item) -> {
            if (cast(player, 0)) {
                item.incrementAmount(-1);
            }
        });
    }

}