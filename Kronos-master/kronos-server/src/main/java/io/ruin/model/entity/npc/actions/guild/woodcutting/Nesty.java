package io.ruin.model.entity.npc.actions.guild.woodcutting;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

public class Nesty {

    static {
        NPCAction.register(7321, "talk-to", (player, npc) -> player.dialogue(
                new PlayerDialogue("Hello! What's this then?"),
                new NPCDialogue(npc, "How can you not know? YOU MUST KNOW! It is of course the shrine!").animate(608),
                new PlayerDialogue("Okay... what is the purpose of said shrine?"),
                new NPCDialogue(npc, "Do you not listen? It is the shrine to almighty one! BWAAAAAK!").animate(608),
                new PlayerDialogue("You know, you're making me worried, are you okay?"),
                new NPCDialogue(npc, "DO NOT defy him! Give him your eggs! So colorful! So beautiful!").animate(608),
                new PlayerDialogue("I'm going to leave you alone now.")
        ));
    }
}
