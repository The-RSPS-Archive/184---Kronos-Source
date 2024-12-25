package io.ruin.model.item.actions.impl.jewellery;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

public class RingOfLife {

    public static void check(Player player) {
        if(player.getDuel().stage >= 4)
            return;
        if (player.getHp() <= player.getMaxHp() * 0.10 && !player.getCombat().isDead()) {
            Item ring = player.getEquipment().get(Equipment.SLOT_RING);
            if (ring == null || ring.getId() != 2570)
                return;
            if(ModernTeleport.teleport(player, World.HOME)) {
                ring.remove();
                player.sendFilteredMessage("Your ring of life crumbles to dust.");
            }
        }
    }
}
