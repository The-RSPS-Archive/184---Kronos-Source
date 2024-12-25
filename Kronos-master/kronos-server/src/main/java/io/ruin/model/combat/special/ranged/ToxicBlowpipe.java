package io.ruin.model.combat.special.ranged;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Projectile;

//Toxic Siphon: Deal an attack that inflicts 50% more
//damage and heals you for 50% of the damage dealt. (50%)
public class ToxicBlowpipe implements Special {

    public static final Projectile SIPHON_PROJECTILE = new Projectile(1043, 35, 36, 32, 39, 3, 15, 105);

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 12926;
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle style, AttackType type, int maxDamage) {
        //Handled in player combat.
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}