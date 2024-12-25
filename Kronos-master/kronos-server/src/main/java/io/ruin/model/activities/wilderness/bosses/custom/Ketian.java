package io.ruin.model.activities.wilderness.bosses.custom;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;

import java.util.List;

public class Ketian extends NPCCombat {

    private static final Projectile STANDARD_PROJECTILE = new Projectile(995, 60, 40, 61, 20, 15, 15, 127);
    private static final Projectile FIRE_PROJECTILE = new Projectile(17, 60, 40, 65, 20, 15, 15, 127);
    private static final Projectile ICE_PROJECTILE = new Projectile(16, 60, 40, 65, 20, 15, 15, 127);
    private static final Projectile FIRE_SECONDARY_PROJECTILE =  new Projectile(660, 30, 0, 0, 75, 0, 55, 0);

    @Override
    public void init() {
        npc.hitsUpdate.hpBarType = 2;
        npc.hitListener = new HitListener().postDefend(this::postDefend);
    }

    private void postDefend(Hit hit) {
        if (hit.damage > 100 && !hit.isBlocked())
            hit.damage = 100;
    }

    @Override
    public void updateLastDefend(Entity attacker) {
        super.updateLastDefend(attacker);
        if(attacker.player != null && !attacker.player.getCombat().isSkulled()) {
            attacker.player.getCombat().skullNormal();
            attacker.player.sendMessage("<col=6f0000>You've been marked with a skull for attacking Ket'ian!");
        }
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        if (Random.rollPercent(30))
            fireFire();
        else if (Random.rollPercent(30))
            fireIce();
        else
            fireStandard();
        return true;
    }

    private void fireStandard() {
        npc.graphics(994);
        projectileAttack(STANDARD_PROJECTILE, info.attack_animation, AttackStyle.RANGED, info.max_damage);
    }

    private void fireIce() {
        npc.animate(info.attack_animation);
        npc.graphics(25, 250, 0);
        int delay = ICE_PROJECTILE.send(npc, target);
        target.graphics(1312, 0, delay);
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay).postDamage(entity -> {
            entity.freeze(5, npc);
            if (entity.player != null) {
                entity.player.sendMessage("The ice arrow freezes you to the bone!");
            }
            entity.localPlayers().forEach(p -> {
                if (p == entity)
                    return;
                if (Misc.getDistance(entity.getPosition(), p.getPosition()) <= 1) {
                    p.graphics(1312, 0, 0);
                    p.hit(new Hit(npc, AttackStyle.MAGIC).randDamage((int) (info.max_damage * 2.0/3)));
                }
            });
        }));
    }

    private void fireFire() {
        npc.animate(info.attack_animation);
        npc.graphics(26, 250, 0);
        int delay = FIRE_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay).postDamage(entity -> {
            List<Position> area = entity.getPosition().area(2, pos -> !pos.equals(entity.getPosition()));
            for (int i = 0; i < 6; i++) {
                Position pos = Random.get(area);
                FIRE_SECONDARY_PROJECTILE.send(entity, pos.getX(), pos.getY());
                World.sendGraphics(659, 0, 75, pos);
                World.startEvent(event -> {
                   event.delay(3);
                   npc.localPlayers().forEach(p -> {
                       if (p == entity)
                           return;
                       if (p.getPosition().equals(pos))
                           p.hit(new Hit(npc, null).randDamage(20, 30));
                   });
                });
                area.remove(pos);
            }
        }));

    }


}
