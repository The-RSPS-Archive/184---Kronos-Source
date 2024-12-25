package io.ruin.model.combat.special.ranged;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Projectile;

public class MorrigansThrowingAxe implements Special {

    private static final Projectile PROJECTILE = Projectile.thrown(1625, 11);

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 22634;
    }

    @Override
    public boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        if (victim.player == null) {
            player.sendMessage("You can only use this special attack against other players.");
            return false;
        }
        player.animate(806);
        player.graphics(1626, 96, 0);
        int delay = PROJECTILE.send(player, victim);
        int damage = victim.hit(new Hit(player, attackStyle, attackType).randDamage((int)(maxDamage * 0.20), (int) (maxDamage * 1.20)).clientDelay(delay));
        if (damage > 0) {
            victim.player.morrigansAxeSpecial.delay(100);
            victim.player.sendMessage("Your run energy consumption has been increased 6x for 1 minute!");
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }
}
