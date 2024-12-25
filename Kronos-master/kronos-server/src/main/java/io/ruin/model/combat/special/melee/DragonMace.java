package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

//Shatter: Increase damage by 50%
//and accuracy by 25% for one hit. (25%)
public class DragonMace implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dragon mace");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(1060);
        player.graphics(251, 96, 0);
        player.publicSound(2541);
        target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.50).boostAttack(0.25));
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 25;
    }

}