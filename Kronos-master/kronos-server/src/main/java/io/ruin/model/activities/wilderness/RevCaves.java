package io.ruin.model.activities.wilderness;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class RevCaves { //todo add the agility requirements for this (for the economy world)

    private static void jumpToPillar(Player player, int x, int y, Direction dir, int agilityReq) {
        if(player.getStats().get(StatType.Agility).currentLevel < agilityReq) {
            player.dialogue(new ItemDialogue().one(6514, "You need an agility level of " + agilityReq + " to jump across this pillar."));
            return;
        }
        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(741);
            player.getMovement().force(x, y, 0, 0, 5, 35, dir);
            event.delay(1);
            player.animate(741);
            player.getMovement().force(x, y, 0, 0, 5, 35, dir);
            event.delay(1);
            player.unlock();
        });
    }

    static {
        /**
         * South entrance/exit
         */
        ObjectAction.register(31557, 3194, 10055, 0, "exit", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3075, 3653, 0);
            player.sendFilteredMessage("You exit the cave.");
            player.unlock();
        }));
        ObjectAction.register(31555, 3073, 3654, 0, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3196, 10056, 0);
            player.sendFilteredMessage("You enter the cave.");
            player.unlock();
        }));

        /**
         * North entrance/exit
         */
        ObjectAction.register(31558, 3240, 10235, 0, "exit", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3126, 3833, 0);
            player.sendFilteredMessage("You exit the cave.");
            player.unlock();
        }));
        ObjectAction.register(31556, 3124, 3831, 0, "enter", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3241, 10234, 0);
            player.sendFilteredMessage("You enter the cave.");
            player.unlock();
        }));

        /**
         * Southwest pillar
         */
        ObjectAction.register(31561, 3200, 10136, 0, "jump to", (player, obj) -> {
            jumpToPillar(player, player.getAbsX() == 3198 ? 2 : -2, 0, player.getAbsX() == 3198 ? Direction.EAST : Direction.WEST, 75);
        });
        GameObject southWestPillar = Tile.getObject(31561, 3200, 10136, 0);
        southWestPillar.skipReachCheck = p -> p.equals(3198, 10136) || p.equals(3202, 10136);
        Tile.get(3201, 10136, 0).flagUnmovable();
        Tile.get(3199, 10136, 0).flagUnmovable();

        /**
         * South pillar
         */
        ObjectAction.register(31561, 3220, 10086, 0, "jump to", (player, obj) -> {
            jumpToPillar(player, 0, player.getAbsY() == 10084 ? 2 : -2, player.getAbsY() == 10084 ? Direction.NORTH : Direction.SOUTH, 65);
        });
        GameObject southPillar = Tile.getObject(31561, 3220, 10086, 0);
        southPillar.skipReachCheck = p -> p.equals(3220, 10084) || p.equals(3220, 10088);
        Tile.get(3220, 10085, 0).flagUnmovable();
        Tile.get(3220, 10087, 0).flagUnmovable();

        /**
         * Southeast pillar
         */
        ObjectAction.register(31561, 3241, 10145, 0, "jump to", (player, obj) -> {
            jumpToPillar(player, player.getAbsX() == 3239 ? 2 : -2, 0, player.getAbsX() == 3239 ? Direction.EAST : Direction.WEST, 89);
        });
        GameObject southEastPillar = Tile.getObject(31561, 3241, 10145, 0);
        southEastPillar.skipReachCheck = p -> p.equals(3243, 10145) || p.equals(3239, 10145);
        Tile.get(3240, 10145, 0).flagUnmovable();
        Tile.get(3242, 10145, 0).flagUnmovable();

        /**
         * North pillar
         */
        ObjectAction.register(31561, 3202, 10196, 0, "jump to", (player, obj) -> {
            jumpToPillar(player, player.getAbsX() == 3200 ? 2 : -2, 0, player.getAbsX() == 3200 ? Direction.EAST : Direction.WEST, 75);
        });
        GameObject northPillar = Tile.getObject(31561, 3202, 10196, 0);
        northPillar.skipReachCheck = p -> p.equals(3204, 10196) || p.equals(3200, 10196);
        Tile.get(3201, 10196, 0).flagUnmovable();
        Tile.get(3203, 10196, 0).flagUnmovable();

        /**
         * Northwest pillar
         */
        ObjectAction.register(31561, 3180, 10209, 0, "jump to", (player, obj) -> {
            jumpToPillar(player, 0, player.getAbsY() == 10207 ? 2 : -2, player.getAbsY() == 10207 ? Direction.NORTH : Direction.SOUTH, 75);
        });
        GameObject northWestPillar = Tile.getObject(31561, 3180, 10209, 0);
        northWestPillar.skipReachCheck = p -> p.equals(3180, 10207) || p.equals(3180, 10211);
        Tile.get(3180, 10210, 0).flagUnmovable();
        Tile.get(3180, 10208, 0).flagUnmovable();
    }
}
