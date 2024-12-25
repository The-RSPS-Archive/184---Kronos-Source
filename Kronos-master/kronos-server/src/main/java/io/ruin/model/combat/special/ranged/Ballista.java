package io.ruin.model.combat.special.ranged;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

//Concentrated Shot: Deal an attack with 25% increased accuracy
//and damage, but takes an additional 2.4 seconds to fire. (65%)
public class Ballista implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 19478 || def.id == 19481;
    }

    @Override
    public boolean handle(Player player, Entity victim, AttackStyle style, AttackType type, int maxDamage) {
        player.animate(7222);
        int delay = player.getCombat().rangedData.projectiles[1].send(player, victim);
        victim.hit(new Hit(player, style, type)
                .randDamage(maxDamage)
                .boostDamage(0.25)
                .boostAttack(0.25)
                .clientDelay(delay)
                .postDamage(t -> t.graphics(344, 120, 0))
        );
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 65;
    }

}