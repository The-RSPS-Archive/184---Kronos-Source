package io.ruin.model.content;

import io.ruin.cache.ObjectDef;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Door;
import io.ruin.model.map.route.types.RouteObject;

/**
 * 770
 * 769
 *
 * @author ReverendDread on 7/21/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class Obstacle {

    static {

        /**
         * Start of barbarian outpost basalt obstacles
         */
        //Check starting distances
        Tile.getObject(4550, 2522, 3595, 0).walkTo = Position.of(2522, 3597, 0);
        Tile.getObject(4551, 2522, 3597, 0).walkTo = Position.of(2522, 3595, 0);

        Tile.getObject(4552, 2522, 3600, 0).walkTo = Position.of(2522, 3602, 0);
        Tile.getObject(4553, 2522, 3602, 0).walkTo = Position.of(2522, 3600, 0);

        Tile.getObject(4554, 2518, 3611, 0).walkTo = Position.of(2516, 3611, 0);
        Tile.getObject(4555, 2516, 3611, 0).walkTo = Position.of(2518, 3611, 0);

        Tile.getObject(4556, 2514, 3613, 0).walkTo = Position.of(2514, 3615, 0);
        Tile.getObject(4557, 2514, 3615, 0).walkTo = Position.of(2514, 3613, 0);

        Tile.getObject(4558, 2514, 3617, 0).walkTo = Position.of(2514, 3619, 0);
        Tile.getObject(4559, 2514, 3619, 0).walkTo = Position.of(2514, 3617, 0);

        //Flag unmovable spots
        Tile.get(2522, 3601, 0, true).flagUnmovable();
        Tile.get(2517, 3611, 0, true).flagUnmovable();
        Tile.get(2514, 3614, 0, true).flagUnmovable();
        Tile.get(2514, 3618, 0, true).flagUnmovable();
        Tile.get(2522, 3596, 0, true).flagUnmovable();

        //Do objs
        ObjectAction.register(4550, "jump-to", ((player, obj) -> {
            player.getMovement().force(0, -2, 0, 0, 15, 50, Direction.SOUTH);
            player.animate(769);
        }));
        ObjectAction.register(4551, "jump-across", ((player, obj) -> {
            player.getMovement().force(0, 2, 0, 0, 15, 50, Direction.NORTH);
            player.animate(769);
        }));
        ObjectAction.register(4552, "jump-across", ((player, obj) -> {
            player.getMovement().force(0, -2, 0, 0, 15, 50, Direction.SOUTH);
            player.animate(769);
        }));
        ObjectAction.register(4553, "jump-across", ((player, obj) -> {
            player.getMovement().force(0, 2, 0, 0, 15, 50, Direction.NORTH);
            player.animate(769);
        }));
        ObjectAction.register(4554, "jump-across", ((player, obj) -> {
            player.getMovement().force(2, 0, 0, 0, 15, 50, Direction.EAST);
            player.animate(769);
        }));
        ObjectAction.register(4555, "jump-across", ((player, obj) -> {
            player.getMovement().force(-2, 0, 0, 0, 15, 50, Direction.WEST);
            player.animate(769);
        }));
        ObjectAction.register(4556, "jump-across", ((player, obj) -> {
            player.getMovement().force(0, -2, 0, 0, 15, 50, Direction.SOUTH);
            player.animate(769);
        }));
        ObjectAction.register(4557, "jump-across", ((player, obj) -> {
            player.getMovement().force(0, 2, 0, 0, 15, 50, Direction.NORTH);
            player.animate(769);
        }));
        ObjectAction.register(4558, "jump-across", ((player, obj) -> {
            player.getMovement().force(0, -2, 0, 0, 15, 50, Direction.SOUTH);
            player.animate(769);
        }));
        ObjectAction.register(4559, "jump-to", ((player, obj) -> {
            player.getMovement().force(0, 2, 0, 0, 15, 50, Direction.NORTH);
            player.animate(769);
        }));
        /**
         * End of barbarian outpost basalt obstacles
         */

        //Lighthouse door
        ObjectAction.register(4577, "walk-through", ((player, obj) -> {
            if (player.getAbsY() > 3635) {
                player.getMovement().teleport(2509, 3635, 0);
            } else {
                player.getMovement().teleport(2509, 3636, 0);
            }
        }));
        //Lighthouse stairs
        Tile.getObject(4568, 2506, 3640, 0).walkTo = Position.of(2506, 3642, 0);
        Tile.getObject(4569, 2506, 3640, 1).walkTo = Position.of(2505, 3641, 1);
        Tile.getObject(4569, 2506, 3640, 1).walkTo = Position.of(2505, 3641, 2);
        ObjectAction.register(4568, "climb-up", ((player, obj) -> player.getMovement().teleport(2505, 3641, 1)));
        ObjectAction.register(4569, "climb-up", ((player, obj) -> player.getMovement().teleport(2505, 3641, 2)));
        ObjectAction.register(4569, "climb", ((player, obj) -> {
            player.dialogue(new OptionsDialogue(
                new Option("Climb up.", () -> player.getMovement().teleport(2505, 3641, 2)),
                new Option("Climb down.", () -> player.getMovement().teleport(2505, 3641, 0)),
                new Option("Nevermind.")
            ));
        }));
        ObjectAction.register(4569, "climb-down", ((player, obj) -> player.getMovement().teleport(2505, 3641, 0)));
        ObjectAction.register(4570, "climb-down", ((player, obj) -> player.getMovement().teleport(2505, 3641, 1)));

        //broken bridge
        Tile.get(2597, 3608, 0, true).flagUnmovable();
        Tile.getObject(4615, 2596, 3608, 0).walkTo = Position.of(2596, 3608, 0);
        ObjectAction.register(4615, "cross", ((player, obj) -> {
            player.getMovement().force(2, 0, 0, 0, 15, 50, Direction.EAST);
            player.animate(769);
        }));
        Tile.getObject(4616, 2598, 3608, 0).walkTo = Position.of(2598, 3608, 0);
        ObjectAction.register(4616, "cross", ((player, obj) -> {
            player.getMovement().force(-2, 0, 0, 0, 15, 50, Direction.WEST);
            player.animate(769);
        }));
    }

}
