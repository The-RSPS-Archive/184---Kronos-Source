package io.ruin.model.activities.fightcaves.monsters;

import io.ruin.model.entity.npc.NPCCombat;

public class TzKih extends NPCCombat {

    @Override
    public void init() {
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if(withinDistance(1)) {
            basicAttack(info.attack_animation, info.attack_style, info.max_damage);
            target.player.getPrayer().drain(1);
            return true;
        }
        return false;
    }

}