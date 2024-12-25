package io.ruin.model.activities.tasks;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

public interface TaskListener {

    int onPlayerKill(Player player, Player killed);

    int onNPCKill(Player player, NPC killed);
}
