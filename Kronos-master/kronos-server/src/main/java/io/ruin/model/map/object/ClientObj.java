package io.ruin.model.map.object;

import io.ruin.api.protocol.world.WorldType;
import io.ruin.model.World;
import io.ruin.model.map.Tile;

/**
 * Used to spawn/remove objects client sided. Why? Because I
 * don't want a lot of objects being spawned and sent from the
 * server every time a player logs in or teleports home.XDD
 *
 * Be sure to keep the static block that does removals BEFORE the one that does spawns.
 */
public class ClientObj {

    private int id;
    private int x, y, z;
    private int type, direction;
    private WorldType world;

    private void remove() {
        if(world != null && world != World.type)
            return;
        GameObject obj = Tile.getObject(id, x, y, z, type, direction);
        if(obj == null) {
            System.err.println("Obj (" + id + ", " + x + ", " + y + ", " + z + ", " + type + ", " + direction + ") was not removed because it wasn't found.");
            return;
        }
        obj.tile.removeObject(obj);
    }

    private void spawn() {
        if(world != null && world != World.type)
            return;
        Tile tile = Tile.get(x, y, z, true);
        tile.addObject(new GameObject(id, x, y, z, type, direction));
    }

    private static ClientObj obj(int id, int x, int y, int z, int type, int direction) {
        return obj(id, x, y, z, type, direction, null);
    }

