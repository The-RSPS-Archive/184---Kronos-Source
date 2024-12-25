package io.ruin.model.activities.cluescrolls;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Broadcast;

import java.util.ArrayList;
import java.util.Collections;

import static io.ruin.cache.ItemID.COINS_995;

public enum ClueType {

    EASY(
            2677, 20546, 2, 4,
            /**
             * Common
             */
            new LootItem(1165, 1, 10),                //Black full helm
            new LootItem(1125, 1, 10),                //Black platebody
            new LootItem(1077, 1, 10),                //Black platelegs
            new LootItem(1089, 1, 10),                //Black plateskirt
            new LootItem(1195, 1, 10),                //Black kiteshield
            new LootItem(1297, 1, 10),                //Black longsword
            new LootItem(1313, 1, 10),                //Black 2h sword
            new LootItem(1367, 1, 10),                //Black battleaxe
            new LootItem(1361, 1, 10),                //Black axe
            new LootItem(1269, 1, 10),                //Steel pickaxe
            new LootItem(1169, 1, 10),                //Coif
            new LootItem(1133, 1, 10),                //Studded body
            new LootItem(1097, 1, 10),                //Studded chaps
            new LootItem(849, 1, 10),                 //Willow shortbow
            new LootItem(857, 1, 10),                 //Yew shortbow
            new LootItem(334, 3, 10, 10),             //Trout
            new LootItem(330, 3, 10, 10),             //Salmon
            /**
             * Rare (4 / 25)
             */
            new LootItem(12221, 1, 1),         //Bronze full helm (t)
            new LootItem(12215, 1, 1),         //Bronze platebody (t)
            new LootItem(12217, 1, 1),         //Bronze platelegs (t)
            new LootItem(12219, 1, 1),         //Bronze plateskirt (t)
            new LootItem(12223, 1, 1),         //Bronze kiteshield (t)
            new LootItem(12211, 1, 1),         //Bronze full helm (g)
            new LootItem(12205, 1, 1),         //Bronze platebody (g)
            new LootItem(12207, 1, 1),         //Bronze platelegs (g)
            new LootItem(12209, 1, 1),         //Bronze plateskirt (g)
            new LootItem(12213, 1, 1),         //Bronze kiteshield (g)
            new LootItem(12231, 1, 1),         //Iron full helm (t)
            new LootItem(12225, 1, 1),         //Iron platebody (t)
            new LootItem(12227, 1, 1),         //Iron platelegs (t)
            new LootItem(12229, 1, 1),         //Iron plateskirt (t)
            new LootItem(12233, 1, 1),         //Iron kiteshield (t)
            new LootItem(12241, 1, 1),         //Iron full helm (g)
            new LootItem(12235, 1, 1),         //Iron platebody (g)
            new LootItem(12237, 1, 1),         //Iron platelegs (g)
            new LootItem(12239, 1, 1),         //Iron plateskirt (g)
            new LootItem(12243, 1, 1),         //Iron kiteshield (g)
            new LootItem(20193, 1, 1),         //Steel full helm (t)
            new LootItem(20184, 1, 1),         //Steel platebody (t)
            new LootItem(20187, 1, 1),         //Steel platelegs (t)
            new LootItem(20190, 1, 1),         //Steel plateskirt (t)
            new LootItem(20196, 1, 1),         //Steel kiteshield (t)
            new LootItem(20178, 1, 1),         //Steel full helm (g)
            new LootItem(20169, 1, 1),         //Steel platebody (g)
            new LootItem(20172, 1, 1),         //Steel platelegs (g)
            new LootItem(20175, 1, 1),         //Steel plateskirt (g)
            new LootItem(20181, 1, 1),         //Steel kiteshield (g)
            new LootItem(2587, 1, 1),          //Black full helm (t)
            new LootItem(2583, 1, 1),          //Black platebody (t)
            new LootItem(2585, 1, 1),          //Black platelegs (t)
            new LootItem(3472, 1, 1),          //Black plateskirt (t)
            new LootItem(2589, 1, 1),          //Black kiteshield (t)
            new LootItem(2595, 1, 1),          //Black full helm (g)
            new LootItem(2591, 1, 1),          //Black platebody (g)
            new LootItem(2593, 1, 1),          //Black platelegs (g)
            new LootItem(3473, 1, 1),          //Black plateskirt (g)
            new LootItem(2597, 1, 1),          //Black kiteshield (g)
            new LootItem(2635, 1, 1),          //Black beret
            new LootItem(2633, 1, 1),          //Blue beret
            new LootItem(2637, 1, 1),          //White beret
            new LootItem(12247, 1, 1),         //Red beret
            new LootItem(2631, 1, 1),          //Highwayman mask
            new LootItem(12245, 1, 1),         //Beanie
            new LootItem(7396, 1, 1),          //Blue wizard hat (t)
            new LootItem(7392, 1, 1),          //Blue wizard robe (t)
            new LootItem(7388, 1, 1),          //Blue skirt (t)
            new LootItem(7394, 1, 1),          //Blue wizard hat (g)
            new LootItem(7390, 1, 1),          //Blue wizard robe (g)
            new LootItem(7386, 1, 1),          //Blue skirt (g)
            new LootItem(12455, 1, 1),         //Black wizard hat (t)
            new LootItem(12451, 1, 1),         //Black wizard robe (t)
            new LootItem(12447, 1, 1),         //Black skirt (t)
            new LootItem(12453, 1, 1),         //Black wizard hat (g)
            new LootItem(12449, 1, 1),         //Black wizard robe (g)
            new LootItem(12445, 1, 1),         //Black skirt (g)
            new LootItem(7364, 1, 1),          //Studded body (t)
            new LootItem(7368, 1, 1),          //Studded chaps (t)
            new LootItem(7362, 1, 1),          //Studded body (g)
            new LootItem(7366, 1, 1),          //Studded chaps (g)
            new LootItem(10408, 1, 1),         //Blue elegant shirt
            new LootItem(10410, 1, 1),         //Blue elegant legs
            new LootItem(10428, 1, 1),         //Blue elegant blouse
            new LootItem(10430, 1, 1),         //Blue elegant skirt
            new LootItem(10412, 1, 1),         //Green elegant shirt
            new LootItem(10414, 1, 1),         //Green elegant legs
            new LootItem(10432, 1, 1),         //Green elegant blouse
            new LootItem(10434, 1, 1),         //Green elegant skirt
            new LootItem(10404, 1, 1),         //Red elegant shirt
            new LootItem(10406, 1, 1),         //Red elegant legs
            new LootItem(10424, 1, 1),         //Red elegant blouse
            new LootItem(10426, 1, 1),         //Red elegant skirt
            new LootItem(10316, 1, 1),         //Bob's red shirt
            new LootItem(10320, 1, 1),         //Bob's green shirt
            new LootItem(10318, 1, 1),         //Bob's blue shirt
            new LootItem(10324, 1, 1),         //Bob's purple shirt
            new LootItem(10322, 1, 1),         //Bob's black shirt
            new LootItem(10392, 1, 1),         //A powdered wig
            new LootItem(10394, 1, 1),         //Flared trousers
            new LootItem(10396, 1, 1),         //Pantaloons
            new LootItem(10398, 1, 1),         //Sleeping cap
            new LootItem(10366, 1, 1),         //Amulet of magic (t)
            new LootItem(12375, 1, 1),         //Black cane
            new LootItem(12297, 1, 1),         //Black pickaxe
            new LootItem(10462, 1, 1),         //Guthix robe top
            new LootItem(10466, 1, 1),         //Guthix robe legs
            new LootItem(10458, 1, 1),         //Saradomin robe top
            new LootItem(10464, 1, 1),         //Saradomin robe legs
            new LootItem(10460, 1, 1),         //Zamorak robe top
            new LootItem(10468, 1, 1),         //Zamorak robe legs
            new LootItem(12193, 1, 1),         //Ancient robe top
            new LootItem(12195, 1, 1),         //Ancient robe legs
            new LootItem(12265, 1, 1),         //Bandos robe top
            new LootItem(12267, 1, 1),         //Bandos robe legs
            new LootItem(12253, 1, 1),         //Armadyl robe top
            new LootItem(12255, 1, 1),         //Armadyl robe legs
            new LootItem(12249, 1, 1),         //Imp mask
            new LootItem(12251, 1, 1),         //Goblin mask
            new LootItem(20217, 1, 1),         //Team cape i
            new LootItem(20214, 1, 1),         //Team cape x
            new LootItem(20211, 1, 1),         //Team cape zero
            new LootItem(20166, 1, 1),         //Wooden shield (g)
            new LootItem(20205, 1, 1),         //Golden chef's hat
            new LootItem(20208, 1, 1),         //Golden apron
            new LootItem(20199, 1, 1),         //Monk's top (g)
            new LootItem(20202, 1, 1),         //Monk's bottom (g)
            new LootItem(20164, 1, 1)          //Large spade
    ),
    MEDIUM(
            2801, 20545, 3, 5,
            /**
             * Common
             */
            new LootItem(1161, 1, 50),                //Adamant full helm
            new LootItem(1123, 1, 50),                //Adamant platebody
            new LootItem(1073, 1, 50),                //Adamant platelegs
            new LootItem(1091, 1, 50),                //Adamant plateskirt
            new LootItem(1199, 1, 50),                //Adamant kiteshield
            new LootItem(1301, 1, 50),                //Adamant longsword
            new LootItem(1317, 1, 50),                //Adamant 2h sword
            new LootItem(1371, 1, 50),                //Adamant battleaxe
            new LootItem(1357, 1, 50),                //Adamant axe
            new LootItem(1271, 1, 50),                //Adamant pickaxe
            new LootItem(4823, 3, 15, 50),            //Adamantite nails
            new LootItem(9183, 1, 50),                //Adamant crossbow
            new LootItem(1393, 1, 50),                //Fire battlestaff
            new LootItem(1135, 1, 50),                //Green d'hide body
            new LootItem(1099, 1, 50),                //Green d'hide chaps
            new LootItem(857, 1, 50),                 //Yew shortbow
            new LootItem(374, 3, 10, 50),             //Swordfish
            new LootItem(380, 3, 10, 50),             //Lobster
            /**
             * Rare (4 / 25)
             */
            new LootItem(12293, 1, 20),         //Mithril full helm (t)
            new LootItem(12287, 1, 20),         //Mithril platebody (t)
            new LootItem(12289, 1, 20),         //Mithril platelegs (t)
            new LootItem(12295, 1, 20),         //Mithril plateskirt (t)
            new LootItem(12291, 1, 20),         //Mithril kiteshield (t)
            new LootItem(12283, 1, 20),         //Mithril full helm (g)
            new LootItem(12277, 1, 20),         //Mithril platebody (g)
            new LootItem(12279, 1, 20),         //Mithril platelegs (g)
            new LootItem(12285, 1, 20),         //Mithril plateskirt (g)
            new LootItem(12281, 1, 20),         //Mithril kiteshield (g)
            new LootItem(2605, 1, 20),          //Adamant full helm (t)
            new LootItem(2599, 1, 20),          //Adamant platebody (t)
            new LootItem(2601, 1, 20),          //Adamant platelegs (t)
            new LootItem(3474, 1, 20),          //Adamant plateskirt (t)
            new LootItem(2603, 1, 20),          //Adamant kiteshield (t)
            new LootItem(2613, 1, 20),          //Adamant full helm (g)
            new LootItem(2607, 1, 20),          //Adamant platebody (g)
            new LootItem(2609, 1, 20),          //Adamant platelegs (g)
            new LootItem(3475, 1, 20),          //Adamant plateskirt (g)
            new LootItem(2611, 1, 20),          //Adamant kiteshield (g)
            new LootItem(2647, 1, 20),          //Black headband
            new LootItem(2645, 1, 20),          //Red headband
            new LootItem(2649, 1, 20),          //Brown headband
            new LootItem(12305, 1, 20),         //Pink headband
            new LootItem(12307, 1, 20),         //Green headband
            new LootItem(12301, 1, 20),         //Blue headband
            new LootItem(12299, 1, 20),         //White headband
            new LootItem(12303, 1, 20),         //Gold headband
            new LootItem(7319, 1, 20),          //Red boater
            new LootItem(7321, 1, 20),          //Orange boater
            new LootItem(7323, 1, 20),          //Green boater
            new LootItem(7325, 1, 20),          //Blue boater
            new LootItem(7327, 1, 20),          //Black boater
            new LootItem(12309, 1, 20),         //Pink boater
            new LootItem(12311, 1, 20),         //Purple boater
            new LootItem(12313, 1, 20),         //White boater
            new LootItem(7372, 1, 20),          //Green d'hide body (t)
            new LootItem(7380, 1, 20),          //Green d'hide chaps (t)
            new LootItem(7370, 1, 20),          //Green d'hide body (g)
            new LootItem(7378, 1, 20),          //Green d'hide chaps (g)
            new LootItem(10400, 1, 20),         //Black elegant shirt
            new LootItem(10402, 1, 20),         //Black elegant legs
            new LootItem(10420, 1, 20),         //White elegant blouse
            new LootItem(10422, 1, 20),         //White elegant skirt
            new LootItem(10416, 1, 20),         //Purple elegant shirt
            new LootItem(10418, 1, 20),         //Purple elegant legs
            new LootItem(10436, 1, 20),         //Purple elegant blouse
            new LootItem(10438, 1, 20),         //Purple elegant skirt
            new LootItem(12315, 1, 20),         //Pink elegant shirt
            new LootItem(12317, 1, 20),         //Pink elegant legs
            new LootItem(12339, 1, 20),         //Pink elegant blouse
            new LootItem(12341, 1, 20),         //Pink elegant skirt
            new LootItem(12347, 1, 20),         //Gold elegant shirt
            new LootItem(12349, 1, 20),         //Gold elegant legs
            new LootItem(12343, 1, 20),         //Gold elegant blouse
            new LootItem(12345, 1, 20),         //Gold elegant skirt
            new LootItem(10364, 1, 20),         //Strength amulet (t)
            new LootItem(12377, 1, 20),         //Adamant cane
            new LootItem(10454, 1, 20),         //Guthix mitre
            new LootItem(10448, 1, 20),         //Guthix cloak
            new LootItem(10452, 1, 20),         //Saradomin mitre
            new LootItem(10446, 1, 20),         //Saradomin cloak
            new LootItem(10456, 1, 20),         //Zamorak mitre
            new LootItem(10450, 1, 20),         //Zamorak cloak
            new LootItem(12203, 1, 20),         //Ancient mitre
            new LootItem(12197, 1, 20),         //Ancient cloak
            new LootItem(12271, 1, 20),         //Bandos mitre
            new LootItem(12273, 1, 20),         //Bandos cloak
            new LootItem(12259, 1, 20),         //Armadyl mitre
            new LootItem(12261, 1, 20),         //Armadyl cloak
            new LootItem(12361, 1, 20),         //Cat mask
            new LootItem(12428, 1, 20),         //Penguin mask
            new LootItem(12319, 1, 20),         //Crier hat
            new LootItem(20243, 1, 20),         //Crier bell
            new LootItem(20240, 1, 20),         //Crier coat
            new LootItem(12359, 1, 20),         //Leprechaun hat
            new LootItem(20246, 1, 20),         //Black leprechaun hat
            new LootItem(20266, 1, 20),         //Black unicorn mask
            new LootItem(20269, 1, 20),         //White unicorn mask
            new LootItem(20251, 1, 20),         //Arceuus banner
            new LootItem(20254, 1, 20),         //Hosidius banner
            new LootItem(20257, 1, 20),         //Lovakengj banner
            new LootItem(20260, 1, 20),         //Piscarilius banner
            new LootItem(20263, 1, 20),         //Shayzien banner
            new LootItem(20272, 1, 20),         //Cabbage round shield
            new LootItem(20249, 1, 20),         //Clueless scroll
            /**
             * Mega rare (1 / 293)
             */
            new LootItem(2577, 1, 1),         //Ranger boots
            new LootItem(12598, 1, 1),        //Holy sandals
            new LootItem(2579, 1, 1)          //Wizard boots
    ),
    HARD(
            2722, 20544, 4, 6,
            /**
             * Common
             */
            new LootItem(1163, 1, 100),                //Rune full helm
            new LootItem(1127, 1, 100),                //Rune platebody
            new LootItem(1079, 1, 100),                //Rune platelegs
            new LootItem(1093, 1, 100),                //Rune plateskirt
            new LootItem(1201, 1, 100),                //Rune kiteshield
            new LootItem(1303, 1, 100),                //Rune longsword
            new LootItem(1319, 1, 100),                //Rune 2h sword
            new LootItem(1373, 1, 100),                //Rune battleaxe
            new LootItem(1359, 1, 100),                //Rune axe
            new LootItem(1275, 1, 100),                //Rune pickaxe
            new LootItem(2503, 1, 100),                //Black d'hide body
            new LootItem(2497, 1, 100),                //Black d'hide chaps
            new LootItem(861, 1, 100),                 //Magic shortbow
            new LootItem(859, 1, 100),                 //Magic longbow
            new LootItem(380, 3, 10, 100),             //Lobster
            new LootItem(386, 3, 10, 100),             //Shark
            new LootItem(9185, 1, 100),                    //Rune crossbow
            new LootItem(4587, 1, 50),                //Dragon scimitar
            /**
             * Rare (4 / 25)
             */
            new LootItem(2627, 1, 50),          //Rune full helm (t)
            new LootItem(2623, 1, 50),          //Rune platebody (t)
            new LootItem(2625, 1, 50),          //Rune platelegs (t)
            new LootItem(3477, 1, 50),          //Rune plateskirt (t)
            new LootItem(2629, 1, 50),          //Rune kiteshield (t)
            new LootItem(2619, 1, 50),          //Rune full helm (g)
            new LootItem(2615, 1, 50),          //Rune platebody (g)
            new LootItem(2617, 1, 50),          //Rune platelegs (g)
            new LootItem(3476, 1, 50),          //Rune plateskirt (g)
            new LootItem(2621, 1, 50),          //Rune kiteshield (g)
            new LootItem(2673, 1, 50),          //Guthix full helm
            new LootItem(2669, 1, 50),          //Guthix platebody
            new LootItem(2671, 1, 50),          //Guthix platelegs
            new LootItem(3480, 1, 50),          //Guthix plateskirt
            new LootItem(2675, 1, 50),          //Guthix kiteshield
            new LootItem(2665, 1, 50),          //Saradomin full helm
            new LootItem(2661, 1, 50),          //Saradomin platebody
            new LootItem(2663, 1, 50),          //Saradomin platelegs
            new LootItem(3479, 1, 50),          //Saradomin plateskirt
            new LootItem(2667, 1, 50),          //Saradomin kiteshield
            new LootItem(2657, 1, 50),          //Zamorak full helm
            new LootItem(2653, 1, 50),          //Zamorak platebody
            new LootItem(2655, 1, 50),          //Zamorak platelegs
            new LootItem(3478, 1, 50),          //Zamorak plateskirt
            new LootItem(2659, 1, 50),          //Zamorak kiteshield
            new LootItem(12466, 1, 50),         //Ancient full helm
            new LootItem(12460, 1, 50),         //Ancient platebody
            new LootItem(12462, 1, 50),         //Ancient platelegs
            new LootItem(12464, 1, 50),         //Ancient plateskirt
            new LootItem(12468, 1, 50),         //Ancient kiteshield
            new LootItem(12486, 1, 50),         //Bandos full helm
            new LootItem(12480, 1, 50),         //Bandos platebody
            new LootItem(12482, 1, 50),         //Bandos platelegs
            new LootItem(12484, 1, 50),         //Bandos plateskirt
            new LootItem(12488, 1, 50),         //Bandos kiteshield
            new LootItem(12476, 1, 50),         //Armadyl full helm
            new LootItem(12470, 1, 50),         //Armadyl platebody
            new LootItem(12472, 1, 50),         //Armadyl platelegs
            new LootItem(12474, 1, 50),         //Armadyl plateskirt
            new LootItem(12478, 1, 50),         //Armadyl kiteshield
            new LootItem(7376, 1, 50),          //Blue d'hide body (t)
            new LootItem(7384, 1, 50),          //Blue d'hide chaps (t)
            new LootItem(7374, 1, 50),          //Blue d'hide body (g)
            new LootItem(7382, 1, 50),          //Blue d'hide chaps (g)
            new LootItem(12331, 1, 50),         //Red d'hide body (t)
            new LootItem(12333, 1, 50),         //Red d'hide chaps (t)
            new LootItem(12327, 1, 50),         //Red d'hide body (g)
            new LootItem(12329, 1, 50),         //Red d'hide chaps (g)
            new LootItem(7400, 1, 50),          //Enchanted hat
            new LootItem(7399, 1, 50),          //Enchanted top
            new LootItem(7398, 1, 50),          //Enchanted robe
            new LootItem(2581, 1, 50),          //Robin hood hat
            new LootItem(2639, 1, 50),          //Tan cavalier
            new LootItem(2641, 1, 50),          //Dark cavalier
            new LootItem(2643, 1, 50),          //Black cavalier
            new LootItem(12323, 1, 50),         //Red cavalier
            new LootItem(12321, 1, 50),         //White cavalier
            new LootItem(12325, 1, 50),         //Navy cavalier
            new LootItem(2651, 1, 50),          //Pirate's hat
            new LootItem(10362, 1, 50),         //Amulet of glory (t)
            new LootItem(10382, 1, 50),         //Guthix coif
            new LootItem(10378, 1, 50),         //Guthix dragonhide
            new LootItem(10380, 1, 50),         //Guthix chaps
            new LootItem(10376, 1, 50),         //Guthix bracers
            new LootItem(10390, 1, 50),         //Saradomin coif
            new LootItem(10386, 1, 50),         //Saradomin d'hide
            new LootItem(10388, 1, 50),         //Saradomin chaps
            new LootItem(10384, 1, 50),         //Saradomin bracers
            new LootItem(10374, 1, 50),         //Zamorak coif
            new LootItem(10370, 1, 50),         //Zamorak d'hide
            new LootItem(10372, 1, 50),         //Zamorak chaps
            new LootItem(10368, 1, 50),         //Zamorak bracers
            new LootItem(12512, 1, 50),         //Armadyl coif
            new LootItem(12508, 1, 50),         //Armadyl d'hide
            new LootItem(12510, 1, 50),         //Armadyl chaps
            new LootItem(12506, 1, 50),         //Armadyl bracers
            new LootItem(12496, 1, 50),         //Ancient coif
            new LootItem(12492, 1, 50),         //Ancient d'hide
            new LootItem(12494, 1, 50),         //Ancient chaps
            new LootItem(12490, 1, 50),         //Ancient bracers
            new LootItem(12504, 1, 50),         //Bandos coif
            new LootItem(12500, 1, 50),         //Bandos d'hide
            new LootItem(12502, 1, 50),         //Bandos chaps
            new LootItem(12498, 1, 50),         //Bandos bracers
            new LootItem(19927, 1, 50),         //Guthix d'hide boots
            new LootItem(19933, 1, 50),         //Saradomin d'hide boots
            new LootItem(19936, 1, 50),         //Zamorak d'hide boots
            new LootItem(19930, 1, 50),         //Armadyl d'hide boots
            new LootItem(19921, 1, 50),         //Ancient d'hide boots
            new LootItem(19924, 1, 50),         //Bandos d'hide boots
            new LootItem(10472, 1, 50),         //Guthix stole
            new LootItem(10442, 1, 50),         //Guthix crozier
            new LootItem(10470, 1, 50),         //Saradomin stole
            new LootItem(10440, 1, 50),         //Saradomin crozier
            new LootItem(10474, 1, 50),         //Zamorak stole
            new LootItem(10444, 1, 50),         //Zamorak crozier
            new LootItem(12518, 1, 50),         //Green dragon mask
            new LootItem(12520, 1, 50),         //Blue dragon mask
            new LootItem(12522, 1, 50),         //Red dragon mask
            new LootItem(12524, 1, 50),         //Black dragon mask
            new LootItem(12516, 1, 50),         //Pith helmet
            new LootItem(12514, 1, 50),         //Explorer backpack
            new LootItem(12379, 1, 50),         //Rune cane
            new LootItem(19912, 1, 50),         //Zombie head
            new LootItem(19915, 1, 50),         //Cyclops head
            new LootItem(19918, 1, 50),         //Nunchaku
            /**
             * Mega rare (1 / 351)
             */
            new LootItem(3486, 1, 20),         //Gilded full helm
            new LootItem(3481, 1, 20),         //Gilded platebody
            new LootItem(3483, 1, 20),         //Gilded platelegs
            new LootItem(3485, 1, 20),         //Gilded plateskirt
            new LootItem(3488, 1, 20),         //Gilded kiteshield
            new LootItem(20146, 1, 20),        //Gilded med helm
            new LootItem(20149, 1, 20),        //Gilded chainbody
            new LootItem(20152, 1, 20),        //Gilded sq shield
            new LootItem(20155, 1, 20),        //Gilded 2h sword
            new LootItem(20158, 1, 20),        //Gilded spear
            new LootItem(20161, 1, 20),        //Gilded hasta
            /**
             * Ultra rare (1 / 3510)
             */
            new LootItem(10350, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age full helmet
            new LootItem(10348, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age platebody
            new LootItem(10346, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age platelegs
            new LootItem(10352, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age kiteshield

            new LootItem(10334, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age range coif
            new LootItem(10330, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age range top
            new LootItem(10332, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age range legs
            new LootItem(10336, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age vambraces

            new LootItem(10342, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age mage hat
            new LootItem(10338, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age robe top
            new LootItem(10340, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age robe
            new LootItem(10344, 1, 1).broadcast(Broadcast.GLOBAL)          //3rd age amulet
    ),
    ELITE(
            12073, 20543, 10, 12,
            /**
             * Common
             */
            new LootItem(1163, 1, 150),                //Rune full helm
            new LootItem(1127, 1, 150),                //Rune platebody
            new LootItem(1079, 1, 150),                //Rune platelegs
            new LootItem(1093, 1, 150),                //Rune plateskirt
            new LootItem(1201, 1, 150),                //Rune kiteshield
            new LootItem(1303, 1, 150),                //Rune longsword
            new LootItem(1319, 1, 150),                //Rune 2h sword
            new LootItem(1373, 1, 150),                //Rune battleaxe
            new LootItem(1359, 1, 150),                //Rune axe
            new LootItem(1275, 1, 150),                //Rune pickaxe
            new LootItem(3025, 3, 5, 150),             //Super restore(4)
            new LootItem(6686, 3, 5, 150),             //Saradomin brew(4)
            new LootItem(11952, 3, 5, 150),            //Extended antifire (4)
            new LootItem(2445, 3, 5, 150),             //Ranging potion(4)
            new LootItem(1645, 1, 150),                //Dragonstone ring
            new LootItem(1702, 1, 150),                //Dragonstone amulet
            new LootItem(9194, 3, 15, 150),            //Onyx bolt tips
            new LootItem(5315, 1, 150),                //Yew seed
            new LootItem(5316, 1, 150),                //Magic seed
            new LootItem(985, 1, 150),                 //Tooth half of key
            new LootItem(987, 1, 150),                 //Loop half of key
            new LootItem(989, 1, 150),                 //Crystal key
            new LootItem(7061, 3, 5, 150),             //Tuna potato
            new LootItem(7219, 3, 5, 150),             //Summer pie
            /**
             * Rare (4 / 25)
             */
            new LootItem(12538, 1, 75),         //Dragon full helm ornament kit
            new LootItem(12534, 1, 75),         //Dragon chainbody ornament kit
            new LootItem(12536, 1, 75),         //Dragon legs/skirt ornament kit
            new LootItem(12532, 1, 75),         //Dragon sq shield ornament kit
            new LootItem(20002, 1, 75),         //Dragon scimitar ornament kit
            new LootItem(12528, 1, 75),         //Dark infinity colour kit
            new LootItem(12530, 1, 75),         //Light infinity colour kit
            new LootItem(12526, 1, 75),         //Fury ornament kit
            new LootItem(12351, 1, 75),         //Musketeer hat
            new LootItem(12441, 1, 75),         //Musketeer tabard
            new LootItem(12443, 1, 75),         //Musketeer pants
            new LootItem(12373, 1, 75),         //Dragon cane
            new LootItem(12335, 1, 75),         //Briefcase
            new LootItem(12337, 1, 75),         //Sagacious spectacles
            new LootItem(12432, 1, 75),         //Top hat
            new LootItem(12353, 1, 75),         //Monocle
            new LootItem(12355, 1, 75),         //Big pirate hat
            new LootItem(12540, 1, 75),         //Deerstalker
            new LootItem(12363, 1, 75),         //Bronze dragon mask
            new LootItem(12365, 1, 75),         //Iron dragon mask
            new LootItem(12367, 1, 75),         //Steel dragon mask
            new LootItem(12369, 1, 75),         //Mithril dragon mask
            new LootItem(12385, 1, 75),         //Black d'hide body (t)
            new LootItem(12387, 1, 75),         //Black d'hide chaps (t)
            new LootItem(12381, 1, 75),         //Black d'hide body (g)
            new LootItem(12383, 1, 75),         //Black d'hide chaps (g)
            new LootItem(12596, 1, 75),         //Rangers' tunic
            new LootItem(12430, 1, 75),         //Afro
            new LootItem(12357, 1, 75),         //Katana
            new LootItem(12397, 1, 75),         //Royal crown
            new LootItem(12393, 1, 75),         //Royal gown top
            new LootItem(12395, 1, 75),         //Royal gown bottom
            new LootItem(12439, 1, 75),         //Royal sceptre
            new LootItem(19943, 1, 75),         //Arceuus house scarf
            new LootItem(19946, 1, 75),         //Hosidius house scarf
            new LootItem(19949, 1, 75),         //Lovakengj house scarf
            new LootItem(19952, 1, 75),         //Piscarilius house scarf
            new LootItem(19955, 1, 75),         //Shayzien house scarf
            new LootItem(19988, 1, 75),         //Blacksmith's helm
            new LootItem(19991, 1, 75),         //Bucket helm
            new LootItem(19994, 1, 75),         //Ranger gloves
            new LootItem(19997, 1, 75),         //Holy wraps
            new LootItem(20005, 1, 75),         //Ring of nature
            new LootItem(19970, 1, 75),         //Dark bow tie
            new LootItem(19958, 1, 75),         //Dark tuxedo jacket
            new LootItem(19964, 1, 75),         //Dark trousers
            new LootItem(19961, 1, 75),         //Dark tuxedo cuffs
            new LootItem(19967, 1, 75),         //Dark tuxedo shoes
            new LootItem(19985, 1, 75),         //Light bow tie
            new LootItem(19973, 1, 75),         //Light tuxedo jacket
            new LootItem(19979, 1, 75),         //Light trousers
            new LootItem(19976, 1, 75),         //Light tuxedo cuffs
            new LootItem(19982, 1, 75),         //Light tuxedo shoes
            new LootItem(19941, 1, 75),         //Heavy casket
            /**
             * Mega rare (1 / 206.25)
             */
            new LootItem(3486, 1, 25),       //Gilded full helm
            new LootItem(3481, 1, 25),       //Gilded platebody
            new LootItem(3483, 1, 25),       //Gilded platelegs
            new LootItem(3485, 1, 25),       //Gilded plateskirt
            new LootItem(3488, 1, 25),       //Gilded kiteshield
            new LootItem(20146, 1, 25),      //Gilded med helm
            new LootItem(20149, 1, 25),      //Gilded chainbody
            new LootItem(20152, 1, 25),      //Gilded sq shield
            new LootItem(20155, 1, 25),      //Gilded 2h sword
            new LootItem(20158, 1, 25),      //Gilded spear
            new LootItem(20161, 1, 25),      //Gilded hasta
            new LootItem(12391, 1, 25),      //Gilded boots
            new LootItem(12389, 1, 25),      //Gilded scimitar
            new LootItem(12371, 1, 25),      //Lava dragon mask
            /**
             * Ultra rare (1 / 3300)
             */
            new LootItem(10350, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age full helmet
            new LootItem(10348, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age platebody
            new LootItem(10346, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age platelegs
            new LootItem(10352, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age kiteshield

            new LootItem(10342, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age mage hat
            new LootItem(10338, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age robe top
            new LootItem(10340, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age robe
            new LootItem(10344, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age amulet

            new LootItem(10334, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age range coif
            new LootItem(10330, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age range top
            new LootItem(10332, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age range legs
            new LootItem(10336, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age vambraces

            new LootItem(12437, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age cloak
            new LootItem(12422, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age wand
            new LootItem(12424, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age bow
            new LootItem(12426, 1, 1).broadcast(Broadcast.GLOBAL)          //3rd age longsword
    ),
    MASTER(
            19835, 19836, 15, 17,
            /**
             * Common
             */
            new LootItem(1163, 1, 150),                //Rune full helm
            new LootItem(1127, 1, 150),                //Rune platebody
            new LootItem(1079, 1, 150),                //Rune platelegs
            new LootItem(1093, 1, 150),                //Rune plateskirt
            new LootItem(1201, 1, 150),                //Rune kiteshield
            new LootItem(1303, 1, 150),                //Rune longsword
            new LootItem(1319, 1, 150),                //Rune 2h sword
            new LootItem(1373, 1, 150),                //Rune battleaxe
            new LootItem(1359, 1, 150),                //Rune axe
            new LootItem(1275, 1, 150),                //Rune pickaxe
            new LootItem(3025, 3, 5, 150),             //Super restore(4)
            new LootItem(6686, 3, 5, 150),             //Saradomin brew(4)
            new LootItem(11952, 3, 5, 150),            //Extended antifire (4)
            new LootItem(2445, 3, 5, 150),             //Ranging potion(4)
            new LootItem(1645, 1, 150),                //Dragonstone ring
            new LootItem(1702, 1, 150),                //Dragonstone amulet
            new LootItem(9194, 3, 15, 150),            //Onyx bolt tips
            new LootItem(5315, 1, 150),                //Yew seed
            new LootItem(5316, 1, 150),                //Magic seed
            new LootItem(985, 1, 150),                 //Tooth half of key
            new LootItem(987, 1, 150),                 //Loop half of key
            new LootItem(989, 1, 150),                 //Crystal key
            new LootItem(7061, 3, 5, 150),             //Tuna potato
            new LootItem(7219, 3, 5, 150),             //Summer pie
            /**
             * Rare (4 / 25)
             */
            new LootItem(20238, 1, 100),         //Charge dragonstone jewellery scroll
            new LootItem(20143, 1, 100),         //Dragon defender ornament kit
            new LootItem(20065, 1, 100),         //Occult ornament kit
            new LootItem(20062, 1, 100),         //Torture ornament kit
            new LootItem(20068, 1, 100),         //Armadyl godsword ornament kit
            new LootItem(20071, 1, 100),         //Bandos godsword ornament kit
            new LootItem(20074, 1, 100),         //Saradomin godsword ornament kit
            new LootItem(20077, 1, 100),         //Zamorak godsword ornament kit
            new LootItem(20113, 1, 100),         //Arceuus house hood
            new LootItem(20116, 1, 100),         //Hosidius house hood
            new LootItem(20119, 1, 100),         //Lovakengj house hood
            new LootItem(20122, 1, 100),         //Piscarilius house hood
            new LootItem(20125, 1, 100),         //Shayzien house hood
            new LootItem(20020, 1, 100),         //Lesser demon mask
            new LootItem(20023, 1, 100),         //Greater demon mask
            new LootItem(20026, 1, 100),         //Black demon mask
            new LootItem(20032, 1, 100),         //Jungle demon mask
            new LootItem(20029, 1, 100),         //Old demon mask
            new LootItem(20035, 1, 100),         //Samurai kasa
            new LootItem(20038, 1, 100),         //Samurai shirt
            new LootItem(20041, 1, 100),         //Samurai gloves
            new LootItem(20044, 1, 100),         //Samurai greaves
            new LootItem(20047, 1, 100),         //Samurai boots
            new LootItem(20080, 1, 100),         //Mummy's head
            new LootItem(20083, 1, 100),         //Mummy's body
            new LootItem(20086, 1, 100),         //Mummy's hands
            new LootItem(20089, 1, 100),         //Mummy's legs
            new LootItem(20092, 1, 100),         //Mummy's feet
            new LootItem(20095, 1, 100),         //Ankou mask
            new LootItem(20098, 1, 100),         //Ankou top
            new LootItem(20101, 1, 100),         //Ankou gloves
            new LootItem(20104, 1, 100),         //Ankou's leggings
            new LootItem(20107, 1, 100),         //Ankou socks
            new LootItem(20128, 1, 100),         //Hood of darkness
            new LootItem(20131, 1, 100),         //Robe top of darkness
            new LootItem(20134, 1, 100),         //Gloves of darkness
            new LootItem(20137, 1, 100),         //Robe bottom of darkness
            new LootItem(20140, 1, 100),         //Boots of darkness
            /**
             * Mega rare (1 / 206.25)
             */
            new LootItem(20017, 1, 50),     //Ring of coins
            new LootItem(19724, 1, 50),     //Left eye patch
            new LootItem(20050, 1, 50),     //Obsidian cape (r)
            new LootItem(20008, 1, 50),     //Fancy tiara
            new LootItem(20053, 1, 50),     //Half moon spectacles
            new LootItem(20056, 1, 50),     //Ale of the gods
            new LootItem(20059, 1, 50),     //Bucket helm (g)
            new LootItem(20110, 1, 50),     //Bowl wig
            new LootItem(19730, 1, 1),     //Bloodhound
            /**
             * Ultra rare (1 / 3300)
             */
            new LootItem(10350, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age full helmet
            new LootItem(10348, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age platebody
            new LootItem(10346, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age platelegs
            new LootItem(10352, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age kiteshield

            new LootItem(10342, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age mage hat
            new LootItem(10338, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age robe top
            new LootItem(10340, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age robe
            new LootItem(10344, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age amulet

            new LootItem(10334, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age range coif
            new LootItem(10330, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age range top
            new LootItem(10332, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age range legs
            new LootItem(10336, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age vambraces

            new LootItem(12437, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age cloak
            new LootItem(12422, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age wand
            new LootItem(12424, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age bow
            new LootItem(12426, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age longsword

            new LootItem(20011, 1, 1).broadcast(Broadcast.GLOBAL),         //3rd age axe
            new LootItem(20014, 1, 1).broadcast(Broadcast.GLOBAL)          //3rd age pickaxe
    );

    public final int clueId, casketId;

    private final int minStages, maxStages;

    private final String descriptiveName;

    public final LootItem[] loots;

    ClueType(int clueId, int casketId, int minStages, int maxStages, LootItem... loots) {
        this.clueId = clueId;
        this.casketId = casketId;
        this.minStages = minStages;
        this.maxStages = maxStages;
        this.descriptiveName = StringUtils.vowelStart(name()) ? ("an " + name().toLowerCase()) : ("a " + name().toLowerCase());
        /**
         * Loots
         */
        ArrayList<LootItem> list = new ArrayList<>();
        double baseChance = 1d / 10d;
        LootItem[] baseLoots = {
                new LootItem(3827, 1, 10),          //Saradomin page 1
                new LootItem(3828, 1, 10),          //Saradomin page 2
                new LootItem(3829, 1, 10),          //Saradomin page 3
                new LootItem(3830, 1, 10),          //Saradomin page 4
                new LootItem(3835, 1, 10),          //Guthix page 1
                new LootItem(3836, 1, 10),          //Guthix page 2
                new LootItem(3837, 1, 10),          //Guthix page 3
                new LootItem(3838, 1, 10),          //Guthix page 4
                new LootItem(3831, 1, 10),          //Zamorak page 1
                new LootItem(3832, 1, 10),          //Zamorak page 2
                new LootItem(3833, 1, 10),          //Zamorak page 3
                new LootItem(3834, 1, 10),          //Zamorak page 4
                new LootItem(12617, 1, 10),         //Armadyl page 1
                new LootItem(12618, 1, 10),         //Armadyl page 2
                new LootItem(12619, 1, 10),         //Armadyl page 3
                new LootItem(12620, 1, 10),         //Armadyl page 4
                new LootItem(12621, 1, 10),         //Ancient page 1
                new LootItem(12622, 1, 10),         //Ancient page 2
                new LootItem(12623, 1, 10),         //Ancient page 3
                new LootItem(12624, 1, 10),         //Ancient page 4
                new LootItem(12613, 1, 10),         //Bandos page 1
                new LootItem(12614, 1, 10),         //Bandos page 2
                new LootItem(12615, 1, 10),         //Bandos page 3
                new LootItem(12616, 1, 10),         //Bandos page 4
                new LootItem(10326, 1, 10),         //Purple firelighter
                new LootItem(7329, 1, 10),          //Red firelighter
                new LootItem(7331, 1, 10),          //Blue firelighter
                new LootItem(7330, 1, 10),          //Green firelighter
                new LootItem(10327, 1, 10),         //White firelighter
                new LootItem(10476, 10, 50, 10),    //Purple sweets
                new LootItem(554, 10, 50, 10),      //Fire rune
                new LootItem(555, 10, 50, 10),      //Water rune
                new LootItem(556, 10, 50, 10),      //Air rune
                new LootItem(557, 10, 50, 10),      //Earth rune
                new LootItem(558, 10, 50, 10),      //Mind rune
                new LootItem(560, 10, 50, 10),      //Death rune
                new LootItem(561, 10, 50, 10),      //Nature rune
                new LootItem(562, 10, 50, 10),      //Chaos rune
                new LootItem(563, 10, 50, 10),      //Law rune
                new LootItem(564, 10, 50, 10),      //Cosmic rune
                new LootItem(565, 10, 50, 10),      //Blood rune
                new LootItem(1381, 1, 10),          //Staff of air
                new LootItem(1383, 1, 10),          //Staff of water
                new LootItem(1385, 1, 10),          //Staff of earth
                new LootItem(1387, 1, 10),          //Staff of fire
                new LootItem(1694, 1, 10),          //Sapphire amulet
                new LootItem(1696, 1, 10),          //Emerald amulet
                new LootItem(1698, 1, 10),          //Ruby amulet
                new LootItem(1700, 1, 10),          //Diamond amulet
        };
        Collections.addAll(list, baseLoots);
        Collections.addAll(list, loots);
        this.loots = list.toArray(new LootItem[list.size()]);
    }

    private void open(Player player) {
        ClueSave save;
        if(this == MASTER) {
            if(player.masterClue == null)
                player.masterClue = new ClueSave();
            save = player.masterClue;
        } else if(this == ELITE) {
            if(player.eliteClue == null)
                player.eliteClue = new ClueSave();
            save = player.eliteClue;
        } else if(this == HARD) {
            if(player.hardClue == null)
                player.hardClue = new ClueSave();
            save = player.hardClue;
        } else if(this == MEDIUM) {
            if(player.medClue == null)
                player.medClue = new ClueSave();
            save = player.medClue;
        } else {
            if(player.easyClue == null)
                player.easyClue = new ClueSave();
            save = player.easyClue;
        }
        if(save.id == -1 || Clue.CLUES[save.id] == null) {
            ArrayList<Clue> clues = new ArrayList<>();
            for(Clue clue : Clue.CLUES) {
                if(clue != null && clue.type.ordinal() <= ordinal())
                    clues.add(clue);
            }
            Collections.shuffle(clues);
            save.id = clues.get(0).id;
            if(save.remaining == 0)
                save.remaining = Random.get(minStages, maxStages);
        }

        Clue.CLUES[save.id].open(player);
    }

    public void loot(Player player) {
        ItemContainer container = new ItemContainer();
        container.init(player, 6, -1, 0, 141, true);
        container.sendAll = true;

        LootTable table = new LootTable().addTable(1, loots);
        for(int i = 0; i < Random.get(6); i++) {
            Item item = table.rollItem();
            if(item.lootBroadcast != null)
               item.lootBroadcast.sendNews(player, player.getName() + " just received " + item.getDef().descriptiveName + " from " + descriptiveName + " clue scroll!");
            container.add(item);
        }

        if(container.getFreeSlots() > 0)
            container.add(COINS_995, Random.get(5000, (ordinal() + 1) * 25000));
        for(Item item : container.getItems()) {
            if(item != null)
                player.getInventory().addOrDrop(item.getId(), item.getAmount());
        }
        player.openInterface(InterfaceType.MAIN, 73);
        container.sendUpdates();
    }

    public ClueSave getSave(Player player) {
        if(this == MASTER)
            return player.masterClue;
        if(this == ELITE)
            return player.eliteClue;
        if(this == HARD)
            return player.hardClue;
        if(this == MEDIUM)
            return player.medClue;
        return player.easyClue;
    }

    static {
        for(ClueType type : values()) {
            /*
             * Clue scroll
             */
            if(type.clueId == -1)
                continue;
            ItemDef clueDef = ItemDef.get(type.clueId);
            clueDef.clueType = type;
            ItemAction.registerInventory(clueDef.id, "read", (player, item) -> {
                type.open(player);
            });
            ItemAction.registerInventory(clueDef.id, "check steps", (player, item) -> {
                ClueSave save = type.getSave(player);
                if(save == null)
                    player.sendMessage("You haven't started this clue yet.");
                else if(save.remaining == 1)
                    player.sendMessage("There is 1 step remaining in this clue.");
                else
                    player.sendMessage("There are " + save.remaining + " steps remaining in this clue.");
            });
            /*
             * Casket
             */
            ItemDef casketDef = ItemDef.get(type.casketId);
            ItemAction.registerInventory(casketDef.id, "open", (player, item) -> {
                item.remove(1);
                type.loot(player);
            });
        }
    }

}