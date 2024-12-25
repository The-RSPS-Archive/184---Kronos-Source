package io.ruin.model.content;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.summerevent.SummerShrine;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 6/7/2020
 */
public class PvmPoints {

    public static void addPoints(Player player, NPC npc) {
        int points = 0;
        if (npc.getDef().combatLevel > 274 && !(npc.getDef().name.contains("brutal") || npc.getDef().name.contains("Adamant Dragon") || npc.getDef().name.contains("Rune Dragon"))) {
            points = Random.get(3, 5);
        } else if (npc.getDef().combatLevel < 50) {
            if (Random.get(1, 5) == 1) {
                points = 1;
            }
        } else {
            points = 1;
        }
        player.PvmPoints += points;
        if (player.PvmPoints % 10 == 0) {
            player.sendMessage("You now have " + player.PvmPoints + " PVM Points!");
        }

    }
}
