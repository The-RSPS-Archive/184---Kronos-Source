package io.ruin.model.entity.npc.actions.zanaris;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class FairyAeryka {

    public final static int FAIRY_AERYKA = 5736;

    private static void whatIsPuroPuro(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("What's in Puro-Puro?"),
                new NPCDialogue(npc, "Implings...and wheat."),
                new PlayerDialogue("Erm, anything else?"),
                new NPCDialogue(npc, "Not really. Though I have noticed quite a lot of humans travelling through the portal recently. " +
                        "I suppose you must like wheat."),
                new PlayerDialogue("Well, most of us prefer lobster to be perfectly honest, but there must be something interesting there."),
                new NPCDialogue(npc, "Oh, I did notice a very serious-looking gnome go into the portal. Maybe he knows what's going on."),
                new PlayerDialogue("Do y ou remember what his name was?"),
                new NPCDialogue(npc, "Errm, Ehh-nog, or something like that."),
                new PlayerDialogue("Right, thanks! i'll have a chat with him when I go. Maybe he'll know what's going on."),
                new OptionsDialogue(
                        new Option("So what are these implings then?", () -> whatAreImplings(player, npc)),
                        new Option("I've heard I may find dragon equipment in Puro-Puro.", () -> iveHeard(player, npc)),
                        new Option("No, bye!", () -> noBye(player, npc))
                )
        );
    }

    private static void whatAreImplings(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("So what are these implings then?"),
                new NPCDialogue(npc, "Well, no-one knows for sure. The mischievous little creatures are probably related to imps. And they fly as well."),
                new NPCDialogue(npc, "Also, like imps, they love collecting things. I'm not sure why though. They also seem to like being chased."),
                new PlayerDialogue("So how would I get a hold of what they are carrying, then?"),
                new NPCDialogue(npc, "Catch them, I suppose. I don't know really. Why would you want to?"),
                new PlayerDialogue("Well, if they were carrying something useful. Maybe I could catch them with a bit net - like butterflies."),
                new NPCDialogue(npc, "Sounds a bit cruel to me, but I suppose that's possible."),
                new OptionsDialogue(
                        new Option("What's in Puro-Puro?", () -> whatIsPuroPuro(player, npc)),
                        new Option("I've heard I may find dragon equipment in Puro-Puro.", () -> iveHeard(player, npc)),
                        new Option("No, bye!", () -> noBye(player, npc))
                )
        );
    }

    private static void noBye(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("No, bye!"),
                new NPCDialogue(npc, "See you around!")
        );
    }

    private static void iveHeard(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("I've heard I may find dragon equipment in Puro-Puro."),
                new NPCDialogue(npc, "Really? You humans like that stuff a lot, don't you? I don't really old stuff myself."),
                new PlayerDialogue("Old?"),
                new NPCDialogue(npc, "Yes, dragon stuff feels really old."),
                new PlayerDialogue("How can you tell that?"),
                new NPCDialogue(npc, "From its magical aura, obviously. Oh, I forget you humans can't feel auras."),
                new PlayerDialogue("How old is old?"),
                new NPCDialogue(npc, "Really, really, old."),
                new PlayerDialogue("Can you be any more precise?"),
                new NPCDialogue(npc, "Not really. Time doesn't really mean a lot around here. Hundreds, maybe thousands of your human lifespans, I suppose. " +
                        "Anyway, it would have to be old since it all comes from Necrosyrtes."),
                new PlayerDialogue("Necrosyrtes? Who are they?"),
                new NPCDialogue(npc, "Old and powerful creatures. I don't think there have been any around here for aeons. I haven't seen one. I don't think they are very nice. " +
                        "Not like me. Anyway, this is all ancient history. Boooring!"),
                new PlayerDialogue("While I'm on subject do you have any dragon stuff I can have?"),
                new NPCDialogue(npc, "Oh, I did have loads, but I threw it away."),
                new PlayerDialogue("What?"),
                new NPCDialogue(npc, "Only joking."),
                new NPCDialogue(npc, "No, sorry. I can't help you there. You'll have to look for it yourself. Although, maybe if you find Necosyrtes then they'll give you some. I heard" +
                        " they give dragon stuff away to people they like. Not fairies. We're too"),
                new NPCDialogue(npc, "nice."),
                new OptionsDialogue("Is there anything else you want to ask?",
                        new Option("What's in Puro-Puro?", () -> whatIsPuroPuro(player, npc)),
                        new Option("So what are these implings then?", () -> whatAreImplings(player, npc)),
                        new Option("No, bye!", () -> noBye(player, npc))
                )
        );
    }

    static {
        NPCAction.register(FAIRY_AERYKA, "talk-to", (player, npc) -> {
            if(player.fairyAerykaDialogueStarted) {
                player.dialogue(
                        new NPCDialogue(npc, "It's still here."),
                        new PlayerDialogue("Pardon?"),
                        new NPCDialogue(npc, "It's still here. The crop circle is still here."),
                        new PlayerDialogue("Oh yes, thanks Aery. It didn't go anywhere in the meantime, then?"),
                        new NPCDialogue(npc, "Nope. It just sat there."),
                        new PlayerDialogue("Jolly good. I can come back and visit Puro-Puro whenever I want then. Brilliant!"),
                        new OptionsDialogue(
                                new Option("What's in Puro-Puro?", () -> whatIsPuroPuro(player, npc)),
                                new Option("So what are these implings then?", () -> whatAreImplings(player, npc)),
                                new Option("I've heard I may find dragon equipment in Puro-Puro.", () -> iveHeard(player, npc)),
                                new Option("No, bye!", () -> noBye(player, npc))
                        )
                );
            } else {
                player.dialogue(
                        new PlayerDialogue("Hello, my name is " + player.getName() + ". Who are you?"),
                        new NPCDialogue(npc, "Oh hello " + player.getName() + ". I'm Aeryka. Aery for short."),
                        new PlayerDialogue("Airy Fairy?"),
                        new NPCDialogue(npc, "That's right. What can I do for you?"),
                        new PlayerDialogue("What's that crop circle thing doing here?"),
                        new NPCDialogue(npc, "Crop circle? Oh, you mean the Puromatic portal?"),
                        new PlayerDialogue("The pyromatic what?"),
                        new NPCDialogue(npc, "The Puromatic portal. At least that's what we call them. It's the way the implings travel from Puro-Puro to other planes."),
                        new PlayerDialogue("Puro-Puro?").action(() -> player.fairyAerykaDialogueStarted = true),
                        new NPCDialogue(npc, "The impling home. We call it Puro-Puro. The implings just call it home, I think."),
                        new OptionsDialogue("Is there anything else you want to ask?",
                                new Option("What's in Puro-Puro?", () -> whatIsPuroPuro(player, npc)),
                                new Option("So what are these implings then?", () -> whatAreImplings(player, npc)),
                                new Option("I've heard I may find dragon equipment in Puro-Puro.", () -> iveHeard(player, npc)),
                                new Option("No, bye!", () -> noBye(player, npc))
                        )
                );
            }
        });
    }
}
