package io.ruin.model.entity.player.ai.scripts;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.ai.AIPlayer;
import io.ruin.model.inter.journal.Journal;
import io.ruin.model.map.Position;

/**
 * Represents a melee bot script.
 */
public class MeleeBot extends AIScript {

    /**
     * The x and y.
     */
    int x = 3088, y = 3526;

    Position p = null;

    private long lastVeng = 0;

    /**
     * Whether we are fighting or not.
     */
    private boolean fighting = false;

    /**
     * Constructs a new melee bot.
     * @param player
     */
    public MeleeBot(AIPlayer player) {
        super(player);
    }

    @Override
    public void start() {
        player.getMovement().toggleRunning();
        //Journal.PRESETS.getEntries().get(0).select(player);
        player.handleDialogue(0);
        crossWildernessDitch();
        setDelay(3);
    }

    @Override
    public boolean run() {
       System.out.println("Current Y: " + player.getPosition().getY());
        if (player.getPosition().getY() == 3523 && p == null) {
            int xAdd = Random.get(4);
            int yAdd = Random.get(4);
            p = new Position(x + xAdd, y + yAdd, 0);
            player.move(p, false);
            System.out.println("Walking...");
            setDelay(2);
            return false;
        }
        if (!player.vengeanceActive && canVeng()) {
            castVengeance();
        }
        return false;
    }

    @Override
    public void finish() {
    System.out.println("Finishing...");
    }

    /**
     * Casts vengeance for the melee bot.
     */
    public void castVengeance() {
        player.handleInterfaceAction(218, 137, 1, -1, -1);
        lastVeng = Server.currentTick() + 50;
    }

    /**
     * Crosses the wilderness ditch.
     */
    public void crossWildernessDitch() {
        player.handleObjectInteraction(1, 23271, 3087, 3521, false);
    }

    public boolean canVeng() {
        return Server.currentTick() > lastVeng;
    }
}
