package io.ruin.model.activities.bosses;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.utility.TickDelay;

public class DemonicGorilla extends NPCCombat {
    //todo use dumped projectiles
    private static final Projectile BOULDER_DROP_PROJECTILE = new Projectile(856, 150, 0, 0, 135, 0, 0, 127);
    private static final int BOULDER_HIT_GFX = 305;

    private static final Projectile RANGED_PROJECTILE = new Projectile(1302, 20, 31, 35, 35, 10, 0, 32);
    private static final int RANGED_HIT_GFX = 1303;
    private static final int RANGED_ANIM = 7227;

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1304, 25, 31, 25, 35, 10, 0, 32);
    private static final int MAGIC_HIT_GFX = 1305;
    private static final int MAGIC_ANIM = 7225;

    private static final int SWITCH_ANIM = 7228;

    private static final int MELEE_PRAYER = 7144;
    private static final int RANGED_PRAYER = 7145;
    private static final int MAGIC_PRAYER = 7146;

    private static final int DAMAGE_THRESHOLD = 50;
    private static final int MISS_THRESHOLD = 3;

    private int damageTaken = 0;
    private int misses = 0;
    private AttackStyle style = AttackStyle.MAGIC;
    private TickDelay boulderCooldown = new TickDelay();

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend).postDefend(this::postDefend);
        npc.deathEndListener = (entity, killer, killHit) -> restoreDefaults();
    }

    private void restoreDefaults() {
        boulderCooldown.reset();
        npc.transform(MELEE_PRAYER);
        style = Random.rollPercent(50) ? AttackStyle.CRUSH : (Random.rollPercent(50) ? AttackStyle.RANGED : AttackStyle.MAGIC);
        misses = damageTaken = 0;
    }

    private void preDefend(Hit hit) {
        if(hit.attackStyle == null)
            return;
        if(hit.attackStyle.isMelee() && npc.getId() == MELEE_PRAYER)
            hit.block();
        else if(hit.attackStyle.isRanged() && npc.getId() == RANGED_PRAYER)
            hit.block();
        else if(hit.attackStyle.isMagic() && npc.getId() == MAGIC_PRAYER)
            hit.block();
    }

    private void postDefend(Hit hit) {
        damageTaken += hit.damage;
        if(damageTaken >= DAMAGE_THRESHOLD && hit.attackStyle != null) {
            damageTaken = 0;
            npc.transform(hit.attackStyle.isMelee() ? MELEE_PRAYER : (hit.attackStyle.isRanged() ? RANGED_PRAYER : MAGIC_PRAYER));
            if (target != null && target.getCombat().getTarget() == npc)
                target.getCombat().reset();
        }
    }

    @Override
    public void follow() {
        follow(style.isMelee() ? 1 : 8);
    }

    @Override
    public boolean attack() {
        if(style.isMelee() && !withinDistance(1)) {
            switchAttackStyle();
            return false;
        }
        if(!withinDistance(8))
            return false;
        if(!boulderCooldown.isDelayed() && Random.rollDie(4, 1)) {
            boulderAttack();
            return true;
        }
        switch(style) {
            case SLASH:
            case STAB:
            case CRUSH:
                meleeAttack();
                break;
            case MAGIC:
                rangedAttack();
                break;
            case RANGED:
                magicAttack();
                break;
            default:
                break;
        }
        return true;
    }

    public void magicAttack() {
        npc.animate(MAGIC_ANIM);
        int delay = MAGIC_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC)
                .randDamage(info.max_damage)
                .clientDelay(delay);
        hit.postDamage(t -> {
            attacked(hit.damage);
            t.graphics(MAGIC_HIT_GFX);
        });
        target.hit(hit);
    }

    public void boulderAttack() {
        boulderCooldown.delay(30);
        npc.animate(SWITCH_ANIM);
        Position pos = target.getPosition().copy();
        int clientDelay = BOULDER_DROP_PROJECTILE.send(npc.getAbsX(), npc.getAbsY(), pos.getX(), pos.getY());
        int tickDelay = (((25 * clientDelay)) / 600) - 2;
        World.sendGraphics(BOULDER_HIT_GFX, 35, clientDelay, pos.getX(), pos.getY(), pos.getZ());
        npc.addEvent(event -> {
            event.delay(tickDelay);
            if(target == null)
                return;
            if(target.getPosition().equals(pos)) {
                target.hit(new Hit(npc, null, null).fixedDamage(target.getHp() / 3).ignoreDefence().ignorePrayer());
            }
        });
    }

    public void meleeAttack() {
        attacked(basicAttack().damage);
    }

    public void rangedAttack() {
        npc.animate(RANGED_ANIM);
        int delay = RANGED_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED)
                .randDamage(info.max_damage)
                .clientDelay(delay);
        hit.postDamage(t -> {
            attacked(hit.damage);
            t.graphics(RANGED_HIT_GFX);
        });
        target.hit(hit);
    }

    private void attacked(int damage) {
        if(damage == 0) {
            misses++;
            if(misses >= MISS_THRESHOLD) {
                npc.addEvent(event -> {
                    event.delay(2);
                    switchAttackStyle();
                });
            }
        } else {
            misses = 0;
        }
    }

    private void switchAttackStyle() {
        if(style.isMelee()) {
            style = Random.rollPercent(50) ? AttackStyle.MAGIC : AttackStyle.RANGED;
        } else if(style.isMagic()) {
            style = Random.rollPercent(50) ? AttackStyle.CRUSH : AttackStyle.RANGED;
        } else if(style.isRanged()) {
            style = Random.rollPercent(50) ? AttackStyle.MAGIC : AttackStyle.CRUSH;
        } else {
            return;
        }
        misses = 0;
    }
}
