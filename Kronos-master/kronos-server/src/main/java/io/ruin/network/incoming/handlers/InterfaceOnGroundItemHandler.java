package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.network.incoming.Incoming;

public class InterfaceOnGroundItemHandler {

    //@IdHolder(ids = {39}) //todo@@
    public static final class FromItem implements Incoming {
        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int y = in.readUnsignedShortA();
            int interfaceHash = in.readLEInt();
            int itemId = in.readUnsignedShortA();
            int slot = in.readUnsignedShortA();
            int groundItemId = in.readUnsignedShortA();
            int ctrlRun = in.readByteC();
            int x = in.readUnsignedLEShortA();
            handleAction(player, interfaceHash, slot, itemId, groundItemId, x, y, ctrlRun);
        }
    }

    //@IdHolder(ids = {0}) //todo@@
    public static final class FromInterface implements Incoming {
        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int groundItemId = in.readUnsignedShort();
            int interfaceHash = in.readInt1();
            int y = in.readUnsignedLEShortA();
            int ctrlRun = in.readByte();
            int slot = in.readUnsignedLEShort();
            int x = in.readUnsignedShortA();
            handleAction(player, interfaceHash, slot, -1, groundItemId, x, y, ctrlRun);
        }
    }

    private static void handleAction(Player player, int interfaceHash, int slot, int itemId, int groundItemId, int x, int y, int ctrlRun) {
        if(player.isLocked())
            return;
        player.resetActions(true, true, true);
        int z = player.getHeight();
        Tile tile = Tile.get(x, y, z, false);
        if(tile == null)
            return;
        GroundItem groundItem = tile.getPickupItem(groundItemId, player.getUserId());
        if(groundItem == null)
            return;
        player.getMovement().setCtrlRun(ctrlRun == 1);
        player.getRouteFinder().routeGroundItem(groundItem, distance -> action(player, interfaceHash, slot, itemId, groundItem, distance));
    }

    private static void action(Player player, int interfaceHash, int slot, int itemId, GroundItem groundItem, int distance) {
        InterfaceAction action = InterfaceHandler.getAction(player, interfaceHash);
        if(action == null)
            return;
        if(slot == 65535)
            slot = -1;
        if(itemId == 65535)
            itemId = -1;
        action.handleOnGroundItem(player, slot, itemId, groundItem, distance);
    }

}