package io.ruin.model.skills.construction.actions;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;
import io.ruin.model.skills.construction.RoomDefinition;
import io.ruin.model.skills.construction.room.Room;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Hall {

    static {
        for (Buildable b : Buildable.STAIRS) {
            registerStairActions(b);
            ObjectAction.register(b.getBuiltObjects()[0], "remove-room", (player, obj) -> {
                Room room = player.getCurrentRoom();
                if (room == null)
                    return;
                List<Option> options = new LinkedList<>();
                if (room.getRoomBelow() != null && (obj.getDef().hasOption("climb-down") || obj.getDef().hasOption("enter")))
                    options.add(new Option("Downstairs room: " + room.getRoomBelow().getDefinition().getName(), () -> removeRoom(player, room.getRoomBelow())));
                if (room.getRoomAbove() != null && (obj.getDef().hasOption("climb-up")))
                    options.add(new Option("Upstairs room: " + room.getRoomAbove().getDefinition().getName(), () -> removeRoom(player, room.getRoomAbove())));
                if (options.size() == 0) {
                    player.sendMessage("There's no room to remove.");
                } else if (options.size() == 1) {
                    options.get(0).select(player);
                } else {
                    player.dialogue(new OptionsDialogue(options));
                }
            });
        }
    }

    private static void registerStairActions(Buildable buildable) { // couldn't think of any clean way to do this, but it works perfectly and there will probably (hopefully) never be a reason to update it

        ObjectAction.register(buildable.getBuiltObjects()[0], "climb", (player, obj) -> { // only spirals have this
            Room room = player.getCurrentRoom();
            player.dialogue(new OptionsDialogue("Choose an option",
                    new Option("Climb up", () -> upAction(player, room, obj)),
                    new Option("Climb down", () -> downAction(player, room, obj)),
                    new Option("Cancel")));
        });
        ObjectAction.register(buildable.getBuiltObjects()[0], "climb-up", (player, obj) -> {
                Room room = player.getCurrentRoom();
                if (room != null)
                    upAction(player, room, obj);

        });
        ObjectAction.register(buildable.getBuiltObjects()[0], "climb-down", (player, obj) -> {
            Room room = player.getCurrentRoom();
            if (room != null)
                downAction(player, room, obj);
        });
    }

    private static void upAction(Player player, Room room, GameObject obj) {
        if (room.getChunkZ() == 2) {
            player.dialogue(new MessageDialogue("Those stairs don't lead anywhere."));
            return;
        }
        Room destination = room.getRoomAbove();
        if (destination == null) {
            if (player != room.getHouse().getOwner()) {
                player.dialogue(new MessageDialogue("These stairs don't seem to lead anywhere."));
                return;
            }
            player.dialogue(new MessageDialogue("These stairs don't lead anywhere. Would you like to add a new room?"),
                    new OptionsDialogue("Add new room above?",
                            new Option("Yes.", () -> {
                                List<RoomDefinition> options = new ArrayList<>();
                                if (room.getChunkZ() == 0) {
                                    options.add(RoomDefinition.GARDEN);
                                }
                                options.add(RoomDefinition.SKILL_HALL_DOWN);
                                options.add(RoomDefinition.QUEST_HALL_DOWN);
                                player.dialogue(new OptionsDialogue("Choose a room",
                                        options.stream().map(def -> new Option(StringUtils.capitalizeFirst(def.getName()), () -> {
                                            Room other = def.create();
                                            other.setRotation(room.getRotation());
                                            if (def != RoomDefinition.GARDEN) {
                                                other.setBuilt(0, def.getHotspots()[0].getBuildables()[room.indexOfBuilt(0)]); // setting stairs to be the same as the source room but holy moly what even is this line of code WutFace
                                            } else {
                                                other.setBuilt(other.getHotspotIndex(Hotspot.CENTERPIECE), Buildable.DUNGEON_ENTRANCE);
                                            }
                                            room.getHouse().setRoom(room.getChunkX(), room.getChunkY(), room.getChunkZ() + 1, other);
                                            room.getHouse().buildAndEnter(player, player.getPosition().localPosition(), true);
                                        })).collect(Collectors.toList())));
                            }),
                            new Option("No.")));
        } else {
            GameObject above = Tile.getObject(-1, obj.x, obj.y, obj.z + 1, 10, obj.direction);
            if (room.getRoomAbove() == null || room.getRoomAbove().getRotation() != room.getRotation()
                    || (room.getRoomAbove().getDefinition() == RoomDefinition.GARDEN && !room.getRoomAbove().hasBuildable(Buildable.DUNGEON_ENTRANCE))
                    || (room.getRoomAbove().getDefinition() != RoomDefinition.GARDEN && (above == null || above.isRemoved() || (above.id != obj.id + 1 && above.id != obj.id)))) {
                player.dialogue(new MessageDialogue("The room above does not have stairs connected to this room."));
                return;
            }
            Direction dir = Direction.getFromObjectDirection(obj.direction);
            if (obj.id == 13505 || obj.id == 13503)
                player.getMovement().teleport(player.getAbsX(), player.getAbsY(), player.getHeight() + 1);
            else
                player.getMovement().teleport(player.getAbsX() - (dir.deltaX * 3), player.getAbsY() - (dir.deltaY * 3), player.getHeight() + 1);
        }
    }

    private static void downAction(Player player, Room room, GameObject obj) {
        if (room.getChunkZ() == 0) {
            player.dialogue(new MessageDialogue("Those stairs don't lead anywhere."));
            return;
        }
        Room destination = room.getRoomBelow();
        if (destination == null) {
            if (player != room.getHouse().getOwner()) {
                player.dialogue(new MessageDialogue("These stairs don't seem to lead anywhere."));
                return;
            }
            player.dialogue(new MessageDialogue("These stairs don't lead anywhere. Would you like to add a new room?"),
                    new OptionsDialogue("Add new room below?",
                            new Option("Yes.", () -> {
                                List<RoomDefinition> options = new ArrayList<>();
                                if (room.getChunkZ() == 1) {
                                    options.add(RoomDefinition.DUNGEON_STAIRS);
                                }
                                options.add(RoomDefinition.SKILL_HALL_UP);
                                options.add(RoomDefinition.QUEST_HALL_UP);
                                player.dialogue(new OptionsDialogue("Choose a room",
                                        options.stream().map(def -> new Option(StringUtils.capitalizeFirst(def.getName()), () -> {
                                            Room other = def.create();
                                            other.setRotation(room.getRotation());
                                            other.setBuilt(0, def.getHotspots()[0].getBuildables()[room.indexOfBuilt(0)]); // setting stairs to be the same as the source room but holy moly what even is this line of code WutFace
                                            room.getHouse().setRoom(room.getChunkX(), room.getChunkY(), room.getChunkZ() - 1, other);
                                            room.getHouse().buildAndEnter(player, player.getPosition().localPosition(), true);
                                        })).collect(Collectors.toList())));
                            }),
                            new Option("No.")));
        } else {
            GameObject below = Tile.getObject(-1, obj.x, obj.y, obj.z - 1, 10, obj.direction);
            if (room.getRoomBelow() == null || room.getRoomBelow().getRotation() != room.getRotation()
                    || (room.getRoomBelow().getDefinition() == RoomDefinition.GARDEN && !room.getRoomBelow().hasBuildable(Buildable.DUNGEON_ENTRANCE))
                    || (room.getRoomBelow().getDefinition() != RoomDefinition.GARDEN && (below == null || below.isRemoved() || (below.id != obj.id - 1 && below.id != obj.id)))) {
                player.dialogue(new MessageDialogue("The room below does not have stairs connected to this room."));
                return;
            }
            Direction dir = Direction.getFromObjectDirection(obj.direction);
            if (obj.id == 13505 || obj.id == 13503)
                player.getMovement().teleport(player.getAbsX(), player.getAbsY(), player.getHeight() - 1);
            else
                player.getMovement().teleport(player.getAbsX() + (dir.deltaX * 3), player.getAbsY() + (dir.deltaY * 3), player.getHeight() - 1);
        }
    }

    private static void removeRoom(Player player, Room room) {
        if (player.getCurrentHouse() == null) {
        } else if (!player.isInOwnHouse()) {
            player.sendMessage("Only the house owner can do that.");
        } else if (!player.getCurrentHouse().isBuildingMode()) {
            player.sendMessage("You can only do that in building mode.");
        } else {
            player.getCurrentHouse().confirmRemoveRoom(player, room);
        }
    }

}
