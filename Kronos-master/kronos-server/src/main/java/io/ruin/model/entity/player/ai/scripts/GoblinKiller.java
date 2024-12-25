package io.ruin.model.entity.player.ai.scripts;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.ai.AIPlayer;

import java.util.Iterator;

public class GoblinKiller extends AIScript {

    int count = 0;

    NPC target = null;

    public GoblinKiller(AIPlayer player) {
        super(player);
    }

    @Override
    public void start() {
        player.max();
        player.getMovement().toggleRunning();
        player.equipItem(4151, true);
        player.equipItem(7462, true);
        player.equipItem(6737, true);
        player.equipItem(10828, true);
    }

    @Override
    public boolean run() {
        count++;
        if (target != null && target.getHp() > 0 || player.getCombat().isAttacking(3)) {
            return false;
        }
        Iterator<NPC> it = World.npcs.iterator();
        while (it.hasNext()) {
            NPC npc = it.next();
            if (npc.getDef().name.equalsIgnoreCase("goblin")) {
                if (npc.getPosition().isWithinDistance(player.getPosition(), 10)) {
                    target = npc;
                    player.attack(npc);
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void finish() {

    }

    @Override
    public int getDelay() {
        return 1;
    }
}
