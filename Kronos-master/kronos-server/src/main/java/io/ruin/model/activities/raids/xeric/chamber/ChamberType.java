package io.ruin.model.activities.raids.xeric.chamber;

/**
 * @author Edu
 */
public enum ChamberType {
    BEGIN, // starting room type
    PUZZLE, // puzzle room types, like the crab room
    COMBAT, // combat room types, like bosses
    SCAVENGER, // rooms with scavenger spawns
    RESOURCE, // rooms with resource spawns (fishing spots, farming patches etc)
    FINISH, // last room in the floor, with a passage to the next floor

}
