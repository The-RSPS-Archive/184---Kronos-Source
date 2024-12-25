package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.Title;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class MakeoverMage {

    private static void open(Player player, NPC npc) {
        if(!player.getEquipment().isEmpty()) {
            player.dialogue(new NPCDialogue(npc, "Please remove what your equipment before we proceed with the makeover."));
            return;
        }
        player.openInterface(InterfaceType.MAIN, Interface.MAKE_OVER_MAGE);
    }

    private static List<Option> getOptions(Player player, NPC npc) {
        List<Option> options = new ArrayList<>();
        options.add(new Option(unlockedFormat(player, Skins.BLACK) + "Midnight Black - $50", () -> equipSkin(player, Skins.BLACK)));
        options.add(new Option(unlockedFormat(player, Skins.WHITE) + "Moonlight White - $50", () -> equipSkin(player, Skins.WHITE)));
        options.add(new Option(unlockedFormat(player, Skins.SWAMP_GREEN) + "Swamp Green - 20$", () -> equipSkin(player, Skins.SWAMP_GREEN)));
        options.add(new Option(unlockedFormat(player, Skins.ZOMBIE_BLUE) + "Zombie Blue - 20$", () -> equipSkin(player, Skins.ZOMBIE_BLUE)));
        options.add(new Option(unlockedFormat(player, Skins.PURPLE) + "Putrid Purple - 20$", () -> equipSkin(player, Skins.PURPLE)));
        return options;
    }

    public static void openSkinUnlocks(Player player, NPC npc) {
        OptionScroll.open(player, "Select a skin color you'd like to use", false, getOptions(player, npc));
    }

    public static void equipSkin(Player player, Skins skin) {
        if (unlocked(player, skin)) {
            player.getAppearance().colors[4] = skin.getColor();
            player.getAppearance().update();
            player.sendMessage("You are now using the " + skin.getName() + " skin!");
        } else {
            player.sendMessage("You have not yet unlocked the " + skin.getName() + " skin!");
        }
    }

    private static String unlockedFormat(Player player, Skins skin) {
        if(!unlocked(player, skin))
            return "<str>";
        return "";
    }

    @RequiredArgsConstructor
    @Getter
    public enum Skins {
        PURPLE("Putrid Purple", 12),
        SWAMP_GREEN("Swamp Green", 8),
        ZOMBIE_BLUE("Zombie Blue", 11),
        BLACK("Midnight Black", 9),
        WHITE("Moonlight White", 10);

        private final String name;
        private final int color;
    }
    private static boolean unlocked(Player player, Skins skin) {
        if (skin == Skins.PURPLE && player.unlockedPurpleSkin)
            return true;
        if (skin == Skins.SWAMP_GREEN && player.unlockedGreenSkin)
            return true;
        if (skin == Skins.ZOMBIE_BLUE && player.unlockedBlueSkin)
            return true;
        if (skin == Skins.BLACK && player.unlockedBlackSkin)
            return true;
        if (skin == Skins.WHITE && player.unlockedWhiteSkin)
            return true;
        return false;

    }

    static {
        NPCAction.register(1307, "change-looks", MakeoverMage::open);
        NPCAction.register(1307, "skin-unlocks", MakeoverMage::openSkinUnlocks);
        NPCAction.register(1307, "title-unlocks", MakeoverMage::titles);
    }

    private static void titles(Player player, NPC npc) {
        player.dialogue(new OptionsDialogue(
                new Option("View Unlocked Titles", () -> Title.openSelection(player, false)),
                new Option("View All Titles", () -> Title.openSelection(player, true)),
                new Option("Remove my Title", () -> Title.clearTitle(player)),
                new Option("Cancel", () -> {})));
    }
}
