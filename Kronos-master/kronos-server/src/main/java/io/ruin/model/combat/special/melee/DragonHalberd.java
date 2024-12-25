package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.utility.Misc;

//Sweep: If your target is small, adjacent targets may be hit too.
//Otherwise, your target may be hit a second time, with 25% decreased accuracy.
//Damage in all cases is increased by 10% of your max hit. (30%)
public class DragonHalberd implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dragon halberd") || name.contains("crystal halberd");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(1203, 0);
        player.graphics(1231, 100, 0);
        if(target.getSize() == 1) {
            if(target.inMulti()) {
                target.forLocalEntity(entity -> {
                    if(Misc.getDistance(entity.getPosition(), target.getPosition()) > 1)
                        return;
                    if(!player.getCombat().canAttack(entity, false))
                        return;
                    entity.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.10));
                });
            }
            target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.10));
        } else {
            target.hit(
                    new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.10),
                    new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.10).boostDefence(0.25)
            );
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 30;
    }

}