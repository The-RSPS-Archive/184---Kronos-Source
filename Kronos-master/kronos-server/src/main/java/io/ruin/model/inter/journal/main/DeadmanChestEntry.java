package io.ruin.model.inter.journal.main;

import io.ruin.cache.Color;
import io.ruin.content.areas.wilderness.DeadmanChest;
import io.ruin.content.areas.wilderness.DeadmanChestEvent;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.JournalEntry;

public class DeadmanChestEntry extends JournalEntry {

	public static final DeadmanChestEntry INSTANCE = new DeadmanChestEntry();

	@Override
	public void send(Player player) {
		String chestStatus = DeadmanChestEvent.INSTANCE.getCurrentChest() == null ? DeadmanChestEvent.INSTANCE.timeRemaining() : "Active";
		send(player, "DMM Chest", chestStatus, chestStatus.equalsIgnoreCase("Active") ? Color.GREEN : Color.RED);
	}

	@Override
	public void select(Player player) {
		DeadmanChest chest = DeadmanChestEvent.INSTANCE.getCurrentChest();
		if (chest != null) {
			player.sendMessage("The Deadman Supply Chest is located "+ chest.getLocation().getHint());
		} else {
			player.sendMessage("The Deadman Supply Chest will spawn in "+ DeadmanChestEvent.INSTANCE.timeRemaining() +".");
		}
	}
}
