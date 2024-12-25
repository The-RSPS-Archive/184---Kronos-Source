package io.ruin.model.skills;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.magic.spells.modern.TeleportToHouse;
import io.ruin.utility.OfflineMode;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CapePerks {

	private static void staminaBoost(Player p, Item item) {
		if (!available(p.lastAgilityCapeBoost)) {
			p.sendMessage("You have already made use of that today.");
			return;
		}

		p.getMovement().restoreEnergy(100);
		Config.STAMINA_POTION.set(p, 1);
		p.staminaTicks = 100; // One minute
		p.getPacketSender().sendWidget(Widget.STAMINA, 60);
		p.sendMessage("You activate the effect of your Agility cape.");
		p.lastAgilityCapeBoost = System.currentTimeMillis();
	}

	private static void teleToHouse(Player player, Item item) {
		TeleportToHouse.teleport(player, 0);
	}

	private static void houseDestinations(Player player, Item item) {
		player.dialogue(new OptionsDialogue("Choose a Destination",
				new Option("Rimmington", () -> teleport(player, 2954, 3224)),
				new Option("Taverley", () -> teleport(player, 2894, 3465)),
				new Option("Pollnivneach", () -> teleport(player, 3340, 3004)),
				new Option("Hosidius", () -> teleport(player, 1743, 3517)),
				new Option("More...", () -> {
					player.dialogue(new OptionsDialogue("Choose a Destination",
							new Option("Rellekka", () -> teleport(player, 2670, 3632)),
							new Option("Brimhaven", () -> teleport(player, 2758, 3178)),
							new Option("Yanille", () -> teleport(player, 2544, 3095))
					));
				})
		));
	}

	private static void teleport(Player player, int x, int y) {
		player.getMovement().startTeleport(e -> {
			player.animate(714);
			player.graphics(111, 92, 0);
			player.publicSound(200);
			e.delay(3);
			player.getMovement().teleport(x, y, 0);
		});
	}

	private static void teleportToCraftingGuild(Player player, Item item) {
		teleport(player, 2933, 3287);
	}

	private static void teleportToFarmingGuild(Player player, Item item) {
		player.sendMessage("Please note that the farming guild is not operated at this time.");
		teleport(player, 1248, 3721);
	}

	private static void teleportToFishingGuild(Player player, Item item) {
		teleport(player, 2590, 3417);
	}

	private static void teleportToOttosGrotto(Player player, Item item) {
		teleport(player, 2505, 3488);
	}

	private static void teleportToWarriorsGuild(Player player, Item item) {
		teleport(player, 2875, 3546);
	}

	private static boolean available(long lastUsed) {
		if (lastUsed <= 0) {
			return true;
		}

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(System.currentTimeMillis());
		int currentDay = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTimeInMillis(lastUsed);
		int lastUsedDay = cal.get(Calendar.DAY_OF_MONTH);

		return System.currentTimeMillis() > lastUsed && lastUsedDay != currentDay;
	}

	public static boolean wearsAttackCape(Player player) {
		if (!OfflineMode.enabled) {
			return false;
		}

		int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
		return cape == 9747 || cape == 9748;
	}

	public static boolean wearsCookingCape(Player player) {
		if (!OfflineMode.enabled) {
			return false;
		}

		int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
		return cape == 9801 || cape == 9802;
	}

	public static boolean wearsFarmingCape(Player player) {
		if (!OfflineMode.enabled) {
			return false;
		}

		int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
		return cape == 9810 || cape == 9811;
	}

	public static boolean wearsHPCape(Player player) {
		if (!OfflineMode.enabled) {
			return false;
		}

		int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
		return cape == 9768 || cape == 9769;
	}

	public static boolean wearsThievingCape(Player player) {
		if (!OfflineMode.enabled) {
			return false;
		}

		int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
		return cape == 9777 || cape == 9778;
	}

	public static boolean wearsWoodcuttingCape(Player player) {
		if (!OfflineMode.enabled) {
			return false;
		}

		int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
		return cape == 9807 || cape == 9808;
	}

	public static boolean wearsMiningCape(Player player) {
		if (!OfflineMode.enabled) {
			return false;
		}

		int cape = player.getEquipment().getId(Equipment.SLOT_CAPE);
		return cape == 9792 || cape == 9793;
	}

	static {
		if (OfflineMode.enabled) {
			// Agility
			ItemAction.registerInventory(9771, "Stamina Boost", CapePerks::staminaBoost);
			ItemAction.registerInventory(9772, "Stamina Boost", CapePerks::staminaBoost);

			// Construction
			ItemAction.registerInventory(9789, "Tele to POH", CapePerks::teleToHouse);
			ItemAction.registerInventory(9790, "Tele to POH", CapePerks::teleToHouse);
			ItemAction.registerEquipment(9789, "Tele to POH", CapePerks::teleToHouse);
			ItemAction.registerEquipment(9790, "Tele to POH", CapePerks::teleToHouse);
			ItemAction.registerInventory(9789, "Teleport", CapePerks::houseDestinations);
			ItemAction.registerInventory(9790, "Teleport", CapePerks::houseDestinations);
			ItemAction.registerEquipment(9789, "Teleport", CapePerks::houseDestinations);
			ItemAction.registerEquipment(9790, "Teleport", CapePerks::houseDestinations);

			// Crafting
			ItemAction.registerInventory(9780, "Teleport", CapePerks::teleportToCraftingGuild);
			ItemAction.registerInventory(9781, "Teleport", CapePerks::teleportToCraftingGuild);
			ItemAction.registerEquipment(9780, "Teleport", CapePerks::teleportToCraftingGuild);
			ItemAction.registerEquipment(9781, "Teleport", CapePerks::teleportToCraftingGuild);

			// Farming
			ItemAction.registerInventory(9810, "Teleport", CapePerks::teleportToFarmingGuild);
			ItemAction.registerInventory(9811, "Teleport", CapePerks::teleportToFarmingGuild);
			ItemAction.registerEquipment(9810, "Teleport", CapePerks::teleportToFarmingGuild);
			ItemAction.registerEquipment(9811, "Teleport", CapePerks::teleportToFarmingGuild);

			// Strength
			ItemAction.registerInventory(9750, "Teleport", CapePerks::teleportToWarriorsGuild);
			ItemAction.registerInventory(9751, "Teleport", CapePerks::teleportToWarriorsGuild);
			ItemAction.registerEquipment(9750, "Teleport", CapePerks::teleportToWarriorsGuild);
			ItemAction.registerEquipment(9751, "Teleport", CapePerks::teleportToWarriorsGuild);

			// Fishing
			ItemAction.registerInventory(9798, "Fishing Guild", CapePerks::teleportToFishingGuild);
			ItemAction.registerInventory(9799, "Fishing Guild", CapePerks::teleportToFishingGuild);
			ItemAction.registerEquipment(9798, "Fishing Guild", CapePerks::teleportToFishingGuild);
			ItemAction.registerEquipment(9799, "Fishing Guild", CapePerks::teleportToFishingGuild);
			ItemAction.registerInventory(9798, "Otto's Grotto", CapePerks::teleportToOttosGrotto);
			ItemAction.registerInventory(9799, "Otto's Grotto", CapePerks::teleportToOttosGrotto);
			ItemAction.registerEquipment(9798, "Otto's Grotto", CapePerks::teleportToOttosGrotto);
			ItemAction.registerEquipment(9799, "Otto's Grotto", CapePerks::teleportToOttosGrotto);
		}
	}

}
