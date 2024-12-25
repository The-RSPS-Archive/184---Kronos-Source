package io.ruin.model.inter.utils;

import io.ruin.cache.Varpbit;
import io.ruin.model.entity.player.Player;

import java.util.ArrayList;

public class Config {

    private static final ArrayList<Config> CONFIGS = new ArrayList<>();

    private static final int[] SHIFTS = new int[32];

    static {
        int offset = 2;
        for(int i_4_ = 0; i_4_ < 32; i_4_++) {
            SHIFTS[i_4_] = offset - 1;
            offset += offset;
        }
    }

    /**
     * Default options
     */

    public static final Config BRIGHTNESS = varp(166, true).defaultValue(4);

    public static final Config MUSIC_VOLUME = varp(168, true);

    public static final Config SOUND_EFFECT_VOLUME = varp(169, true);

    public static final Config AREA_SOUND_EFFECT_VOLUME = varp(872, true);

    public static final Config CHAT_EFFECTS = varp(171, true);

    public static final Config SPLIT_PRIVATE_CHAT = varp(287, true).defaultValue(1);

    public static final Config HIDE_PRIVATE_CHAT = varpbit(4089, true);

    public static final Config PROFANITY_FILTER = varp(1074, true);

    public static final Config FRIEND_NOTIFICATION_TIMEOUT = varpbit(1627, true);

    public static final Config MOUSE_BUTTONS = varp(170, true);

    public static final Config MOUSE_CAMERA = varpbit(4134, true);

    public static final Config SHIFT_DROP = varpbit(5542, true);

    public static final Config PLAYER_ATTACK_OPTION = varp(1107, true).forceSend();

    public static final Config NPC_ATTACK_OPTION = varp(1306, true).forceSend();

    public static final Config ACCEPT_AID = varpbit(4180, true);

    public static final Config RUNNING = varp(173, true).defaultValue(1);

    public static final Config DISPLAY_NAME = varp(1055, true);
    public static final Config HAS_DISPLAY_NAME = varpbit(8199, true).defaultValue(1).forceSend();

    /**
     * Advanced options
     */

    public static final Config TRANSPARENT_SIDE_PANEL = varpbit(4609, true);

    public static final Config REMAINING_XP_TOOLTIP = varpbit(4181, true);

    public static final Config PRAYER_TOOLTIPS = varpbit(5711, true);

    public static final Config SPECIAL_ATTACK_TOOLTIPS = varpbit(5712, true);

    public static final Config DATA_ORBS = varpbit(4084, true);

    public static final Config TRANSPARENT_CHATBOX = varpbit(4608, true);

    public static final Config CLICK_THROUGH_CHATBOX = varpbit(2570, true);

    public static final Config SIDE_PANELS = varpbit(4607, true);

    public static final Config HOTKEY_CLOSING_PANELS = varpbit(4611, true);

    public static final Config SIDEBAR_INDICATOR = varpbit(3756, false);

    public static final Config CHATBOX_SCROLLBAR = varpbit(6374, true);
    public static final Config ZOOMING_DISABLED = varpbit(6357, true);

    /**
     * House options
     */

    public static final Config BUILDING_MODE = varpbit(2176, true);

    public static final Config RENDER_DOORS_MODE = varpbit(6269, true);

    public static final Config TELEPORT_INSIDE = varpbit(4744, true);

    /**
     * House viewer
     */

    public static final Config HOUSE_VIEWER_SELECTED = varpbit(5329, false);

    public static final Config HOUSE_VIEWER_HIGHLIGHTED = varpbit(5330, false);

    public static final Config HOUSE_VIEWER_ROOM_ROTATION = varpbit(5331, false);

    public static final Config HOUSE_VIEWER_ALLOW_ROTATION = varpbit(5332, false);

    public static final Config HOUSE_VIEWER_ROOM_ID = varpbit(5333, false);

    /**
     * Other construction related things
     */

    public static final Config LECTERN_EAGLE = varp(261, false).defaultValue(0);
    public static final Config LECTERN_DEMON = varp(262, false).defaultValue(0);
    public static final Config PETS_ROAMING_DISABLED = varpbit(2013, true);
    public static final Config HIRED_SERVANT = varpbit(2190, false);

    /**
     * Notification options
     */

    public static final Config LOOT_DROP_NOTIFICATION_ENABLED = varpbit(5399, true);

    public static final Config LOOT_DROP_NOTIFICATION_VALUE = varpbit(5400, true);

