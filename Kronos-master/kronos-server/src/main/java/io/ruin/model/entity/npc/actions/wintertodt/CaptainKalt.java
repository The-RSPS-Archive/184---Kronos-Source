package io.ruin.model.entity.npc.actions.wintertodt;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class CaptainKalt {

    private static void aboutWintertodt(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about the Wintertodt."),
                new NPCDialogue(npc, "Ignisia claims it's some kind of evil spirit.").animate(610),
                new PlayerDialogue("You don't believe her?"),
                new NPCDialogue(npc, "Nah, I believe in things I can stab and kill. This is all<br>" +
                        "just a storm in a teacup. I mean, how can wind and<br>snow be evil? My toad is more of a threat!").animate(590),
                new PlayerDialogue("Your... toad?"),
                new NPCDialogue(npc, "Yeah, just a bit of fun, I have a pet - he's around here<br>somewhere, assuming that troublesome cat hasn't eaten<br>him."),
                new PlayerDialogue("That's a bit weird, but okay.").animate(575),
                new NPCDialogue(npc, "No weirder than being scared of snow."),
                new OptionsDialogue(
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("Tell me how I'm doing.", () -> howImDoing(player, npc)),
                        new Option("See you later.", () -> seeYouLater(player, npc))
                )
        );
    }

    private static void aboutYourself(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about yourself."),
                new NPCDialogue(npc, "I'm Captain Kalt, proud member of the Shayzien<br>Guard."),
                new PlayerDialogue("How long have you been a guard?"),
                new NPCDialogue(npc, "Man and boy I've worn the uniform. Earned Captain a<br>few years back and never looked back. Not too happy" +
                        "<br>about babysitting delusional wizards though. But Orders<br>is Orders.").animate(570),
                new OptionsDialogue(
                        new Option("Tell me about Wintertodt.", () -> aboutWintertodt(player, npc)),
                        new Option("Tell me how I'm doing.", () -> howImDoing(player, npc)),
                        new Option("See you later.", () -> seeYouLater(player, npc))
                )
        );
    }

    private static void howImDoing(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me how I'm doing."),
                new MessageDialogue("You have subdued the Wintertodt " + player.wintertodtSubdued + " time" + (player.wintertodtSubdued > 1 ? "s" : "") +
                        "<br>Your lifetime score: " + player.lifetimeWintertodtPoints + "<br>Your highest score: " + player.wintertodtHighscore).lineHeight(24),
                new OptionsDialogue(
                        new Option("Tell me about Wintertodt.", () -> aboutWintertodt(player, npc)),
                        new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                        new Option("See you later.", () -> seeYouLater(player, npc))
                )
        );
    }

    private static void seeYouLater(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("See you later."),
                new NPCDialogue(npc, "Goodbye.")
        );
    }

    static {
        NPCAction.register(7377, "talk-to", (player, npc) -> {
            if(player.talkedToIgnisia) {
                player.dialogue(
                        new NPCDialogue(npc, "Hello there."),
                        new OptionsDialogue(
                                new Option("Tell me about Wintertodt.", () -> aboutWintertodt(player, npc)),
                                new Option("Tell me about yourself.", () -> aboutYourself(player, npc)),
                                new Option("Tell me how I'm doing.", () -> howImDoing(player, npc)),
                                new Option("See you later.", () -> seeYouLater(player, npc))
                        )
                );
            } else {
                player.dialogue(
                        new NPCDialogue(npc, "Hello there."),
                        new PlayerDialogue("Hello. What's going on here ten?"),
                        new NPCDialogue(npc, "You should speak to Ignisia, she'll fill you in.")
                );
            }
        });
        NPCAction.register(7377, "check scores", (player, npc) -> {
            if(player.talkedToIgnisia) {
                player.dialogue(new MessageDialogue("You have subdued the Wintertodt " + player.wintertodtSubdued + " time" + (player.wintertodtSubdued > 1 ? "s" : "") +
                        "<br>Your lifetime score: " + player.lifetimeWintertodtPoints + "<br>Your highest score: " + player.wintertodtHighscore).lineHeight(24));
            } else {
                player.dialogue(new NPCDialogue(npc, "You should speak to Ignisia, she'll fill you in."));
            }
        });
    }
}
