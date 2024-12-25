package io.ruin.model.map;

import io.ruin.model.activities.bosses.zulrah.Zulrah;
import io.ruin.model.activities.wilderness.BloodyChest;

public class MultiZone {

    /**
     * Adding
     */

    public static void add(int x, int y, int z) {
        set(x, y, z, true);
    }

    public static void add(Bounds... bounds) {
        set(true, bounds);
    }

    /**
     * Removing
     */

    public static void remove(int x, int y, int z) {
        set(x, y, z, false);
    }

    public static void remove(Bounds... bounds) {
        set(false, bounds);
    }

    /**
     * Setting
     */

    private static void set(boolean multi, Bounds... bounds) {
        for(Bounds b : bounds) {
            for(int x = b.swX; x <= b.neX; x++) {
                for(int y = b.swY; y <= b.neY; y++) {
                    if(b.z == -1) {
                        /**
                         * All heights
                         */
                        for(int z = 0; z < 4; z++)
                            set(x, y, z, multi);
                    } else {
                        /**
                         * Fixed height
                         */
                        set(x, y, b.z, multi);
                    }
                }
            }
        }
    }

    public static void set(int x, int y, int z, boolean multi) {
        Tile.get(x, y, z, true).multi = multi;
    }

    /**
     * Loading
     */

    public static void load() {
        /**
         * By regions
         */
        int[] regions = {
                /** Safe: **/
                11827, 11828, 11829, //Falador
                12341, //Barbarian Village
                8253, 8252, 8508, 8509, 8254, //Lunar Isle:
                9273, 9017, //Piscatoris Fishing Colony
                9532, 9276, //Fremennik Isles
                10809, 10810, 10554, //Relleka
                10549, //Ranging Guild
                10034, //Battlefield
                10029, //Feldip hills
                11318, //White wolf mountain
                11575, //Burthope
                11577, 11578, //Trollheim
                11050, 11051, 10794, 10795,//Apeatoll
                12590, //Bandit camp
                13105, //Al Kharid
                12337, //Wizards tower
                12338, //Draynor Village
                11602, 11603, 11346, 11347, //Godwars Dungeon
                13131, 13387, //FFA clan wars, top half
                11844, //Corporeal beast
                11589, 11588, //Dagannoths
                5690, 5689, //Zeah lizanman pit
                14682, //Kraken cave
                8023, //Gnome Stronghold crash site (monkey madness)
                13972, // Kalphite queen lair
                13204, 13205, // Kalphite cave
                12363, 12362, 12106, 11851, 11850, // Abyssal Sire
                14938, 14939, // Smokedevil room in Nieve's cave + kalphite hive room
                9023, // vorkath island
                12889, // olm chamber

                /** Wildy: (uses 8x8 chunks for some sections as well as chunks) **/
                12599, 12600, //Wilderness Ditch
                12855, 12856, //Mammoths (lvl 9)
                13111, 13112, 13113, 13114, 13115, 13116, 13117, //Varrock -> GDZ
                12857, 12858, 12859,12860,12861, //East graveyard (lvl 17)
                13372, 13373, //East of Callisto (lvl 41)
                12604, //Black chins (lvl 33)
                12348, //Wildy GWD & Center wildy north of lava maze
                12088, 12089, //North of dark warriors (lvl 17)
                12961, //Scorpia pit
                9033, // KBD zone
                9551, //Fight caves
                9043, //Inferno
                12107, //Abyss
                9619, //Smoke devil dungeon
                12960, 12958, 12957,
                6810, // Skotizo lair
                10536, // Pest Control battlegrounds
        };
        for(int regionId : regions)
            set(true, Bounds.fromRegion(regionId));
            // Lava maze dungeon (for the bloody chest)
            set(true, BloodyChest.BLOODY_DUNGEON);
        /*
         * By chunks
         */
        int[] chunks = {
                // Chaos temple - Crazy Arch 44s
                24117724, 24117725, 24117726,
                24183260, 24183261, 24183262,

                //Black chins
                25756120, 25756121, 25756122, 25756123, 25756124, 25756125, 25756126, 25756127,
                25821656, 25821657, 25821658, 25821659, 25821660, 25821661, 25821662, 25821663,
                25887192, 25887193, 25887194, 25887195, 25887196, 25887197, 25887198, 25887199,
                25952728, 25952729, 25952730, 25952731, 25952732, 25952733, 25952734, 25952735,
                26018264, 26018265, 26018266, 26018267, 26018268, 26018269, 26018270, 26018271,
                26083800, 26083801, 26083802, 26083803, 26083804, 26083805, 26083806, 26083807,
                26149336, 26149337, 26149338, 26149339, 26149340, 26149341, 26149342, 26149343,


                //KBD Cage
                24642018, 24642019, 24642020, 24642021, 24642022, 24642023,
                24707554, 24707555, 24707556, 24707557, 24707558, 24707559,
                24773090, 24773091, 24773092, 24773093, 24773094, 24773095,
                24838626, 24838627, 24838628, 24838629, 24838630, 24838631,
                24904162, 24904163, 24904164, 24904165, 24904166, 24904167,

                //Rune rocks north of KBD cage
                24969699, 24969700, 24969702, 24969703, 25035238, 25035239,
                25100774, 25100775,

                // Wilderness agility course at 55 wilderness
                24445417, 24510953, 24576489,
                24445418, 24510954, 24576490,
                24445419
        };
        for(int chunk : chunks) {
            int chunkAbsX = (chunk >> 16) << 3;
            int chunkAbsY = (chunk & 0xffff) << 3;
            set(true, new Bounds(chunkAbsX, chunkAbsY, chunkAbsX + 7, chunkAbsY + 7, 0));
        }
        /**
         * By bounds
         */
        Bounds[] bounds = {
            /* wilderness agility area */
            new Bounds(2984, 3912, 3007, 3927, 0),
            /* waterbirth dungeon */
            new Bounds(2433, 10115, 2560, 10177, 0),
            new Bounds(1792, 4330, 1984, 4452, 0),
            new Bounds(1792, 4330, 1984, 4452, 1),
            new Bounds(1792, 4330, 1984, 4452, 2),
            new Bounds(1792, 4330, 1984, 4452, 3),

            /* catacombs of kourend */
            new Bounds(1598, 9963, 1766, 10067, -1),
            new Bounds(1638, 10067, 1737, 10111, -1),

            /* Kraken boss room */
            new Bounds(2270, 10019, 2293, 10045, -1),

            /* Zulrah arena */
            Zulrah.SHRINE_BOUNDS,

            /* Wilderness Godwars */
            new Bounds(3013, 10108, 3078, 10177, 0),

            /* Raids source area */
            new Bounds(3264, 5152, 3400, 5727, -1),

            /* Revs caves */
            new Bounds(3233, 10229, 3235, 10231, -1),
            new Bounds(3136, 10061, 3263, 10228, -1),
            new Bounds(3208, 10048, 3263, 10082, -1),

            new Bounds(1357, 10193, 1378, 10220, 1)
        };
        for(Bounds b : bounds)
            set(true, b);
    }

}
