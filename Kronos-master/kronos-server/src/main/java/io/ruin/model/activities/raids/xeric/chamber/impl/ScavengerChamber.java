package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Direction;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class ScavengerChamber extends Chamber {

    private static final int[][][] scavengerSpawns = {
            {
                    {6, 16},
                    {10, 18},
                    {13, 20},
                    {19, 18},
                    {19, 15},
                    {16, 13},
            },
            {
                    {15, 5},
                    {13, 9},
                    {8, 13},
                    {5, 20},
                    {8, 20},
                    {14, 25},
            },
            {
                    {13, 7},
                    {11, 10},
                    {9, 16},
                    {11, 19},
                    {15, 16},
                    {19, 13},
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
