package io.ruin.model.activities.miscpvm.dragons;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;

public class RuneDragon extends MetalDragon  {

    protected static final Projectile MAGIC_PROJECTILE = new Projectile(162, 10, 31, 40, 56, 10, 16, 192);
    protected static final Projectile RANGED_PROJECTILE = new Projectile(1485, 10, 31, 40, 56, 5, 16, 127);
    protected static final Projectile SPARK_PROJECTILE = new Projectile(1488, 10, 0, 40, 80, 0, 16, 192);

    @Override
    public void init() {

    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (Random.rollDie(6, 1)) {
            sparkAttack();
            return true;
        }
        if (withinDistance(1) && Random.rollDie(5, 3))
            basicAttack();
        else if (withinDistance(1) && !fire && Random.rollDie(2, 1))
            meleeDragonfire();
        else if (Random.rollDie(3, 1))
            rangedDragonfire();
        else if (Random.rollDie(2, 1))
            magicAttack();
        else
            rangedAttack();
        return true;
    }

    private void sparkAttack() {
        npc.animate(81);
        for (int i = 0; i < 2; i++) {
            Position pos = target.getPosition().relative(Random.get(-1, 1), Random.get(-1, 1));
            SPARK_PROJECTILE.send(npc, pos);
            target.addEvent(event -> {
                event.delay(1);
                NPC spark = new NPC(8032).spawn(pos);
                event.delay(2);
                for (int j = 0; j < 5; j++) {
                    if (target == null)
                        break;
                    spark.step(Random.get(-1, 1), Random.get(-1, 1), StepType.FORCE_WALK);
                    if (target.getPosition().isWithinDistance(spark.getPosition(), 1)) {
                        int maxDamage = 8;
                        if (target.player != null && target.player.getEquipment().hasId(7159))
                            maxDamage = 2;
                        target.hit(new Hit().randDamage(1, maxDamage).delay(0));
                    }
                    event.delay(1);
                }
                spark.remove();
            });
        }
    }

    private void rangedAttack() {
        npc.animate(81);
        int delay = RANGED_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED).clientDelay(delay).randDamage(32);
        if (Random.rollDie(5, 2)) {
            hit.boostDamage(0.20);
            target.graphics(753);
            int damage = target.hit(hit);
            if (damage > 0)
                npc.hit(new Hit(HitType.HEAL).fixedDamage((int) (damage * 1.25)).delay(0));
        } else {
            target.hit(hit);
        }
    }

    private void magicAttack() {
        npc.animate(81);
        int delay = MAGIC_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC).clientDelay(delay).randDamage(32));
        target.graphics(163, 92, delay);
    }

}
