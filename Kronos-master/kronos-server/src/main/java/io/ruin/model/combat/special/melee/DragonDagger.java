package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

//Puncture: Deal two quick slashes with
//25% increased accuracy and 15% increased damage. (25%)
public class DragonDagger implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dragon dagger");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(1062);
        player.graphics(252, 96, 0);
        player.publicSound(2537);
        target.hit(
                new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.15).boostAttack(0.25),
                new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.15).boostAttack(0.25)
        );
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 25;
    }

}