    public static final Config UNTRADEABLE_LOOT_NOTIFICATIONS = varpbit(5402, true);

    public static final Config BOSS_KC_UPDATE = varpbit(4930, true);

    public static final Config DROP_ITEM_WARNING_ENABLED = varpbit(5411, true);

    public static final Config DROP_ITEM_WARNING_VALUE = varpbit(5412, true).defaultValue(30000);

    public static final Config FOLLOWER_PRIORITY = varpbit(5599, true);

    /**
     * Music player
     */

    public static final Config[] MUSIC_UNLOCKS = {
            varp(20, true),
            varp(21, true),
            varp(22, true),
            varp(23, true),
            varp(24, true),
            varp(25, true),
            varp(298, true),
            varp(311, true),
            varp(346, true),
            varp(414, true),
            varp(464, true),
            varp(598, true),
            varp(662, true),
            varp(721, true),
            varp(906, true),
            varp(1009, true),
            varp(1338, true),
    };

    public static final Config MUSIC_PREFERENCE = varp(18, true);

    public static final Config MUSIC_LOOP = varpbit(4137, true);

    /**
     * Display name options
     */

    //varpbit 5605 enables look up

    /**
     * World switcher
     */

    public static final Config WORLD_SWITCHER_SETTINGS = varpbit(4596, true);
    public static final Config WORLD_SWITCHER_FAVOURITE_ONE = varpbit(4597, true);
    public static final Config WORLD_SWITCHER_FAVOURITE_TWO = varpbit(4598, true);

    /**
     * Keybind options
     */

    public static final Config[] KEYBINDS = {
            varpbit(4675, true).defaultValue(1),     //Combat
            varpbit(4676, true).defaultValue(2),     //Stats
            varpbit(4677, true).defaultValue(3),     //Quests
            varpbit(4678, true).defaultValue(13),    //Inventory
            varpbit(4679, true).defaultValue(4),     //Equipment

            varpbit(4680, true).defaultValue(5),     //Prayer
            varpbit(4682, true).defaultValue(6),     //Magic
            varpbit(4684, true).defaultValue(8),     //Friends
            varpbit(4685, true).defaultValue(9),     //Ignores
            varpbit(4689, true).defaultValue(0),     //Logout

            varpbit(4686, true).defaultValue(10),    //Options
            varpbit(4687, true).defaultValue(11),    //Emotes
            varpbit(4683, true).defaultValue(7),     //Clan
            varpbit(4688, true).defaultValue(12),    //Music
    };

    public static final Config ESCAPE_CLOSES = varpbit(4681, true);


    /**
     * Combat Options
     */

    public static final Config ATTACK_SET = varp(43, true);

    public static final Config AUTO_RETALIATE = varp(172, true);

    public static final Config WEAPON_TYPE = varpbit(357, false);

    public static final Config SPECIAL_ENERGY = varp(300, true).defaultValue(1000);

    public static final Config SPECIAL_ACTIVE = varp(301, false);

    public static final Config SPECIAL_ORB_STATE = varpbit(8121, false).defaultValue(2);

    public static final Config TARGET_OVERLAY_CUR = varpbit(5653, false);

    public static final Config TARGET_OVERLAY_MAX = varpbit(5654, false);

    /**
     * Skill guide
     */

    public static final Config SKILL_GUIDE_STAT = varpbit(4371, false);

    public static final Config SKILL_GUIDE_CAT = varpbit(4372, false);

    /**
     * Bank
     */

    public static final Config BANK_TAB = varpbit(4150, false);

    public static final Config BANK_LAST_X = varpbit(3960, true);

    public static final Config BANK_INSERT_MODE = varpbit(3959, true);

    public static final Config BANK_ALWAYS_PLACEHOLDERS = varpbit(3755, true);

    public static final Config BANK_TAB_DISPLAY = varpbit(4170, true);

    public static final Config BANK_INCINERATOR = varpbit(5102, true);

    public static final Config BANK_DEPOSIT_EQUIPMENT = varpbit(5364, true);

    public static final Config[] BANK_TAB_SIZES = {
            varpbit(4171, true), varpbit(4172, true), varpbit(4173, true),
            varpbit(4174, true), varpbit(4175, true), varpbit(4176, true),
            varpbit(4177, true), varpbit(4178, true), varpbit(4179, true),
    };

    public static final Config BANK_DEFAULT_QUANTITY = varpbit(6590, true);

    /**
     * Prayer
     */

    public static final Config QUICK_PRAYERS_ACTIVE = varpbit(4102, false);

