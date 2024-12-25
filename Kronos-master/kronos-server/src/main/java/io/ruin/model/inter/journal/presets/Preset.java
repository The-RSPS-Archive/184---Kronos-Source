package io.ruin.model.inter.journal.presets;

import com.google.gson.annotations.Expose;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.content.activities.tournament.TournamentManager;
import io.ruin.model.activities.clanwars.FFAClanWars;
import io.ruin.model.entity.npc.actions.edgeville.Nurse;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.impl.jewellery.RingOfRecoil;
import io.ruin.model.item.actions.impl.storage.RunePouch;
import io.ruin.model.item.containers.bank.BankItem;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

import static io.ruin.cache.ItemID.*;

public class Preset extends JournalEntry {

    private static final Bounds EDGE_BOUNDS = new Bounds(3036, 3478, 3144, 3524, 0);

    private static final Bounds MAGE_BANK = new Bounds(2527, 4708, 2551, 4727, 0);

    protected boolean free;

    @Expose protected String name;

    @Expose protected int[] attack, strength, defence, ranged, prayer, magic, hitpoints;

    @Expose protected SpellBook spellBook;

    @Expose protected Item[] invItems = new Item[28], equipItems = new Item[14];

    @Expose protected Item[] runePouchItems = new Item[3];

    @Override public void send(Player player) {
        send(player, name, Color.BRONZE);
    }

    /**
     * Selecting
     */

    public final boolean allowSelect(Player player) {
        // Shouldn't happen but safety check for now.
        if (TournamentManager.Lobby.contains(player) || player.tournament != null) {
            player.dialogue(new MessageDialogue("You can't spawn a preset while participating in a tournament."));
            return false;
        }
        if (player.isLocked()) {
            player.dialogue(new MessageDialogue("Please finish what you're doing before attempting this again."));
            return false;
        }
        if (player.wildernessLevel > 0) {
            player.dialogue(new MessageDialogue("You can't use this inside the wilderness."));
            return false;
        }
        if(player.presetDelay.isDelayed()) {
            int seconds = player.presetDelay.remaining() / 10 * 6;
            player.dialogue(new MessageDialogue("You have recently died above level 15 wilderness. Please wait another " + seconds + " seconds before spawning a preset."));
            return false;
        }
/*        if (player.pvpInstancePosition != null && !player.pvpAttackZone) {
            return true;
        }*/
        if(player.getPosition().inBounds(FFAClanWars.FFA_FIGHT_BOUNDS)) {
            player.sendMessage("You can't use presets while inside the fighting area!");
            return false;
        }
        if (!player.getPosition().inBounds(EDGE_BOUNDS) && !player.getPosition().inBounds(MAGE_BANK) && !player.pvpAttackZone) {
            //It's important that we don't let any preset actions unless the player is near a bank.
            //This ensures the player is not in any kind of minigame (or at least one that gives items.)
            player.dialogue(new MessageDialogue("Presets may only be used at Edgeville/Mage Bank/Pvp Zones"));
            return false;
        }
        if(player.getGameMode().isIronMan() && this.free) {
            player.dialogue(new MessageDialogue("You ironmen stand alone!"));
            return false;
        }
        /*player.dialogue(new OptionsDialogue("Pay with 250k coins or 25 Blood money?",
                new Option("Coins", () -> {
                    if (player.getInventory().contains(COINS_995, 250_000)) {
                        player.getInventory().remove(COINS_995, 250_000);
                    } else if (player.getBank().contains(COINS_995, 250_000)) {
                        player.getBank().remove(COINS_995, 250_000);
                    }
                }),
                new Option("Blood Money", () -> {
                    if (player.getInventory().contains(BLOOD_MONEY, 25)) {
                        player.getInventory().remove(BLOOD_MONEY, 25);
                    } else if (player.getBank().contains(BLOOD_MONEY, 25)) {
                        player.getBank().remove(BLOOD_MONEY, 25);
                    }
                })
        ));*/

        if(player.getBankPin().requiresVerification(this::allowSelect))
            return false;

        return true;
    }

    @Override public final void select(Player player) {
        if (allowSelect(player))
            selectStart(player);
    }

    public void selectStart(Player player) {
        player.dialogue(
                new OptionsDialogue(
                        name,
                        new Option("Select Preset", () -> selectFinish(player)),
                        new Option("Nevermind", player::closeDialogue)
                )
        );
    }

