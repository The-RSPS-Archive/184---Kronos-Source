package io.ruin.model.activities.bosses.corp;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;

public class DarkCore extends NPCCombat {
    @Override
    public void init() {

    }

    @Override
    public void follow() {

    }

    @Override
    public boolean allowRetaliate(Entity attacker) {
        //todo - let's add some kind of boolean in combat for npc allowRetaliate so we don't have to create a class
        //everytime we don't want an npc to simply not allowRetaliate, l0l.
        return false;
    }

    @Override
    public boolean attack() {
        return false;
    }

}
