package io.ruin.model.map.object.actions.impl.tzhaar;

import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.object.actions.ObjectAction;

public class HotVentDoor {

    static {
        ObjectAction.register(30266, "pass", (player, obj) -> {
            if (player.canEnterMorUlRek) {
                player.startEvent(event -> {
                    player.lock();
                    if (obj.direction == 0)
                        player.stepAbs(obj.x, obj.y + (player.getAbsY() > obj.y ? -1 : 1), StepType.FORCE_WALK);
                    else
                        player.stepAbs(obj.x + (player.getAbsX() > obj.x ? -1 : 1), obj.y, StepType.FORCE_WALK);
                    event.delay(2);
                    player.unlock();
                });
            } else {
                if (player.getInventory().hasId(6570) || player.getEquipment().hasId(6570)) {
                    player.dialogue(
                            new NPCDialogue(2187, "Oy! Get back from there, no JalYt allowed through."),
                            new PlayerDialogue("I managed to defeat TzTok-Jad and obtain this fire cape."),
                            new ItemDialogue().one(6570, "You hold out your fire cape and show it to TzHaar-Ket."),
                            new NPCDialogue(2187, " That is most impressive JalYt-Ket-" + player.getName() + "."),
                            new PlayerDialogue("Surely this proves I am capable? Can I pleeease come through now?"),
                            new NPCDialogue(2187, "I suppose so, I'll grant you access to Mor Ul Rek. The guards will open the gates for you, you are the first JalYt to pass these gates!"),
                            new ActionDialogue(() -> player.canEnterMorUlRek = true));
                } else {
                    player.dialogue(new NPCDialogue(2187, "Oy! Get back from there, no JalYt allowed through."));
                }
            }
        });
    }
}
