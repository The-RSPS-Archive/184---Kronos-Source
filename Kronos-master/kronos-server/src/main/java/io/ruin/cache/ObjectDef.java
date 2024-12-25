package io.ruin.cache;

import com.google.common.collect.Maps;
import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static io.ruin.cache.ObjectID.PORTAL_OF_CHAMPIONS;

public class ObjectDef {

    public static boolean aBool1550 = false;
    private static byte[] EMPTY_BUFFER = new byte[] {0};

    public static Map<Integer, ObjectDef> LOADED = Maps.newConcurrentMap();
    public static ObjectDef[] LOADED_EXTRA = new ObjectDef[10];

    @SuppressWarnings("Duplicates")
    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        int size = index.getLastFileId(6) + 1;
        for (int id = 0; id < size; id++) {
            byte[] data = index.getFile(6, id);
            if (data != null) {
                ObjectDef def = new ObjectDef();
                def.id = id;
                def.decode(new InBuffer(data));
                if (def.someFlag) {
                    def.clipType = 0;
                    def.tall = false;
                }
                if (StringUtils.vowelStart(def.name))
                    def.descriptiveName = "an " + def.name;
                else
                    def.descriptiveName = "a " + def.name;
                LOADED.put(id, def);
            }
        }
    }

    public static void forEach(Consumer<ObjectDef> consumer) {
        for (ObjectDef def : LOADED.values()) {
            if (def != null)
                consumer.accept(def);
        }
    }

    public static ObjectDef get(int id) {
        return LOADED.get(id);
    }


    /**
     * Custom data
     */

    public String descriptiveName;

    public ObjectAction[] defaultActions;

    public HashMap<Integer, ItemObjectAction> itemActions;

    public ItemObjectAction defaultItemAction;

    public boolean bank;

    /**
     * Door data
     */

    public boolean gateType;

    public boolean longGate;

    public int doorOppositeId = -1;

    public boolean doorReversed, doorClosed;

    public int doorOpenSound = -1, doorCloseSound = -1;

    public boolean reversedConstructionDoor;

    /**
     * Separator
     */

    public int id;
    public int type22Int = -1;
    public int unknownOpcode75 = -1;
    public String name = "null";
    public int xLength = 1;
    public int yLength = 1;
    public int clipType = 2;
    public boolean tall = true;
    public boolean aBool1552 = false;
    public int unknownOpcode24 = -1; // idle animation
    public int anInt1595 = 16;
    public int mapMarkerId = -1;
    public boolean aBool1580 = true;
    public int anInt1578 = -1;
    public boolean type22Boolean = false;
    public int unknownOpcode78 = -1;
    public int unknownOpcode_78_79 = 0;
    public int anInt1548 = 0;
    public int anInt1571 = 0;
    public int[] anIntArray1597;
    public int[] showIds;
    public String[] options = new String[5];
    public int[] modelTypes;
    public int[] modelIds;
    int anInt1569 = -1;
    boolean aBool1582 = false;
    int unknownOpcode29 = 0;
    int anInt1575 = 0;
    public short[] originalModelColors;
    public short[] modifiedModelColors;
    short[] aShortArray1596;
    short[] aShortArray1563;
    public boolean verticalFlip = false;
    public int render0x1 = 128;
    public int render0x2 = 128;
    public int render0x4 = 128;
    int anInt1584 = 0;
    int anInt1585 = 0;
    int anInt1586 = 0;
    public boolean someFlag = false;
    public int varpBitId = -1;
    public int varpId = -1;

    public int someDirection;

    private void decode(InBuffer in) {
        for (;;) {
            int opcode = in.readUnsignedByte();
            if (opcode == 0)
                break;
            decode(in, opcode);
        }
        if (id == ObjectID.GRAND_EXCHANGE_BOOTH) {
            name = "Trading Post";
            options[0] = "Open";
            options[1] = "Coffer";
            options[2] = "Guide";
        } else if (id == 50001) {
            //custom home altar
            options[0] = "Pray-at";
            options[1] = "Spellbook";
        } else if (id == 23311) {
            //custom home altar
            options[0] = "Teleport";
            options[1] = "Previous Teleport";
        } else if (id == 11833) {
            //fight caves entrance
            options[1] = "Practice";
        } else if (id == 32758) {
            //rfd chest
            name = "Loyalty Chest";
            options[0] = "Loot";
            options[1] = "About";
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if(id == 32759) {
            name = "Loyalty Chest";
            options[0] = null;
            options[1] = null;
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if(id == 3192) {
            name = "PvP Leaderboard";
            options[0] = "Edge PKing";
            options[1] = "Deep Wild PKing";
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == 31379) {
            //uhh i hope 31379 isn't ever used
            name = "Donation table";
            type22Int = 1;
            xLength = yLength = 2;
            type22Boolean = false;
            unknownOpcode75 = 1;
            showIds = null;
            modelIds = new int[]{32153};
            varpBitId = -1;
        } else if (id == 31380) {
            //uhh i hope 31380 isn't ever used
            name = "Rejuvenation pool";
            type22Int = 1;
            xLength = yLength = 2;
            unknownOpcode24 = 7304;
            unknownOpcode_78_79 = 3;
            tall = false;
            options[0] = "Drink";
            unknownOpcode78 = 2149;
            clipType = 1;
            type22Boolean = false;
            showIds = null;
            unknownOpcode29 = 40;
            modelIds = new int[]{32101};
            varpBitId = -1;
        } else if (id == PORTAL_OF_CHAMPIONS) {
            //home teleport portal - where is this portal even used on real rs?
            name = "Kronos Teleporter";
            options[0] = "Teleport";
            options[1] = "Teleport-previous";
        } else if (id == 25203) {
            //decapitated elvarg corpse
            options[0] = "Loot";
        } else if (id == 29226) {
            //pet list
            name = "Pet list";
            options[4] = null;
        } else if (id == 6045) {
            //jail mine cart
            options[0] = "Dump-ore";
        } else if (id == 3581) {
            //tournament ticket exchange
            name = "Ticket exchange";
            options[0] = "Use";
        } else if (id == 15477) {
            //raids mumbo jumbo
            name = "Chambers of Xeric";
            options[1] = null;
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == 26714) {
            //mounted max cape
            options[0] = options[3] = null;
        } else if (id == 11508 || id == 11509) {
            //curtain
            clipType = 0;
        } else if (id == 30352) {
            name = "test";
            options[1] = "Practice";
        } else if(id == 539) {
            name = "Donator Area";
        } else if(id == 33114) {
            name = "PvP Supply Chest";
            options[0] = "Check-timer";
        } else if(id == 33115) {
            name = "PvP Supply Chest";
        } else if(id == 31583) {
            name = "PvP Supply Chest";
        } else if(id == 32572) {
            name = "Bloody Chest";
            options[1] = "Information";
        } else if(id == 31622) {
            name = "Ket'ian Wilderness Boss Portal";
        } else if(id == 31621) {
            name = "Wilderness Portal";
        } else if(id == 31626) {
            name = "Tournament Entrance";
        } else if(id == 32573) {
            name = "Bloody Chest";
            options[0] = null;
            options[1] = null;
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == 30169) { // Dagannoth kings crack
            options[0] = "Instance";
            options[1] = "Peek";
        } else if (id == 1816) { // KBD Lever
            options[1] = "Instance";
            options[2] = "Commune";
        } else if (id == 535) { // Thermonuclear smoke devil crevice
            options[1] = "Instance";
            options[2] = "Peek";
        } else if (id == 23104) { // Cerberus iron winch
            options[1] = "Instance";
            options[2] = "Peek";
        } else if (id == 29705) { // KQ Crack
            options[0] = "Instance";
            options[1] = "Peek";
        } else if (id >= 26502 && id <= 26505) { // GWD boss doors
            options[1] = "Instance";
            options[2] = "Peek";
        } else if (id == 4407) { // pvm instance portal
            name = "Boss instance portal";
            options[0] = "Use";
        } else if(id == 19038) { //christmas tree
            name = "Christmas tree";
            options[0] = "Grab-present";
        } else if(id == 29709) {
            name = "Snowball Exchange";
            options[0] = "Open";
            options[1] = "Information";
        } else if (id == 40000) {
            copy(32546);
            name = "Giveaway booth";
        } else if(id == 40001) {
            copy(4525);
            options[0] = "Exit";
            options[1] = null;
            options[2] = null;
        } else if(id == 40002) {
            copy(32424);
            name = "Consumables";
        } else if(id == 40003) {
            copy(32425);
            name = "Equipment";
        } else if(id == 2654) {
            name = "Bloody Fountain";
            options[0] = "Drink";
            options[1] = null;
            render0x2 = 200;
            render0x1 = 200;
            render0x4 = 200;
            xLength = 3;
            yLength = 3;
        } else if(id == 40004) {
            copy(29241);
            options[4] = null;
        } else if(id == 40005) {
            copy(31858);
            options[0] = "Pray-at";
            options[1] = "Spellbook";
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if(id == 40006) {
            copy(4537);
            options[4] = null;
        } else if(id == 40007) {
            copy(13619);
            name = "Fun PVP Portal";
            options[4] = null;
        } else if(id == 40008) {
            copy(20839);
            name = "Tournament Barrier";
            options[0] = "Use";
        } else if(id == 40009) {
            copy(26714);
            name = "Mounted Max Cape";
            options[0] = null;
            options[1] = null;
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if(id == 31846) {
            name = "Tournament Information";
            options[0] = "Read";
        } else if(id == 29087) {
            name = "Ticket Exchange";
            options[0] = "Use";
        } else if (id == 4451 || id == 14321 || id == 14329 || id == 14324 || id == 14333) { // Asset non-projectile clip for pc portal decortations.
            clipType = 0;
            tall = false;
        } else if (id == 4390) {
            name = "Donator Zone portal";
        }
    }

    private void decode(InBuffer in, int i) {
        if (i == 1) {
            int i_7_ = in.readUnsignedByte();
            if (i_7_ > 0) {
                if (modelIds == null || aBool1550) {
                    modelTypes = new int[i_7_];
                    modelIds = new int[i_7_];
                    for (int i_8_ = 0; i_8_ < i_7_; i_8_++) {
                        modelIds[i_8_] = in.readUnsignedShort();
                        modelTypes[i_8_] = in.readUnsignedByte();
                    }
                } else in.skip(i_7_ * 3);
            }
        } else if (i == 2)
            name = in.readString();
        else if (i == 5) {
            int i_9_ = in.readUnsignedByte();
            if (i_9_ > 0) {
                if (modelIds == null || aBool1550) {
                    modelTypes = null;
                    modelIds = new int[i_9_];
                    for (int i_10_ = 0; i_10_ < i_9_; i_10_++)
                        modelIds[i_10_] = in.readUnsignedShort();
                } else in.skip(i_9_ * 2);
            }
        } else if (i == 14)
            xLength = in.readUnsignedByte();
        else if (i == 15)
            yLength = in.readUnsignedByte();
        else if (i == 17) {
            clipType = 0;
            tall = false;
        } else if (i == 18)
            tall = false;
        else if (i == 19)
            type22Int = in.readUnsignedByte();
        else if (i == 21)
            anInt1569 = 0;
        else if (i == 22)
            aBool1582 = true;
        else if (i == 23)
            aBool1552 = true;
        else if (i == 24) {
            unknownOpcode24 = in.readUnsignedShort();
            if (unknownOpcode24 == 65535)
                unknownOpcode24 = -1;
        } else if (i == 27)
            clipType = 1;
        else if (i == 28)
            anInt1595 = in.readUnsignedByte();
        else if (i == 29)
            unknownOpcode29 = in.readByte();
        else if (i == 39)
            anInt1575 = in.readByte() * 25;
        else if (i >= 30 && i < 35) {
            options[i - 30] = in.readString();
            if (options[i - 30].equalsIgnoreCase("Hidden"))
                options[i - 30] = null;
        } else if (i == 40) {
            int i_11_ = in.readUnsignedByte();
            originalModelColors = new short[i_11_];
            modifiedModelColors = new short[i_11_];
            for (int i_12_ = 0; i_12_ < i_11_; i_12_++) {
                originalModelColors[i_12_] = (short) in.readUnsignedShort();
                modifiedModelColors[i_12_] = (short) in.readUnsignedShort();
            }
        } else if (i == 41) {
            int i_13_ = in.readUnsignedByte();
            aShortArray1596 = new short[i_13_];
            aShortArray1563 = new short[i_13_];
            for (int i_14_ = 0; i_14_ < i_13_; i_14_++) {
                aShortArray1596[i_14_] = (short) in.readUnsignedShort();
                aShortArray1563[i_14_] = (short) in.readUnsignedShort();
            }
        } else if (i == 60) //this was removed
            mapMarkerId = in.readUnsignedShort();
        else if (i == 62)
            verticalFlip = true;
        else if (i == 64)
            aBool1580 = false;
        else if (i == 65)
            render0x1 = in.readUnsignedShort();
        else if (i == 66)
            render0x2 = in.readUnsignedShort();
        else if (i == 67)
            render0x4 = in.readUnsignedShort();
        else if (i == 68)
            anInt1578 = in.readUnsignedShort();
        else if (i == 69)
            someDirection = in.readUnsignedByte();
        else if (i == 70)
            anInt1584 = in.readShort();
        else if (i == 71)
            anInt1585 = in.readShort();
        else if (i == 72)
            anInt1586 = in.readShort();
        else if (i == 73)
            type22Boolean = true;
        else if (i == 74)
            someFlag = true;
        else if (i == 75)
            unknownOpcode75 = in.readUnsignedByte();
        else if (i == 77 || i == 92) {
            varpBitId = in.readUnsignedShort();
            if (varpBitId == 65535)
                varpBitId = -1;
            varpId = in.readUnsignedShort();
            if (varpId == 65535)
                varpId = -1;
            int i_17_ = -1;
            if (i == 92) {
                i_17_ = in.readUnsignedShort();
                if (i_17_ == 65535)
                    i_17_ = -1;
            }
            int i_18_ = in.readUnsignedByte();
            showIds = new int[i_18_ + 2];
            for (int i_19_ = 0; i_19_ <= i_18_; i_19_++) {
                showIds[i_19_] = in.readUnsignedShort();
                if (showIds[i_19_] == 65535)
                    showIds[i_19_] = -1;
            }
            showIds[i_18_ + 1] = i_17_;
        } else if (i == 78) {
            unknownOpcode78 = in.readUnsignedShort();
            unknownOpcode_78_79 = in.readUnsignedByte();
        } else if (i == 79) {
            anInt1548 = in.readUnsignedShort();
            anInt1571 = in.readUnsignedShort();
            unknownOpcode_78_79 = in.readUnsignedByte();
            int i_15_ = in.readUnsignedByte();
            anIntArray1597 = new int[i_15_];
            for (int i_16_ = 0; i_16_ < i_15_; i_16_++)
                anIntArray1597[i_16_] = in.readUnsignedShort();
        } else if (i == 81)
            anInt1569 = in.readUnsignedByte() * 256;
    }

    public boolean isClippedDecoration() {
        return type22Int != 0 || clipType == 1 || type22Boolean;
    }

    public boolean hasOption(String... searchOptions) {
        return getOption(searchOptions) != -1;
    }

    public int getOption(String... searchOptions) {
        if (options != null) {
            for (String s : searchOptions) {
                for (int i = 0; i < options.length; i++) {
                    String option = options[i];
                    if (s.equalsIgnoreCase(option))
                        return i + 1;
                }
            }
        }
        return -1;
    }

    private void copy(int id) {
        if (this.id < id) {
            System.err.println("Unable to copy Object where target has lower id.");
            return;
        }

        ObjectDef from = LOADED.get(id);

        try {
            type22Int = from.type22Int;
            unknownOpcode75 = from.unknownOpcode75;
            name = from.name;
            xLength = from.xLength;
            yLength = from.yLength;
            clipType = from.clipType;
            tall = from.tall;
            aBool1552 = from.aBool1552;
            unknownOpcode24 = from.unknownOpcode24;
            anInt1595 = from.anInt1595;
            mapMarkerId = from.mapMarkerId;
            aBool1580 = from.aBool1580;
            anInt1578 = from.anInt1578;
            type22Boolean = from.type22Boolean;
            unknownOpcode78 = from.unknownOpcode78;
            unknownOpcode_78_79 = from.unknownOpcode_78_79;
            anInt1548 = from.anInt1548;
            anInt1571 = from.anInt1571;
            anIntArray1597 = from.anIntArray1597 == null ? null : Arrays.copyOf(from.anIntArray1597, from.anIntArray1597.length);
            showIds = from.showIds == null ? null : Arrays.copyOf(from.showIds, from.showIds.length);
            options = from.options == null ? null : Arrays.copyOf(from.options, from.options.length);
            modelTypes = from.modelTypes == null ? null : Arrays.copyOf(from.modelTypes, modelTypes.length);
            modelIds = from.modelIds == null ? null : Arrays.copyOf(from.modelIds, from.modelIds.length);
            anInt1569 = from.anInt1569;
            aBool1582 = from.aBool1582;
            unknownOpcode29 = from.unknownOpcode29;
            anInt1575 = from.anInt1575;
            originalModelColors = from.originalModelColors == null ? null : Arrays.copyOf(from.originalModelColors, from.originalModelColors.length);
            modifiedModelColors = from.modifiedModelColors == null ? null : Arrays.copyOf(from.modifiedModelColors, from.modifiedModelColors.length);
            aShortArray1596 = from.aShortArray1596 == null ? null : Arrays.copyOf(from.aShortArray1596, from.aShortArray1596.length);
            aShortArray1563 = from.aShortArray1563 == null ? null : Arrays.copyOf(from.aShortArray1563, from.aShortArray1563.length);
            verticalFlip = from.verticalFlip;
            render0x1 = from.render0x1;
            render0x2 = from.render0x2;
            render0x4 = from.render0x4;
            anInt1584 = from.anInt1584;
            anInt1585 = from.anInt1585;
            anInt1586 = from.anInt1586;
            someFlag = from.someFlag;
            varpBitId = from.varpBitId;
            varpId = from.varpId;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
