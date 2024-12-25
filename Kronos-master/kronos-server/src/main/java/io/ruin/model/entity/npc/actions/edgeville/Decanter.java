package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.skills.herblore.Potion;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/20/2020
 */
public class Decanter {
    private static void decantPotions(Player player, NPC npc) {
        player.openInterface(InterfaceType.CHATBOX, Interface.POTION_DECANTING);
    }
    static {
        NPCAction.register(5449, "decant", Decanter::decantPotions);
        InterfaceHandler.register(Interface.POTION_DECANTING, h -> {
            h.actions[3] = (SimpleAction) p -> Potion.decant(p, 1);
            h.actions[4] = (SimpleAction) p -> Potion.decant(p, 2);
            h.actions[5] = (SimpleAction) p -> Potion.decant(p, 3);
            h.actions[6] = (SimpleAction) p -> Potion.decant(p, 4);
        });
    }
}