    private static ClientObj obj(int id, int x, int y, int z, int type, int direction, WorldType world) {
        ClientObj obj = new ClientObj();
        obj.id = id;
        obj.x = x;
        obj.y = y;
        obj.z = z;
        obj.type = type;
        obj.direction = direction;
        obj.world = world;
        return obj;
    }
/*

    */
/**
     * Remove (Make sure client/server code matches!)
     *//*

    static {
        if(World.isPVP()) {
            //edgeville misc
            obj(29005, 3077, 3503, 0, 10, 2).remove(); //ugly clue bush
            obj(884, 3084, 3502, 0, 10, 1).remove(); //well
            obj(2771, 3084, 3502, 0, 22, 0).remove(); //well water icon
            obj(1276, 3076, 3516, 0, 11, 0).remove(); //annoying tree lol
            obj(1276, 3082, 3516, 0, 11, 0).remove(); //annoying tree lol
            obj(1278, 3090, 3516, 0, 11, 0).remove(); //annoying tree lol
            obj(1276, 3097, 3516, 0, 10, 0).remove(); //annoying tree lol
            obj(1276, 3103, 3511, 0, 11, 0).remove(); //annoying tree lol
            obj(1276, 3090, 3503, 0, 10, 0).remove(); //annoying tree lol
            obj(27269, 3095, 3502, 0, 0, 1).remove(); //no idea what this even is
            obj(673, 3096, 3504, 0, 22, 0).remove(); //emblem trader icon
            obj(23590, 3121, 3517, 0, 22, 0).remove(); //east achievements icon
            obj(307, 3088, 3509, 0, 10, 0).remove(); //cart
            //edgeville general store
            obj(1823, 3075, 3508, 0, 5, 2).remove();
            obj(1259, 3075, 3509, 0, 22, 0).remove();
            obj(1258, 3075, 3510, 0, 22, 3).remove();
            obj(196, 3076, 3507, 0, 7, 1).remove();
            obj(1823, 3076, 3508, 0, 4, 0).remove();
            obj(1017, 3076, 3509, 0, 10, 0).remove();
            obj(1016, 3076, 3510, 0, 10, 0).remove();
            obj(1015, 3076, 3511, 0, 10, 0).remove();
            obj(1016, 3076, 3512, 0, 10, 0).remove();
            obj(196, 3076, 3513, 0, 7, 2).remove();
            obj(381, 3077, 3507, 0, 10, 3).remove();
            obj(917, 3077, 3511, 0, 22, 3).remove();
            obj(917, 3077, 3512, 0, 22, 0).remove();
            obj(17390, 3077, 3512, 0, 10, 1).remove();
            obj(1018, 3077, 3513, 0, 10, 1).remove();
            obj(1823, 3078, 3506, 0, 5, 1).remove();
            obj(1823, 3078, 3507, 0, 4, 3).remove();
            obj(595, 3078, 3510, 0, 10, 0).remove();
            obj(918, 3078, 3511, 0, 22, 2).remove();
            obj(918, 3078, 3512, 0, 22, 0).remove();
            obj(1017, 3078, 3513, 0, 10, 1).remove();
            obj(205, 3079, 3506, 0, 5, 1).remove();
            obj(381, 3079, 3507, 0, 10, 3).remove();
            obj(918, 3079, 3511, 0, 22, 2).remove();
            obj(918, 3079, 3512, 0, 22, 0).remove();
            obj(1018, 3079, 3513, 0, 10, 1).remove();
            obj(1259, 3079, 3514, 0, 22, 3).remove();
            obj(916, 3080, 3507, 0, 22, 2).remove();
            obj(1536, 3080, 3507, 0, 0, 2).remove();
            obj(2733, 3080, 3509, 0, 22, 0).remove();
            obj(356, 3080, 3510, 0, 10, 0).remove();
            obj(918, 3080, 3511, 0, 22, 2).remove();
            obj(918, 3080, 3512, 0, 22, 0).remove();
            obj(1016, 3080, 3513, 0, 10, 1).remove();
            obj(1258, 3080, 3514, 0, 22, 0).remove();
            obj(196, 3081, 3507, 0, 4, 3).remove();
            obj(595, 3081, 3510, 0, 10, 0).remove();
            obj(918, 3081, 3511, 0, 22, 2).remove();
            obj(918, 3081, 3512, 0, 22, 0).remove();
            obj(887, 3081, 3513, 0, 4, 1).remove();
            obj(1259, 3081, 3514, 0, 22, 0).remove();
            obj(1258, 3082, 3506, 0, 22, 2).remove();
            obj(1823, 3082, 3506, 0, 5, 1).remove();
            obj(1823, 3082, 3507, 0, 4, 3).remove();
            obj(918, 3082, 3511, 0, 22, 2).remove();
            obj(918, 3082, 3512, 0, 22, 0).remove();
            obj(196, 3082, 3513, 0, 4, 1).remove();
            obj(1258, 3083, 3506, 0, 22, 1).remove();
            obj(374, 3083, 3507, 0, 10, 2).remove();
            obj(917, 3083, 3511, 0, 22, 2).remove();
            obj(917, 3083, 3512, 0, 22, 1).remove();
            obj(16683, 3083, 3513, 0, 10, 2).remove();
            obj(196, 3084, 3507, 0, 7, 0).remove();
            obj(1823, 3084, 3508, 0, 4, 2).remove();
            obj(358, 3084, 3509, 0, 10, 2).remove();
            obj(887, 3084, 3509, 0, 4, 2).remove();
            obj(358, 3084, 3510, 0, 10, 2).remove();
            obj(1023, 3084, 3510, 0, 4, 2).remove();
            obj(1023, 3084, 3511, 0, 4, 2).remove();
            obj(357, 3084, 3512, 0, 10, 1).remove();
            obj(1023, 3084, 3512, 0, 4, 2).remove();
            obj(196, 3084, 3513, 0, 7, 3).remove();
            obj(1823, 3085, 3508, 0, 5, 0).remove();
            obj(1258, 3085, 3509, 0, 22, 1).remove();
            obj(1259, 3085, 3510, 0, 22, 0).remove();
            obj(1258, 3085, 3511, 0, 22, 2).remove();

            //edgeville general store (custom wall removals)
            obj(1902, 3085, 3509, 0, 0, 0).remove();
            obj(1902, 3085, 3510, 0, 0, 0).remove();
            obj(1902, 3085, 3511, 0, 0, 0).remove();

            //edgeville house north of bank
            obj(1838, 3090, 3508, 0, 5, 2).remove();
            obj(1838, 3090, 3512, 0, 5, 2).remove();
            obj(362, 3091, 3507, 0, 10, 2).remove();
            obj(362, 3091, 3508, 0, 10, 3).remove();
            obj(1838, 3091, 3508, 0, 4, 0).remove();
            obj(298, 3091, 3510, 0, 10, 1).remove();
            obj(1838, 3091, 3512, 0, 4, 0).remove();
            obj(362, 3091, 3513, 0, 10, 3).remove();
            obj(1258, 3091, 3514, 0, 22, 3).remove();
            obj(1844, 3092, 3506, 0, 5, 1).remove();
            obj(1844, 3092, 3507, 0, 4, 3).remove();
            obj(362, 3092, 3513, 0, 10, 0).remove();
            obj(1838, 3092, 3513, 0, 4, 1).remove();
            obj(1838, 3092, 3514, 0, 5, 3).remove();
            obj(296, 3093, 3507, 0, 10, 2).remove();
            obj(300, 3093, 3509, 0, 10, 1).remove();
            obj(1016, 3093, 3513, 0, 10, 1).remove();
            obj(1258, 3094, 3506, 0, 22, 1).remove();
            obj(1263, 3094, 3511, 0, 22, 3).remove();
            obj(299, 3094, 3513, 0, 10, 0).remove();
            obj(1258, 3094, 3514, 0, 22, 0).remove();
            obj(1259, 3095, 3506, 0, 22, 2).remove();
            obj(1262, 3095, 3510, 0, 22, 0).remove();
            obj(1258, 3095, 3514, 0, 22, 0).remove();
            obj(1259, 3096, 3506, 0, 22, 1).remove();
            obj(1017, 3096, 3507, 0, 10, 3).remove();
            obj(16683, 3096, 3511, 0, 10, 3).remove();
            obj(1015, 3096, 3513, 0, 10, 1).remove();
            obj(1258, 3097, 3506, 0, 22, 3).remove();
            obj(1018, 3097, 3507, 0, 10, 3).remove();
            obj(1263, 3097, 3510, 0, 22, 0).remove();
            obj(1016, 3097, 3513, 0, 10, 1).remove();
            obj(1262, 3097, 3513, 0, 22, 1).remove();
            obj(1258, 3098, 3506, 0, 22, 2).remove();
            obj(1016, 3098, 3507, 0, 10, 3).remove();
            obj(1262, 3098, 3508, 0, 22, 1).remove();
            obj(356, 3098, 3513, 0, 10, 3).remove();
            obj(1259, 3099, 3506, 0, 22, 1).remove();
            obj(1838, 3099, 3506, 0, 5, 1).remove();
            obj(356, 3099, 3507, 0, 10, 0).remove();
            obj(1838, 3099, 3507, 0, 4, 3).remove();
            obj(1262, 3099, 3511, 0, 22, 2).remove();
            obj(357, 3099, 3512, 0, 10, 2).remove();
            obj(357, 3099, 3513, 0, 10, 3).remove();
            obj(1844, 3099, 3513, 0, 4, 1).remove();
            obj(1844, 3099, 3514, 0, 5, 3).remove();
            obj(1259, 3100, 3506, 0, 22, 2).remove();
            obj(358, 3100, 3507, 0, 10, 3).remove();
            obj(356, 3100, 3508, 0, 10, 3).remove();
            obj(1838, 3100, 3508, 0, 4, 2).remove();
            obj(357, 3100, 3512, 0, 10, 1).remove();
            obj(1838, 3100, 3512, 0, 4, 2).remove();
            obj(358, 3100, 3513, 0, 10, 3).remove();
            obj(1259, 3101, 3506, 0, 22, 1).remove();
            obj(1259, 3101, 3507, 0, 22, 0).remove();
            obj(1838, 3101, 3508, 0, 5, 0).remove();
            obj(1521, 3101, 3509, 0, 0, 0).remove();
            obj(1524, 3101, 3510, 0, 0, 0).remove();
            obj(205, 3101, 3511, 0, 5, 0).remove();
            obj(1259, 3101, 3511, 0, 22, 0).remove();
            obj(1259, 3101, 3512, 0, 22, 3).remove();
            obj(1838, 3101, 3512, 0, 5, 0).remove();
            obj(1258, 3101, 3513, 0, 22, 1).remove();

            //edgeville house north of bank (custom wall removals)
            obj(1902, 3090, 3511, 0, 0, 2).remove();
            obj(1902, 3090, 3510, 0, 0, 2).remove();
            obj(1902, 3090, 3509, 0, 0, 2).remove();

            obj(1902, 3094, 3506, 0, 0, 1).remove();
            obj(1902, 3095, 3506, 0, 0, 1).remove();
            obj(1902, 3096, 3506, 0, 0, 1).remove();
            obj(1902, 3097, 3506, 0, 0, 1).remove();

            obj(1902, 3094, 3514, 0, 0, 3).remove();
            obj(1902, 3095, 3514, 0, 0, 3).remove();
            obj(1902, 3096, 3514, 0, 0, 3).remove();
            obj(1902, 3097, 3514, 0, 0, 3).remove();

            //edgeville house west of bank (misc)
            obj(1558, 3080, 3501, 0, 0, 3).remove(); //gate
            obj(1560, 3079, 3501, 0, 0, 3).remove(); //gate
            obj(2772, 3077, 3496, 0, 22, 0).remove(); //cooking icon
            obj(2771, 3077, 3489, 0, 22, 0).remove(); //sink water icon

            //edgeville house west of bank (grass)
            obj(9489, 3076, 3492, 0, 22, 2).remove();
            obj(9489, 3082, 3492, 0, 22, 0).remove();
            obj(9489, 3082, 3494, 0, 22, 0).remove();
            obj(9489, 3077, 3497, 0, 22, 3).remove();
            obj(9488, 3081, 3500, 0, 22, 2).remove();
            obj(9487, 3081, 3499, 0, 22, 2).remove();
            obj(9487, 3077, 3499, 0, 22, 2).remove();
            obj(9487, 3078, 3500, 0, 22, 1).remove();
            obj(9488, 3077, 3500, 0, 22, 3).remove();
            obj(9485, 3078, 3499, 0, 22, 0).remove();
            obj(9491, 3082, 3488, 0, 22, 2).remove();
            obj(9492, 3082, 3487, 0, 22, 3).remove();
            obj(9488, 3076, 3488, 0, 22, 3).remove();
            obj(9488, 3076, 3487, 0, 22, 0).remove();
            obj(9489, 3080, 3497, 0, 22, 3).remove();
            obj(9485, 3077, 3498, 0, 22, 3).remove();
            obj(9485, 3081, 3498, 0, 22, 1).remove();
            obj(9491, 3081, 3487, 0, 22, 3).remove();

            //edgeville house west of bank (1st floor)
            obj(12278, 3076, 3488, 0, 3, 1).remove();
            obj(12278, 3076, 3489, 0, 0, 2).remove();
            obj(12278, 3076, 3490, 0, 0, 2).remove();
            obj(12278, 3076, 3491, 0, 0, 2).remove();
            obj(12278, 3076, 3492, 0, 0, 2).remove();
            obj(12278, 3076, 3493, 0, 0, 2).remove();
            obj(12278, 3076, 3494, 0, 0, 2).remove();
            obj(1838, 3076, 3495, 0, 5, 2).remove();
            obj(12278, 3076, 3495, 0, 0, 2).remove();
            obj(12278, 3076, 3496, 0, 0, 2).remove();
            obj(12278, 3076, 3497, 0, 3, 2).remove();
            obj(12278, 3077, 3488, 0, 0, 1).remove();
            obj(12279, 3077, 3489, 0, 10, 2).remove();
            obj(12291, 3077, 3491, 0, 4, 0).remove();
            obj(12266, 3077, 3493, 0, 22, 3).remove();
            obj(1838, 3077, 3495, 0, 4, 0).remove();
            obj(12269, 3077, 3495, 0, 10, 0).remove();
            obj(12278, 3077, 3497, 0, 0, 3).remove();
            obj(12278, 3078, 3488, 0, 0, 1).remove();
            obj(5610, 3078, 3489, 0, 10, 0).remove();
            obj(12292, 3078, 3489, 0, 4, 3).remove();
            obj(917, 3078, 3491, 0, 22, 3).remove();
            obj(918, 3078, 3492, 0, 22, 3).remove();
            obj(918, 3078, 3493, 0, 22, 3).remove();
            obj(917, 3078, 3494, 0, 22, 0).remove();
            obj(1838, 3078, 3496, 0, 4, 1).remove();
            obj(1838, 3078, 3497, 0, 5, 3).remove();
            obj(12278, 3078, 3497, 0, 0, 3).remove();
            obj(1838, 3079, 3488, 0, 5, 1).remove();
            obj(12278, 3079, 3488, 0, 0, 1).remove();
            obj(1838, 3079, 3489, 0, 4, 3).remove();
            obj(917, 3079, 3491, 0, 22, 2).remove();
            obj(918, 3079, 3492, 0, 22, 1).remove();
            obj(918, 3079, 3493, 0, 22, 1).remove();
            obj(917, 3079, 3494, 0, 22, 1).remove();
            obj(916, 3079, 3496, 0, 22, 0).remove();
            obj(1535, 3079, 3497, 0, 0, 3).remove();
            obj(12278, 3080, 3488, 0, 0, 1).remove();
            obj(5497, 3080, 3489, 0, 10, 1).remove();
            obj(5497, 3080, 3493, 0, 10, 1).remove();
            obj(1838, 3080, 3496, 0, 4, 1).remove();
            obj(1838, 3080, 3497, 0, 5, 3).remove();
            obj(12278, 3080, 3497, 0, 0, 3).remove();
            obj(12278, 3081, 3488, 0, 0, 1).remove();
            obj(12293, 3081, 3490, 0, 10, 1).remove();
            obj(1838, 3081, 3491, 0, 4, 2).remove();
            obj(12281, 3081, 3492, 0, 10, 0).remove();
            obj(12282, 3081, 3494, 0, 10, 2).remove();
            obj(1838, 3081, 3495, 0, 4, 2).remove();
            obj(12278, 3081, 3497, 0, 0, 3).remove();
            obj(12278, 3082, 3488, 0, 3, 0).remove();
            obj(12278, 3082, 3489, 0, 0, 0).remove();
            obj(12278, 3082, 3490, 0, 0, 0).remove();
            obj(1838, 3082, 3491, 0, 5, 0).remove();
            obj(12278, 3082, 3491, 0, 0, 0).remove();
            obj(12278, 3082, 3492, 0, 0, 0).remove();
            obj(12278, 3082, 3493, 0, 0, 0).remove();
            obj(12278, 3082, 3494, 0, 0, 0).remove();
            obj(1838, 3082, 3495, 0, 5, 0).remove();
            obj(12278, 3082, 3495, 0, 0, 0).remove();
            obj(12278, 3082, 3496, 0, 0, 0).remove();
            obj(12278, 3082, 3497, 0, 3, 3).remove();

            //edgeville house west of bank (2nd floor)
            obj(12289, 3076, 3488, 1, 21, 2).remove();
            obj(12287, 3076, 3489, 1, 18, 2).remove();
            obj(12287, 3076, 3490, 1, 18, 2).remove();
            obj(12287, 3076, 3491, 1, 18, 2).remove();
            obj(12287, 3076, 3492, 1, 18, 2).remove();
            obj(12287, 3076, 3493, 1, 18, 2).remove();
            obj(12287, 3076, 3494, 1, 18, 2).remove();
            obj(12287, 3076, 3495, 1, 18, 2).remove();
            obj(12287, 3076, 3496, 1, 18, 2).remove();
            obj(12288, 3076, 3497, 1, 21, 2).remove();
            obj(12285, 3077, 3488, 1, 18, 1).remove();
            obj(12283, 3077, 3489, 1, 12, 2).remove();
            obj(12283, 3077, 3490, 1, 12, 2).remove();
            obj(12283, 3077, 3491, 1, 12, 2).remove();
            obj(12283, 3077, 3492, 1, 12, 2).remove();
            obj(12283, 3077, 3493, 1, 12, 2).remove();
            obj(12283, 3077, 3494, 1, 12, 2).remove();
            obj(12283, 3077, 3495, 1, 12, 2).remove();
            obj(12283, 3077, 3496, 1, 12, 2).remove();
            obj(12283, 3077, 3497, 1, 18, 3).remove();
            obj(12286, 3078, 3488, 1, 18, 1).remove();
            obj(12284, 3078, 3489, 1, 12, 2).remove();
            obj(12284, 3078, 3490, 1, 12, 2).remove();
            obj(12284, 3078, 3491, 1, 12, 2).remove();
            obj(12284, 3078, 3492, 1, 12, 2).remove();
            obj(12284, 3078, 3493, 1, 12, 2).remove();
            obj(12284, 3078, 3494, 1, 12, 2).remove();
            obj(12284, 3078, 3495, 1, 12, 2).remove();
            obj(12284, 3078, 3496, 1, 12, 2).remove();
            obj(12284, 3078, 3497, 1, 18, 3).remove();
            obj(12290, 3079, 3488, 1, 18, 1).remove();
            obj(12284, 3079, 3489, 1, 17, 2).remove();
            obj(12284, 3079, 3490, 1, 17, 2).remove();
            obj(12284, 3079, 3491, 1, 17, 2).remove();
            obj(12284, 3079, 3492, 1, 17, 2).remove();
            obj(12284, 3079, 3493, 1, 17, 2).remove();
            obj(12284, 3079, 3494, 1, 17, 2).remove();
            obj(12284, 3079, 3495, 1, 17, 2).remove();
            obj(12284, 3079, 3496, 1, 17, 2).remove();
            obj(12290, 3079, 3497, 1, 18, 3).remove();
            obj(12284, 3080, 3488, 1, 18, 1).remove();
            obj(12284, 3080, 3489, 1, 12, 0).remove();
            obj(12284, 3080, 3490, 1, 12, 0).remove();
            obj(12284, 3080, 3491, 1, 12, 0).remove();
            obj(12284, 3080, 3492, 1, 12, 0).remove();
            obj(12284, 3080, 3493, 1, 12, 0).remove();
            obj(12284, 3080, 3494, 1, 12, 0).remove();
            obj(12284, 3080, 3495, 1, 12, 0).remove();
            obj(12284, 3080, 3496, 1, 12, 0).remove();
            obj(12286, 3080, 3497, 1, 18, 3).remove();
            obj(12283, 3081, 3488, 1, 18, 1).remove();
            obj(12283, 3081, 3489, 1, 12, 0).remove();
            obj(12283, 3081, 3490, 1, 12, 0).remove();
            obj(12283, 3081, 3491, 1, 12, 0).remove();
            obj(12283, 3081, 3492, 1, 12, 0).remove();
            obj(12283, 3081, 3493, 1, 12, 0).remove();
            obj(12283, 3081, 3494, 1, 12, 0).remove();
            obj(12283, 3081, 3495, 1, 12, 0).remove();
            obj(12283, 3081, 3496, 1, 12, 0).remove();
            obj(12285, 3081, 3497, 1, 18, 3).remove();
            obj(12288, 3082, 3488, 1, 21, 0).remove();
            obj(12287, 3082, 3489, 1, 18, 0).remove();
            obj(12287, 3082, 3490, 1, 18, 0).remove();
            obj(12287, 3082, 3491, 1, 18, 0).remove();
            obj(12287, 3082, 3492, 1, 18, 0).remove();
            obj(12287, 3082, 3493, 1, 18, 0).remove();
            obj(12287, 3082, 3494, 1, 18, 0).remove();
            obj(12287, 3082, 3495, 1, 18, 0).remove();
            obj(12287, 3082, 3496, 1, 18, 0).remove();
            obj(12289, 3082, 3497, 1, 21, 0).remove();

            obj(979, 3081, 3501, 0, 0, 3).remove();
            obj(979, 3082, 3501, 0, 1, 3).remove();
            obj(979, 3082, 3500, 0, 0, 0).remove();
            obj(979, 3082, 3499, 0, 0, 0).remove();
            obj(979, 3082, 3498, 0, 9, 3).remove();
            obj(979, 3083, 3498, 0, 1, 3).remove();
            obj(979, 3078, 3487, 0, 0, 3).remove();
            obj(979, 3077, 3487, 0, 0, 3).remove();
            obj(4339, 3079, 3498, 0, 22, 0).remove();
            obj(4339, 3079, 3497, 0, 22, 0).remove();
            obj(4339, 3080, 3500, 0, 22, 0).remove();
            obj(4339, 3079, 3499, 0, 22, 0).remove();
            obj(4339, 3079, 3500, 0, 22, 0).remove();
            obj(4339, 3079, 3501, 0, 22, 0).remove();
            obj(979, 3076, 3498, 0, 9, 2).remove();
            obj(979, 3076, 3499, 0, 0, 2).remove();
            obj(979, 3076, 3500, 0, 0, 2).remove();
            obj(979, 3076, 3501, 0, 1, 2).remove();
            obj(979, 3077, 3501, 0, 0, 3).remove();
            obj(979, 3078, 3501, 0, 0, 3).remove();
            obj(979, 3083, 3492, 0, 0, 0).remove();
            obj(979, 3083, 3493, 0, 0, 0).remove();
            obj(979, 3083, 3494, 0, 0, 0).remove();
            obj(979, 3083, 3491, 0, 0, 0).remove();
            obj(979, 3083, 3490, 0, 0, 0).remove();
            obj(979, 3083, 3495, 0, 0, 0).remove();
            */
/*obj(4339, 3079, 3498, 0, 22, 1, WorldType.PVP).remove();
            obj(4339, 3079, 3501, 0, 22, 1, WorldType.PVP).remove();
            obj(4340, 3079, 3499, 0, 22, 3, WorldType.PVP).remove();
            obj(4340, 3079, 3500, 0, 22, 3, WorldType.PVP).remove();
            obj(5568, 3083, 3490, 0, 0, 0, WorldType.PVP).remove();
            obj(5567, 3083, 3495, 0, 0, 0, WorldType.PVP).remove();*//*

        } else {
            //edgeville misc
            obj(29005, 3077, 3503, 0, 10, 2, WorldType.ECO).remove(); //ugly clue bush
            obj(884, 3084, 3502, 0, 10, 1, WorldType.ECO).remove(); //well
            obj(2771, 3084, 3502, 0, 22, 0, WorldType.ECO).remove(); //well water icon
            obj(1276, 3076, 3516, 0, 11, 0, WorldType.ECO).remove(); //annoying tree lol
            obj(1276, 3082, 3516, 0, 11, 0, WorldType.ECO).remove(); //annoying tree lol
            obj(1278, 3090, 3516, 0, 11, 0, WorldType.ECO).remove(); //annoying tree lol
            obj(1276, 3097, 3516, 0, 10, 0, WorldType.ECO).remove(); //annoying tree lol
            obj(1276, 3103, 3511, 0, 11, 0, WorldType.ECO).remove(); //annoying tree lol
            obj(1276, 3090, 3503, 0, 10, 0, WorldType.ECO).remove(); //annoying tree lol
            obj(27269, 3095, 3502, 0, 0, 1, WorldType.ECO).remove(); //no idea what this even is
            obj(26756, 3095, 3500, 0, 4, 3, WorldType.ECO).remove(); //kd board
            obj(673, 3096, 3504, 0, 22, 0, WorldType.ECO).remove(); //emblem trader icon
            obj(23590, 3121, 3517, 0, 22, 0, WorldType.ECO).remove(); //east achievements icon
            obj(307, 3088, 3509, 0, 10, 0, WorldType.ECO).remove(); //cart

            //edgeville jail
            obj(997, 3110, 3517, 0, 0, 0, WorldType.ECO).remove();
            obj(1573, 3111, 3514, 0, 0, 3, WorldType.ECO).remove();
            obj(1574, 3111, 3515, 0, 0, 1, WorldType.ECO).remove();
            obj(652, 3108, 3513, 0, 22, 3, WorldType.ECO).remove();
            obj(653, 3110, 3512, 0, 22, 2, WorldType.ECO).remove();
            obj(1902, 3107, 3516, 0, 2, 3, WorldType.ECO).remove();
            obj(653, 3110, 3513, 0, 22, 1, WorldType.ECO).remove();
            obj(662, 3108, 3516, 0, 10, 1, WorldType.ECO).remove();
            obj(997, 3109, 3516, 0, 0, 3, WorldType.ECO).remove();
            obj(997, 3110, 3516, 0, 0, 0, WorldType.ECO).remove();
            obj(1548, 3108, 3516, 0, 0, 3, WorldType.ECO).remove();
            obj(5118, 3109, 3516, 0, 22, 0, WorldType.ECO).remove();
            obj(647, 3107, 3511, 0, 10, 2, WorldType.ECO).remove();



            //edgeville general store
            obj(1823, 3075, 3508, 0, 5, 2, WorldType.ECO).remove();
            obj(1259, 3075, 3509, 0, 22, 0, WorldType.ECO).remove();
            obj(1258, 3075, 3510, 0, 22, 3, WorldType.ECO).remove();
            obj(196, 3076, 3507, 0, 7, 1, WorldType.ECO).remove();
            obj(1823, 3076, 3508, 0, 4, 0, WorldType.ECO).remove();
            obj(1017, 3076, 3509, 0, 10, 0, WorldType.ECO).remove();
            obj(1016, 3076, 3510, 0, 10, 0, WorldType.ECO).remove();
            obj(1015, 3076, 3511, 0, 10, 0, WorldType.ECO).remove();
            obj(1016, 3076, 3512, 0, 10, 0, WorldType.ECO).remove();
            obj(196, 3076, 3513, 0, 7, 2, WorldType.ECO).remove();
            obj(381, 3077, 3507, 0, 10, 3, WorldType.ECO).remove();
            obj(917, 3077, 3511, 0, 22, 3, WorldType.ECO).remove();
            obj(917, 3077, 3512, 0, 22, 0, WorldType.ECO).remove();
            obj(17390, 3077, 3512, 0, 10, 1, WorldType.ECO).remove();
            obj(1018, 3077, 3513, 0, 10, 1, WorldType.ECO).remove();
            obj(1823, 3078, 3507, 0, 4, 3, WorldType.ECO).remove();
            obj(595, 3078, 3510, 0, 10, 0, WorldType.ECO).remove();
            obj(918, 3078, 3511, 0, 22, 2, WorldType.ECO).remove();
            obj(918, 3078, 3512, 0, 22, 0, WorldType.ECO).remove();
            obj(1017, 3078, 3513, 0, 10, 1, WorldType.ECO).remove();
            obj(205, 3079, 3506, 0, 5, 1, WorldType.ECO).remove();
            obj(381, 3079, 3507, 0, 10, 3, WorldType.ECO).remove();
            obj(918, 3079, 3511, 0, 22, 2, WorldType.ECO).remove();
            obj(918, 3079, 3512, 0, 22, 0, WorldType.ECO).remove();
            obj(1018, 3079, 3513, 0, 10, 1, WorldType.ECO).remove();
            obj(1259, 3079, 3514, 0, 22, 3, WorldType.ECO).remove();
            obj(356, 3080, 3510, 0, 10, 0, WorldType.ECO).remove();
            obj(918, 3080, 3511, 0, 22, 2, WorldType.ECO).remove();
            obj(918, 3080, 3512, 0, 22, 0, WorldType.ECO).remove();
            obj(1016, 3080, 3513, 0, 10, 1, WorldType.ECO).remove();
            obj(1258, 3080, 3514, 0, 22, 0, WorldType.ECO).remove();
            obj(196, 3081, 3507, 0, 4, 3, WorldType.ECO).remove();
            obj(595, 3081, 3510, 0, 10, 0, WorldType.ECO).remove();
            obj(918, 3081, 3511, 0, 22, 2, WorldType.ECO).remove();
            obj(918, 3081, 3512, 0, 22, 0, WorldType.ECO).remove();
            obj(887, 3081, 3513, 0, 4, 1, WorldType.ECO).remove();
            obj(1259, 3081, 3514, 0, 22, 0, WorldType.ECO).remove();
            obj(1823, 3082, 3507, 0, 4, 3, WorldType.ECO).remove();
            obj(918, 3082, 3511, 0, 22, 2, WorldType.ECO).remove();
            obj(918, 3082, 3512, 0, 22, 0, WorldType.ECO).remove();
            obj(196, 3082, 3513, 0, 4, 1, WorldType.ECO).remove();
            obj(1258, 3083, 3506, 0, 22, 1, WorldType.ECO).remove();
            obj(374, 3083, 3507, 0, 10, 2, WorldType.ECO).remove();
            obj(917, 3083, 3511, 0, 22, 2, WorldType.ECO).remove();
            obj(917, 3083, 3512, 0, 22, 1, WorldType.ECO).remove();
            obj(16683, 3083, 3513, 0, 10, 2, WorldType.ECO).remove();
            obj(196, 3084, 3507, 0, 7, 0, WorldType.ECO).remove();
            obj(1823, 3084, 3508, 0, 4, 2, WorldType.ECO).remove();
            obj(358, 3084, 3509, 0, 10, 2, WorldType.ECO).remove();
            obj(887, 3084, 3509, 0, 4, 2, WorldType.ECO).remove();
            obj(358, 3084, 3510, 0, 10, 2, WorldType.ECO).remove();
            obj(1023, 3084, 3510, 0, 4, 2, WorldType.ECO).remove();
            obj(1023, 3084, 3511, 0, 4, 2, WorldType.ECO).remove();
            obj(357, 3084, 3512, 0, 10, 1, WorldType.ECO).remove();
            obj(1023, 3084, 3512, 0, 4, 2, WorldType.ECO).remove();
            obj(196, 3084, 3513, 0, 7, 3, WorldType.ECO).remove();
            obj(1823, 3085, 3508, 0, 5, 0, WorldType.ECO).remove();
            obj(1259, 3085, 3510, 0, 22, 0, WorldType.ECO).remove();

            //edgeville house north of bank
            obj(1838, 3090, 3508, 0, 5, 2, WorldType.ECO).remove();
            obj(1838, 3090, 3512, 0, 5, 2, WorldType.ECO).remove();
            obj(362, 3091, 3507, 0, 10, 2, WorldType.ECO).remove();
            obj(362, 3091, 3508, 0, 10, 3, WorldType.ECO).remove();
            obj(1838, 3091, 3508, 0, 4, 0, WorldType.ECO).remove();
            obj(298, 3091, 3510, 0, 10, 1, WorldType.ECO).remove();
            obj(1838, 3091, 3512, 0, 4, 0, WorldType.ECO).remove();
            obj(362, 3091, 3513, 0, 10, 3, WorldType.ECO).remove();
            obj(1258, 3091, 3514, 0, 22, 3, WorldType.ECO).remove();
            obj(1844, 3092, 3506, 0, 5, 1, WorldType.ECO).remove();
            obj(1844, 3092, 3507, 0, 4, 3, WorldType.ECO).remove();
            obj(362, 3092, 3513, 0, 10, 0, WorldType.ECO).remove();
            obj(1838, 3092, 3513, 0, 4, 1, WorldType.ECO).remove();
            obj(1838, 3092, 3514, 0, 5, 3, WorldType.ECO).remove();
            obj(296, 3093, 3507, 0, 10, 2, WorldType.ECO).remove();
            obj(300, 3093, 3509, 0, 10, 1, WorldType.ECO).remove();
            obj(1016, 3093, 3513, 0, 10, 1, WorldType.ECO).remove();
            obj(1258, 3094, 3506, 0, 22, 1, WorldType.ECO).remove();
            obj(1263, 3094, 3511, 0, 22, 3, WorldType.ECO).remove();
            obj(299, 3094, 3513, 0, 10, 0, WorldType.ECO).remove();
            obj(1258, 3094, 3514, 0, 22, 0, WorldType.ECO).remove();
            obj(1259, 3095, 3506, 0, 22, 2, WorldType.ECO).remove();
            obj(1262, 3095, 3510, 0, 22, 0, WorldType.ECO).remove();
            obj(1258, 3095, 3514, 0, 22, 0, WorldType.ECO).remove();
            obj(1259, 3096, 3506, 0, 22, 1, WorldType.ECO).remove();
            obj(1017, 3096, 3507, 0, 10, 3, WorldType.ECO).remove();
            obj(16683, 3096, 3511, 0, 10, 3, WorldType.ECO).remove();
            obj(1015, 3096, 3513, 0, 10, 1, WorldType.ECO).remove();
            obj(1258, 3097, 3506, 0, 22, 3, WorldType.ECO).remove();
            obj(1018, 3097, 3507, 0, 10, 3, WorldType.ECO).remove();
            obj(1263, 3097, 3510, 0, 22, 0, WorldType.ECO).remove();
            obj(1016, 3097, 3513, 0, 10, 1, WorldType.ECO).remove();
            obj(1262, 3097, 3513, 0, 22, 1, WorldType.ECO).remove();
            obj(1258, 3098, 3506, 0, 22, 2, WorldType.ECO).remove();
            obj(1016, 3098, 3507, 0, 10, 3, WorldType.ECO).remove();
            obj(1262, 3098, 3508, 0, 22, 1, WorldType.ECO).remove();
            obj(356, 3098, 3513, 0, 10, 3, WorldType.ECO).remove();
            obj(1259, 3099, 3506, 0, 22, 1, WorldType.ECO).remove();
            obj(1838, 3099, 3506, 0, 5, 1, WorldType.ECO).remove();
            obj(356, 3099, 3507, 0, 10, 0, WorldType.ECO).remove();
            obj(1838, 3099, 3507, 0, 4, 3, WorldType.ECO).remove();
            obj(1262, 3099, 3511, 0, 22, 2, WorldType.ECO).remove();
            obj(357, 3099, 3512, 0, 10, 2, WorldType.ECO).remove();
            obj(357, 3099, 3513, 0, 10, 3, WorldType.ECO).remove();
            obj(1844, 3099, 3513, 0, 4, 1, WorldType.ECO).remove();
            obj(1844, 3099, 3514, 0, 5, 3, WorldType.ECO).remove();
            obj(1259, 3100, 3506, 0, 22, 2, WorldType.ECO).remove();
            obj(358, 3100, 3507, 0, 10, 3, WorldType.ECO).remove();
            obj(356, 3100, 3508, 0, 10, 3, WorldType.ECO).remove();
            obj(1838, 3100, 3508, 0, 4, 2, WorldType.ECO).remove();
            obj(357, 3100, 3512, 0, 10, 1, WorldType.ECO).remove();
            obj(1838, 3100, 3512, 0, 4, 2, WorldType.ECO).remove();
            obj(358, 3100, 3513, 0, 10, 3, WorldType.ECO).remove();
            obj(1259, 3101, 3506, 0, 22, 1, WorldType.ECO).remove();
            obj(1259, 3101, 3507, 0, 22, 0, WorldType.ECO).remove();
            obj(1838, 3101, 3508, 0, 5, 0, WorldType.ECO).remove();
            obj(1521, 3101, 3509, 0, 0, 0, WorldType.ECO).remove();
            obj(1524, 3101, 3510, 0, 0, 0, WorldType.ECO).remove();
            obj(205, 3101, 3511, 0, 5, 0, WorldType.ECO).remove();
            obj(1259, 3101, 3511, 0, 22, 0, WorldType.ECO).remove();
            obj(1259, 3101, 3512, 0, 22, 3, WorldType.ECO).remove();
            obj(1838, 3101, 3512, 0, 5, 0, WorldType.ECO).remove();
            obj(1258, 3101, 3513, 0, 22, 1, WorldType.ECO).remove();

            //edgeville house north of bank (custom wall removals)
            obj(1902, 3094, 3506, 0, 0, 1, WorldType.ECO).remove();
            obj(1902, 3095, 3506, 0, 0, 1, WorldType.ECO).remove();
            obj(1902, 3096, 3506, 0, 0, 1, WorldType.ECO).remove();
            obj(1902, 3097, 3506, 0, 0, 1, WorldType.ECO).remove();

            obj(1902, 3094, 3514, 0, 0, 3, WorldType.ECO).remove();
            obj(1902, 3095, 3514, 0, 0, 3, WorldType.ECO).remove();
            obj(1902, 3096, 3514, 0, 0, 3, WorldType.ECO).remove();
            obj(1902, 3097, 3514, 0, 0, 3, WorldType.ECO).remove();

            //edgeville house west of bank (misc)
            obj(1558, 3080, 3501, 0, 0, 3, WorldType.ECO).remove(); //gate
            obj(1560, 3079, 3501, 0, 0, 3, WorldType.ECO).remove(); //gate
            obj(2772, 3077, 3496, 0, 22, 0, WorldType.ECO).remove(); //cooking icon
            obj(2771, 3077, 3489, 0, 22, 0, WorldType.ECO).remove(); //sink water icon

            //edgeville house west of bank (grass)
            obj(9489, 3076, 3492, 0, 22, 2, WorldType.ECO).remove();
            obj(9489, 3082, 3492, 0, 22, 0, WorldType.ECO).remove();
            obj(9489, 3082, 3494, 0, 22, 0, WorldType.ECO).remove();
            obj(9489, 3077, 3497, 0, 22, 3, WorldType.ECO).remove();
            obj(9488, 3081, 3500, 0, 22, 2, WorldType.ECO).remove();
            obj(9487, 3081, 3499, 0, 22, 2, WorldType.ECO).remove();
            obj(9487, 3077, 3499, 0, 22, 2, WorldType.ECO).remove();
            obj(9487, 3078, 3500, 0, 22, 1, WorldType.ECO).remove();
            obj(9488, 3077, 3500, 0, 22, 3, WorldType.ECO).remove();
            obj(9485, 3078, 3499, 0, 22, 0, WorldType.ECO).remove();

            //edgeville house west of bank (1st floor)
            obj(12278, 3076, 3488, 0, 3, 1, WorldType.ECO).remove();
            obj(12278, 3076, 3489, 0, 0, 2, WorldType.ECO).remove();
            obj(12278, 3076, 3490, 0, 0, 2, WorldType.ECO).remove();
            obj(12278, 3076, 3491, 0, 0, 2, WorldType.ECO).remove();
            obj(12278, 3076, 3492, 0, 0, 2, WorldType.ECO).remove();
            obj(12278, 3076, 3493, 0, 0, 2, WorldType.ECO).remove();
            obj(12278, 3076, 3494, 0, 0, 2, WorldType.ECO).remove();
            obj(1838, 3076, 3495, 0, 5, 2, WorldType.ECO).remove();
            obj(12278, 3076, 3495, 0, 0, 2, WorldType.ECO).remove();
            obj(12278, 3076, 3496, 0, 0, 2, WorldType.ECO).remove();
            obj(12278, 3076, 3497, 0, 3, 2, WorldType.ECO).remove();
            obj(12278, 3077, 3488, 0, 0, 1, WorldType.ECO).remove();
            obj(12279, 3077, 3489, 0, 10, 2, WorldType.ECO).remove();
            obj(12291, 3077, 3491, 0, 4, 0, WorldType.ECO).remove();
            obj(12266, 3077, 3493, 0, 22, 3, WorldType.ECO).remove();
            obj(1838, 3077, 3495, 0, 4, 0, WorldType.ECO).remove();
            obj(12269, 3077, 3495, 0, 10, 0, WorldType.ECO).remove();
            obj(12278, 3077, 3497, 0, 0, 3, WorldType.ECO).remove();
            obj(12278, 3078, 3488, 0, 0, 1, WorldType.ECO).remove();
            obj(5610, 3078, 3489, 0, 10, 0, WorldType.ECO).remove();
            obj(12292, 3078, 3489, 0, 4, 3, WorldType.ECO).remove();
            obj(917, 3078, 3491, 0, 22, 3, WorldType.ECO).remove();
            obj(918, 3078, 3492, 0, 22, 3, WorldType.ECO).remove();
            obj(918, 3078, 3493, 0, 22, 3, WorldType.ECO).remove();
            obj(917, 3078, 3494, 0, 22, 0, WorldType.ECO).remove();
            obj(1838, 3078, 3496, 0, 4, 1, WorldType.ECO).remove();
            obj(1838, 3078, 3497, 0, 5, 3, WorldType.ECO).remove();
            obj(12278, 3078, 3497, 0, 0, 3, WorldType.ECO).remove();
            obj(1838, 3079, 3488, 0, 5, 1, WorldType.ECO).remove();
            obj(12278, 3079, 3488, 0, 0, 1, WorldType.ECO).remove();
            obj(1838, 3079, 3489, 0, 4, 3, WorldType.ECO).remove();
            obj(917, 3079, 3491, 0, 22, 2, WorldType.ECO).remove();
            obj(918, 3079, 3492, 0, 22, 1, WorldType.ECO).remove();
            obj(918, 3079, 3493, 0, 22, 1, WorldType.ECO).remove();
            obj(917, 3079, 3494, 0, 22, 1, WorldType.ECO).remove();
            obj(916, 3079, 3496, 0, 22, 0, WorldType.ECO).remove();
            obj(1535, 3079, 3497, 0, 0, 3, WorldType.ECO).remove();
            obj(12278, 3080, 3488, 0, 0, 1, WorldType.ECO).remove();
            obj(5497, 3080, 3489, 0, 10, 1, WorldType.ECO).remove();
            obj(5497, 3080, 3493, 0, 10, 1, WorldType.ECO).remove();
            obj(1838, 3080, 3496, 0, 4, 1, WorldType.ECO).remove();
            obj(1838, 3080, 3497, 0, 5, 3, WorldType.ECO).remove();
            obj(12278, 3080, 3497, 0, 0, 3, WorldType.ECO).remove();
            obj(12278, 3081, 3488, 0, 0, 1, WorldType.ECO).remove();
            obj(12293, 3081, 3490, 0, 10, 1, WorldType.ECO).remove();
            obj(1838, 3081, 3491, 0, 4, 2, WorldType.ECO).remove();
            obj(12281, 3081, 3492, 0, 10, 0, WorldType.ECO).remove();
            obj(12282, 3081, 3494, 0, 10, 2, WorldType.ECO).remove();
            obj(1838, 3081, 3495, 0, 4, 2, WorldType.ECO).remove();
            obj(12278, 3081, 3497, 0, 0, 3, WorldType.ECO).remove();
            obj(12278, 3082, 3488, 0, 3, 0, WorldType.ECO).remove();
            obj(12278, 3082, 3489, 0, 0, 0, WorldType.ECO).remove();
            obj(12278, 3082, 3490, 0, 0, 0, WorldType.ECO).remove();
            obj(1838, 3082, 3491, 0, 5, 0, WorldType.ECO).remove();
            obj(12278, 3082, 3491, 0, 0, 0, WorldType.ECO).remove();
            obj(12278, 3082, 3492, 0, 0, 0, WorldType.ECO).remove();
            obj(12278, 3082, 3493, 0, 0, 0, WorldType.ECO).remove();
            obj(12278, 3082, 3494, 0, 0, 0, WorldType.ECO).remove();
            obj(1838, 3082, 3495, 0, 5, 0, WorldType.ECO).remove();
            obj(12278, 3082, 3495, 0, 0, 0, WorldType.ECO).remove();
            obj(12278, 3082, 3496, 0, 0, 0, WorldType.ECO).remove();
            obj(12278, 3082, 3497, 0, 3, 3, WorldType.ECO).remove();

            //edgeville house west of bank (2nd floor)
            obj(12289, 3076, 3488, 1, 21, 2, WorldType.ECO).remove();
            obj(12287, 3076, 3489, 1, 18, 2, WorldType.ECO).remove();
            obj(12287, 3076, 3490, 1, 18, 2, WorldType.ECO).remove();
            obj(12287, 3076, 3491, 1, 18, 2, WorldType.ECO).remove();
            obj(12287, 3076, 3492, 1, 18, 2, WorldType.ECO).remove();
            obj(12287, 3076, 3493, 1, 18, 2, WorldType.ECO).remove();
            obj(12287, 3076, 3494, 1, 18, 2, WorldType.ECO).remove();
            obj(12287, 3076, 3495, 1, 18, 2, WorldType.ECO).remove();
            obj(12287, 3076, 3496, 1, 18, 2, WorldType.ECO).remove();
            obj(12288, 3076, 3497, 1, 21, 2, WorldType.ECO).remove();
            obj(12285, 3077, 3488, 1, 18, 1, WorldType.ECO).remove();
            obj(12283, 3077, 3489, 1, 12, 2, WorldType.ECO).remove();
            obj(12283, 3077, 3490, 1, 12, 2, WorldType.ECO).remove();
            obj(12283, 3077, 3491, 1, 12, 2, WorldType.ECO).remove();
            obj(12283, 3077, 3492, 1, 12, 2, WorldType.ECO).remove();
            obj(12283, 3077, 3493, 1, 12, 2, WorldType.ECO).remove();
            obj(12283, 3077, 3494, 1, 12, 2, WorldType.ECO).remove();
            obj(12283, 3077, 3495, 1, 12, 2, WorldType.ECO).remove();
            obj(12283, 3077, 3496, 1, 12, 2, WorldType.ECO).remove();
            obj(12283, 3077, 3497, 1, 18, 3, WorldType.ECO).remove();
            obj(12286, 3078, 3488, 1, 18, 1, WorldType.ECO).remove();
            obj(12284, 3078, 3489, 1, 12, 2, WorldType.ECO).remove();
            obj(12284, 3078, 3490, 1, 12, 2, WorldType.ECO).remove();
            obj(12284, 3078, 3491, 1, 12, 2, WorldType.ECO).remove();
            obj(12284, 3078, 3492, 1, 12, 2, WorldType.ECO).remove();
            obj(12284, 3078, 3493, 1, 12, 2, WorldType.ECO).remove();
            obj(12284, 3078, 3494, 1, 12, 2, WorldType.ECO).remove();
            obj(12284, 3078, 3495, 1, 12, 2, WorldType.ECO).remove();
            obj(12284, 3078, 3496, 1, 12, 2, WorldType.ECO).remove();
            obj(12284, 3078, 3497, 1, 18, 3, WorldType.ECO).remove();
            obj(12290, 3079, 3488, 1, 18, 1, WorldType.ECO).remove();
            obj(12284, 3079, 3489, 1, 17, 2, WorldType.ECO).remove();
            obj(12284, 3079, 3490, 1, 17, 2, WorldType.ECO).remove();
            obj(12284, 3079, 3491, 1, 17, 2, WorldType.ECO).remove();
            obj(12284, 3079, 3492, 1, 17, 2, WorldType.ECO).remove();
            obj(12284, 3079, 3493, 1, 17, 2, WorldType.ECO).remove();
            obj(12284, 3079, 3494, 1, 17, 2, WorldType.ECO).remove();
            obj(12284, 3079, 3495, 1, 17, 2, WorldType.ECO).remove();
            obj(12284, 3079, 3496, 1, 17, 2, WorldType.ECO).remove();
            obj(12290, 3079, 3497, 1, 18, 3, WorldType.ECO).remove();
            obj(12284, 3080, 3488, 1, 18, 1, WorldType.ECO).remove();
            obj(12284, 3080, 3489, 1, 12, 0, WorldType.ECO).remove();
            obj(12284, 3080, 3490, 1, 12, 0, WorldType.ECO).remove();
            obj(12284, 3080, 3491, 1, 12, 0, WorldType.ECO).remove();
            obj(12284, 3080, 3492, 1, 12, 0, WorldType.ECO).remove();
            obj(12284, 3080, 3493, 1, 12, 0, WorldType.ECO).remove();
            obj(12284, 3080, 3494, 1, 12, 0, WorldType.ECO).remove();
            obj(12284, 3080, 3495, 1, 12, 0, WorldType.ECO).remove();
            obj(12284, 3080, 3496, 1, 12, 0, WorldType.ECO).remove();
            obj(12286, 3080, 3497, 1, 18, 3, WorldType.ECO).remove();
            obj(12283, 3081, 3488, 1, 18, 1, WorldType.ECO).remove();
            obj(12283, 3081, 3489, 1, 12, 0, WorldType.ECO).remove();
            obj(12283, 3081, 3490, 1, 12, 0, WorldType.ECO).remove();
            obj(12283, 3081, 3491, 1, 12, 0, WorldType.ECO).remove();
            obj(12283, 3081, 3492, 1, 12, 0, WorldType.ECO).remove();
            obj(12283, 3081, 3493, 1, 12, 0, WorldType.ECO).remove();
            obj(12283, 3081, 3494, 1, 12, 0, WorldType.ECO).remove();
            obj(12283, 3081, 3495, 1, 12, 0, WorldType.ECO).remove();
            obj(12283, 3081, 3496, 1, 12, 0, WorldType.ECO).remove();
            obj(12285, 3081, 3497, 1, 18, 3, WorldType.ECO).remove();
            obj(12288, 3082, 3488, 1, 21, 0, WorldType.ECO).remove();
            obj(12287, 3082, 3489, 1, 18, 0, WorldType.ECO).remove();
            obj(12287, 3082, 3490, 1, 18, 0, WorldType.ECO).remove();
            obj(12287, 3082, 3491, 1, 18, 0, WorldType.ECO).remove();
            obj(12287, 3082, 3492, 1, 18, 0, WorldType.ECO).remove();
            obj(12287, 3082, 3493, 1, 18, 0, WorldType.ECO).remove();
            obj(12287, 3082, 3494, 1, 18, 0, WorldType.ECO).remove();
            obj(12287, 3082, 3495, 1, 18, 0, WorldType.ECO).remove();
            obj(12287, 3082, 3496, 1, 18, 0, WorldType.ECO).remove();
            obj(12289, 3082, 3497, 1, 21, 0, WorldType.ECO).remove();
        }
    }

    */
/**
     * Spawn (Make sure client/server code matches!)
     *//*


    static {
        if(World.isPVP()) {
            //misc
            obj(6045, 3285, 9442, 0, 10, 0).spawn(); //jail mine cart

            //edgeville misc
            obj(864, 3091, 3487, 0, 10, 0).spawn(); //dummy standard
            obj(864, 3098, 3487, 0, 10, 0).spawn(); //dummy standard

            obj(4483, 3099, 3514, 0, 10, 0).spawn(); //wildy edge bank

            obj(4483, 3092, 3514, 0, 10, 0).spawn(); //wildy edge bank

            //edgeville general store
            obj(1902, 3080, 3506, 0, 0, 1).spawn(); //door space to wall
            obj(11508, 3090, 3509, 0, 0, 2).spawn(); //curtain
            obj(11509, 3090, 3511, 0, 0, 2).spawn(); //curtain
            for (int y = 3507; y <= 3513; y++)
                obj(24401, 3077, y, 0, 0, 2).spawn(); //shop ropes
            spawnRedCarpet(3078, 3509, 3084, 3511);

            obj(31379, 3083, 3507, 0, 11, 0).spawn(); //donation table
            //obj(29226, 3079, 3507, 0, 10, 3).spawn(); //pet list

            obj(40009, 3083, 3512, 0, 11, 0).spawn(); //max cape stand
            obj(3624, 3080, 3513, 0, 10, 0).spawn(); //mannequin™

            //edgeville house north of bank
            obj(1902, 3101, 3509, 0, 0, 0).spawn(); //door space to wall
            obj(1902, 3101, 3510, 0, 0, 0).spawn(); //door space to wall
            obj(11508, 3085, 3511, 0, 0, 0).spawn(); //curtain
            obj(11509, 3085, 3509, 0, 0, 0).spawn(); //curtain

            spawnRedCarpet(3091, 3509, 3099, 3511);

            obj(11509, 3094, 3506, 0, 0, 1).spawn(); //curtain
            obj(11508, 3097, 3506, 0, 0, 1).spawn(); //curtain
            obj(25732, 3098, 3507, 0, 10, 3).spawn(); //dragon head
            obj(25732, 3093, 3507, 0, 10, 3).spawn(); //dragon head

            obj(11508, 3094, 3514, 0, 0, 3).spawn(); //curtain
            obj(11509, 3097, 3514, 0, 0, 3).spawn(); //curtain
            obj(31380, 3095, 3513, 0, 10, 0).spawn(); //rejuv pool
            obj(25732, 3098, 3513, 0, 10, 1).spawn(); //dragon head
            obj(25732, 3093, 3513, 0, 10, 1).spawn(); //dragon head

            obj(202, 3100, 3512, 0, 10, 1).spawn(); //candles
            obj(18258, 3100, 3509, 0, 10, 2).spawn(); //altar
            obj(202, 3100, 3508, 0, 10, 1).spawn(); //candles

            obj(32758, 3092, 3507, 0, 10, 2).spawn(); //loyalty chest
            obj(30816, 3091, 3507, 0, 10, 0).spawn(); //boxes

            obj(4483, 3092, 3513, 0, 10, 2).spawn(); //bank chest
            obj(30816, 3091, 3513, 0, 10, 1).spawn(); //boxes

            //edgeville house west of bank (default)
            obj(979, 3082, 3487, 0, 0, 1).spawn(); //wall
            obj(979, 3083, 3487, 0, 3, 0).spawn(); //wall
            obj(979, 3081, 3487, 0, 0, 1).spawn(); //wall
            obj(979, 3080, 3487, 0, 0, 1).spawn(); //wall
            obj(979, 3079, 3487, 0, 0, 1).spawn(); //wall
            obj(979, 3078, 3487, 0, 0, 1).spawn(); //wall
            obj(979, 3077, 3487, 0, 0, 1).spawn(); //wall

            obj(979, 3076, 3498, 0, 0, 3).spawn(); //wall
            obj(979, 3077, 3498, 0, 0, 3).spawn(); //wall
            obj(979, 3078, 3498, 0, 0, 3).spawn(); //wall
            obj(979, 3079, 3498, 0, 0, 3).spawn(); //wall
            obj(979, 3080, 3498, 0, 0, 3).spawn(); //wall
            obj(979, 3081, 3498, 0, 0, 3).spawn(); //wall
            obj(979, 3082, 3498, 0, 0, 3).spawn(); //wall
            obj(979, 3083, 3498, 0, 3, 3).spawn(); //wall
            obj(5568, 3083, 3489, 0, 0, 0).spawn();
            obj(5567, 3083, 3496, 0, 0, 0).spawn();
            obj(31846, 3083, 3495, 0, 10, 1).spawn();
            obj(26747, 3083, 3490, 0, 10, 0).spawn();
            obj(6882, 3082, 3493, 0, 10, 0).spawn(); //crushed spears
            obj(6882, 3082, 3492, 0, 10, 0).spawn(); //crushed spears

            obj(5568, 3083, 3490, 0, 0, 0).spawn();
            obj(5567, 3083, 3495, 0, 0, 0).spawn();

            //edgeville house west of bank (PVP)
            obj(738, 3079, 3492, 0, 22, 0).spawn(); //minigame icon
            obj(6415, 3076, 3488, 0, 10, 0).spawn(); //torch
            obj(6415, 3076, 3497, 0, 10, 0).spawn(); //torch
            obj(29065, 3083, 3494, 0, 22, 0).spawn(); //Coffer
            obj(29087, 3083, 3494, 0, 10, 3).spawn(); //Coffer

            for (int x = 3077; x <= 3081; x++) {
                obj(5860, x, 3497, 0, 10, 3).spawn(); //spears (north)
                obj(5860, x, 3488, 0, 10, 1).spawn(); //spears (south)
            }
            for (int y = 3489; y <= 3496; y++) {
                obj(5860, 3076, y, 0, 10, 2).spawn(); //spears (west)
            }
            for (int y = 3489; y <= 3496; y++) {
                if (y != 3492 && y != 3493) obj(5860, 3082, y, 0, 10, 0).spawn(); //spears (east)
            }
            obj(28925, 3091, 3506, 0, 10, 0).spawn(); //Fun PVP portal
            obj(31618, 3079, 3506, 0, 10, 0).spawn(); //Donator instance portal
            //thieving stalls
           */
/* obj(6163, 3175, 3943, 0, 10, 2, WorldType.PVP).spawn();
            obj(6165, 3177, 3943, 0, 10, 2, WorldType.PVP).spawn();
            obj(6164, 3179, 3943, 0, 10, 2, WorldType.PVP).spawn();
            obj(6162, 3181, 3943, 0, 10, 2, WorldType.PVP).spawn();*//*

        } else {
            //edgeville house west of bank (ECO)
            obj(884, 3077, 3499, 0, 11, 1, WorldType.ECO).spawn(); //well
            obj(29005, 3081, 3500, 0, 10, 0, WorldType.ECO).spawn(); //clue bush
            obj(15531, 3079, 3489, 0, 22, 0, WorldType.ECO).spawn(); //house portal icon
            obj(15478, 3077, 3488, 0, 10, 0, WorldType.ECO).spawn(); //house portal
            obj(15468, 3076, 3497, 0, 10, 0, WorldType.ECO).spawn(); //sawmill
            obj(7165, 3076, 3493, 0, 10, 3, WorldType.ECO).spawn(); //bench
            obj(1189, 3076, 3492, 0, 10, 0, WorldType.ECO).spawn(); //daisies
            obj(7165, 3076, 3490, 0, 10, 3, WorldType.ECO).spawn(); //bench
            obj(15465, 3082, 3497, 0, 10, 1, WorldType.ECO).spawn(); //display board
            obj(7165, 3082, 3493, 0, 10, 1, WorldType.ECO).spawn(); //bench
            obj(1189, 3082, 3492, 0, 10, 0, WorldType.ECO).spawn(); //daisies
            obj(7165, 3082, 3490, 0, 10, 1, WorldType.ECO).spawn(); //bench
            obj(25824, 3104, 3497, 0, 10, 1, WorldType.ECO).spawn(); //spinning wheel
            for (int y = 3507; y <= 3513; y++)
                obj(24401, 3076, y, 0, 0, 2).spawn(); //shop ropes
            for (int y = 3507; y <= 3513; y++)
                obj(24401, 3083, y, 0, 0, 2).spawn(); //shop ropes
            spawnRedCarpet(3077, 3509, 3083, 3511);

            obj(32758, 3090, 3499, 0, 10, 1).spawn(); //loyalty chest
            obj(30816, 3091, 3507, 0, 10, 0, WorldType.ECO).spawn(); //boxes
            obj(2031, 3106, 3501, 0, 10, 2, WorldType.ECO).spawn(); //anvil

            obj(4483, 3092, 3513, 0, 10, 2, WorldType.ECO).spawn(); //bank chest
            obj(30816, 3091, 3513, 0, 10, 1, WorldType.ECO).spawn(); //boxes

            //edgeville jail
            obj(1902, 3107, 3516, 0, 0, 0, WorldType.ECO).spawn();
            obj(40008, 3112, 3514, 0, 10, 0, WorldType.ECO).spawn();

            //edgeville house west of bank (default)
            obj(979, 3079, 3487, 0, 0, 3, WorldType.ECO).spawn(); //wall
            obj(979, 3080, 3487, 0, 0, 3, WorldType.ECO).spawn(); //wall
            obj(979, 3081, 3487, 0, 0, 3, WorldType.ECO).spawn(); //wall
            obj(23590, 3069, 3517, 0, 22, 0, WorldType.ECO).spawn(); //achievements icon
           */
/* obj(864, 3091, 3487, 0, 10, 0, WorldType.PVP).spawn(); //dummy standard
            obj(864, 3098, 3487, 0, 10, 0, WorldType.PVP).spawn(); //dummy standard
            obj(2744, 3097, 3485, 0, 22, 0, WorldType.PVP).spawn(); //dummy icon*//*



            //edgeville house north of bank
            obj(1902, 3101, 3509, 0, 0, 0, WorldType.ECO).spawn(); //door space to wall
            obj(1902, 3101, 3510, 0, 0, 0, WorldType.ECO).spawn(); //door space to wall

            obj(172, 3091, 3506, 0, 10, 2, WorldType.ECO).spawn(); //Crystal chest

            obj(26747, 3112, 3516, 0, 10, 0, WorldType.ECO).spawn(); //viewing orb for tournament

            spawnRedCarpet(3091, 3509, 3099, 3511);

            obj(11509, 3094, 3506, 0, 0, 1, WorldType.ECO).spawn(); //curtain
            obj(11508, 3097, 3506, 0, 0, 1, WorldType.ECO).spawn(); //curtain
            obj(25732, 3098, 3507, 0, 10, 3, WorldType.ECO).spawn(); //dragon head
            obj(25732, 3093, 3507, 0, 10, 3, WorldType.ECO).spawn(); //dragon head

            obj(11508, 3094, 3514, 0, 0, 3, WorldType.ECO).spawn(); //curtain
            obj(11509, 3097, 3514, 0, 0, 3, WorldType.ECO).spawn(); //curtain
            obj(31380, 3095, 3513, 0, 10, 0, WorldType.ECO).spawn(); //rejuv pool
            obj(25732, 3098, 3513, 0, 10, 1, WorldType.ECO).spawn(); //dragon head
            obj(25732, 3093, 3513, 0, 10, 1, WorldType.ECO).spawn(); //dragon head

            obj(202, 3100, 3512, 0, 10, 1, WorldType.ECO).spawn(); //candles
            obj(18258, 3100, 3509, 0, 10, 2, WorldType.ECO).spawn(); //altar
            obj(202, 3100, 3508, 0, 10, 1, WorldType.ECO).spawn(); //candles
            obj(6045, 3285, 9442, 0, 10, 0).spawn(); //jail mine cart

            obj(23590, 3069, 3517, 0, 22, 0, WorldType.ECO).spawn(); //achievements icon
            //stalls
            obj(6163, 3083, 3487, 0, 10, 1, WorldType.ECO).spawn(); //baker stall
            obj(6165, 3083, 3490, 0, 10, 1, WorldType.ECO).spawn(); //cloth stall
            obj(6164, 3083, 3493, 0, 10, 1, WorldType.ECO).spawn(); //silver stall
            obj(6162, 3083, 3496, 0, 10, 1, WorldType.ECO).spawn(); //gem stall

            obj(5623, 3087, 3261, 0, 10, 1, WorldType.ECO).spawn(); //gem stall

            //donator zone
                obj(40004, 3164, 3489, 2, 10, 1, WorldType.ECO).spawn(); //rejuvination pool



            //DMM chest

            obj(27290, 3091, 3514, 0, 10, 2, WorldType.ECO).spawn();

            obj(4483, 3372, 3271, 0, 10, 2, WorldType.ECO).spawn(); //bank chest duel arena

            obj(4483, 3080, 3514, 0, 10, 0, WorldType.ECO).spawn(); //bank chest edge

            obj(3192, 3084, 3514, 0, 10, 0, WorldType.ECO).spawn(); //pvp leaderboard edge

            obj(29716, 3090, 3494, 0, 11, 2).remove(); //edge bank chair
            obj(1088, 3090, 3496, 0, 11, 3).remove(); //edge bank chair
            obj(1088, 3092, 3496, 0, 11, 0).remove(); //edge bank chair
            obj(598, 3091, 3495, 0, 10, 3).remove(); //edge bank table obj
            obj(2095, 3090, 3494, 0, 10, 0, WorldType.ECO).spawn(); //edge bank bar
            obj(24875, 3090, 3496, 0, 10, 0, WorldType.ECO).spawn(); //edge bank coins
        }
    }
*/

