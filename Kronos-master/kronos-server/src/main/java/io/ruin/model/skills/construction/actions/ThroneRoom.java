package io.ruin.model.skills.construction.actions;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.ObjectDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.Region;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.*;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.skills.construction.room.impl.PortalChamberRoom;

import java.util.function.Consumer;

public class ThroneRoom {

    enum Trapdoor {
        OAK(13675, 13678),
        TEAK(13676, 13679),
        MAHOGANY(13677, 13680),;
        int closed, open;

        Trapdoor(int closed, int open) {
            this.closed = closed;
            this.open = open;
        }
    }

    static {
        for (Trapdoor trapdoor : Trapdoor.values()) {
            ObjectAction.register(trapdoor.closed, "open", (player, obj) -> {
                player.animate(827);
                obj.setId(trapdoor.open);
            });
            ObjectAction.register(trapdoor.open, "close", (player, obj) -> {
                player.animate(827);
                obj.setId(trapdoor.closed);
            });
            ObjectAction.register(trapdoor.open, "go-down", Construction.forCurrentRoomElse((player, room) -> {
                if (room.getRoomBelow() == null) {
                    if (!player.isInOwnHouse()) {
                        player.dialogue(new MessageDialogue("It doesn't seem to lead anywhere."));
                    } else {
                        player.dialogue(new MessageDialogue("The trapdoor doesn't currently lead anywhere.<br>Would you like to add an Oubliette below?<br>It will cost " + NumberUtils.formatNumber(RoomDefinition.OUBLIETTE.getCost()) + " coins."),
                                new OptionsDialogue(new Option("Yes", () -> addOubliette(player, room)),
                                        new Option("No")));
                    }
                } else if (room.getRoomBelow().getDefinition() == RoomDefinition.OUBLIETTE && room.getRotation() == room.getRoomBelow().getRotation() && room.getRoomBelow().getBuilt(Hotspot.LADDER) != null) {
                    player.startEvent(e -> {
                        player.lock(LockType.FULL_DELAY_DAMAGE);
                        player.animate(827);
                        e.delay(1);
                        player.getMovement().teleport(player.getAbsX(), player.getAbsY(), player.getHeight() - 1);
                        player.unlock();
                    });
                } else {
                    player.dialogue(new MessageDialogue("It doesn't seem to lead anywhere."));
                }
            }, player -> {
                if (player.getPosition().getRegion().id == 15148) {
                    player.getMovement().teleport(3810, 2844, 0);
                }
            }));
            for (int id: new int[]{trapdoor.open, trapdoor.closed}) {
                ObjectAction.register(id, "remove-room", Construction.forHouseOwnerOnly((player, house) -> {
                    if (player.getCurrentRoom() == null) {
                        player.dialogue(new MessageDialogue("The trapdoor doesn't lead anywhere."));
                    } else if (!player.house.isBuildingMode()) {
                        player.dialogue(new MessageDialogue("You can only do that in building mode."));
                    } else {
                        player.house.confirmRemoveRoom(player, player.getCurrentRoom().getRoomBelow());
                    }
                }));
            }
        }
    }


    private static void addOubliette(Player player, Room room) {
        if (!player.isInOwnHouse())
            return;
        if (!player.getCurrentHouse().isBuildingMode()) {
            player.dialogue(new MessageDialogue("You can only do that in building mode."));
            return;
        }
        if (!player.getCurrentHouse().canAddRoom(player, RoomDefinition.OUBLIETTE, room.getChunkX(), room.getChunkY(), room.getChunkZ() - 1)) {
            return;
        }
        Room oubliette = RoomDefinition.OUBLIETTE.create();
        oubliette.setRotation(room.getRotation());
        int index = oubliette.getHotspotIndex(Hotspot.LADDER);
        oubliette.setBuilt(index, RoomDefinition.OUBLIETTE.getHotspots()[index].getBuildables()[room.indexOfBuilt(room.getHotspotIndex(Hotspot.TRAPDOOR))]); // adds a ladder in the oubliette
        player.getCurrentHouse().setRoom(room.getChunkX(), room.getChunkY(), room.getChunkZ() - 1, oubliette);
        player.getCurrentHouse().buildAndEnter(player, player.getPosition().localPosition(), true);
    }

