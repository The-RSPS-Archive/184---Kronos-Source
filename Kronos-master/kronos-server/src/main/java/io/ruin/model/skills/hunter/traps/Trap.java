package io.ruin.model.skills.hunter.traps;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.hunter.creature.Creature;

public class Trap {

    private Player owner;
    private TrapType trapType;
    private GameObject object;
    private Creature trappedCreature;
    private boolean busy;

    private boolean removed = false;

    public Trap(Player owner, TrapType trapType, GameObject object) {
        this.owner = owner;
        this.trapType = trapType;
        this.object = object;
        object.trap = this;
    }

    public Player getOwner() {
        return owner;
    }

    public TrapType getTrapType() {
        return trapType;
    }

    public GameObject getObject() {
        return object;
    }

    public void setObject(GameObject object) {
        this.object = object;
    }

    public Creature getTrappedCreature() {
        return trappedCreature;
    }

    public void setTrappedCreature(Creature trappedCreature) {
        this.trappedCreature = trappedCreature;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}
