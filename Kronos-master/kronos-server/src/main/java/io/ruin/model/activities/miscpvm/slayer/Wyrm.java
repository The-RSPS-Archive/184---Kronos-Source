package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;

public class Wyrm extends NPCCombat {

    private static final int IDLE = 8610;
    private static final int ACTIVE = 8611;

    private static final Projectile PROJECTILE = new Projectile(1634, 36, 31, 30, 50, 8, 16, 96);

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDamage(this::postDamage);
    }

    private void postDamage(Hit hit) {
        if (npc.getId() != ACTIVE) {
            npc.transform(ACTIVE);
            npc.animate(8268);
        }
    }

    @Override
    public void reset() {
        super.reset();
        if (!isDead() && npc.getId() == ACTIVE) {
            npc.animate(8269);
            npc.addEvent(event -> {
                event.delay(1);
                npc.transform(IDLE);
            });
        }
    }

    @Override
    public void follow() {
        follow(6);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(6))
            return false;
        if (withinDistance(1) && Random.rollDie(2, 1))
            basicAttack();
        else
            magicAttack();
        return true;
    }

    private void magicAttack() {
        npc.animate(8271);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay);
        target.hit(hit);
        if (hit.damage > 0)
            target.graphics(1635, 0, delay);
    }
}
