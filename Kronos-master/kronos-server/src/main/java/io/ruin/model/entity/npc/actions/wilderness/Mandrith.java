package io.ruin.model.entity.npc.actions.wilderness;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

public class Mandrith {

    private static void talk(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Who are you and what is this place?").animate(554),
                new NPCDialogue(npc, "My name is Mandrith.").animate(588),
                new NPCDialogue(npc, "I collect valuable resources and pawn off access to them<br>to foolish adventurers, like yourself.").animate(589),
                new NPCDialogue(npc, "You should take a look inside my arena. Theres an<br>abundance of valuable resources inside.").animate(555),
                new PlayerDialogue("And I can take whatever I want?").animate(554),
                new NPCDialogue(npc, "It's all yours. All I ask is you pay the upfront fee.").animate(588),
                new PlayerDialogue("Will others be able to kill me inside?").animate(588),
                new NPCDialogue(npc, "Yes. These walls will only hold them back for so long.").animate(588),
                new PlayerDialogue("You'll stop them though, right?").animate(554),
                new NPCDialogue(npc, "Haha! For the right price, I won't deny any one access<br>to my arena. Even if their intention is to kill you.").animate(568),
                new PlayerDialogue("Right...").animate(571),
                new NPCDialogue(npc, "My arena holds many treasures that I've acquired at<br>great expense, adventurer. Their bounty can come at a<br>price.").animate(590),
                new NPCDialogue(npc, "One day, adventurer, I will boast ownership of a much<br>larger, much more dangerous arena than this. Take<br>advantage of this offer while it lasts.").animate(590)
        );
    }

    static {
        NPCAction.register(6599, "talk-to", (player, npc) -> talk(player, npc));
    }
}

