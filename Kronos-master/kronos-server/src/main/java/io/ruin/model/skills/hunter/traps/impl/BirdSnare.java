package io.ruin.model.skills.hunter.traps.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.hunter.Hunter;
import io.ruin.model.skills.hunter.traps.TrapType;

public class BirdSnare implements TrapType {

    public static final TrapType INSTANCE = new BirdSnare();

    private BirdSnare() {

    }

    static {
        Hunter.registerTrap(INSTANCE, true);
    }

    @Override
    public int getItemId() {
        return 10006;
    }

    @Override
    public int getLevelReq() {
        return 1;
    }

    @Override
    public int getActiveObjectId() {
        return 9345;
    }

    @Override
    public int getFailedObjectId() {
        return 9344;
    }

    @Override
    public int getPlaceAnimation() {
        return 5208;
    }

    @Override
    public int getDismantleAnimation() {
        return 5207;
    }

    @Override
    public int[] getSuccessObjects() {
        return new int[] {9373, 9377, 9379, 9375, 9348};
    }

    @Override
    public void onPlace(Player player, GameObject object) {

    }

    @Override
    public void onRemove(Player player, GameObject object) {

    }

}
