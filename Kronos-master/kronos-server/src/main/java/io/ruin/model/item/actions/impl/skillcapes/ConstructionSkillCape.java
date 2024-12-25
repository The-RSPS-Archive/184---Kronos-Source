package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.TeleportTab;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/21/2020
 */
public class ConstructionSkillCape {
    private static final int CAPE = StatType.Construction.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Construction.trimmedCapeId;

    static {
        ItemAction.registerInventory(CAPE, "Teleport", ConstructionSkillCape::selectTeleport);
        ItemAction.registerEquipment(CAPE, "Teleport", ConstructionSkillCape::selectTeleport);
        ItemAction.registerInventory(CAPE, "Tele to POH", ConstructionSkillCape::telePOH);
        ItemAction.registerEquipment(CAPE, "Tele to POH", ConstructionSkillCape::telePOH);

        ItemAction.registerInventory(TRIMMED_CAPE, "Teleport", ConstructionSkillCape::selectTeleport);
        ItemAction.registerEquipment(TRIMMED_CAPE, "Teleport", ConstructionSkillCape::selectTeleport);
        ItemAction.registerInventory(TRIMMED_CAPE, "Tele to POH", ConstructionSkillCape::telePOH);
        ItemAction.registerEquipment(TRIMMED_CAPE, "Tele to POH", ConstructionSkillCape::telePOH);
    }

    private static void selectTeleport(Player player, Item item) {
        player.dialogue(new OptionsDialogue("Choose Location:",
                new Option("Rimmington", () -> ModernTeleport.teleport(player, new Bounds(2953,3223,2955,3225,0))),
                new Option("Taverley", () -> ModernTeleport.teleport(player, new Bounds(2893,3464,2895,3466,0))),
                new Option("Pollnivneach", () -> ModernTeleport.teleport(player, new Bounds(3338,3003,3342,3005,0))),
                new Option("Next", () -> selectTeleport2(player, item))
        ));
    }

    private static void selectTeleport2(Player player, Item item) {
        player.dialogue(new OptionsDialogue("Choose Location:",
                new Option("Hosidius", () -> ModernTeleport.teleport(player, new Bounds(1742,3516,1744,3518,0))),
                new Option("Brimhaven", () -> ModernTeleport.teleport(player, new Bounds(2757,3177,2759,3179,0))),
                new Option("Yanille", () -> ModernTeleport.teleport(player, new Bounds(2543,3094,2545,3096,0))),
                new Option("Previous", () -> selectTeleport(player, item))
        ));
    }

    private static void telePOH(Player player, Item item) {
        TeleportTab.houseTab(player, item);
    }
}
