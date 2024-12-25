package io.ruin.model.skills.smithing;

import io.ruin.cache.ItemDef;
import io.ruin.cache.ItemID;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum SmithBar {
    BRONZE(
            2349, 1,
            1, 6.25,
            PlayerCounter.SMELTED_BRONZE_BARS,
            Arrays.asList(new Item(436, 1), new Item(438, 1)),
            new SmithItem(1, 12.5, 1205, 1, 1),//Bronze dagger
            new SmithItem(4, 12.5, 1277, 1, 1),//Bronze sword
            new SmithItem(5, 25.0, 1321, 1, 2),//Bronze scimitar
            new SmithItem(6, 25.0, 1291, 1, 2),//Bronze longsword
            new SmithItem(14, 37.5, 1307, 1, 3),//Bronze 2h sword
            new SmithItem(1, 12.5, 1351, 1, 1),//Bronze axe
            new SmithItem(2, 12.5, 1422, 1, 1),//Bronze mace
            new SmithItem(9, 37.5, 1337, 1, 3),//Bronze warhammer
            new SmithItem(10, 12.5, 1375, 1, 3),//Bronze battleaxe
            SmithItem.NONE,
            new SmithItem(11, 37.5, 1103, 1, 3),//Bronze chainbody
            new SmithItem(16, 37.5, 1075, 1, 3),//Bronze platelegs
            new SmithItem(16, 37.5, 1087, 1, 3),//Bronze plateskirt
            new SmithItem(18, 62.5, 1117, 1, 5),//Bronze platebody
            new SmithItem(4, 12.5, 4819, 15, 1),//Bronze nails
            new SmithItem(3, 12.5, 1139, 1, 1),//Bronze med helm
            new SmithItem(7, 25.0, 1155, 1, 2),//Bronze full helm
            new SmithItem(8, 25.0, 1173, 1, 2),//Bronze sq shield
            new SmithItem(12, 37.5, 1189, 1, 3),//Bronze kiteshield
            SmithItem.NONE,
            new SmithItem(4, 12.5, 819, 10, 1),//Bronze dart tip
            new SmithItem(5, 12.5, 39, 15, 1),//Bronze arrowtips
            new SmithItem(7, 12.5, 864, 5, 1),//Bronze knife
            new SmithItem(4, 12.5, 1794, 1, 1),//Bronze wire
            new SmithItem(6, 12.5, 19570, 5, 1),//Bronze javelin heads
            new SmithItem(3, 12.5, 9375, 10, 1),//Bronze bolts (unf)
            new SmithItem(6, 12.5, 9420, 1, 1)//Bronze limbs
    ),
    BLURITE(
            9467, -1,
            8, 8.0,
            PlayerCounter.SMELTED_BLURITE_BARS,
            Collections.singletonList(new Item(668, 1)),
            new SmithItem(8, 17.0, 9376, 10, 1),
            new SmithItem(13, 17.0, 9422, 1, 1)
    ),
    IRON(
            2351, 2,
            15, 12.5,
            PlayerCounter.SMELTED_IRON_BARS,
            Collections.singletonList(new Item(440, 1)),
            new SmithItem(15, 25.0, 1203, 1, 1),//Iron dagger
            new SmithItem(19, 25.0, 1279, 1, 1),//Iron sword
            new SmithItem(20, 50.0, 1323, 1, 2),//Iron scimitar
            new SmithItem(21, 50.0, 1293, 1, 2),//Iron longsword
            new SmithItem(29, 75.0, 1309, 1, 3),//Iron 2h sword
            new SmithItem(16, 25.0, 1349, 1, 1),//Iron axe
            new SmithItem(17, 25.0, 1420, 1, 1),//Iron mace
            new SmithItem(24, 75.0, 1335, 1, 3),//Iron warhammer
            new SmithItem(25, 75.0, 1363, 1, 3),//Iron battleaxe
            SmithItem.NONE,
            new SmithItem(26, 75.0, 1101, 1, 3),//Iron chainbody
            new SmithItem(31, 75.0, 1067, 1, 3),//Iron platelegs
            new SmithItem(31, 75.0, 1081, 1, 3),//Iron plateskirt
            new SmithItem(33, 125.0, 1115, 1, 5),//Iron platebody
            new SmithItem(19, 25.0, 4820, 15, 1),//Iron nails
            new SmithItem(18, 25.0, 1137, 1, 1),//Iron med helm
            new SmithItem(22, 50.0, 1153, 1, 2),//Iron full helm
            new SmithItem(23, 50.0, 1175, 1, 2),//Iron sq shield
            new SmithItem(27, 75.0, 1191, 1, 3),//Iron kiteshield
            new SmithItem(26, 25.0, 4540, 1, 1),//Oil lantern frame
            new SmithItem(19, 25.0, 820, 10, 1),//Iron dart tip
            new SmithItem(20, 25.0, 40, 15, 1),//Iron arrowtips
            new SmithItem(22, 25.0, 863, 5, 1),//Iron knife
            new SmithItem(16, 25.0, 7225, 1, 1),//Iron spit
            new SmithItem(21, 25.0, 19572, 5, 1),//Iron javelin heads
            new SmithItem(18, 25.0, 9377, 10, 1),//Iron bolts (unf)
            new SmithItem(23, 25.0, 9423, 1, 1)//Iron limbs
    ),
    SILVER(
            2355, -1,
            20, 13.67,
            PlayerCounter.SMELTED_SILVER_BARS,
            Collections.singletonList(new Item(442, 1))
            //uhhhhh
    ),
    STEEL(
            2353, 3,
            30, 17.5,
            PlayerCounter.SMELTED_STEEL_BARS,
            Arrays.asList(new Item(440, 1), new Item(453, 2)),
            new SmithItem(30, 37.5, 1207, 1, 1),//Steel dagger
            new SmithItem(34, 37.5, 1281, 1, 1),//Steel sword
            new SmithItem(35, 75.0, 1325, 1, 2),//Steel scimitar
            new SmithItem(36, 75.0, 1295, 1, 2),//Steel longsword
            new SmithItem(44, 112.5, 1311, 1, 3),//Steel 2h sword
            new SmithItem(31, 37.5, 1353, 1, 1),//Steel axe
            new SmithItem(32, 37.5, 1424, 1, 1),//Steel mace
            new SmithItem(39, 112.5, 1339, 1, 3),//Steel warhammer
            new SmithItem(40, 112.5, 1365, 1, 3),//Steel battleaxe
            SmithItem.NONE,
            new SmithItem(41, 112.5, 1105, 1, 3),//Steel chainbody
            new SmithItem(46, 112.5, 1069, 1, 3),//Steel platelegs
            new SmithItem(46, 112.5, 1083, 1, 3),//Steel plateskirt
            new SmithItem(48, 187.5, 1119, 1, 5),//Steel platebody
            new SmithItem(34, 37.5, 1539, 15, 1),//Steel nails
            new SmithItem(33, 37.5, 1141, 1, 1),//Steel med helm
            new SmithItem(37, 75.0, 1157, 1, 2),//Steel full helm
            new SmithItem(38, 75.0, 1177, 1, 2),//Steel sq shield
            new SmithItem(42, 112.5, 1193, 1, 3),//Steel kiteshield
            new SmithItem(49, 37.5, 4544, 1, 1),//Bullseye lantern (unf)
            new SmithItem(34, 37.5, 821, 10, 1),//Steel dart tip
            new SmithItem(35, 37.5, 41, 15, 1),//Steel arrowtips
            new SmithItem(37, 37.5, 865, 5, 1),//Steel knife
            new SmithItem(36, 37.5, 2370, 1, 1),//Steel studs
            new SmithItem(36, 37.5, 19574, 5, 1),//Steel javelin heads
            new SmithItem(33, 37.5, 9378, 10, 1),//Steel bolts (unf)
            new SmithItem(36, 37.5, 9425, 1, 1)//Steel limbs
    ),
    GOLD(
            2357, -1,
            40, 22.5,
            PlayerCounter.SMELTED_GOLD_BARS,
            Collections.singletonList(new Item(444, 1))
            //uhhhhh
    ),
    MITHRIL(
            2359, 4,
            50, 30.0,
            PlayerCounter.SMELTED_MITHRIL_BARS,
            Arrays.asList(new Item(447, 1), new Item(453, 4)),
            new SmithItem(50, 50.0, 1209, 1, 1),//Mithril dagger
            new SmithItem(54, 50.0, 1285, 1, 1),//Mithril sword
            new SmithItem(55, 100.0, 1329, 1, 2),//Mithril scimitar
            new SmithItem(56, 100.0, 1299, 1, 2),//Mithril longsword
            new SmithItem(64, 150.0, 1315, 1, 3),//Mithril 2h sword
            new SmithItem(51, 50.0, 1355, 1, 1),//Mithril axe
            new SmithItem(52, 50.0, 1428, 1, 1),//Mithril mace
            new SmithItem(59, 150.0, 1343, 1, 3),//Mithril warhammer
            new SmithItem(60, 150.0, 1369, 1, 3),//Mithril battleaxe
            SmithItem.NONE,
            new SmithItem(61, 150.0, 1109, 1, 3),//Mithril chainbody
            new SmithItem(66, 150.0, 1071, 1, 3),//Mithril platelegs
            new SmithItem(66, 150.0, 1085, 1, 3),//Mithril plateskirt
            new SmithItem(68, 250.0, 1121, 1, 5),//Mithril platebody
            new SmithItem(54, 50.0, 4822, 15, 1),//Mithril nails
            new SmithItem(53, 50.0, 1143, 1, 1),//Mithril med helm
            new SmithItem(57, 100.0, 1159, 1, 2),//Mithril full helm
            new SmithItem(58, 100.0, 1181, 1, 2),//Mithril sq shield
            new SmithItem(62, 150.0, 1197, 1, 3),//Mithril kiteshield
            SmithItem.NONE,
            new SmithItem(54, 50.0, 822, 10, 1),//Mithril dart tip
            new SmithItem(56, 50.0, 42, 15, 1),//Mithril arrowtips
            new SmithItem(57, 50.0, 866, 5, 1),//Mithril knife
            new SmithItem(59, 50.0, 9416, 1, 1),//Mith grapple tip
            new SmithItem(56, 50.0, 19576, 5, 1),//Mithril javelin heads
            new SmithItem(53, 50.0, 9379, 10, 1),//Mithril bolts (unf)
            new SmithItem(56, 50.0, 9427, 1, 1)//Mithril limbs

    ),
    ADAMANT(
            2361, 5,
            70, 37.5,
            PlayerCounter.SMELTED_ADAMANT_BARS,
            Arrays.asList(new Item(449, 1), new Item(453, 3)),
            new SmithItem(70, 62.5, 1211, 1, 1),//Adamant dagger
            new SmithItem(74, 62.5, 1287, 1, 1),//Adamant sword
            new SmithItem(75, 125.0, 1331, 1, 2),//Adamant scimitar
            new SmithItem(76, 125.0, 1301, 1, 2),//Adamant longsword
            new SmithItem(84, 187.5, 1317, 1, 3),//Adamant 2h sword
            new SmithItem(71, 62.5, 1357, 1, 1),//Adamant axe
            new SmithItem(72, 62.5, 1430, 1, 1),//Adamant mace
            new SmithItem(79, 187.5, 1345, 1, 3),//Adamant warhammer
            new SmithItem(80, 187.5, 1371, 1, 3),//Adamant battleaxe
            SmithItem.NONE,
            new SmithItem(81, 187.5, 1111, 1, 3),//Adamant chainbody
            new SmithItem(86, 187.5, 1073, 1, 3),//Adamant platelegs
            new SmithItem(86, 187.5, 1091, 1, 3),//Adamant plateskirt
            new SmithItem(88, 312.5, 1123, 1, 5),//Adamant platebody
            new SmithItem(74, 62.5, 4823, 15, 1),//Adamantite nails
            new SmithItem(73, 62.5, 1145, 1, 1),//Adamant med helm
            new SmithItem(77, 125.0, 1161, 1, 2),//Adamant full helm
            new SmithItem(78, 125.0, 1183, 1, 2),//Adamant sq shield
            new SmithItem(82, 187.5, 1199, 1, 3),//Adamant kiteshield
            SmithItem.NONE,
            new SmithItem(74, 62.5, 823, 10, 1),//Adamant dart tip
            new SmithItem(75, 62.5, 43, 15, 1),//Adamant arrowtips
            new SmithItem(77, 62.5, 867, 5, 1),//Adamant knife
            new SmithItem(76, 62.5, 19578, 5, 1),//Adamant javelin heads
            SmithItem.NONE,
            new SmithItem(73, 62.5, 9380, 10, 1),//Adamant bolts(unf)
            new SmithItem(76, 62.5, 9429, 1, 1)//Adamantite limbs

    ),
    RUNITE(
            2363, 6,
            85, 50.0,
            PlayerCounter.SMELTED_RUNITE_BARS,
            Arrays.asList(new Item(451, 1), new Item(453, 4)),
            new SmithItem(85, 75.0, 1213, 1, 1),//Rune dagger
            new SmithItem(89, 75.0, 1289, 1, 1),//Rune sword
            new SmithItem(90, 150.0, 1333, 1, 2),//Rune scimitar
            new SmithItem(91, 150.0, 1303, 1, 2),//Rune longsword
            new SmithItem(99, 225.0, 1319, 1, 3),//Rune 2h sword
            new SmithItem(86, 75.0, 1359, 1, 1),//Rune axe
            new SmithItem(87, 75.0, 1432, 1, 1),//Rune mace
            new SmithItem(94, 225.0, 1347, 1, 3),//Rune warhammer
            new SmithItem(95, 225.0, 1373, 1, 3),//Rune battleaxe
            SmithItem.NONE,
            new SmithItem(96, 225.0, 1113, 1, 3),//Rune chainbody
            new SmithItem(99, 225.0, 1079, 1, 3),//Rune platelegs
            new SmithItem(99, 225.0, 1093, 1, 3),//Rune plateskirt
            new SmithItem(99, 375.0, 1127, 1, 5),//Rune platebody
            new SmithItem(89, 75.0, 4824, 15, 1),//Rune nails
            new SmithItem(88, 75.0, 1147, 1, 1),//Rune med helm
            new SmithItem(92, 150.0, 1163, 1, 2),//Rune full helm
            new SmithItem(93, 150.0, 1185, 1, 2),//Rune sq shield
            new SmithItem(97, 225.0, 1201, 1, 3),//Rune kiteshield
            SmithItem.NONE,
            new SmithItem(89, 75.0, 824, 10, 1),//Rune dart tip
            new SmithItem(90, 75.0, 44, 15, 1),//Rune arrowtips
            new SmithItem(92, 75.0, 868, 5, 1),//Rune knife
            new SmithItem(91, 75.0, 19580, 5, 1),//Rune javelin heads
            SmithItem.NONE,
            new SmithItem(88, 75.0, 9381, 10, 1),//Runite bolts (unf)
            new SmithItem(91, 75.0, 9431, 1, 1)//Runite limbs

    ),
    LOVAKITE(
            13354, 7,
            45, 20.0,
            null,
            Arrays.asList(new Item(13356, 1), new Item(453, 2)),
            new SmithItem(45, 10.0, 13538, 1, 1),//Shayzien supply gloves (1)
            new SmithItem(47, 10.0, 13539, 1, 1),//Shayzien supply boots (1)
            new SmithItem(49, 20.0, 13540, 1, 2),//Shayzien supply helm (1)
            new SmithItem(51, 30.0, 13541, 1, 3),//Shayzien supply greaves (1)
            new SmithItem(53, 40.0, 13542, 1, 4),//Shayzien supply platebody (1)
            new SmithItem(55, 10.0, 13543, 1, 1),//Shayzien supply gloves (2)
            new SmithItem(57, 10.0, 13544, 1, 1),//Shayzien supply boots (2)
            new SmithItem(59, 20.0, 13545, 1, 2),//Shayzien supply helm (2)
            new SmithItem(61, 30.0, 13546, 1, 3),//Shayzien supply greaves (2)
            new SmithItem(63, 40.0, 13547, 1, 4),//Shayzien supply platebody (2)
            new SmithItem(65, 10.0, 13548, 1, 1),//Shayzien supply gloves (3)
            new SmithItem(67, 10.0, 13549, 1, 1),//Shayzien supply boots (3)
            new SmithItem(69, 20.0, 13550, 1, 2),//Shayzien supply helm (3)
            new SmithItem(71, 30.0, 13551, 1, 3),//Shayzien supply greaves (3)
            new SmithItem(73, 40.0, 13552, 1, 4),//Shayzien supply platebody (3)
            new SmithItem(75, 10.0, 13553, 1, 1),//Shayzien supply gloves (4)
            new SmithItem(77, 10.0, 13554, 1, 1),//Shayzien supply boots (4)
            new SmithItem(79, 20.0, 13555, 1, 2),//Shayzien supply helm (4)
            new SmithItem(81, 30.0, 13556, 1, 3),//Shayzien supply greaves (4)
            SmithItem.NONE,
            new SmithItem(83, 40.0, 13557, 1, 4),//Shayzien supply platebody (4)
            new SmithItem(85, 10.0, 13558, 1, 1),//Shayzien supply gloves (5)
            new SmithItem(87, 10.0, 13559, 1, 1),//Shayzien supply boots (5)
            new SmithItem(89, 20.0, 13560, 1, 2),//Shayzien supply helm (5)
            new SmithItem(91, 30.0, 13561, 1, 3),//Shayzien supply greaves (5)
            new SmithItem(93, 40.0, 13562, 1, 4),//Shayzien supply platebody (5)
            SmithItem.NONE,
            SmithItem.NONE,
            SmithItem.NONE
    ),
    CORRUPT(
            30112, 8,
            90, 60.0,
            PlayerCounter.SMELTED_CORRUPT_BARS,
            Arrays.asList(new Item(30109, 1), new Item(453, 2)),
            new SmithItem(90, 50.0, 30119, 5, 1),
            new SmithItem(90, 50.0, 30095, 1, 3)
    );

    public final int itemId;

    public final int smithValue;

    public final int smeltLevel;

    public final double smeltXp;

    public final PlayerCounter counter;

    public final List<Item> smeltItems;

    public final SmithItem[] smithItems;

    public final String name;

    SmithBar(int itemId, int smithValue, int smeltLevel, double smeltXp, PlayerCounter counter, List<Item> smeltItems, SmithItem... smithItems) {
        this.itemId = itemId;
        this.smithValue = smithValue;
        this.smeltLevel = smeltLevel;
        this.smeltXp = smeltXp;
        this.counter = counter;
        this.smeltItems = smeltItems;
        this.smithItems = smithItems;
        this.name = name().toLowerCase();
        ItemDef.get(itemId).smithBar = this;
    }

    /**
     * Smithing (Opening)
     */

    private static void open(Player player) {
        if(!player.getInventory().hasId(Tool.HAMMER) && !player.getInventory().hasId(ItemID.DRAGON_WARHAMMER)) {
            player.dialogue(new MessageDialogue("You need a hammer to work the metal with."));
            return;
        }
        SmithBar bar = findBestBar(player);
        if(bar == null || bar.equals(SmithBar.CORRUPT)) {
            player.dialogue(new MessageDialogue("You should select an item from your inventory and use it on the anvil."));
            return;
        }
        open(player, bar);
    }

    private static void open(Player player, Item item) {
        SmithBar bar = item.getDef().smithBar;
        if(bar.smithValue == -1) {
            player.dialogue(new PlayerDialogue("Perhaps I should use this in a furnace instead.").animate(575));
            return;
        }
        if(player.getStats().get(StatType.Smithing).currentLevel < bar.smithItems[0].level) {
            player.dialogue(new MessageDialogue("You need a Smithing level of at least " + bar.smithItems[0].level + " to work " + bar.name + " bars."));
            return;
        }
        open(player, bar);
    }

    private static void open(Player player, SmithBar bar) {
        player.smithBar = bar;
        Config.SMITHING_TYPE.set(player, bar.smithValue);
        player.openInterface(InterfaceType.MAIN, Interface.SMITHING);
    }

    private static SmithBar findBestBar(Player player) {
        int smithingLevel = player.getStats().get(StatType.Smithing).currentLevel;
        SmithBar bestBar = null;
        for(Item item : player.getInventory().getItems()) {
            if(item == null) {
                /* item does not exist */
                continue;
            }
            SmithBar bar = item.getDef().smithBar;
            if(bar == null) {
                /* this item isn't a bar */
                continue;
            }
            if(bar.smithItems.length == 0) {
                /* not yet handled or can't be smithed */
                continue;
            }
            int minLevel = bar.smithItems[0].level;
            if(minLevel > smithingLevel) {
                /* player smithing level is too low */
                continue;
            }
            if(bestBar == null || bestBar.equals(SmithBar.CORRUPT)) {
                /* first smithable bar found */
                bestBar = bar;
            } else if(minLevel > bestBar.smithItems[0].level) {
                /* better smithable bar found */
                bestBar = bar;
            }
        }
        return bestBar;
    }

    /**
     * Smithing (Making)
     */

    private static void make(Player player, int itemIndex, int option) {
        SmithItem item = player.smithBar.smithItems[itemIndex];
        if(item == null)
            return;
        item.make(player, Math.max(1, player.getInventory().getAmount(player.smithBar.itemId) / item.barReq));
        /*if(option == 1) {
            item.make(player, 1);
            return;
        }
        if(option == 2) {
            item.make(player, 5);
            return;
        }
        if(option == 3) {
            item.make(player, 10);
            return;
        }
        if(option == 4) {
            player.integerInput("Enter amount:", amt -> item.make(player, amt));
            return;
        }
        if(option == 5) {
            item.make(player, Math.max(1, player.getInventory().getAmount(player.smithBar.itemId) / item.barReq));
            return;
        }*/
        Item.examine(player, item.makeId);
    }

    /**
     * Handlers
     */

    static {
        ObjectAction.register("anvil", "smith", (player, obj) -> open(player));
        for(SmithBar bar : SmithBar.values()) {
            if (bar.equals(SmithBar.CORRUPT))
                continue;
            ItemObjectAction.register(bar.itemId, "anvil", (player, item, obj) -> open(player, item));
        }
        ItemObjectAction.register(SmithBar.CORRUPT.itemId, "anvil", (player, item, obj) -> openCorrupt(player, item));
        InterfaceHandler.register(Interface.SMITHING, h -> {
            for(int i = 9; i <= 35; i++) {
                int itemIndex = i - 9;
                h.actions[i] = (DefaultAction) (p, option, slot, itemId) -> make(p, itemIndex, option);
            }
        });
    }

    private static void openCorrupt(Player player, Item item) {
        SmithBar bar = item.getDef().smithBar;
        if (bar == null)
            return;
        player.smithBar = bar;
        SkillDialogue.make(player,
            new SkillItem(bar.smithItems[0].makeId).addAction((p, amount, e) -> {
                if (!player.getInventory().contains(ItemID.HAMMER, 1)) {
                    player.sendMessage("You need a hammer to do that.");
                    return;
                }
                bar.smithItems[0].make(player, Math.min(amount, player.getInventory().getAmount(player.smithBar.itemId)));
            }),
            new SkillItem(bar.smithItems[1].makeId).addAction((p, amount, e) -> {
                if (!player.getInventory().contains(ItemID.HAMMER, 1)) {
                    player.sendMessage("You need a hammer to do that.");
                    return;
                }
                bar.smithItems[1].make(player, Math.min(amount, player.getInventory().getAmount(player.smithBar.itemId)));
            })
        );
    }

}
