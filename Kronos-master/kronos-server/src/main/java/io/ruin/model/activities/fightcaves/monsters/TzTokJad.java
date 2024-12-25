package io.ruin.model.activities.fightcaves.monsters;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

//todo SOUNDS
public class TzTokJad extends NPCCombat {

    private static final int MAX_DISTANCE = 10; //16 if and only if we have the correct sounds!

    private static final Projectile[] MAGIC_PROJECTILES = {
            new Projectile(448, 128, 31, 2, 10, 8, 16, 32),
            new Projectile(449, 128, 31, 6, 14, 8, 16, 32),
            new Projectile(450, 128, 31, 10, 20, 8, 16, 32)
    };

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(MAX_DISTANCE);
    }

    @Override
    public boolean attack() {
        if(withinDistance(1)) {
            if(Random.rollDie(3)) {
                basicAttack(info.attack_animation, info.attack_style, info.max_damage);
                return true;
            }
        } else if(!withinDistance(MAX_DISTANCE)) {
            /**
             * Not in ranged distance
             */
            return false;
        }
        if(Random.rollPercent(50)) {
            /**
             * Magic attack
             */
            npc.animate(2656);
            npc.addEvent(e -> {
                e.delay(2);
                if(target == null)
                    return;
                npc.graphics(447, 500, 0);
                int delay = MAGIC_PROJECTILES[0].send(npc, target);
                for(int i = 1; i < MAGIC_PROJECTILES.length; i++)
                    MAGIC_PROJECTILES[i].send(npc, target);
                Hit hit = new Hit(npc, AttackStyle.MAGIC)
                        .randDamage(info.max_damage)
                        .clientDelay(delay);
                hit.postDamage(t -> {
                    t.graphics(157);
                    t.privateSound(163);
                });
                target.hit(hit);
            });
        } else {
            /**
             * Ranged attack
             */
            npc.animate(2652);
            npc.addEvent(e -> {
                e.delay(2);
                if(target == null)
                    return;
                World.sendGraphics(451, 0, 0, target.getPosition());
                Hit hit = new Hit(npc, AttackStyle.RANGED)
                        .randDamage(info.max_damage)
                        .delay(2);
                hit.postDamage(t -> {
                    t.graphics(157);
                    t.privateSound(163);
                });
                target.hit(hit);
            });
        }
        return true;
    }

}