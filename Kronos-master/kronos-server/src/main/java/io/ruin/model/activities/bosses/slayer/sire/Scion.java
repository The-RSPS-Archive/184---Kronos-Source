package io.ruin.model.activities.bosses.slayer.sire;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;
import io.ruin.utility.TickDelay;

public class Scion extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(628, 28, 43, 20, 35, 5, 16, 0); // TODO find correct gfx id ;[

    private TickDelay transformDelay = new TickDelay();
    private boolean melee = false;

    @Override
    public void init() {
        transformDelay.delay(Random.get(14, 18));
        npc.hitListener = new HitListener().preTargetDefend((hit, entity) -> {
            if (Random.rollDie(2, 1))
                    hit.ignorePrayer();
        });
    }

    @Override
    public void follow() {
        follow(melee ? 1 : 3);
    }

    @Override
    public boolean attack() {
        if (npc.getId() != 5918 && !transformDelay.isDelayed()) { // transform
            npc.addEvent(event -> {
                npc.lock();
                npc.transform(5918);
                npc.animate(7123);
                delayAttack(2);
                if (target != null) { // step away
                    int x = -npc.getPosition().unitVectorX(target.getPosition());
                    int y = -npc.getPosition().unitVectorY(target.getPosition());
                    if (x == 0)
                        x += Random.rollDie(2, 1) ? -1 : 1;
                    if (y == 0)
                        y += Random.rollDie(2, 1) ? -1 : 1;
                    melee = false;
                    npc.step(x, y, StepType.NORMAL);
                    event.delay(2);
                }
                transformDelay.delay(30);
                npc.unlock();
            });
            return true;
        } else if (npc.getId() == 5918 && !transformDelay.isDelayed()) { // expire
            startDeath(null);
            return false;
        }
        if (melee) {
            if (!withinDistance(1))
                return false;
            npc.animate(info.attack_animation);
            target.hit(new Hit(npc, AttackStyle.STAB).randDamage(info.max_damage));
        } else {
            if (!withinDistance(4))
                return false;
            npc.animate(npc.getId() == 5918 ? 7127 : info.attack_animation);
            int delay = PROJECTILE.send(npc, target);
            target.hit(new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).ignorePrayer().clientDelay(delay));
        }
        if (Random.get() < 0.6)
            melee = !melee;
        return true;
    }

}
