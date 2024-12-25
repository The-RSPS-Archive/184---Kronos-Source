package io.ruin.model.entity.npc.actions.traveling;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

/**
 *
 * traveling screen is interface 431
 *
 * @author ReverendDread on 7/19/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class CaptainBentley {

    static {
        NPCAction.register(6650, "talk-to", ((player, npc) -> {
            if (player.getPosition().getY() > 3886) {
                player.dialogue(
                    new PlayerDialogue("Hi."),
                    new NPCDialogue(npc, "And you're wanting what now?"),
                    new OptionsDialogue(
                        new Option("Can you take me back to Rellekka please?", () -> {
                            player.dialogue(
                                new PlayerDialogue("Can you take me back to Rellekka please?"),
                                new NPCDialogue(npc, "I'll take you as far as Pirates' Cove. You'll have to find the rest of the way back yourself."),
                                new ActionDialogue(() -> {
                                    Traveling.fadeTravel(player, 2224, 3796, 2);
                                })
                            );
                        }),
                        new Option("So we're here?", () -> {
                            player.dialogue(
                                new PlayerDialogue("So we're here?"),
                                new NPCDialogue(npc, "Yep. You're free to explore the island. Be careful though, the Moon Clan are very powerful, it wouldn't be wise to wrong them."),
                                new PlayerDialogue("Thanks, I'll keep that seal of passage close.")
                            );
                        })
                    )
                );
            } else {
                player.dialogue(
                    new PlayerDialogue("Can we head to Lunar Isle?"),
                    new NPCDialogue(npc, "Sure matey!"),
                    new ActionDialogue(() -> {
                        //TODO pirate ship interface
                        Traveling.fadeTravel(player, 2130, 3899, 2);
                    })
                );
            }
        }));
        NPCAction.register(6650, "travel", ((player, npc) -> {
            if (player.getPosition().getY() > 3886) {
                Traveling.fadeTravel(player, 2224, 3796, 2);
            } else {
                Traveling.fadeTravel(player, 2130, 3899, 2);
            }
        }));
    }

}
