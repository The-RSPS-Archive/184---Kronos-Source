package io.ruin.model.activities.barrows.brothers;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.StatType;

public class Ahrim extends NPCCombat {

    private Projectile FIRE_WAVE = new Projectile(156, 43, 31, 51, 56, 10, 16, 64);

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if(!withinDistance(8))
            return false;
        npc.graphics(155, 92, 0);
        npc.publicSound(162, 1, 0);
        Hit hit = projectileAttack(FIRE_WAVE, 1167, AttackStyle.MAGIC, info.max_damage);
        if(Random.rollDie(4)) {
            target.player.getStats().get(StatType.Strength).drain(5);
            target.graphics(400);
        }
        return true;
    }

}