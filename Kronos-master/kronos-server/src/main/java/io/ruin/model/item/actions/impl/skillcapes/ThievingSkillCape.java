package io.ruin.model.item.actions.impl.skillcapes;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/31/2020
 */
public class ThievingSkillCape {

    private static final int CAPE = StatType.Thieving.regularCapeId;
    private static final int TRIMMED_CAPE = StatType.Thieving.trimmedCapeId;
    private static final int MASTER_CAPE = StatType.Thieving.masterCapeId;

    public static boolean wearsThievingCape(Player player) {
        int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
        return cape == CAPE || cape == TRIMMED_CAPE || cape == 13280 || cape == MASTER_CAPE;
    }
}
