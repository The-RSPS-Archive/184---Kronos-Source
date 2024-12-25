package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Tile;

import java.util.function.Consumer;

public class Spade {

    public static void registerDig(int x, int y, int z, Consumer<Player> action) {
        Tile.get(x, y, z, true).digAction = action;
    }

    public static void registerDig(Bounds bounds, Consumer<Player> action) {
        for(int x = bounds.swX; x <= bounds.neX; x++) {
            for(int y = bounds.swY; y <= bounds.neY; y++)
                registerDig(x, y, bounds.z, action);
        }
    }

    private static void dig(Player player) {
        player.resetActions(true, true, true);
        player.animate(830);
        Tile tile = Tile.get(player.getAbsX(), player.getAbsY(), player.getHeight(), false);
        player.startEvent(e -> {
            player.lock();
            e.delay(2);
            if(tile == null || tile.digAction == null)
                player.sendMessage("Nothing interesting happens.");
            else
                tile.digAction.accept(player);
            player.unlock();
        });
    }

    static {
        ItemAction.registerInventory(952, "dig", (p, i) -> dig(p));
    }

}
