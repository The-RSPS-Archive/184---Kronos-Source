package io.ruin.model.entity.npc.actions;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;

public class TwiggyOKorn {

    static {
        NPCAction.register(5527, "talk-to", (player, npc) -> player.dialogue(
                new PlayerDialogue("Hello, what are you?").action(() -> npc.faceTemp(player)),
                new NPCDialogue(npc, "Hi there, my name is Twiggy O'Korn and I'm the Master of Achievements here at " + World.type.getWorldName() + "."),
                new OptionsDialogue(
                        new Option("What is an achievement?", () -> player.dialogue(
                                new PlayerDialogue("What is an achievement?").animate(554),
                                new NPCDialogue(npc, "They're an ever growing set of challenges for you to fulfill which I " +
                                        "often reward for completing."),
                                new NPCDialogue(npc, "These challenges involve doing all sorts of random stuff, varying in different difficulties. " +
                                        "Some might have you jumping from roof to roof, while another might just have you cutting down trees."),
                                new PlayerDialogue("Okay.. how can I view the challenges?"),
                                new NPCDialogue(npc, "To view the achievements, simply head to your quest tab and click on the green icon near the top. " +
                                        "You'll see they're all sorted according to difficulty."),
                                new NPCDialogue(npc, "Achievements that've yet to be started will be in <col=FF0000>red</col>, in progress will be <col=ffff00>yellow</col>, and completed " +
                                        "will be <col=00FF00>green</col>."),
                                new NPCDialogue(npc, "When you start or complete an achievement, you'll receive a notification in your chat box. If it's rewarded, come find me!"),
                                new PlayerDialogue("Great, thanks!")
                        )),
                        new Option("Do you sell anything?", () -> player.dialogue(
                                new PlayerDialogue("Do you sell anything?"),
                                new NPCDialogue(npc, "Yes, but only if you've completed the corresponding achievement. You can claim your reward in my shop "),
                                new NPCDialogue(npc, "but if you lose it, you'll have to purchase it back!"),
                                new NPCDialogue(npc, "Would you like to view the achievement rewards shop?"),
                                new OptionsDialogue("View the Achievement Rewards?",
                                        new Option("Yes", () -> ShopManager.openIfExists(player, "")),//TODO Fill this in
                                        new Option("No", player::closeDialogue)
                                )
                        )),
                        new Option("Can you tell me about your cape?", () -> player.dialogue(
                                new PlayerDialogue("I really like your cape!"),
                                new NPCDialogue(npc, "Thank you! This is the achievement cape."),
                                new PlayerDialogue("How would I go about getting one for myself?"),
                                new NPCDialogue(npc, "You'd have to complete every achievement! Once you've managed that, I'll be able to give you a cape of your very own."),
                                new PlayerDialogue("I see.. so I can't just wear it anyways?"),
                                new NPCDialogue(npc, "That is correct!"),
                                new PlayerDialogue("Awww..")
                        ))
                )
        ));
    }
}