    public void selectFinish(Player player) {
        bankItems(player, true);
        player.getRunePouch().empty(true);
        if (!bankItems(player, false)) {
            player.sendMessage(Color.RED.wrap("Failed to load preset. Please make sure you have enough bank space to deposit your current gear."));
            return;
        }

        /*
         * Set combat levels
         */
        if (!player.getGameMode().isIronMan()) {
            if (attack != null)
                player.getStats().set(StatType.Attack, attack[0], attack[1]);
            if (strength != null)
                player.getStats().set(StatType.Strength, strength[0], strength[1]);
            if (defence != null)
                player.getStats().set(StatType.Defence, defence[0], defence[1]);
            if (ranged != null)
                player.getStats().set(StatType.Ranged, ranged[0], ranged[1]);
            if (prayer != null)
                player.getStats().set(StatType.Prayer, prayer[0], prayer[1]);
            if (magic != null)
                player.getStats().set(StatType.Magic, magic[0], magic[1]);
            if (hitpoints != null)
                player.getStats().set(StatType.Hitpoints, hitpoints[0], hitpoints[1]);
        }
        
        /*
         * Set items
         */
        withdrawItems(player, true);
        withdrawItems(player, false);

        /*
         * Set spellbook
         */
        if (spellBook != null) {
            spellBook.setActive(player);
        }

        /*
         * Misc
         */
        player.getPrayer().deactivateAll();
        player.getCombat().updateLevel();
        Nurse.heal(player, null);
        player.lastPresetUsed = this;
        PlayerCounter.PRESETS_LOADED.increment(player, 1);
    }

    private boolean bankItems(Player player, boolean inventory) {
        ItemContainer container;
        if (inventory) {
            container = player.getInventory();
        } else {
            container = player.getEquipment();
        }

        /*
         * First, deposit any items you have.
         */
        for (Item item : container.getItems()) {
            if (item != null && player.getBank().deposit(item, item.getAmount(), false) == 0) {
                player.sendMessage(Color.COOL_BLUE.wrap(item.getDef().name) + " x " + item.getAmount() + " could not be banked.");
                if (!inventory) // it's okay if we fail to bank an inventory item, but we have to abort if we fail equipment or we could end up with gear equipped that we don't have stat requirements for
                    return false;
            }
        }
        return true;
    }

    private void withdrawItems(Player player, boolean inventory) {
        Item[] items;
        ItemContainer container;
        if (inventory) {
            items = invItems;
            container = player.getInventory();
        } else {
            items = equipItems;
            container = player.getEquipment();
        }

        /*
         * Second, try to withdraw items saved in your preset.
         */
        int removedBankItems = 0;
        outer:
        for (int i = 0; i < items.length; i++) {
            if (container.get(i) != null) {
                //Item failed to bank
                continue;
            }
            Item presetItem = items[i];
            if (presetItem == null) {
                container.set(i, null);
                continue;
            }
            ItemDef presetDef = presetItem.getDef();
            if (free || presetDef.bmShopPrice == 0) {
                container.set(i, presetItem.copy());
                continue;
            }
            if(presetDef.equipReqs != null) {
                for(int req : presetDef.equipReqs) {
                    int statId = req >> 8;
                    int lvl = req & 0xff;
                    Stat stat = player.getStats().get(statId);
                    if(stat.fixedLevel < lvl) {
                        player.sendMessage("You need " + StatType.get(statId).descriptiveName + " level of " + lvl + " to equip this item.");
                        continue outer;
                    }
                }
            }
            BankItem bankItem = player.getBank().findItem(presetItem.getId());
            if (bankItem == null) {
                player.sendMessage(Color.COOL_BLUE.wrap(presetDef.name) + " x " + Color.COOL_BLUE.wrap("" + presetItem.getAmount()) + " not in bank.");
                continue;
            }
            if (presetItem.getAmount() >= bankItem.getAmount()) {
                container.set(i, bankItem.copy());
                if (!player.getBank().toPlaceholder(bankItem)) {
                    bankItem.remove();
                    removedBankItems++;
                }
            } else {
                container.set(i, new Item(presetItem.getId(), presetItem.getAmount(), bankItem.copyOfAttributes()));
                bankItem.incrementAmount(-presetItem.getAmount());
            }
            if (presetItem.getId() == RunePouch.RUNE_POUCH) {
                if(runePouchItems != null) {
                    for (Item rune : runePouchItems) {
                        if (rune != null) {
                            BankItem runeItem = player.getBank().findItem(rune.getId());
                            if (runeItem == null)
                                continue;
                            player.getRunePouch().deposit(runeItem, rune.getAmount());
                        }
                    }
                }
            }
            if(presetItem.getId() == RingOfRecoil.RING_OF_RECOIL)
                RingOfRecoil.rechargeRing(player);
        }
        if (removedBankItems > 0) //meh I rather only do this when bank opens
            player.getBank().sort();
    }

