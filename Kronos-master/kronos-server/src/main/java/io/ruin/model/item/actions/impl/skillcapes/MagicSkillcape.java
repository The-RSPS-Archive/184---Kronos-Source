package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.object.actions.impl.PrayerAltar;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.stat.StatType;

import java.util.concurrent.TimeUnit;

public class MagicSkillcape {

    private static final int CAPE = StatType.Magic.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Magic.trimmedCapeId;

    static {
        ItemAction.registerInventory(CAPE, "spellbook", MagicSkillcape::swapSelection);
        ItemAction.registerEquipment(CAPE, "spellbook", MagicSkillcape::swapSelection);

        ItemAction.registerInventory(TRIMMED_CAPE, "spellbook", MagicSkillcape::swapSelection);
        ItemAction.registerEquipment(TRIMMED_CAPE, "spellbook", MagicSkillcape::swapSelection);
    }

    private static void swapSelection(Player player, Item item) {
        if(player.mageSkillcapeSpecial < System.currentTimeMillis()) {
            player.mageSkillcapeSpecial = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(player.isSapphire() ? 6 : 12);
            player.magicSkillcapeUses = 0;
        }
        if(player.magicSkillcapeUses >= 5) {
            player.sendMessage("You've already used spellbook swap 5 times today. You can use this again in another " + getRemainingTime(player) + ".");
            return;
        }
        int spellBook = Config.MAGIC_BOOK.get(player);
        if (spellBook == SpellBook.MODERN.ordinal()) {
            player.dialogue(new OptionsDialogue("Choose spellbook:",
                    new Option("Ancient", () -> swap(player, SpellBook.ANCIENT, "Ancient")),
                    new Option("Lunar", () -> swap(player, SpellBook.LUNAR, "Lunar")),
                    new Option("<str>Arceuus", player::closeDialogue)
            ));
        } else if(spellBook == SpellBook.ANCIENT.ordinal()) {
            player.dialogue(new OptionsDialogue("Choose spellbook:",
                    new Option("Modern", () -> swap(player, SpellBook.MODERN, "Modern")),
                    new Option("Lunar", () -> swap(player, SpellBook.LUNAR, "Lunar")),
                    new Option("<str>Arceuus", player::closeDialogue)
            ));
        } else if(spellBook == SpellBook.LUNAR.ordinal()) {
            player.dialogue(new OptionsDialogue("Choose spellbook:",
                    new Option("Modern", () -> swap(player, SpellBook.MODERN, "Modern")),
                    new Option("Ancient", () -> swap(player, SpellBook.ANCIENT, "Ancient")),
                    new Option("<str>Arceuus", player::closeDialogue)
            ));
        }
    }

    private static void swap(Player player, SpellBook spellBook, String name) {
        PrayerAltar.switchBook(player, spellBook, false);
        player.sendMessage(name + " spellbook activated.");
        player.magicSkillcapeUses++;
    }

    private static String getRemainingTime(Player player) {
        long ms = player.mageSkillcapeSpecial - System.currentTimeMillis();
        long hours = TimeUnit.MILLISECONDS.toHours(ms);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        return (hours >= 1 ? (hours + " hour" + (hours > 1 ? "s" : "") + " and ") : "") +
                Math.max((minutes - TimeUnit.HOURS.toMinutes(hours)), 1) + " minute" +
                ((minutes - TimeUnit.HOURS.toMinutes(hours)) > 1 ? "s" : "");
    }
}
