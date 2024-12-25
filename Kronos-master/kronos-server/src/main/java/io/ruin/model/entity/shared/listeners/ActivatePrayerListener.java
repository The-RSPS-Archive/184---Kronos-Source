package io.ruin.model.entity.shared.listeners;

import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.prayer.Prayer;

@FunctionalInterface
public interface ActivatePrayerListener {

	boolean allow(Player player, Prayer prayer);
}