    /**
     * Utils
     */
    protected static int[] level(int level) {
        return new int[]{level, Stat.xpForLevel(level)};
    }

    protected static int[] level(Stat stat) {
        return new int[]{stat.fixedLevel, (int) stat.experience};
    }

    protected static void copy(ItemContainer srcContainer, Item[] destItems) {
        Item[] srcItems = srcContainer.getItems();
        for (int i = 0; i < srcItems.length; i++) {
            Item item = srcContainer.get(i);
            destItems[i] = item == null ? null : item.copy();
        }
    }

    /**
     * Teleports
     */

    public static final int TELEPORT_HOME = 19625;

    /**
     * Potions
     */

    public static final int RANGING_POTION_4 = 2444;
    public static final int RANGING_POTION_3 = 169;
    public static final int RANGING_POTION_2 = 171;
    public static final int RANGING_POTION_1 = 173;
    public static final int SUPER_STRENGTH_POTION_4 = 2440;
    public static final int SUPER_STRENGTH_POTION_3 = 157;
    public static final int SUPER_STRENGTH_POTION_2 = 159;
    public static final int SUPER_STRENGTH_POTION_1 = 161;
    public static final int SUPER_ATTACK_POTION_4 = 2436;
    public static final int SUPER_ATTACK_POTION_3 = 145;
    public static final int SUPER_ATTACK_POTION_2 = 147;
    public static final int SUPER_ATTACK_POTION_1 = 149;
    public static final int SUPER_RESTORE_POTION_4 = 3024;
    public static final int SUPER_RESTORE_POTION_3 = 3026;
    public static final int SUPER_RESTORE_POTION_2 = 3028;
    public static final int SUPER_RESTORE_POTION_1 = 3030;
    public static final int PRAYER_POTION_4 = 2434;
    public static final int SARADOMIN_BREW_4 = 6685;
    public static final int SARADOMIN_BREW_3 = 6689;
    public static final int SARADOMIN_BREW_2 = 6687;
    public static final int SARADOMIN_BREW_1 = 6691;
    public static final int SANFEW_SERUM = 10925;
    public static final int GUTHIX_REST = 4417;
    public static final int SUPER_COMBAT_POTION = 12695;

    /**
     * Food
     */

    public static final int POTATO_WITH_CHEESE = 6705;
    public static final int COOKED_KARAMBWAN = 3144;
    public static final int SHARK = 385;
    public static final int PINEAPPLE_PIZZA = 2301;
    public static final int DARK_CRAB = 11936;

    /**
     * Runes
     */

    public static final int WATER_RUNE = 555;
    public static final int DEATH_RUNE = 560;
    public static final int BLOOD_RUNE = 565;
    public static final int ASTRAL_RUNE = 9075;
    public static final int EARTH_RUNE = 557;

    /**
     * Weapons
     */

    public static final int DRAGON_2H_SWORD = 7158;
    public static final int DRAGON_CLAWS = 13652;
    public static final int GRANITE_MAUL = 4153;
    public static final int BARRELCHEST_ANCHOR = 10887;
    public static final int MAGIC_SHORTBOW = 861;
    public static final int RUNE_CROSSBOW = 9185;
    public static final int DRAGON_SCIMITAR = 4587;
    public static final int ANCIENT_STAFF = 4675;
    public static final int DRAGON_DAGGERP = 5698;
    public static final int DHAROK_GREATAXE = 4718;
    public static final int ABYSSAL_TENTACLE = 12006;
    public static final int ARMADYL_CROSSBOW = 11785;
    public static final int TOXIC_STAFF_OF_THE_DEAD = 12904;
    public static final int ABYSSAL_WHIP = 4151;
    public static final int BANDOS_GODSWORD = 11804;
    public static final int MAGIC_SHORTBOW_IMBUE = 12788;
    public static final int ARMADYL_GODSWORD = 11802;

