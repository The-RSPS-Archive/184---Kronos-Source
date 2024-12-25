package io.ruin.model.combat.special.melee;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

//Binding Tentacle: Bind your target for 5 seconds
//and increase the chance of them being poisoned. (50%)
public class AbyssalTentacle implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("abyssal tentacle");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.publicSound(2713); //TODO: proper sound
        player.animate(1658);
        target.graphics(341, 96, 0);
        if(!target.isFrozen())
            target.freeze(5, target);
        if(Random.rollDie(8, 1)) //regular chance is 4, 1 so this just "increases" chance of being poisoned.
            target.poison(4);
        target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage));
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}