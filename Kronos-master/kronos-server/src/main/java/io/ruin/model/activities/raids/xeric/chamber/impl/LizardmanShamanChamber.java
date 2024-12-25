package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;

public class LizardmanShamanChamber extends Chamber {

    private static final int SPIRIT_TENDRILS = 29768;
    static {
        ObjectAction.register(SPIRIT_TENDRILS, 1, (player, obj) -> {
            int x = player.getAbsX();
            int y = player.getAbsY();
            if (obj.direction == 0) {
                if (x < obj.x)
                    x += 2;
                else
                    x -= 2;
            } else {
                if (y < obj.y)
                    x += 2;
                else
                    x -= 2;
            }
            player.stepAbs(x, y, StepType.FORCE_WALK);
        });
    }
    private static final int SPAWN = 6768;

    private static final int[][][] tendrilSpawns = {
            {
                    {25, 13},
                    {25, 14},
            },
            {

            },
            {
                    {14, 4},
                    {15, 4},
            }
    };

    private static final int[][][] blockSpawns = {
            {
                    {4, 15},
                    {4, 16},
            },
            {
                    {15, 27},
                    {16, 27},
            },
            {
                    {25, 16},
                    {25, 17},
            }
    };

    private static final int[][][] shamanSpawns = {
            {
                    {9, 21},
                    {12, 8},
                    {15, 21},
                    {10, 13},
            },
            {
                    {10, 19},
                    {20, 11},
                    {10, 11},
                    {20, 20},
            },
            {
                    {12, 20},
                    {17, 10},
                    {11, 8},
                    {19, 22},
            }
    };

    private static int getShamanCount(int partySize) {
        if (partySize == 1)
            return 2;
        else if (partySize < 6)
            return 3;
        else
            return 4;
    }

    private static Direction[] directions = {Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST};


    private int liveShamans = 0;

    @Override
    public void onRaidStart() {
        int spawns[][] = tendrilSpawns[getLayout()];
        /*for (int[] spawn : spawns) {
            spawnObject(SPIRIT_TENDRILS, spawn[0], spawn[1], 10, getLayout());
        }*/
        NPC[] blocks = new NPC[2];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = spawnNPC(SPAWN, blockSpawns[getLayout()][i][0], blockSpawns[getLayout()][i][1], directions[(3 + getLayout()) % directions.length], 0);
            Tile.get(blocks[i].getPosition()).flagUnmovable();
        }
        liveShamans = getShamanCount(getRaid().getPartySize());
        for (int i = 0; i < liveShamans; i++) {
            NPC shaman = spawnNPC(i % 2 == 0? 7573 : 7574, shamanSpawns[getLayout()][i][0], shamanSpawns[getLayout()][i][1], Direction.SOUTH, 4);
            shaman.attackBounds = new Bounds(shaman.getSpawnPosition(), 20);
            shaman.deathEndListener = (entity, killer, killHit) -> {
                if (--liveShamans <= 0) {
                    for (NPC block: blocks) {
                        block.addEvent(event -> {
                            block.animate(7159);
                            event.delay(2);
                            World.sendGraphics(1295, 0, 0, block.getPosition());
                            Tile.get(block.getPosition()).unflagUnmovable();
                            block.remove();
                        });
                    }
                }
                shaman.remove();
            };
        }

    }

}
