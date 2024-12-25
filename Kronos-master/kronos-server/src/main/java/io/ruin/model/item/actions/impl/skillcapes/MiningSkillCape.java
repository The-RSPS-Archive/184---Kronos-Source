package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/31/2020
 */
public class MiningSkillCape {

    private static final int CAPE = StatType.Mining.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Mining.trimmedCapeId;
    private static final int MASTER_CAPE = StatType.Mining.masterCapeId;

    public static boolean wearsMiningCape(Player player) {
        int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
        return cape == CAPE || cape == TRIMMED_CAPE || cape == 13280 || cape == MASTER_CAPE;
    }
}