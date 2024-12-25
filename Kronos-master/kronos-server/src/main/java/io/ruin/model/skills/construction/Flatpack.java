package io.ruin.model.skills.construction;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.stat.StatType;

import java.util.Arrays;

public enum Flatpack {
    CRUDE_CHAIR(8496, Buildable.CRUDE_WOODEN_CHAIR),
    WOODEN_CHAIR(8498, Buildable.WOODEN_CHAIR),
    ROCKING_CHAIR(8500, Buildable.ROCKING_CHAIR),
    OAK_CHAIR(8502, Buildable.OAK_CHAIR),
    OAK_ARMCHAIR(8504, Buildable.OAK_ARMCHAIR),
    TEAK_ARMCHAIR(8506, Buildable.TEAK_ARMCHAIR),
    MAHOGANY_ARMCHAIR(8508, Buildable.MAHOGANY_ARMCHAIR),

    WOODEN_BOOKCASE(8510, Buildable.WOODEN_BOOKCASE),
    OAK_BOOKCASE(8512, Buildable.OAK_BOOKCASE),
    MAHOGANY_BOOKCASE(8514, Buildable.MAHOGANY_BOOKCASE),

    BEER_BARREL(8516, Buildable.BEER_BARREL),
    CIDER_BARREL(8518, Buildable.CIDER_BARREL),
    ASGARNIAN_ALE_BARREL(8520, Buildable.ASGARNIAN_ALE_BARREL),
    GREENMANS_ALE_BARREL(8522, Buildable.GREENMANS_ALE_BARREL),
    DRAGON_BITTER(8524, Buildable.DRAGON_BITTER_BARREL),
    CHEFS_DELIGHT_BARREL(8526, Buildable.CHEFS_DELIGHT_BARREL),

    KITCHEN_TABLE(8528, Buildable.WOODEN_KITCHEN_TABLE),
    OAK_KITCHEN_TABLE(8530, Buildable.OAK_KITCHEN_TABLE),
    TEAK_KITCHEN_TABLE(8532, Buildable.TEAK_KITCHEN_TABLE),

    WOOD_DINING_TABLE(8548, Buildable.WOOD_DINING_TABLE),
    OAK_DINING_TABLE(8550, Buildable.OAK_DINING_TABLE),
    CARVED_OAK_TABLE(8552, Buildable.CARVED_OAK_TABLE),
    TEAK_TABLE(8554, Buildable.TEAK_DINING_TABLE),
    CARVED_TEAK_TABLE(8556, Buildable.CARVED_TEAK_TABLE),
    MAHOGANY_TABLE(8558, Buildable.MAHOGANY_DINING_TABLE),
    OPULENT_TABLE(8560, Buildable.OPULENT_DINING_TABLE),

    WOODEN_BENCH(8562, Buildable.WOODEN_BENCH),
    OAK_BENCH(8564, Buildable.OAK_BENCH),
    CARVED_OAK_BENCH(8566, Buildable.CARVED_OAK_BENCH),
    TEAK_BENCH(8568, Buildable.TEAK_BENCH),
    CARVED_TEAK_BENCH(8570, Buildable.CARVED_TEAK_BENCH),
    MAHOGANY_BENCH(8572, Buildable.MAHOGANY_BENCH),
    GILDED_BENCH(8574, Buildable.GILDED_BENCH),

    WOODEN_BED(8576, Buildable.WOODEN_BED),
    OAK_BED(8578, Buildable.OAK_BED),
    LARGE_OAK_BED(8580, Buildable.LARGE_OAK_BED),
    TEAK_BED(8582, Buildable.TEAK_BED),
    LARGE_TEAK_BED(8584, Buildable.LARGE_TEAK_BED),
    FOUR_POSTER(8586, Buildable.FOUR_POSTER_BED),
    GILDED_FOUR_POSTER(8588, Buildable.GILDED_FOUR_POSTER_BED),

    SHAVING_STAND(8596, Buildable.SHAVING_STAND),
    OAK_SHAVING_STAND(8598, Buildable.OAK_SHAVING_STAND),
    OAK_DRESSER(8600, Buildable.OAK_DRESSER),
    TEAK_DRESSER(8602, Buildable.TEAK_DRESSER),
    FANCY_TEAK_DRESSER(8604, Buildable.FANCY_TEAK_DRESSER),
    MAHOGANY_DRESSER(8606, Buildable.MAHOGANY_DRESSER),
    GILDED_DRESSER(8608, Buildable.GILDED_DRESSER),