    static {
        for (Buildable lever : Hotspot.LEVER.getBuildables()) {
            ObjectAction.register(lever.getBuiltObjects()[0], "pull", Construction.forCurrentRoom((player, room) -> {
                activateTrap(player, room, room.getBuilt(Hotspot.FLOOR_SPACE));
            }));
            ObjectAction.register(lever.getBuiltObjects()[0], "challenge-mode", Construction.forHouseOwnerOnly((player, house) -> {
                if (house.isBuildingMode()) {
                    player.sendMessage("You can't enable challenge mode while in building mode.");
                    return;
                }
                if (house.getChallengeMode() != ChallengeMode.OFF) {
                    player.animate(3611);
                    house.setChallengeMode(ChallengeMode.OFF);
                } else {
                    Consumer<ChallengeMode> turnItOn = challengeMode -> {
                        player.animate(3611);
                        house.setChallengeMode(challengeMode);
                    };
                    player.dialogue(new OptionsDialogue(
                            new Option("Challenge Mode", () -> turnItOn.accept(ChallengeMode.ON)),
                            new Option("PvP Challenge Mode", () -> turnItOn.accept(ChallengeMode.PVP))
                    ));
                }
            }));
        }
    }

    static {
        ObjectDef.get(13681).clipType = 1;
        ObjectDef.get(13681).tall = true;
        ObjectDef.get(13682).clipType = 1;
        ObjectDef.get(13682).tall = true;
        ObjectDef.get(13683).clipType = 1;
        ObjectDef.get(13683).tall = true;
    }

