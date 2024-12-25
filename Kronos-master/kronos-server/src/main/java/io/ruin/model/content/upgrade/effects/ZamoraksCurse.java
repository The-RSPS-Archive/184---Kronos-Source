package io.ruin.model.content.upgrade.effects;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
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
public class ZamoraksCurse extends ItemUpgrade {
    @Override
    public void preTargetDefend(Player player, Entity target, Item item, Hit hit) {
        if (target.getHp() - hit.damage <= 0) {
            if (Random.rollDie(10)) {
                World.startEvent(e -> {
                    e.delay(1);
                    target.graphics(437, 0, 0);
                    player.hit(new Hit().fixedDamage(10));
                    player.sendMessage("Zamorak's Curse inflicts damage!");
                });
            }
        }
    }
}
