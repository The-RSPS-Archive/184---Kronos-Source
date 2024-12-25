package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;

import java.util.LinkedList;
import java.util.List;

public class VasaNistiroChamber extends Chamber {

    private static final int ACTIVE_CRYSTAL = 29774;
    private static final int DRAINED_CRYSTAL = 29775;
    private static final int BLOCK_CRYSTAL = 30016;
    private static final int CRYSTAL_NPC = 7568;

    private static final int[][][] crystalSpawns = { // the ones used in fight
            {
                    {6, 23},
                    {23, 23},
                    {6, 5},
                    {23, 5},
            },
            {
                    {5, 22},
                    {23, 22},
                    {5, 5},
                    {23, 5},
            },
            {
                    {5, 23},
                    {22, 23},
                    {5, 5},
                    {22, 5},
            }
    };

    private static final int[][] blockCrystalSpawns = { // blocks passage to next room, 2 per layout
            {4, 15},
            {4, 16},
            {15, 27},
            {16, 27},
            {27, 15},
            {27, 16},
    };

    private static final int[] VASA_SPAWN = {14, 13};

    @Override
    public void onRaidStart() {
        GameObject blocking = spawnObject(BLOCK_CRYSTAL, blockCrystalSpawns[getLayout() * 2], 10, 0);
        GameObject blocking2 = spawnObject(BLOCK_CRYSTAL, blockCrystalSpawns[(getLayout() * 2) + 1], 10, 0);
        List<GameObject> crystals = new LinkedList<>();
        for (int i = 0; i < 4; i++) { // spawning crystals
            crystals.add(spawnObject(DRAINED_CRYSTAL, crystalSpawns[getLayout()][i], 10, 0));
            NPC crystal = spawnNPC(CRYSTAL_NPC, crystalSpawns[getLayout()][i], Direction.SOUTH, 0);
            int crystalIndex = i;
            crystal.attackNpcListener = (player, npc, message) -> {
                if (crystals.get(crystalIndex).id == DRAINED_CRYSTAL) {
                    if (message)
                        player.sendMessage("The crystal is currently invulnerable!");
                    return false;
                }
                return true;
            };
        }
        NPC vasa = spawnNPC(7565, VASA_SPAWN, Direction.SOUTH, 0);
        vasa.deathEndListener = (entity, killer, killHit) -> {
            vasa.remove();
            World.startEvent(event -> {
                blocking.animate(7506);
                blocking2.animate(7506);
                event.delay(3);
                blocking.remove();
                blocking2.remove();
            });
        };
    }
}
