package io.ruin.model.skills.construction.room.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;
import io.ruin.model.skills.construction.room.Room;

public class ChapelRoom extends Room { // this is necessary because of how the god alignment works

    enum God {
        SARADOMIN(0, 0, 4),
        GUTHIX(2, 3, 2),
        ZAMORAK(1, 6, 6),
        BOB(0, 9, 0); // bob uses same altar as saradomin, not a bug!!

        int altarOffset;
        int statueOffset;

        God(int altarOffset, int statueOffset, int windowOffset) {
            this.altarOffset = altarOffset;
            this.statueOffset = statueOffset;
            this.windowOffset = windowOffset;
        }

        int windowOffset;
    }

    @Override
    protected int getHotspotReplacementId(Hotspot hotspot, Buildable built, int index) {
        God god = getRoomGod();
        int baseId = super.getHotspotReplacementId(hotspot, built, index);
        if (hotspot == Hotspot.ALTAR) {
            return baseId + god.altarOffset;
        } else if (hotspot == Hotspot.WINDOW) {
            return baseId + god.windowOffset;
        } else if (hotspot == Hotspot.STATUE) {
            return baseId + god.statueOffset;
        }
        return baseId;
    }

    @Override
    protected void onBuildableChanged(Player player, Hotspot hotspot, Buildable newBuildable) {
        if (hotspot == Hotspot.ICON) { // force rebuild
            getHouse().buildAndEnter(player, player.getPosition().localPosition(), true);
        }
    }

    private God getRoomGod() {
        Buildable icon = getBuilt(Hotspot.ICON);
        if (icon == null)
            return God.SARADOMIN;
        if (icon == Buildable.SARADOMIN_ICON || icon == Buildable.SARADOMIN_SYMBOL)
            return God.SARADOMIN;
        if (icon == Buildable.ZAMORAK_ICON || icon == Buildable.ZAMORAK_SYMBOL)
            return God.ZAMORAK;
        if (icon == Buildable.GUTHIX_ICON || icon == Buildable.GUTHIX_SYMBOL)
            return God.GUTHIX;
        if (icon == Buildable.BOB_ICON)
            return God.BOB;
        return God.SARADOMIN;
    }



}