    private static void spawnRedCarpet(int swX, int swY, int neX, int neY) {
        for(int x = swX; x <= neX; x++) {
            for(int y = swY; y <= neY; y++) {
                if (x == swX) {
                    if (y == swY)
                        obj(6812, x, y, 0, 22, 3, WorldType.ECO).spawn();
                    else if (y == neY)
                        obj(6812, x, y, 0, 22, 0, WorldType.ECO).spawn();
                    else
                        obj(6813, x, y, 0, 22, 3, WorldType.ECO).spawn();
                } else if (x == neX) {
                    if (y == swY)
                        obj(6812, x, y, 0, 22, 2, WorldType.ECO).spawn();
                    else if (y == neY)
                        obj(6812, x, y, 0, 22, 1, WorldType.ECO).spawn();
                    else
                        obj(6813, x, y, 0, 22, 1, WorldType.ECO).spawn();
                } else {
                    if (y == swY)
                        obj(6813, x, y, 0, 22, 2, WorldType.ECO).spawn();
                    else if (y == neY)
                        obj(6813, x, y, 0, 22, 0, WorldType.ECO).spawn();
                    else
                        obj(6814, x, y, 0, 22, 0, WorldType.ECO).spawn();
                }
            }
        }
    }

/*    *//**
     * Misc
     *//*

    static {
        if(World.isPVP()) {
            BankActions.markTiles(Tile.getObject(4483, 3099, 3514, 0));
            BankActions.markTiles(Tile.getObject(4483, 3092, 3514, 0));
        }
        *//**
         * The ropes used to separate shops causes reach issues, here's a cheap fix!
         *//*

        //West side
        for(int y = 3508; y <= 3513; y++) {
            Tile tile = Tile.get(3076, y, 0, true);
            tile.clipping = 0;
            tile.flagUnmovable();
        }

    }*/

}