package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;

import java.util.ArrayList;
import java.util.List;

public class IceDemonChamber extends Chamber {

    private static final int ICE_DEMON_FROZEN = 7584; // initial state (not melted)
    private static final int ICE_DEMON = 7585; // active fight
    private static final int ICE_FIEND = 7586;
    private static final int UNLIT_BRAZIER = 29747;
    private static final int LIT_BRAZIER = 29748;
    private static final int SNOW = 29876;
    private static final int[][][] brazierPositions = {
            { // layout 0
                    {11, 20},
                    {11, 23},
                    {8, 25},
                    {8, 18}
            },
            { // layout 1
                    {13, 21},
                    {16, 21},
                    {11, 24},
                    {18, 24}
            },
            { // layout 2
                    {18, 20},
                    {18, 17},
                    {21, 22},
                    {21, 15}
            }
    };

    private static final int[][][] icefiendPositions = {
            { // layout 0
                    {11, 21},
                    {11, 22},
                    {9, 25},
                    {9, 18}
            },
            { // layout 1
                    {14, 21},
                    {15, 21},
                    {11, 23},
                    {18, 23}
            },
            { // layout 2
                    {18, 19},
                    {18, 18},
                    {20, 22},
                    {20, 15}
            }
    };

    private static final int[][] demonSpawns = {
            {7, 21},
            {14, 24},
            {21, 18},
    };

    private static final int[][] walkPositions = {
            {13, 21},
            {14, 18},
            {15, 18},

    };

    private static final Direction[] demonFacing = {
            Direction.EAST, Direction.SOUTH, Direction.WEST,
    };

    static {
        ObjectAction.register(31634, 1, (player, obj) -> { // tinderbox spawn
            if (player.getInventory().contains(Tool.TINDER_BOX, 1)) {
                player.sendMessage("You already have a tinderbox.");
                return;
            }
            player.animate(827);
            player.getInventory().add(Tool.TINDER_BOX, 1);
        });
        ObjectAction.register(31862, 1, (player, obj) -> { // bronze axe
            if (player.getInventory().contains(1351, 1)) {
                player.sendMessage("You already have an axe.");
                return;
            }
            player.animate(827);
            player.getInventory().add(1351, 1);
        });
    }

    private static final int KINDLING = 20799;

    private List<GameObject> braziers = new ArrayList<>(4);
    private List<NPC> icefiends = new ArrayList<>(4);
    private int maxShieldHealth = 1000;
    private int shieldHealth = 1000;

    @Override
    public void onRaidStart() {
        NPC demon = spawnNPC(ICE_DEMON_FROZEN, demonSpawns[getLayout()][0], demonSpawns[getLayout()][1], demonFacing[getLayout()], 0);
        demon.hitsUpdate.hpBarType = 7;
        demon.attackNpcListener = (player, npc, message) -> {
          if (message)
              player.sendMessage("The demon is protected by ice, your attacks would be ineffective!");
          return false;
        };
        GameObject snow = spawnObject(SNOW, demonSpawns[getLayout()][0], demonSpawns[getLayout()][1], 10, 0);
        for (int i = 0; i < 4; i++) {
            GameObject obj = getObject(UNLIT_BRAZIER, brazierPositions[getLayout()][i][0], brazierPositions[getLayout()][i][1], 0);
            braziers.add(obj);
            ObjectAction.register(obj, 1, this::addKindling);
            if (i < getRaid().getPartySize()) {
                NPC icefiend = spawnNPC(ICE_FIEND, icefiendPositions[getLayout()][i][0], icefiendPositions[getLayout()][i][1], Direction.SOUTH, 0);
                icefiend.face(braziers.get(i));
                icefiends.add(icefiend);
            }
        }
        demon.addEvent(event -> {
            int damageTicks = 0;
            while (shieldHealth > 0) {
                if (damageTicks++ == 2) {
                    int remove = Math.min(2, getRaid().getPartySize());
                    int damage = 0;
                    for (int i = 0; i < 4; i++) {
                        GameObject obj = braziers.get(i);
                        int kindling = obj.get("KINDLING", 0);
                        if (kindling >= remove) {
                            damage += 10;
                            if (i < Math.min(3, getRaid().getPartySize())) { // if guarded by icefiend, consume more
                                remove *= 2;
                                icefiends.get(i).animate(7820);
                            }
                            kindling -= remove;
                            if (kindling < 0) kindling = 0;
                            obj.set("KINDLING", kindling);
                            if (kindling == 0)
                                obj.restore();
                        }
                    }
                    shieldHealth -= Math.min(damage, shieldHealth);
                    damageTicks = 0;
                }
                demon.hitsUpdate.forceSend(shieldHealth, maxShieldHealth);
                event.delay(1);
            } // all burned
            icefiends.forEach(n -> n.startEvent(deathEvent -> {
                n.animate(1580);
                deathEvent.delay(2);
                n.remove();
            }));
            Position dest = getPosition(walkPositions[getLayout()][0], walkPositions[getLayout()][1]);
            demon.getDef().ignoreOccupiedTiles = true;
            demon.stepAbs(dest.getX(), dest.getY(), StepType.NORMAL);
            event.waitForMovement(demon);
            event.delay(1);
            demon.hitsUpdate.hpBarType = 5;
            demon.attackNpcListener = null;
            demon.transform(ICE_DEMON);
            demon.deathEndListener = (entity, killer, killHit) -> {
                snow.remove();
                demon.remove();
            };
            // begin fight
        });
    }

    private void addKindling(Player player, GameObject gameObject) {
        int amount = player.getInventory().getAmount(KINDLING);
        if (amount == 0) {
            player.sendMessage("You'll need some kindling to light the brazier.");
            return;
        }
        if (gameObject.id == UNLIT_BRAZIER && !player.getInventory().contains(Tool.TINDER_BOX, 1)) {
            player.sendMessage("You'll need a tinderbox to light the brazier.");
            return;
        }
        player.animate(832);
        player.sendMessage("You add some kindling to the brazier.");
        player.getInventory().remove(KINDLING, amount);
        gameObject.set("KINDLING", gameObject.get("KINDLING", 0) + amount);
        if (gameObject.id == UNLIT_BRAZIER)
            gameObject.setId(LIT_BRAZIER);
        ChambersOfXeric.addPoints(player, amount * 25);
    }
}
