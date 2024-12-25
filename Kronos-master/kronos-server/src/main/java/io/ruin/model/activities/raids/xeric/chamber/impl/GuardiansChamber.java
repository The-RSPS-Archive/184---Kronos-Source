package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.combat.Hit;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class GuardiansChamber extends Chamber {

    private static final int[][][] spawns = {
            {
                    {12, 21},
                    {12, 17},
            },
            {
                    {12, 23},
                    {16, 23},
            },
            {
                    {16, 24},
                    {16, 20},
            }
    };

    private static final int[][] passages = {
            {8, 19},
            {14, 26},
            {19, 22},
    };

    private static Direction[] directions = {Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST};

    @Override
    public void onRaidStart() {
        for (int i = 0; i < 2; i++) {
            spawnNPC(7569 + i, spawns[getLayout()][i][0], spawns[getLayout()][i][1], directions[(3 + getLayout()) % directions.length], 0);
        }
        GameObject passage = getObject(29789, passages[getLayout()][0], passages[getLayout()][1], getLayout());
        ObjectAction.register(passage, 1, (player, obj) -> {
            if (player.localNpcs().stream().anyMatch(npc -> npc.getId() == 7569 || npc.getId() == 7570)) {
                player.sendMessage("The guardians prevent you from passing.");
                player.hit(new Hit().randDamage(5, 10));
            } else {
                obj.getDef().defaultActions[0].handle(player, obj);
            }
        });
    }
}
