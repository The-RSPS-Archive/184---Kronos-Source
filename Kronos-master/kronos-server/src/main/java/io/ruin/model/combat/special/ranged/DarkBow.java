package io.ruin.model.combat.special.ranged;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.*;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;

//Descent of Darkness: Deal a double attack that inflicts
//up to 30% more damage (minimum damage of 5 per hit). (55%)
public class DarkBow implements Special {

    private static final Projectile[] SMOKE_PROJECTILES = {
            new Projectile(1101, 40, 36, 41, 52, 6, 5, 11),
            new Projectile(1101, 40, 36, 41, 65, 10, 25, 11),
    };

    private static final Projectile[] DRAGON_PROJECTILES = {
            new Projectile(1099, 40, 36, 41, 52, 6, 5, 11),
            new Projectile(1099, 40, 36, 41, 65, 10, 25, 11),
    };

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dark bow");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle style, AttackType type, int maxDamage) {
        Item ammo = player.getEquipment().get(Equipment.SLOT_AMMO);
        if(ammo == null || ammo.getAmount() < 2) {
            player.sendMessage("You need at least two arrows in your quiver to use this special attack.");
            return false;
        }

        RangedData data = player.getCombat().rangedData;
        int minDamage;
        double damageBoost;
        Projectile[] projectiles;
        int gfxId;
        if(data != RangedAmmo.DRAGON_ARROW.data) {
            minDamage = 5;
            damageBoost = 0.30;
            projectiles = SMOKE_PROJECTILES;
            gfxId = 1103;
        } else {
            minDamage = 8;
            damageBoost = 0.50;
            projectiles = DRAGON_PROJECTILES;
            gfxId = 1100;
        }

        player.animate(426);
        player.graphics(data.doubleDrawbackId, 96, 0);

        Hit[] hits = new Hit[projectiles.length];
        for(int i = 0; i < projectiles.length; i++) {
            int delay = projectiles[i].send(player, target);
            Hit hit = new Hit(player, style, type)
                    .randDamage(maxDamage)
                    .boostDamage(damageBoost)
                    .boostAttack(0.45)
                    .clientDelay(delay);
            hit.postDefend(t -> {
                hit.type = HitType.DAMAGE;
                hit.damage = Math.max(minDamage, hit.damage);
            }).postDamage(t -> t.graphics(gfxId, 96, 0));
            hits[i] = hit;
        }
        player.getCombat().removeAmmo(ammo, hits);
        target.hit(hits);
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 55;
    }

}