package io.ruin.model.activities.pvminstances;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class DonatorSteppingStones {

	private static void jumpStone(Player player, GameObject object) {
		if (player.isGroup(PlayerGroup.SAPPHIRE)) {
			player.dialogue(new NPCDialogue(2108, "Hold on, that's not yours to enter."),
					new PlayerDialogue("What do you mean..?"),
					new NPCDialogue(2108, "The outer bounds of the island are only available to Super Donators or higher. It looks like you don't qualify yet, unfortunately."),
					new PlayerDialogue("So what do I do?"),
					new NPCDialogue(2108, "Simply put, purchasing additional things in the store for a total of 50$, minus what you've spent thus far, lets you enter!"),
					new OptionsDialogue("Open the store?", new Option("Yes please, I'll take a look.", DonatorSteppingStones::openStore), new Option("No thank you.")));
			return;
		}

		int dirX = 0; int dirY = 0;
		int dx = object.x - player.getAbsX();
		int dy = object.y - player.getAbsY();

		if (dx == -1 && dy == 0) {
			dirX = -2;
		} else if (dx == 1 && dy == 0) {
			dirX = 2;
		} else if (dx == 0 && dy == 1) {
			dirY = 2;
		} else if (dx == 0 && dy == -1) {
			dirY = -2;
		} else {
			return;
		}

		player.getMovement().teleport(player.getAbsX() + dirX, player.getAbsY() + dirY, 0);
	}

	public static void openStore(Player player) {
		player.openUrl(World.type.getWorldName() + " Store", World.type.getWebsiteUrl() + "/store");
	}

	static {
		ObjectAction.register(4411, "Jump-to", DonatorSteppingStones::jumpStone);
	}

}
