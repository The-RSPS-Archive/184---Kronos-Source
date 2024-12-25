package io.ruin.model.activities.raids.xeric.chamber.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class CorruptedScavengerChamber extends Chamber {



    private enum ChestType {
        POISON(29743),
        HATCHING(29744),
        HATCHED(29745),
        BATS(29743),
        EMPTY(29743),
        ;
        private int objId;
        ChestType(int objId) {
            this.objId = objId;
        }

    }

    private static final int[][] scavengerSpawns = {
            {7, 14},
            {22, 21},
            {22, 21},
    };

    private static final int[][] deathLocations = {
            {7, 17},
            {25, 21},
            {22, 18},
    };

    private static final int[][] troughLocations = { //x,y,dir
            {7, 13, 2},
            {21, 21, 3},
            {22, 23, 0},
    };

    private static final int EMPTY_TROUGH = 29746;
    private static final int FILLED_TROUGH = 29874;
    private static final int CORRUPTED_SCAVENGER = 7602;
    private static final int SLEEPING_SCAVENGER = 7603;
    private static final int CAVERN_GRUBS = 20885;
    private static final int CLOSED_CHEST = 29742;


    private GameObject trough;
    private int grubs = 0;
    private boolean dead;

    static {
        ObjectAction.register(CLOSED_CHEST, 1, (player, obj) -> player.startEvent(event -> {
            while (true) {
                if (obj.id != CLOSED_CHEST)
                    return;

                player.animate(832);
                if (Random.get() > (player.getStats().get(StatType.Thieving).currentLevel / 2.0 / 100.0)) {
                    event.delay(1);
                    return;
                }

                ChestType type = obj.get("GRUB_CHEST_TYPE", roll());
                ChestType nextType = type;
                obj.setId(type.objId);
                switch (type) {
                    case POISON:
                        World.sendGraphics(184, 100, 0, obj.x, obj.y, obj.z);
                        player.hit(new Hit(HitType.POISON).randDamage(1, 3));
                        break;
                    case EMPTY:
                        player.sendMessage("This chest is empty.");
                        break;
                    case BATS:
                        player.getInventory().addOrDrop(20883, 6);
                        player.dialogue(new ItemDialogue().one(20883, "You find some bats in the chest."));
                        nextType = ChestType.EMPTY;
                        break;
                    case HATCHING:
                        player.dialogue(new MessageDialogue("No cocoons in this chest have hatched."));
                        nextType = ChestType.HATCHED;
                        break;
                    case HATCHED:
                        player.getInventory().addOrDrop(CAVERN_GRUBS, 1);
                        player.dialogue(new ItemDialogue().one(CAVERN_GRUBS, "Some cavern grubs have hatched."));
                        break;
                }
                obj.set("GRUB_CHEST_TYPE", nextType);
                World.startEvent(worldEvent -> {
                    worldEvent.delay(12);
                    obj.restore();
                });
            }
        }));
    }

    private static ChestType roll() {
        if (Random.rollDie(20, 1))
            return ChestType.BATS;
        if (Random.rollDie(12, 1))
            return ChestType.POISON;
        if (Random.rollDie(3, 1))
            return ChestType.HATCHING;
        return ChestType.HATCHED;
    }

    @Override
    public void onRaidStart() {
        trough = getObject(EMPTY_TROUGH, troughLocations[getLayout()][0], troughLocations[getLayout()][1], troughLocations[getLayout()][2]);
        if (trough == null) {
            throw new RuntimeException("Could not find trough. rotation " + getRotation() + " layout " + getLayout());
        }
        ObjectAction.register(trough, 1, this::deposit);
        NPC npc = spawnNPC(CORRUPTED_SCAVENGER, scavengerSpawns[getLayout()][0], scavengerSpawns[getLayout()][1], rotatedDir(Direction.SOUTH, getLayout()), 0);
        for (int x = npc.getAbsX(); x < npc.getAbsX() + npc.getSize(); x++) {
            for (int y = npc.getAbsY(); y < npc.getAbsY() + npc.getSize(); y++) {
                Tile.get(x, y, getBasePosition().getZ(), true).flagUnmovable(); // stops players from accessing the exit
            }
        }
        npc.attackNpcListener = (player, npc1, message) -> {
            if (message)
                player.sendMessage("You can't attack this NPC.");
            return false;
        };
        npc.hitsUpdate.hpBarType = 7;
        npc.startEvent(event -> {
            int inactiveTicks = 0;
            int eatCooldown = 0;
            while (true) {
                if (eatCooldown > 0)
                    eatCooldown--;
                else if (grubs > 0) {
                    npc.animate(256);
                    grubs--;
                    if (grubs == 0)
                        trough.restore();
                    npc.setHp(Math.max(0, npc.getHp() - 8));
                    eatCooldown = 2;
                    if (npc.getHp() <= 0) {
                        npc.setHp(0);
                        dead = true;
                        death(npc);
                        npc.hitsUpdate.forceSend(npc.getHp(), npc.getMaxHp());
                        return;
                    }
                } else if (++inactiveTicks >= 6) {
                    npc.setHp(Math.min(npc.getHp() + 1, npc.getMaxHp()));
                    inactiveTicks = 0;
                }
                npc.hitsUpdate.forceSend(npc.getHp(), npc.getMaxHp());
                event.delay(1);
            }
        });
    }

    private void deposit(Player player, GameObject gameObject) {
        int amount = player.getInventory().getAmount(CAVERN_GRUBS);
        if (amount == 0) {
            player.sendMessage("You don't have any cavern grubs to deposit on the trough.");
            return;
        }
        if (dead) {
            player.sendMessage("Theres no point to deposting any more grubs.");
            return;
        }
        player.animate(832);
        player.getInventory().remove(CAVERN_GRUBS, amount);
        grubs += amount;
        if (trough.id == EMPTY_TROUGH)
            trough.setId(FILLED_TROUGH);
        player.sendMessage("You deposit the cave grubs in the trough.");
        ChambersOfXeric.addPoints(player, amount * 50);
    }

    private void death(NPC npc) {
        for (int x = npc.getAbsX(); x < npc.getAbsX() + npc.getSize(); x++) {
            for (int y = npc.getAbsY(); y < npc.getAbsY() + npc.getSize(); y++) {
                Tile.get(x, y, getBasePosition().getZ(), true).unflagUnmovable(); // unblock exit
            }
        }
        npc.startEvent(event -> {
            Position pos = getPosition(deathLocations[getLayout()][0], deathLocations[getLayout()][1]);
            npc.stepAbs(pos.getX(), pos.getY(), StepType.NORMAL);
            event.waitForMovement(npc);
            event.delay(1);
            npc.animate(7497);
            npc.forceText("Yawwwwwwwn!");
            event.delay(3);
            npc.transform(SLEEPING_SCAVENGER);
        });
    }
}
