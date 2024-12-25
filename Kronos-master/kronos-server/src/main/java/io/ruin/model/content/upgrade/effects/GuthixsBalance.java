package io.ruin.model.content.upgrade.effects;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
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
public class GuthixsBalance extends ItemUpgrade {
    @Override
    public void preTargetDefend(Player player, Entity target, Item item, Hit hit) {
        if (Random.rollDie(10)) {
            target.graphics(436, 48, 0);
            player.graphics(436, 48, 0);
            player.privateSound(958);
            player.incrementHp(10);
            target.incrementHp(10);
            player.sendMessage("Guthix's Balance has healed you and your opponent!");
        }
    }
}