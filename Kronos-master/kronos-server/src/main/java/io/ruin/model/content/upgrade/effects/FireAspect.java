package io.ruin.model.content.upgrade.effects;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
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
public class FireAspect extends ItemUpgrade {

    @Override
    public void preTargetDefend(Player player, Entity target, Item item, Hit hit) {
        if (target != null && target.npc != null && target.get("FIRE_ASPECT") == null && Random.rollDie(10)) {
            World.startEvent(e -> {
                target.graphics(453);
                target.set("FIRE_ASPECT", true);
                for (int cycles = 0; cycles < 3; cycles++) {
                    target.hit(new Hit().fixedDamage(2));
                    e.delay(2);
                }
                target.remove("FIRE_ASPECT");
            });
        }
    }

}
