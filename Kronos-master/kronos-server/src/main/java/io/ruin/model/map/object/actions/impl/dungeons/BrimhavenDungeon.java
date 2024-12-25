package io.ruin.model.map.object.actions.impl.dungeons;

import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class BrimhavenDungeon {

    static {
        /**
         * Entrance/exit
         */
        ObjectAction.register(20878, 1, (player, obj) -> player.getMovement().teleport(2745, 3152, 0));
        ObjectAction.register(20877, 1, (player, obj) -> player.getMovement().teleport(2713, 9564, 0));

        /**
         * Stairs
         */
        ObjectAction.register(21722, 1, (player, obj) ->player.getMovement().teleport(2643, 9594, 2));
        ObjectAction.register(21724, 1, (player, obj) ->player.getMovement().teleport(2649, 9591, 0));
        ObjectAction.register(21725, 1, (player, obj) ->player.getMovement().teleport(2637, 9510, 2));
        ObjectAction.register(21726, 1, (player, obj) ->player.getMovement().teleport(2637, 9517, 0));

        /**
         * Shortcuts/Obstacles
         */

        //lvl 87 agility vine (up)
        ObjectAction.register(26880, 1, (p, obj) -> {
            if(!p.getStats().check(StatType.Agility, 87, "use this shortcut"))
                return;
            p.getMovement().teleport(2670, 9583, 2);
        });
        //lvl 87 agility vine (down)
        ObjectAction.register(26882, 1, (p, obj) -> {
            if(!p.getStats().check(StatType.Agility, 87, "use this shortcut"))
                return;
            p.getMovement().teleport(2673, 9583, 0);
        });

        //lvl 22 agility pipe
        ObjectAction.register(21728, 1, (p, obj) -> {
            if(!p.getStats().check(StatType.Agility, 22, "use this shortcut"))
                return;
            p.getMovement().teleport(2655, p.getAbsY() >= 9572 ? 9566 : 9573, 0);
        });

        //lvl 12 agility stones
        ObjectAction.register(21738, 1, (p, obj) -> {
            if(!p.getStats().check(StatType.Agility, 12, "use this shortcut"))
                return;
            p.getMovement().teleport(2647, 9557, 0);
        });
        //lvl 12 agility stones
        ObjectAction.register(21739, 1, (p, obj) -> {
            if(!p.getStats().check(StatType.Agility, 12, "use this shortcut"))
                return;
            p.getMovement().teleport(2649, 9562, 0);
        });
        //lvl 83 agility stones
        Tile.getObject(19040, 2684, 9548, 0, 22, 1).walkTo = new Position(2682, 9548, 0);
        Tile.getObject(19040, 2688, 9547, 0, 22, 2).walkTo = new Position(2690, 9547, 0);
        ObjectAction.register(19040, 1, (p, obj) -> {
            if(!p.getStats().check(StatType.Agility, 83, "use this shortcut"))
                return;
            int x = p.getAbsX();
            int y = p.getAbsY();
            if(x == 2682 && y == 9548)
                p.getMovement().teleport(2690, 9547, 0);
            else if(x == 2690 && y == 9547)
                p.getMovement().teleport(2682, 9548, 0);
            else if(x == 2695 && y == 9533)
                p.getMovement().teleport(2697, 9525, 0);
            else if(x == 2697 && y == 9525)
                p.getMovement().teleport(2695, 9533, 0);
            else
                p.sendMessage("You can't reach that.");
        });
        //lvl 30 agility balance
        ObjectAction.register(20882, 1, (p, obj) -> {
            if(!p.getStats().check(StatType.Agility, 30, "use this shortcut"))
                return;
            p.getMovement().teleport(2687, 9506, 0);
        });
        //lvl 30 agility balance
        ObjectAction.register(20884,1, (p, obj) -> {
            if(!p.getStats().check(StatType.Agility, 30, "use this shortcut"))
                return;
            p.getMovement().teleport(2682, 9506, 0);
        });
        //lvl 34 agility pipe
        ObjectAction.register(21727, 1, (p, obj)-> {
            if(!p.getStats().check(StatType.Agility, 34, "use this shortcut"))
                return;
            p.getMovement().teleport(2698, p.getAbsY() >= 9499 ? 9492 : 9500, 0);
        });
    }
}
