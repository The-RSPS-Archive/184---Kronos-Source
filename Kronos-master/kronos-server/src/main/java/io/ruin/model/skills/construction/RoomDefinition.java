package io.ruin.model.skills.construction;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Tile;
import io.ruin.model.map.dynamic.DynamicChunk;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.skills.construction.room.impl.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.model.skills.construction.Hotspot.*;

public enum RoomDefinition {
    GROUND_FLOORS(233, 712),
    BASEMENT_FLOORS(235, 712),
    SUPERIOR_GARDEN(65, 75000, 237, 712, true, false, 26, "superior garden", 1458, SuperiorGardenRoom.class,
            TELEPORT_SPACE, TOPIARY_SPACE, POOL_SPACE, THEME_SPACE, FENCE_SPACE, GARDEN_BENCH_1, GARDEN_BENCH_2),
    MENAGERIE_OUTDOORS(37, 30000, 239, 712, true, false, 25, "outdoor menagerie", 1457, MenagerieRoom.class,
            PET_HOUSE, EMPTY, HABITAT, SCRATCHING_POST, ARENA, PET_LIST, PET_FEEDER),
    GARDEN(1, 1000, 232, 713, true, false, 2, "garden", 1453, SimpleRoom.class,
            CENTERPIECE, BIG_TREE, SMALL_TREE, BIG_PLANT_1, BIG_PLANT_2, SMALL_PLANT_1, SMALL_PLANT_2, GARDEN_TIP_JAR),
    FORMAL_GARDEN(55, 75000, 234, 713, true, false, 21, "formal garden", 1454, SimpleRoom.class,
            FORMAL_GARDEN_CENTERPIECE, FENCING, HEDGING, FORMAL_BIG_PLANT, FORMAL_BIG_PLANT_2, FORMAL_SMALL_PLANT, FORMAL_SMALL_PLANT_2, GARDEN_TIP_JAR),
    BLANK(236, 713),
    COSTUME_ROOM(42, 50000, 238, 713, false, false, 23, "costume room", 1455, SimpleRoom.class,
            CAPE_RACK, MAGIC_WARDROBE, EMPTY, TOY_BOX, TREASURE_CHEST, FANCY_DRESS_BOX, ARMOUR_CASE),
    ROOFTOP_STRAIGHT(233, 714),
    ROOFTOP_THREE_WAY(235, 714),
    ROOFTOP_FOUR_WAY(237, 714),
    MENAGERIE_INDOORS(37, 30000, 239, 714, false, false, 24, "indoor menagerie", 1456, MenagerieRoom.class,
            PET_HOUSE, EMPTY, EMPTY, SCRATCHING_POST, ARENA, PET_LIST, PET_FEEDER),
    DUNGEON_JUNCTION(70, 7500, 232, 715, false, true, 18, "dungeon junction", 1450, DungeonRoom.class,
            DUNGEON_GUARD, DUNGEON_TRAP_1, DUNGEON_TRAP_2, DUNGEON_DOOR_1, DUNGEON_DOOR_2, DUNGEON_LIGHTING, DUNGEON_DECORATION),
    DUNGEON_STAIRS(70, 7500, 234, 715, false, true, 19, "dungeon stairs", 1451, DungeonGuardedRoom.class,
            Hotspot.DUNGEON_STAIRS, DUNGEON_STAIRS_GUARD_1, DUNGEON_STAIRS_GUARD_2, DUNGEON_DOOR_1, DUNGEON_DOOR_2, DUNGEON_LIGHTING, DUNGEON_DECORATION),
    DUNGEON_CORRIDOR(70, 7500, 236, 715, false, true, 17, "dungeon corridor", 1449, DungeonRoom.class,
            DUNGEON_GUARD, DUNGEON_TRAP_1, DUNGEON_TRAP_2, DUNGEON_DOOR_1, DUNGEON_DOOR_2, DUNGEON_LIGHTING, DUNGEON_DECORATION),
    OUBLIETTE(65, 150000, 238, 715, false, true, 16, "oubliette", 1448, OublietteRoom.class,
            OUBLIETTE_FLOOR_SPACE, PRISON, OUBLIETTE_GUARD, LIGHTING, LADDER, EMPTY, DECORATION),
    PORTAL_CHAMBER(50, 100000, 233, 716, false, false, 14, "portal chamber", 1446, PortalChamberRoom.class,
            EMPTY, EMPTY, EMPTY, PORTAL_1, PORTAL_2, PORTAL_3, TELEPORT_FOCUS), // i assume the 3 empty slots are for the focused portals, but i decided to handle it differently
    COMBAT_ROOM(32, 25000, 235, 716, false, false, 22, "combat room", 1438, SimpleRoom.class,
            COMBAT_RING, EMPTY, EMPTY, RACK, COMBAT_ROOM_DECORATION, COMBAT_DUMMY),
    GAMES_ROOM(30, 25000, 237, 716, false, false, 6, "games room", 1437, SimpleRoom.class,
            GAME, PRIZE_CHEST, ATTACK_STONE, ELEMENTAL_BALANCE, RANGING_GAME),
    TREASURE_ROOM(75, 250000, 239, 716, false, true, 20, "treasure room", 1452, TreasureRoom.class,
            TREASURE, TREASURE_BOSS, EMPTY, DUNGEON_DOOR_1, TREASURE_ROOM_DECORATION, DUNGEON_LIGHTING, DUNGEON_DECORATION),
    WORKSHOP(15, 10000, 232, 717, false, false, 12, "workshop", 1444, SimpleRoom.class,
            WORKBENCH, CRAFTING_TABLE, TOOLS, REPAIR_SPACE, HERALDRY_STAND),
    CHAPEL(45, 50000, 234, 717, false, false, 11, "chapel", 1443, ChapelRoom.class,
            ICON, ALTAR, LAMP, WINDOW, CHAPEL_RUG, STATUE, MUSICAL),
    STUDY(40, 50000, 236, 717, false, false, 13, "study", 1445, SimpleRoom.class,
            LECTERN, GLOBE, EMPTY, CRYSTAL_BALL, WALL_CHART, TELESCOPE, STUDY_BOOKCASES),
    THRONE_ROOM(60, 150000, 238, 717, false, false, 15, "throne room", 1447, SimpleRoom.class,
            THRONE, FLOOR_SPACE, THRONE_ROOM_WALL_DECORATION, LEVER, THRONE_ROOM_SEATING_SPACE_1, THRONE_ROOM_SEATING_SPACE_2, TRAPDOOR),
    SKILL_HALL_UP(25, 15000, 233, 718, false, false, 7, "skill hall", 1439, SimpleRoom.class,
            SKILL_HALL_STAIRS_UP, HEAD_TROPHY, EMPTY, FISHING_TROPHY, METAL_ARMOUR_STAND, CW_ARMOUR_STAND, RUNE_CASE),
    SKILL_HALL_DOWN(25, 15000, 235, 718, false, false, 8, "skill hall", 1439, SimpleRoom.class,
            SKILL_HALL_STAIRS_DOWN, HEAD_TROPHY, EMPTY, FISHING_TROPHY, METAL_ARMOUR_STAND, CW_ARMOUR_STAND, RUNE_CASE),
    QUEST_HALL_UP(35, 25000, 237, 718, false, false, 9, "quest hall", 1441, SimpleRoom.class,
            QUEST_HALL_STAIRS_UP, PORTRAIT, LANDSCAPE, GUILD_TROPHY, MOUNTED_SWORD, MAP, QUEST_HALL_BOOKCASE),
    QUEST_HALL_DOWN(35, 25000, 239, 718, false, false, 10, "quest hall", 1442, SimpleRoom.class,
            QUEST_HALL_STAIRS_DOWN, PORTRAIT, LANDSCAPE, GUILD_TROPHY, MOUNTED_SWORD, MAP, QUEST_HALL_BOOKCASE),
    PARLOUR(1, 1000, 232, 719, false, false, 1, "parlour", 1433, SimpleRoom.class,
            CHAIR_1, CHAIR_2, CHAIR_3, RUG, BOOKCASE, FIREPLACE, CURTAINS),
    KITCHEN(5, 5000, 234, 719, false, false, 3, "kitchen", 1434, SimpleRoom.class,
            STOVE, SHELVES, BARREL, CAT_BASKET, LARDER, SINK, KITCHEN_TABLE),
    DINING_ROOM(10, 5000, 236, 719, false, false, 4, "dining room", 1435, SimpleRoom.class,
            DINING_TABLE, SEATING_SPACE_1, SEATING_SPACE_2, DINING_ROOM_FIREPLACE, DINING_ROOM_CURTAINS, WALL_DECORATION, BELL_PULL),
    BEDROOM(20, 10000, 238, 719, false, false, 5, "bedroom", 1436, SimpleRoom.class,
            BED, WARDROBE, DRESSER, BEDROOM_CURTAINS, BEDROOM_RUG, BEDROOM_FIREPLACE, BEDROOM_CORNER),
    ACHIEVEMENT_GALLERY(80, 200000, 233, 720, false, false, 27, "achievement gallery", 1459, AchievementGalleryRoom.class,
            SPELLBOOK_ALTAR, ADVENTURE_LOG, JEWELLERY_BOX, BOSS_LAIR_DISPLAY, DISPLAY, QUEST_LIST);

