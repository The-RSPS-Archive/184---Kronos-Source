package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Position;
import io.ruin.model.map.route.routes.ProjectileRoute;

public class AbyssalDemon extends NPCCombat {

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        if (Random.rollDie(4, 1) && teleportAttack())
            return true;
        else
            basicAttack();
        return true;
    }

    private boolean teleportAttack() {
        Entity entity = Random.rollDie(2, 1) ? npc : target;
        Position destination = entity.getPosition().copy().translate(Random.get(-1, 1), Random.get(-1, 1), 0);
        if (destination.equals(entity.getPosition()))
            return false;
        if (!ProjectileRoute.allow(entity, destination))
            return false;
        entity.getMovement().teleport(destination);
        entity.graphics(409);
        if (entity == target)
            target.getCombat().reset();
        Hit hit = new Hit(npc, info.attack_style);
        hit.nullify();
        target.hit(hit); // prevent nearby demons from pjing
        return true;
    }
}
