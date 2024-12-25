package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.actions.ObjectAction;

public class TightRopeChamber extends Chamber {

    private static final int BARRIER = 29749;
    private static final int CRYSTAL_ITEM = 20884;
    private static final int CRYSTAL_OBJECT = 29751;
    private static final int TIGHTROPE = 29750;

    private static final int RANGER = 7559;
    private static final int MAGE = 7560;

    static {
        ObjectAction.register(BARRIER, 1, (player, obj) -> {
            if (!player.getInventory().contains(CRYSTAL_ITEM, 1)) {
                player.sendMessage("You'll need the keystone crystal to dispel this barrier.");
                return;
            }
            player.animate(832);
            obj.remove();
            player.getInventory().remove(CRYSTAL_ITEM, 1);
            ChambersOfXeric.addPoints(player, 1200);
            player.sendMessage("You dispel the barrier. The path is now open.");
        });
        ObjectAction.register(CRYSTAL_OBJECT, 1, (player, obj) -> {
            if (player.getInventory().isFull()) {
                player.sendMessage("Not enough space in your inventory.");
                return;
            }
            player.stepAbs(obj.x, obj.y, StepType.FORCE_WALK);
            obj.remove();
            player.getInventory().add(CRYSTAL_ITEM, 1);
        });
        ObjectAction.register(TIGHTROPE, 1, (player, obj) -> {
            int x = 0;
            int y = 0;
            if (obj.direction == 0 || obj.direction == 2) {
                if (obj.y < player.getAbsY())
                    y = -1;
                else
                    y = 1;
            } else {
                if (obj.x < player.getAbsX())
                    x = -1;
                else
                    x = 1;
            }
            int finalX = x;
            int finalY = y;
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.clearHits();
            player.addEvent(event -> {
                for (NPC npc: player.localNpcs()) {
                    if (npc.getCombat() != null && !npc.getCombat().isDead() && npc.getCombat().getTarget() == null) {
                        npc.getCombat().setTarget(player);
                        npc.face(player);
                        if (Random.rollDie(2, 1))
                            npc.forceText("Protect the keystone!");
                    }
                }
                player.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
                player.step(finalX * 10, finalY * 10, StepType.FORCE_WALK);
                event.waitForMovement(player);
                player.getAppearance().removeCustomRenders();
                event.delay(1);
                player.unlock();
            });
        });
    }

    private static final int[][][] rangerSpawns = {
            {
                    {20, 26},
                    {20, 25},
                    {21, 26},
            },
            {
                    {20, 26},
                    {20, 25},
                    {21, 26},
            },
            {
                    {6, 20},
                    {5, 20},
                    {6, 22},
            },
    };

    private static final int[][][] mageSpawns = {
            {
                    {21, 20},
                    {22, 20},
                    {21, 19},
            },
            {
                    {21, 20},
                    {22, 20},
                    {21, 19},
            },
            {
                    {11, 21},
                    {12, 22},
                    {12, 21},
            },
    };

    private static final int[][] barrierPositions = {
            {6, 16},
            {13, 24},
            {27, 15},
    };

    @Override
    public void onRaidStart() {
        spawnObject(BARRIER, barrierPositions[getLayout()][0], barrierPositions[getLayout()][1], 10, getLayout()); // barrier that blocks the entrance
        for (int i = 0; i < (getRaid().getPartySize() < 6 ? 2 : 3); i++) {
            spawnNPC(RANGER, rangerSpawns[getLayout()][i][0], rangerSpawns[getLayout()][i][1], i == 0 ? Direction.WEST : Direction.SOUTH, 2);
            spawnNPC(MAGE, mageSpawns[getLayout()][i][0], mageSpawns[getLayout()][i][1], i == 0 ? Direction.WEST : Direction.SOUTH, 2);
        }
    }

}
