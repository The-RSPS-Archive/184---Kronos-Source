package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.services.Votes;
import io.ruin.utility.Broadcast;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.process.event.EventWorker.startEvent;

public class VoteManager {

    private static final String VOTE_URL = World.type.getWebsiteUrl() + "/voting";

    private static int voteMysteryBoxesClaimed = 0;

    static {
        NPCAction.register(1815, "cast-votes", (player, npc) -> {
            player.dialogue(
                    new OptionsDialogue("Would you like to open our voting page?",
                            new Option("Yes", () -> player.openUrl("Voting Page", VOTE_URL)),
                            new Option("No", player::closeDialogue)
                    )
            );
        });
        NPCAction.register(1815, "claim-votes", (player, npc) -> {
            Votes.claim(player, npc, (claimed, runelocus) -> {
                if(claimed == -1 || runelocus == -1) {
                    player.dialogue(new NPCDialogue(npc, "Error claiming votes, please try again."));
                    return;
                }
                if(claimed == 0 && runelocus == 0) {
                    player.dialogue(new NPCDialogue(npc, "No unclaimed votes found."));
                    return;
                }
                if (runelocus > 0) {
                    player.getInventory().add(4067, runelocus * 5);
                    player.getInventory().addOrDrop(new Item(621, 2));
                }

                if (claimed > 0) {
                    player.getInventory().add(4067, claimed * 3);
                }

                player.claimedVotes += (claimed + runelocus);
                player.dialogue(new NPCDialogue(npc, "You've successfully claimed " + (claimed + runelocus) + " vote" + ((claimed + runelocus) > 1 ? "s" : "") + "!"));
                player.sendFilteredMessage(Color.COOL_BLUE.wrap("You receive " + (claimed * 3) + " vote ticket" + ((claimed + runelocus) > 1 ? "s" : "") + " for voting."));
                player.voteMysteryBoxReward += (claimed + runelocus);
                if(player.voteMysteryBoxReward >= 3) {
                    voteMysteryBoxesClaimed += 1;
                    player.voteMysteryBoxReward -= 3;
                    boolean bank;
                    /*if(bank) {
                        player.getBank().add(6829, 1);
                    } else {
                        player.getInventory().add(6829, 1);
                    }*/
                    bank = player.getInventory().isFull();
                    if(bank) {
                        player.getBank().add(6758, 1);
                    } else {
                        player.getInventory().add(6758, 1);
                    }
                    bank = player.getInventory().isFull();
                    if(bank) {
                        player.getBank().add(COINS_995, 1000000);
                    } else {
                        player.getInventory().add(COINS_995, 1000000);
                    }
                    bank = player.getInventory().isFull();
                    if (bank) {
                        player.getBank().add(1464, 1);
                    } else {
                        player.getInventory().add(1464, 1);
                    }
                    player.sendMessage(Color.COOL_BLUE.wrap("You receive double xp scroll, vote lottery ticket and 1m cash for voting on all 3 sites" + (bank ? " which has been deposited into your bank" : "") + "!"));
                    if (Random.get(1,4) == 4) {
                        bank = player.getInventory().isFull();
                        if (bank) {
                            player.getBank().add(6829, 1);
                        } else {
                            player.getInventory().add(6829, 1);
                        }
                        player.sendMessage(Color.COOL_BLUE.wrap("You also receive a vote mystery box!" + (bank ? " which has been deposited into your bank" : "") + "!"));
                    }
                }
            });
        });
        startEvent(e -> {
            while(true) {
                e.delay(3000); //30 minutes
                if(voteMysteryBoxesClaimed > 1) {
                    Broadcast.WORLD.sendNews(Icon.ANNOUNCEMENT, "Another " + voteMysteryBoxesClaimed + " players have claimed their FREE Double Experience Scroll! Type ::vote and claim yours now!");
                    voteMysteryBoxesClaimed = 0;
                }
            }
        });
    }

}
