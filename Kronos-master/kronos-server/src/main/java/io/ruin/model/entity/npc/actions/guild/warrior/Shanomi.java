package io.ruin.model.entity.npc.actions.guild.warrior;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;

public class Shanomi {

    private static final String[] SHOUT = {
            "Those things which cannot be seen, perceive them.",
            "Do nothing which is of no use.",
            "Think not dishonestly.",
            "The Way in training is.",
            "Gain and loss between you must distinguish.",
            "Trifles pay attention even to.",
            "Way of the warrior this is.",
            "Acquainted with every art become.",
            "Ways of all professions know you."};


    static {
        NPCAction.register(2462, "talk-to", (player, npc) -> {
            npc.faceTemp(player);
            player.dialogue(
                    new NPCDialogue(npc, "Greetings, " + player.getName() + ". Welcome you are in the test of combat."),
                    new OptionsDialogue(
                            new Option("What do I do here?", () -> player.dialogue(
                                    new PlayerDialogue("What do I do here?"),
                                    new NPCDialogue(npc, "A spare suit of plate armour need you will. Full helm, plate leggings and platebody, yes? Placing it in the centre of the magical machines you will" +
                                            " be doing. KA-POOF! The armour, it attack most furiously as if").animate(590),
                                    new NPCDialogue(npc, "alive! Kill it you must, yes.").animate(590),
                                    new PlayerDialogue("So I use a full set of armour on the centre plate of the machines and it will animate it? Then I have to kill my our armour... how bizarre!"),
                                    new NPCDialogue(npc, "Yes. It is as you are saying. For this earn tokens you will. Also gain experience in combat you will. Trained long and here here have I.").animate(590),
                                    new PlayerDialogue("You're not from around here are you...?"),
                                    new NPCDialogue(npc, "It is as you say.").animate(590),
                                    new PlayerDialogue("So will I lose my armour?"),
                                    new NPCDialogue(npc, "Lose armour you will if damaged too much it becomes. Rare this is, but still possible. If kill you the armour does, also lose armour you will.").animate(590),
                                    new PlayerDialogue("So, occasionally I might lose a bit because it's being based about and I'll obviously lose it if I die.. that it?"),
                                    new NPCDialogue(npc, "It is as you say.").animate(590)
                            )),
                            new Option("Where do the machines come from?", () -> player.dialogue(
                                    new PlayerDialogue("Where do the machines come from?"),
                                    new NPCDialogue(npc, "Make them I did, with magics.").animate(590),
                                    new PlayerDialogue("Magic, in the Warrior's Guild?"),
                                    new NPCDialogue(npc, "A skilled warrior also am I. Harrallak mistakes does not make. Potential in my invention he sees and opportunity grasps.").animate(590),
                                    new PlayerDialogue("I see, so you made the magical machines and Harrallak saw how they could be used in the guild to train warrior's combat... interesting." +
                                            "Harrallak certainly is an intelligent guy."),
                                    new NPCDialogue(npc, "It is as you say.").animate(590)
                            )),
                            new Option("What items can I get from here?", () -> ShopManager.openIfExists(player, "")),//TODO Fill this in
                            new Option("Bye", () -> player.dialogue(
                                    new PlayerDialogue("Bye!"),
                                    new NPCDialogue(npc, "Bye!").animate(590)))
                    )
            );
        });

        SpawnListener.register(2462, npc -> npc.addEvent(e -> {
            while (true) {
                e.delay(Random.get(5, 10));
                npc.forceText(Random.get(SHOUT));
            }
        }));
    }
}
