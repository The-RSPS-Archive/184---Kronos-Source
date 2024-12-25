package io.ruin.model.entity.shared.listeners;

import io.ruin.model.entity.player.Player;

public interface AttackPlayerListener {

    boolean allow(Player player, Player pTarget, boolean message);

}