    SHOE_BOX(8610, Buildable.SHOE_BOX),
    OAK_DRAWERS(8612, Buildable.OAK_DRAWERS),
    OAK_WARDROBE(8614, Buildable.OAK_WARDROBE),
    TEAK_DRAWERS(8616, Buildable.TEAK_DRAWERS),
    TEAK_WARDROBE(8618, Buildable.TEAK_WARDROBE),
    MAHOGANY_WARDROBE(8620, Buildable.MAHOGANY_WARDROBE),
    GILDED_WARDROBE(8622, Buildable.GILDED_WARDROBE),

    OAK_CLOCK(8590, Buildable.OAK_CLOCK),
    TEAK_CLOCK(8592, Buildable.TEAK_CLOCK),
    GILDED_CLOCK(8594, Buildable.GILDED_CLOCK),

    OAK_CAPE_RACK(9843, Buildable.OAK_CAPE_RACK),
    TEAK_CAPE_RACK(9844, Buildable.TEAK_CAPE_RACK),
    MAHOGANY_CAPE_RACK(9845, Buildable.MAHOGANY_CAPE_RACK),
    GILDED_CAPE_RACK(9846, Buildable.GILDED_CAPE_RACK),
    MARBLE_CAPE_RACK(9847, Buildable.MARBLE_CAPE_RACK),
    MAGICAL_CAPE_RACK(9848, Buildable.MAGICAL_CAPE_RACK),

    OAK_MAGIC_WARDROBE(9852, Buildable.OAK_MAGIC_WARDROBE),
    CARVED_OAK_MAGIC_WARDROBE(9853, Buildable.CARVED_OAK_MAGIC_WARDROBE),
    TEAK_MAGIC_WARDROBE(9854, Buildable.TEAK_MAGIC_WARDROBE),
    CARVED_TEAK_MAGIC_WARDROBE(9855, Buildable.CARVED_TEAK_BENCH),
    MAHOGANY_MAGIC_WARDROBE(9856, Buildable.MAHOGANY_MAGIC_WARDROBE),
    GILDED_MAGIC_WARDROBE(9857, Buildable.GILDED_MAGIC_WARDROBE),
    MARBLE_MAGIC_WARDROBE(9858, Buildable.MARBLE_MAGIC_WARDROBE),

    OAK_ARMOUR_CASE(9826, Buildable.OAK_ARMOUR_CASE),
    TEAK_ARMOUR_CASE(9827, Buildable.TEAK_ARMOUR_CASE),
    MAHOGANY_ARMOUR_CASE(9828, Buildable.MAHOGANY_ARMOUR_CASE),

    OAK_TREASURE_CHEST(9862, Buildable.OAK_TREASURE_CHEST),
    TEAK_TREASURE_CHEST(9863, Buildable.TEAK_TREASURE_CHEST),
    MAHOGANY_TREASURE_CHEST(9864, Buildable.MAHOGANY_TREASURE_CHEST),

    OAK_FANCY_BOX(9865, Buildable.OAK_FANCY_DRESS_BOX),
    TEAK_FANCY_BOX(9866, Buildable.TEAK_FANCY_DRESS_BOX),
    MAHOGANY_FANCY_BOX(9867, Buildable.MAHOGANY_FANCY_DRESS_BOX),

    OAK_TOY_BOX(9849, Buildable.OAK_TOY_BOX),
    TEAK_TOY_BOX(9850, Buildable.TEAK_TOY_BOX),
    MAHOGANY_TOY_BOX(9851, Buildable.MAHOGANY_TOY_BOX),


    ;

    int itemId;

    Flatpack(int itemId, Buildable buildable) {
        this.itemId = itemId;
        this.setBuildable(buildable);
    }

    private Buildable buildable;


