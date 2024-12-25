package io.ruin.cache;

import com.google.common.collect.Maps;
import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.World;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.combat.*;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.presets.Preset;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemGroundItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.impl.ItemBreaking;
import io.ruin.model.item.actions.impl.ItemSet;
import io.ruin.model.item.actions.impl.ItemUpgrading;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.model.item.actions.impl.combine.ItemCombining;
import io.ruin.model.item.listeners.IncomingHitListener;
import io.ruin.model.item.listeners.OutgoingHitListener;
import io.ruin.model.map.ground.GroundItemAction;
import io.ruin.model.shop.Currency;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.herblore.Potion;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.mining.Pickaxe;
import io.ruin.model.skills.smithing.SmithBar;
import io.ruin.model.skills.woodcutting.Hatchet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.ruin.cache.ItemID.BLOOD_FRAGMENT;

public class ItemDef {

    public static Map<Integer, ItemDef> cached = Maps.newConcurrentMap();
    private boolean currency;
    private boolean currencyChecked;

    public boolean isCurrency(){
        if(!currencyChecked){
            currency = Currency.itemCurrencyStream().anyMatch(c -> c.getCurrencyItemId() == id);
            currencyChecked = true;
        }
        return currency;
    }

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        int size = index.getLastFileId(10) + 1;
        for(int id = 0; id < size; id++) {
            ItemDef def = new ItemDef();
            def.id = id;
            byte[] data = index.getFile(10, def.id);
            if (data != null) {
                def.decode(new InBuffer(data));
                if (def.stackable)
                    def.descriptiveName = "some " + def.name.toLowerCase();
                else if (StringUtils.vowelStart(def.name))
                    def.descriptiveName = "an " + def.name;
                else
                    def.descriptiveName = "a " + def.name;
                cached.put(id, def);
            }
        }
    }

    public static void forEach(Consumer<ItemDef> consumer) {
        for(ItemDef def : cached.values()) {
            if(def != null)
                consumer.accept(def);
        }
    }

    public static int findId(String name) {
        for(ItemDef def : cached.values()) {
            if(def != null && def.name.toLowerCase().contains(name))
                return def.id;
        }
        System.err.println("Failed to find item with name: " + name + " | " + new Throwable().getStackTrace()[1].toString());
        return -1;
    }

    public static ItemDef get(int id) {
        return cached.get(id);
    }



    /**
     * Stored data
     */

    public int id;

    public boolean tradeable;

    public boolean wilderness;

    public String examine;

    public double weightInventory, weightEquipment;

    public String descriptiveName;

    public int equipSlot = -1;

    public int[] equipReqs;

    public int[] equipBonuses;

    public ShieldType shieldType;

    public WeaponType weaponType;

    public RangedWeapon rangedWeapon;

    public RangedAmmo rangedAmmo;

    public boolean twoHanded;

    public boolean hideHair, hideBeard, hideArms;

    public boolean maxType;

    public int lowAlchValue, highAlchValue;

    public Achievement achievement;
    public boolean achievementReqIsIronmanOnly;

    /**
     * Custom data
     */

    public int dropOption = -1;

    public int protectValue;
    public boolean neverProtect;
    public boolean dropAnnounce;
    public int equipOption = -1;

    public Special special;

    public int rangedLevel = 1;

    public ItemAction[] inventoryActions, equipmentActions;

    public GroundItemAction[] groundActions;

    public ItemItemAction defaultPrimaryItemItemAction;

    public ArrayList<ItemItemAction> primaryItemItemActions, secondaryItemItemActions;

    public ItemGroundItemAction defaultItemGroundItemAction;

    public Map<Integer, ItemGroundItemAction> itemGroundItemActions;

    public ItemAction tickAction, attackAction, defendAction;

    public int pickupOption = -1;

    public Hatchet hatchet;

    public Pickaxe pickaxe;

    public boolean sharpWeapon;

    public boolean consumable;

    public Potion potion;

    public int potionDoses;

    public Rune rune;

    public Rune staffRune;

    public boolean allowFruit;

    public boolean allowPilesToNote;

    public SmithBar smithBar;

    public ItemSet itemSet;

    public ClueType clueType;

    public Crop seedType;
    public Crop produceOf;

    public ItemBreaking brokenFrom;
    public ItemBreaking breakTo;
    public ItemUpgrading upgradedFrom;
    public ItemCombining combinedFrom;
    public int breakId;

    public int bmShopPrice = -1;

    public int sigmundBuyPrice;

    public boolean slayerHelm;

    public boolean leafBladed;

    public boolean slayerBoostMelee;

    public boolean slayerBoostAll;

    public boolean[] godItem = new boolean[4]; // protection against gwd aggro

    public BankWithdrawListener bankWithdrawListener;

    public Pet pet;

    public boolean graniteMaul;

    public boolean free;

    public boolean coxItem;

    /**
     * Cache data
     */

    public String name = "null";
    public int zoom2d = 2000;
    public int xan2d = 0;
    public int yan2d = 0;
    public int zan2d = 0;
    public int xof2d = 0;
    public int yof2d = 0;
    public boolean stackable;
    public int value = 1;
    public boolean members = false;
    public String[] groundOptions = {null, null, "Take", null, null};
    public String[] inventoryOptions = {null, null, null, null, "Drop"};
    public int notedId = -1;
    public int notedTemplateId = -1;
    public int ambient = 0;
    public int contrast = 0;
    public int team = 0;
    public boolean grandExchange = false;
    public int placeholderMainId = -1;
    public int placeholderTemplateId = -1;
    public int anInt1504 = -1;
    int anInt1493 = -1;
    int maleOffset = 0;
    int anInt1467 = -1;
    int anInt1496 = -1;
    int femaleOffset = 0;
    int anInt1498 = -1;
    int anInt1499 = -1;
    int maleHeadModel = -1;
    int anInt1501 = -1;
    int femaleHeadModel = -1;
    int anInt1503 = -1;
    int resizeX = 128;
    int resizeY = 128;
    int resizeZ = 128;
    int anInt1489 = -1;
    int anInt1515 = -1;
    public int inventoryModel;
    public short[] colorFind;
    public short[] colorReplace;
    short[] textureFind;
    short[] textureReplace;
    int[] anIntArray1497;
    int[] anIntArray1505;
    public HashMap<Object, Object> attributes;

    /**
     * Decoding
     */

    private void decode(InBuffer buffer) {
        for(;;) {
            int opcode = buffer.readUnsignedByte();
            if(opcode == 0)
                break;
            decode(buffer, opcode);
        }
        if(id == 2742) {
            name = "Coin Casket (small)";
            inventoryOptions[1] = null;
        }

        if(id == 2744) {
            name = "Coin Casket (medium)";
            inventoryOptions[1] = null;
        }

        if(id == 2746) {
            name = "Coin Casket (large)";
            inventoryOptions[1] = null;
        }

        if(id == 2748) {
            name = "Coin Casket (giant)";
            inventoryOptions[1] = null;
        }

        if (id == 1464) {
            name = "Vote Lottery Ticket";
            stackable = false;
        }

        if(id == 13190) { //bond
            name = World.type.getWorldName() + " Credit";
            stackable = true;
            value = 0; //important for sigmund
            inventoryOptions[0] = "Claim";
            inventoryOptions[2] = null;
        }

        if (id == Preset.DRAGON_2H_SWORD || id == 13024 || id == 13026 || id == 9243 || id == 6109 || id == 3751) {
            value = 0;
        }

        if(id == 6199) {
            inventoryOptions[2] = "Gift";
        }

        if(id == 290) {
            name = "Super Mystery Box";
            inventoryOptions[1] = "Open";
            inventoryOptions[2] = "Gift";
        }
        if(id == 30185) {
            name = "Summer Mystery Box";
            inventoryOptions[1] = "Open";
            inventoryOptions[2] = "Gift";
        }

        if(id == 6828) {
            name = "Pet Mystery Box";
            inventoryOptions[1] = "Open";
            inventoryOptions[2] = "Gift";
        }

        if(id == 6829) {
            name = "Voting Mystery Box";
            inventoryOptions[1] = "Open";
            inventoryOptions[2] = "Gift";
        }

        if(id == 6831) {
            name = "3rd age Mystery Box";
            inventoryOptions[1] = "Open";
            inventoryOptions[2] = "Gift";
        }

        if(id == 1505) {
            name = "Obelisk destination scroll";
        }

        if(id == 4067) {
            name = "Vote ticket";
        }

        if(id == 10834) {
            name = "Dice bag";
            inventoryOptions[0] = "Roll 4-sided";
            inventoryOptions[1] = "Roll 12-sided";
            inventoryOptions[2] = "Roll 100-sided";
        }

        if (id == 9477) {
            name = "Coinbox";
            inventoryOptions[0] = "Open";
            stackable = false;
            tradeable = true;
        }

        if (id == 3455 || id == 3457 || id == 3458) {
            name = "Clue key";
            inventoryOptions[3] = "Check-Hint";
        }

        if(id == 6806) {
            name = "Green Skin Scroll";
            inventoryOptions[0] = "Redeem";
        }

        if(id == 6807) {
            name = "Blue Skin Scroll";
            inventoryOptions[0] = "Redeem";
        }

        if(id == 6808) {
            name = "Purple Skin Scroll";
            inventoryOptions[0] = "Redeem";
        }

        if(id == 19625) {
            name = "Home teleport";
        }
        if(id == 8007) {
            inventoryOptions[1] = null;
        }
        if(id >= 1567 && id <= 1572)
            name = "Overgrown cat";
        /* Quick option for repairing all broken items */
        if(name.endsWith("(broken)"))
            inventoryOptions[0] = "Fix";
        if(id == 11169) {
            name = World.type.getWorldName() + " Herald";
            inventoryOptions[0] = "View-updates";
            inventoryOptions[1] = null;
            inventoryOptions[4] = "Destroy";
        }
        if(id == 12746 || (id >= 12748 && id <= 12756)) {
            inventoryOptions[0] = "Info";
            inventoryOptions[1] = "Redeem";
        }
        if(id == 621) {
            name = "Tournament voucher";
        }
        if(id == BLOOD_FRAGMENT) {
            name = "Blood Fragments";
        }
        if(id == 607) {
            name = "Rare Drop Scroll";
            inventoryOptions[0] = "Activate";
        }
        if(id == 608) {
            name = "Pet Drop Scroll";
            inventoryOptions[0] = "Activate";
        }
        if(id == 6758) {
            name = "Bonus Exp Scroll";
            inventoryOptions[0] = "Activate";
        }
        if(id  == 7671)
            name += " (red)";
        if (id == 7673)
            name += " (blue)";

        if(id == 2730) {
            name = "Wilderness Reward Box (small)";
            inventoryOptions[0] = "Open";
            inventoryOptions[1] = null;
        }

        if(id == 2732) {
            name = "Wilderness Reward Box (medium)";
            inventoryOptions[0] = "Open";
            inventoryOptions[1] = null;
        }

        if(id == 2734) {
            name = "Wilderness Reward Box (large)";
            inventoryOptions[0] = "Open";
            inventoryOptions[1] = null;
        }

        if(id == 2736) {
            name = "Wilderness Reward Box (giant)";
            inventoryOptions[0] = "Open";
            inventoryOptions[1] = null;
        }

        if(id == 13215) {
            name = "Bloody Token";
            stackable = true;
        }

        if(id == 22330) {
            name = "PVP Armour Mystery Box";
            inventoryOptions[0] = "Open";
            inventoryOptions[1] = "Gift";
        }

        if(id == 3606) {
            name = "Bloody key (easy)";
        }

        if(id == 3608) {
            name = "Bloody key (medium)";
        }

        if(id == 7297) {
            name = "Bloody key (hard)";
        }

        if(id == 11918) {
            name = "Santa Outfit Box";
            inventoryOptions[0] = "Open";
        }

        if(id == 12897) {
            name = "Anti-Santa Outfit Box";
            inventoryOptions[0] = "Open";
        }

        if(id == 22816 || id == 22817) {
            inventoryOptions[4] = "Destroy";
        }

        if(id == 21227) {
            name = "Easter Egg";
            inventoryOptions[0] = "Open";
        }

        if(id == 11028) {
            stackable = true;
        }

        if(id == 8943) {
            name = "Wilderness key (1M OSRS)";
            inventoryOptions[0] = "Information";
            inventoryOptions[3] = null;
            inventoryOptions[4] = "Destroy";
        }

        if(id == 8944) {
            name = "Wilderness key (5M OSRS)";
            inventoryOptions[0] = "Information";
            inventoryOptions[3] = null;
            inventoryOptions[4] = "Destroy";
        }

        if(id == 8945) {
            name = "Wilderness key (10M OSRS)";
            inventoryOptions[0] = "Information";
            inventoryOptions[3] = null;
            inventoryOptions[4] = "Destroy";
        }

        if(id == 8946) {
            name = "Wilderness key (25M OSRS)";
            inventoryOptions[0] = "Information";
            inventoryOptions[3] = null;
            inventoryOptions[4] = "Destroy";
        }

        if(id == 8947) {
            name = "Wilderness key (50M OSRS)";
            inventoryOptions[0] = "Information";
            inventoryOptions[3] = null;
            inventoryOptions[4] = "Destroy";
        }

        if(id == 8948) {
            name = "Wilderness key (100M OSRS)";
            inventoryOptions[0] = "Information";
            inventoryOptions[3] = null;
            inventoryOptions[4] = "Destroy";
        }
    }

    private void decode(InBuffer buffer, int opcode) {
        if(opcode == 1)
            inventoryModel = buffer.readUnsignedShort();
        else if(opcode == 2)
            name = buffer.readString();
        else if(opcode == 4)
            zoom2d = buffer.readUnsignedShort();
        else if(opcode == 5)
            xan2d = buffer.readUnsignedShort();
        else if(opcode == 6)
            yan2d = buffer.readUnsignedShort();
        else if(opcode == 7) {
            xof2d = buffer.readUnsignedShort();
            if(xof2d > 32767)
                xof2d -= 65536;
        } else if(opcode == 8) {
            yof2d = buffer.readUnsignedShort();
            if(yof2d > 32767)
                yof2d -= 65536;
        } else if(opcode == 11)
            stackable = true;
        else if(opcode == 12)
            value = buffer.readInt();
        else if(opcode == 16)
            members = true;
        else if(opcode == 23) {
            anInt1504 = buffer.readUnsignedShort();
            maleOffset = buffer.readUnsignedByte();
        } else if(opcode == 24)
            anInt1493 = buffer.readUnsignedShort();
        else if(opcode == 25) {
            anInt1467 = buffer.readUnsignedShort();
            femaleOffset = buffer.readUnsignedByte();
        } else if(opcode == 26)
            anInt1496 = buffer.readUnsignedShort();
        else if(opcode >= 30 && opcode < 35) {
            groundOptions[opcode - 30] = buffer.readString();
            if(groundOptions[opcode - 30].equalsIgnoreCase("Hidden"))
                groundOptions[opcode - 30] = null;
        } else if(opcode >= 35 && opcode < 40)
            inventoryOptions[opcode - 35] = buffer.readString();
        else if(opcode == 40) {
            int i_0_ = buffer.readUnsignedByte();
            colorFind = new short[i_0_];
            colorReplace = new short[i_0_];
            for(int i_1_ = 0; i_1_ < i_0_; i_1_++) {
                colorFind[i_1_] = (short) buffer.readUnsignedShort();
                colorReplace[i_1_] = (short) buffer.readUnsignedShort();
            }
        } else if(opcode == 41) {
            int i_2_ = buffer.readUnsignedByte();
            textureFind = new short[i_2_];
            textureReplace = new short[i_2_];
            for(int i_3_ = 0; i_3_ < i_2_; i_3_++) {
                textureFind[i_3_] = (short) buffer.readUnsignedShort();
                textureReplace[i_3_] = (short) buffer.readUnsignedShort();
            }
        } else if(opcode == 65)
            grandExchange = true;
        else if(opcode == 78)
            anInt1498 = buffer.readUnsignedShort();
        else if(opcode == 79)
            anInt1499 = buffer.readUnsignedShort();
        else if(opcode == 90)
            maleHeadModel = buffer.readUnsignedShort();
        else if(opcode == 91)
            femaleHeadModel = buffer.readUnsignedShort();
        else if(opcode == 92)
            anInt1501 = buffer.readUnsignedShort();
        else if(opcode == 93)
            anInt1503 = buffer.readUnsignedShort();
        else if(opcode == 95)
            zan2d = buffer.readUnsignedShort();
        else if(opcode == 97)
            notedId = buffer.readUnsignedShort();
        else if(opcode == 98)
            notedTemplateId = buffer.readUnsignedShort();
        else if(opcode >= 100 && opcode < 110) {
            if(anIntArray1497 == null) {
                anIntArray1497 = new int[10];
                anIntArray1505 = new int[10];
            }
            anIntArray1497[opcode - 100] = buffer.readUnsignedShort();
            anIntArray1505[opcode - 100] = buffer.readUnsignedShort();
        } else if(opcode == 110)
            resizeX = buffer.readUnsignedShort();
        else if(opcode == 111)
            resizeY = buffer.readUnsignedShort();
        else if(opcode == 112)
            resizeZ = buffer.readUnsignedShort();
        else if(opcode == 113)
            ambient = buffer.readByte();
        else if(opcode == 114)
            contrast = buffer.readByte() * 5;
        else if(opcode == 115)
            team = buffer.readUnsignedByte();
        else if(opcode == 139)
            anInt1489 = buffer.readUnsignedShort();
        else if(opcode == 140)
            anInt1515 = buffer.readUnsignedShort();
        else if(opcode == 148)
            placeholderMainId = buffer.readUnsignedShort();
        else if(opcode == 149)
            placeholderTemplateId = buffer.readUnsignedShort();
        else if(opcode == 249) {
            int size = buffer.readUnsignedByte();
            if(attributes == null)
                attributes = new HashMap<>();
            for(int i = 0; i < size; i++) {
                boolean stringType = buffer.readUnsignedByte() == 1;
                int key = buffer.readMedium();
                if(stringType)
                    attributes.put(key, buffer.readString());
                else
                    attributes.put(key, buffer.readInt());
            }
        }
    }

    public int getOption(String... options) {
        if(inventoryOptions != null) {
            for(String s : options) {
                for(int i = 0; i < inventoryOptions.length; i++) {
                    String option = inventoryOptions[i];
                    if(s.equalsIgnoreCase(option))
                        return i + 1;
                }
            }
        }
        return -1;
    }

    public int getGroundOption(String... options) {
        if(groundOptions != null) {
            for(String s : options) {
                for(int i = 0; i < groundOptions.length; i++) {
                    String option = groundOptions[i];
                    if(s.equalsIgnoreCase(option))
                        return i + 1;
                }
            }
        }
        return -1;
    }

    public boolean hasOption(String... options) {
        return getOption(options) != -1;
    }

    public boolean isNote() {
        return notedId != -1 && notedTemplateId != -1;
    }

    public ItemDef fromNote() {
        return get(notedId);
    }

    public boolean isPlaceholder() {
        return placeholderMainId != -1 && placeholderTemplateId != -1;
    }

    public boolean hasPlaceholder() {
        return placeholderMainId != -1 && placeholderTemplateId == -1;
    }

    public int bankWithdrawListener(Player player, Item item, int amount) {
        if (bankWithdrawListener != null)
            return bankWithdrawListener.withdraw(player, item, amount);
        return 0;
    }

    /**
     * Predicate
     */

    @FunctionalInterface
    public interface ItemDefPredicate {

        boolean accept(ItemDef def, String name);

        default boolean test(ItemDef def) {
            return accept(def, def.name.toLowerCase());
        }

    }

    @FunctionalInterface
    public interface BankWithdrawListener {

        int withdraw(Player player, Item item, int amount);

    }

    /**
     * u brought this on urself tbh
     */
    private OutgoingHitListener postTargetDamageListener;
    private OutgoingHitListener preTargetDefendListener;
    private OutgoingHitListener postTargetDefendListener;
    private IncomingHitListener postDamageListener;
    private IncomingHitListener preDefendListener;
    private BiConsumer<Player, Item> onTick;
    private Consumer<Item> onDrop;


    /**
     * Adds a listener for when a player with this item equipped <b>deals damage</b>.
     */
    public void addPostTargetDamageListener(OutgoingHitListener listener) {
        if (postTargetDamageListener == null)
            postTargetDamageListener = listener;
        else
            postTargetDamageListener = postTargetDamageListener.andThen(listener);
    }

    public void postTargetDamage(Player player, Item item, Hit hit, Entity entity) {
        if (postTargetDamageListener != null)
            postTargetDamageListener.accept(player, item, hit, entity);
    }

    /**
     * Adds a listener for when a player with this item equipped <b>takes a hite</b>.
     */
    public void addPostDamageListener(IncomingHitListener event) {
        if (postDamageListener == null)
            postDamageListener = event;
        else
            postDamageListener = postDamageListener.andThen(event);
    }

    public void postDamage(Player player, Item item, Hit hit) {
        if (postDamageListener != null)
            postDamageListener.accept(player, item, hit);
    }

    /**
     * Adds a listener for when a player with this item equipped <b>is defending against a hit</b>.
     */

    public void addPreDefendListener(IncomingHitListener event) {
        if (preDefendListener == null)
            preDefendListener = event;
        else
            preDefendListener = preDefendListener.andThen(event);
    }

    public void preDefend(Player player, Item item, Hit hit) {
        if (preDefendListener != null)
            preDefendListener.accept(player, item, hit);
    }

    /**
     * Adds a listener for when a player with this item equipped <b>is attacking but the hit hasn't yet been dealt.</b>.
     */

    public void addPreTargetDefendListener(OutgoingHitListener event) {
        if (preTargetDefendListener == null)
            preTargetDefendListener = event;
        else
            preTargetDefendListener = preTargetDefendListener.andThen(event);
    }

    public void preTargetDefend(Player player, Item item, Hit hit, Entity victim) {
        if (preTargetDefendListener != null)
            preTargetDefendListener.accept(player, item, hit, victim);
    }


    public void addPostTargetDefendListener(OutgoingHitListener event) {
        if (postTargetDefendListener == null)
            postTargetDefendListener = event;
        else
            postTargetDefendListener = postTargetDefendListener.andThen(event);
    }

    public void postTargetDefend(Player player, Item item, Hit hit, Entity victim) {
        if (postTargetDefendListener != null)
            postTargetDefendListener.accept(player, item, hit, victim);
    }

    /**
     * Adds a listener on each tick while the player has this item equipped.
     */
    public void addOnTickEvent(BiConsumer<Player, Item> event) {
        if (onTick == null)
            onTick = event;
        else
            onTick = onTick.andThen(event);
    }

    public void onTick(Player player, Item item) {
        if (onTick != null)
            onTick.accept(player, item);
    }

    /**
     * Adds a listener for when a grounditem for this item is created
     */
    public void addOnDropEvent(Consumer<Item> event) {
        if (onDrop == null)
            onDrop = event;
        else
            onDrop = onDrop.andThen(event);
    }

    public void onDrop(Item item) {
        if (onDrop != null)
            onDrop.accept(item);
    }


}
