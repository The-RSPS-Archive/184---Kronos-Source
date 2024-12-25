package io.ruin.model.combat.special.ranged;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Projectile;

//Armadyl Eye: Deal an attack with double accuracy. (40%)
public class ArmadylCrossbow implements Special {

    private static final Projectile PROJECTILE = new Projectile(301, 38, 36, 41, 51, 5, 5, 11);

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("armadyl crossbow");
    }

    @Override
    public boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(4230);
        int delay = PROJECTILE.send(player, victim);
        victim.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostAttack(1.0).clientDelay(delay));
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 40;
    }

}