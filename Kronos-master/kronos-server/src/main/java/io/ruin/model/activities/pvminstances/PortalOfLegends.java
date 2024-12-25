package io.ruin.model.activities.pvminstances;

import io.ruin.model.activities.wilderness.BossEvent;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class PortalOfLegends {

	private static void enter(Player player, GameObject object) {
		if (BossEvent.boss != null) {
			String spawnMessage = BossEvent.boss.getDef().name + " has been sighted in level " +
					BossEvent.boss.wildernessSpawnLevel + " wilderness. Do you wish to teleport?";

			player.dialogue(new MessageDialogue(spawnMessage), new OptionsDialogue("Teleport to Boss?",
					new Option("Yes, I'm fearless!", PortalOfLegends::teleportToBoss), new Option("No, never mind.")));
		} else {
			player.dialogue(new PlayerDialogue("There doesn't seem to be a boss right now."));
		}
	}

	private static void teleportToBoss(Player player) {
		player.getMovement().startTeleport(e -> {
			player.animate(714);
			player.graphics(111, 92, 0);
			player.publicSound(200);
			e.delay(3);
			player.getMovement().teleport(BossEvent.boss.getPosition());
		});
	}

	static {
		ObjectAction.register("Ket'ian Wilderness Boss Portal", "Enter", PortalOfLegends::enter);
	}

}