    public static final Config QUICK_PRAYING = varpbit(4103, false);

    public static final Config CHIVALRY_PIETY_UNLOCK = varpbit(3909, false).defaultValue(8);

    public static final Config RIGOUR_UNLOCK = varpbit(5451, true);

    public static final Config AUGURY_UNLOCK = varpbit(5452, true);

    public static final Config PRESERVE_UNLOCK = varpbit(5453, true);

    /**
     * Runecrafting
     */
    public static final Config AIR_TIARA_UNLOCK = varpbit(607, true).defaultValue(0); //why default 0? it's already 0?

    public static final Config MIND_TIARA_UNLOCK = varpbit(608, true).defaultValue(0); //why default 0? it's already 0?

    /**
     * Rune pouch
     */

    public static final Config RUNE_POUCH_TYPES = varp(1139, false);
    public static final Config RUNE_POUCH_AMOUNTS = varp(1140, false);
    public static final Config RUNE_POUCH_LEFT_TYPE = varpbit(29, true);
    public static final Config RUNE_POUCH_LEFT_AMOUNT = varpbit(1624, true);

    public static final Config RUNE_POUCH_MIDDLE_TYPE = varpbit(1622, true);
    public static final Config RUNE_POUCH_MIDDLE_AMOUNT = varpbit(1625, true);

    public static final Config RUNE_POUCH_RIGHT_TYPE = varpbit(1623, true);
    public static final Config RUNE_POUCH_RIGHT_AMOUNT = varpbit(1626, true);

    /**
     * Misc
     */

    public static final Config GAME_FILTER = varpbit(26, true);

    public static final Config MAGIC_BOOK = varpbit(4070, true);

    public static final Config AUTOCAST = varpbit(276, true);

    public static final Config DEFENSIVE_CAST = varpbit(2668, true);

    public static final Config AUTOCAST_SET = varp(664, false);

    public static final Config SMITHING_TYPE = varpbit(3216, false);

    public static final Config ABYSS_MAP = varpbit(625, false);

    public static final Config PLAYER_PRIORITY = varp(1075, false);

    public static final Config STAMINA_POTION = varpbit(25, true);

    public static final Config LOCK_CAMERA = varpbit(4606, false);

    public static final Config MULTI_ZONE = varpbit(4605, false);

    public static final Config MY_TRADE_MODIFIED = varpbit(4374, false);

    public static final Config OTHER_TRADE_MODIFIED = varpbit(4375, false);

    public static final Config HAM_HIDEOUT_ENTRANCE = varpbit(235, false);

    public static final Config VENG_COOLDOWN = varpbit(2451, false);

    public static final Config INFERNO_ENTRANCE = varpbit(5646, true).defaultValue(2); //uhh todo

    public static final Config INFERNO_PILLAR_DAMAGE_WEST = varpbit(5655, false);

    public static final Config INFERNO_PILLAR_DAMAGE_SOUTH = varpbit(5656, false);

    public static final Config INFERNO_PILLAR_DAMAGE_EAST = varpbit(5657, false);

    public static final Config PVP_KILLS = varp(1103, true);

    public static final Config PVP_DEATHS = varp(1102, true);

    public static final Config PVP_KD_OVERLAY = varpbit(4143, true).defaultValue(1);

    public static final Config IN_PVP_AREA = varpbit(8121, true).defaultValue(1);

    public static final Config WILDERNESS_TIMER = varpbit(877, false);

    public static final Config ARDOUGNE_LEVER_UNLOCK = varpbit(4470, false).defaultValue(1);

    public static final Config LARRANS_CHEST = varpbit(6583, false).defaultValue(0);

