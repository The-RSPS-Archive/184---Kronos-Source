package io.ruin.model.skills.construction.actions;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Construction;
import io.ruin.model.skills.construction.Hotspot;

public class DiningRoom {

    static {
        for (Buildable buildable : Hotspot.BELL_PULL.getBuildables()) {
            ObjectAction.register(buildable.getBuiltObjects()[0], "ring", Construction.forHouseOwnerOnly((player, house) -> {
                if (house.getServantSave().getHiredServant() == null) {
                    player.sendMessage("You don't have a servant.");
                    return;
                }
                house.callServant();
                player.animate(3667);
                player.sendMessage("You ring the bell and your servant comes to you.");
            }));
        }
    }

}
