package io.ruin.model.content.upgrade.effects;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.upgrade.ItemUpgrade;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.stat.StatType;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 8/10/2020
 */
public class SaradominsGrace extends ItemUpgrade {
    @Override
    public void preTargetDefend(Player player, Entity target, Item item, Hit hit) {
        if (target.getHp() - hit.damage <= 0) {
            if (Random.rollDie(10)) {
                player.getStats().get(StatType.Prayer).restore(5);
                player.sendMessage("Saradomin's Grace has restored 5 prayer points!");
            }
        }
    }
}
