package io.ruin.model.entity.npc.actions.zanaris.puropuro;


import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.impl.ButterflyNet;
import io.ruin.model.item.actions.impl.ImplingJar;
import io.ruin.model.item.actions.impl.scrolls.ImplingScroll;

public class ElnockInquisitor {

    private static final int ELNOCK_INQUISITOR = 5734;

    private static void trade(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.ELNOCK_EXCHANGE);
    }

    private static void requestEquipment(Player player, NPC npc) {
        if (!player.elnockInquisitorDialogueStarted)
            player.elnockInquisitorDialogueStarted = true;

        if (player.elnockInquisitorGivenEquipment) {
            player.dialogue(
                    new NPCDialogue(npc, "I have already given you some equipment."),
                    new NPCDialogue(npc, "If you are ready to start hunting implings, then enter the main part of the maze."),
                    new NPCDialogue(npc, "Just push through the wheat that surrounds the centre of the maze and get catching!")
            );
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "Yes! I have some spare equipment for you."),
                    new ActionDialogue(() -> {
                        if (player.getInventory().getFreeSlots() < 9) {
                            player.dialogue(new NPCDialogue(npc, "You'd better clear some space in your inventory for a butterfly net, 7 jars and a collector's scroll, then."));
                        } else {
                            player.elnockInquisitorGivenEquipment = true;
                            player.getInventory().add(ImplingScroll.IMPLING_SCROLL, 1);
                            player.getInventory().add(ImplingJar.IMPLING_JAR, 7);
                            player.getInventory().add(ButterflyNet.BUTTERFLY_NET, 1);
                            player.dialogue(
                                    new NPCDialogue(npc, "There you go. You have everything you need now."),
                                    new NPCDialogue(npc, "If you are ready to start hunting implings, then enter the main part of the maze."),
                                    new NPCDialogue(npc, "Just push through the wheat that surrounds the centre of the maze and get catching!"),
                                    new PlayerDialogue("Thanks, I'll get going."));
                        }
                    })
            );
        }
    }

    private static void whereIsThisPlace(Player player, NPC npc) {
        player.dialogue(event -> player.dialogue(
                new PlayerDialogue("Where is this place?"),
                new NPCDialogue(npc, "The fairies call it Puro-Puro. It seems to be the home plane of the implings."),
                new NPCDialogue(npc, "I don't think these creatures have a name for it. As you can see there isn't a lot else here other than wheat."),
                new PlayerDialogue("How did you get here?"),
                new NPCDialogue(npc, "The same way you did, I suspect. Through a portal in a wheat field. I followed one back."),
                new PlayerDialogue("I haven't noticed them do that."),
                new NPCDialogue(npc, "That's why I'm the investigator and you're the adventurer."),
                new OptionsDialogue("What do you want to ask?",
                        new Option("So what are these implings?", () -> soWhatAre(player, npc)),
                        new Option("Can I catch these implings then?", () -> canICatch(player, npc))
                )
        ));
    }

    private static void soWhatAre(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("So, what are these implings?"),
                new NPCDialogue(npc, "That's a very interesting question. My best guess is that they are relatives of imps, which " +
                        "is why there are imps here as well."),
                new NPCDialogue(npc, "Implings do appear to like collecting objects and, as my clients have noted, have no concept of ownership. " +
                        "However, I do not sense any malicious intent."),
                new NPCDialogue(npc, "It is my observation that they collect things that other creatures want, rather than they want them themselves. " +
                        "It seems to provide them with sustenance."),
                new PlayerDialogue("So they feed off our desire for things?"),
                new NPCDialogue(npc, "Possibly. Either way, it seems that they almost exclusively collect things that people want, except their younglings who I " +
                        "infer haven't learnt the best things to collect yet."),
                new PlayerDialogue("So the more experienced implings have the more desirable items?"),
                new NPCDialogue(npc, "That is my observation. Yes."),
                new OptionsDialogue("What do you want to ask?",
                        new Option("Where is this place?", () -> whereIsThisPlace(player, npc)),
                        new Option("Can I catch these implings then?", () -> canICatch(player, npc))
                )
        );
    }

    private static void canICatch(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Can I catch these implings, then?"),
                new NPCDialogue(npc, "Indeed you may. In fact I encourage it. You will, however, require some equipment."),
                new NPCDialogue(npc, "Firstly you will need a butterfly net in which to catch them and at least one special impling jar " +
                        "to store an impling."),
                new NPCDialogue(npc, "You will also require some experience as a Hunter since these creatures are elusive. The more immature " +
                        "implings require less experience, but some of the rare implings are extraordinarily hard to find and catch."),
                new NPCDialogue(npc, "Once you have caught one, you may break the jar open and obtain hte object the impling is carrying. " +
                        "Alternatively, you may exchange certain combination of jars with me. I will return the jars to my clients. In"),
                new NPCDialogue(npc, "exchange I will be able to provide you with some equipment that may help you hunt butterflies more effectively."),
                new NPCDialogue(npc, "Also, beware. Those imps walking around the maze do not like the fact that their kindred spirits are being" +
                        " captured and will attempt to steal any full jars you have on you, setting the implings free."),
                new OptionsDialogue("What do you want to ask about?",
                        new Option("Tell me more about these jars.", () -> player.dialogue(
                                new NPCDialogue(npc, "You cannot use an ordinary butterfly jar as a container as the implings will escape from them with " +
                                        "ease. However, I have done some investigation and have come up with a solution - if a butterfly jar is coated"),
                                new NPCDialogue(npc, "with a thin layer of a substance noxious to them they become incapable of escape."),
                                new PlayerDialogue("What substance is that, then?"),
                                new NPCDialogue(npc, "I have tried a few experiments with the help of a friend back home, and it turns out that " +
                                        "a combination of anchovy oil and flowers - marigolds, rosemary or nasturtiums - will work."),
                                new PlayerDialogue("Is there anywhere I can buy these jars?"),
                                new NPCDialogue(npc, "Well I may be able to let you have a few - if it means you will start hunting these implings - although " +
                                        "I do not have an infinite supply."),
                                new OptionsDialogue("What do you want to ask about?",
                                        new Option("So what's this equipment you can give me, then?", () -> player.dialogue(
                                                new PlayerDialogue("So what's this equipment you can give me, then?"),
                                                new NPCDialogue(npc, "I have been given permission by my clients to give three pieces of equipment to able hunters."),
                                                new NPCDialogue(npc, "Firstly, I have some imp deterent. If you bring me three baby implings, two young implings and one " +
                                                        "gourmet implings aready jarred, I will give you a vial. Imps don't like the smell, so they will be less likely to"),
                                                new NPCDialogue(npc, "steal jarred implings from you."),
                                                new NPCDialogue(npc, "Secondly, I have magical butterfly nets. If you bring me three gourmet implings, two earth implings and one" +
                                                        " essence impling I will give you a new net. It will help you catch born implings and butterflies."),
                                                new NPCDialogue(npc, "Lastly, I have magical jar generators. If you bring me three essence implings, two electric implings and one " +
                                                        "nature impling I will give you a jar generator. This object will create either butterfly or impling jars (up to"),
                                                new NPCDialogue(npc, "a limited number of charges) without having to carry a pack full of them."),
                                                new OptionsDialogue("What do you want to ask about?",
                                                        new Option("Do you have some spare equipment I can use?", () -> requestEquipment(player, npc)),
                                                        new Option("Thank you", () -> player.dialogue(
                                                                new PlayerDialogue("Thank you.")
                                                        ))
                                                )
                                        )),
                                        new Option("Do you have some spare equipment I can use?", () -> requestEquipment(player, npc))
                                )
                        ))

                )
        );
    }

    static {
        NPCAction.register(ELNOCK_INQUISITOR, "talk-to", (player, npc) -> {
            if (player.elnockInquisitorDialogueStarted) {
                //TODO check for impling scroll and offer to give every time until has
                player.dialogue(
                        new NPCDialogue(npc, "Ah, good day, it's you again. What can I do for you?"),
                        new OptionsDialogue("What would you like to ask about?",
                                new Option("Can I trade some jarred implings please?", () -> trade(player)),
                                new Option("Do you have some spare equipment I can use?", () -> requestEquipment(player, npc))
                        )
                );
            } else {
                player.dialogue(
                        new PlayerDialogue("What's a gnome doing here?"),
                        new NPCDialogue(npc, "I'm an investigator. I'm watching the implings."),
                        new PlayerDialogue("Why would you want to do that?"),
                        new NPCDialogue(npc, "My client has asked me to find out where certain missing items have been going."),
                        new PlayerDialogue("Who is this client?"),
                        new NPCDialogue(npc, "I'm not at liberty to discuss that. Investigator-client confidentiality don't you know."),
                        new OptionsDialogue("What do you want to ask?",
                                new Option("Where is this place?", () -> whereIsThisPlace(player, npc)),
                                new Option("So what are these implings?", () -> soWhatAre(player, npc)),
                                new Option("Can I catch these implings then?", () -> canICatch(player, npc))
                        )
                );
            }
        });
        NPCAction.register(ELNOCK_INQUISITOR, "trade", (player, npc) -> trade(player));
    }

}
