package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class SecurityAdvisor {

    private static void imSecure(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Yes, I'm secure."),
                new ActionDialogue(() -> {
                    if (player.getBankPin().hasPin() && player.tfa) {
                        player.dialogue(new NPCDialogue(npc, "Good to know! It always warms my heart to see people valuing their security."));
                    } else {
                        player.dialogue(
                                new NPCDialogue(npc, "It doesn't look like you are.. setting up the security for your account takes no" +
                                        " more than a few minutes and can save you a ton of time and heartache should anything ever happen "),
                                new NPCDialogue(npc, "Would you like me to give you a rundown of how you can properly secure your account?"),
                                new OptionsDialogue(
                                        new Option("Yes, help me secure my account.", () -> player.dialogue(
                                                new PlayerDialogue("Yes, please help me secure my account!"),
                                                new ActionDialogue(() -> helpSecure(player, npc)))
                                        ),
                                        new Option("No, I don't want me account to be safe.", () -> player.dialogue(
                                                new PlayerDialogue("No, I don't want my account to be safe.")
                                        ))
                                ));
                    }
                })
        );
    }

    private static void helpSecure(Player player, NPC npc) {
        if (player.getBankPin().hasPin() && player.tfa) {
            player.dialogue(new NPCDialogue(npc, "It looks like you have all the security options we have to offer!"),
                    new PlayerDialogue("Oh, I guess I am secure."));
        } else {
            player.dialogue(
                    new NPCDialogue(npc, "Alright! Well there's two types of security features here at " + World.type.getWorldName() + " that you can take advantage of. The first is " +
                            "2FA and the second is setting a bank pin. Which one would you like to know about?"),
                    new OptionsDialogue(
                            new Option("Tell me about 2FA.", () -> player.dialogue(
                                    new PlayerDialogue("I'd like to know about the 2FA system."),
                                    new NPCDialogue(npc, "Two-factor authentication is integrated into your forum account. Once you setup 2FA on your forum, you'll be prompted after typing in your login " +
                                            "details for a 6-digit pin"),
                                    new NPCDialogue(npc, "which is automatically generated from your mobile device. Without the pin, players will be unable to login to your account. It's the safest form of" +
                                            " security we offer here at " + World.type.getWorldName() + "."),
                                    new NPCDialogue(npc, "Would you like me to open the web-page so you can setup your 2FA?"),
                                    new OptionsDialogue("Open the 2FA setup page?",
                                            new Option("Yes, I'd like to secure my account.", () -> player.openUrl(World.type.getWorldName() + " 2FA", "https://community.kronos.rip/index.php?account/security")),
                                            new Option("No, I'll set it up later.", () -> player.dialogue(new PlayerDialogue("No, I'll set it up later.")))
                                    )
                            )),
                            new Option("Tell me about Bank Pins.", () -> player.dialogue(
                                    new PlayerDialogue("I'd like to know about the bank pins system."),
                                    new NPCDialogue(npc, "Bank pins are the best security feature for keeping the contents of your bank safe! With one in place, your bank access " +
                                            "will be locked up and will require a custom set 4 number code to get into."),
                                    new NPCDialogue(npc, "If you're interested in setting up such a code, speak with one of the lovely workers over at the bank and they'll get you started."),
                                    new PlayerDialogue("Okay, thanks!")
                            ))
                    )
            );
        }
    }

    static {
        NPCAction.register(5442, "check 2fa settings", (player, npc) -> player.openUrl(World.type.getWorldName() + " 2FA", "https://community.kronos.rip/index.php?account/security"));
        NPCAction.register(5442, "check pin settings", (player, npc) -> player.getBankPin().openSettings());
        NPCAction.register(5442, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Making sure your account is secure is absolutely critical! Have you taken the time to secure your account with a bank pin and 2FA?"),
                new OptionsDialogue(
                        new Option("Yes, I'm secure.", () -> imSecure(player, npc)),
                        new Option("I'm not secure yet.", () -> player.dialogue(
                                new PlayerDialogue("I'm not secure yet."),
                                new ActionDialogue(() -> {
                                    if (player.getBankPin().hasPin() && player.tfa) {
                                        player.dialogue(
                                                new NPCDialogue(npc, "It looks like you have all the security options we have to offer!"),
                                                new PlayerDialogue("Oh, I guess I am secure."));
                                    } else {
                                        player.dialogue(
                                                new NPCDialogue(npc, "Well that just simply won't do! Setting up the security for your account takes no more " +
                                                        "than a few minutes and can save you a ton of time and heartache should anything ever happen."),
                                                new NPCDialogue(npc, "Would you like me to give you a rundown of how you can properly secure your account?"),
                                                new OptionsDialogue(
                                                        new Option("Yes, help me secure my account.", () -> player.dialogue(
                                                                new PlayerDialogue("Yes, please help me secure my account!"),
                                                                new ActionDialogue(() -> helpSecure(player, npc)))
                                                        ),
                                                        new Option("No, I don't want me account to be safe.", () -> player.dialogue(
                                                                new PlayerDialogue("No, I don't want my account to be safe.")
                                                        ))
                                                ));
                                    }
                                })
                        ))
                )
        ));
    }
}
