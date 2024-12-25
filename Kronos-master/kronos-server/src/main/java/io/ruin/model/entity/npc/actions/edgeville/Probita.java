package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.impl.Pet;

public class Probita {

    static {
        for(Pet pet : Pet.values()) {
            ItemNPCAction.register(pet.itemId, 5906, (player, item, npc) -> {
                player.dialogue(
                        new NPCDialogue(npc, "That's a cute pet! Can I pet them?"),
                        new NPCDialogue(pet.npcId, "Grr!"),
                        new NPCDialogue(npc, "Oh, I guess not!")
                );
            });
        }
        NPCAction.register(5906, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "Hello, how can I help you?"),
                    new OptionsDialogue(
                            new Option("Where can I get pets from?", () -> {
                                player.dialogue(
                                        new PlayerDialogue("Where can I get pets from?"),
                                        new NPCDialogue(npc, "The quickest way to get your hands on a pet would be by opening pet mystery boxes! I've also heard rumors that kittens sneak into the voting mystery boxes, yuck!"),
                                        new PlayerDialogue("Thanks!")
                                );
                            }),
                            new Option("What happens if I die with a pet?", () -> {
                                player.dialogue(
                                        new PlayerDialogue("What happens if I die with a pet?"),
                                        new NPCDialogue(npc, "No matter what your pet will always return to you!"),
                                        new PlayerDialogue("Wow, that's great to hear. Thanks!")
                                );
                            }),
                            new Option("Nevermind")
                    )
            );
        });
    }

}
