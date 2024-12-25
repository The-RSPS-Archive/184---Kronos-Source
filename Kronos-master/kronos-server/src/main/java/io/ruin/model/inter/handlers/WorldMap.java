package io.ruin.model.inter.handlers;

import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;

public class WorldMap {
    static {
        InterfaceHandler.register(Interface.WORLD_MAP, h -> h.actions[37] = (SimpleAction) p -> p.closeInterface(InterfaceType.WORLD_MAP));
    }
}
