package io.ruin.model.entity.npc.actions.dungeons;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class Taverly {


    static {
        /**
         * Slieve
          */
        NPCAction.register(7653, "talk-to", (player, npc) -> {
            player.dialogue(
                    new NPCDialogue(npc, "Hello. Are you here for my black dragons?"),
                    new OptionsDialogue(
                            new Option("Why are you here asking about that?", () -> player.dialogue(
                                    new PlayerDialogue("Why are you here asking about that?"),
                                    new NPCDialogue(npc, "Because I like my black dragons, and I had to work hard to get access to their " +
                                            "lair, and I don't want people using them for training without a reason."),
                                    new NPCDialogue(npc, "So if one of the Slayer Masters sent you to slay black dragons, that's fine, but otherwise " +
                                            "you should go and train elsewhere."),
                                    new NPCDialogue(npc, "I'd rather people didn't train on my dragons at all, but I've got a distant cousin who's a Slayer " +
                                            "Master, so I've got to support the family business."),
                                    new PlayerDialogue("Oh, okay. I won't kill them unless Black Dragons are my task. Thanks!")
                            )),
                            new Option("Excuse me, I've got to go.", () -> player.dialogue(new PlayerDialogue("Excuse me, I've got to go.")))
                    )
            );
        });

        /**
         * Key master
         */
        NPCAction.register(5870, "talk-to", (player, npc) -> player.dialogue(
                new PlayerDialogue("Hello."),
                new NPCDialogue(npc, "Who goes there?"),
                new NPCDialogue(npc, "This is no place for a human. You need to leave."),
                new PlayerDialogue("Why?"),
                new NPCDialogue(npc, "The voices! The voices in my head!"),
                new PlayerDialogue("You're starting to scare me man..."),
                new NPCDialogue(npc, "I am no man! They changed me and cursed me to<br>remain here..."),
                new OptionsDialogue(
                        new Option("What do you mean they changed you?", () -> player.dialogue(
                                new NPCDialogue(npc, "I was once a free man, powerful and wealthy. I owned<br>several apothecaries across Zeah and sold the tastiest<br>potions in the land."),
                                new PlayerDialogue("What happened?"),
                                new NPCDialogue(npc, "One of my greatest inventions, it was going so well. I<br>spent days finding the right herbs, I travelled across all<br>of Zeah to find" +
                                        " the most exotic weeds. Once I had<br>gathered them all, I put them in a potion and mixed in"),
                                new NPCDialogue(npc, "The final ingredient."),
                                new PlayerDialogue("Zeah? Interesting..."),
                                new NPCDialogue(npc, "Yes, yes... The potion tasted delicious but it was missing<br>a tiny something so I added Magic roots to the potion.<br>It started to pulsate and glow!" +
                                        " I took a sip and I felt<br>like a million gold! A few seconds later my eye sight"),
                                new NPCDialogue(npc, "began to blur, my brain was throbbing. I fell and hit<br>my head on my worktop, then it all went black."),
                                new PlayerDialogue("Ouch! But, you still haven't said who changed you?"),
                                new NPCDialogue(npc, "Will you let me finish??"),
                                new NPCDialogue(npc, "I woke up screaming in pain. Blue foam streaming out<br>of my mouth, my eye sight worse than before, the only<br>things I could make out were 3 tall figures with" +
                                        " green<br>banners - they were of the Arceuus Elders. They"),
                                new NPCDialogue(npc, "muttered to each other in Archaic Language after<br>trying to get up several times. I lost hope and stared at<br>the sky. I had given up when the tallest of the figures<br>" +
                                        "bent over and brought his face right to mine and spoke"),
                                new NPCDialogue(npc, "very softly 'We can save you, but it will come at a<br>cost'."),
                                new PlayerDialogue("So they saved you?"),
                                new NPCDialogue(npc, "Yes of course they saved me but look at the cost! I<br>have horns coming out of my head and I'm still blind<br>and stuck here... forever alone.")
                        )),
                        new Option("What as the curse?", () -> player.dialogue(
                                new NPCDialogue(npc, "I have been charged to stay here to prevent the<br>Monstrosity from escaping. Those gates and the<br>winches that operate them are the only thing that stops<br>" +
                                        "it breaking free."),
                                new PlayerDialogue("What monstrosity? What's behind those gates?"),
                                new NPCDialogue(npc, "You really do not want to know. Leave this place<br>human."),
                                new PlayerDialogue("Hey now, I'm no wimp. What's to stop me from just<br>turning the winch to open the gate and going in?"),
                                new NPCDialogue(npc, "Me. And of course your quick demise at the mercy of<br>Cerberus, guardian of the river of souls."),
                                new PlayerDialogue("But I can do it!"),
                                new NPCDialogue(npc, "You are obviously passionate at trying. But only those<br>with great skill at slaying these types of beast may<br>enter."),
                                new PlayerDialogue("I have been charged by the slayer masters to eliminate<br>this type of threat."),
                                new NPCDialogue(npc, "Then you may pass... may your soul not end up<br>consumed and forever condemned to the-"),
                                new NPCDialogue(npc, "THE VOICES! AAAHHHHH! Leave this place human!")
                        )),
                        new Option("Goodbye", () -> player.dialogue(new PlayerDialogue("Goodbye.")))
                )
        ));
    }
}
