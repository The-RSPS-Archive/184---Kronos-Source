package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.slayer.Slayer;

public class Karuulm {

    private static Bounds DUNGEON_BOUNDS = new Bounds(1216, 10112, 1406, 10303, -1);
    private static Bounds SAFE_BOUNDS = new Bounds(1303, 10187, 1320, 10214, 0);
    private static Bounds[] SLAYER_ONLY_BOUNDS = {
        new Bounds(1251, 10147, 1279, 10170, 0), // Wyrms
        new Bounds(1300, 10255, 1336, 10277, 0), // Hydras
        new Bounds(1337, 10223, 1366, 10255, 1), // Drakes
    };

    static {
        MapListener.register(Karuulm::checkActive).onEnter(Karuulm::onEnter).onExit(Karuulm::onExit);

        ObjectAction.register(34544, "climb", (player, obj) -> { // walls near the entrance
            if (!isProtectedFromBurn(player) && player.getPosition().inBounds(SAFE_BOUNDS)) {
                player.dialogue(new MessageDialogue("The floor looks dangerously hot ahead, you will likely need feet protection.<br>Perhaps a slayer master could help.<br><br>Are you sure you want to continue?"),
                        new OptionsDialogue(
                                new Option("Yes.", () -> climbWall(player, obj)),
                                new Option("No.")
                        ));
            } else {
                climbWall(player, obj);
            }
        });
        Tile.getObject(34515, 1269, 10171, 0, 10, -1).skipReachCheck = p -> true;
        Tile.getObject(34515, 1307, 10253, 0, 10, -1).skipReachCheck = p -> true;
        ObjectAction.register(34515, 1, Karuulm::jumpGap);

        ObjectAction.register(34516, "crawl through", Karuulm::crawlThroughTunnel);

        ObjectAction.register(34530, 1330, 10205, 0, 1, (player, obj) -> player.getMovement().teleport(1334, 10205, 1));
        ObjectAction.register(34531, 1330, 10205, 1, 1, (player, obj) -> player.getMovement().teleport(1329, 10205, 0));

        ObjectAction.register(34530, 1314, 10188, 1, 1, (player, obj) -> player.getMovement().teleport(1318, 10188, 2));
        ObjectAction.register(34531, 1314, 10188, 2, 1, (player, obj) -> player.getMovement().teleport(1313, 10188, 1));

        ObjectAction.register(4469, 1357, 10206, 1, 1, Karuulm::handleBarrier);
        ObjectAction.register(4469, 1357, 10207, 1, 1, Karuulm::handleBarrier);

    }

    private static void handleBarrier(Player player, GameObject wall) {
        if (player.getCombat().isDefending(25)) {
            player.sendMessage("You can't leave this barrier while in combat.");
            return;
        }
        player.addEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            boolean east = wall.x > player.getAbsX();
            player.getMovement().force(east ? 1 : -1 , 0, 0, 0, 0, 50, east ? Direction.EAST : Direction.WEST);
            e.delay(2);
            player.unlock();
        });
    }

    private static boolean isInSlayerOnlyArea(Player player) {
        for (Bounds b : SLAYER_ONLY_BOUNDS) {
            if (player.getPosition().inBounds(b)) {
                return true;
            }
        }
        return false;
    }

    private static void onEnter(Player player) {
        player.attackNpcListener = (player1, npc, message) -> {
            if (isInSlayerOnlyArea(player1) && !Slayer.isTask(player1, npc)) {
                if (message)
                    player.sendMessage("In this area, you may only attack monsters if they are your slayer assignment.");
                return false;
            }
            return true;
        };
    }

    private static void onExit(Player player, boolean logout) {
        player.attackNpcListener = null;
    }

    private static void climbWall(Player player, GameObject wall) {
        Direction dir;
        switch (wall.direction) {
            case 0:
            case 2:
                dir = player.getAbsY() < wall.y ? Direction.NORTH : Direction.SOUTH;
                break;
            case 1:
            case 3:
                dir = player.getAbsX() < wall.x ? Direction.EAST : Direction.WEST;
                break;
            default:
                return;
        }
        player.addEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(839);
            player.getMovement().force(dir.deltaX * 2, dir.deltaY * 2, 0, 0, 0, 60, dir);
            e.delay(2);
            player.unlock();
        });
    }

    private static void jumpGap(Player player, GameObject gap) {
        Direction dir = player.getAbsY() > gap.y ? Direction.SOUTH : Direction.NORTH;
        player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(3067);
            player.getMovement().force(dir.deltaX * 5, dir.deltaY * 5, 0, 0, 25, 65, dir);
            e.delay(3);
            player.unlock();
        });
    }

    private static void crawlThroughTunnel(Player player, GameObject gap) {
        Direction dir = player.getAbsX() > gap.x ? Direction.WEST : Direction.EAST;
        player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(2796);
            player.getMovement().force(dir.deltaX * 7, dir.deltaY * 7, 0, 0, 15, 85, dir);
            e.delay(3);
            player.animate(-1);
            player.unlock();
        });
    }

    private static boolean checkActive(Player player) {
        if (!player.getPosition().inBounds(DUNGEON_BOUNDS) && !isInHydraInstance(player)) {
            return false;
        }
        if (!player.getPosition().inBounds(SAFE_BOUNDS) && !isProtectedFromBurn(player)) {
            player.hit(new Hit().randDamage(1, 3));
        }
        return true;
    }

    private static boolean isProtectedFromBurn(Player player) {
        return player.getEquipment().hasId(23037) // boots of stone
                || player.getEquipment().hasId(22951) // boots of brimstone
                || player.getEquipment().hasId(21643); // granite boots
    }

    private static boolean isInHydraInstance(Player player) {
        return player.getPosition().getRegion().dynamicData != null
                && player.getPosition().getRegion().dynamicData[0][0][0][1] == 5536; // chunk is generated off region 5536, which is the hydra boss lair region
    }


}
