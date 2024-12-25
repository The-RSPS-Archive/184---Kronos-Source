package io.ruin.model.activities.miscpvm.slayer;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;

public class DeathSpawn extends NPCCombat {
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
        npc.animate(info.attack_animation);
        target.hit(new Hit(null, info.attack_style).randDamage(info.max_damage).ignorePrayer());
        return true;
    }

    @Override
    public boolean multiCheck(Entity entity) {
        return true;
    }
}