    /**
     * Slayer
     */
    public static final Config SLAYER_UNLOCKED_HELM = varpbit(3202, true);
    public static final Config RING_BLING = varpbit(3207, true);
    public static final Config BROADER_FLETCHING = varpbit(3208, true);
    public static final Config KING_BLACK_BONNET = varpbit(5080, true);
    public static final Config KALPHITE_KHAT = varpbit(5081, true);
    public static final Config UNHOLY_HELMET = varpbit(5082, true);
    public static final Config BIGGER_AND_BADDER = varpbit(5358, true);
    public static final Config SEEING_RED = varpbit(2462, true);
    public static final Config UNLOCK_BLOCK_TASK_SIX = varpbit(4538, true).defaultValue(1).forceSend();
    public static final Config UNLOCK_DULY_NOTED = varpbit(4589, true);
    public static final Config GARGOYLE_SMASHER = varpbit(4027, true);
    public static final Config SLUG_SALTER = varpbit(4028, true);
    public static final Config REPTILE_FREEZER = varpbit(4029, true);
    public static final Config SHROOM_SPRAYER = varpbit(4030, true);
    public static final Config NEED_MORE_DARKNESS = varpbit(4031, true);
    public static final Config ANKOU_VERY_MUCH = varpbit(4085, true);
    public static final Config SUQ_ANOTHER_ONE = varpbit(4086, true);
    public static final Config FIRE_AND_DARKNESS = varpbit(4087, true);
    public static final Config PEDAL_TO_THE_METALS = varpbit(4088, true);
    public static final Config AUGMENT_MY_ABBIES = varpbit(4090, true);
    public static final Config ITS_DARK_IN_HERE = varpbit(4091, true);
    public static final Config GREATER_CHALLENGE = varpbit(4092, true);
    public static final Config I_HOPE_YOU_MITH_ME = varpbit(4094, true);
    public static final Config WATCH_THE_BIRDIE = varpbit(4095, true);
    public static final Config HOT_STUFF = varpbit(4691, true);
    public static final Config LIKE_A_BOSS = varpbit(4724, true);
    public static final Config BLEED_ME_DRY = varpbit(4746, true);
    public static final Config SMELL_YA_LATER = varpbit(4747, true);
    public static final Config BIRDS_OF_A_FEATHER = varpbit(4748, true);
    public static final Config I_REALLY_MITH_YOU = varpbit(4749, true);
    public static final Config HORRORIFIC = varpbit(4750, true);
    public static final Config TO_DUST_YOU_SHALL_RETURN = varpbit(4751, true);
    public static final Config WYVER_NOTHER_ONE = varpbit(4752, true);
    public static final Config GET_SMASHED = varpbit(4753, true);
    public static final Config NECHS_PLEASE = varpbit(4754, true);
    public static final Config KRACK_ON = varpbit(4755, true);
    public static final Config SPIRITUAL_FERVOUR = varpbit(4757, true);
    public static final Config REPTILE_GOT_RIPPED = varpbit(4996, true);
    public static final Config GET_SCABARIGHT_ON_IT = varpbit(5359, true);
    public static final Config WYVER_NOTHER_TWO = varpbit(5733, true);
    public static final Config DARK_MANTLE = varpbit(5631, true);
    public static final Config UNDEAD_HEAD = varpbit(6096, true);
    public static final Config USE_MORE_HEAD = varpbit(6570, true);
    public static final Config STOP_THE_WYVERN = varpbit(240, true);
    public static final Config DOUBLE_TROUBLE = varpbit(6485, true);
    public static final Config ADA_MIND_SOME_MORE = varpbit(1, true);
    public static final Config RUUUUUNE = varpbit(1, true);
    public static final Config BASILOCKED = varpbit(1, true);
    public static final Config BASILONGER = varpbit(1, true);

    public static final Config SLAYER_TASK_AMOUNT = varp(261, false);
    public static final Config SLAYER_TASK_1 = varp(262, false);
    public static final Config SLAYER_TASK_2 = varp(263, false);
    public static final Config SLAYER_POINTS = varpbit(4068, true);

    public static final Config[] BLOCKED_TASKS = {
            varpbit(3209, false),
            varpbit(3210, false),
            varpbit(3211, false),
            varpbit(3212, false),
            varpbit(4441, false),
            varpbit(5023, false),
    };

    /**
     * Quest points
     */
    public static final Config QUEST_POINTS = varp(101, true).defaultValue(252).forceSend();

    /**
     * Farming
     */
    public static final Config FARMING_PATCH_1 = varpbit(4771, false);
    public static final Config FARMING_PATCH_2 = varpbit(4772, false);
    public static final Config FARMING_PATCH_3 = varpbit(4773, false);
    public static final Config FARMING_PATCH_4 = varpbit(4774, false);
    public static final Config FARMING_COMPOST_BIN = varpbit(4775, false);

