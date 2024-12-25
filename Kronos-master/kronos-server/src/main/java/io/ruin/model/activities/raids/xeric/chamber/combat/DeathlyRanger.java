package io.ruin.model.activities.raids.xeric.chamber.combat;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;

public class DeathlyRanger extends NPCCombat {

    private static final Projectile PROJECTILE = Projectile.arrow(1384)[0];

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(10);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        npc.animate(426);
        npc.graphics(1385, 96, 0);
        int delay = PROJECTILE.send(npc, target);
        int maxDamage = info.max_damage;
        if (target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES))
            maxDamage /= 2;
        target.hit(new Hit(npc, AttackStyle.RANGED).randDamage(maxDamage).ignorePrayer().clientDelay(delay));
        return true;
    }
}
