package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

/**
 * @author ReverendDread on 7/19/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class LokarSearunner {

    static {
        NPCAction.register(3855, "talk-to", (player, npc) -> {
            if (npc.getPosition().equals(2214, 3794, 0)) {
                player.dialogue(
                    new PlayerDialogue("Hello again Lokar."),
                    new NPCDialogue(npc, "Hi again Balvald!<br>What can I do for you?"),
                    new OptionsDialogue(
                        new Option("Can you take me back to Rellekka?", () -> {
                            player.dialogue(
                                new PlayerDialogue("Can you take me back to Rellekka please?"),
                                new NPCDialogue(npc, "Hey, if you want to go back to loserville with all the losers, who am I to stop you?"),
                                new ActionDialogue(() -> Traveling.fadeTravel(player,2621, 3686, 0))
                            );
                        }),
                        new Option("Nothing thanks.", () -> {
                            player.dialogue(
                                new PlayerDialogue("Nothing thanks!<br>I just saw you here and thought I'd say hello!"),
                                new NPCDialogue(npc, "Hey, I knew you seemed cool when I met you Balvald!")
                            );
                        })
                    )
                );
            } else {
                player.dialogue(
                    new PlayerDialogue("Hi Lokar, can you take me back to your ship?"),
                    new NPCDialogue(npc, "Sheesh, make your mind up lady, I'm not a taxi service!"),
                    new OptionsDialogue(
                        new Option("Go now.", () -> Traveling.fadeTravel(player, 2213, 3794, 0)),
                        new Option("Don't go.", () -> {
                            player.dialogue(
                                new PlayerDialogue("Actually, I've changed my mind.<br>Again.<br>I don't want to go."),
                                new NPCDialogue(npc, "You are possibly the most indecisive person I have ever met..."),
                                new PlayerDialogue("Well, 'bye then.")
                            );
                        })
                    )
                );
            }
        });
        NPCAction.register(3855, "talk-to", (player, npc) -> {
            if (npc.getPosition().equals(2214, 3794, 0)) {
                Traveling.fadeTravel(player,2621, 3686, 0);
            } else {
                Traveling.fadeTravel(player, 2213, 3794, 0);
            }
        });
    }

}
