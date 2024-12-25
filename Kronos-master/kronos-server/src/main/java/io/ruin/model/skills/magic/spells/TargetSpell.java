package io.ruin.model.skills.magic.spells;

import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.CombatUtils;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.handlers.EquipmentStats;
import io.ruin.model.item.Item;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.StatType;

import java.util.function.BiPredicate;

public class TargetSpell extends Spell {

    public static final TargetSpell[] AUTOCASTS = new TargetSpell[53];

    protected int lvlReq;

    public double baseXp;

    private int maxDamage;

    protected int animationId;

    protected int[] castGfx;
    protected int[] castSound;

    protected int[] hitGfx;

    protected int hitSoundId = -1;

    private boolean multiTarget;

    protected Projectile projectile;

    public int getLvlReq() {
        return lvlReq;
    }

    public double getBaseXp() {
        return baseXp;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getAnimationId() {
        return animationId;
    }

    public int[] getCastGfx() {
        return castGfx;
    }

    public int[] getCastSound() {
        return castSound;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Item[] getRuneItems() {
        return runeItems;
    }

    protected Item[] runeItems;

    protected BiPredicate<Entity, Entity> castCheck;

    public void setLvlReq(int lvlReq) {
        this.lvlReq = lvlReq;
    }

    public void setBaseXp(double baseXp) {
        this.baseXp = baseXp;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public void setCastGfx(int id, int height, int delay) {
        this.castGfx = new int[]{id, height, delay};
    }

    public void setCastSound(int id, int type, int delay) {
        this.castSound = new int[]{id, type, delay};
    }

    public void setHitGfx(int id, int height) {
        this.hitGfx = new int[]{id, height};
    }

    public void setHitSound(int id) {
        this.hitSoundId = id;
    }

    public void setMultiTarget() {
        this.multiTarget = true;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public void setRunes(Item... runeItems) {
        this.runeItems = runeItems;
    }

    public void setCastCheck(BiPredicate<Entity, Entity> castCheck) {
        this.castCheck = castCheck;
    }

    public void setAutoCast(int index) {
        AUTOCASTS[index] = this;
    }

    public TargetSpell() {
        entityAction = (p, e) -> p.getCombat().queueSpell(this, e);
    }

    public boolean cast(Entity entity, Entity target) {
        return cast(entity, target, -1, maxDamage);
    }

    private boolean cast(Entity entity, Entity target, int projectileDuration, int maxDamage) {
        boolean primaryCast = projectileDuration == -1;
        if(primaryCast) {
            RuneRemoval r = null;
            if(entity.player != null) {
                if(!entity.player.getStats().check(StatType.Magic, lvlReq, "cast this spell"))
                    return false;
                if(DuelRule.NO_MAGIC.isToggled(entity.player)) {
                    entity.player.sendMessage("Magic has been disabled for this duel!");
                    return false;
                }
                if(runeItems != null && (r = RuneRemoval.get(entity.player, runeItems)) == null) {
                    entity.player.sendMessage("You don't have enough runes to cast this spell.");
                    return false;
                }
            }
            if(castCheck != null && !castCheck.test(entity, target))
                return false;
            entity.animate(animationId);
            if(castGfx != null)
                entity.graphics(castGfx[0], castGfx[1], castGfx[2]);
            if(castSound != null)
                entity.publicSound(castSound[0], castSound[1], castSound[2]);
            if(projectile != null) //tb should be the only spell this is true for
                projectileDuration = projectile.send(entity, target);
            double percentageBonus = entity.getCombat().getBonus(EquipmentStats.MAGIC_DAMAGE);
            if(percentageBonus > 0)
                maxDamage *= (1D + percentageBonus * 0.01);
            if(r != null)
                r.remove();
        }

        Hit hit = new Hit(entity, AttackStyle.MAGIC, AttackType.ACCURATE)
                .randDamage(maxDamage)
                .clientDelay(projectileDuration, 19)
                .setAttackSpell(this);
        hit.postDamage(t -> {
            if(hit.isBlocked()) {
                t.graphics(85, 124, 0);
                t.publicSound(227, 1, 0);
                hit.hide();
            } else {
                if(hitGfx != null)
                    t.graphics(hitGfx[0], hitGfx[1], 0);
                if(hitSoundId != -1)
                    t.publicSound(hitSoundId, 1, 0);
            }
        });
        beforeHit(hit, target);
        int damage = target.hit(hit);
        if(baseXp > 0 && entity.isPlayer())
            if (target.isPlayer() || (target.isNpc() && target.npc.getId() != 2668 && target.npc.getId() != 7413))
                CombatUtils.addMagicXp(entity.player, baseXp, damage, target.isNpc());
        afterHit(hit, target);

        if(primaryCast && multiTarget && target.inMulti()) {
            int entityIndex = entity.getClientIndex();
            int targetIndex = target.getClientIndex();
            int targetCount = 0;
            for(Player player : target.localPlayers()) {
                int playerIndex = player.getClientIndex();
                if(playerIndex == entityIndex || playerIndex == targetIndex)
                    continue;
                if(!player.getPosition().isWithinDistance(target.getPosition(), 1))
                    continue;
                if(entity.player != null) {
                    if(!entity.player.getCombat().canAttack(player, false))
                        continue;
                } else {
                    if(!entity.npc.getCombat().canAttack(player))
                        continue;
                }
                cast(entity, player, projectileDuration, maxDamage);
                if(++targetCount >= 9)
                    break;
            }
            for(NPC npc : target.localNpcs()) {
                int npcIndex = npc.getClientIndex();
                if(npcIndex == entityIndex || npcIndex == targetIndex)
                    continue;
                if(!npc.getPosition().isWithinDistance(target.getPosition(), 1))
                    continue;
                if(npc.getDef().ignoreMultiCheck)
                    continue;
                if(entity.player != null) {
                    if(!entity.player.getCombat().canAttack(npc, false))
                        continue;
                } else {
                    if(!entity.npc.getCombat().canAttack(npc))
                        continue;
                }
                cast(entity, npc, projectileDuration, maxDamage);
                if(++targetCount >= 9)
                    break;
            }
        }
        return true;
    }

    protected void beforeHit(Hit hit, Entity target) {
        //override if needed lol
    }

    protected void afterHit(Hit hit, Entity target) {
        //override if needed lol
    }

    /**
     * Misc
     */

    protected static boolean allowHold(Entity entity, Entity target) {
        if(target.hasFreezeImmunity()) {
            if(entity.player != null) {
                if(target.isFrozen())
                    entity.player.sendMessage("Your target is already held by a magical force.");
                else
                    entity.player.sendMessage("Your target is currently immune to that spell.");
            }
            return false;
        }
        return true;
    }

    protected static void hold(Hit hit, Entity target, int seconds, boolean ice) {
        if(hit.isBlocked() || target.hasFreezeImmunity())
            return;
        target.freeze(seconds, hit.attacker);
        if(ice && target.player != null) {
            target.player.sendMessage("You have been frozen.");
            target.player.getPacketSender().sendWidget(Widget.BARRAGE, seconds);
        }
    }

}