    private static void activateTrap(Player player, Room room, Buildable trap) {
        Position[] positions = new Position[]{room.getAbsolutePosition(4, 3), room.getAbsolutePosition(3, 3), room.getAbsolutePosition(3, 4), room.getAbsolutePosition(4, 4)};
        if (trap == null || trap == Buildable.FLOOR_DECORATION) {
            player.animate(3611);
            player.dialogue(new MessageDialogue("Nothing interesting happens."));
        } else if (trap == Buildable.STEEL_CAGE_TRAP) {
            if (Tile.getObject(13681, positions[0].getX(), positions[0].getY(), positions[0].getZ()) == null) { // inactive, activate
                player.animate(3611);
                for (int i = 0; i < positions.length; i++) {
                    GameObject.spawn(13681, positions[i].getX(), positions[i].getY(), positions[i].getZ(), 10, (i + room.getRotation()) & 3);
                }
                trapPlayers(room);
            } else { // deactivate?
                player.dialogue(new OptionsDialogue(
                        new Option("Release", () -> {
                            player.animate(3661);
                            for (Position position : positions) {
                                GameObject.forObj(13681, position.getX(), position.getY(), position.getZ(), GameObject::remove);
                            }
                            releasePlayers(room);
                        }),
                        new Option("Cancel")));
            }
        } else if (trap == Buildable.TRAP_TRAPDOOR) {
            if (Tile.getObject(6521, positions[0].getX(), positions[0].getY(), positions[0].getZ()) == null) { // not active
                if (room.getRoomBelow() == null || room.getRoomBelow().getDefinition() != RoomDefinition.OUBLIETTE) {
                    player.dialogue(new MessageDialogue("There is a pitfall trap installed, but no Oubliette below this room."));
                    return;
                }
                player.animate(3611);
                room.getHouse().getMap().addEvent(event -> {
                    for (Position pos : positions) {
                        GameObject obj = Tile.getObject(-1, pos.getX(), pos.getY(), pos.getZ(), 22, -1);
                        if (obj == null) continue;
                        obj.setId(6521);
                    }
                    forPlayersInTrapArea(room, p -> {
                        p.lock();
                        p.animate(1950);
                    });
                    event.delay(5);
                    forPlayersInTrapArea(room, p -> {
                        p.getMovement().teleport(p.getAbsX(), p.getAbsY(), p.getHeight() - 1);
                        p.animate(room.getRoomBelow().hasBuildable(Buildable.TENTACLE_POOL) ? 3641 : 3640);
                        p.unlock();
                    });
                    event.delay(3);
                    room.renderHotspot(room.getHotspotIndex(Hotspot.FLOOR_SPACE));
                });
            } else {
                player.dialogue(new MessageDialogue("The trap is currently active."));
            }
        } else if (trap == Buildable.LESSER_MAGIC_CAGE) {
            int x = (player.getAbsX() & ~7) + 3;
            int y = (player.getAbsY() & ~7) + 3;
            GameObject cage = Tile.getObject(13682, x,y,room.getChunkZ());
            if (cage == null) { // inactive. activate
                player.animate(3611);
                GameObject.spawn(13682, x, y, room.getChunkZ(), 10, 0);
                trapPlayers(room);
            } else {
                player.dialogue(new OptionsDialogue(
                        new Option("Release", () -> {
                            player.animate(3611);
                            cage.remove();
                            releasePlayers(room);
                        }),
                        new Option("Drop", () -> magicDrop(player, room, cage)),
                        new Option("Cancel")
                ));
            }
        } else if (trap == Buildable.GREATER_MAGIC_CAGE) {
            int x = (player.getAbsX() & ~7) + 3;
            int y = (player.getAbsY() & ~7) + 3;
            GameObject cage = Tile.getObject(13683, x,y,room.getChunkZ());
            if (cage == null) { // inactive. activate
                player.animate(3611);
                GameObject.spawn(13683, x, y, room.getChunkZ(), 10, 0);
                trapPlayers(room);
            } else {
                player.dialogue(new OptionsDialogue(
                        new Option("Release", () -> {
                            player.animate(3611);
                            cage.remove();
                            releasePlayers(room);
                        }),
                        new Option("Teleport", () -> {
                            PortalChamberRoom.PortalDestination destination = PortalChamberRoom.PortalDestination.values()[Random.get(0, PortalChamberRoom.PortalDestination.ANNAKARL.ordinal())];
                            player.animate(3611);
                            cage.remove();
                            forPlayersInTrapArea(room, p -> {
                                p.addEvent(event -> {
                                    p.lock();
                                    p.animate(1816);
                                    p.graphics(342);
                                    event.delay(3);
                                    p.getMovement().teleport(destination.bounds.randomX(), destination.bounds.randomY(), destination.bounds.z);
                                    p.animate(-1);
                                    p.unlock();
                                });
                            });
                        }),
                        new Option("Drop", () -> magicDrop(player, room, cage)),
                        new Option("Cancel")
                ));
            }
        }

    }

    private static void magicDrop(Player player, Room room, GameObject cage) {
        if (room.getRoomBelow() == null || room.getRoomBelow().getDefinition() != RoomDefinition.OUBLIETTE) {
            player.dialogue(new MessageDialogue("There is no Oubliette below this room to drop the players into."));
            return;
        }
        player.animate(3611);
        cage.remove();
        forPlayersInTrapArea(room, p -> {
            p.startEvent(event -> {
                p.lock();
                p.animate(4071);
                p.graphics(678);
                event.delay(2);
                p.getMovement().teleport(p.getAbsX(), p.getAbsY(), p.getHeight() - 1);
                p.animate(room.getRoomBelow().hasBuildable(Buildable.TENTACLE_POOL) ? 3641 : 3640);
                p.unlock();
            });
        });
    }

    private static void trapPlayers(Room room) {
        forPlayersInTrapArea(room, p -> {
            p.lock(LockType.MOVEMENT);
            p.sendMessage("You're trapped!");
        });
    }

    private static void releasePlayers(Room room) {
        forPlayersInTrapArea(room, p -> {
            p.unlock();
            p.sendMessage("You've been released!");
        });
    }


    private static void forPlayersInTrapArea(Room room, Consumer<Player> p) {
        for (Region r: room.getHouse().getMap().getRegions())
            if (r != null) for (Player player : r.players) {
                if (player.getCurrentRoom() != room)
                    continue;
                int x = player.getAbsX() & 7;
                int y = player.getAbsY() & 7;
                if (x >= 3 && x <= 4 && y >= 3 && y <= 4) {
                    p.accept(player);
                }
            }
    }
}
