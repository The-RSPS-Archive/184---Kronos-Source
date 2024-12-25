package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.woodcutting.Hatchet;
import io.ruin.model.stat.StatType;

public enum CanoeStation {

    LUMBRIDGE(12163, Config.LUMBRIDGE_CANOE,
            new int[]{32, 11},
            new Position(3240, 3241, 0),
            "in Lumbridge",
            new Position(2, 0, 0),
            new Position(2, 2, 0),
            new Position(-5, 0, 0),
            new Position(3238, 3240, 0),
            12, 50),
    CHAMPION_GUILD(12164, Config.CHAMPION_GUILD_CANOE,
            new int[]{13, 33},
            new Position(3199, 3343, 0),
            "at the Champion's Guild",
            new Position(4, 2, 0),
            new Position(2, 2, 0),
            new Position(0, -5, 0),
            new Position(3204, 3337, 0),
            12, 47),
    BARBARIAN_VILLAGE(12165, Config.BARBARIAN_VILLAGE_CANOE,
            new int[]{34, 15},
            new Position(3109, 3415, 0),
            "in Barbarian village",
            new Position(2, 0, 0),
            new Position(2, 2, 0),
            new Position(-5, 0, 0),
            new Position(3107, 3414, 0),
            12, 44),
    EDGEVILLE(12166, Config.EDGEVILLE_CANOE,
            new int[]{38, 39},
            new Position(3128, 3503, 0),
            "in Edgeville",
            new Position(2, 0, 0),
            new Position(2, 2, 0),
            new Position(-5, 0, 0),
            new Position(3127, 3503, 0),
            12, 36),
    WILDERNESS_CHINS(-1, Config.WILDERNESS_CHINS_CANOE,
            new int[]{35},
            new Position(3144, 3798, 0),
            "in Deep Wilderness",
            new Position(0, 0, 0),
            new Position(0, 0, 0),
            new Position(0, 0, 0),
            new Position(0, 0, 0),
            1, -1);

    public final int objectId, levelReq, childId;
    public final Config config;
    public final int[] travelButtonIds;
    public final String endMessage;
    public final Position endPosition, chopPosition, shapePosition, faceBoatPosition, sinkPosition;

    CanoeStation(int objectId, Config config, int[] travelButtonIds, Position endPosition, String endMessage, Position chopPosition, Position shapePosition,
                 Position faceBoatPosition, Position sinkPosition, int levelReq, int childId) {
        this.objectId = objectId;
        this.config = config;
        this.travelButtonIds = travelButtonIds;
        this.endPosition = endPosition;
        this.endMessage = endMessage;
        this.chopPosition = chopPosition;
        this.shapePosition = shapePosition;
        this.faceBoatPosition = faceBoatPosition;
        this.sinkPosition = sinkPosition;
        this.levelReq = levelReq;
        this.childId = childId;
    }

    private static final int
            TREE_FALLING_ANIMATION = 3304,
            PUSH_OFF_ANIMATION = 3301,
            BEGINNING_STATE = 0,
            NO_EXAMINE_STATE = 9,
            CHOPPED_DOWN_STATE = 10;

    private static final int[] BUILT_ON_STAND = {1, 2, 3, 4};
    private static final int[] PADDLE_TO_DESTINATION = {11, 12, 13, 14};

