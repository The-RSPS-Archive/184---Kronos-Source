package io.ruin.model.activities.legacytournament;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class TournamentViewingOrb {

    public static void reset(Player player) {
        if (player.usingTournamentOrb)
            player.getMovement().teleport(player.viewingOrbLocation);
        player.closeInterface(InterfaceType.INVENTORY);
        player.getAppearance().setNpcId(-1);
        player.usingTournamentOrb = false;
        player.usingTournamentOrbFromHome = false;
        player.setHidden(false);
        player.hidePet = false;
        player.unlock();
    }

    private static void move(Player player, Position orbPosition) {
        if (player.isLocked() && !player.usingTournamentOrb)
            return;
        if(player.getPosition().equals(orbPosition)) {
            player.sendFilteredMessage("You're already viewing that orb.");
            return;
        }
        if(!player.isHidden() || player.getAppearance().getNpcId() == -1) {
            player.lock();
            player.setHidden(true);
            player.hidePet = true;
            player.usingTournamentOrb = true;
            player.getAppearance().setNpcId(7177);
        }
        player.getMovement().teleport(orbPosition);
    }

    static {
        /**
         * Viewing orb objects
         */
        ObjectAction.register(26741, "use", (player, obj) -> {
            player.usingTournamentOrbFromHome = false;
            player.viewingOrbLocation = player.getPosition().copy();
            player.openInterface(InterfaceType.INVENTORY, Interface.VIEWING_ORB_INTERFACE);
        });
        ObjectAction.register(26747, "use", (player, obj) -> {

            player.usingTournamentOrbFromHome = true;
            player.viewingOrbLocation = player.getPosition().copy();
            player.openInterface(InterfaceType.INVENTORY, Interface.VIEWING_ORB_INTERFACE);
        });

        /**
         * Viewing orb interface handler
         */
        InterfaceHandler.register(Interface.VIEWING_ORB_INTERFACE, h -> {
            h.actions[5] = (SimpleAction) TournamentViewingOrb::reset;
            h.actions[11] = (SimpleAction) player -> move(player, new Position(3286, 4958, 0));
            h.actions[12] = (SimpleAction) player -> move(player, new Position(3279, 4976, 0));
            h.actions[13] = (SimpleAction) player -> move(player, new Position(3306, 4976, 0));
            h.actions[14] = (SimpleAction) player -> move(player, new Position(3306, 4951, 0));
            h.actions[15] = (SimpleAction) player -> move(player, new Position(3281, 4943, 0));
        });
    }

}
