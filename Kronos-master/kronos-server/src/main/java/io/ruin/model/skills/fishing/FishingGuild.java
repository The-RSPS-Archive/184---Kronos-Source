package io.ruin.model.skills.fishing;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class FishingGuild {

    private static final int KYLIE_MINNOW_DOCK = 7727;
    private static final int KYLIE_MINNOW_PLATFORM = 7728;
    private static final int MASTER_FISHER = 2913;

    private static void introductionDialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Strewth! Nippy little blighters!"),
                new PlayerDialogue("Excuse me?"),
                new NPCDialogue(npc, "Oh G'day! Sorry, didn't see you there!"),
                new PlayerDialogue("That's okay. What seems to be the problem?"),
                new NPCDialogue(npc, "Oh, well I wanted to catch some minnows so I could use em' as bait, but the little blighters are just too quick! I managed to get " +
                        "'em all rounded up over on my fishing platform, but I still can't catch 'em!"),
                new NPCDialogue(npc, "Then, to make things worse, any time I do finally catch some, those bloody flying fish jump out at me and gobble up me minnows!").animate(611),
                new NPCDialogue(npc, "What I need is someone who can catch 'em for me!"),
                new PlayerDialogue("Well, maybe I could help out?").action(() -> player.kylieMinnowDialogueStarted = true),
                new NPCDialogue(npc, "Well I don't know. I don't want to be letting just anyone onto me fishing platform! But I guess if you could show me that you are a " +
                        "worthy fisher than that would be okay. I can even give you shark in exchange"),
                new NPCDialogue(npc, "for the minnows you catch, 'cos them I can catch no problem!"),
                new PlayerDialogue("Sound fair. What can I do to show that I am worthy?"),
                new NPCDialogue(npc, "Well you should be very experienced in the art of fishing, own the proper attire, and be recognised by your peers as a great fisherman."),
                new MessageDialogue("You must have level 82 Fishing" + " in order to suitably impress Kylie.")
        );
    }

    private static void experiencedDialogue(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("So, how about letting me out onto your fishing platform?"),
                new NPCDialogue(npc, "Ripper! You certainly seem to be the real deal, so sure, go ahead and take the boat over there.")
        );
    }

    private static void notExperiencedDialogue(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("So, how about letting me out onto your fishing platform?"),
                new NPCDialogue(npc, "Sorry, but I just don't think you are experienced enough."),
                new MessageDialogue("You must have level 82 Fishing" + " in order to suitably impress Kylie.")
        );
    }

    static {
        /**
         * Entrance
         */
        ObjectAction.register(20925, "open", (player, obj) -> {
            if(player.getAbsY() <= 3393) {
                if(player.getStats().get(StatType.Fishing).currentLevel < 68) {
                    player.dialogue(new NPCDialogue(MASTER_FISHER, "Hello, only the top fishers are allowed in here. You need a fishing level of 68 to enter."));
                    return;
                }
            }

            player.startEvent(event -> {
                player.lock();

                if(!player.isAt(obj.x, player.getAbsY() <= 3393 ? obj.y - 1 : obj.y)) {
                    player.stepAbs(obj.x, player.getAbsY() <= 3393 ? obj.y - 1 : obj.y, StepType.FORCE_WALK);
                    event.delay(1);
                }
                GameObject opened = GameObject.spawn(1542, 2611, 3393, 0, obj.type, 0);
                obj.skipClipping(true).remove();
                player.step(0, player.getAbsY() <= 3393 ? 1 : -1, StepType.FORCE_WALK);
                event.delay(2);
                obj.restore().skipClipping(false);
                opened.remove();

                player.unlock();
            });
        });

        /**
         * Row boat
         */
        ObjectAction.register(30376, "travel to platform", (player, obj) -> {
            int fishingLevel = player.getStats().get(StatType.Fishing).currentLevel;
            if(fishingLevel < 82) {
                player.dialogue(new NPCDialogue(KYLIE_MINNOW_DOCK, "G'day, only the best fishers are allowed onto the fishing platform. You need a fishing level of 82."));
                return;
            }

            player.startEvent(event -> {
                player.lock();
                player.getPacketSender().fadeOut();
                event.delay(3);
                player.dialogue(new MessageDialogue("You board the boat and row to the fishing platform.").hideContinue());
                event.delay(2);
                player.getPacketSender().fadeIn();
                player.getMovement().teleport(2614, 3440);
                player.unlock();
            });
        });

        ObjectAction.register(30377, "leave platform", (player, obj) -> player.startEvent(event -> {
            player.lock();
            player.getPacketSender().fadeOut();
            event.delay(3);
            player.dialogue(new MessageDialogue("You board the boat and row to the " + ("dock") + ".").hideContinue());
            event.delay(2);
            player.getPacketSender().fadeIn();
            player.getMovement().teleport(2600, 3425);
            player.unlock();
        }));

        /**
         * Kylie Minnow
         */
        NPCAction.register(KYLIE_MINNOW_DOCK, "talk-to", (player, npc) -> {
            if(player.kylieMinnowDialogueStarted) {
                int fishingLevel = player.getStats().get(StatType.Fishing).currentLevel;
                if(fishingLevel < 82)
                    notExperiencedDialogue(player, npc);
                else
                    experiencedDialogue(player, npc);
            } else {
                introductionDialogue(player, npc);
            }
        });
        NPCAction.register(KYLIE_MINNOW_PLATFORM, "talk-to", (player, npc) -> player.dialogue(new NPCDialogue(npc, "Catch some minnows!")));
        NPCAction.register(KYLIE_MINNOW_PLATFORM, "trade", (player, npc) -> {
            String fishName = "shark";
            Item minnows = player.getInventory().findItem(21356);
            if(minnows == null) {
                player.dialogue(new NPCDialogue(npc, "You'll be needing at least 40 minnows to trade for a " + fishName + "! Come back and see me when you have some more!"));
                return;
            }
            int amount = minnows.getAmount();
            if(amount < 40) {
                player.dialogue(new NPCDialogue(npc, "You'll be needing at least 40 minnows to trade for a " + fishName + "! Come back and see me when you have some more!"));
                return;
            }
            int max = amount / 40;
            player.dialogue(
                    new NPCDialogue(npc, "I can give you " + ("a shark") +  " for every 40 minnows that you give me. How many " + fishName + " would you like?"),
                    new ActionDialogue(() -> player.integerInput("How many " + fishName + " would you like? (0 - " + max + ")", amt -> {
                        int exchange = Math.min(amt, max);
                        minnows.remove(exchange * 40);
                        player.getInventory().add(384, exchange);
                        player.dialogue(new MessageDialogue("You trade in " + exchange * 40 + " minnows for " + exchange + " sharks."));
                    }))
            );
        });

    }
}
