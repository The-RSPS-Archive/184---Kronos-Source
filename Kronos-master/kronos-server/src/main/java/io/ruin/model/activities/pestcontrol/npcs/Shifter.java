package io.ruin.model.activities.pestcontrol.npcs;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The combat script for the Shifter pest.
 * @author Heaven
 */
public class Shifter extends NPCCombat {

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
		if (!teleport() && withinDistance(1)) {
			basicAttack();
			return true;
		}
		return false;
	}

	private boolean teleport() {
		if (target != null && Random.rollDie(4, 1)) {
			Bounds b = new Bounds(target.getPosition(), 1);
			List<Position> tiles = new ArrayList<>();
			b.forEachPos(tiles::add);
			Collections.shuffle(tiles);
			Position dstTile = Random.get(tiles);
			npc.getMovement().teleport(dstTile);
			return true;
		}

		return false;
	}
}