    public static final Config STORAGE_RAKE = varpbit(1435, true);
    public static final Config STORAGE_SEED_DIBBER = varpbit(1436, true);
    public static final Config STORAGE_SPADE = varpbit(1437, true);
    public static final Config STORAGE_SECATEURS = varpbit(1438, true);
    public static final Config STORAGE_SECATEURS_TYPE = varpbit(1848, true); // 0 for normal, 1 for magic
    public static final Config STORAGE_WATERING_CAN = varpbit(1439, true);
    public static final Config STORAGE_TROWEL = varpbit(1440, true);
    public static final Config STORAGE_EMPTY_BUCKET_1 = varpbit(1441, true); // 5 bits
    public static final Config STORAGE_EMPTY_BUCKET_2 = varpbit(4731, true); // 3 bits
    public static final Config STORAGE_COMPOST_1 = varpbit(1442, true); // 8 bits
    public static final Config STORAGE_COMPOST_2 = varpbit(6266, true); // 2 bits
    public static final Config STORAGE_SUPERCOMPOST_1 = varpbit(1443, true); // 8 bits
    public static final Config STORAGE_SUPERCOMPOST_2 = varpbit(6267, true); // 2 bits
    public static final Config STORAGE_PLANT_CURE = varpbit(6268, true);
    public static final Config STORAGE_ULTRACOMPOST = varpbit(5732, true);
    public static final Config STORAGE_BOTTOMLESS_COMPOST = varpbit(7915, true);


    /**
     * Motherlode mine
     */
    public static final Config PAY_DIRT_IN_SACK = varpbit(5558, true);

    /**
     * Barrows
     */

    public static final Config AHRIM_KILLED = varpbit(457, true);
    public static final Config DHAROK_KILLED = varpbit(458, true);
    public static final Config GUTHAN_KILLED = varpbit(459, true);
    public static final Config KARIL_KILLED = varpbit(460, true);
    public static final Config TORAG_KILLED = varpbit(461, true);
    public static final Config VERAC_KILLED = varpbit(462, true);
    public static final Config BARROWS_CHEST = varpbit(1394, false);

    /**
     * Fairy ring
     */
    public static final Config FAIRY_RING_LEFT = varpbit(3985, true);
    public static final Config FAIRY_RING_MIDDLE = varpbit(3986, true);
    public static final Config FAIRY_RING_RIGHT = varpbit(3987, true);
    public static final Config FAIRY_RING_LAST_DESTINATION = varpbit(5374, true);

    /**
     * Canoe station
     */
    public static final Config LUMBRIDGE_CANOE = varpbit(1839, true);
    public static final Config CHAMPION_GUILD_CANOE = varpbit(1840, true);
    public static final Config BARBARIAN_VILLAGE_CANOE = varpbit(1841, true);
    public static final Config EDGEVILLE_CANOE = varpbit(1842, true);
    public static final Config WILDERNESS_CHINS_CANOE = varpbit(1843, true);

    /**
     * Godwars
     */
    public static final Config GWD_SARADOMIN_KC = varpbit(3972, true);
    public static final Config GWD_ARMADYL_KC = varpbit(3973, true);
    public static final Config GWD_BANDOS_KC = varpbit(3975, true);
    public static final Config GWD_ZAMORAK_KC = varpbit(3976, true);
    public static final Config SARADOMINS_LIGHT = varpbit(4733, true);
    public static final Config GODWARS_DUNGEON = varpbit(3966, true);
    public static final Config GODWARS_SARADOMIN_FIRST_ROPE = varpbit(3967, true);
    public static final Config GODWARS_SARADOMIN_SECOND_ROPE = varpbit(3968, true);

    /**
     * Poison
     */
    public static final Config POISONED = varp(102, true);

    /**
     * Exp Counter
     */
    public static final Config XP_COUNTER_SHOWN = varpbit(4702, true);
    public static final Config XP_COUNTER_POSITION = varpbit(4692, true);
    public static final Config XP_COUNTER_SIZE = varpbit(4693, true);
    public static final Config XP_COUNTER_DURATION = varpbit(4694, true);
    public static final Config XP_COUNTER_COLOUR = varpbit(4695, true);
    public static final Config XP_COUNTER_GROUP = varpbit(4696, true);
    public static final Config XP_COUNTER_COUNTER = varpbit(4697, true);
    public static final Config XP_COUNTER_PROGRESS_BAR = varpbit(4698, true);
    public static final Config XP_COUNTER_SPEED = varpbit(4722, true);

    /**
     * Kalphite lair
     */
    public static final Config KALPHITE_LAIR_ROPE_INTERIOR = varpbit(4587, true);
    public static final Config KALPHITE_LAIR_ROPE_EXTERIOR = varpbit(4586, true);

