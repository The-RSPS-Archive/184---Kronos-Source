package io.ruin.model.activities.pestcontrol.npcs;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;

/**
 * The combat script for the Brawler pest.
 * @author Heaven
 */
public class Brawler extends NPCCombat {

	@Override
	public void init() {
		npc.hitListener = new HitListener().postDamage((hit)-> {
			Entity attacker = hit.attacker;
			if (attacker != null && attacker.isPlayer()) {
				Player player = attacker.player;
				if (player.pestGame != null && hit.damage > 0) {
					player.pestActivityScore += hit.damage / 2;
				}
			}
		});
	}

	@Override
	public void follow() {
		follow(1);
	}

	@Override
	public boolean attack() {
		if (withinDistance(1)) {
			basicAttack();
			return true;
		}
		return false;
	}
}
