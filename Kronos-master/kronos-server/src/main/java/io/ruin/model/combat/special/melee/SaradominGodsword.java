package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

//Healing Blade: Deal an attack that inflicts 10% more damage with double accuracy, while
//restoring your Hitpoints and Prayer by 50% and 25% of the damage dealt respectively. (50%)
public class SaradominGodsword implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("saradomin godsword");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(player.getEquipment().getId(Equipment.SLOT_WEAPON) == 11806 ? 7640 : 7641);
        player.graphics(1209);
        player.publicSound(3869);
        int damage = target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostDamage(0.10).boostAttack(1.0));
        if(damage > 0) {
            player.incrementHp((int) Math.ceil(damage * 0.50));
            player.getStats().get(StatType.Prayer).restore((int) Math.ceil(damage * 0.25), 0);
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}