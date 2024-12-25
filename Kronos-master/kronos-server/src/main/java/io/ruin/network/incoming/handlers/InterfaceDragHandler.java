package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.DebugMessage;
import io.ruin.utility.IdHolder;

public class InterfaceDragHandler {

    @IdHolder(ids = {38})
    public static final class SingleInterface implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int interfaceHash = in.readInt1();
            int toSlot = in.readShort();
            int unknownInt = in.readByte(); //Always 0 or 1
            int fromSlot = in.readLEShort();
            handleDrag(player, interfaceHash, fromSlot, -1, interfaceHash, toSlot, -1);
        }

    }

    @IdHolder(ids = {94})
    public static final class TwoInterfaces implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int toSlot = in.readLEShortA();
            int toItemId = in.readLEShortA();
            int fromSlot = in.readLEShort();
            int toInterfaceHash = in.readInt1();
            int fromItemId = in.readShortA();
            int fromInterfaceHash = in.readInt();
            handleDrag(player, fromInterfaceHash, fromSlot, fromItemId, toInterfaceHash, toSlot, toItemId);
        }

    }

    private static void handleDrag(Player player, int fromInterfaceHash, int fromSlot, int fromItemId, int toInterfaceHash, int toSlot, int toItemId) {
        int fromInterfaceId = fromInterfaceHash >> 16;
        int fromChildId = fromInterfaceHash & 0xffff;
        if(fromChildId == 65535)
            fromChildId = -1;
        if(fromSlot == 65535)
            fromSlot = -1;
        if(fromItemId == 65535)
            fromItemId = -1;

        int toInterfaceId = toInterfaceHash >> 16;
        int toChildId = toInterfaceHash & 0xffff;
        if(toChildId == 65535)
            toChildId = -1;
        if(toSlot == 65535)
            toSlot = -1;
        if(toItemId == 65535)
            toItemId = -1;

        if(player.debug) {
            player.sendFilteredMessage("[DragItssem]");
            DebugMessage debug = new DebugMessage()
                    .add("inter", fromInterfaceId)
                    .add("child", fromChildId)
                    .add("slot", fromSlot)
                    .add("item", fromItemId);
            player.sendFilteredMessage("    From: " + debug.toString());
            debug = new DebugMessage()
                    .add("inter", toInterfaceId)
                    .add("child", toChildId)
                    .add("slot", toSlot)
                    .add("item", toItemId);
            player.sendFilteredMessage("    To: " + debug.toString());
        }

        InterfaceAction action = InterfaceHandler.getAction(player, fromInterfaceId, fromChildId);
        if(action != null)
            action.handleDrag(player, fromSlot, fromItemId, toInterfaceId, toChildId, toSlot, toItemId);
    }

}