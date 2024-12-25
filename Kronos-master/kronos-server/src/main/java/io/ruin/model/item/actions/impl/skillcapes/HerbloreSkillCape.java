package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.stat.StatType;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/21/2020
 */
public class HerbloreSkillCape {
    private static final int CAPE = StatType.Herblore.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Herblore.trimmedCapeId;
    private static final int PESTLE = 233;

    static {
        ItemAction.registerInventory(CAPE, "Search", HerbloreSkillCape::herbloreSearch);
        ItemAction.registerEquipment(CAPE, "Search", HerbloreSkillCape::herbloreSearch);

        ItemAction.registerInventory(TRIMMED_CAPE, "Search", HerbloreSkillCape::herbloreSearch);
        ItemAction.registerEquipment(TRIMMED_CAPE, "Search", HerbloreSkillCape::herbloreSearch);
    }

    private static void herbloreSearch(Player player, Item item) {
        if (player.getInventory().hasFreeSlots(1) && !player.getInventory().contains(PESTLE)) {
            player.getInventory().add(PESTLE, 1);
            player.sendMessage("You search your cape for a Pestle and mortar.");
        } else {
            player.sendMessage("You already have a Pestle and mortar.");
        }
    }
}
