package io.ruin.model.skills.fletching;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;

import static io.ruin.model.skills.Tool.FEATHER;

public enum Dart {

    BRONZE_DART(806, 819, 1, 1.8),
    IRON_DART(807, 820, 22, 3.8),
    STEEL_DART(808, 821, 37, 7.5),
    MITHRIL_DART(809, 822, 52, 11.2),
    ADAMANT_DART(810, 823, 67, 15.0),
    RUNE_DART(811, 824, 81, 18.8),
    DRAGON_DART(11230, 11232, 95, 25.0),

    BRONZE_BOLT(877, 9375, 9, 0.5),
    IRON_BOLT(9140, 9377, 39, 1.5),
    SILVER_BOLT(9145, 9382, 43, 3.0),
    STEEL_BOLT(9141, 9378, 46, 3.5),
    BROAD_BOLT(11875, 11876, 55, 2.5),
    MITHRIL_BOLT(9142, 9379, 54, 5.0),
    ADAMANT_BOLT(9143, 9380, 61, 7.0),
    RUNE_BOLT(9144, 9381, 69, 10.0),
    DRAGON_BOLTS(21905, 21930, 84, 12.0),
    ;

    public final int lvlReq;

    public final double xp;

    public final int unfinishedId, finishedId;

    public final String pluralName;

    Dart(int dartId, int tipId, int lvlReq, double xp) {
        this.lvlReq = lvlReq;
        this.xp = xp;
        this.unfinishedId = tipId;
        this.finishedId = dartId;
        this.pluralName = ItemDef.get(dartId).name.toLowerCase() + (ItemDef.get(dartId).name.endsWith("s") ? "" : "s");
    }

    private void make(Player player, Item tipItem, Item featherItem) {
        if(!player.getStats().check(StatType.Fletching, lvlReq, finishedId, "make " + pluralName))
            return;
        if (this == BROAD_BOLT && Config.BROADER_FLETCHING.get(player) == 0) {
            player.sendMessage("You haven't unlocked the ability to fletch broad bolts yet.");
            return;
        }
        int supplyCount = Math.min(tipItem.getAmount(), featherItem.getAmount());
        int toMake = Math.min(supplyCount, 10);
        tipItem.remove(toMake);
        featherItem.remove(toMake);
        player.getInventory().add(finishedId, toMake);
        player.getStats().addXp(StatType.Fletching, xp * toMake, true);
        player.sendFilteredMessage("You make " + toMake + " " + pluralName + ".");
    }

    static {
        for(Dart dart : values())
            ItemItemAction.register(dart.unfinishedId, FEATHER, dart::make);
    }

}