    /**
     * AMMO
     */

    public static final int RUNE_ARROW = 892;
    public static final int DRAGON_BOLT = 9244;
    public static final int RUNITE_BOLT = 9144;
    public static final int DIAMOND_BOLTS_E = 9243;

    /**
     * Rings
     */

    public static final int RING_OF_RECOIL = 2550;
    public static final int BERSERKER_RING_IMBUE = 11773;
    public static final int SEERS_RING_IMBUE = 11770;

    /**
     * Amulet
     */

    public static final int STRENGTH_AMULET = 1725;
    public static final int AMULET_OF_GLORY_6 = 11978;
    public static final int AMULET_OF_GLORY_5 = 11976;
    public static final int AMULET_OF_GLORY_4 = 1712;
    public static final int AMULET_OF_GLORY_3 = 1710;
    public static final int AMULET_OF_GLORY_2 = 1708;
    public static final int AMULET_OF_GLORY_1 = 1706;
    public static final int AMULET_OF_GLORY_UNCHARGED = 1704;
    public static final int AMULET_OF_FURY = 6585;

    /**
     * Boots
     */

    public static final int CLIMBING_BOOTS = 3105;
    public static final int DRAGON_BOOTS = 11840;
    public static final int WIZARD_BOOTS = 2579;
    public static final int PRIMORDIAL_BOOTS = 13239;
    public static final int ETERNAL_BOOTS = 13235;

    /**
     * Gloves
     */

    public static final int MITHRIL_GLOVES = 7458;
    public static final int RUNE_GLOVES = 7460;
    public static final int BARROWS_GLOVES = 7462;

    /**
     * Legs
     */

    public static final int BLACK_DRAGONHIDE_CHAPS = 2497;
    public static final int GHOSTLY_ROBE_BOTTOM = 6108;
    public static final int MONK_ROBE_BOTTOM = 542;
    public static final int IRON_PLATELEGS = 1067;
    public static final int RUNE_PLATELEGS = 1079;
    public static final int MYSTIC_ROBE_BOTTOM = 4103;
    public static final int ZAMORAK_ROBE_SKIRT = 1033;
    public static final int DHAROK_LEGS = 4722;
    public static final int ELDER_CHAOS_ROBE = 20520;
    public static final int VERAC_PLATESKIRT = 4759;
    public static final int AHRIM_ROBESKIRT = 4714;

    /**
     * Cape
     */

    public static final int AVAS_ACCUMULATOR = 10499;
    public static final int SARADOMIN_CAPE = 2412;
    public static final int ZAMORAK_CAPE = 2414;
    public static final int LEGENDS_CAPE = 1052;
    public static final int FIRE_CAPE = 6570;
    public static final int INFERNAL_CAPE = 21295;
    public static final int IMBUED_ZAMORAK_CAPE = 21795;
    public static final int IMBUED_SARADOMIN_CAPE = 21791;
    public static final int AVAS_ASSEMBLER = 22109;

    /**
     * Body
     */

    public static final int LEATHER_BODY = 1129;
    public static final int GHOSTLY_ROBE_TOP = 6107;
    public static final int MONK_ROBE_TOP = 544;
    public static final int IRON_PLATEBODY = 1115;
    public static final int RUNE_PLATEBODY = 1127;
    public static final int BLACK_DRAGONHIDE_BODY = 2503;
    public static final int MYSTIC_ROBE_TOP = 4101;
    public static final int ZAMORAK_ROBE_TOP = 1035;
    public static final int DHAROK_BODY = 4720;
    public static final int ELDER_CHAOS_TOP = 20517;
    public static final int DECORATIVE_TOP = 11899;
    public static final int TORAG_PLATEBODY = 4749;
    public static final int KARIL_LEATHERTOP = 4736;
    public static final int AHRIM_ROBETOP = 4712;

    /**
     * Hat
     */

    public static final int BLUE_WIZARD_HAT = 579;
    public static final int BLACK_WIZARD_HAT = 1017;
    public static final int HELM_OF_NEITIZNOT = 10828;
    public static final int IRON_FULL_HELM = 1153;
    public static final int COIF = 1169;
    public static final int DHAROK_HELMET = 4716;
    public static final int ELDER_CHAOS_HOOD = 20595;
    public static final int ZAMORAK_HALO = 12638;
    public static final int SERPENTINE_HELM = 12931;
    public static final int GHOSTLY_HOOD = 6109;
    public static final int BERSERKER_HELM = 3751;

