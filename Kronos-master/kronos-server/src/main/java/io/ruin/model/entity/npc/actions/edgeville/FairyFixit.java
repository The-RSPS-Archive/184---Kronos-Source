package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;

public class FairyFixit {

    static {
        NPCAction.register(7333, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Pssst! Human! I've got something for you."),
                new OptionsDialogue(
                        new Option("What have you got for me?", () -> player.dialogue(
                                new PlayerDialogue("What have you got for me?"),
                                new NPCDialogue(npc, "I've got a staff and enchantment scrolls which might help if you're working with fairy rings."),
                                new ActionDialogue(() -> ShopManager.openIfExists(player, ""))
                        )),
                        new Option("Why are you carrying that toolbox?", () -> player.dialogue(
                                new PlayerDialogue("Why are you carrying that toolbox?"),
                                new NPCDialogue(npc, "It's the fizgog! It's picking up cable again!"),
                                new PlayerDialogue("Uh, right. So is it safe to use the fairy rings then?"),
                                new NPCDialogue(npc, "Sure, as long as you have a dramen staff! You should be aware that using the fairy rings sometimes has strange " +
                                        "results, the locations that you have been to may affect the locations you are"),
                                new NPCDialogue(npc, "trying to reach. I could fix it by replacing the fizgog and the whosprangit, I've put in a request for some new" +
                                        " parts but they're pretty hard to get ahold of it seems.")
                        )),
                        new Option("Not interested, thanks.", () -> player.dialogue(new PlayerDialogue("Not interested, thanks.")))
                )
        ));
    }
}
