package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

//Abyssal Puncture: Deal an attack that hits twice with
//25% increased accuracy, but inflicts 15% less damage per hit.
//The second hit is guaranteed if the first deals damage. (50%)
public class AbyssalDagger implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("abyssal dagger");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(3300);
        player.graphics(1283);
        player.publicSound(2537);
        maxDamage *= 0.85;

        Hit baseHit = new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostAttack(0.25);
        Hit secondaryHit = new Hit(player, attackStyle, attackType).randDamage(maxDamage);
        baseHit.postDefend(t -> {
            if(baseHit.damage == 0)
                secondaryHit.block();
            else
                secondaryHit.ignoreDefence(); //second hit is garunateed
        });
        target.hit(baseHit, secondaryHit);
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}