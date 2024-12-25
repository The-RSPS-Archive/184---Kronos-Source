package io.ruin.model.entity.npc.actions.wintertodt;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;

public class WinterSoldier {

    static {
        NPCAction.register(7379, "talk-to", (player, npc) -> {
            int random = Random.get(3);
            if (random == 0)
                player.dialogue(new NPCDialogue(npc, "I'm hungry...").animate(610));
            if (random == 1)
                player.dialogue(new NPCDialogue(npc, "It's so cold...").animate(610));
            if (random == 2)
                player.dialogue(new NPCDialogue(npc, "This could be worse I guess. Oh wait... No it can't.").animate(610));
            if (random == 3)
                player.dialogue(new NPCDialogue(npc, "Sometimes I wish this thing would just escape. At least<br>we'd have something to do then.").animate(610));
        });
    }
}
