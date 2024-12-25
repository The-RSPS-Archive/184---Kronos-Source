package io.ruin.model.skills.fishing.lakemolch;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;

public class AlryTheAngler {

    private static final int ARLY_THE_ANGLER = 8521;
    private static final int CORMORANTS_GLOVE_BIRD = 22817;

    static {
        NPCAction.register(ARLY_THE_ANGLER, "talk-to", (player, npc) -> player.dialogue(
                new PlayerDialogue("Hello there."),
                new NPCDialogue(ARLY_THE_ANGLER, "What brings you to these parts, stranger?"),
                new OptionsDialogue(
                        new Option("What is this place?", () -> {
                            player.dialogue(new PlayerDialogue("What is this place?"),
                                    new NPCDialogue(ARLY_THE_ANGLER, "This is Lake Molch! I train cormorants here and sell them to the locals."),
                                    new PlayerDialogue("Oh.. well that's interesting!"));
                        }),
                        new Option("Could I have a go with the bird?", () -> getBird(player, npc))
                )
        ));
        NPCAction.register(ARLY_THE_ANGLER, "get bird", AlryTheAngler::getBird);
    }

    private static void getBird(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Can I have a go with your bird?"),
                new ActionDialogue(() -> {
                    Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
                    if (weapon != null && weapon.getId() == CORMORANTS_GLOVE_BIRD || player.getInventory().hasId(CORMORANTS_GLOVE_BIRD)) {
                        player.dialogue(new NPCDialogue(ARLY_THE_ANGLER, "You.. you already have my bird."));
                    } else {
                        player.dialogue(new NPCDialogue(ARLY_THE_ANGLER, "I don't know, I doubt that your type's up to the task..."),
                                new NPCDialogue(ARLY_THE_ANGLER, "But it would be quite the amusing sight. Go on!"),
                                new ActionDialogue(() -> {
                                    if (freeHands(player)) {
                                        player.getEquipment().set(Equipment.SLOT_WEAPON, new Item(CORMORANTS_GLOVE_BIRD, 1));
                                        player.dialogue(
                                                new MessageDialogue("Arly gives you a large leather glove and summons a cormorant which lands on it."),
                                                new NPCDialogue(ARLY_THE_ANGLER, "I'll keep an eye on you and make sure you don't have too much trouble with her!"));
                                    } else {
                                        player.dialogue(new NPCDialogue(ARLY_THE_ANGLER, "You're going to need to free up your hands first, though. Nothing in or on your hands."));
                                    }
                                })
                        );
                    }
                })
        );
    }

    private static boolean freeHands(Player player) {
        Item gloves = player.getEquipment().get(Equipment.SLOT_HANDS);
        Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
        Item shield = player.getEquipment().get(Equipment.SLOT_SHIELD);
        return gloves == null && weapon == null && shield == null;
    }

}
