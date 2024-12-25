package io.ruin.model.item.actions.impl;

import io.ruin.model.World;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.spells.HomeTeleport;

public class RoyalSeedPod {

    static {
        ItemAction.registerInventory(19564, "commune", (player, item) -> {
            player.getMovement().startTeleport(30, event -> {
                player.graphics(767);
                player.animate(4544);
                event.delay(3);
                player.getAppearance().setNpcId(716);
                event.delay(3);
                Position override = HomeTeleport.getHomeTeleportOverride(player);
                if (override != null) {
                    player.getMovement().teleport(override);
                } else {
                    if (!player.edgeHome) {
                        player.getMovement().teleport(World.HOME);
                    } else {
                        player.getMovement().teleport(World.EDGEHOME);

                    }
                }
                event.delay(2);
                player.graphics(769);
                event.delay(2);
                player.getAppearance().setNpcId(-1);
            });
        });
    }

}