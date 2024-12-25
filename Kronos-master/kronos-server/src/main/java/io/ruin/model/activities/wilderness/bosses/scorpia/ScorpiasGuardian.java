package io.ruin.model.activities.wilderness.bosses.scorpia;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;

public class ScorpiasGuardian extends NPCCombat {
    @Override
    public void init() {

    }

    @Override
    public void follow() {

    }

    @Override
    public boolean allowRetaliate(Entity attacker) {
        return false;
    }

    @Override
    public boolean attack() {
        return false;
    }
}
