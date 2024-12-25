package io.ruin.model.combat;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.skills.magic.spells.TargetSpell;
import lombok.Getter;

import java.util.function.Consumer;

public class Hit {

	public static double PVP_MAGIC_ACCURACY_MODIFIER = 1.22;

	public static double PVP_MELEE_ACCURACY_MODIFIER = 1.12;

	public HitType type;

	public Entity attacker;

	public AttackStyle attackStyle;

	public AttackType attackType;

	public Hit setResetActions(boolean resetActions) {
		this.resetActions = resetActions;
		return this;
	}

	public boolean resetActions = true;

	@Getter
	private int ticks = 1;

	public Hit() {
		this.type = HitType.DAMAGE;
	}

	public Hit(HitType type) {
		this.type = type;
	}

	public Hit(Entity attacker) {
		//count damage for attacker!
		this(attacker, null, null);
	}

	public Hit(Entity attacker, AttackStyle attackStyle) {
		this(attacker, attackStyle, null);
	}

	public Hit(Entity attacker, AttackStyle attackStyle, AttackType attackType) {
		this.type = HitType.DAMAGE;
		this.attacker = attacker;
		this.attackStyle = attackStyle;
		this.attackType = attackType;
	}

	public Hit delay(int ticks) {
		this.ticks = ticks;
		return this;
	}

	public Hit clientDelay(int delay, int cycleRate) {
		this.ticks = Math.max(1, (delay * cycleRate) / Server.tickMs());
		return this;
	}

	public Hit clientDelay(int delay) {
		return clientDelay(delay, 16);
	}

	/**
	 * Nullify
	 */

	public Hit nullify() {
		this.ticks = -1;
		return this;
	}

	public boolean isNullified() {
		return ticks == -1;
	}

	/**
	 * Hide (Still hits, just doesn't show on the player)
	 */

	private boolean hidden;

	public Hit hide() {
		hidden = true;
		return this;
	}

	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Damage
	 */

	private int minDamage;

	public int maxDamage;

	public int damage;

	public Hit randDamage(int maxDamage) {
		return randDamage(0, maxDamage);
	}

	public Hit randDamage(int minDamage, int maxDamage) {
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		return this;
	}

	public Hit fixedDamage(int damage) {
		this.minDamage = damage;
		this.maxDamage = damage;
		return this;
	}

	/**
	 * Damage boost
	 */

	private double damageBoost;

	public Hit boostDamage(double damageBoost) {
		this.damageBoost += damageBoost;
		return this;
	}

	public double getDamageBoost() {
		return damageBoost;
	}

	/**
	 * Attack boost
	 */

	private double attackBoost;

	public Hit boostAttack(double boost) {
		attackBoost += boost;
		return this;
	}

	public double getAttackBoost() {
		return attackBoost;
	}

	/**
	 * Defence boost
	 */

	private double defenceBoost;

	public Hit boostDefence(double boost) {
		defenceBoost += boost;
		return this;
	}

	public double getDefenceBoost() {
		return defenceBoost;
	}

	/**
	 * Ignores
	 */

	public boolean defenceIgnored, prayerIgnored;

	public Hit ignoreDefence() {
		defenceIgnored = true;
		return this;
	}

	public Hit ignorePrayer() {
		prayerIgnored = true;
		return this;
	}

	/**
	 * Dragonfire
	 */

	private double dragonfireResistancePenetration;

	public Hit penetrateDragonfireResistance(double amount) {
		dragonfireResistancePenetration += amount;
		return this;
	}

	public double getDragonfireResistancePenetration() {
		return dragonfireResistancePenetration;
	}

	/**
	 * Blocking
	 */

	private boolean blocked;

	public Hit block() {
		blocked = true;
		damage = 0;
		return this;
	}

	public boolean isBlocked() {
		return blocked;
	}

	/**
	 * Drop
	 */

	private GroundItem dropItem;

	public Hit drop(GroundItem dropItem) {
		this.dropItem = dropItem;
		return this;
	}

	/**
	 * Attacking data
	 */

	public ItemDef attackWeapon, rangedWeapon, rangedAmmo;

	public TargetSpell attackSpell;

	public Hit setAttackWeapon(ItemDef attackWeapon) {
		this.attackWeapon = attackWeapon;
		return this;
	}

	public Hit setRangedWeapon(ItemDef rangedWeapon) {
		this.rangedWeapon = rangedWeapon;
		return this;
	}

	public Hit setRangedAmmo(ItemDef rangedAmmo) {
		this.rangedAmmo = rangedAmmo;
		return this;
	}

	public Hit setAttackSpell(TargetSpell attackSpell) {
		this.attackSpell = attackSpell;
		return this;
	}

	/**
	 * Listener
	 */

	private Consumer<Entity> preDefend, postDefend;

	private Consumer<Entity> preDamage, postDamage, afterPostDamage;

	public Hit preDefend(Consumer<Entity> preDefend) {
		this.preDefend = preDefend;
		return this;
	}

	public Hit postDefend(Consumer<Entity> postDefend) {
		this.postDefend = postDefend;
		return this;
	}