    /**
     * Shield
     */

    public static final int UNHOLY_BOOK = 3842;
    public static final int RUNE_KITESHIELD = 1201;
    public static final int RUNE_DEFENDER = 8850;
    public static final int DRAGON_DEFENDER = 12954;
    public static final int MAGES_BOOK = 6889;
    public static final int BLESSED_SPIRIT_SHIELD = 12831;
    public static final int SPIRIT_SHIELD = 12829;
    public static final int ARCANE_SPIRIT_SHIELD = 12825;

    static {
        // Make sure we set all the free preset items to ItemDef.free = true so our visibility toggle works
        int[] presetItems = new int[]{
                TELEPORT_HOME, RANGING_POTION_4, SUPER_STRENGTH_POTION_4, SUPER_ATTACK_POTION_4, SUPER_RESTORE_POTION_4,
                PRAYER_POTION_4, SARADOMIN_BREW_4, SANFEW_SERUM, GUTHIX_REST, SUPER_COMBAT_POTION, POTATO_WITH_CHEESE,
                COOKED_KARAMBWAN, SHARK, PINEAPPLE_PIZZA, DARK_CRAB, WATER_RUNE, DEATH_RUNE, BLOOD_RUNE, ASTRAL_RUNE,
                EARTH_RUNE, MAGIC_SHORTBOW, RUNE_CROSSBOW, DRAGON_SCIMITAR, ANCIENT_STAFF, DRAGON_DAGGERP, RUNE_ARROW,
                RUNITE_BOLT, RING_OF_RECOIL, STRENGTH_AMULET, AMULET_OF_GLORY_6, CLIMBING_BOOTS, WIZARD_BOOTS,
                BLACK_DRAGONHIDE_CHAPS, GHOSTLY_ROBE_BOTTOM, MONK_ROBE_BOTTOM, IRON_PLATELEGS, RUNE_PLATELEGS,
                MYSTIC_ROBE_BOTTOM, LEATHER_BODY, GHOSTLY_ROBE_TOP, MONK_ROBE_TOP, IRON_PLATEBODY, RUNE_PLATEBODY,
                BLACK_DRAGONHIDE_BODY, MYSTIC_ROBE_TOP, ZAMORAK_ROBE_TOP, BLUE_WIZARD_HAT, BLACK_WIZARD_HAT,
                HELM_OF_NEITIZNOT, IRON_FULL_HELM, COIF, UNHOLY_BOOK, RUNE_KITESHIELD, AMULET_OF_GLORY_UNCHARGED,
                AMULET_OF_GLORY_1, AMULET_OF_GLORY_2, AMULET_OF_GLORY_3, AMULET_OF_GLORY_4, AMULET_OF_GLORY_5,
                ZAMORAK_ROBE_SKIRT, DRAGON_BOLT, RANGING_POTION_1, RANGING_POTION_2, RANGING_POTION_3, SUPER_ATTACK_POTION_1,
                SUPER_ATTACK_POTION_2, SUPER_ATTACK_POTION_3, SUPER_STRENGTH_POTION_1, SUPER_STRENGTH_POTION_2,
                SUPER_STRENGTH_POTION_3, RANGING_POTION_1, RANGING_POTION_2, RANGING_POTION_3, SUPER_RESTORE_POTION_1,
                SUPER_RESTORE_POTION_2, SUPER_RESTORE_POTION_3, SARADOMIN_BREW_1, SARADOMIN_BREW_2, SARADOMIN_BREW_3, 561,
                RUNE_FULL_HELM, MYSTIC_HAT_DARK, DRAGON_DAGGER, BERSERKER_HELM, MYSTIC_BOOTS_DARK, RUNE_PLATESKIRT, DRAGON_2H_SWORD
                };
//        for(int presetItem : presetItems) {
//            ItemDef.get(presetItem).free = true;
//            ItemDef.get(presetItem).neverProtect = true;
//            int notedId = ItemDef.get(presetItem).notedId;
//            if(ItemDef.get(notedId) != null) {
//                ItemDef.get(notedId).free = true;
//                ItemDef.get(notedId).neverProtect = true;
//            }
//        }
    }

}
