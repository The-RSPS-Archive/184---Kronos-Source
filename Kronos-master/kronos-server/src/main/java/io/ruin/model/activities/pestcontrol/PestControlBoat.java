package io.ruin.model.activities.pestcontrol;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.GameObject;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;

/**
 * Manages the Pest Control lander.
 * @author Heaven
 */
public final class PestControlBoat {

	/**
	 * The interface id accompanying the Pest Control boat.
	 */
	private static final int OVERLAY = 407;

	/**
	 * The lowest amount of participants required to play this activity.
	 */
	private static final int MINIMUM_PARTY_SIZE = 5;

	/**
	 * The collection of players awaiting within this boat.
	 */
	private final ArrayList<Player> players = new ArrayList<>();

	/**
	 * The Pest Control game link for this {@link PestControlBoat}.
	 */
	public PestControlGame game;

	/**
	 * The mode of this {@link PestControlBoat}.
	 */
	private PestControlGameSettings settings;

	/**
	 * The delay until the next {@link PestControlGame} is attempted to dispatch.
	 */
	public TickDelay nextDeparture = new TickDelay();

	/**
	 * Constructs a new {@link PestControlBoat} instance with the respective properties.
	 * @param settings
	 */
	PestControlBoat(PestControlGameSettings settings) {
		this.settings = settings;
		nextDeparture.delay(1000 * 60 * 6 / 600);
		World.startEvent(e -> {
			while(true) {
				e.delay(5);
				pulse();
			}
		});
	}

	/**
	 * Processes all actions upon the player entering this boat, if applicable.
	 * @param player
	 * @param gangplank
	 * @return
	 */
	public void join(Player player, GameObject gangplank) {
		if (players.stream().anyMatch(p -> p == player)) {
			return;
		}

		if (player.getCombat().getLevel() < settings.combatLvReq()) {
			player.dialogue(new MessageDialogue("You require a combat level of at least "+ settings.combatLvReq() +" or higher to join this lander."));
			return;
		}

		int offset = gangplank.direction == 0 ? 4 : -4;
		player.lock();
		players.add(player);
		player.getMovement().teleport(player.getPosition().relative(offset, 0, 0));
		player.openInterface(InterfaceType.PRIMARY_OVERLAY, OVERLAY);
		player.getPacketSender().sendString(OVERLAY, 21, settings.title());
		player.getPacketSender().sendString(OVERLAY, 6, "Points: "+ player.pestPoints);
		updateOverlay();
		player.teleportListener = p -> {
			leave(p);
			return true;
		};
		player.logoutListener = new LogoutListener().onLogout(p -> {
			leave(p);
			p.getMovement().teleport(settings.exitTile());
		});
		player.unlock();
	}

	/**
	 * Processes all actions upon the player leaving this boat.
	 * @param player
	 * @return
	 */
	public void leave(Player player) {
		player.lock();
		players.removeIf(p -> p == player);
		player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
		player.logoutListener = null;
		player.teleportListener = null;
		player.unlock();
		updateOverlay();
	}

	/**
	 * A method called every update at the rate defined in our event processor.
	 */
	private void pulse() {
		if (lobbySize() >= MINIMUM_PARTY_SIZE || nextDeparture.finished()) {
			if (lobbySize() < MINIMUM_PARTY_SIZE) {
				nextDeparture.delay(1000 * 60 * 2 / 600);
			} else {
				if (game == null || game.ended()) {
					startGame();
					nextDeparture.delay(1000 * 60 * 6 / 600);
				}
			}
		}

		updateOverlay();
	}

	/**
	 * Starts a new {@link PestControlGame} session and inherits the specified difficulty.
	 */
	private void startGame() {
		game = new PestControlGame(settings);
		game.start(players);
		players.clear();
	}

	/**
	 * Updates the overlay properties for all players within this boat.
	 */
	private void updateOverlay() {
		players.forEach(p -> {
			int mins = nextDeparture.remainingToMins();
			String timeDisplay = mins == 0 ? "In a few moments!" : mins + " min";
			p.getPacketSender().sendString(OVERLAY, 4, "Next Departure: "+ timeDisplay);
			if (lobbySize() < MINIMUM_PARTY_SIZE) {
				int needed = MINIMUM_PARTY_SIZE - lobbySize();
				p.getPacketSender().sendString(OVERLAY, 5, "Players Ready: "+ lobbySize() +" ("+ needed +" needed)");
			} else {
				p.getPacketSender().sendString(OVERLAY, 5, "Players Ready: "+ lobbySize());
			}
		});
	}

	/**
	 * The current lobby size for this Pest Control boat.
	 * @return
	 */
	private int lobbySize() {
		return players.size();
	}

	/**
	 * The settings for this game type such as reward amount and other configs.
	 * @return
	 */
	public PestControlGameSettings settings() {
		return settings;
	}
}