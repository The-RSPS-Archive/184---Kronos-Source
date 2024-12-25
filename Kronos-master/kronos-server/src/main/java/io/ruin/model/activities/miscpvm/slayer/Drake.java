package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;

public class Drake extends NPCCombat {

    private static final Projectile RANGED_PROJECTILE = new Projectile(1636, 25, 31, 40, 30, 8, 16, 96);
    private static final Projectile SPECIAL_PROJECTILE = new Projectile(1637, 25, 0, 40, 85, 0, 16, 96);
    private int count = 0;

    @Override
    public void init() {
        npc.deathEndListener = (entity, killer, killHit) -> npc.transform(8612);
    }

    @Override
    public void follow() {
        follow(6);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(6))
            return false;
        if (!withinDistance(6))
            return false;
        if (withinDistance(1) && Random.rollDie(2, 1))
            basicAttack();
        else if (count > 6) {
            specialAttack();
            count = 0;
        } else
            rangedAttack();
        count++;
        return true;
    }

    private void rangedAttack() {
        projectileAttack(RANGED_PROJECTILE, 8276, AttackStyle.RANGED, info.max_damage);
    }

    private void specialAttack() {
        npc.animate(8276);
        final Position targetPos = target.getPosition().copy();
        int delay = SPECIAL_PROJECTILE.send(npc, targetPos);
        World.sendGraphics(1638, 0, delay, targetPos);
        npc.addEvent(event -> {
            event.delay(2);
            if (target != null && target.getPosition().equals(targetPos)) {
                target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(24, 32));
            }
        });
    }

    @Override
    public void startDeath(Hit killHit) {
        setDead(true);
        if(target != null)
            reset();
        npc.addEvent(event -> {
            npc.animate(8277);
            event.delay(1);
            npc.transform(8613);
            npc.animate(8278);
            super.startDeath(killHit);
        });
    }
}
