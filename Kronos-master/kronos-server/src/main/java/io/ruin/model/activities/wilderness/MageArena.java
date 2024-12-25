package io.ruin.model.activities.wilderness;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Direction;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;

public class MageArena {

    private static void pullLever(Player player, GameObject lever, int teleportY, String message, boolean enter) {
        if (player.getCombat().checkTb())
            return;
        player.startEvent(event -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE);
            player.animate(2710);
            lever.animate(2711);
            player.sendMessage("You pull the lever...");
            event.delay(2);
            player.animate(714);
            player.graphics(111, 110, 0);
            event.delay(3);
            player.resetAnimation();
            player.mageArena = enter;
            player.getMovement().teleport(3105, teleportY, 0);
            player.sendMessage("...and get teleported " + message + " the arena!");
            player.unlock();
        });
    }

    static {
        /**
         * Levers
         */
        ObjectAction.register(9706, 3104, 3956, 0, "pull", (player, obj) -> {
            pullLever(player, obj, 3951, "into", true);
        });
        ObjectAction.register(9707, 3105, 3952, 0, "pull", (player, obj) -> {
            pullLever(player, obj, 3956, "out of", false);
        });

        /**
         * Odd unclipped areas around the circle which make you look
         * like you're floating on lava.
         */
        Tile.get(3093, 3939, 0).flagUnmovable();
        Tile.get(3094, 3941, 0).flagUnmovable();
        Tile.get(3110, 3946, 0).flagUnmovable();
        Tile.get(3098, 3945, 0).flagUnmovable();
        Tile.get(3100, 3946, 0).flagUnmovable();
        Tile.get(3112, 3945, 0).flagUnmovable();
        Tile.get(3116, 3941, 0).flagUnmovable();
        Tile.get(3117, 3939, 0).flagUnmovable();
        Tile.get(3117, 3928, 0).flagUnmovable();
        Tile.get(3116, 3926, 0).flagUnmovable();
        Tile.get(3112, 3922, 0).flagUnmovable();
        Tile.get(3110, 3921, 0).flagUnmovable();
        Tile.get(3100, 3921, 0).flagUnmovable();
        Tile.get(3098, 3922, 0).flagUnmovable();
        Tile.get(3094, 3926, 0).flagUnmovable();
        Tile.get(3093, 3928, 0).flagUnmovable();

       SpawnListener.register("battle mage", npc -> npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            if (killer.player.mageArena) {
                int randomPoints = Random.get(3, 6);
                killer.player.mageArenaPoints += randomPoints;
            }
        });

        MapListener.registerRegion(12349)
                .onExit((p, logout) -> {
                    if (!logout)
                        p.mageArena = false;
                });

        NPCAction.register(1603, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "How can I help you?"),
                new OptionsDialogue(
                        new Option("How do I get mage arena points?", () -> player.dialogue(
                                new PlayerDialogue("How do I get mage arena points?"),
                                new NPCDialogue(npc, "Killing a Battle mage inside the Mage Arena will give you anywhere from 1-3 points. Be careful though, as "),
                                new NPCDialogue(npc, "it's dangerous out there. Also be sure to bring runes, as you can only use magic based attacks inside the arena."),
                                new PlayerDialogue("Okay, thanks.")
                        )),
                        /*
                        new Option("Can I see the point exchange?", () -> player.dialogue(
                                new PlayerDialogue("Can I see the point exchange?"),
                                new ActionDialogue(() -> npc.getDefinition().shop.open(player))
                        )),
                        */
                        new Option("I have to go.", () -> player.dialogue(new PlayerDialogue("I have to go.")))
                )
        ));
        NPCAction.register(1603, "check-points", (player, npc) -> {
            player.dialogue(new NPCDialogue(npc, "You've currently have " + player.mageArenaPoints + " point" + (player.mageArenaPoints == 1 ? "." : "s. Kill Battle Mage's inside the Mage Arena to get more points.")));
        });

        /**
         * Sack containing knife
         */
        ObjectAction.register(14743, 3093, 3956, 0, "search", (player, obj) -> {
            if(player.getInventory().isFull()) {
                player.sendFilteredMessage("Nothing interesting happens.");
                return;
            }

            player.getInventory().add(Tool.KNIFE, 1);
            player.sendFilteredMessage("You search the sack and find a knife.");
        });

        ObjectAction.register(2878, 2541, 4719, 0, "Step-into", (player, obj) -> {
            player.dialogue(new MessageDialogue("You step into the pool of sparkling water. You feel energy rush through your veins."), new ActionDialogue(() -> {
                player.startEvent((event) -> {
                    player.lock(LockType.FULL_DELAY_DAMAGE);
                    event.path(player, Position.of(2542, 4718));
                    event.delay(1);
                    player.animate(741);
                    player.getMovement().force(0, 2, 0, 0, 5, 40, Direction.getDirection(player.getPosition(), Position.of(2542, 4720)));
                    event.delay(1);
                    player.animate(804);
                    player.graphics(68);
                    event.delay(1);
                    player.getMovement().teleport(2509, 4689, 0);
                    player.animate(-1);
                    player.unlock();
                    player.getAppearance();
                });
            }));
        });

        ObjectAction.register(2879, 2508, 4686, 0, "Step-into", (player, obj) -> {
            player.dialogue(new MessageDialogue("You step into the pool of sparkling water. You feel energy rush through your veins."), new ActionDialogue(() -> {
                player.startEvent((event) -> {
                    player.lock(LockType.FULL_DELAY_DAMAGE);
                    event.path(player, Position.of(2509, 4689));
                    event.delay(1);
                    player.animate(741);
                    player.getMovement().force(0, -2, 0, 0, 5, 40, Direction.getDirection(player.getPosition(), Position.of(2509, 4687)));
                    event.delay(1);
                    player.animate(804);
                    player.graphics(68);
                    event.delay(1);
                    player.getMovement().teleport(2542, 4718, 0);
                    player.animate(-1);
                    player.unlock();
                });
            }));
        });

        ObjectAction.register(2873, 2500, 4720, 0, "Pray-at", (player, obj) -> {
            player.startEvent((event) -> {
                player.lock(LockType.MOVEMENT);
                player.animate(645);
                player.dialogue(new MessageDialogue("You kneel and chant to Saradomin...").hideContinue());
                event.delay(2);
                player.dialogue(new MessageDialogue("You kneel and chant to Saradomin...<br>" +
                        "You feel a rush of energy charge through your veins.<br>" +
                        "Suddenly a cape appears before you."));
                event.delay(1);
                World.sendGraphics(188, 50, 0, 2500, 4720, 0);
                new GroundItem(2412, 1).position(2500, 4720, 0).owner(player).spawnPrivate();
                player.unlock();
            });
        });

        ObjectAction.register(2875, 2507, 4723, 0, "Pray-at", (player, obj) -> {
            player.startEvent((event) -> {
                player.lock(LockType.MOVEMENT);
                player.animate(645);
                player.dialogue(new MessageDialogue("You kneel and chant to Guthix...").hideContinue());
                event.delay(3);
                World.sendGraphics(188, 0, 0, 2507, 4723, 0);
                new GroundItem(2413, 1).position(2507, 4723, 0).owner(player).spawnPrivate();
                player.dialogue(new MessageDialogue("You kneel and chant to Guthix...<br>" +
                        "You feel a rush of energy charge through your veins.<br>" +
                        "Suddenly a cape appears before you."));
                player.unlock();
            });
        });

        ObjectAction.register(2874, 2516, 4720, 0, "Pray-at", (player, obj) -> {
            player.startEvent((event) -> {
                player.lock(LockType.MOVEMENT);
                player.animate(645);
                player.dialogue(new MessageDialogue("You kneel and chant to Zamorak...").hideContinue());
                event.delay(3);
                World.sendGraphics(188, 0, 0, 2516, 4720, 0);
                new GroundItem(2414, 1).position(2516, 4720, 0).owner(player).spawnPrivate();
                player.dialogue(new MessageDialogue("You kneel and chant to Zamorak...<br>" +
                        "You feel a rush of energy charge through your veins.<br>" +
                        "Suddenly a cape appears before you."));
                player.unlock();
            });
        });
    }

}