    /**
     * Bounty hunter
     */
    public static final Config BOUNTY_HUNTER_RISK = varpbit(1538, false);
    public static final Config BOUNTY_HUNTER_EMBLEM = varpbit(4162, false);
    public static final Config BOUNTY_HUNTER_RECORD_OVERLAY = varpbit(1621, true);
    public static final Config BOUNTY_HUNTER_ROGUE_KILLS = varp(1134, true);
    public static final Config BOUNTY_HUNTER_ROGUE_RECORD = varp(1133, true);
    public static final Config BOUNTY_HUNTER_TARGET_KILLS = varp(1136, true);
    public static final Config BOUNTY_HUNTER_TARGET_RECORD = varp(1135, true);
    public static final Config BOUNTY_HUNTER_TELEPORT = varpbit(2010, true);

    /**
     * Chatbox interface setting
     */
    public static final Config CHATBOX_INTERFACE_USE_FULL_FRAME = varpbit(5983, false);

    /**
     * Pets
     */
    public static final Config PET_NPC_INDEX = varp(447, false);
    /*
    public static final Config INSURED_PET_DAGANNOTH_SUPREME = varpbit(4338, true);
    public static final Config INSURED_PET_DAGANNOTH_PRIME = varpbit(4339, true);
    public static final Config INSURED_PET_DAGANNOTH_REX = varpbit(4340, true);
    public static final Config INSURED_PET_PENANCE_QUEEN = varpbit(4341, true);
    public static final Config INSURED_PET_KREEARRA = varpbit(4342, true);
    public static final Config INSURED_PET_GRAARDOR = varpbit(4343, true);
    public static final Config INSURED_PET_ZILYANA = varpbit(4344, true);
    public static final Config INSURED_PET_KRIL = varpbit(4345, true);
    public static final Config INSURED_PET_MOLE = varpbit(4346, true);
    public static final Config INSURED_PET_KBD = varpbit(4347, true);
    public static final Config INSURED_PET_KQ = varpbit(4348, true);
    public static final Config INSURED_PET_SMOKE_DEVIL = varpbit(4349, true);
    public static final Config INSURED_PET_KRAKEN = varpbit(4350, true);
    public static final Config INSURED_PET_CHOMPY = varpbit(4445, true);
    public static final Config INSURED_PET_CALLISTO = varpbit(4568, true);
    public static final Config INSURED_PET_VENENATIS = varpbit(4429, true);
    public static final Config INSURED_PET_VETION_PURPLE = varpbit(4569, true);
    public static final Config INSURED_PET_SCORPIA = varpbit(4570, true);
    public static final Config INSURED_PET_JAD = varpbit(4699, true);
    public static final Config INSURED_PET_CERBERUS = varpbit(4726, true);
    public static final Config INSURED_PET_HERON = varpbit(4846, true);
    public static final Config INSURED_PET_GOLEM = varpbit(4847, true);
    public static final Config INSURED_PET_BEAVER = varpbit(4848, true);
    public static final Config INSURED_PET_CHINCHOMPA = varpbit(4849, true);
    public static final Config INSURED_PET_ABYSSAL = varpbit(204, true);
    public static final Config INSURED_PET_CORE = varpbit(997, true);
    public static final Config INSURED_PET_ZULRAH = varpbit(1526, true);
    public static final Config INSURED_PET_CHAOS_ELE = varpbit(3962, true);
    public static final Config INSURED_PET_GIANT_SQUIRREL = varpbit(2169, true);
    public static final Config INSURED_PET_TANGLEROOT = varpbit(2170, true);
    public static final Config INSURED_PET_RIFT_GUARDIAN = varpbit(2171, true);
    public static final Config INSURED_PET_ROCKY = varpbit(2172, true);
    public static final Config INSURED_PET_BLOODHOUND = varpbit(5181, true);
    public static final Config INSURED_PET_PHOENIX = varpbit(5363, true);
    public static final Config INSURED_PET_OLMLET = varpbit(5448, true);
    public static final Config INSURED_PET_SKOTOS = varpbit(5632, true);
    public static final Config INSURED_PET_JAL_NIB_REK = varpbit(5644, true);
    public static final Config INSURED_PET_MIDNIGHT = varpbit(6013, true);
    public static final Config INSURED_PET_HERBI = varpbit(5735, true);
    */

    /**
     * Corp beast damage counter
     */
    public static final Config CORPOREAL_BEAST_DAMAGE = varp(1142, false);

