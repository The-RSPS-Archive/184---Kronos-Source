package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Unlock;

public class TabLogout {

    static {
        InterfaceHandler.register(Interface.LOGOUT, h -> {
            h.actions[3] = (SimpleAction) WorldSwitcher::open;
            h.actions[8] = (SimpleAction) Player::attemptLogout;
        });
    }

    private static final class WorldSwitcher {

        static {
            InterfaceHandler.register(Interface.WORLD_SWITCHER, h -> {
                h.actions[3] = (SimpleAction) p -> p.closeInterface(InterfaceType.INVENTORY_OVERLAY);
                h.actions[19] = (SimpleAction) Player::attemptLogout;
                h.actions[9] = (SimpleAction) p -> toggleSettings(p, 2, 3);
                h.actions[10] = (SimpleAction) p -> toggleSettings(p, 1, 0);
                h.actions[11] = (SimpleAction) p -> toggleSettings(p, 4, 5);
                h.actions[12] = (SimpleAction) p -> toggleSettings(p, 8, 9);
                h.actions[13] = (SimpleAction) p -> toggleSettings(p, 6, 7);
                h.actions[14] = (DefaultAction) (player, option, slot, itemId) -> {
                    if (option == 1) {
                        player.attemptLogout();
                    } else {
                        if (Config.WORLD_SWITCHER_FAVOURITE_ONE.get(player) == 0)
                            Config.WORLD_SWITCHER_FAVOURITE_ONE.set(player, slot);
                        else
                            Config.WORLD_SWITCHER_FAVOURITE_TWO.set(player, slot);
                    }
                };
                h.actions[17] = (DefaultAction) (player, option, slot, itemId) -> {
                    if (option == 1)
                        System.out.println("World hop");
                    else
                        Config.WORLD_SWITCHER_FAVOURITE_ONE.set(player, 0);

                };
                h.actions[18] = (DefaultAction) (player, option, slot, itemId) -> {
                    if (option == 1)
                        System.out.println("World hop");
                    else
                        Config.WORLD_SWITCHER_FAVOURITE_TWO.set(player, 0);
                };
            });
        }

        public static void open(Player player) {
           if (true) {
               player.sendMessage("Error sending request to world server.");
           } else {
               player.openInterface(InterfaceType.INVENTORY_OVERLAY, Interface.WORLD_SWITCHER);
               new Unlock(Interface.WORLD_SWITCHER, 14).children(0, 420).unlockWith(player, 6);
           }
        }

        private static void toggleSettings(Player p, int set, int elseSet) {
            if (Config.WORLD_SWITCHER_SETTINGS.get(p) == set)
                Config.WORLD_SWITCHER_SETTINGS.set(p, elseSet);
            else
                Config.WORLD_SWITCHER_SETTINGS.set(p, set);
        }

    }

}