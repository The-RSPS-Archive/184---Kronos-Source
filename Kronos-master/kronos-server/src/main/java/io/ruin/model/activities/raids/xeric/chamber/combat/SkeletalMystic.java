package io.ruin.model.activities.raids.xeric.chamber.combat;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.StatType;

public class SkeletalMystic extends NPCCombat {

    private static final int VULN_GFX = 1321;
    private static final int FIRE_GFX = 1322;
    private static final int VULN_HIT_GFX = 169;
    private static final int FIRE_HIT_GFX = 131;

    private static final Projectile VULN_PROJECTILE = new Projectile(168, 70, 31, 34, 56, 10, 16, 127);
    private static final Projectile FIRE_PROJECTILE = new Projectile(130, 70, 31, 34, 56, 10, 16, 127);

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        if (withinDistance(1) && Random.rollDie(2, 1)) {
            meleeAttack();
        } else {
            if (Random.rollDie(3, 1))
                vulnAttack();
            else
                fireAttack();
        }
        return true;
    }

    private void vulnAttack() {
        npc.animate(info.attack_animation);
        npc.graphics(VULN_GFX);
        int delay = VULN_PROJECTILE.send(npc, target);
        int maxDamage = 25;
        if (target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC))
            maxDamage /= 2;
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(maxDamage).clientDelay(delay).ignorePrayer().postDamage(entity -> {
            if (entity.player != null)
                entity.player.getStats().get(StatType.Defence).drain(0.1);
        }));
        target.graphics(VULN_HIT_GFX, 124, delay);
    }

    private void fireAttack() {
        npc.animate(info.attack_animation);
        npc.graphics(FIRE_GFX);
        int delay = FIRE_PROJECTILE.send(npc, target);
        int maxDamage = 35;
        if (target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC))
            maxDamage /= 2;
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(maxDamage).clientDelay(delay).ignorePrayer());
        target.graphics(FIRE_HIT_GFX, 124, delay);
    }

    private void meleeAttack() {
        npc.animate(5487);
        int maxDamage = 30;
        if (target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE))
            maxDamage /= 2;
        target.hit(new Hit(npc, AttackStyle.CRUSH).randDamage(maxDamage).ignorePrayer());
    }
}
