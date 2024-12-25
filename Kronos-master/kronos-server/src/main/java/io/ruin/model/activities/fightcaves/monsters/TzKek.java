package io.ruin.model.activities.fightcaves.monsters;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;

public class TzKek extends NPCCombat {

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDamage(hit -> {
            if(hit.attackStyle != null && hit.attackStyle.isMelee())
                hit.attacker.hit(new Hit().fixedDamage(1));
        });
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if(withinDistance(1)) {
            basicAttack(info.attack_animation, info.attack_style, info.max_damage);
            return true;
        }
        return false;
    }

}