package io.ruin.model.activities.raids.xeric.chamber.combat;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;

public class DeathlyMage extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(130, 43, 31, 51, 56, 10, 16, 64);

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
        npc.animate(info.attack_animation);
        npc.graphics(129, 92, 0);
        int delay = PROJECTILE.send(npc, target);
        int maxDamage = info.max_damage;
        if (target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
            maxDamage /= 2;
        }
        Hit hit = new Hit(npc, AttackStyle.MAGIC)
                .randDamage(maxDamage)
                .clientDelay(delay)
                .ignorePrayer();
        hit.postDamage(t -> {
            if(hit.damage > 0) {
                t.graphics(131, 124, delay);
            } else {
                t.graphics(85, 124, delay);
                hit.hide();
            }
        });
        target.hit(hit);
        return true;
    }
}
