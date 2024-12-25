package io.ruin.central.utility;

import io.ruin.central.model.Player;
import io.ruin.central.model.world.World;

import java.util.ArrayList;
import java.util.Iterator;

public class Limbo {


    private static final ArrayList<Limbo> list = new ArrayList();
    private final int userId;
    private final int worldId;

    private Limbo(int userId, int worldId) {
        this.userId = userId;
        this.worldId = worldId;
    }

    public static void add(World world) {
        int added = 0;
        for (Player player : world.players) {
            list.add(new Limbo(player.userId, world.id));
            player.destroy();
            ++added;
        }
        if (added > 0) {
            System.out.println("Added " + added + " players to limbo on world " + world.id + "!");
        }
    }

    public static void clear(int worldId) {
        int removed = 0;
        Iterator<Limbo> it = list.iterator();
        while (it.hasNext()) {
            Limbo player = it.next();
            if (player.worldId != worldId) continue;
            it.remove();
            ++removed;
        }
        if (removed > 0) {
            System.out.println("Removed " + removed + " players from limbo on world " + worldId + "!");
        }
    }

    public static int get(int userId) {
        for (Limbo player : list) {
            if (player.userId != userId) continue;
            return player.worldId;
        }
        return -1;
    }
}

