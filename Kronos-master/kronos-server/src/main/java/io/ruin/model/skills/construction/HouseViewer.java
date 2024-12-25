package io.ruin.model.skills.construction;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.skills.construction.room.Room;

import static io.ruin.cache.ItemID.COINS_995;

public class HouseViewer { // mostly just converted jordan's code to work with my house system... could use a lot of improvements...

    public static int open(Player player) {
        if(!player.isInOwnHouse()) {
            player.sendMessage("You need to be in your house to do this.");
            return -1;
        }
        if(!player.getCurrentHouse().isBuildingMode()) {
            player.sendMessage("The house viewer is only for building mode.");
            return -1;
        }
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, Interface.CONSTRUCTION_HOUSE_VIEWER);
        player.houseViewerRooms = new Room[33];
        int roomOffset = 0;
        int addedRoomIndex = -1;
        for(int pointZ = 0; pointZ < 3; pointZ++) {
            for(int pointX = 0; pointX < 8; pointX++) {
                for(int pointY = 0; pointY < 8; pointY++) {
                    Room room = player.house.getRoom(pointX,pointY,pointZ);
                    if(room == null)
                        continue;
                    if(room == player.houseViewerRoom)
                        addedRoomIndex = roomOffset;
                    player.houseViewerRooms[roomOffset] = room;
                    sendRoom(player, room, ++roomOffset);
                }
            }
        }
        player.getPacketSender().sendClientScript(1382, "icci", roomOffset,
                1 << 14 | 1,
                2 << 28 | 6 << 14,
                player.houseViewerRoom == null ? 1 : player.houseViewerRoom.pointZ);
        player.getPacketSender().sendAccessMask(Interface.CONSTRUCTION_HOUSE_VIEWER, 5, 0, 242, 66);
        if(player.houseViewerRoom == null) {
            Config.HOUSE_VIEWER_SELECTED.set(player, 0);
            Config.HOUSE_VIEWER_HIGHLIGHTED.set(player, 0);
            Config.HOUSE_VIEWER_ALLOW_ROTATION.set(player, 0);
            Config.HOUSE_VIEWER_ROOM_ID.set(player, 0);
        }
        player.closeInterface(InterfaceType.INVENTORY_OVERLAY);
        return addedRoomIndex;
    }

    private static final int[] shifts1 = {15, 18, 21, 24, 26, 28, 30, 0};
    private static final int[] shifts2 = {0, 2, 4, 6, 9, 12, 15, 18};
    private static final int[] bits1 = {3, 3, 3, 2, 2, 2, 2, 0};

    private static void sendRoom(Player player, Room room, int roomIndex) {
        int objSetId = room.getDefinition().getEnumId();
        int unused = 11;
        int hash1 = room.getChunkX() | (room.getChunkY() << 3) | room.getChunkZ() << 6 | room.getRotation() << 8 | room.getDefinition().getId() << 10;
        int hash2 = 0;
        for (int i = 0; i < room.getDefinition().getHotspots().length; i++) {
            if (room.getBuilt(i) == null) {
                continue;
            }
            int index = 1 + room.indexOfBuilt(i);
            hash1 |= (index & (int) (Math.pow(2, bits1[i])) - 1) << shifts1[i];
            hash2 |= ((index >> (bits1[i])) & (int) (Math.pow(2, 5 - bits1[i]) - 1)) << shifts2[i];
        }
        player.getPacketSender().sendClientScript(1376, "igiii", roomIndex, objSetId, unused, hash1, hash2);
    }

    private static void toPortal(Player player) {
        player.closeInterface(InterfaceType.MAIN);
        player.getMovement().teleport(player.house.getEntryPosition());
    }

    private static int gridPos(Player player) {
        return gridPos(player.houseViewerRoom.pointX, player.houseViewerRoom.pointY, player.houseViewerRoom.pointZ);
    }

    private static int gridPos(int pointX, int pointY, int pointZ) {
        return (((pointY * 9) + 1) + pointX) + (pointZ * 81);
    }

    private static void addMoveRoom(Player player, int slot, boolean move) {
        if(slot < 0 || slot > 222)
            return;
        int pointZ = slot / 81;
        slot -= (pointZ * 81);
        int pointY = slot / 9;
        int pointX = slot - (pointY * 9);
        if(move)
            moveRoom(player, pointX, pointY, pointZ);
        else {
            player.houseBuildPointX = pointX;
            player.houseBuildPointY = pointY;
            player.houseBuildPointZ = pointZ;
            RoomDefinition.openRoomSelection(player, def -> addRoom(player, def));
        }
    }

    public static void addRoom(Player player, RoomDefinition type) {
        player.houseViewerRoom = type.create();
        player.houseViewerRoom.pointX = player.houseBuildPointX;
        player.houseViewerRoom.pointY = player.houseBuildPointY;
        player.houseViewerRoom.pointZ = player.houseBuildPointZ;
        player.houseViewerRoom.viewerCost = type.getCost();
        open(player);
        Config.HOUSE_VIEWER_SELECTED.set(player, 0);
        Config.HOUSE_VIEWER_HIGHLIGHTED.set(player, gridPos(player));
        Config.HOUSE_VIEWER_ROOM_ROTATION.set(player, 0);
        Config.HOUSE_VIEWER_ALLOW_ROTATION.set(player, 1);
        Config.HOUSE_VIEWER_ROOM_ID.set(player, type.getId());
    }

    private static void viewRoom(Player player, int index) {
        if(index >= player.houseViewerRooms.length)
            return;
        Room room = player.houseViewerRooms[index];
        if(room == null)
            return;
        room.viewerRotation = room.getRotation();
        room.viewerMovedPoints = null;
        player.houseViewerRoom = room;
        Config.HOUSE_VIEWER_SELECTED.set(player, index + 1);
        Config.HOUSE_VIEWER_HIGHLIGHTED.set(player, 0);
        Config.HOUSE_VIEWER_ROOM_ROTATION.set(player, room.getRotation());
        Config.HOUSE_VIEWER_ALLOW_ROTATION.set(player, 0);
        Config.HOUSE_VIEWER_ROOM_ID.set(player, room.getDefinition().getId());
    }

    private static void moveRoom(Player player, int pointX, int pointY, int pointZ) {
        if(player.houseViewerRoom == null || player.house.getRoom(pointX, pointY, pointZ) != null)
            return;
        player.houseViewerRoom.viewerMovedPoints = new int[]{pointX, pointY, pointZ};
        Config.HOUSE_VIEWER_HIGHLIGHTED.set(player, gridPos(pointX, pointY, pointZ));
        Config.HOUSE_VIEWER_ALLOW_ROTATION.set(player, 1);
    }

    private static void rotate(Player player) {
        if(player.houseViewerRoom == null)
            return;
        Config.HOUSE_VIEWER_HIGHLIGHTED.set(player, gridPos(player));
        Config.HOUSE_VIEWER_ALLOW_ROTATION.set(player, 1);
    }

    private static void rotate(Player player, boolean clockwise) {
        if(player.houseViewerRoom == null)
            return;
        if(clockwise)
            player.houseViewerRoom.viewerRotation++;
        else
            player.houseViewerRoom.viewerRotation--;
        player.houseViewerRoom.viewerRotation &= 0x3;
        Config.HOUSE_VIEWER_ROOM_ROTATION.set(player, player.houseViewerRoom.viewerRotation);
    }

    private static void delete(Player player) {
        Room room = player.houseViewerRoom;
        if(room == null)
            return;
        player.closeInterface(InterfaceType.MAIN);
        player.dialogue(
                new OptionsDialogue("Delete the " + room.getDefinition().getName() + "?",
                        new Option("Yes", () -> {
                            if (!player.house.canRemoveRoom(player, room)) {
                                return;
                            }
                            player.house.setRoom(room.getChunkX(), room.getChunkY(), room.getChunkZ(), null);
                            player.house.buildAndEnter(player, player.getPosition().localPosition(), true, () -> open(player));
                        }),
                        new Option("No", () -> {
                            player.closeDialogue();
                            player.houseViewerRoom = room;
                            Config.HOUSE_VIEWER_ROOM_ID.set(player, room.getDefinition().getId());
                            open(player);
                        })
                )
        );
    }

    private static void cancel(Player player) {
        if(player.houseViewerRoom == null)
            return;
        Config.HOUSE_VIEWER_HIGHLIGHTED.set(player, 0);
        Config.HOUSE_VIEWER_ALLOW_ROTATION.set(player, 0);
        if(player.houseViewerRoom.viewerCost != 0) {
            Config.HOUSE_VIEWER_ROOM_ID.set(player, 0);
            player.houseViewerRoom = null;
            return;
        }
        if(player.houseViewerRoom.viewerRotation != player.houseViewerRoom.getRotation()) {
            player.houseViewerRoom.viewerRotation = player.houseViewerRoom.getRotation();
            Config.HOUSE_VIEWER_ROOM_ROTATION.set(player, player.houseViewerRoom.getRotation());
        }
        player.houseViewerRoom.viewerMovedPoints = null;
    }

    private static void done(Player player) {
        Room room = player.houseViewerRoom;
        if(room == null)
            return;
        if(room.viewerCost != 0) {
            Item coins = player.getInventory().findItem(COINS_995);
            if(coins == null || coins.getAmount() < room.viewerCost) {
                player.sendMessage("You don't have enough coins to build that room.");
                Config.HOUSE_VIEWER_HIGHLIGHTED.set(player, 0);
                Config.HOUSE_VIEWER_ALLOW_ROTATION.set(player, 0);
                Config.HOUSE_VIEWER_ROOM_ID.set(player, 0);
                player.houseViewerRoom = null;
                return;
            } else if (!player.house.canAddRoom(player, room.getDefinition(), room.pointX, room.pointY, room.pointZ)) {
                Config.HOUSE_VIEWER_HIGHLIGHTED.set(player, 0);
                Config.HOUSE_VIEWER_ALLOW_ROTATION.set(player, 0);
                Config.HOUSE_VIEWER_ROOM_ID.set(player, 0);
                player.houseViewerRoom = null;
                return;
            }
            coins.remove(room.viewerCost);
            player.house.setRoom(room.pointX, room.pointY, room.pointZ, room);
        }
        if(room.viewerMovedPoints != null) {
            if (!player.house.canMoveRoom(player, room)
                    || !player.house.canAddRoom(player, room.getDefinition(), room.pointX, room.pointY, room.pointZ)) {
                Config.HOUSE_VIEWER_HIGHLIGHTED.set(player, 0);
                Config.HOUSE_VIEWER_ALLOW_ROTATION.set(player, 0);
                Config.HOUSE_VIEWER_ROOM_ID.set(player, 0);
                player.houseViewerRoom = null;
                return;
            }
            int pointX = room.viewerMovedPoints[0];
            int pointY = room.viewerMovedPoints[1];
            int pointZ = room.viewerMovedPoints[2];
            room.viewerMovedPoints = null;
            player.house.setRoom(room.pointX, room.pointY, room.pointZ, null);
            room.pointX = pointX;
            room.pointY = pointY;
            room.pointZ = pointZ;
            player.house.setRoom(room.pointX, room.pointY, room.pointZ, room);
        }
        if(room.getRotation() != room.viewerRotation) {
            room.setRotation(room.viewerRotation);
        }
        Config.HOUSE_VIEWER_HIGHLIGHTED.set(player, 0);
        Config.HOUSE_VIEWER_ALLOW_ROTATION.set(player, 0);
        player.closeInterface(InterfaceType.MAIN);
        player.house.buildAndEnter(player, player.getPosition().localPosition(), player.house.isBuildingMode(), () -> {
            player.houseViewerRoom = room;
            int index = open(player);
            if(index != -1)
                Config.HOUSE_VIEWER_SELECTED.set(player, index + 1);
        });
    }

    static {
        InterfaceHandler.register(Interface.CONSTRUCTION_HOUSE_VIEWER, h -> {
            h.actions[5] = (DefaultAction) (p, option, slot, itemId) -> addMoveRoom(p, slot, option == 1);
            for(int i = 6; i <= 38; i++) {
                final int index = i - 6;
                h.actions[i] = (SimpleAction) p -> viewRoom(p, index);
            }
            h.actions[41] = (SimpleAction) HouseViewer::toPortal;
            h.actions[59] = (SimpleAction) HouseViewer::rotate;
            h.actions[60] = (SimpleAction) p -> rotate(p, true);
            h.actions[61] = (SimpleAction) p -> rotate(p, false);
            h.actions[62] = (SimpleAction) HouseViewer::delete;
            h.actions[63] = (SimpleAction) HouseViewer::cancel;
            h.actions[64] = (SimpleAction) HouseViewer::done;
            h.closedAction = (p, i) -> {
                p.houseViewerRooms = null;
                p.houseViewerRoom = null;
            };
        });
    }

}