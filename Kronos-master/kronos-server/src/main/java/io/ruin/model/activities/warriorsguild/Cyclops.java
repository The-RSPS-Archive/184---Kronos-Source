package io.ruin.model.activities.warriorsguild;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.activities.miscpvm.BasicCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.ground.GroundItem;

public class Cyclops extends BasicCombat {

    @Override
    public void init() {
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            if(killer == null)
                return;
            int defenderId = killer.player.nextDefenderId;
            if(defenderId == -1)
                return;
            if(!Random.rollDie(defenderId == DRAGON_DEFENDER ? 20 : 10))
                return;
            new GroundItem(defenderId, 1)
                    .owner(killer.player)
                    .position(npc.getPosition())
                    .spawn();
            killer.player.sendMessage("<col=804080>The cyclops has dropped a " + ItemDef.get(defenderId).name + ".");
        };
    }

    /**
     * Defender stuff
     */

    public static final int
            BRONZE_DEFENDER = 8844,
            RUNE_DEFENDER = 8850,
            DRAGON_DEFENDER = 12954;

    public static int getNext(Player player, boolean allowDragon) {
        int bestDefenderId = -1;
        int shieldId = player.getEquipment().getId(Equipment.SLOT_SHIELD);
        if(isDefender(shieldId))
            bestDefenderId = shieldId;
        for(Item item : player.getInventory().getItems()) {
            if(item == null || !isDefender(item.getId()))
                continue;
            if(item.getId() < bestDefenderId)
                continue;
            bestDefenderId = item.getId();
        }
        if(bestDefenderId == -1)
            return BRONZE_DEFENDER;
        if(bestDefenderId >= RUNE_DEFENDER)
            return allowDragon ? DRAGON_DEFENDER : RUNE_DEFENDER;
        return bestDefenderId + 1;
    }

    private static boolean isDefender(int id) {
        return (id >= BRONZE_DEFENDER && id <= RUNE_DEFENDER) || id == DRAGON_DEFENDER;
    }

}