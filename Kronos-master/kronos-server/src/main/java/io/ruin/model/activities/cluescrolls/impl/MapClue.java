package io.ruin.model.activities.cluescrolls.impl;

import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.Tile;

public class MapClue extends Clue {

    private final int interfaceId;

    private MapClue(int interfaceId, ClueType type) {
        super(type);
        this.interfaceId = interfaceId;
    }

    @Override
    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, interfaceId);
    }

    /**
     * Register
     */

    private static void registerDig(int interfaceId, int x, int y, int z, ClueType type) {
        MapClue clue = new MapClue(interfaceId, type);
        Tile.get(x, y, z, true).digAction = clue::advance;
    }

    private static void registerObj(int interfaceId, int objectId, int x, int y, int z, ClueType type) {
        Tile.getObject(objectId, x, y, z).mapClue = new MapClue(interfaceId, type);
    }

    static {
        registerDig(86, 2449, 3130, 0, ClueType.ELITE);
        registerDig(87, 3300, 3290, 0, ClueType.EASY);
        registerDig(337, 2970, 3414, 0, ClueType.EASY);
        registerDig(338, 3021, 3912, 0, ClueType.HARD);
        registerDig(339, 2723, 3338, 0, ClueType.HARD);
        registerDig(340, 2536, 3865, 0, ClueType.MEDIUM);
        registerDig(341, 3434, 3265, 0, ClueType.MEDIUM);
        registerDig(342, 2455, 3230, 0, ClueType.MEDIUM);
        registerDig(343, 2578, 3597, 0, ClueType.MEDIUM);
        registerDig(344, 2666, 3562, 0, ClueType.MEDIUM);
        registerDig(346, 3166, 3360, 0, ClueType.EASY);
        registerDig(347, 3289, 3374, 0, ClueType.EASY);
        registerDig(348, 3091, 3227, 0, ClueType.MEDIUM);
        registerDig(351, 3043, 3399, 0, ClueType.EASY);
        registerDig(352, 2906, 3293, 0, ClueType.MEDIUM);
        registerDig(353, 2616, 3077, 0, ClueType.HARD);
        registerDig(354, 2612, 3482, 0, ClueType.EASY);
        registerDig(356, 3110, 3152, 0, ClueType.EASY);
        registerDig(357, 2488, 3308, 0, ClueType.HARD);
        registerObj(358, 18506, 2457, 3182, 0, ClueType.HARD);
        registerObj(359, 354, 3026, 3628, 0, ClueType.HARD);
        registerDig(360, 2651, 3231, 0, ClueType.MEDIUM);
        registerObj(361, 354, 2565, 3248, 0, ClueType.MEDIUM);
        registerDig(362, 2924, 3209, 0, ClueType.MEDIUM);
    }

}

