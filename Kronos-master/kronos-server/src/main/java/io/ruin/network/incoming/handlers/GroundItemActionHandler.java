package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.ground.GroundItemAction;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

@IdHolder(ids = {97, 27, 18, 63, 24})
public class GroundItemActionHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        if(player.isLocked())
            return;
        player.resetActions(true, true, true);
        int option = OPTIONS[opcode];
        if(option == 1) {
            int ctrlRun = in.readByteS();
            int y = in.readLEShort();
            int x = in.readShortA();
            int id = in.readLEShortA();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if(option == 2) {
            int ctrlRun = in.readByteA();
            int x = in.readShortA();
            int id = in.readShortA();
            int y = in.readShortA();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if(option == 3) {
            int ctrlRun = in.readByteC();
            int x = in.readLEShortA();
            int y = in.readShortA();
            int id = in.readLEShort();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if(option == 4) {
            int x = in.readLEShortA();
            int y = in.readShortA();
            int ctrlRun = in.readByteS();
            int id = in.readShort();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        if(option == 5) {
            int x = in.readLEShort();
            int y = in.readLEShort();
            int id = in.readLEShortA();
            int ctrlRun = in.readByteS();
            handleAction(player, option, id, x, y, ctrlRun);
            return;
        }
        player.sendFilteredMessage("Unhandled ground item action: option=" + option + " opcode=" + opcode);
    }

    private static void handleAction(Player player, int option, int groundItemId, int x, int y, int ctrlRun) {
        int z = player.getHeight();
        Tile tile = Tile.get(x, y, z, false);
        if(tile == null)
            return;
        GroundItem groundItem = tile.getPickupItem(groundItemId, player.getUserId());
        if(groundItem == null)
            return;
        ItemDef def = ItemDef.get(groundItem.id);
        player.getMovement().setCtrlRun(ctrlRun == 1);
        player.getRouteFinder().routeGroundItem(groundItem, distance -> {
            int i = option - 1;
            if(i < 0 || i >= 5)
                return;
            if(option == def.pickupOption) {
                groundItem.pickup(player, distance);
                return;
            }
            GroundItemAction action;
            if(def.groundActions != null && (action = def.groundActions[i]) != null) {
                action.handle(player, groundItem, distance);
                return;
            }
            player.sendMessage("Nothing interesting happens.");
        });
    }

}