    private static void shapeBoat(Player player, int boatType) {
        Hatchet hatchet = Hatchet.find(player);
        if (hatchet == null) {
            player.sendMessage("You do not have an axe which you have the woodcutting level to use.");
            player.privateSound(2277);
            return;
        }
        CanoeStation canoeStation = player.canoeStation;
        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.closeInterface(InterfaceType.MAIN);
            player.face(player.getAbsX() + canoeStation.faceBoatPosition.getX(), player.getAbsY() + canoeStation.faceBoatPosition.getY());
            int loops = 10;
            while (loops-- > 0) {
                event.delay(1);
                player.animate(hatchet.canoeAnimationId);
            }
            player.resetAnimation();
            canoeStation.config.set(player, boatType);
            player.unlock();
        });
    }

    private static void travelToDestination(Player player, CanoeStation canoeStation) {
        if(player.canoeStation == null) {
            canoeStation.config.set(player, 0);
            return;
        }
        if(player.canoeStation == canoeStation) {
            player.closeInterface(InterfaceType.MAIN);
            player.dialogue(new MessageDialogue("You need to pick a destination you're not already at."));
            return;
        }
        player.startEvent(event -> { //todo fix this lmao
            player.lock(LockType.FULL_NULLIFY_DAMAGE);
            player.closeInterface(InterfaceType.MAIN);
            player.getPacketSender().fadeOut();
            event.delay(3);
            DynamicMap dynamicMap = new DynamicMap().buildSw(7238, 1);
            player.getMovement().teleport(dynamicMap.convertX(1817), dynamicMap.convertY(4515), 0);
            player.face(player.getAbsX(), player.getAbsY() - 1);
            player.getPacketSender().sendMapState(2);
            player.privateSound(2728, 16, 0);
            event.delay(1);
            player.animate(3302);
            Config.LOCK_CAMERA.set(player, 1);
            player.getPacketSender().moveCameraToLocation(dynamicMap.convertX(1814), dynamicMap.convertY(4518), 255, 100, 100);
            player.getPacketSender().turnCameraToLocation(dynamicMap.convertX(1817), dynamicMap.convertY(4518), 255, 100, 100);
            player.face(Direction.SOUTH);
            player.getPacketSender().fadeIn();
            event.delay(10);
            player.getPacketSender().fadeOut();
            event.delay(1);
            player.getPacketSender().fadeIn();
            player.resetAnimation();
            canoeStation.config.set(player, BEGINNING_STATE);
            player.canoeStation.config.set(player, BEGINNING_STATE);
            Config.LOCK_CAMERA.set(player, 0);
            player.getPacketSender().sendMapState(0);
            player.getMovement().teleport(canoeStation.endPosition);
            dynamicMap.destroy();
            int sinkId = canoeStation.config.get(player) - 11 + 12159;
            player.getPacketSender().resetCamera();
            player.sendFilteredMessage("You arrive at the Barbarian Village.<br>Your canoe sinks into the water after the hard journey.");
            World.startEvent(e -> {
                GameObject canoe = GameObject.spawn(sinkId, canoeStation.sinkPosition.getX(), canoeStation.sinkPosition.getY(), 0, 10, 1);
                event.delay(2);
                canoe.remove();
            });
            player.unlock();
        });
    }

    private static void station(Player player, GameObject obj, CanoeStation canoeStation) {
        if (canoeStation.config.get(player) == BEGINNING_STATE) {
            player.startEvent(event -> {
                Position chopPosition = new Position(obj.x + canoeStation.chopPosition.getX(), obj.y + canoeStation.chopPosition.getY(), 0);
                player.lock();
                if (!player.isAt(chopPosition)) {
                    player.stepAbs(chopPosition.getX(), chopPosition.getY(), StepType.FORCE_WALK);
                    event.waitForMovement(player);
                }
                player.face(obj.x + canoeStation.faceBoatPosition.getX(), obj.y + canoeStation.faceBoatPosition.getY());
                if (player.getStats().get(StatType.Woodcutting).currentLevel < canoeStation.levelReq) {
                    player.dialogue(new MessageDialogue("You must have at least level " + canoeStation.levelReq + " Woodcutting to start making canoes."));
                    player.unlock();
                    return;
                }
                Hatchet hatchet = Hatchet.find(player);
                if (hatchet == null) {
                    player.sendMessage("You do not have an axe which you have the woodcutting level to use.");
                    player.privateSound(2277);
                    player.unlock();
                    return;
                }
                player.animate(hatchet.animationId);
                event.delay(2);
                player.resetAnimation();
                obj.animate(TREE_FALLING_ANIMATION);
                canoeStation.config.set(player, NO_EXAMINE_STATE);
                event.delay(1);
                player.privateSound(2734);
                canoeStation.config.set(player, CHOPPED_DOWN_STATE);
                player.unlock();
            });
        } else if (canoeStation.config.get(player) == CHOPPED_DOWN_STATE) {
            player.startEvent(event -> {
                player.lock();
                player.canoeStation = canoeStation;
                Position shapePosition = new Position(obj.x + canoeStation.shapePosition.getX(), obj.y + canoeStation.shapePosition.getY(), 0);
                if (!player.isAt(shapePosition)) {
                    player.stepAbs(shapePosition.getX(), shapePosition.getY(), StepType.FORCE_WALK);
                    event.waitForMovement(player);
                }
                player.face(obj.x + canoeStation.faceBoatPosition.getX(), obj.y + canoeStation.faceBoatPosition.getY());
                event.delay(1);
                player.getPacketSender().sendClientScript(917, "Ii", 3612928, 0);
                player.getPacketSender().setHidden(Interface.CANOE_SELECTION, 6, false);
                player.getPacketSender().setHidden(Interface.CANOE_SELECTION, 32, true);
                player.getPacketSender().setHidden(Interface.CANOE_SELECTION, 3, false);
                player.getPacketSender().setHidden(Interface.CANOE_SELECTION, 35, true);
                player.getPacketSender().setHidden(Interface.CANOE_SELECTION, 13, false);
                player.getPacketSender().setHidden(Interface.CANOE_SELECTION, 29, true);
                player.openInterface(InterfaceType.MAIN, Interface.CANOE_SELECTION);
                player.getPacketSender().sendClientScript(917, "ii", -1, -1);
                player.unlock();
            });
        } else {
            for (int i : BUILT_ON_STAND) {
                if (canoeStation.config.get(player) == i) {
                    player.startEvent(event -> {
                        player.lock();
                        player.animate(PUSH_OFF_ANIMATION);
                        player.face(obj.x + canoeStation.faceBoatPosition.getX(), obj.y + canoeStation.faceBoatPosition.getY());
                        canoeStation.config.set(player, canoeStation.config.get(player) + 4);
                        obj.animate(TREE_FALLING_ANIMATION);
                        event.delay(3);
                        canoeStation.config.set(player, canoeStation.config.get(player) + 6);
                        player.unlock();
                    });
                    return;
                }
            }
            for (int i : PADDLE_TO_DESTINATION) {
                if (canoeStation.config.get(player) == i) {
                    player.startEvent(event -> {
                        if(canoeStation.childId != -1)
                            player.getPacketSender().setHidden(Interface.CANOE_LOCATION, canoeStation.childId, false);
                        player.getPacketSender().setHidden(Interface.CANOE_LOCATION, 11, false);
                        player.getPacketSender().setHidden(Interface.CANOE_LOCATION, 13, false);
                        player.getPacketSender().setHidden(Interface.CANOE_LOCATION, 15, false);
                        player.getPacketSender().setHidden(Interface.CANOE_LOCATION, 38, false);
                        player.getPacketSender().setHidden(Interface.CANOE_LOCATION, 31, false);
                        player.getPacketSender().setHidden(Interface.CANOE_LOCATION, 32, false);
                        player.openInterface(InterfaceType.MAIN, Interface.CANOE_LOCATION);
                    });
                    return;
                }
            }
        }
    }

    static {
        for (CanoeStation canoeStation : values()) {
            if (canoeStation != WILDERNESS_CHINS)
                ObjectAction.register(canoeStation.objectId, 1, (player, obj) -> station(player, obj, canoeStation));
        }

        InterfaceHandler.register(Interface.CANOE_LOCATION, h -> {
            for (CanoeStation canoeStation : values())
                for (int i : canoeStation.travelButtonIds)
                    h.actions[i] = (SimpleAction) p -> travelToDestination(p, canoeStation);
        });
        //TODO::CHANGE THIS 182
        /*InterfaceHandler.register(Interface.CANOE_SELECTION, h -> {
            *//**
             * Log
             *//*
            h.actions[8] = (SimpleAction) p -> shapeBoat(p, 1); //<-- 182 out of bounds
            h.actions[24] = (SimpleAction) p -> shapeBoat(p, 1);
            h.actions[36] = (SimpleAction) p -> shapeBoat(p, 1);

            *//**
             * Dugout
             *//*
            h.actions[6] = (SimpleAction) p -> shapeBoat(p, 2);
            h.actions[25] = (SimpleAction) p -> shapeBoat(p, 2);
            h.actions[37] = (SimpleAction) p -> shapeBoat(p, 2);

            *//**
             * Stable dugout
             *//*
            h.actions[3] = (SimpleAction) p -> shapeBoat(p, 3);
            h.actions[26] = (SimpleAction) p -> shapeBoat(p, 3);
            h.actions[38] = (SimpleAction) p -> shapeBoat(p, 3);

            *//**
             * Waka
             *//*
            h.actions[13] = (SimpleAction) p -> shapeBoat(p, 4);
            h.actions[27] = (SimpleAction) p -> shapeBoat(p, 4);
            h.actions[39] = (SimpleAction) p -> shapeBoat(p, 4);
        });*/
    }
}
