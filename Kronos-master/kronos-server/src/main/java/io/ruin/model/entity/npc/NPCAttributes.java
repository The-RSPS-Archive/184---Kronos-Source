package io.ruin.model.entity.npc;

import io.ruin.model.entity.Entity;
import io.ruin.model.skills.fishing.FishingArea;

import java.util.function.Predicate;

public abstract class NPCAttributes extends Entity {

    public FishingArea fishingArea;

    public boolean minnowsFish;

    public int wildernessSpawnLevel;

    public Predicate<Entity> aggressionImmunity;

    public int ownerId = -1; //for pets

}
