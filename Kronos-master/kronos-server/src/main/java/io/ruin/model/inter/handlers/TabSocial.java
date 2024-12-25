package io.ruin.model.inter.handlers;

import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;

public class TabSocial {

    static {
        InterfaceHandler.register(Interface.FRIENDS_LIST, h -> h.actions[1]  = (SimpleAction) p -> {
            p.openInterface(InterfaceType.SOCIAL_TAB, Interface.IGNORE_LIST);
            Config.FRIENDS_AND_IGNORE_TOGGLE.set(p, 1);
        });

        InterfaceHandler.register(Interface.IGNORE_LIST, h -> h.actions[1]  = (SimpleAction) p -> {
            p.openInterface(InterfaceType.SOCIAL_TAB, Interface.FRIENDS_LIST);
            Config.FRIENDS_AND_IGNORE_TOGGLE.set(p, 0);
        });
    }

}