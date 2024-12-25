package io.ruin.model.inter.handlers;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.journal.Journal;

public class TabJournal {

        static {
//            InterfaceHandler.register(259, h -> {
//                h.actions[8] = (SimpleAction) Journal.MAIN::send;
//                h.actions[9] = (SimpleAction) Journal.ACHIEVEMENTS::send;
//                h.actions[10] = (SimpleAction) Journal.PRESETS::send ;
//                h.actions[11] = (SimpleAction) Journal.TOGGLES::send;
//                h.actions[12] = (SimpleAction) Journal.BESTIARY::send;
//                h.actions[4] = (SlotAction) (p, slot) -> p.journal.select(p, slot);
//            });
            InterfaceHandler.register(Interface.SERVER_TAB, h -> {
                h.actions[48] = (SimpleAction) TabJournal::restore;
//                h.actions[49] = (SimpleAction) TabJournal::restore;
                for(int i = 9; i <= 47; i++) {
                    int finalI = i;
                    h.actions[i] = (SimpleAction) p -> p.journal.select(p, finalI - 8);
                }
            });
        }
//        InterfaceHandler.register(Interface.SERVER_TAB, h -> {
//            for(int i = 9; i <= 47; i++) {
//                int finalI = i;
//                h.actions[i] = (SimpleAction) p -> p.journal.select(p, finalI - 8);
//            }
//
//            h.actions[48] = (SimpleAction) player -> {
//                for(int childId = 9; childId <= 47; childId++) {
//                    player.getPacketSender().sendClientScript(135, "ii",701 << 16 | childId, 494);
//                    player.getPacketSender().sendString(701, childId, "");
//                }
//                player.journal = player.journal.previous();
//                player.getPacketSender().sendString(Interface.SERVER_TAB, 1, StringUtils.getFormattedEnumName(player.journal));
//                player.journal.send(player);
//            };
//
//            h.actions[49] = (SimpleAction) player -> {
//                for(int childId = 9; childId <= 47; childId++) {
//                    player.getPacketSender().sendClientScript(135, "ii",701 << 16 | childId, 494);
//                    player.getPacketSender().sendString(701, childId, "");
//                }
//                player.journal = player.journal.next();
//                player.getPacketSender().sendString(Interface.SERVER_TAB, 1, StringUtils.getFormattedEnumName(player.journal));
//                player.journal.send(player);
//            };
//        });
//    }

    public static void swap(Player player, int interfaceId) {
        if(player.isFixedScreen())
            player.getPacketSender().sendInterface(interfaceId, 548, 68, 1);
        else if(player.getGameFrameId() == 164)
            player.getPacketSender().sendInterface(interfaceId, 164, 70, 1);
        else
            player.getPacketSender().sendInterface(interfaceId, 161, 70, 1);
    }

    public static void restore(Player player) {
        swap(player, Interface.NOTICEBOARD);
        TabQuest.send(player);
        //player.journal.send(player);
    }

}