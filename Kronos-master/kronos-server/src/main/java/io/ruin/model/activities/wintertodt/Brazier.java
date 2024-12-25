package io.ruin.model.activities.wintertodt;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.object.GameObject;

public class Brazier {

    private GameObject object;
    private NPC pyromancer;

    private int flameOffsetX, flameOffsetY;

    public Brazier(GameObject object, NPC pyromancer, int flameOffsetX, int flameOffsetY) {
        this.object = object;
        this.pyromancer = pyromancer;
        this.flameOffsetX = flameOffsetX;
        this.flameOffsetY = flameOffsetY;
    }

    public GameObject getObject() {
        return object;
    }

    public NPC getPyromancer() {
        return pyromancer;
    }

    public int getPyromancerState() {
        return pyromancer.getId() == Wintertodt.PYROMANCER ? 1 : 0;
    }

    public boolean isPyromancerAlive() {
        return pyromancer.getId() == Wintertodt.PYROMANCER;
    }

    public int getFlameOffsetX() {
        return flameOffsetX;
    }

    public int getFlameOffsetY() {
        return flameOffsetY;
    }

    public int getBrazierState() {
        return object.id == Wintertodt.EMPTY_BRAZIER ? 1 :
                (object.id == Wintertodt.BURNING_BRAZIER ? 2 : 0);
    }

}
