package io.ruin.model.entity.npc.actions.wintertodt;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class Ignisia {

    private static void aboutWintertodt(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about the Wintertodt."),
                new NPCDialogue(npc, "Do you feel the winds of winter as they lick across the<br>land? This is the power of the Wintertodt. We know" +
                        "<br>not what it is nor from where it came. Only that it<br>wields the power of Winter itself.").animate(591),
                new NPCDialogue(npc, "The ground on which we stand was once full of light,<br>full of life. But then the Wintertodt came. Now the" +
                        "<br>fires of these lands burn no more.").animate(590),
                new NPCDialogue(npc, "How else may I be of assistance?"),
                new OptionsDialogue(
                        new Option("Tell me about the Pyromancers.", () -> aboutPyromancers(player, npc)),
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("What can I do to help?", () -> doToHelp(player, npc)),
                        new Option("I'm fine thanks.", () -> fineThanks(player, npc))
                )
        );
    }

    private static void aboutPyromancers(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about the Pyromancers."),
                new NPCDialogue(npc, "We are the Order of the Sacred Flame, formed over a<br>thousand years ago when the Wintertodt first" +
                        "<br>threatened Great Kourend."),
                new NPCDialogue(npc, "We fought against the Wintertodt until we managed to<br>lure it behind Dinh's great doors. Our task did not end" +
                        "<br>there though, for we always knew that the Wintertodt<br>would rise again."),
                new NPCDialogue(npc, "How else may I be of assistance?"),
                new OptionsDialogue(
                        new Option("Tell me about the Wintertodt.", () -> aboutWintertodt(player, npc)),
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("What can I do to help?", () -> doToHelp(player, npc)),
                        new Option("I'm fine thanks.", () -> fineThanks(player, npc))
                )
        );
    }

    private static void aboutYourself(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about yourself."),
                new NPCDialogue(npc, "I am Ignisia, Grand Master of the Order of the Sacred<br>Flame. I have served our Order faithfully since I" +
                        "<br>passed the Trials of Fire before the Ascent of Arceuus."),
                new NPCDialogue(npc, "How else may I be of assistance?"),
                new OptionsDialogue(
                        new Option("Tell me about the Wintertodt.", () -> aboutWintertodt(player, npc)),
                        new Option("Tell me about the Pyromancers.", () -> aboutPyromancers(player, npc)),
                        new Option("What can I do to help?", () -> doToHelp(player, npc)),
                        new Option("I'm fine thanks.", () -> fineThanks(player, npc))
                )
        );
    }

    private static void doToHelp(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("What can I do to help?"),
                new NPCDialogue(npc, "We must hold back the Wintertodt until we can find a<br>way to defeat it. Head into the prison and keep the holy" +
                        "<br>fires lit so that our Order may keep the evil at bay."),
                new PlayerDialogue("How can I do that?"),
                new NPCDialogue(npc, "You will find the roots of the great Bruma tree have<br>crept into the prison. Chop the roots and use them to" +
                        "<br>keep the sacred flame alive."),
                new PlayerDialogue("Anything else?"),
                new NPCDialogue(npc, "You can use the herbs of the Bruma tree to make<br>Rejuvination potions. Give these to any of our order" +
                        "<br>who fall to the cold of the Wintertodt before they<br>succumb to the darkness."),
                new NPCDialogue(npc, "How else may I be of assistance?"),
                new OptionsDialogue(
                        new Option("Tell me about the Wintertodt.", () -> aboutWintertodt(player, npc)),
                        new Option("Tell me about the Pyromancers.", () -> aboutPyromancers(player, npc)),
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("I'm fine thanks.", () -> fineThanks(player, npc))
                )
        );
    }

    private static void fineThanks(Player player, NPC npc) {
        player.dialogue(new NPCDialogue(npc, "May the sacred flame guide you brother."));
    }

    static {
        NPCAction.register(7374, "talk-to", (player, npc) -> {
            if (player.talkedToIgnisia) {
                player.dialogue(
                        new NPCDialogue(npc, "Welcome back brother. How may I be of assistance?"),
                        new OptionsDialogue(
                                new Option("Tell me about the Wintertodt.", () -> aboutWintertodt(player, npc)),
                                new Option("Tell me about the Pyromancers.", () -> aboutPyromancers(player, npc)),
                                new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                                new Option("What can I do to help?", () -> doToHelp(player, npc)),
                                new Option("I'm fine thanks.", () -> fineThanks(player, npc))
                        )
                );
            } else {
                player.dialogue(
                        new NPCDialogue(npc, "Welcome brother."),
                        new PlayerDialogue("Err... Are we related?"),
                        new NPCDialogue(npc, "We are all related brother, for we are all children of the<br>flame."),
                        new PlayerDialogue("Okayyy...").animate(575),
                        new PlayerDialogue("So what is this place?"),
                        new NPCDialogue(npc, "You stand before the Doors of Dinh, behind them lies<br>the prison of the Wintertodt."),
                        new PlayerDialogue("What's a Wintertodt?"),
                        new NPCDialogue(npc, "We do not know what it is. All we know is that it<br>threatens to snuff out every flame in this land and" +
                                "<br>plunge the world into eternal darkness."),
                        new PlayerDialogue("Oh dear... We should probably do something about that."),
                        new NPCDialogue(npc, "That is why we are here brother. Ever since the<br>Wintertodt was imprisoned, the Order of the Sacred" +
                                "<br>Flame has stood vigil over this place."),
                        new NPCDialogue(npc, "But alas, despite our best efforts, the Wintertodt is<br>growing in power again."),
                        new PlayerDialogue("Well that's just no good. Is there anything I can do to<br>help."),
                        new NPCDialogue(npc, "Our order searches tirelessly for a way to defeat the<br>Wintertodt. Until we discover it, the Wintertodt must be" +
                                "<br>held at bay."),
                        new NPCDialogue(npc, "Pass through the Doors of Dinh to the prison within.<br>You will find the finest of our Order channeling the" +
                                "<br>power of the sacred flame into the Wintertodt."),
                        new NPCDialogue(npc, "Help them keep the holy fires lit so that they can keep<br>the Wintertodt subdued."),
                        new PlayerDialogue("How can I do that?"),
                        new NPCDialogue(npc, "You will find the roots of the great Bruma tree have<br>crept into the prison. Chop the roots and use them to" +
                                "<br>keep the sacred flame alive."),
                        new PlayerDialogue("Anything else?").action(() -> player.talkedToIgnisia = true),
                        new NPCDialogue(npc, "You can use the herbs of the Bruma tree to make<br>Rejuvination potions. Give these to any of our order" +
                                "<br>who fall to the cold of the Wintertodt before they<br>succumb to the darkness."),
                        new NPCDialogue(npc, "How else may I be of assistance?"),
                        new OptionsDialogue(
                                new Option("Tell me about the Wintertodt.", () -> aboutWintertodt(player, npc)),
                                new Option("Tell me about the Pyromancers.", () -> aboutPyromancers(player, npc)),
                                new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                                new Option("I'm fine thanks.", () -> fineThanks(player, npc))
                        )
                );
            }
        });
    }
}
