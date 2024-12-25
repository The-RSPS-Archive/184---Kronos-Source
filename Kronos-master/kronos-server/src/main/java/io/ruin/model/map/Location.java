package io.ruin.model.map;

//this class is just for admin tele command lol
public enum Location {

    EDGEVILLE(3087, 3503, 0, "Edgeville", "edge"),
    KARAMJA(2918, 3176, 0, "Karamja"),
    DRAYNOR(3105, 3251, 0, "Draynor Village", "draynor"),
    VARROCK(3212, 3423, 0, "Varrock"),
    AL_KHARID(3293, 3163, 0, "Al Kharid"),
    AL_KHARID_DUEL_ARENA(3367, 3266, 0, "Al Kharid Duel Arena", "Duel", "Duel arena", "arena", "duelarena"),
    CLAN_WARS_ARENA(3388, 3160, 0, "Clan Wars Arena", "Clan wars"),
    CASTLE_WARS_ARENA(2440, 3090, 0, "Castle Wars Arena", "Castle wars"),
    CHAOS_TEMPLE(3236, 3635, 0, "Chaos Temple"),
    BANDIT_CAMP(3037, 3651, 0, "Bandit Camp"),
    LAVA_MAZE(3030, 3841, 0, "Lava Maze"),
    ESSENCE(2911, 4830, 0, "Essence", "Ess"),
    WILDERNESS_RESOURCE_AREA(3188, 3946, 0, "Wilderness resource area", "Wilderness resource", "ra", "resource area"),
    WARRIORS_GUILD(2884, 3547, 0, "Warriors' Guild", "warrior guild", "warriors guild"),
    CHAMPIONS_GUILD(3190, 3368, 0, "Champions' Guild", "champions guild", "champion guild"),
    MONASTERY(3052, 3489, 0, "Monastery"),
    RANGING_GUILD(2655, 3440, 0, "Ranging Guild"),
    DIGSITE(3342, 3447, 0, "Digsite"),
    BURTHOPE(2900, 3552, 0, "Burthope"),
    BARBARIAN_OUTPOST(2519, 3572, 0, "Barbarian Outpost"),
    CORPOREAL_BEAST(2966, 4382, 2, "Corporeal Beast"),
    TEARS_OF_GUTHIX(3244, 9503, 2, "Tears of Guthix"),
    WINTERTODT_CAMP(1627, 3941, 0, "Wintertodt Camp"),
    WIZARDS_TOWER(3113, 3176, 0, "Wizard's Tower"),
    THE_OUTPOST(2425, 3349, 0, "The Outpost"),
    EAGLES_EYRIE(3406, 3157, 0, "Eagle's Eyrie"),
    MISCELLANIA(2564, 3847, 0, "Miscellania"),
    GRAND_EXCHANGE_ENTRANCE(3164, 3464, 0, "Grand Exchange Entrance"),
    FALADOR_PARK(3002, 3377, 0, "Falador Park"),
    KALDAGRIM(2838, 10127, 0, "Dondakan (Kaldagrim)"),
    FOUNTAIN_OF_RUNE(3376, 3891, 0, "Fountain of rune", "Fountain of runes"),
    FISHING_GUILD(2614, 3391, 0, "Fishing Guild"),
    MINING_GUILD(3050, 9766, 0, "Mining Guild"),
    CRAFTING_GUILD(2933, 3293, 0, "Crafting Guild"),
    COOKING_GUILD(3144, 3442, 0, "Cooking Guild"),
    WOODCUTTING_GUILD(1660, 3506, 0, "Woodcutting Guild"),
    SLAYER_TOWER(3429, 3534, 0, "Slayer Tower"),
    FREMENNIK_SLAYER_DUNGEON(2794, 3615, 0, "Fremennik Slayer Dungeon"),
    STRONGHOLD_SLAYER_CAVE(2434, 3423, 0, "Stronghold Slayer Cave"),
    DARK_BEASTS(2026, 4636, 0, "Dark Beasts"),
    CANIFIS(3496, 3489, 0, "Canifis"),
    MAGEBANK(3101, 3961, 0, "mage bank", "mb", "magebank"),
    KBD(3005, 3849, 0, "kbd", "king black dragon"),
    WILDERNESS_GODWARS(3064, 10155, 3, "wilderness godwars"),
    CAMELOT(2757, 3479, 0, "camelot", "cammy"),
    ROGUES_CHEST(3291, 3940, 0, "rogues chest", "rogues castle"),
    ARDOUGNE(2662, 3307, 0, "ardougne", "ardy"),
    FALADOR(2965, 3380, 0, "falador", "fally"),
    ROGUES_DEN(3054, 4980, 1, "rogues den", "wall safe", "wall safes"),
    LUMBRIDGE(3222, 3218, 0, "lumbridge", "lumby", "lumb"),
    CATHERBY(2804, 3433, 0, "catherby", "cath"),
    ANGLER_FISH(1829, 3782, 0, "angler", "anglerfish", "angler fish", "angler fishing"),
    MINNOWS(2614, 3440, 0, "minnows", "minnow", "minnow fishing"),
    BARB_FISHING(2498, 3504, 0, "barbarian fishing", "barb fishing"),
    DESERT_QUARRY(3170, 2913, 0, "granite", "sandstone"),
    PURO_PURO(2594, 4321, 0, "puro", "puro puro", "puropuro"),
    ZANARIS(2432, 4457, 0, "zanaris"),
    MOTHERLODE_MINE(3751, 5669, 0, "motherlode mine", "mlm"),
    BARROWS(3565, 3313, 0, "barrows", "barrow"),
    TZHAAR(2443, 5171, 0, "tzhaar", "jad", "fight caves"),
    INFERNO(2495, 5111, 0, "inferno"),
    WINTERTODT(1630, 3958, 0, "wintertodt"),
    GEM_ROCK(2825, 2997, 0, "rock data"),
    GODWARS(2879, 5310, 2, "godwars", "god wars", "gwd"),
    REVENANTS(3244, 10169, 0, "revs", "rev"),
    ACTIVE_VOLCANO(3368, 3935, 0, "active volcano", "volcano"),
    DARK_CASTLE(3028, 3631, 0, "dark castle")
    ;

    public final int x, y, z;

    public final String[] names;

    Location(int x, int y, int z, String... names) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.names = names;
    }

    public static Location find(String name) {
        for(Location l : Location.values()) {
            for(String s : l.names) {
                if(name.equalsIgnoreCase(s))
                    return l;
            }
        }
        return null;
    }

}