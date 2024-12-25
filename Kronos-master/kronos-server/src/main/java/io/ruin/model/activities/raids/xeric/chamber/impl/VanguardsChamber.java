package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;

import java.util.Arrays;

public class VanguardsChamber extends Chamber {

    private static final double HEAL_THRESHOLD = 0.4;

    static {
        for (int id = 7525; id <= 7529; id++) {
            NPCDef.get(id).ignoreOccupiedTiles = true;
        }
    }

    private static final int[][] crystalSpawns = {
            {4, 15},
            {15, 27},
            {22, 16},
    };

    private static final int[][] waypoints = {
            {11, 13},
            {19, 13},
            {15, 19},
    };

    private int[][] instancedWaypoints = new int[3][];
    private int[] lastWaypoints = new int[3];
    private boolean activated = false;
    private NPC[] vanguards = new NPC[3];

    @Override
    public void onRaidStart() {
        GameObject crystal = spawnObject(30017, crystalSpawns[getLayout()], 10, 0);
        for (int i = 0; i < waypoints.length; i++) {
            NPC vanguard = vanguards[i] = spawnNPC(7525, waypoints[i], Random.get(Direction.values()), 0);
            lastWaypoints[i] = i;
            instancedWaypoints[i] = new int[]{getBasePosition().getX() + rotatedX(waypoints[i][0], waypoints[i][1]), getBasePosition().getY() + rotatedY(waypoints[i][0], waypoints[i][1])};
            vanguard.deathEndListener = (entity, killer, killHit) -> {
                if (Arrays.stream(vanguards).allMatch(n -> n.getCombat().isDead())) {
                    World.startEvent(event -> {
                        crystal.animate(7506);
                        event.delay(3);
                        if (!crystal.isRemoved())
                            crystal.remove();
                    });
                }
                vanguard.remove();
            };
        }
        getRaid().getMap().addEvent(event -> {
            while (!activated) {
                if (vanguards[0].localPlayers().stream().anyMatch(p -> p.getPosition().isWithinDistance(vanguards[0].getPosition(), 8))) {
                    activated = true;
                    for (int i = 0; i < vanguards.length; i++) {
                        activated = true;
                        vanguards[i].animate(7428);
                        vanguards[i].transform(7527 + i);
                    }
                    event.delay(2);
                    for (NPC vanguard : vanguards) {
                        vanguard.animate(vanguard.getCombat().getInfo().spawn_animation);
                    }
                } else {
                    event.delay(1);
                }
            }
            event.delay(Random.get(50, 70));
            while (!crystal.isRemoved()) {
                //shuffle
                boolean heal = false/*getLargestDifference() > HEAL_THRESHOLD*/;
                for (NPC vanguard : vanguards) {
                    if (vanguard.isRemoved() || vanguard.getCombat().isDead())
                        continue;
                    vanguard.set("VANGUARD_ORIGINAL_ID", vanguard.getId());
                    vanguard.getCombat().reset();
                    vanguard.lock(LockType.FULL_NULLIFY_DAMAGE);
                    vanguard.animate(vanguard.getCombat().getInfo().death_animation);
                    vanguard.transform(7526);
                    if (heal)
                        vanguard.getCombat().restore();
                }
                event.delay(2);
                for (int i = 0; i < vanguards.length; i++) {
                    if (vanguards[i].isRemoved() || vanguards[i].getCombat().isDead())
                        continue;
                    lastWaypoints[i] = (lastWaypoints[i] + 1) % 3;
                    int[] waypoint = instancedWaypoints[lastWaypoints[i]];
                    vanguards[i].getRouteFinder().routeAbsolute(waypoint[0], waypoint[1]);
                }
                while (Arrays.stream(vanguards).anyMatch(n -> !n.getMovement().isAtDestination()))
                    event.delay(1);
                event.delay(1);
                for (NPC vanguard : vanguards) {
                    if (vanguard.isRemoved() || vanguard.getCombat().isDead())
                        continue;
                    vanguard.transform(vanguard.get("VANGUARD_ORIGINAL_ID", 0));
                    vanguard.animate(vanguard.getCombat().getInfo().spawn_animation);
                    vanguard.unlock();
                }
                event.delay(Random.get(60, 80));
            }
        });
    }

    private double getLargestDifference() {
        return Math.max(Math.abs(getHealthRatio(0) - getHealthRatio(1)),
                Math.max(Math.abs(getHealthRatio(0) - getHealthRatio(2)), Math.abs(getHealthRatio(1) - getHealthRatio(2))));
    }

    private double getHealthRatio(int index) {
        return  vanguards[index].getCombat().isDead() ? 0 : (double)vanguards[index].getHp() / vanguards[index].getMaxHp();
    }

}
