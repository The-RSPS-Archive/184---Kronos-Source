package io.ruin.model.entity.shared.listeners;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

public interface AttackNpcListener {

    boolean allow(Player player, NPC npc, boolean message);

}
