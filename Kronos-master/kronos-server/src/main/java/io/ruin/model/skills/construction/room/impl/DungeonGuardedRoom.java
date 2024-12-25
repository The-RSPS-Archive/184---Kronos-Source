package io.ruin.model.skills.construction.room.impl;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;
import io.ruin.model.skills.construction.room.Room;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DungeonGuardedRoom extends Room { // for dungeon rooms

    public static final Map<Buildable, Integer> GUARD_NPC_IDS = new HashMap<>();

    static {
        GUARD_NPC_IDS.put(Buildable.SKELETON_GUARD, 130);
        GUARD_NPC_IDS.put(Buildable.GUARD_DOG, 131);
        GUARD_NPC_IDS.put(Buildable.HOBGOBLIN, 132);
        GUARD_NPC_IDS.put(Buildable.TROLL_GUARD, 133);
        GUARD_NPC_IDS.put(Buildable.HUGE_SPIDER, 134);
        GUARD_NPC_IDS.put(Buildable.HELLHOUND, 135);
        GUARD_NPC_IDS.put(Buildable.BABY_RED_DRAGON, 137);

        GUARD_NPC_IDS.put(Buildable.DEMON, 142);
        GUARD_NPC_IDS.put(Buildable.KALPHITE_SOLDIER, 138);
        GUARD_NPC_IDS.put(Buildable.TOK_XIL, 141);
        GUARD_NPC_IDS.put(Buildable.DAGANNOTH, 140);
        GUARD_NPC_IDS.put(Buildable.STEEL_DRAGON, 139);
        GUARD_NPC_IDS.put(Buildable.RUNE_DRAGON, 8027);
    }

    private List<NPC> guards = new LinkedList<>();

    public List<NPC> getGuards() {
        return guards;
    }

    @Override
    public void enableChallengeMode() {
        super.enableChallengeMode();
        for (Hotspot hotspot : getDefinition().getHotspots()) {
            if (isGuardHotspot(hotspot) && getBuilt(hotspot) != null) {
                getHotspotObjects(hotspot).forEach(obj -> {
                   obj.remove();
                   NPC guard = house.addNPC(new NPC(GUARD_NPC_IDS.get(getBuilt(hotspot))).spawn(obj.x, obj.y, obj.z, Direction.getFromObjectDirection(obj.direction), 0));
                   guard.walkBounds = new Bounds(getBaseAbsolutePosition().getX(), getBaseAbsolutePosition().getY(), getBaseAbsolutePosition().getX() + 7, getBaseAbsolutePosition().getY() + 7, getBaseAbsolutePosition().getZ());
                   guards.add(guard);
                });
            }
        }
    }

    @Override
    public void disableChallengeMode() {
        super.disableChallengeMode();
        guards.forEach(NPC::remove);
        guards.clear();
        for (Hotspot hotspot : getDefinition().getHotspots()) {
            if (isGuardHotspot(hotspot)) {
                renderHotspot(getHotspotIndex(hotspot));
            }
        }
    }

    private boolean isGuardHotspot(Hotspot hotspot) { // we only do guards and not the boss monsters here
        return hotspot == Hotspot.DUNGEON_GUARD || hotspot == Hotspot.DUNGEON_STAIRS_GUARD_1 || hotspot == Hotspot.DUNGEON_STAIRS_GUARD_2 || hotspot == Hotspot.OUBLIETTE_GUARD;
    }
}
