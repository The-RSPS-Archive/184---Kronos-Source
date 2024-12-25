package io.ruin.model.combat.special.ranged;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;

public class DragonThrownaxe implements Special {

    private static final Projectile PROJECTILE = Projectile.thrown(1318, 11);

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 20849;
    }

    @Override
    public boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        int delay = PROJECTILE.send(player, victim);
        player.animate(7521);
        player.graphics(1317, 120, 0);
        Hit hit = new Hit(player, attackStyle, attackType).randDamage(maxDamage).clientDelay(delay).boostAttack(0.25);
        victim.hit(hit);
        player.getCombat().removeAmmo(player.getEquipment().get(Equipment.SLOT_WEAPON), hit);
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 25;
    }
}
