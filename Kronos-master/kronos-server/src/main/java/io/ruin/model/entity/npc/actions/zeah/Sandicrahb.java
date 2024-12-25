package io.ruin.model.entity.npc.actions.zeah;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;

import static io.ruin.cache.ItemID.COINS_995;

public class Sandicrahb {
    static {
        NPCAction go = (player, npc) -> {
            if (!player.getInventory().contains(COINS_995, 10000)) {
                player.dialogue(new NPCDialogue(npc, "I can take you to Crabclaw Isle, but it'll cost you 10,000 coins."));
            } else {
                player.getInventory().remove(COINS_995, 10000);
                player.startEvent(event -> {
                    player.lock(LockType.FULL_DELAY_DAMAGE);
                    player.getPacketSender().fadeOut();
                    event.delay(2);
                    player.getMovement().teleport(1781, 3412, 0);
                    player.dialogue(new MessageDialogue("You arrive at Crabclaw Isle."));
                    player.getPacketSender().fadeIn();
                    event.delay(1);
                    player.unlock();
                });
            }
        };

        NPCAction leave = (player, npc) -> {
            player.startEvent(event -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.getPacketSender().fadeOut();
                event.delay(2);
                player.getMovement().teleport(1783, 3459, 0);
                player.dialogue(new MessageDialogue("You return to shore."));
                player.getPacketSender().fadeIn();
                event.delay(1);
                player.unlock();
            });
        };
        NPCAction.register(7483, "talk-to", go); // TODO dialogue
        NPCAction.register(7483, "quick-travel", go);

        NPCAction.register(7484, "leave-island", leave); // TODO dialogue?
        NPCAction.register(7484, "quick-travel", leave);



    }

}
