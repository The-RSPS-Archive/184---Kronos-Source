package io.ruin.model.activities.cluescrolls;

import io.ruin.cache.ObjectDef;
import io.ruin.content.activities.lms.LastManStanding;
import io.ruin.model.activities.motherlodemine.MotherlodeMine;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.model.skills.Tool.HAMMER;

public class ClueObjects {

	static {
		/*
		 * Searchables
		 */
		ObjectDef.forEach(def -> {
			if (def.name.equalsIgnoreCase("mine cart") || def.name.equalsIgnoreCase("bookcase")
					|| def.name.equalsIgnoreCase("chest") || def.name.equalsIgnoreCase("open chest")
					|| def.name.contains("wardrobe") || def.name.equalsIgnoreCase("drawers")
					|| (def.id != LastManStanding.LOOT_CRATE_ID && def.name.equalsIgnoreCase("crate")) || def.name.equalsIgnoreCase("boxes")
					|| def.name.equalsIgnoreCase("wheelbarrow") || def.name.equalsIgnoreCase("bush")
					|| def.name.equalsIgnoreCase("stones") || def.name.equalsIgnoreCase("crates")) {
				ObjectAction.register(def.id, "search", (player, obj) -> {
					if (obj.crypticClue != null && obj.crypticClue.advance(player))
						return;
					if (obj.mapClue != null && obj.mapClue.advance(player))
						return;
					if (obj.id == 357) {
						for (Position position : MotherlodeMine.HAMMER_CRATES) {
							if (position.equals(obj.x, obj.y)) {
								if (player.getInventory().isFull() || player.getInventory().hasId(HAMMER)) {
									player.sendFilteredMessage("You search the crate but find nothing.");
									return;
								}

								player.getInventory().add(HAMMER, 1);
								player.dialogue(new ItemDialogue().one(HAMMER, "You've found a hammer. How handy."));
								return;
							}
						}

						for (Position position : MotherlodeMine.PICKAXE_CRATES) {
							if (position.equals(obj.x, obj.y)) {
								if (player.getInventory().isFull() || player.getInventory().hasId(1265)) {
									player.sendFilteredMessage("You search the crate but find nothing.");
									return;
								}

								player.getInventory().add(1265, 1);
								player.dialogue(new ItemDialogue().one(1265, "You've found a bronze pickaxe. How handy."));
								return;
							}
						}
					}
					player.sendFilteredMessage("You find nothing interesting.");
				});
			}
		});

		/*
		 * Wardrobe
		 */
		ObjectAction.register(5622, "open", (player, obj) -> obj.setId(obj.id + 1));
		ObjectAction.register(5623, "close", (player, obj) -> obj.setId(obj.originalId));

		/*
		 * Chests
		 */
		ObjectAction.register(375, "open", (player, obj) -> {
			player.animate(536);
			obj.setId(378);
		});
		ObjectAction.register(378, "shut", (player, obj) -> {
			player.animate(535);
			obj.setId(375);
		});
		ObjectAction.register(25592, "open", (player, obj) -> {
			player.animate(536);
			obj.setId(25593);
		});
		ObjectAction.register(25593, "shut", (player, obj) -> {
			player.animate(535);
			obj.setId(25592);
		});

		/*
		 * Coffins
		 */
		ObjectAction.register(398, "open", (player, obj) -> obj.setId(3577));
		ObjectAction.register(3577, "close", (player, obj) -> obj.setId(398));

		/*
		 * Staircase
		 */
		ObjectAction.register(24076, 2973, 3385, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2973, 3386));
		ObjectAction.register(24075, 2973, 3384, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2972, 3385, 1));
		ObjectAction.register(11793, 3255, 3421, 1, "climb-down", (player, obj) -> player.getMovement().teleport(3256, 3420));
		ObjectAction.register(11789, 3255, 3421, 0, "climb-up", (player, obj) -> player.getMovement().teleport(3257, 3421, 1));

		ObjectAction.register(18992, 2966, 3215, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2964, player.getAbsY(), 0));
		ObjectAction.register(18991, 2965, 3215, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2968, player.getAbsY(), 1));
		ObjectAction.register(24076, 3035, 3363, 1, "climb-down", (player, obj) -> player.getMovement().teleport(3036, 3363, 0));
		ObjectAction.register(24075, 3034, 3363, 0, "climb-up", (player, obj) -> player.getMovement().teleport(3036, 3363, 1));

		/*
		 * West Ardougne door
		 */
		ObjectAction.register(8739, 2558, 3300, 0, "open", (player, obj) -> player.getMovement().teleport(2556, 3300));
		ObjectAction.register(8738, 2558, 3299, 0, "open", (player, obj) -> player.getMovement().teleport(2556, 3299));

		ObjectAction.register(8739, 2557, 3299, 0, "open", (player, obj) -> player.getMovement().teleport(2559, 3299));
		ObjectAction.register(8738, 2557, 3300, 0, "open", (player, obj) -> player.getMovement().teleport(2559, 3300));

	}
}
