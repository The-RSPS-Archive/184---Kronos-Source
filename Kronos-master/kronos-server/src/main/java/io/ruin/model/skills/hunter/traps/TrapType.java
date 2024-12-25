package io.ruin.model.skills.hunter.traps;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;

public interface TrapType {

    int getItemId(); // TODO will probably need support for multiple items for net traps, need to investigate
    int getLevelReq();
    int getActiveObjectId(); // trap state ready to catch creature
    int getFailedObjectId(); // after failing
    int getPlaceAnimation();
    int getDismantleAnimation();
    int[] getSuccessObjects();

    void onPlace(Player player, GameObject object);
    void onRemove(Player player, GameObject object);

    default void collapse(Player player, Trap trap, boolean remove) {
        if (trap.getObject().id == -1 || trap.getOwner() == null) {
            return;
        }
        trap.getObject().remove();
        new GroundItem(trap.getTrapType().getItemId(), 1)
                .owner(player)
                .position(trap.getObject().x, trap.getObject().y, trap.getObject().z)
                .spawn();
        trap.setRemoved(true);
        if (remove)
            trap.getOwner().traps.remove(trap);
    }

}
