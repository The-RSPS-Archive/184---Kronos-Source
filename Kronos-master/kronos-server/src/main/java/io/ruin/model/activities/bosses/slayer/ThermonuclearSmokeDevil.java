package io.ruin.model.activities.bosses.slayer;

import io.ruin.model.activities.miscpvm.slayer.SmokeDevil;
import io.ruin.model.combat.Hit;

public class ThermonuclearSmokeDevil extends SmokeDevil {

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (smokeAttack())
            return true;
        npc.animate(info.attack_animation);
        int delay = SmokeDevil.PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, info.attack_style).randDamage(info.max_damage).ignorePrayer().clientDelay(delay);
        target.hit(hit);
        return true;
    }

}
