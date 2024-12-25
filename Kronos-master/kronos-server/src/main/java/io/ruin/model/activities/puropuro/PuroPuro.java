package io.ruin.model.activities.puropuro;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Direction;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class PuroPuro {

    private static final String[] PUSH_THROUGH_MESSAGE = {
            "You use your strength to push through the wheat in the most efficient fashion.",
            "You push through the wheat. It's hard work though.",
            "You use your strength to push through the wheat.",
    };

    private static void moveThroughWall(Player player, GameObject wall) {
        int playerX = player.getAbsX();
        int playerY = player.getAbsY();
        int wallX = wall.x;
        int wallY = wall.y;

        if (playerX == wallX && playerY < wallY)
            player.getMovement().force(0, 2, 0, 0, 0, 210, Direction.NORTH);
        else if (playerX == wallX && playerY > wallY)
            player.getMovement().force(0, -2, 0, 0, 0, 210, Direction.SOUTH);
        else if (playerY == wallY && playerX < wallX)
            player.getMovement().force(2, 0, 0, 0, 0, 210, Direction.EAST);
        else if (playerY == wallY && playerX > wallX)
            player.getMovement().force(-2, 0, 0, 0, 0, 210, Direction.WEST);

        player.getStats().addXp(StatType.Strength, Random.get(2, 4), false);
    }

    private static void exit(Player player, GameObject portal) {
        player.startEvent(event -> {
            player.lock();
            player.stepAbs(portal.x, portal.y, StepType.FORCE_WALK);
            event.delay(2);
            player.animate(6601);
            player.graphics(1118);
            event.delay(9);
            player.getMovement().teleport(2426, 4445);
            player.unlock();
        });
    }

    static {
        /**
         * Crop circle
         */
        ObjectAction.register(24991, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock();
            event.delay(1);
            player.stepAbs(2427, 4446, StepType.FORCE_WALK);
            event.delay(1);
            player.animate(6601);
            player.graphics(1118);
            event.delay(9);
            player.getMovement().teleport(2591, 4319);
            player.unlock();
        }));

        /**
         * Portal
         */
        ObjectAction.register(25014, "use", (player, obj) -> player.dialogue(
                new NPCDialogue(5735, "Going so soon?"),
                new OptionsDialogue(
                        new Option("Yes, get me out of here!", () -> player.dialogue(
                                new PlayerDialogue("Yes, get me out of here!"),
                                new ActionDialogue(() -> player.startEvent(event -> exit(player, obj)))
                        )),
                        new Option("Ah, no I'll hang around a bit longer.", () -> player.dialogue(
                                new PlayerDialogue("Ah, no I'll hang around a bit longer.")
                        )),
                        new Option("Where will I go if I leave?", () -> player.dialogue(
                                new PlayerDialogue("Where will I go if I leave?"),
                                new NPCDialogue(5735, "Back where you came from, of course!"),
                                new PlayerDialogue("So not back to where the crop circle is now?"),
                                new NPCDialogue(5735, "The aetheric thread connecting you to your own plane leads back to the wheat field" +
                                        " where you came from. Obviously. So you will always go back to where you came from. Don't you humans know anything?"),
                                new PlayerDialogue("Evidently not.")
                        ))
                )
        ));
        ObjectAction.register(25014, "escape", PuroPuro::exit);


        /**
         * Magical wheat
         */
        int[] magicalWheat = {25029, 25016, 25019, 25021};
        for (int id : magicalWheat) {
            ObjectAction.register(id, "push-through", (player, obj) -> player.startEvent(event -> {
                player.lock();
                event.delay(1);
                moveThroughWall(player, obj);
                player.animate(6594);
                player.sendFilteredMessage(Random.get(PUSH_THROUGH_MESSAGE));
                event.delay(6);
                player.unlock();
            }));
        }
    }

    private static final WheatPosition[] POSITIONS = {
            new WheatPosition(2572, 4348, false),
            new WheatPosition(2606, 4348, false),
            new WheatPosition(2603, 4346, true),
            new WheatPosition(2569, 4345, false),
            new WheatPosition(2576, 4342, false),
            new WheatPosition(2580, 4343, true),
            new WheatPosition(2606, 4342, false),
            new WheatPosition(2601, 4340, true),
            new WheatPosition(2595, 4343, true),
            new WheatPosition(2607, 4339, false),
            new WheatPosition(2604, 4336, false),
            new WheatPosition(2601, 4337, true),
            new WheatPosition(2584, 4336, false),
            new WheatPosition(2586, 4334, true),
            new WheatPosition(2589, 4333, false),
            new WheatPosition(2599, 4333, false),
            new WheatPosition(2596, 4331, true),
            new WheatPosition(2593, 4330, false),
            new WheatPosition(2584, 4330, false),
            new WheatPosition(2582, 4333, false),
            new WheatPosition(2581, 4337, true),
            new WheatPosition(2568, 4330, false),
            new WheatPosition(2565, 4311, false),
            new WheatPosition(2571, 4316, false),
            new WheatPosition(2574, 4311, false),
            new WheatPosition(2574, 4328, false),
            new WheatPosition(2576, 4301, false),
            new WheatPosition(2579, 4298, false),
            new WheatPosition(2576, 4295, false),
            new WheatPosition(2572, 4292, false),
            new WheatPosition(2579, 4293, true),
            new WheatPosition(2599, 4296, true),
            new WheatPosition(2607, 4295, false),
            new WheatPosition(2605, 4298, false),
            new WheatPosition(2614, 4292, false),
            new WheatPosition(2615, 4315, false),
            new WheatPosition(2612, 4324, false),
            new WheatPosition(2618, 4335, false),
            new WheatPosition(2612, 4336, false),
            new WheatPosition(2603, 4314, false),
            new WheatPosition(2586, 4302, true),
            new WheatPosition(2590, 4299, true),
            new WheatPosition(2583, 4296, true),
            new WheatPosition(2600, 4293, true),
            new WheatPosition(2618, 4305, false),
            new WheatPosition(2609, 4310, false),
            new WheatPosition(2606, 4329, false),
            new WheatPosition(2599, 4305, true),
            new WheatPosition(2595, 4308, true),
            new WheatPosition(2586, 4310, false),
            new WheatPosition(2598, 4310, false),
            new WheatPosition(2602, 4307, false),
            new WheatPosition(2587, 4307, false),
            new WheatPosition(2583, 4304, false),
            new WheatPosition(2603, 4304, false),
            new WheatPosition(2607, 4301, false),


    };

    private static final int WHEAT_STALE_VERTICAL = 25021;
    private static final int WHEAT_STALE_HORIZONTAL = 25016;
    private static final int WHEAT_GROWING = 25022;
    private static final int WHEAT_GROWING_ANIM = 6597;
    private static final int WHEAT_DISAPPEARING = 25023;
    private static final int WHEAT_DISAPPEARING_ANIM = 6598;

    static {
        World.startEvent(event -> {
            while (true) {
                event.delay(10);
                for (WheatPosition wp : POSITIONS) {// todo make this run only when there are players in the area
                    GameObject obj = Tile.getObject(WHEAT_STALE_VERTICAL, wp.x, wp.y, 0, 22, -1);
                    if (obj == null)
                        obj = Tile.getObject(WHEAT_STALE_HORIZONTAL, wp.x, wp.y, 0, 22, -1);
                    if (!Random.rollPercent(30))
                        continue;
                    if (obj == null || (obj.id != WHEAT_STALE_HORIZONTAL && obj.id != WHEAT_STALE_VERTICAL)) {
                        if (obj != null)
                            obj.remove();
                        block(wp); // todo animations
                        replaceSurrounding(wp);
                    } else {
                        unblock(wp);
                        restoreSurrounding(wp);
                    }
                }
                event.delay(300);
            }
        });
    }

    private static void replaceSurrounding(WheatPosition wp) {
        int vecX = wp.horizontal ? 1 : 0;
        int vecY = wp.horizontal ? 0 : -1;
        int id = wp.getOppositeStaleId();
        if (!wp.horizontal && (Tile.get(wp.x + vecX * -2, wp.y + vecY * -2, 0, false).clipping != 0 && Tile.get(wp.x + vecX * 3, wp.y + vecY * 3, 0, false).clipping != 0)) {
            id = wp.getStaleId();
        }
        int finalId = id;
        GameObject.forObj(-1, wp.x + vecX * -1, wp.y + (vecY*-1), 0, o -> o.setId(finalId));
        GameObject.forObj(-1, wp.x + vecX * 2, wp.y + vecY * 2, 0, o -> o.setId(finalId));
    }

    private static void restoreSurrounding(WheatPosition wp) {
        int vecX = wp.horizontal ? 1 : 0;
        int vecY = wp.horizontal ? 0 : -1;
        int id = wp.getOppositeStaleId();
        if (!wp.horizontal && (Tile.get(wp.x + vecX * -2, wp.y + vecY * -2, 0, false).clipping != 0 && Tile.get(wp.x + vecX * 3, wp.y + vecY * 3, 0, false).clipping != 0)) {
            id = wp.getStaleId();
        }
        GameObject.forObj(id, wp.x + vecX * -1, wp.y + (vecY*-1), 0, o -> o.setId(o.originalId));
        GameObject.forObj(id, wp.x + vecX * 2, wp.y + vecY * 2, 0, o -> o.setId(o.originalId));
    }

    private static void block(WheatPosition wp) {
        int vecX = wp.horizontal ? 1 : 0;
        int vecY = wp.horizontal ? 0 : -1;
        int id = wp.getStaleId();
        if (!wp.horizontal && (Tile.get(wp.x + vecX * -2, wp.y + vecY * -2, 0, false).clipping != 0 && Tile.get(wp.x + vecX * 3, wp.y + vecY * 3, 0, false).clipping != 0)) {
            id = wp.getOppositeStaleId();
        }
        int finalId = id;
        GameObject.forObj(-1, wp.x, wp.y, 0, o -> o.setId(finalId));
        GameObject.forObj(-1, wp.x + vecX, wp.y + vecY, 0,  o -> o.setId(finalId));
    }

    private static void unblock(WheatPosition wp) {
        int vecX = wp.horizontal ? 1 : 0;
        int vecY = wp.horizontal ? 0 : -1;
        int id = wp.getStaleId();
        if (!wp.horizontal && (Tile.get(wp.x + vecX * -2, wp.y + vecY * -2, 0, false).clipping != 0 && Tile.get(wp.x + vecX * 3, wp.y + vecY * 3, 0, false).clipping != 0)) {
            id = wp.getOppositeStaleId();
        }
        GameObject.forObj(id, wp.x, wp.y, 0, o -> o.setId(o.originalId)); //null pointer????
        /*
            java.lang.NullPointerException
                at io.runite.model.map.object.GameObject.clip(GameObject.java:140)
                at io.runite.model.map.Tile.removeObject(Tile.java:114)
                at io.runite.model.map.object.GameObject.setId(GameObject.java:96)
                at io.runite.model.map.object.GameObject.setId(GameObject.java:90)
                at io.runite.model.activities.puropuro.PuroPuro.lambda$unblock$18(PuroPuro.java:272)
                at io.runite.model.map.object.GameObject.forObj(GameObject.java:76)
                at io.runite.model.activities.puropuro.PuroPuro.unblock(PuroPuro.java:272)
                at io.runite.model.activities.puropuro.PuroPuro.lambda$static$11(PuroPuro.java:221)
         */
        GameObject.forObj(id, wp.x + vecX, wp.y + vecY, 0, o -> o.setId(o.originalId));
    }

    private static class WheatPosition {
        int x, y;
        boolean horizontal;

        public WheatPosition(int x, int y, boolean horizontal) {
            this.x = x;
            this.y = y;
            this.horizontal = horizontal;
        }

        int getStaleId() {
            return horizontal ? WHEAT_STALE_HORIZONTAL : WHEAT_STALE_VERTICAL;
        }

        int getOppositeStaleId() {
            return !horizontal ? WHEAT_STALE_HORIZONTAL : WHEAT_STALE_VERTICAL;
        }
    }

    public static void entered(Player player) {
        player.getPacketSender().sendMapState(2);
    }

    public static void exited(Player player, boolean logout) {
        if (logout) //no need to do anything
            return;
        player.getPacketSender().sendMapState(0);
        if (player.isVisibleInterface(Interface.IMPLING_SCROLL))
            player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
    }

    static  {
        MapListener.registerRegion(10307)
                .onEnter(PuroPuro::entered)
                .onExit(PuroPuro::exited);
    }

}
