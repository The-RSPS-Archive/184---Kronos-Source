package io.ruin.model.item.actions.impl.combine;

import io.ruin.cache.ItemDef;
import io.ruin.cache.ItemID;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlayerHelm {

    public static final int NOSE_PEG = 4168;
    public static final int FACEMASK = 4164;
    public static final int GEM = 4155;
    public static final int EARMUFFS = 4166;
    public static final int BLACK_MASK = 8901;
    public static final int BLACK_MASK_IMBUE = 11784;
    public static final int SPINY_HELM = 4551;
    public static final int SLAYER_HELM = 11864;
    public static final int SLAYER_HELM_IMBUE = 11865;

    public static final int RED_SLAYER_HELM = 19647;
    public static final int RED_HELM_IMBUE = 19649;
    public static final int GREEN_SLAYER_HELM = 19643;
    public static final int GREEN_HELM_IMBUE = 19645;
    public static final int BLACK_SLAYER_HELM = 19639;
    public static final int BLACK_HELM_IMBUE = 19641;
    public static final int PURPLE_SLAYER_HELM = 21264;
    public static final int PURPLE_HELM_IMBUE = 21266;
    public static final int TURQUOISE_SLAYER_HELM = 21888;
    public static final int TURQUOISE_HELM_IMBUE = 21890;
    public static final int HYDRA_SLAYER_HELM = ItemID.HYDRA_SLAYER_HELMET;
    public static final int HYDRA_HELM_IMBUE = ItemID.HYDRA_SLAYER_HELMET_I;

    public static final int ABYSSAL_HEAD = 7979;
    public static final int KQ_HEAD = 7981;
    public static final int KBD_HEADS = 7980;
    public static final int DARK_CLAW = 21275;
    public static final int VORKATHS_HEAD = 2425;
    public static final int VORKATH_SLAYER_HELM = ItemID.TURQUOISE_SLAYER_HELMET;
    public static final int VORKATH_SLAYER_HELM_IMBUE = ItemID.TURQUOISE_SLAYER_HELMET_I;
    public static final int HYDRAS_HEAD = ItemID.ALCHEMICAL_HYDRA_HEADS;

    private static final List<Integer> MELEE_BOOST_HELMS = Arrays.asList(
            8901, 8903, 8905, 8907, 8909, 8911, 8913, 8915, 8917, 8919, 8921, // black masks
            SLAYER_HELM, RED_SLAYER_HELM, GREEN_SLAYER_HELM, BLACK_SLAYER_HELM, PURPLE_SLAYER_HELM, TURQUOISE_SLAYER_HELM, HYDRA_SLAYER_HELM
    );

    private static final List<Integer> ALL_BOOST_HELMS = Arrays.asList(
            8901, 8903, 8905, 8907, 8909, 8911, 8913, 8915, 8917, 8919, 8921, // black masks
            SLAYER_HELM_IMBUE, RED_HELM_IMBUE, GREEN_HELM_IMBUE, BLACK_HELM_IMBUE, PURPLE_HELM_IMBUE, TURQUOISE_HELM_IMBUE, HYDRA_HELM_IMBUE
    );

    static {
        MELEE_BOOST_HELMS.stream().map(ItemDef::get).forEach(def -> def.slayerBoostMelee = true);
        ALL_BOOST_HELMS.stream().map(ItemDef::get).forEach(def -> def.slayerBoostAll = true);
    }


    public static boolean boost(Player player, Entity target, Hit hit) {
        if (hit.attackStyle != null && target.npc != null && (Slayer.isTask(player, target.npc) || target.npc.getId() == 7413)) { // npc 7413 = undead combat dummy, always counts as task for max hit
            ItemDef helm = player.getEquipment().getDef(Equipment.SLOT_HAT);
            if (helm == null)
                return false;
            if (hit.attackStyle.isMelee() && (helm.slayerBoostMelee || helm.slayerBoostAll)) {
                hit.boostAttack((7.0/6) - 1);
                hit.boostDamage((7.0/6) - 1);
                return true;
            } else if (helm.slayerBoostAll) {
                hit.boostAttack(0.15);
                hit.boostDamage(0.15);
                return true;
            }
        }
        return false;
    }

    private static void makeMask(Player player, boolean imbued) {
        if(Config.SLAYER_UNLOCKED_HELM.get(player) != 1) {
            player.sendMessage("You have not unlocked the ability to combine these items.");
            return;
        }

        ArrayList<Item> items = player.getInventory().collectOneOfEach(NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, imbued ? BLACK_MASK_IMBUE : BLACK_MASK);
        if(items == null) {
            player.sendMessage("You need a nosepeg, facemask, earmuffs, spiny helmet, enchanted gem and a black mask in your inventory in order to construct a Slayer helm.");
            return;
        }
        if(player.getStats().get(StatType.Crafting).currentLevel < 55) {
            player.sendMessage("You need a Crafting level of 55 to make a Slayer helm.");
            return;
        }
        items.forEach(Item::remove);
        player.getInventory().add(imbued ? SLAYER_HELM_IMBUE : SLAYER_HELM, 1);
        player.sendMessage("You combine the items into a Slayer helm.");
    }

    private static void colorizeHelm(Player player, Item primary, Item secondary, int result, Config config) {
        if(config.get(player) != 1) {
            player.sendMessage("You need to unlock this ability from a Slayer master first!");
            return;
        }

        primary.remove();
        secondary.remove();
        player.getInventory().add(result, 1);
    }

    private static void disassemble(Player player, Item item, int...itemIds) {
        if(player.getInventory().getFreeSlots() < (itemIds.length - 1)) { //-1 because the item itself is being removed, which makes for 1 more slot.
            player.sendMessage("You don't have enough space to do this.");
            return;
        }
        item.remove();
        for(int itemId : itemIds)
            player.getInventory().add(itemId, 1);
        player.sendMessage("You disassemble your Slayer helm.");
    }

    static {
        /*
         * Item combining
         */
        ItemItemAction.register(NOSE_PEG, FACEMASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(NOSE_PEG, GEM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(NOSE_PEG, EARMUFFS, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(NOSE_PEG, BLACK_MASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(NOSE_PEG, BLACK_MASK_IMBUE, (player, primary, secondary) -> makeMask(player,  true));
        ItemItemAction.register(NOSE_PEG, SPINY_HELM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(FACEMASK, GEM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(FACEMASK, EARMUFFS, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(FACEMASK, FACEMASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(FACEMASK, BLACK_MASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(FACEMASK, BLACK_MASK_IMBUE, (player, primary, secondary) -> makeMask(player,  true));
        ItemItemAction.register(FACEMASK, SPINY_HELM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(GEM, EARMUFFS, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(GEM, BLACK_MASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(GEM, BLACK_MASK_IMBUE, (player, primary, secondary) -> makeMask(player,  true));
        ItemItemAction.register(GEM, SPINY_HELM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(EARMUFFS, BLACK_MASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(EARMUFFS, BLACK_MASK_IMBUE, (player, primary, secondary) -> makeMask(player,  true));
        ItemItemAction.register(EARMUFFS, SPINY_HELM, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(SPINY_HELM, BLACK_MASK, (player, primary, secondary) -> makeMask(player,  false));
        ItemItemAction.register(SPINY_HELM, BLACK_MASK_IMBUE, (player, primary, secondary) -> makeMask(player,  true));

        /*
         * Colorizing
         */
        ItemItemAction.register(ABYSSAL_HEAD, SLAYER_HELM, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, RED_SLAYER_HELM, Config.UNHOLY_HELMET));
        ItemItemAction.register(ABYSSAL_HEAD, SLAYER_HELM_IMBUE, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, RED_HELM_IMBUE, Config.UNHOLY_HELMET));
        ItemItemAction.register(KBD_HEADS, SLAYER_HELM, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, BLACK_SLAYER_HELM, Config.KING_BLACK_BONNET));
        ItemItemAction.register(KBD_HEADS, SLAYER_HELM_IMBUE, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, BLACK_HELM_IMBUE, Config.KING_BLACK_BONNET));
        ItemItemAction.register(KQ_HEAD, SLAYER_HELM, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, GREEN_SLAYER_HELM, Config.KALPHITE_KHAT));
        ItemItemAction.register(KQ_HEAD, SLAYER_HELM_IMBUE, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, GREEN_HELM_IMBUE, Config.KALPHITE_KHAT));
        ItemItemAction.register(DARK_CLAW, SLAYER_HELM, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, PURPLE_SLAYER_HELM, Config.DARK_MANTLE));
        ItemItemAction.register(DARK_CLAW, SLAYER_HELM_IMBUE, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, PURPLE_HELM_IMBUE, Config.DARK_MANTLE));
        ItemItemAction.register(VORKATHS_HEAD, SLAYER_HELM, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, TURQUOISE_SLAYER_HELM, Config.UNDEAD_HEAD));
        ItemItemAction.register(VORKATHS_HEAD, SLAYER_HELM_IMBUE, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, TURQUOISE_HELM_IMBUE, Config.UNDEAD_HEAD));
        ItemItemAction.register(HYDRAS_HEAD, SLAYER_HELM, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, HYDRA_SLAYER_HELM, Config.UNDEAD_HEAD));
        ItemItemAction.register(HYDRAS_HEAD, SLAYER_HELM_IMBUE, (player, primary, secondary) -> colorizeHelm(player, primary, secondary, HYDRA_HELM_IMBUE, Config.UNDEAD_HEAD));

        /*
         * Disassemble
         */
        ItemAction.registerInventory(SLAYER_HELM, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK));
        ItemAction.registerInventory(SLAYER_HELM_IMBUE, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK_IMBUE));
        ItemAction.registerInventory(RED_SLAYER_HELM, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK, ABYSSAL_HEAD));
        ItemAction.registerInventory(RED_HELM_IMBUE, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK_IMBUE, ABYSSAL_HEAD));
        ItemAction.registerInventory(GREEN_SLAYER_HELM, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK, KQ_HEAD));
        ItemAction.registerInventory(GREEN_HELM_IMBUE, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK_IMBUE, KQ_HEAD));
        ItemAction.registerInventory(BLACK_SLAYER_HELM, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK, KBD_HEADS));
        ItemAction.registerInventory(BLACK_HELM_IMBUE, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK_IMBUE, KBD_HEADS));
        ItemAction.registerInventory(PURPLE_SLAYER_HELM, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK, DARK_CLAW));
        ItemAction.registerInventory(PURPLE_HELM_IMBUE, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK_IMBUE, DARK_CLAW));
        ItemAction.registerInventory(TURQUOISE_SLAYER_HELM, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK, VORKATHS_HEAD));
        ItemAction.registerInventory(TURQUOISE_HELM_IMBUE, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK_IMBUE, VORKATHS_HEAD));
        ItemAction.registerInventory(HYDRA_SLAYER_HELM, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK, HYDRAS_HEAD));
        ItemAction.registerInventory(HYDRA_HELM_IMBUE, "disassemble", (player, item) -> disassemble(player, item, NOSE_PEG, FACEMASK, GEM, EARMUFFS, SPINY_HELM, BLACK_MASK_IMBUE, HYDRAS_HEAD));

        for (int helm : Arrays.asList(SLAYER_HELM, SLAYER_HELM_IMBUE, RED_HELM_IMBUE, RED_SLAYER_HELM, GREEN_SLAYER_HELM, GREEN_HELM_IMBUE, BLACK_HELM_IMBUE, BLACK_SLAYER_HELM, PURPLE_HELM_IMBUE, PURPLE_SLAYER_HELM, TURQUOISE_SLAYER_HELM, TURQUOISE_HELM_IMBUE , HYDRA_SLAYER_HELM, HYDRA_HELM_IMBUE)) {
            ItemAction.registerEquipment(helm, 4, (player, item) -> Slayer.check(player));
            ItemAction.registerEquipment(helm, "log", (player, item) -> KillCounter.openOwnSlayer(player));
            ItemAction.registerInventory(helm, "check", (player, item) -> Slayer.check(player));
            ItemAction.registerInventory(ItemID.ENCHANTED_GEM, "check", (player, item) -> Slayer.check(player));
            ItemAction.registerInventory(ItemID.ENCHANTED_GEM, "activate", (player, item) -> Slayer.check(player));
            ItemAction.registerInventory(ItemID.ENCHANTED_GEM, "log", (player, item) -> KillCounter.openOwnSlayer(player));
        }
    }

}
