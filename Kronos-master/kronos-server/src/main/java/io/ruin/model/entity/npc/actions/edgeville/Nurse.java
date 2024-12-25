package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;

public class Nurse {

    public static void heal(Player player, NPC npc) {
        player.resetFreeze();
        player.resetStun();
        player.getStats().restore(false);
        player.getMovement().restoreEnergy(100);
        player.cureVenom(0);

        if(npc != null) {
            npc.faceTemp(player);
            npc.animate(881);
            player.dialogue(new NPCDialogue(npc, "There you go, you should be all set. Stay safe out there now."));
        }
        player.graphics(436, 48, 0);
        player.privateSound(958);
        if(player.nurseSpecialRefillCooldown.isDelayed() && !player.isAdmin()) {
            player.sendMessage("The nurse can only refill your special attack every minute.");
            return;
        }
        Config.SPECIAL_ENERGY.set(player, 1000);
        player.nurseSpecialRefillCooldown.delaySeconds(60);
    }

    static {
        NPCAction.register(3343, "talk-to", (player, npc) -> player.dialogue(
                new PlayerDialogue("Hey there, what do you do?"),
                new NPCDialogue(npc, "I'm the local nurse! Bruises, scatches, cuts, or scrapes, I can fix them all! Just let me know where it hurts and I'll do my very best."),
                new OptionsDialogue(
                        new Option("I'm hurt, can you heal me?", () -> player.dialogue(
                                new PlayerDialogue("I'm hurt, can you heal me?"),
                                new NPCDialogue(npc, "Of course. Just stand still for a second.."),
                                new ActionDialogue(() -> heal(player, npc))
                        )),
                        new Option("I'm okay for now.", () -> player.dialogue(
                                new PlayerDialogue("I'm okay for now."),
                                new NPCDialogue(npc, "See you next time!")
                        ))
                )
        ));
        NPCAction.register(3343, "heal", Nurse::heal);
    }

}
