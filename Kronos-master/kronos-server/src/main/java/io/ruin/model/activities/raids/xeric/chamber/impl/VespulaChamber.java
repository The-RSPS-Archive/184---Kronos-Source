package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.cache.ObjectDef;
import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class VespulaChamber extends Chamber {

    private static final int GRUB_MAX_HEALTH = 100;
    private static final int GRUB_BASE_HEALTH_DECAY = 2;
    private static final int GRUB_HEALTH_RESTORE = 25;

    private static final int[][] portalSpawns = {
            {17, 21},
            {23, 16},
            {11, 21},
    };

    private static final int[][] vespulaSpawns = {
            {16, 8},
            {10, 11},
            {12, 8},
    };

    private static final int[][][] grubSpawns = {
            {//l0
                    {13, 13},
                    {20, 13},
                    {12, 5},
                    {22, 8},
            },
            //l1
            {
                    {7, 16},
                    {7, 10},
                    {15, 16},
                    {15, 8}
            },
            {//l2
                    {15, 13},
                    {8, 13},
                    {5, 9},
                    {8, 5},
            }

    };

    private static final Direction[][] grubDirections = {
            {//l0
                    Direction.SOUTH, Direction.SOUTH, Direction.NORTH, Direction.WEST
            },
            {//l1
                    Direction.EAST, Direction.EAST, Direction.WEST, Direction.WEST,
            },
            {//l2
                    Direction.SOUTH, Direction.SOUTH, Direction.EAST, Direction.NORTH
            }

    };

    private static final int[][] blockSpawns = {
            {3, 15},
            {15, 27},
            {27, 15},
    };


    static { // Picking root
        ObjectAction.register(30068, "pick", (player, obj) -> {
            if (!player.getInventory().hasFreeSlots(1)) {
                player.sendMessage("Not enough space in your inventory.");
                return;
            }
            player.animate(827);
            player.getInventory().add(20892, 1);
            obj.setId(30069);
            World.startEvent(event -> {
               event.delay(8);
               if (!obj.isRemoved())
                   obj.setId(30068);
            });
        });
        ObjectDef.get(30061).clipType = 1;
    }

    private NPC portal;
    private NPC vespula;
    private int[] grubHealth = new int[4];
    private NPC[] grubs = new NPC[4];
    private List<NPC> vespineSoldiers = new LinkedList<>();
    @Override
    public void onRaidStart() {
        //spawn portal
        portal = spawnNPC(7533, portalSpawns[getLayout()], Direction.SOUTH, 0);
        //spawn vespula
        vespula = spawnNPC(7530, vespulaSpawns[getLayout()], Direction.SOUTH, 0);
        //spawn grubbers
        for (int i = 0; i < grubSpawns[getLayout()].length; i++) {
            NPC grub = grubs[i] = spawnNPC(7535, grubSpawns[getLayout()][i], grubDirections[getLayout()][i], 0);
            int grubId = i;
            grubHealth[grubId] = GRUB_MAX_HEALTH;
            grub.addEvent(event -> {
                while (!grub.isRemoved() && !portal.isRemoved()) {
                    if (grubHealth[grubId] > 0) {
                        grubHealth[grubId] -= GRUB_BASE_HEALTH_DECAY + (getRaid().getPartySize() / 5);
                        if (grubHealth[grubId] <= 0)
                            transformGrub(grub, grubId);
                        grub.hitsUpdate.forceSend(grubHealth[grubId], GRUB_MAX_HEALTH);
                    }
                    event.delay(2);
                }
            });
            NPCAction.register(grub, "feed", (player, npc) -> {
                if (!player.getInventory().contains(20892, 1)) {
                    player.sendMessage("You'll need a medivaemia blossom to feed the grub!");
                    return;
                }
                player.animate(827);
                player.getInventory().remove(20892, 1);
                grubHealth[grubId] += GRUB_HEALTH_RESTORE;
                grub.hitsUpdate.forceSend(grubHealth[grubId], GRUB_MAX_HEALTH);
                player.sendMessage("You feed the grub.");
            });
        }
        //roots
        for (int x = 0; x < TILE_SIZE; x++) {
            for (int y = 0; y < TILE_SIZE; y++) {
                Optional.ofNullable(getObject(30069, x,y, 0)).ifPresent(o -> o.setId(30068));
            }
        }
        //block crystal
        GameObject crystal = spawnObject(30018, blockSpawns[getLayout()], 10, 0);
        portal.deathStartListener = (entity, killer, killHit) -> {
            vespula.getCombat().startDeath(killHit);
            vespineSoldiers.forEach(soldier -> soldier.getCombat().startDeath(killHit));
        };
        portal.deathEndListener = (entity, killer, killHit) -> World.startEvent(event -> {
            crystal.animate(7506);
            event.delay(3);
            crystal.remove();
            portal.remove();
        });
        portal.attackNpcListener = (player, npc, message) -> {
            if (vespula.getId() == 7530) {
                if (message)
                    player.sendMessage("Vespula is protecting the portal!");
                return false;
            }
            return true;
        };
    }

    private void transformGrub(NPC grub, int grubId) {
        grub.transform(7537);
        grub.graphics(1365);
        NPC soldier = spawnNPC(7539, grubSpawns[getLayout()][grubId], grubDirections[getLayout()][grubId], 0);
        vespineSoldiers.add(soldier);
        soldier.attackBounds = new Bounds(soldier.spawnPosition, 16);
        vespula.setHp(vespula.getMaxHp());
        if (vespula.getId() == 7532) {
            vespula.animate(7452);
            vespula.transform(7530);
        }
    }
}
