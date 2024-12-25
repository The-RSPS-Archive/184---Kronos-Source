package io.ruin.model.entity.npc.actions.zanaris.puropuro;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

public class Impling {

    private final static int IMPLING = 5735;

    static {
        NPCAction.register(IMPLING, "talk-to", (player, npc) -> player.dialogue(
                new PlayerDialogue("Hello. So what is this place?"),
                new NPCDialogue(npc, "This is my home, mundane human! What do you have in your pockets? Something tasty?"),
                new PlayerDialogue("Stay out of my pockets! I don't have anything that you want."),
                new NPCDialogue(npc, "Ah, but do you have anything that *you* want?"),
                new PlayerDialogue("Of course I do!"),
                new NPCDialogue(npc, "Then you have something that implings want."),
                new PlayerDialogue("Eh?"),
                new NPCDialogue(npc, "We want things you people want. They are tasty to us! The more you want them, the tastier they are!"),
                new PlayerDialogue("So, you collect things that humans want? Interesting... So, what would happen if I caught an impling in a butterfly net?"),
                new NPCDialogue(npc, "Don't do that! That would be cruel. But chase us, yes! That is good. Implings are not easy to catch. Especially" +
                        " ones with really tasty food."),
                new PlayerDialogue("So, some of these implings have things that I will really want? Hmm, maybe it would be worth my while trying to catch some.")
        ));
    }
}
