package io.ruin.model.content.upgrade.effects;

import io.ruin.model.combat.Hit;
import io.ruin.model.content.upgrade.ItemUpgrade;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

/**
 * @author ReverendDread on 6/18/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class Spiteful extends ItemUpgrade {

    @Override
    public void preTargetDefend(Player player, Entity target, Item item, Hit hit) {
        double multiplier = Math.max(0, (player.getMaxHp() - player.getHp()) * 0.0025);
        hit.boostDamage(multiplier);
    }

}