	public Hit preDamage(Consumer<Entity> preDamage) {
		this.preDamage = preDamage;
		return this;
	}

	public Hit postDamage(Consumer<Entity> postDamage) {
		this.postDamage = postDamage;
		return this;
	}

	public Hit afterPostDamage(Consumer<Entity> afterPostDamage) {
		this.afterPostDamage = afterPostDamage;
		return this;
	}

	/**
	 * Defend
	 */

	public boolean defend(Entity target) {
		if (preDefend != null)
			preDefend.accept(target);
		if (target.hitListener != null && target.hitListener.preDefend != null)
			target.hitListener.preDefend.accept(this);
		if (attacker != null && attacker.hitListener != null && attacker.hitListener.preTargetDefend != null)
			attacker.hitListener.preTargetDefend.accept(this, target);

		if (isNullified())
			return false;

		if (ticks > 0 && target.processed)
			ticks--;

		if (!blocked) {
			if (minDamage == maxDamage) {
				damage = minDamage;
			} else {
				maxDamage *= 1D + damageBoost;
				damage = Random.get(minDamage, maxDamage);
			}
			if (attackStyle != null && !defenceIgnored) {
				if (attackStyle == AttackStyle.DRAGONFIRE) {
					damage *= (1 - (Math.max(0, Math.min(1, target.getCombat().getDragonfireResistance()) - dragonfireResistancePenetration)));
					if (damage <= 0)
						block();
				} else if (attacker != null) {
					double attackBonus = getAttackBonus() * (1D + attackBoost);
					if (attacker.isPlayer() && target.isPlayer()) {
						if (PVP_MAGIC_ACCURACY_MODIFIER != 0 && attackStyle.isMagic())
							attackBonus *= PVP_MAGIC_ACCURACY_MODIFIER;
						if (PVP_MELEE_ACCURACY_MODIFIER != 0 && attackStyle.isMelee())
							attackBonus *= PVP_MELEE_ACCURACY_MODIFIER;
					}
					double defenceBonus = getDefenceBonus(target) * (1D + defenceBoost);
					double hitChance;
					if (attackBonus > defenceBonus)
						hitChance = 1D - (defenceBonus + 2D) / (2D * (attackBonus + 1D));
					else
						hitChance = attackBonus / (2D * (defenceBonus + 1D));
					if (attacker.player != null && attacker.player.debug) {
						attacker.player.sendFilteredMessage("Hit:");
						attacker.player.sendFilteredMessage("Chance: " + hitChance);
						attacker.player.sendFilteredMessage("Max Damage: " + maxDamage);
					}
					if (Random.get() > hitChance)
						block();
				}
			}
		}

		if (postDefend != null)
			postDefend.accept(target);
		if (target.hitListener != null && target.hitListener.postDefend != null)
			target.hitListener.postDefend.accept(this);
		if (attacker != null && attacker.hitListener != null && attacker.hitListener.postTargetDefend != null)
			attacker.hitListener.postTargetDefend.accept(this, target);

		//The following check is important for accurate xp drops.
		//It is also important to note that OSRS removed this specific check for PvP combat.
		if (target.npc != null && damage > target.getHp())
			damage = target.getHp();

		return true;
	}

	public double getAttackBonus() {
		return CombatUtils.getAttackBonus(attacker, attackStyle, attackType);
	}

	public double getDefenceBonus(Entity target) {
		return CombatUtils.getDefenceBonus(target, attackStyle);
	}

	/**
	 * Finish
	 */

	public boolean finish(Entity target) {
		if (ticks-- > 0) {
			return false;
		}
		if (target.isLocked(LockType.FULL_DELAY_DAMAGE)) {
			//hmm this spot should be right?!
			return false;
		}
		if (target.isInvincible()) {
			return false;
		}

		if (preDamage != null)
			preDamage.accept(target);
		if (target.hitListener != null && target.hitListener.preDamage != null)
			target.hitListener.preDamage.accept(this);
		if (attacker != null && attacker.hitListener != null && attacker.hitListener.preTargetDamage != null)
			attacker.hitListener.preTargetDamage.accept(this, target);

		if (damage > 0) {
			if (attacker != null) {
				target.getCombat().addKiller(attacker, damage);
			}
			if (type == HitType.HEAL) {
				target.incrementHp(damage);
			} else {
				if (target.incrementHp(-damage) == 0)
					target.getCombat().startDeath(this);
			}
		} else {
			if (type == HitType.DAMAGE)
				type = HitType.BLOCKED;
		}

		if (postDamage != null)
			postDamage.accept(target);
		if (afterPostDamage != null)
			afterPostDamage.accept(target);
		if (target.hitListener != null && target.hitListener.postDamage != null)
			target.hitListener.postDamage.accept(this);
		if (attacker != null && attacker.hitListener != null && attacker.hitListener.postTargetDamage != null)
			attacker.hitListener.postTargetDamage.accept(this, target);

		if (dropItem != null)
			dropItem.spawn();

		return true;
	}

	/**
	 * Removed
	 */

	public boolean removed;

}