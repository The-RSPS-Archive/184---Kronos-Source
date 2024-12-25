package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.activities.bosses.hydra.AlchemicalHydra;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.utility.Misc;

import java.util.LinkedList;
import java.util.List;

public class Hydra extends NPCCombat {


    private static final Projectile MAGIC_PROJECTILE = new Projectile(1662, 35, 21, 25, 30, 10, 16, 64);
    private static final Projectile RANGED_PROJECTILE = new Projectile(1663, 40, 31, 25, 30, 10, 16, 64);
    private static final Projectile POISON_PROJECTILE = new Projectile(1644, 35, 0, 25, 75, 0, 16, 64);


    private AttackStyle currentStyle = Random.rollPercent(50) ? AttackStyle.RANGED : AttackStyle.MAGIC;
    private int attackCounter = 0;

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(6);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(6))
            return false;
        if (attackCounter > 0 && attackCounter % 9 == 0) {
            poisonAttack();
        } else {
            if (currentStyle == AttackStyle.MAGIC)
                magicAttack();
            else
                rangedAttack();
        }
        attackCounter++;
        if (attackCounter % 3 == 0)
            switchStyle();
        return true;
    }

    private void magicAttack() {
        projectileAttack(MAGIC_PROJECTILE, 8261, AttackStyle.MAGIC, info.max_damage);
    }

    private void rangedAttack() {
        projectileAttack(RANGED_PROJECTILE, 8262, AttackStyle.RANGED, info.max_damage);
    }


    private void poisonAttack() {
        npc.animate(8263);
        List<Position> targets = new LinkedList<>();
        targets.add(target.getPosition().copy());
            Bounds hydraBounds = npc.getBounds();
            List<Position> positions = target.getPosition().area(3, pos -> pos.getTile().clipping == 0 && !pos.inBounds(hydraBounds) && ProjectileRoute.allow(npc, pos));
            for (int i = 0; i < 2; i++)
                targets.add(Random.get(positions));
        targets.forEach(pos -> npc.addEvent(event -> {
            int delay = POISON_PROJECTILE.send(npc, pos);
            Direction dir = Direction.getDirection(Misc.getClosestPosition(npc, pos), pos);
            World.sendGraphics(1645, 0, delay, pos.getX(), pos.getY(), pos.getZ());
            World.sendGraphics(AlchemicalHydra.POISON_POOLS[dir.ordinal()], 0, delay, pos.getX(), pos.getY(), pos.getZ());
            event.delay(3);
            for (int i = 0; i < 15; i++) {
                if (target == null)
                    return;
                if (target.getPosition().isWithinDistance(pos, i == 0 ? 1 : 0)) {
                    target.hit(new Hit(HitType.POISON).randDamage(1, 4));
                }
                event.delay(2);
            }
        }));
    }

    private void switchStyle() {
        currentStyle = currentStyle == AttackStyle.MAGIC ? AttackStyle.RANGED : AttackStyle.MAGIC;
    }

}
