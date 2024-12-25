package io.ruin.model.entity.npc.actions.tzhaar;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.Pet;

public class TzHaarKetKeh {

    private static void whatIsThisPlace(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("What is this place?"),
                new NPCDialogue(npc, "This is our birthing pool. TzHaar are born from eggs incubated in this large pool here."),
                new NPCDialogue(npc, "When hatched, we retain memories. Memories and knowledge passed on from ancestors who returned to the lava."),
                new PlayerDialogue("Something doesn't seem right... There's a large opening."),
                new OptionsDialogue(
                        new Option("What happened here?", () -> whatHappenedHere(player, npc)),
                        new Option("Can I go down there?", () -> goDownThere(player, npc)),
                        new Option("Nevermind.", () -> player.dialogue(new PlayerDialogue("Nevermind.")))
                )
        );
    }

    private static void whatHappenedHere(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("What happened here?"),
                new NPCDialogue(npc, "We went too far... way too far, we need the memories. JalYt would not understand."),
                new PlayerDialogue("Please help me understand then."),
                new NPCDialogue(npc, "We TzHaar have special ability. When incubated in lava, a hatched TzHaar retains knowledge and memories of " +
                        "ancestors who returned to the lava."),
                new NPCDialogue(npc, "Not all memories though, those about earliest ancestors were not being retained. We experiment."),
                new NPCDialogue(npc, "We increased the deapth at which the eggs are incubated. Over time this worked, newly hatched Tzhaar had more memories " +
                        "of lost history!"),
                new PlayerDialogue("That sounds fantastic though, uncovering teh past to determine where your species originated?"),
                new NPCDialogue(npc, "Yes, but you JalYt do not understand, we kept pushing it. We wanted answers, but too deep we went."),
                new NPCDialogue(npc, "Eventually the pool collapsed into a sink hole, huge inferno there. This was big ancient incubation chamber."),
                new NPCDialogue(npc, "We hatched eggs in there and it was not a good decision. They were so different, bigger, stronger and fought each other for dominance."),
                new NPCDialogue(npc, "Instead of knowledge, we now have a prison of dangerous TzHaar creatures - it gets worse but I cannot trust JalYt with this knowledge yet."),
                new OptionsDialogue(
                        new Option("What is this place?", () -> whatIsThisPlace(player, npc)),
                        new Option("Can I go down there?", () -> goDownThere(player, npc)),
                        new Option("Nevermind.", () -> player.dialogue(new PlayerDialogue("Nevermind.")))
                )
        );
    }

    private static void goDownThere(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Can I go down there?"),
                new NPCDialogue(npc, "JalYt, we need help, but this is extremely dangerous. TzHaar not strong enough to take control back."),
                new NPCDialogue(npc, "We are worried about trusting JalYt to take on this task for us."),
                new PlayerDialogue("Let me prove it."),
                new NPCDialogue(npc, "I have one idea, if you could sacrifice your fire cape to me, I can take your word."),
                new ActionDialogue(() -> {
                    if(player.getInventory().hasId(6570)) {
                        player.dialogue(
                                new OptionsDialogue("Sacrifice your Fire cape?",
                                        new Option("Yes, I want to sacrifice my cape.", () -> player.dialogue(
                                                new PlayerDialogue("Okay, I'd like to sacrifice my fire cape."),
                                                new NPCDialogue(npc, "Very well, just hand it over."),
                                                new OptionsDialogue("Really sacrifice your Fire cape?",
                                                        new Option("No, I want to keep it!", () -> player.dialogue(new PlayerDialogue("No, I want to keep it!"))),
                                                        new Option("Yes, hand it over.", () -> {
                                                            if(player.getInventory().hasId(6570)) {
                                                                player.getInventory().remove(6570,1 );
                                                                player.dialogue(new MessageDialogue("You hand over your cape to TzHaar-Ket-Keh"),
                                                                        new NPCDialogue(npc, "Give it your best shot JalYt-Ket-Xo-" + player.getName() + "."));
                                                                Config.INFERNO_ENTRANCE.set(player, 2);
                                                            }
                                                        })
                                                )

                                        )),
                                        new Option("No, I want to keep it!", () -> player.dialogue(new PlayerDialogue("No, I want to keep it!")))

                                )
                        );
                    } else {
                        player.dialogue(
                                new PlayerDialogue("I don't have a fire cape on me unfortunately.")
                        );
                    }
                })
        );
    }

    private static void gambleCape(Player player, NPC npc) {
        if(player.getInventory().hasId(Pet.TZREK_ZUK.itemId) || player.getBank().hasId(Pet.TZREK_ZUK.itemId) || (player.pet != null && player.pet == Pet.TZREK_ZUK)) {
            player.dialogue(new NPCDialogue(npc, "You already have TzRek-Zuk!"));
            return;
        }
        Item cape = player.getInventory().findItem(21295);
        if(cape == null) {
            player.dialogue(new NPCDialogue(npc, "You no have cape!"));
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Sacrifice your infernal cape for a chance at TzRek-Zuk?", cape, () -> {
            cape.remove();
            if (Random.rollDie(25, 1)) {
                Pet.TZREK_ZUK.unlock(player);
                player.dialogue(new NPCDialogue(npc, "You lucky. Better train him good else TzKal-Zuk find you, JalYt."));
            } else {
                player.dialogue(new NPCDialogue(npc, "You not lucky. Maybe next time, JalYt."));
            }
        }));
    }

    static {
        NPCAction.register(7690, "talk to", (player, npc) -> {
            if(player.talkedToTzHaarKetKeh) {
                player.dialogue(
                        new NPCDialogue(npc, "Yes, JalYt-Ket-Xo-" + player.getName() + "?"),
                        new OptionsDialogue(
                                new Option("What is this place?", () -> whatIsThisPlace(player, npc)),
                                new Option("What happened here?", () -> whatHappenedHere(player, npc)),
                                new Option("Nevermind.", () -> player.dialogue(new PlayerDialogue("Nevermind.")))
                        )
                );
            } else {
                player.dialogue(
                        new PlayerDialogue("Wow what has happened here? That doesn't look good!"),
                        new NPCDialogue(npc, "We pushed it too far! Way too far. JalYt would not understand. The memories we needed them. Now big Inferno."),
                        new ActionDialogue(() -> player.talkedToTzHaarKetKeh = true),
                        new OptionsDialogue(
                                new Option("What is this place?", () -> whatIsThisPlace(player, npc)),
                                new Option("What happened here?", () -> whatHappenedHere(player, npc)),
                                new Option("Can I go down there?", () -> goDownThere(player, npc)),
                                new Option("Nevermind.", () -> player.dialogue(new PlayerDialogue("Nevermind.")))
                        )
                );
            }
        });
        NPCAction.register(7690, "exchange", TzHaarKetKeh::gambleCape);
    }
}
