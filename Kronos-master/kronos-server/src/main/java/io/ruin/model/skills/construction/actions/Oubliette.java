package io.ruin.model.skills.construction.actions;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.skills.construction.*;
import io.ruin.model.skills.construction.room.Room;

public class Oubliette {

    static { // cage options
        for (Buildable b : Hotspot.PRISON.getBuildables()) {
            for (int id : b.getBuiltObjects())
                ObjectAction.register(id, "build-in", Construction.forHouseOwnerOnly((player, house) -> {
                    if (!house.isBuildingMode()) {
                        player.dialogue(new MessageDialogue("You can only do that in building mode."));
                        return;
                    }
                    if (player.getCurrentRoom().getBuilt(Hotspot.OUBLIETTE_FLOOR_SPACE) != null) {
                        player.dialogue(new MessageDialogue("You already have something built inside the cage."));
                        return;
                    }
                    player.getCurrentRoom().openFurnitureCreation(player, player.getCurrentRoom().getHotspotIndex(Hotspot.OUBLIETTE_FLOOR_SPACE));
                }));
        }
    }

    static { // cage doors
        Dungeon.registerDoor(Buildable.OAK_CAGE, 1);
        Dungeon.registerDoor(Buildable.OAK_AND_STEEL_CAGE, 0.98);
        Dungeon.registerDoor(Buildable.STEEL_CAGE, 0.94);
        Dungeon.registerDoor(Buildable.SPIKED_CAGE, 0.90);
        Dungeon.registerDoor(Buildable.BONE_CAGE, 0.85);

    }

    static { // ladder
        for (Buildable b : Hotspot.LADDER.getBuildables()) {
            ObjectAction.register(b.getBuiltObjects()[0], "climb", Construction.forCurrentRoom((player, room) -> {
                Room above = room.getRoomAbove();
                if (above != null && above.getDefinition() == RoomDefinition.THRONE_ROOM && above.getBuilt(Hotspot.TRAPDOOR) != null && above.getRotation() == room.getRotation()) { // all conditions met, go up
                    if (room.getHouse().getChallengeMode() != ChallengeMode.OFF && !player.isInOwnHouse()) {
                        player.dialogue(new MessageDialogue("You can't use this ladder while challenge mode is active."));
                    } else {
                        Ladder.climb(player, player.getAbsX(), player.getAbsY(), player.getHeight() + 1, true, true, false);
                    }
                } else if (above == null) {
                    if (!player.isInOwnHouse() || !room.getHouse().isBuildingMode()) {
                        player.dialogue(new MessageDialogue("This ladder doesn't seem to lead anywhere."));
                    } else {
                        player.dialogue(new MessageDialogue("This ladder doesn't lead anywhere. Would you like to add a Throne Room above?"),
                                new OptionsDialogue(
                                        new Option("Yes", () -> addThroneRoom(player, room)),
                                        new Option("No")
                                ));
                    }
                } else {
                    player.dialogue(new MessageDialogue("This ladder doesn't lead anywhere."));
                }
            }));

            ObjectAction.register(b.getBuiltObjects()[0], "remove-room", Construction.forHouseOwnerOnly((player, house) -> {
                if (player.getCurrentRoom() == null) {
                    player.dialogue(new MessageDialogue("The ladder doesn't lead anywhere."));
                } else if (!player.house.isBuildingMode()) {
                    player.dialogue(new MessageDialogue("You can only do that in building mode."));
                } else {
                    player.house.confirmRemoveRoom(player, player.getCurrentRoom().getRoomAbove());
                }
            }));
        }
    }

    private static void addThroneRoom(Player player, Room room) {
        if (!player.isInOwnHouse())
            return;
        if (!player.getCurrentHouse().isBuildingMode()) {
            player.dialogue(new MessageDialogue("You can only do that in building mode."));
            return;
        }
        if (!player.getCurrentHouse().canAddRoom(player, RoomDefinition.THRONE_ROOM, room.getChunkX(), room.getChunkY(), room.getChunkZ() + 1)) {
            return;
        }
        Room throneRoom = RoomDefinition.THRONE_ROOM.create();
        throneRoom.setRotation(room.getRotation());
        int index = throneRoom.getHotspotIndex(Hotspot.TRAPDOOR);
        throneRoom.setBuilt(index, RoomDefinition.THRONE_ROOM.getHotspots()[index].getBuildables()[room.indexOfBuilt(room.getHotspotIndex(Hotspot.LADDER))]); // adds a trapdoor in the throne room that matches the ladder we have in the oubliette
        player.getCurrentHouse().setRoom(room.getChunkX(), room.getChunkY(), room.getChunkZ() + 1, throneRoom);
        player.getCurrentHouse().buildAndEnter(player, player.getPosition().localPosition(), true);
    }

}
