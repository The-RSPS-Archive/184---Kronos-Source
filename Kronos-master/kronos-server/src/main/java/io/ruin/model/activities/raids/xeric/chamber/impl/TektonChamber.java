package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;

public class TektonChamber extends Chamber {

    private static final int[][] tektonSpawns = {
            {15, 18},
            {11, 14},
            {13, 18},
    };

    private static final int[][] crystals = {
            {7, 14},
            {14, 22},
            {21, 16},
    };

    @Override
    public void onRaidStart() {
        NPC tekton = spawnNPC(7540, tektonSpawns[getLayout()], getLayout() == 1 ? Direction.WEST : Direction.NORTH, 0);
        GameObject crystal = spawnObject(30017, crystals[getLayout()], 10, 0);
        tekton.deathEndListener = (entity, killer, killHit) -> {
            tekton.remove();
            World.startEvent(event -> {
                crystal.animate(7506);
                event.delay(3);
                crystal.remove();
            });
        };
    }


}
