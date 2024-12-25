package io.ruin.model.entity.npc.actions.tzhaar;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

public class TzHaarKet {

    static {
        /**
         * Inside ring
         */
        NPCAction.register(7679, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Hello JalYt-Ket-Xo-" + player.getName() + "."),
                new PlayerDialogue("Hello!"),
                new NPCDialogue(npc, "You need help little JalYt?"),
                new PlayerDialogue("Not unless you can help me in The Inferno."),
                new NPCDialogue(npc, "We dare not go there. That job is for you."),
                new PlayerDialogue("Oh... worth a try I suppose.")
        ));

        /**
         * Guarding hot vent door
         */
        final int[] GUARDS = {2186, 2187};
        for (int guard : GUARDS) {
            NPCAction.register(guard, "talk-to", (player, npc) -> {
                if (player.canEnterMorUlRek) {
                    player.dialogue(
                            new NPCDialogue(npc, "You need help JalYt-Ket-Xo-" + player.getName() + "?"),
                            new OptionsDialogue(
                                    new Option("What are you guarding?", () -> player.dialogue(
                                            new PlayerDialogue("What are you guarding?"),
                                            new NPCDialogue(npc, "Ah, trusted and most capable JalYt, this is the city of Mor El Rek. It has grand central Inferno, our birthing" +
                                                    " pool. Go! See for yourself JalYt-Ket-Xo-" + player.getName() + "."),
                                            new PlayerDialogue("Thanks, goodbye.")
                                    )),
                                    new Option("No I'm fine thanks.", () -> player.dialogue(
                                            new PlayerDialogue("No I'm fine thanks.")
                                    ))
                            )
                    );
                } else {
                    player.dialogue(
                            new NPCDialogue(npc, "You need help JalYt-Ket-Xo-" + player.getName() + "?"),
                            new OptionsDialogue(
                                    new Option("Why can't I go past here?", () -> player.dialogue(
                                            new PlayerDialogue("Why can't I go past here?"),
                                            new NPCDialogue(npc, "This are closed to JalYt."),
                                            new OptionsDialogue(
                                                    new Option("Why's that?", () -> player.dialogue(
                                                            new PlayerDialogue("Why's that?"),
                                                            new NPCDialogue(npc, "JalYt are not trusted. Nor are capable of withstanding the heat. The features of the city are a secret."),
                                                            new OptionsDialogue(
                                                                    new Option("That's a shame.", () -> player.dialogue(new PlayerDialogue("That's a shame."))),
                                                                    new Option("What if I could prove I am capable?", () -> player.dialogue(
                                                                            new PlayerDialogue("What if I could prove that I am capable?"),
                                                                            new NPCDialogue(npc, "JalYt-Xil-" + player.getName() + ", I suppose that is perfectly acceptable, but it would take lots of convince."),
                                                                            new ActionDialogue(() -> {
                                                                                if (player.getInventory().hasId(6570)) {
                                                                                    player.dialogue(
                                                                                            new OptionsDialogue(
                                                                                                    new Option("How about a large stack of gold?", () -> player.dialogue(
                                                                                                            new PlayerDialogue("How about a large stack of coins?"),
                                                                                                            new NPCDialogue(npc, "Gold is bad currency, I have no use for JalYt coins.")
                                                                                                    )),
                                                                                                    new Option("I managed to defeat TzTok-Jad.", () -> player.dialogue(
                                                                                                            new PlayerDialogue("I managed to defeat TzTok-Jad and obtain this fire cape."),
                                                                                                            new ItemDialogue().one(6570, "You hold out your fire cape and show it to TzHaar-Ket."),
                                                                                                            new NPCDialogue(npc, " That is most impressive JalYt-Ket-" + player.getName() + "."),
                                                                                                            new PlayerDialogue("Surely this privates I am capable? Can I pleeease come through now?"),
                                                                                                            new NPCDialogue(npc, "I suppose so, I'll grand you access to Mor Ul Rek. The guards will open the gates for you, you are the first JalYt to pass these gates!"),
                                                                                                            new ActionDialogue(() -> player.canEnterMorUlRek = true))),
                                                                                                    new Option("Sorry, I don't think I am capable.", () -> player.dialogue(
                                                                                                            new PlayerDialogue("Sorry, I don't think I am capable.")
                                                                                                    ))
                                                                                            )
                                                                                    );
                                                                                } else {
                                                                                    player.dialogue(
                                                                                            new OptionsDialogue(
                                                                                                    new Option("How about a large stack of gold?", () -> player.dialogue(
                                                                                                            new PlayerDialogue("How about a large stack of coins?"),
                                                                                                            new NPCDialogue(npc, "Gold is bad currency, I have no use for JalYt coins.")
                                                                                                    )),
                                                                                                    new Option("Sorry, I don't think I am capable.", () -> player.dialogue(
                                                                                                            new PlayerDialogue("Sorry, I don't think I am capable.")
                                                                                                    ))
                                                                                            )
                                                                                    );
                                                                                }
                                                                            })
                                                                    )),
                                                                    new Option("I'm a hardened adventurer, nothing can stop me!", () -> player.dialogue(
                                                                            new PlayerDialogue("I'm a hardened adventurer, nothing can stop me!"),
                                                                            new NPCDialogue(npc, "Back off JalYt-Xil-" + player.getName() + ", trust me when I say our obsidian bodies are harder.")
                                                                    ))
                                                            )
                                                    )),
                                                    new Option("What are you guarding?", () -> player.dialogue(
                                                            new PlayerDialogue("What are you guarding?"),
                                                            new NPCDialogue(npc, "Ah, trusted and most capable JalYt, this is the city of Mor El Rek. It has grand central Inferno, our birthing" +
                                                                    " pool. Go! See for yourself JalYt-Ket-Xo-" + player.getName() + "."),
                                                            new PlayerDialogue("Thanks, goodbye.")
                                                    )),
                                                    new Option("Fair enough.", () -> player.dialogue(new PlayerDialogue("Fair enough.")))
                                            )
                                    )),
                                    new Option("What are you guarding?", () -> player.dialogue(
                                            new PlayerDialogue("What are you guarding?"),
                                            new NPCDialogue(npc, "Ah, trusted and most capable JalYt, this is the city of Mor El Rek. It has grand central Inferno, our birthing" +
                                                    " pool. Go! See for yourself JalYt-Ket-Xo-" + player.getName() + "."),
                                            new PlayerDialogue("Thanks, goodbye.")
                                    )),
                                    new Option("No I'm fine thanks.", () -> player.dialogue(
                                            new PlayerDialogue("No I'm fine thanks.")
                                    ))
                            )
                    );
                }
            });
        }
    }
}