    /**
     * Raid party configs
     */
    public static final Config RAIDS_PREFERRED_PARTY_SIZE = varp(1432, false);
    public static final Config RAIDS_PREFERRED_COMBAT_LEVEL = varpbit(5426, false);
    public static final Config RAIDS_PREFERRED_SKILL_TOTAL = varpbit(5427, false);
    public static final Config RAIDS_PARTY = varp(1427, false).defaultValue(-1);
    public static final Config RAIDS_TAB_ICON = varpbit(5432, false).defaultValue(-1);
    public static final Config RAIDS_STAGE = varp(1430, false).defaultValue(-1);

    public static final Config RAIDS_PARTY_POINTS = varpbit(5431, false);
    public static final Config RAIDS_PERSONAL_POINTS = varpbit(5422, false);
    public static final Config RAIDS_TIMER = varpbit(6386, false);

    public static final Config RAIDS_STORAGE_WARNING_DISMISSED = varpbit(5509, false);
    public static final Config RAIDS_STORAGE_PRIVATE_INVENTORY = varpbit(3459, false);


    /**
     * Gamemode (ironman)
     */
    public static final Config IRONMAN_MODE = varpbit(1777, true);

    /**
     * Used to toggle the friends/ignore list frame icon
     */
    public static final Config FRIENDS_AND_IGNORE_TOGGLE = varpbit(6516, true);

    /**
     * Emotes
     */
   public static final Config UNLOCK_FLAP_EMOTE = varpbit(2309, true);
   public static final Config UNLOCK_SLAP_HEAD_EMOTE = varpbit(2310, true);
   public static final Config UNLOCK_IDEA_EMOTE = varpbit(2311, true);
   public static final Config UNLOCK_STAMP_EMOTE = varpbit(2312, true);
   public static final Config UNLOCK_GOBLIN_BOW_AND_SALUTE_EMOTE = varpbit(532, true);
   public static final Config EMOTES = varp(313, true);

    /**
     * Magic book
     */
    public static final Config DISABLE_SPELL_FILTERING = varpbit(6718, true);
    public static final Config SHOW_COMBAT_SPELLS = varpbit(6605, true);
    public static final Config SHOW_UTILITY_SPELLS = varpbit(6606, true);
    public static final Config SHOW_SPELLS_LACK_LEVEL = varpbit(6607, true);
    public static final Config SHOW_SPELLS_LACK_RUNES = varpbit(6608, true);
    public static final Config SHOW_TELEPORT_SPELLS = varpbit(6609, true);

    /**
     * Clan Wars
     */
    public static final Config CLAN_WARS_GAME_END = varpbit(4270, false);
    public static final Config CLAN_WARS_MELEE_DISABLED = varpbit(4271, false);
    public static final Config CLAN_WARS_RANGING_DISABLED = varpbit(4272, false);
    public static final Config CLAN_WARS_MAGIC_DISABLED = varpbit(4273, false);
    public static final Config CLAN_WARSS_PRAYER_DISABLED = varpbit(4274, false);
    public static final Config CLAN_WARS_FOOD_DISABLED = varpbit(4275, false);
    public static final Config CLAN_WARS_DRINKS_DISABLED = varpbit(4276, false);
    public static final Config CLAN_WARS_SPECIAL_ATTACKS_DISABLED = varpbit(4277, false);
    public static final Config CLAN_WARS_STRAGGLERS = varpbit(4278, false);
    public static final Config CLAN_WARS_IGNORE_FREEZING = varpbit(4279, false);
    public static final Config CLAN_WARS_PJ_TIMER = varpbit(4280, false);
    public static final Config CLAN_WARS_ALLOW_TRIDENT = varpbit(4281, false);
    public static final Config CLAN_WARS_SINGLE_SPELLS = varpbit(4282, false);
    public static final Config CLAN_WARS_ARENA = varpbit(4283, false);
    public static final Config CLAN_WARS_ACCEPT = varpbit(4285, false);
    public static final Config CLAN_WARS_COUNTDOWN_TIMER = varpbit(4286, false);
    public static final Config CLAN_WARS_TEAM_1_COUNT = varpbit(4287, false);
    public static final Config CLAN_WARS_TEAM_2_COUNT = varpbit(4288, false);
    public static final Config CLAN_WARS_ACTIVE_TEAM = varpbit(4289, false);

    /**
     * Catacombs entrances
     */
    public static final Config CATACOMBS_ENTRANCE_NW = varpbit(5090, true);
    public static final Config CATACOMBS_ENTRANCE_SW = varpbit(5088, true);
    public static final Config CATACOMBS_ENTRANCE_SE = varpbit(5087, true);
    public static final Config CATACOMBS_ENTRANCE_NE = varpbit(5089, true);

