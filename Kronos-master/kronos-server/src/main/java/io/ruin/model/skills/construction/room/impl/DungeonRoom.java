package io.ruin.model.skills.construction.room.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.ChallengeMode;
import io.ruin.model.skills.construction.Hotspot;
import io.ruin.model.stat.StatType;

import java.util.List;

public class DungeonRoom extends DungeonGuardedRoom { // rooms with floor traps -> corridor and junction rooms
    @Override
    protected void onBuild() {
        super.onBuild();
        registerFloorTraps();
    }

    private void registerFloorTraps() {
        for (Hotspot hotspot : new Hotspot[]{Hotspot.DUNGEON_TRAP_1, Hotspot.DUNGEON_TRAP_2}) {
            Buildable trap = getBuilt(hotspot);
            if (trap == null) {
                continue;
            }
            List<GameObject> objs = getHotspotObjects(hotspot);
            if (!getHouse().isBuildingMode()) {
                objs.forEach(GameObject::remove); // remove the building mode indicators
            }
            switch (trap) {
                case SPIKE_TRAP:
                    objs.forEach(o -> {
                        o.tile.addPlayerTrigger(p -> {
                            if (p.getCurrentHouse() == null || p.getCurrentHouse().getChallengeMode() == ChallengeMode.OFF)
                                return;
                            World.sendGraphics(615, 0, 0, o.x, o.y, o.z);
                            if (Random.get(255) < 100 + p.getStats().get(StatType.Agility).currentLevel) {
                                p.getStats().addXp(StatType.Agility, 1, true);
                                p.sendMessage("You avoid a spike trap!");
                            } else {
                                p.addEvent(event -> {
                                    p.getMovement().reset();
                                    p.lock();
                                    Direction dir = o.direction == 1 || o.direction == 3 ? (Random.get() < 0.5 ? Direction.WEST : Direction.EAST) : (Random.get() < 0.5 ? Direction.NORTH : Direction.SOUTH);
                                    p.animate(3627);
                                    p.getMovement().force(0, 0, -dir.deltaX, -dir.deltaY, 25,  5, dir);
                                    p.hit(new Hit().randDamage(1, 3));
                                    event.delay(1);
                                    p.unlock();
                                });
                            }
                        });
                    });
                    break;
                case MAN_TRAP:
                    objs.forEach(o -> {
                        o.tile.addPlayerTrigger(p -> {
                            if (p.getCurrentHouse() == null || p.getCurrentHouse().getChallengeMode() == ChallengeMode.OFF)
                                return;
                            World.sendGraphics(616, 0, 0, o.x, o.y, o.z);
                            if (Random.get(255) < 100 + (p.getStats().get(StatType.Agility).currentLevel * 1.2)) {
                                p.getStats().addXp(StatType.Agility, 1, true);
                                p.sendMessage("You avoid a man trap!");
                            } else {
                                p.addEvent(event -> {
                                    p.getMovement().reset();
                                    p.lock();
                                    p.animate(3630);
                                    p.hit(new Hit().randDamage(9, 12));
                                    p.sendMessage("You triggered a trap!");
                                    event.delay(1);
                                    p.unlock();
                                });
                            }
                        });
                    });
                    break;
                case TANGLE_VINE:
                    objs.forEach(o -> {
                        o.tile.addPlayerTrigger(p -> {
                            if (p.getCurrentHouse() == null || p.getCurrentHouse().getChallengeMode() == ChallengeMode.OFF)
                                return;
                            if (Random.get(255) < 70 + p.getStats().get(StatType.Agility).currentLevel) {
                                p.getStats().addXp(StatType.Agility, 1, true);
                            } else {
                                p.addEvent(event -> {
                                    p.getMovement().reset();
                                    p.lock();
                                    event.delay(1);
                                    int trapTicks = 4 + (4 - (p.getStats().get(StatType.Agility).currentLevel / 30));
                                    for (int i = 0; i < trapTicks; i++) {
                                        p.animate(i == trapTicks - 1 ? 3635 : 3636);
                                        if (i == 0)
                                            p.graphics(617);
                                        else if (i < trapTicks - 1)
                                            p.graphics(619);
                                        else
                                            p.graphics(618);
                                        event.delay(1);
                                    }
                                    p.unlock();
                                });
                            }
                        });
                    });
                    break;
                case MARBLE_TRAP:
                    objs.forEach(o -> {
                        o.tile.addPlayerTrigger(p -> {
                            if (p.getCurrentHouse() == null || p.getCurrentHouse().getChallengeMode() == ChallengeMode.OFF)
                                return;
                            World.sendGraphics(620, 0, 0, o.x, o.y, o.z);
                            if (Random.get(255) < 60 + p.getStats().get(StatType.Agility).currentLevel) {
                                p.getStats().addXp(StatType.Agility, 1, true);
                                p.sendMessage("You avoid a marble trap!");
                            } else {
                                p.addEvent(event -> {
                                    p.getMovement().reset();
                                    p.lock();
                                    p.animate(3639);
                                    event.delay(1);
                                    p.graphics(621);
                                    event.delay(1);
                                    p.hit(new Hit().randDamage(2, 4));
                                    p.getStats().get(StatType.Agility).drain(5);
                                    event.delay(1);
                                    p.unlock();
                                });
                            }
                        });
                    });
                    break;
                case TELEPORT_TRAP:
                    objs.forEach(o -> {
                        o.tile.addPlayerTrigger(p -> {
                            if (p.getCurrentHouse() == null || p.getCurrentHouse().getChallengeMode() == ChallengeMode.OFF)
                                return;
                            if (Random.get(255) < 135 + p.getStats().get(StatType.Agility).currentLevel) {
                                p.getStats().addXp(StatType.Agility, 1, true);
                            } else {
                                p.addEvent(event -> {
                                    p.getMovement().reset();
                                    p.lock();
                                    event.delay(1);
                                    o.setId(6521);
                                    p.animate(1950);
                                    p.graphics(623);
                                    event.delay(5);
                                    getHouse().teleportInside(p, getHouse().getEntryPosition());
                                    p.sendMessage("You've been teleported back to the house entrance!");
                                    event.delay(1);
                                    p.animate(-1);
                                    p.unlock();
                                    o.remove();
                                });
                            }
                        });
                    });
                    break;
            }
        }
    }

    @Override
    protected void onBuildableChanged(Player player, Hotspot hotspot, Buildable newBuildable) {
        super.onBuildableChanged(player, hotspot, newBuildable);
        if (hotspot == Hotspot.DUNGEON_TRAP_1 || hotspot == Hotspot.DUNGEON_TRAP_2) {
            getHotspotObjects(Hotspot.DUNGEON_TRAP_1).forEach(o -> o.tile.clearTriggers());
            getHotspotObjects(Hotspot.DUNGEON_TRAP_2).forEach(o -> o.tile.clearTriggers());
            registerFloorTraps();
        }
    }
}
