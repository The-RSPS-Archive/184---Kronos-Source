package io.ruin.model.inter;

import io.ruin.cache.InterfaceDef;
import io.ruin.model.entity.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class InterfaceHandler {

    public static final InterfaceHandler[] HANDLERS = new InterfaceHandler[InterfaceDef.COUNTS == null ? 712 : InterfaceDef.COUNTS.length];

    public static final InterfaceHandler EMPTY_HANDLER = new InterfaceHandler();

    public final int id;

    public final InterfaceAction[] actions;

    public BiConsumer<Player, Integer> closedAction;

    public InterfaceHandler() {
        this.id = -1;
        this.actions = new InterfaceAction[0];
    }

    protected InterfaceHandler(int id) {
        this.id = id;
        this.actions = new InterfaceAction[InterfaceDef.COUNTS[id]];
        HANDLERS[id] = this;
    }

    public static void register(int interfaceId, Consumer<InterfaceHandler> consumer) {
        InterfaceHandler handler = new InterfaceHandler(interfaceId);
        consumer.accept(handler);
        HANDLERS[interfaceId] = handler;
    }

    public static InterfaceAction getAction(Player player, int interfaceHash) {
        int interfaceId = interfaceHash >> 16;
        int childId = interfaceHash & 0xffff;
        if(childId == 65535)
            childId = -1;
        return getAction(player, interfaceId, childId);
    }

    public static InterfaceAction getAction(Player player, int interfaceId, int childId) {
        if(interfaceId < 0 || interfaceId >= HANDLERS.length)
            return null;
        if(!player.isVisibleInterface(interfaceId)) {
            return null;
        }
        InterfaceHandler handler = HANDLERS[interfaceId];
        if(handler == null)
            return null;
        if(childId < 0 || childId >= handler.actions.length)
            return null;
        return handler.actions[childId];
    }

}