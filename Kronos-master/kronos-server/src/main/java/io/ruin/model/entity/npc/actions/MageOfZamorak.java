package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.Random;
import io.ruin.model.achievements.listeners.novice.IntoTheAbyss;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.skills.runecrafting.Abyss;

public class MageOfZamorak {

    private static final int MAGE_OF_ZAMORAK = 2581;

    static {
        NPCAction.register(MAGE_OF_ZAMORAK, "talk-to", (player, npc) -> {
            player.dialogue(new NPCDialogue(npc, "This is no place to talk! Meet me at the Varrock Chaos Temple!").animate(557));
        });
        NPCAction.register(MAGE_OF_ZAMORAK, "teleport", ((player, npc) ->
                player.startEvent(event -> {
                    player.lock(LockType.FULL_NULLIFY_DAMAGE);
                    npc.lock();
                    npc.faceTemp(player);
                    npc.forceText("Veniens! Sallakar! Rinnesset!");
                    npc.animate(1818, 1);
                    npc.graphics(343, 100, 1);
                    event.delay(2);
                    player.unlock();
                    player.getCombat().skullNormal();
                    player.getPrayer().drain(1000); //1000 is just a safe "drain all"
                    Abyss.randomize(player);
                    IntoTheAbyss.entered(player);
                    player.getMovement().teleport(Random.get(Abyss.OUTER_TELEPORTS));
                    player.resetAnimation();
                    npc.unlock();
                }
        )));
    }
}
