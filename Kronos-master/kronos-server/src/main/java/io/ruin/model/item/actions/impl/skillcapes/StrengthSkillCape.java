package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;
import io.ruin.model.stat.StatType;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/23/2020
 */

public class StrengthSkillCape {

    private static final int CAPE = StatType.Strength.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Strength.trimmedCapeId;


    static {
        ItemAction.registerInventory(CAPE, "Teleport", StrengthSkillCape::strengthTeleport);
        ItemAction.registerEquipment(CAPE, "Warriors' Guild", StrengthSkillCape::strengthTeleport);

        ItemAction.registerInventory(TRIMMED_CAPE, "Teleport", StrengthSkillCape::strengthTeleport);
        ItemAction.registerEquipment(TRIMMED_CAPE, "Warriors' Guild", StrengthSkillCape::strengthTeleport);
    }

    private static void strengthTeleport(Player player, Item item) {
        ModernTeleport.teleport(player, new Bounds(2850, 3547, 2852, 3549, 0));
    }
}
