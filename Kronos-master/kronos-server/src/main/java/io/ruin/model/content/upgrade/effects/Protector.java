package io.ruin.model.content.upgrade.effects;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.upgrade.ItemUpgrade;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 8/10/2020
 */
public class Protector extends ItemUpgrade {

    @Override
    public void preDefend(Player player, Entity target, Item item, Hit hit) {
        if (Random.rollDie(20)  && hit.damage != 0) {
            hit.damage *= 0.8;
        }
    }
}
