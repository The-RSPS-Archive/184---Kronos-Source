package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.activities.raids.xeric.chamber.skilling.XericBat;
import io.ruin.model.map.Direction;
import io.ruin.model.stat.StatType;

public class HunterResourceChamber extends Chamber {

    private static final int[][][] batSpawns = {
            {
                    {18, 21},
                    {22, 23},
                    {23, 19},
                    {23, 18},
            },
            {
                    {13, 14},
                    {17, 16},
                    {18, 14},
                    {18, 15},
            },
            {
                    {17, 20},
                    {18, 16},
                    {20, 18},
                    {20, 19},
            }
    };

    @Override
    public void onRaidStart() {
        XericBat type = getBaseBatType();
        for (int i = 0; i < batSpawns[getLayout()].length; i++) { // will spawn 3 of the highest level catchable for the party, and 1 of the second highest
            spawnNPC(type.getNpcId(), batSpawns[getLayout()][i], Direction.SOUTH, 5);
            if (i >= 2 && type != XericBat.GUANIC)
                type = XericBat.values()[type.ordinal() - 1];
        }
    }

    private XericBat getBaseBatType() {
        int level = getRaid().getParty().getMembers().stream().mapToInt(p -> p.getStats().get(StatType.Hunter).fixedLevel).max().orElse(1);
        XericBat[] values = XericBat.values();
        for (int i = values.length - 1; i >= 0; i--) {
            XericBat type = values[i];
            if (level >= type.getLevelReq()) {
                return type;
            }
        }
        return XericBat.GUANIC;
    }
}