    /**
     * Death storage
     */
    public static final Config DEATH_STORAGE_TYPE = varp(261, false);

    /**
     * Theatre of Blood 1=In Party, 2=Inside/Spectator, 3=Dead Spectating
     */
    public static final Config THEATRE_OF_BLOOD = varpbit(6440, false);
    /**
     * Theatre of Blood party hud, 0 = List, 1 = Orbs
     */
    public static final Config THEATRE_HUD_STATE = varpbit(6441, false);
    public static final Config BLOAT_DOOR = varpbit(6447, false);
    public static final Config TOB_PARTY_LEADER = varp(1740, false).defaultValue(-1);

    /**
     * Theatre of Blood orb varbits each number stands for the player's health on a scale of 1-27 (I think), 0 hides the orb
     */
    public static final Config THEATRE_OF_BLOOD_ORB_1 = varpbit(6442, false);
    public static final Config THEATRE_OF_BLOOD_ORB_2 = varpbit(6443, false);
    public static final Config THEATRE_OF_BLOOD_ORB_3 = varpbit(6444, false);
    public static final Config THEATRE_OF_BLOOD_ORB_4 = varpbit(6445, false);
    public static final Config THEATRE_OF_BLOOD_ORB_5 = varpbit(6446, false);

    /**
     * Separator
     */

    public int id;

    private Varpbit bit;

    private boolean save;

    private int defaultValue;

    private boolean forceSend;

    public Config defaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public Config forceSend() {
        this.forceSend = true;
        return this;
    }
    public void update(Player player) {
        player.updateVarp(bit == null ? id : bit.varpId);
    }

    public int toggle(Player player) {
        if(get(player) == 0) {
            set(player, 1);
            return 1;
        } else {
            set(player, 0);
            return 0;
        }
    }

    public int increment(Player player, int amount) {
        int newValue = get(player) + amount;
        set(player, newValue);
        return newValue;
    }

    public void reset(Player player) {
        set(player, defaultValue);
    }

    public void set(Player player, int value) {
        if(bit != null) {
            int varpId = bit.varpId;
            int least = bit.leastSigBit;
            int most = bit.mostSigBit;
            int shift = SHIFTS[most - least];
            if(value < 0 || value > shift)
                value = 0;
            int varpValue = player.varps[varpId];
            shift <<= least;
            player.varps[varpId] = ((varpValue & (~shift)) | value << least & shift);
            player.updateVarp(varpId);
        } else {
            player.varps[id] = value;
            player.updateVarp(id);
        }
    }

    public int get(Player player) {
        if(bit != null) {
            int varpId = bit.varpId;
            int least = bit.leastSigBit;
            int most = bit.mostSigBit;
            int shift = SHIFTS[most - least];
            return player.varps[varpId] >> least & shift;
        }
        return player.varps[id];
    }

    /**
     * Creation
     */

    public static Config varp(int id, boolean save) {
        return create(id, null, save, true);
    }

    public static Config varpbit(int id, boolean save) {
        return create(id, Varpbit.get(id), save, true);
    }

    public static Config create(int id, Varpbit bit, boolean save, boolean store) {
        Config config = new Config();
        config.id = id;
        config.bit = bit;
        config.save = save;
        if(store) {
            CONFIGS.add(config);
            CONFIGS.trimToSize();
        }
        return config;
    }

    /**
     * Loading
     */

    public static void save(Player player) {
        for(Config config : CONFIGS) {
            if(!config.save)
                continue;
            int saveId = config.id << 16 | (config.bit != null ? 1 : 0);
            player.savedConfigs.put(saveId, config.get(player));
        }
    }

    public static void load(Player player) {
        for(Config config : CONFIGS) {
            if(!config.save) {
                if(config.defaultValue != 0)
                    config.set(player, config.defaultValue);
                else if(config.forceSend)
                    config.update(player);
                continue;
            }
            int saveId = config.id << 16 | (config.bit != null ? 1 : 0);
            Integer savedValue = player.savedConfigs.get(saveId);
            if(savedValue == null) {
                if(config.defaultValue != 0)
                    config.set(player, config.defaultValue);
                else if(config.forceSend)
                    config.update(player);
                continue;
            }
            if(savedValue == 0) {
                if(config.defaultValue == 0 && config.forceSend)
                    config.update(player);
                continue;
            }
            config.set(player, savedValue);
        }
        player.savedConfigs.clear();
    }

}