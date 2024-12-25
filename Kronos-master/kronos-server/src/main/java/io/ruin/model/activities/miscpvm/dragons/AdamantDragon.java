package io.ruin.model.activities.miscpvm.dragons;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;

public class AdamantDragon extends MetalDragon {

    protected static final Projectile MAGIC_PROJECTILE = new Projectile(165, 10, 31, 40, 56, 10, 16, 192);
    protected static final Projectile RANGED_PROJECTILE = new Projectile(1485, 10, 31, 40, 56, 5, 16, 127);
    protected static final Projectile POISON_PROJECTILE = new Projectile(1486, 10, 0, 40, 110, 0, 16, 192);
    protected static final Projectile GTG_POISON = new Projectile(1486, 0, 0, 0, 60, 0, 28, 0);

    @Override
    public void init() {

    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (Random.rollDie(6, 1)) {
            poisonAttack();
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

    private void rangedAttack() {
        npc.animate(81);
        int delay = RANGED_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED).clientDelay(delay).randDamage(24);
        if (Random.rollDie(5, 2)) {
            hit.ignoreDefence();
            hit.boostDamage(0.15);
            target.graphics(758);
        }
        target.hit(hit);
    }

    private void magicAttack() {
        npc.animate(81);
        int delay = MAGIC_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC).clientDelay(delay).randDamage(24));
        target.graphics(166, 92, delay);
    }

    private void poisonAttack() {
        npc.animate(81);
        Position pos = target.getPosition().copy();
        int delay = POISON_PROJECTILE.send(npc, pos);
        World.sendGraphics(1487, 0, delay - 5, pos);
        target.addEvent(event -> {
            event.delay(3);
            if (target == null)
                return;
            if (target.getPosition().isWithinDistance(pos, 1)) {
                int maxDamage = target.isPoisonImmune() ? 12 : 24;
                target.hit(new Hit(HitType.POISON).randDamage(1, maxDamage).delay(0));
            }
            Position[] positions = new Position[2];
            //for (int i = 0; i < 2; i++) {
                int x = Random.get(1);
                int y = Random.get(1);
                if (x == y && x == 0) {
                    x = Random.get(1);
                    y = 1;
                }
                if (Random.rollDie(2)) x *= -1;
                if (Random.rollDie(2)) y *= -1;
                Position p = pos.relative(x, y);
                //if (i == 0)
                    World.sendGraphics(1487, 0, GTG_POISON.send(pos, p), p);
                //else
                  //  World.sendGraphics(1487, 0, new Projectile(1486, 0, 0, 10, 50, 0, 28, 0).send(pos, p), p);
                positions[0] = p;
            //}
            event.delay(2);
            if (target == null)
                return;
            for (Position poss : positions) {
                if (target == null)
                    continue;
                if (target.getPosition().isWithinDistance(poss, 1)) {
                    int maxDamage = target.isPoisonImmune() ? 8 : 16;
                    target.hit(new Hit(HitType.POISON).randDamage(1, maxDamage).delay(0));
                }
            }
        });
    }

}
