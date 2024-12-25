package io.ruin.model.entity.npc.actions.guild.warrior;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.stat.StatType;

public class Ghommal {

    static {
        NPCAction.register(2457, "talk-to", (player, npc) -> {
            int combinedLevel = player.getStats().get(StatType.Attack).fixedLevel + player.getStats().get(StatType.Strength).fixedLevel;
            if (combinedLevel < 130) {
                player.dialogue(
                        new NPCDialogue(npc, "You not pass. You too weedy."),
                        new PlayerDialogue("What? But I'm a warrior!").animate(571),
                        new NPCDialogue(npc, "Heehee... he say he warrior... I not heard that one<br>for... at leas' 5 minutes!"),
                        new PlayerDialogue("Go on, let me in, you know you want to. I could...<br>make it worth your while...").animate(593),
                        new NPCDialogue(npc, "No! You is not a strong warrior, you not enter till you<br>bigger. Ghommal does not take bribes.").animate(615),
                        new PlayerDialogue("Why not?"),
                        new NPCDialogue(npc, "Ghommal stick to Warrior's Code of Honour. When<br>you a bigger, stronger warrior, you come back.").animate(589)
                );
            } else {
                player.dialogue(
                        new NPCDialogue(npc, "Ghommal welcome you to Warrior Guild!"),
                        new PlayerDialogue("Uhm.. thank you, I think.")
                );
            }
        });
    }
}
