package io.ruin.model.activities.pestcontrol;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.activities.miscpvm.PassiveCombat;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;

/**
 * Represents a portal within the Pest Control game.
 * @author Heaven
 */
public class PestPortal {

	private NPC npc;
	private int shieldId;
	private int unshieldId;
	public boolean shieldDropped;
	private String name;
	private PestControlGame game;
	public int widgetIdx;
	public boolean dead;

	PestPortal(PestControlGame game, String name, int widgetIdx, int shieldId, int unshieldId) {
		this.name = name;
		this.shieldId = shieldId;
		this.widgetIdx = widgetIdx;
		this.unshieldId = unshieldId;
		this.game = game;
	}

	public void spawn(int x, int y) {
		NPCDef def = NPCDef.get(shieldId);
		def.combatHandlerClass = PassiveCombat.class;
		def.combatInfo = new npc_combat.Info();
		def.combatInfo.hitpoints = game.settings().portalHp();
		def.combatInfo.defend_animation = -1;
		def.combatInfo.death_animation = -1;

		npc = new NPC(shieldId);
		npc.spawn(game.map().convertX(x), game.map().convertY(y), 0);
		npc.hitListener = new HitListener().postDamage((hit)-> {
			Entity attacker = hit.attacker;
			if (attacker != null && attacker.isPlayer()) {
				Player player = attacker.player;
				if (player.pestGame != null && hit.damage > 0) {
					player.pestActivityScore += hit.damage / 2;
				}
			}
		});
		game.portals().add(this);
		game.map().addNpc(npc);
	}

	/**
	 * Drops this portals shield and makes it vulnerable to attacks. Pests cannot
	 * spawn until the shield has been dropped.
	 */
	void dropShield() {
		shieldDropped = true;
		npc.transform(unshieldId);
		game.players().forEach(p -> p.sendMessage(Color.PURPLE.wrap("The "+ name +" Portal's Shield, has fallen.")));
	}

	/**
	 * Spawns some pests surrounding this portal.
	 */
	void spawnPests() {
		int maxSpawnCount = Random.get(2, 3 + game.settings().ordinal());
		int[] pests = game.settings().pests();
		for (int i = 0; i < maxSpawnCount; i++) {
			int roll = Random.get(5);
			int pestId = pests[Random.get(pests.length - 1)];
			int offsetX;
			int offsetY;
			if (roll == 1) {
				offsetX = -3;
				offsetY = 2;
			} else if (roll == 2) {
				offsetX = 2;
				offsetY = -3;
			} else if (roll == 3) {
				offsetX = 4;
				offsetY = 1;
			} else if (roll == 4) {
				offsetX = 1;
				offsetY = 4;
			} else {
				offsetX = 3;
				offsetY = 0;
			}

			game.map().addNpc(new NPC(pestId).spawn(npc.getPosition().relative(offsetX, offsetY, 0)));
		}
	}

	/**
	 * The link to the {@link NPC} instance backing this portal.
	 * @return
	 */
	NPC link() {
		return npc;
	}

	/**
	 * Supplies if the shield has been dropped for this portal.
	 * @return
	 */
	boolean shieldDropped() {
		return shieldDropped;
	}

	/**
	 * Supplies if the portal still has their shield activated.
	 * @return
	 */
	boolean shieldActive() {
		return !shieldDropped;
	}

	/**
	 * Supplies if the shield is still 'alive' within the Pest Control game.
	 * @return
	 */
	boolean alive() {
		return !dead;
	}

	/**
	 * Supplies the name for this portal.
	 * @return
	 */
	public String name() {
		return name;
	}
}
