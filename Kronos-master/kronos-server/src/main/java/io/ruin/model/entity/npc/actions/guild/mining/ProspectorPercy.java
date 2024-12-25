package io.ruin.model.entity.npc.actions.guild.mining;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;

public class ProspectorPercy {

    private static final int PROSPECTOR_PERCY = 6562;

    private static void howDoIMine(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("How do I mine here?"),
                new NPCDialogue(npc, "Git ahold of yer pickaxe, find a vein of ore, and set to<br>work. If ye got a bit of skill, ye'll have a pocket o' pay-<br>dirt in no time."),
                new NPCDialogue(npc, "I've built me a contraption to wash the pay-dirt. Just<br>drop yer pay-dirt in the hopper, an' wait fer it at the<br>other end.").animate(590),
                new NPCDialogue(npc, "I won't charge ye fer usin' my contraption, but ye'd<br>better fix it yerself when it breaks. A good whack with a<br>hammer usually settles it."),
                new NPCDialogue(npc, "Now will ye be gettin' to work now, or are ye gonna<br>keep yappin' like a doggone galoot?").animate(615),
                new OptionsDialogue(
                        new Option("Would you like to trade?", () -> wouldYouLikeToTrade(player, npc)),
                        new Option("Tell me about yourself.", () -> tellMeAboutYourself(player, npc)),
                        new Option("Can I go up the ladder to mine there?", () -> goUpLadder(player, npc)),
                        new Option("I'll leave you alone.", () -> leaveYouAlone(player, npc))
                )
        );
    }

    private static void wouldYouLikeToTrade(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Would you like to trade?"),
                new NPCDialogue(npc, "If ye've found yerself some golden nuggets in this 'ere<br>mine, I'll do you a swap, yeah.").animate(593),
                new ActionDialogue(() -> ShopManager.openIfExists(player, ""))//TODO Fill this in
        );
    }

    private static void tellMeAboutYourself(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Tell me about yourself."),
                new NPCDialogue(npc, "Why, I'm Percy. Prospector Percy, the roughest,<br>toughest, gruffest miner in the land. I've been pannin'<br>fer gold since I were a young-un, " +
                        "and here's where<br>I've struck it lucky.").animate(565),
                new OptionsDialogue(
                        new Option("Excuse me, what language are you speaking??", () -> excuseMe(player, npc)),
                        new Option("You discovered this mine?", () -> youDiscoveredThisMine(player, npc))
                )
        );
    }

    private static void excuseMe(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Excuse me, but what language are you speaking?"),
                new NPCDialogue(npc, "Don't ye give me any of yer lip, ye dern varmint!<br>Young'uns these days got no respect.").animate(615),
                new PlayerDialogue("Do go on.").animate(592),
                new NPCDialogue(npc, "This here's the richest seam of ore I've found in all my<br>days. After I built a contraption for washing the pay-<br>dirt, the dwarves let me run things down here.").animate(590),
                new NPCDialogue(npc, "Now, have ye any more idjit questions, or are ye ready<br>to do some real work?").animate(555),
                new OptionsDialogue(
                        new Option("How do I mine here?", () -> howDoIMine(player, npc)),
                        new Option("Would you like to trade?", () -> wouldYouLikeToTrade(player, npc)),
                        new Option("Can I go up the ladder to mine there?", () -> goUpLadder(player, npc)),
                        new Option("I'll leave you alone.", () -> leaveYouAlone(player, npc))
                )
        );
    }

    private static void youDiscoveredThisMine(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("You discovered this mine?"),
                new NPCDialogue(npc, "This here's the richest seam of ore I've found in all my<br>days. After I built a contraption for washing the pay-<br>dirt, the dwarves let me run things down here.").animate(590),
                new NPCDialogue(npc, "Now, have ye any more idjit questions, or are ye ready<br>to do some real work?").animate(555),
                new OptionsDialogue(
                        new Option("How do I mine here?", () -> howDoIMine(player, npc)),
                        new Option("Would you like to trade?", () -> wouldYouLikeToTrade(player, npc)),
                        new Option("Can I go up the ladder to mine there?", () -> goUpLadder(player, npc)),
                        new Option("I'll leave you alone.", () -> leaveYouAlone(player, npc))
                )
        );
    }

    private static void goUpLadder(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Can I go up the ladder to mine there?"),
                new NPCDialogue(npc, "Ye'll need level 72 Mining first. An' don't think ye can<br>fool me with yer potions and fancy stat-boosts. Get yer<br>level up for real."),
                new OptionsDialogue(
                        new Option("How do I mine here?", () -> howDoIMine(player, npc)),
                        new Option("Would you like to trade?", () -> wouldYouLikeToTrade(player, npc)),
                        new Option("Tell me about yourself.", () -> tellMeAboutYourself(player, npc)),
                        new Option("I'll leave you alone.", () -> leaveYouAlone(player, npc))
                )
        );
    }

    private static void leaveYouAlone(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("I'll leave you alone.").animate(571),
                new NPCDialogue(npc, "Dern straight ye will."));
    }

    static {
        NPCAction.register(PROSPECTOR_PERCY, "talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Git back ter work, ye young varmint! There's treasure<br>in them walls, and it's not gonna mine itself while ye<br>" +
                        "stand here yappin'.").animate(616),
                new OptionsDialogue(
                        new Option("How do I mine here?", () -> howDoIMine(player, npc)),
                        new Option("Would you like to trade?", () -> wouldYouLikeToTrade(player, npc)),
                        new Option("Tell me about yourself.", () -> tellMeAboutYourself(player, npc)),
                        new Option("Can I go up the ladder to mine there?", () -> goUpLadder(player, npc)),
                        new Option("I'll leave you alone.", () -> leaveYouAlone(player, npc))
                )
        ));
    }
}
