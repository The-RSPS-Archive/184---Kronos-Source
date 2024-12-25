package io.ruin.model.entity.npc.actions.tzhaar;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

public class TzHaarMejDir {

    static {
        NPCAction.register(7680, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Hello JalYt-Ket-Xo-" + player.getName() + "."),
                new PlayerDialogue("Hello. What's happening here?"),
                new NPCDialogue(npc, "This is TzHaar terminal chamber, TzHaar rest here to draw warmth from the volcano."),
                new PlayerDialogue("Why is that?"),
                new NPCDialogue(npc, "As TzHaar grow older we lose heat and begin to solidify. Drawing heat from the volcano allows us to prolong this process."),
                new PlayerDialogue("What happens when TzHaar solidify?"),
                new NPCDialogue(npc, "Over time TzHaar will eventually harden and cease to be molten entirely before turning to rock, known as Haar-Tok."),
                new PlayerDialogue("Are Haar-Tok dead?"),
                new NPCDialogue(npc, "In essence, yes, as Haar-Tok can not move or communicate but the former TzHaar remains conscious."),
                new PlayerDialogue("Intriguing, thank you.")
        ));
    }
}
