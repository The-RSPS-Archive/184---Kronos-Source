package io.ruin.model.skills.construction.room.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;

import java.util.List;
import java.util.stream.Collectors;

public class OublietteRoom extends DungeonGuardedRoom {

    private NPC cageMonster;
    private Bounds cageBounds;

    @Override
    protected void onBuild() {
        super.onBuild();
        registerFloorTrap();
        cageBounds = new Bounds(getBaseAbsolutePosition().getX() + 2, getBaseAbsolutePosition().getY() + 2, getBaseAbsolutePosition().getX() + 5, getBaseAbsolutePosition().getY() + 5, getChunkZ());
    }

    private void registerFloorTrap() {
        Buildable trap = getBuilt(Hotspot.OUBLIETTE_FLOOR_SPACE);
        if (trap == null) {
            return;
        }
        switch (trap) {
            case SPIKES:
                for (int x = getBaseAbsolutePosition().getX() + 2; x <= getBaseAbsolutePosition().getX() + 5; x++) {
                    for (int y = getBaseAbsolutePosition().getY() + 2; y <= getBaseAbsolutePosition().getY() + 5; y++) {
                        Tile.get(x,y,getChunkZ(), true).addPlayerTrigger(p -> p.hit(new Hit().randDamage(2)));
                    }
                }
                break;
            case TENTACLE_POOL:
                for (int x = getBaseAbsolutePosition().getX() + 2; x <= getBaseAbsolutePosition().getX() + 5; x++) {
                    for (int y = getBaseAbsolutePosition().getY() + 2; y <= getBaseAbsolutePosition().getY() + 5; y++) {
                        Tile.get(x,y,getChunkZ(), true).addPlayerTrigger(p -> {
                            if (p.get("TENTACLE_POOL") == null) {
                                p.set("TENTACLE_POOL", Boolean.TRUE);
                                p.getAppearance().setCustomRenders(Renders.SWIM);
                                p.addEvent(event -> {
                                    while (p.getPosition().inBounds(cageBounds))
                                        event.delay(1);
                                    p.getAppearance().removeCustomRenders();
                                    p.remove("TENTACLE_POOL");
                                });
                            }
                        });
                    }
                }
                break;
            case FLAME_PIT:
                for (int x = getBaseAbsolutePosition().getX() + 2; x <= getBaseAbsolutePosition().getX() + 5; x++) {
                    for (int y = getBaseAbsolutePosition().getY() + 2; y <= getBaseAbsolutePosition().getY() + 5; y++) {
                        Tile.get(x,y,getChunkZ(), true).addPlayerTrigger(p -> {
                            if (p.get("FLAME_PIT_ACTIVE") == null) {
                                p.set("FLAME_PIT_ACTIVE", Boolean.TRUE);
                                p.addEvent(event -> {
                                    while (Tile.getObject(13337, p.getAbsX(), p.getAbsY(), p.getHeight(), 10, -1) != null) {
                                        p.hit(new Hit().randDamage(1, 3));
                                        event.delay(1);
                                    }
                                    p.remove("FLAME_PIT_ACTIVE");
                                });
                            }
                        });
                    }
                }
                break;
        }
    }

    @Override
    protected void onBuildableChanged(Player player, Hotspot hotspot, Buildable newBuildable) {
        super.onBuildableChanged(player, hotspot, newBuildable);
        if (hotspot == Hotspot.OUBLIETTE_FLOOR_SPACE) {
            for (int x = getBaseAbsolutePosition().getX() + 2; x <= getBaseAbsolutePosition().getX() + 5; x++) {
                for (int y = getBaseAbsolutePosition().getY() + 2; y <= getBaseAbsolutePosition().getY() + 5; y++) {
                    Tile.get(x,y,getChunkZ(), true).clearTriggers();
                }
            }
            registerFloorTrap();
        }
    }

    @Override
    public void enableChallengeMode() {
        super.enableChallengeMode();
        if (getBuilt(Hotspot.OUBLIETTE_FLOOR_SPACE) == Buildable.TENTACLE_POOL) {
            cageMonster = house.addNPC(new NPC(129).spawn(getBaseAbsolutePosition().copy().translate(4,4,0)));
            cageMonster.addEvent(event -> {
                while (true) {
                    List<Player> targets = cageMonster.localPlayers().stream().filter(p -> p.getPosition().inBounds(cageBounds)).collect(Collectors.toList());
                    if (targets.size() > 0) {
                        Player p = Random.get(targets);
                        cageMonster.faceTemp(p);
                        cageMonster.animate(3618);
                        p.hit(new Hit().randDamage(3, 10));
                    }
                    event.delay(4);
                }
            });
        } else if (getBuilt(Hotspot.OUBLIETTE_FLOOR_SPACE) == Buildable.ROCNAR) {
            getHotspotObjects(Hotspot.OUBLIETTE_FLOOR_SPACE).forEach(GameObject::remove);
            cageMonster = house.addNPC(new NPC(143).spawn(getBaseAbsolutePosition().copy().translate(3,3,0)));
        }
    }

    @Override
    public void disableChallengeMode() {
        super.disableChallengeMode();
        if (cageMonster != null) {
            cageMonster.remove();
            cageMonster = null;
            renderHotspot(getHotspotIndex(Hotspot.OUBLIETTE_FLOOR_SPACE));
        }
    }
}
