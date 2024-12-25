package io.ruin.model.activities.miscpvm;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.ProjectileRoute;

public class ElderChaosDruid extends NPCCombat {

    private static final Projectile WIND_WAVE = new Projectile(159, 43, 31, 51, 56, 10, 16, 64);

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        /* TO FIX THE TELEPORT ISSUE! */
        //if (Random.rollPercent(80) && npc.getPosition().isWithinDistance(target.getPosition(), 6)
        //        && teleportAttack() && !target.player.elderChaosDruidTeleport.isDelayed()
        //        && !target.player.getCombat().isAttacking(5))
        //    return true;
        npc.graphics(158, 92, 0);
        npc.publicSound(222, 1, 0);
        npc.animate(1167);
        int delay = WIND_WAVE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC, null).randDamage(17).clientDelay(delay);
        hit.postDamage(t -> {
            if (hit.damage > 0) {
                t.graphics(160, 124, 0);
                t.privateSound(223);
            } else {
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        target.hit(hit);
        return true;
    }

    private boolean teleportAttack() {
        Position destination = npc.getPosition().copy().translate(Random.get(-1, 1), Random.get(-1, 1), 0);
        if (destination.equals(target.getPosition()))
            return false;
        if (!ProjectileRoute.allow(target, destination))
            return false;
        if (target.getMovement().isTeleportQueued())
            return false;
        target.getMovement().teleport(destination);
        npc.forceText("You dare run from us!");
        target.graphics(409, 0, 4);
        target.player.elderChaosDruidTeleport.delay(15);
        if (npc == target)
            target.getCombat().reset();
        Hit hit = new Hit(npc, info.attack_style);
        hit.nullify();
        target.hit(hit);
        return true;
    }
}