    private int chunkX, chunkY;

    private int id;

    private String name;
    private Class<? extends Room> handler;
    private Hotspot[] hotspots;
    private int levelReq;
    private int cost;
    private final boolean outdoors;
    private final boolean basement;
    private final int enumId;

    private List<VisualObject> visualObjects = new LinkedList<>();

    RoomDefinition(int levelReq, int cost, int chunkX, int chunkY, boolean outdoors, boolean basement, int id, String name, int enumId, Class<? extends Room> handler, Hotspot... hotspots) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.outdoors = outdoors;
        this.id = id;
        this.name = name;
        this.enumId = enumId;
        this.handler = handler;
        this.levelReq = levelReq;
        this.cost = cost;
        this.hotspots = hotspots;
        this.basement = basement;
    }

    public boolean isOutdoors() {
        return outdoors;
    }

    public boolean isBasement() {
        return basement;
    }

    RoomDefinition(int chunkX, int chunkY) {
        this(-1, 0, chunkX, chunkY, false, false, -1, null, 0, null);
    }

    public DynamicChunk getChunk(HouseStyle style) {
        return new DynamicChunk(chunkX + style.changeX, chunkY, style.changeZ);
    }

    public Class<? extends Room> getHandler() {
        return handler;
    }

    public Hotspot[] getHotspots() {
        return hotspots;
    }

    public List<VisualObject> getVisualObjects() {
        return visualObjects;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public RoomDefinition getAlternateForm(boolean up) {
        if (this == SKILL_HALL_UP || this == SKILL_HALL_DOWN)
            return up ? SKILL_HALL_UP : SKILL_HALL_DOWN;
        else if (this == QUEST_HALL_UP || this == QUEST_HALL_DOWN)
            return up ? QUEST_HALL_UP : QUEST_HALL_DOWN;
        else
            return this;
    }

    public int getId() {
        return id;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public int getEnumId() {
        return enumId;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }

    public static class VisualObject {


        public int id;
        public int x;
        public int y;
        public int type;

        public VisualObject(int id, int x, int y, int type, int direction) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.type = type;
            this.direction = direction;
        }

        public int direction;
    }

    static {
        for (RoomDefinition def : values()) {
            for (int localX = 0; localX < 8; localX++) {
                for (int localY = 0; localY < 8; localY++) {
                    int absX = (def.chunkX * 8) + localX;
                    int absY = (def.chunkY * 8) + localY;
                    Tile tile = Tile.get(absX, absY, 0);
                    if (tile != null && tile.gameObjects != null) {
                        int finalLocalX = localX;
                        int finalLocalY = localY;
                        tile.gameObjects.stream()
                                .filter(o -> o.getDef().hasOption("build"))
                                .map(o -> new VisualObject(o.id, finalLocalX, finalLocalY, o.type, o.direction))
                                .forEach(def.visualObjects::add);
                    }
                }
            }
        }
    }

    public static void openRoomSelection(Player player, Consumer<RoomDefinition> action) {
        player.openInterface(InterfaceType.MAIN, Interface.CONSTRUCTION_ROOM_CREATION);
        player.set("ROOM_CREATION_CONSUMER", action);
    }

    public Room create() {
        try {
            return handler.newInstance().init(this);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        InterfaceHandler.register(Interface.CONSTRUCTION_ROOM_CREATION, handler -> {
            handler.actions[4] = (SlotAction) (p, slot) -> {
                Consumer<RoomDefinition> action = p.get("ROOM_CREATION_CONSUMER"); // workaround for this insanely inflexible interface handler system
                if (action == null)
                    return;
                RoomDefinition def = null;
                for (RoomDefinition rd : values()) {
                    if (rd.id == slot) {
                        def = rd;
                        break;
                    }
                }
                if (def == null)
                    return;
                if (!p.getInventory().contains(COINS_995, def.cost)) {
                    p.dialogue(new MessageDialogue("You don't have enough coins to create that room."));
                    return;
                }
                p.remove("ROOM_CREATION_CONSUMER");
                action.accept(def);
            };
        });
    }
}
