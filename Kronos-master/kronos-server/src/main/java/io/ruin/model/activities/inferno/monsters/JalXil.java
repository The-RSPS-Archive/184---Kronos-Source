package io.ruin.model.activities.inferno.monsters;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;

public class JalXil extends NPCCombat { // Ranger
    private static final Projectile PROJECTILE = new Projectile(1377, 60, 31, 50, 40, 8, 16, 0).regionBased();

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(16);
    }

    @Override
    public boolean attack() {
        if(withinDistance(1)) {
            if(Random.rollDie(3)) {
                basicAttack(info.attack_animation, info.attack_style, info.max_damage);
                return true;
            }
        } else if(!withinDistance(16)) {
            /**
             * Not in ranged distance
             */
            return false;
        }
        npc.animate(7605);
        Direction dir = Direction.getDirection(Misc.getClosestPosition(npc, target), target.getPosition());
        int delay;
        if (dir == Direction.NORTH || dir == Direction.SOUTH) {
            delay = PROJECTILE.send(npc, npc.getPosition().getX(), npc.getPosition().getY() + 1, target);
            PROJECTILE.send(npc, npc.getPosition().getX() + 2, npc.getPosition().getY() + 1, target);
        } else {
            delay = PROJECTILE.send(npc, npc.getPosition().getX() + 1, npc.getPosition().getY(), target);
            PROJECTILE.send(npc, npc.getPosition().getX() + 1, npc.getPosition().getY() + 2, target);
        }
        Hit hit = new Hit(npc, AttackStyle.RANGED, null).randDamage(info.max_damage).clientDelay(delay);
        target.hit(hit);
        return true;
    }
}