    private static Flatpack[] ARMCHAIRS = {CRUDE_CHAIR, WOODEN_CHAIR, ROCKING_CHAIR, OAK_CHAIR, OAK_ARMCHAIR, TEAK_ARMCHAIR, MAHOGANY_ARMCHAIR};
    private static Flatpack[] BOOKCASES = {WOODEN_BOOKCASE, OAK_BOOKCASE, MAHOGANY_BOOKCASE};
    private static Flatpack[] BARRELS = {BEER_BARREL, CIDER_BARREL, ASGARNIAN_ALE_BARREL, GREENMANS_ALE_BARREL, DRAGON_BITTER, CHEFS_DELIGHT_BARREL};
    private static Flatpack[] KITCHEN_TABLES = {KITCHEN_TABLE, OAK_KITCHEN_TABLE, TEAK_KITCHEN_TABLE};
    private static Flatpack[] DINING_TABLES = {WOOD_DINING_TABLE, OAK_DINING_TABLE, CARVED_OAK_TABLE, TEAK_TABLE, CARVED_TEAK_TABLE, MAHOGANY_TABLE, OPULENT_TABLE};
    private static Flatpack[] DINING_BENCHES = {WOODEN_BENCH, OAK_BENCH, CARVED_OAK_BENCH, TEAK_BENCH, CARVED_TEAK_BENCH, MAHOGANY_BENCH, GILDED_BENCH};
    private static Flatpack[] BEDS = {WOODEN_BED, OAK_BED,LARGE_OAK_BED, TEAK_BED, LARGE_TEAK_BED, FOUR_POSTER, GILDED_FOUR_POSTER};
    private static Flatpack[] DRESSERS = {SHAVING_STAND,OAK_SHAVING_STAND,OAK_DRESSER,TEAK_DRESSER,FANCY_TEAK_DRESSER,MAHOGANY_DRESSER,GILDED_DRESSER};
    private static Flatpack[] WARDROBES = {SHOE_BOX, OAK_DRAWERS, OAK_WARDROBE, TEAK_DRAWERS, TEAK_WARDROBE, MAHOGANY_WARDROBE, GILDED_WARDROBE};
    private static Flatpack[] CLOCKS = { OAK_CLOCK,TEAK_CLOCK,GILDED_CLOCK};
    private static Flatpack[] CAPE_RACKS = {OAK_CAPE_RACK,TEAK_CAPE_RACK,MAHOGANY_CAPE_RACK,GILDED_CAPE_RACK,MARBLE_CAPE_RACK,MAGICAL_CAPE_RACK};
    private static Flatpack[] MAGIC_WARDROBES = {OAK_MAGIC_WARDROBE,CARVED_OAK_MAGIC_WARDROBE,TEAK_MAGIC_WARDROBE,CARVED_TEAK_MAGIC_WARDROBE,MAHOGANY_MAGIC_WARDROBE,GILDED_MAGIC_WARDROBE,MARBLE_MAGIC_WARDROBE};
    private static Flatpack[] ARMOUR_CASES = {OAK_ARMOUR_CASE,TEAK_ARMOUR_CASE,MAHOGANY_ARMOUR_CASE};
    private static Flatpack[] TREASURE_CHESTS = {OAK_TREASURE_CHEST,TEAK_TREASURE_CHEST,MAHOGANY_TREASURE_CHEST};
    private static Flatpack[] FANCY_DRESS_BOXES = {OAK_FANCY_BOX,TEAK_FANCY_BOX,MAHOGANY_FANCY_BOX};
    private static Flatpack[] TOY_BOXES = {OAK_TOY_BOX,TEAK_TOY_BOX,MAHOGANY_TOY_BOX};

    static {
        InterfaceHandler.register(Interface.CONSTRUCTION_FLATPACK_CREATION, h -> {
            h.actions[111] = (SimpleAction) p -> openCategory(p, ARMCHAIRS);
            h.actions[112] = (SimpleAction) p -> openCategory(p, BOOKCASES);
            h.actions[113] = (SimpleAction) p -> openCategory(p, BARRELS);
            h.actions[114] = (SimpleAction) p -> openCategory(p, KITCHEN_TABLES);
            h.actions[115] = (SimpleAction) p -> openCategory(p, DINING_TABLES);
            h.actions[116] = (SimpleAction) p -> openCategory(p, DINING_BENCHES);
            h.actions[117] = (SimpleAction) p -> openCategory(p, BEDS);
            h.actions[118] = (SimpleAction) p -> openCategory(p, DRESSERS);
            h.actions[119] = (SimpleAction) p -> openCategory(p, WARDROBES);
            h.actions[120] = (SimpleAction) p -> openCategory(p, CLOCKS);
            h.actions[121] = (SimpleAction) p -> openCategory(p, CAPE_RACKS);
            h.actions[122] = (SimpleAction) p -> openCategory(p, MAGIC_WARDROBES);
            h.actions[123] = (SimpleAction) p -> openCategory(p, ARMOUR_CASES);
            h.actions[124] = (SimpleAction) p -> openCategory(p, TREASURE_CHESTS);
            h.actions[125] = (SimpleAction) p -> openCategory(p, FANCY_DRESS_BOXES);
            h.actions[126] = (SimpleAction) p -> openCategory(p, TOY_BOXES);
        });
    }

