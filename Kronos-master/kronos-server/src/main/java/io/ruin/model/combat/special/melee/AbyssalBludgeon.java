package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

//Penance: Deal an attack that inflicts 0.5%
//more damage per prayer point that you have used. (50%)
public class AbyssalBludgeon implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("abyssal bludgeon");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(7010);
        target.graphics(1284);
        player.publicSound(2715, 1,10);
        player.publicSound(1930, 1,30);
        Hit hit = new Hit(player, attackStyle, attackType)
                .randDamage(maxDamage)
                .boostAttack(0.25);
        Stat prayer = player.getStats().get(StatType.Prayer);
        int prayerUsed = prayer.fixedLevel - prayer.currentLevel;
        if(prayerUsed > 0)
            hit.boostDamage(prayerUsed * 0.005);
        target.hit(hit);
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}