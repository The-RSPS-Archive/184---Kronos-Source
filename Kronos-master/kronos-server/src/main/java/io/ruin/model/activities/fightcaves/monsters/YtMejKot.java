package io.ruin.model.activities.fightcaves.monsters;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;

public class YtMejKot extends NPCCombat {

    private int attacks = 0;

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
            if(!heal())
                basicAttack(info.attack_animation, info.attack_style, info.max_damage);
            return true;
        }
        return false;
    }

    private boolean heal() {
        if(target.player == null) //should never happen
            return false;
        if(attacks++ < 2)
            return false;
        if(npc.getHp() >= npc.getMaxHp() / 2)
            return false;
        attacks = 0;
        npc.animate(2639);
        npc.graphics(444, 256, 0);
        npc.incrementHp(Random.get(1, 10));
        for(NPC n : target.player.localNpcs()) {
            if(n.isTargeting(target.player) && n.getHp() < n.getMaxHp() / 2)
                n.incrementHp(Random.get(1, 10));
        }
        return true;
    }

}