    public static void openFlatpackCategories(Player player, int maxLevel) {
       player.openInterface(InterfaceType.MAIN, Interface.CONSTRUCTION_FLATPACK_CREATION);
       player.set("WORKBENCH_LEVEL", maxLevel);

    }

    private static void openCategory(Player p, Flatpack[] set) {
        int count = 1;
        int maxLevel = p.get("WORKBENCH_LEVEL", 1);
        for (Flatpack flatpack :  set) {
            if (flatpack.getBuildable().getLevelReq() > maxLevel)
                break;
            p.getPacketSender().sendClientScript(1404, "iiisi", count++, flatpack.getBuildable().getItemId(), flatpack.getBuildable().getLevelReq(), flatpack.getBuildable().getCreationMenuString(), flatpack.getBuildable().hasLevelAndMaterials(p) || p.isAdmin() ? 1 : 0);
        }
        p.getPacketSender().sendClientScript(1406, "ii", count - 1, 0);
        p.set("FLATPACK_SET", set);
        p.openInterface(InterfaceType.MAIN, Interface.CONSTRUCTION_FURNITURE_CREATION);
    }

    public static void make(Player player, int slot) {
        Flatpack[] set = player.get("FLATPACK_SET");
        int maxLevel = player.get("WORKBENCH_LEVEL", 1);
        player.closeInterfaces();
        if (set == null || maxLevel == 1 || slot < 1 || slot > set.length) {
            return;
        }
        Buildable selected = set[slot - 1].getBuildable();
        if (maxLevel < selected.getLevelReq()) {
            player.sendMessage("Your workbench is not high enough level to build that item.");
            return;
        }
        if (Construction.getEffectiveLevel(player, selected) < selected.getLevelReq()) {
            player.dialogue(new MessageDialogue("You need a Construction level of at least " + selected.getLevelReq() + " to build that."));
            return;
        }
        if (!selected.hasTools(player)) {
            player.dialogue(new MessageDialogue("You will need a hammer and a saw to build that."));
            return;
        }
        if (!selected.hasAllMaterials(player) && !player.isAdmin()) {
            player.sendMessage("You do not have all the required materials to build that.");
            return;
        }
        player.startEvent(event -> {
            while (true) {
                if (Construction.getEffectiveLevel(player, selected) < selected.getLevelReq()) {
                    player.dialogue(new MessageDialogue("You need a Construction level of at least " + selected.getLevelReq() + " to build that."));
                    return;
                }
                if (!selected.hasAllMaterials(player) && !player.isAdmin()) {
                    return;
                }
                if (!selected.canBuild(player)) {
                    return;
                }
                player.animate(896);
                selected.removeMaterials(player);
                player.getStats().addXp(StatType.Construction, selected.getXP(), true);
                player.getInventory().add(set[slot - 1].itemId, 1);
                event.delay(8);
            }
        });
    }

    static { // Assembling the flatpack in the hotspot
        for (Flatpack f : Flatpack.values()) {
            for (Hotspot hotspot : Hotspot.values()) {
                if (Arrays.asList(hotspot.getBuildables()).contains(f.getBuildable())) {
                    for (int objId : hotspot.getHotspotIds()) {
                        ItemObjectAction.register(f.itemId, objId, (player, item, obj) -> {
                            if (!player.isInOwnHouse()) {
                                return;
                            }
                            if (!player.house.isBuildingMode()) {
                                return;
                            }
                            player.getCurrentRoom().assembleFlatpack(player, f, item, hotspot);
                        });
                    }
                }
            }
        }
    }

    public Buildable getBuildable() {
        return buildable;
    }

    public void setBuildable(Buildable buildable) {
        this.buildable = buildable;
    }
}
