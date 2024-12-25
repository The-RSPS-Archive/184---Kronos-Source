package io.ruin.network.incoming.handlers;

import io.ruin.api.buffer.InBuffer;
import io.ruin.event.GameEventProcessor;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.item.Item;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.DebugMessage;
import io.ruin.utility.IdHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

@Slf4j
public class ActionButtonHandler {

    @IdHolder(ids = {60, 65, 73, 10, 20, 16, 87, 19, 29, 2})
    public static final class DefaultHandler implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int option = OPTIONS[opcode];
            int interfaceHash = in.readInt();
            int slot = in.readUnsignedShort();
            int itemId = in.readUnsignedShort();
            handleAction(player, option, interfaceHash, slot, itemId, false);
        }

    }

    @IdHolder(ids = {88, 70, 5, 14, 76, 28})
    public static final class InventoryHandler implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int option = OPTIONS[opcode];
            if(option == 1) {
                int interfaceHash = in.readInt();
                int slot = in.readLEShort();
                int itemId = in.readLEShort();
                handleAction(player, option, interfaceHash, slot, itemId, false);
                return;
            }
            if(option == 2) {
                int interfaceHash = in.readInt();
                int slot = in.readLEShort();
                int itemId = in.readShortA();
                handleAction(player, option, interfaceHash, slot, itemId, false);
                return;
            }
            if(option == 3) {
                int itemId = in.readLEShort();
                int interfaceHash = in.readLEInt();
                int slot = in.readShort();
                handleAction(player, option, interfaceHash, slot, itemId, false);
                return;
            }
            if(option == 4) {
                int itemId = in.readLEShortA();
                int slot = in.readLEShort();
                int interfaceHash = in.readInt1();
                handleAction(player, option, interfaceHash, slot, itemId, false);
                return;
            }
            if(option == 5) {
                int slot = in.readShortA();
                int interfaceHash = in.readInt1();
                int itemId = in.readShortA();
                handleAction(player, option, interfaceHash, slot, itemId, false);
                return;
            }
            if(option == 6) {
                int itemId = in.readLEShort();
                int interfaceHash = in.readLEInt();
                int slot = in.readShort();
                handleAction(player, 10, interfaceHash, slot, itemId, false);
                return;
            }
            player.sendFilteredMessage("Unhandled interface action: option=" + option + " opcode=" + opcode);
        }

    }

    @IdHolder(ids = {1})
    public static final class DialogueHandler implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int interfaceHash = in.readLEInt();
            int slot = in.readShort();
            if (GameEventProcessor.resumeWith(player, slot)) {
                return;
            }
            handleAction(player, 1, interfaceHash, slot, -1, true);
        }

    }

    @IdHolder(ids = {99})
    public static final class OtherHandler implements Incoming {

        @Override
        public void handle(Player player, InBuffer in, int opcode) {
            int interfaceHash = in.readInt();
            handleAction(player, 1, interfaceHash, -1, -1, false);
        }

    }

    public static void handleAction(Player player, int option, int interfaceHash, int slot, int itemId, boolean dialogue) {
        int interfaceId = interfaceHash >> 16;
        int childId = interfaceHash & 0xffff;
        if(childId == 65535)
            childId = -1;
        if(slot == 65535)
            slot = -1;
        if(itemId == 65535)
            itemId = -1;
        if(player.debug) {
            DebugMessage debug = new DebugMessage()
                    .add("option", option)
                    .add("inter", interfaceId)
                    .add("child", childId)
                    .add("slot", slot)
                    .add("item", itemId);
            player.sendFilteredMessage("[ActionButton] " + debug.toString());
        }
        if(player.inTutorial && interfaceId != Interface.LOGOUT && !dialogue && interfaceId != Interface.IRON_MAN_SETTINGS && interfaceId != Interface.APPEARANCE_CUSTOMIZATION)
            return;
        if (option == 10 && interfaceId == 149 && itemId != -1) {
            player.getInventory().get(slot).examine(player);
            return;
        }
        InterfaceAction action = InterfaceHandler.getAction(player, interfaceId, childId);
        if(action != null)
            action.handleClick(player, option, slot, itemId);
    }

}