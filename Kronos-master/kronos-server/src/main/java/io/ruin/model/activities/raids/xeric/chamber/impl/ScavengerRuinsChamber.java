package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Direction;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class ScavengerRuinsChamber extends Chamber {

    private static final int[][][] scavengerSpawns = {
            {
                    {13, 6},
                    {18, 8},
                    {22, 12},
                    {20, 22},
                    {9, 18},
                    {6, 13},
            },
            {
                    {16, 14},
                    {20, 18},
                    {16, 24},
                    {3, 7},
                    {21, 19},
                    {11, 13},
            },
            {
                    {26, 14},
                    {23, 17},
                    {19, 22},
                    {8, 21},
                    {10, 10},
                    {18, 6},
            },
    };


    @Override
    public void onRaidStart() {
        LinkedList<int[]> spawns = new LinkedList<>(Arrays.asList(scavengerSpawns[getLayout()]));
        Collections.shuffle(spawns);
        int spawnCount = getSpawnCount();
        for (int i = 0; i < spawnCount; i++) {
            int[] spawnPoint = spawns.pop();
            NPC scavenger = spawnNPC(Random.rollPercent(50) ? 7548 : 7549, spawnPoint, Direction.SOUTH, 4);
        }
    }

    private int getSpawnCount() {
        if (getRaid().getPartySize() > 9)
            return 4;
        else if (getRaid().getPartySize() > 4)
            return 3;
        return 2;
    }
}
