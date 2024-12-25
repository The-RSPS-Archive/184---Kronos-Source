package io.ruin.model.activities.pestcontrol.npcs;

import com.google.common.collect.Streams;
import io.ruin.api.utils.Random;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.utility.Misc;

/**
 * The combat script for the Spinner pest.
 * @author Heaven
 */
public class Spinner extends NPCCombat {

	@Override
	public void init() {
		npc.deathEndListener = (entity, killer, hit) -> poison();
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
		follow(6);
	}

	@Override
	public boolean attack() {
		if (!heal() && withinDistance(1)) {
			basicAttack();
			return true;
		}
		return false;
	}

	public boolean heal() {
		NPC portal = Streams.stream(npc.localNpcs())
				.filter(i -> i.getId() >= 1747 && i.getId() <= 1750)
				.filter(i -> Misc.getEffectiveDistance(npc, i) <= 1)
				.findAny().orElse(null);

		if (portal != null) {
			npc.face(portal);
			portal.incrementHp(Random.get(5, 10));
			return true;
		}

		return false;
	}

	private void poison() {
		Entity victim = Streams.stream(npc.localNpcs()).filter(i -> Misc.getEffectiveDistance(npc, i) <= 1).findAny().orElse(null);
		if (victim != null && !victim.isPoisonImmune() && !victim.isPoisoned()) {
			victim.poison(5);
		}
	}
}
