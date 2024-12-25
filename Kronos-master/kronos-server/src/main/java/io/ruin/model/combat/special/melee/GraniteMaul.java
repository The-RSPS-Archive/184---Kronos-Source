package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

//Quick Smash: Deal an extra attack instantly. (50%)
public class GraniteMaul implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("granite maul");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(1667);
        player.graphics(340, 96, 0);
        player.publicSound(2715);
        target.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage));
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

    static {
        for(int id : new int[]{4153, 12848, 20557})
            ItemDef.get(id).graniteMaul = true;
    }

}